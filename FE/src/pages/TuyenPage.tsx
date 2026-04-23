import React, { useEffect, useState } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '../components/ui/card';
import { Button } from '../components/ui/button';
import { Input } from '../components/ui/input';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '../components/ui/table';
import { Pencil, RefreshCw, Trash2 } from 'lucide-react';
import { getTuyens, createTuyen, updateTuyen, deleteTuyen, type TuyenResponse } from '../services/api';

const initialForm = { maTuyen: '', tenTuyen: '', diemDi: '', diemDen: '', khoangCachKm: '', donGia: '' };
const fmt = (n: number) => new Intl.NumberFormat('vi-VN').format(n);

export default function TuyenPage() {
  const [items, setItems] = useState<TuyenResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [form, setForm] = useState(initialForm);
  const [editingId, setEditingId] = useState<number | null>(null);
  const [saving, setSaving] = useState(false);
  const [formError, setFormError] = useState('');

  const load = async () => {
    setLoading(true); setError('');
    try { setItems(await getTuyens()); }
    catch (e: any) { setError(e.message || 'Lỗi tải dữ liệu'); }
    finally { setLoading(false); }
  };

  useEffect(() => { load(); }, []);

  const onSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setSaving(true); setFormError('');
    try {
      if (!form.maTuyen.trim() || !form.tenTuyen.trim() || !form.diemDi.trim() || !form.diemDen.trim() || form.khoangCachKm === '' || form.donGia === '') {
        setFormError('Vui lòng điền đầy đủ các thông tin bắt buộc');
        setSaving(false);
        return;
      }


      const payload = { ...form, khoangCachKm: Number(form.khoangCachKm), donGia: Number(form.donGia) };
      if (editingId) await updateTuyen(editingId, payload);
      else await createTuyen(payload);
      setForm(initialForm); setEditingId(null);
      await load();
    } catch (err: any) {
      setFormError(err.message || 'Lỗi không xác định');
    } finally { setSaving(false); }
  };

  const onEdit = (item: TuyenResponse) => {
    setEditingId(item.id);
    setForm({ maTuyen: item.maTuyen, tenTuyen: item.tenTuyen, diemDi: item.diemDi, diemDen: item.diemDen, khoangCachKm: String(item.khoangCachKm), donGia: String(item.donGia) });
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  const onDelete = async (id: number) => {
    if (!confirm('Xóa tuyến này?')) return;
    try { await deleteTuyen(id); await load(); }
    catch (err: any) { setError(err.message || 'Lỗi khi xóa'); }
  };

  return (
    <div className="space-y-6">
      <Card>
        <CardHeader className="border-b border-[#E0E5F2] pb-4">
          <div className="flex items-center justify-between gap-4 flex-wrap">
            <CardTitle className="text-[#2B3674] text-[16px]">Quản lý Tuyến xe</CardTitle>
            <Button variant="outline" onClick={load}><RefreshCw size={14} className="mr-2" />Tải lại</Button>
          </div>
        </CardHeader>
        <CardContent className="pt-6">
          <form onSubmit={onSubmit} className="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-7 gap-3 mb-6">
            <Input value={form.maTuyen} onChange={e => setForm({ ...form, maTuyen: e.target.value })} placeholder="Mã tuyến *" disabled={!!editingId} />
            <Input value={form.tenTuyen} onChange={e => setForm({ ...form, tenTuyen: e.target.value })} placeholder="Tên tuyến *" />
            <Input value={form.diemDi} onChange={e => setForm({ ...form, diemDi: e.target.value })} placeholder="Điểm đi *" />
            <Input value={form.diemDen} onChange={e => setForm({ ...form, diemDen: e.target.value })} placeholder="Điểm đến *" />
            <Input type="number" value={form.khoangCachKm} onChange={e => setForm({ ...form, khoangCachKm: e.target.value })} placeholder="Km *" />
            <Input type="number" step="1000" value={form.donGia} onChange={e => setForm({ ...form, donGia: e.target.value })} placeholder="Đơn giá (VNĐ) *" />
            <div className="flex gap-2">
              <Button type="submit" disabled={saving}>{editingId ? 'Cập nhật' : 'Thêm mới'}</Button>
              {editingId && <Button type="button" variant="outline" onClick={() => { setEditingId(null); setForm(initialForm); }}>Hủy</Button>}
            </div>
          </form>
          {formError && <p className="text-sm text-red-500 mb-4">⚠️ {formError}</p>}
          {error && <p className="text-sm text-red-500 mb-4">⚠️ {error}</p>}
          {loading ? <p className="py-8 text-center text-slate-500">Đang tải...</p> : (
            <div className="overflow-x-auto">
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>Mã tuyến</TableHead><TableHead>Tên tuyến</TableHead>
                    <TableHead>Điểm đi</TableHead><TableHead>Điểm đến</TableHead>
                    <TableHead className="text-right">Km</TableHead>
                    <TableHead className="text-right">Đơn giá</TableHead>
                    <TableHead className="text-right">Số chuyến</TableHead>
                    <TableHead className="text-center">Thao tác</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {items.length === 0 ? (
                    <TableRow><TableCell colSpan={8} className="text-center py-8 text-slate-400">Chưa có tuyến nào</TableCell></TableRow>
                  ) : items.map(item => (
                    <TableRow key={item.id}>
                      <TableCell className="font-mono font-bold text-[#003366]">{item.maTuyen}</TableCell>
                      <TableCell>{item.tenTuyen}</TableCell>
                      <TableCell>{item.diemDi}</TableCell>
                      <TableCell>{item.diemDen}</TableCell>
                      <TableCell className="text-right">{item.khoangCachKm}</TableCell>
                      <TableCell className="text-right">{fmt(item.donGia)}đ</TableCell>
                      <TableCell className="text-right">{item.soLuongChuyen}</TableCell>
                      <TableCell className="text-center">
                        <div className="inline-flex gap-2">
                          <Button variant="outline" size="sm" type="button" onClick={() => onEdit(item)}><Pencil size={14} className="mr-1" />Sửa</Button>
                          <Button variant="destructive" size="sm" type="button" onClick={() => onDelete(item.id)}><Trash2 size={14} className="mr-1" />Xóa</Button>
                        </div>
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </div>
          )}
        </CardContent>
      </Card>
    </div>
  );
}
