import React, { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import * as z from 'zod';
import { getTuyens, createLichTrinh, type TuyenResponse } from '../services/api';
import { Button } from '../components/ui/button';
import { Input } from '../components/ui/input';
import { Label } from '../components/ui/label';
import { X } from 'lucide-react';

const scheduleSchema = z.object({
  tenTaiXe: z.string().min(1, 'Vui lòng nhập tên tài xế'),
  tuyenId: z.string().min(1, 'Vui lòng chọn tuyến xe'),
  ngayXuatBen: z.string().min(1, 'Vui lòng chọn ngày xuất bến'),
  gioXuatBen: z.string().min(1, 'Vui lòng chọn giờ xuất bến'),
  donGia: z.coerce.number().min(1000, 'Đơn giá phải lớn hơn 0'),
  soLuongHanhKhach: z.coerce.number().min(1, 'Số hành khách phải lớn hơn 0').max(100),
});

type ScheduleFormValues = z.infer<typeof scheduleSchema>;

interface Props {
  maXe: string;
  onClose: () => void;
  onSuccess: () => void;
}

const formatCurrency = (v: number) => new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(v);

export default function AddScheduleModal({ maXe, onClose, onSuccess }: Props) {
  const [tuyens, setTuyens] = useState<TuyenResponse[]>([]);
  const [submitting, setSubmitting] = useState(false);
  const [apiError, setApiError] = useState('');

  const { register, handleSubmit, watch, formState: { errors } } = useForm<ScheduleFormValues>({
    resolver: zodResolver(scheduleSchema) as any,
    defaultValues: { soLuongHanhKhach: 1, donGia: 100000, tenTaiXe: '' },
  });

  useEffect(() => {
    getTuyens().then(setTuyens).catch(console.error);
  }, []);

  const onSubmit = async (data: ScheduleFormValues) => {
    setSubmitting(true);
    setApiError('');
    try {
      await createLichTrinh(maXe, {
        tenTaiXe: data.tenTaiXe,
        tuyenId: Number(data.tuyenId),
        ngayGioXuatBen: `${data.ngayXuatBen}T${data.gioXuatBen}:00`,
        donGia: data.donGia,
        soLuongHanhKhach: data.soLuongHanhKhach,
      });
      onSuccess();
      onClose();
    } catch (err: any) {
      setApiError(err.message || 'Lỗi không xác định');
    } finally {
      setSubmitting(false);
    }
  };

  const tongTien = watch('donGia') && watch('soLuongHanhKhach') ? Number(watch('donGia')) * Number(watch('soLuongHanhKhach')) : 0;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-slate-900/50 backdrop-blur-sm">
      <div className="bg-white rounded-xl shadow-xl w-full max-w-lg overflow-hidden">
        <div className="flex justify-between items-center p-6 border-b border-slate-100">
          <div>
            <h2 className="text-xl font-bold text-[#003366]">Thêm lịch trình mới</h2>
            <p className="text-[12px] text-[#A3AED0] mt-0.5">Xe: <span className="font-mono font-bold text-[#003366]">{maXe}</span></p>
          </div>
          <button onClick={onClose} className="text-slate-400 hover:text-slate-600 hover:bg-slate-100 p-2 rounded-full"><X size={20} /></button>
        </div>

        <form onSubmit={handleSubmit(onSubmit)} className="p-6 space-y-4">
          <div className="space-y-2">
            <Label>Tên tài xế *</Label>
            <Input {...register('tenTaiXe')} placeholder="VD: Nguyễn Văn An" />
            {errors.tenTaiXe && <p className="text-sm text-red-500">{errors.tenTaiXe.message}</p>}
          </div>
          <div className="space-y-2">
            <Label>Tuyến xe *</Label>
            <select {...register('tuyenId')} className="flex w-full rounded-[8px] border border-[#E0E5F2] bg-white px-[12px] py-[10px] text-[13px] text-[#2B3674] focus:outline-none focus:ring-1 focus:ring-[#003366]">
              <option value="">-- Chọn tuyến xe --</option>
              {tuyens.map(t => <option key={t.id} value={t.id}>{t.tenTuyen} ({t.khoangCachKm} km)</option>)}
            </select>
            {errors.tuyenId && <p className="text-sm text-red-500">{errors.tuyenId.message}</p>}
          </div>
          <div className="grid grid-cols-2 gap-4">
            <div className="space-y-2"><Label>Ngày xuất bến *</Label><Input type="date" {...register('ngayXuatBen')} />{errors.ngayXuatBen && <p className="text-sm text-red-500">{errors.ngayXuatBen.message}</p>}</div>
            <div className="space-y-2"><Label>Giờ xuất bến *</Label><Input type="time" {...register('gioXuatBen')} />{errors.gioXuatBen && <p className="text-sm text-red-500">{errors.gioXuatBen.message}</p>}</div>
          </div>
          <div className="grid grid-cols-2 gap-4">
            <div className="space-y-2"><Label>Đơn giá (VNĐ) *</Label><Input type="number" step="1000" {...register('donGia')} placeholder="VD: 250000" />{errors.donGia && <p className="text-sm text-red-500">{errors.donGia.message}</p>}</div>
            <div className="space-y-2"><Label>Số hành khách *</Label><Input type="number" min={1} max={100} {...register('soLuongHanhKhach')} />{errors.soLuongHanhKhach && <p className="text-sm text-red-500">{errors.soLuongHanhKhach.message}</p>}</div>
          </div>
          {tongTien > 0 && <div className="bg-[#F4F7FE] rounded-lg px-4 py-3 flex justify-between items-center"><span className="text-[13px] text-[#A3AED0]">Tổng doanh thu dự kiến:</span><span className="font-bold text-[#003366]">{formatCurrency(tongTien)}</span></div>}
          {apiError && <p className="text-sm text-red-500 bg-red-50 px-3 py-2 rounded-lg">⚠️ {apiError}</p>}
          <div className="pt-4 flex justify-end gap-3 border-t border-slate-100">
            <Button type="button" variant="outline" onClick={onClose} disabled={submitting}>Hủy bỏ</Button>
            <Button type="submit" disabled={submitting} className="bg-[#003366] hover:bg-[#003366]/90 text-white">{submitting ? 'Đang lưu...' : 'Lưu lịch trình'}</Button>
          </div>
        </form>
      </div>
    </div>
  );
}
