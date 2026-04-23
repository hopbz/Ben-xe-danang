import React, { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import * as z from 'zod';
import { addMonths, startOfDay } from 'date-fns';
import { getLoaiXes, getNhaXes, getTaiXes, createXe, type LoaiXeResponse, type NhaXeResponse, type TaiXeResponse } from '../services/api';
import { Button } from '../components/ui/button';
import { Input } from '../components/ui/input';
import { Label } from '../components/ui/label';
import { Card, CardContent, CardHeader, CardTitle } from '../components/ui/card';
import { Save } from 'lucide-react';
import { useNavigate } from 'react-router-dom';

const vehicleSchema = z.object({
  maXe: z.string().min(1, 'Mã xe không được để trống').max(50, 'Mã xe tối đa 50 ký tự'),
  hangSanXuat: z.string().min(1, 'Hãng sản xuất không được để trống').max(100, 'Hãng sản xuất tối đa 100 ký tự'),
  loaiXeId: z.string().min(1, 'Vui lòng chọn loại xe'),
  bienSo: z.string().min(1, 'Biển số không được để trống').regex(/^\d{2}[A-Z]\d-\d{5}$/, 'Biển số phải đúng dạng xxYx-xxxxx'),
  hanKiemDinh: z.string().refine(dateStr => dateStr && startOfDay(new Date(dateStr)) >= startOfDay(addMonths(new Date(), 1)), { message: 'Hạn kiểm định phải lớn hơn thời điểm hiện tại ít nhất 1 tháng' }),
  taiXeId: z.string().min(1, 'Vui lòng chọn tài xế'),
  nhaXeId: z.string().min(1, 'Vui lòng chọn nhà xe'),
});

type VehicleFormValues = z.infer<typeof vehicleSchema>;

export default function AddVehicle() {
  const navigate = useNavigate();
  const [nhaXes, setNhaXes] = useState<NhaXeResponse[]>([]);
  const [taiXes, setTaiXes] = useState<TaiXeResponse[]>([]);
  const [loaiXes, setLoaiXes] = useState<LoaiXeResponse[]>([]);
  const [submitting, setSubmitting] = useState(false);
  const [apiError, setApiError] = useState('');
  const [loadError, setLoadError] = useState('');
  const [nhaXeText, setNhaXeText] = useState('');
  const [taiXeText, setTaiXeText] = useState('');

  const { register, handleSubmit, setValue, formState: { errors } } = useForm<VehicleFormValues>({ resolver: zodResolver(vehicleSchema), mode: 'onTouched' });

  useEffect(() => {
    Promise.all([getNhaXes(), getTaiXes(), getLoaiXes()])
      .then(([nx, tx, lx]) => {
        setNhaXes(nx);
        setTaiXes(tx);
        setLoaiXes(lx);
      })
      .catch(err => setLoadError('Không thể tải dữ liệu: ' + err.message));
  }, []);

  const onSubmit = async (data: VehicleFormValues) => {
    setSubmitting(true);
    setApiError('');
    try {
      await createXe({
        maXe: data.maXe,
        hangSanXuat: data.hangSanXuat,
        bienSo: data.bienSo,
        hanKiemDinh: data.hanKiemDinh,
        loaiXeId: Number(data.loaiXeId),
        taiXeId: Number(data.taiXeId),
        nhaXeId: Number(data.nhaXeId),
      });
      navigate('/vehicles-schedules');
    } catch (err: any) {
      setApiError(err.message || 'Lỗi không xác định');
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="space-y-6">
      <div>
        <h2 className="text-2xl font-bold tracking-tight text-[#003366] mb-1">Thêm mới Xe</h2>
        <p className="text-slate-500 text-sm">Nhập thông tin chi tiết để thêm phương tiện mới vào hệ thống</p>
      </div>

      {loadError && <div className="text-red-500 text-sm bg-red-50 px-4 py-3 rounded-lg">⚠️ {loadError}</div>}

      <Card className="max-w-2xl">
        <CardHeader><CardTitle className="text-xl text-[#003366]">Thông tin phương tiện</CardTitle></CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div className="space-y-2"><Label htmlFor="maXe">Mã xe *</Label><Input id="maXe" {...register('maXe')} placeholder="VD: PT-001" />{errors.maXe && <p className="text-sm text-red-500">{errors.maXe.message}</p>}</div>
              <div className="space-y-2"><Label htmlFor="hangSanXuat">Hãng sản xuất *</Label><Input id="hangSanXuat" {...register('hangSanXuat')} placeholder="VD: THACO" />{errors.hangSanXuat && <p className="text-sm text-red-500">{errors.hangSanXuat.message}</p>}</div>
              <div className="space-y-2"><Label htmlFor="loaiXeId">Loại xe *</Label><select id="loaiXeId" {...register('loaiXeId')} className="flex w-full rounded-[8px] border border-[#E0E5F2] bg-white px-[12px] py-[10px] text-[13px] text-[#2B3674] focus:outline-none focus:ring-1 focus:ring-[#003366]"><option value="">-- Chọn loại xe --</option>{loaiXes.map((lx) => <option key={lx.id} value={lx.id}>{lx.maLoaiXe} - {lx.moTaLoaiXe} ({lx.soLuongChoNgoi} chỗ)</option>)}</select>{errors.loaiXeId && <p className="text-sm text-red-500">{errors.loaiXeId.message}</p>}</div>
              <div className="space-y-2"><Label htmlFor="bienSo">Biển số *</Label><Input id="bienSo" {...register('bienSo')} placeholder="VD: 43H1-12345" />{errors.bienSo && <p className="text-sm text-red-500">{errors.bienSo.message}</p>}</div>
              <div className="space-y-2 md:col-span-2"><Label htmlFor="nhaXeText">Nhà xe *</Label><Input id="nhaXeText" list="nhaXeList" placeholder="Nhập hoặc chọn nhà xe..." value={nhaXeText} onChange={(e) => { const val = e.target.value; setNhaXeText(val); const found = nhaXes.find(nx => nx.tenNhaXe === val); setValue('nhaXeId', found ? String(found.id) : '', { shouldValidate: true }); }} /><datalist id="nhaXeList">{nhaXes.map(nx => <option key={nx.id} value={nx.tenNhaXe} />)}</datalist><input type="hidden" {...register('nhaXeId')} />{errors.nhaXeId && <p className="text-sm text-red-500">{errors.nhaXeId.message}</p>}</div>
              <div className="space-y-2 md:col-span-2"><Label htmlFor="taiXeText">Tài xế *</Label><Input id="taiXeText" list="taiXeList" placeholder="Nhập hoặc chọn tài xế..." value={taiXeText} onChange={(e) => { const val = e.target.value; setTaiXeText(val); const found = taiXes.find(tx => `${tx.hoTen} — ${tx.kinhNghiemNam} năm KN` === val); setValue('taiXeId', found ? String(found.id) : '', { shouldValidate: true }); }} /><datalist id="taiXeList">{taiXes.map(tx => <option key={tx.id} value={`${tx.hoTen} — ${tx.kinhNghiemNam} năm KN`} />)}</datalist><input type="hidden" {...register('taiXeId')} />{errors.taiXeId && <p className="text-sm text-red-500">{errors.taiXeId.message}</p>}</div>
              <div className="space-y-2 md:col-span-2"><Label htmlFor="hanKiemDinh">Hạn kiểm định *</Label><Input id="hanKiemDinh" type="date" {...register('hanKiemDinh')} className="max-w-[200px]" />{errors.hanKiemDinh && <p className="text-sm text-red-500">{errors.hanKiemDinh.message}</p>}</div>
            </div>
            {apiError && <p className="text-sm text-red-500 bg-red-50 px-3 py-2 rounded-lg">⚠️ {apiError}</p>}
            <div className="pt-6 flex justify-end gap-4 border-t border-slate-100">
              <Button type="button" variant="outline" onClick={() => navigate('/vehicles-schedules')} disabled={submitting}>Hủy bỏ</Button>
              <Button type="submit" disabled={submitting} className="bg-[#003366] hover:bg-[#003366]/90 flex gap-2"><Save size={18} /> {submitting ? 'Đang lưu...' : 'Lưu thông tin'}</Button>
            </div>
          </form>
        </CardContent>
      </Card>
    </div>
  );
}
