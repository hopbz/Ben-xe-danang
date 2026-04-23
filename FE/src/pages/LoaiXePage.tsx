import React, { useEffect, useState } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '../components/ui/card';
import { Button } from '../components/ui/button';
import { Input } from '../components/ui/input';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '../components/ui/table';
import { Pencil, RefreshCw, Trash2 } from 'lucide-react';
import { getLoaiXes, createLoaiXe, updateLoaiXe, deleteLoaiXe, type LoaiXeResponse } from '../services/api';

const initialForm = { maLoaiXe: '', moTaLoaiXe: '', soLuongChoNgoi: '' };

export default function LoaiXePage() {
  const [items, setItems] = useState<LoaiXeResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [form, setForm] = useState(initialForm);
  const [editingId, setEditingId] = useState<number | null>(null);
  const [saving, setSaving] = useState(false);
  const [formError, setFormError] = useState('');

  const load = async () => {
    setLoading(true);
    setError('');
    try { setItems(await getLoaiXes()); } catch (e: any) { setError(e.message || 'Lỗi tải dữ liệu'); } finally { setLoading(false); }
  };

  useEffect(() => { load(); }, []);

  const onSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setSaving(true);
    setFormError('');
    try {
      if (!form.maLoaiXe.trim() || !form.moTaLoaiXe.trim() || form.soLuongChoNgoi === '') {
        setFormError('Vui lòng điền đầy đủ các thông tin bắt buộc');
        setSaving(false);
        return;
      }


      const payload = { ...form, soLuongChoNgoi: Number(form.soLuongChoNgoi) };
      if (editingId) await updateLoaiXe(editingId, payload); else await createLoaiXe(payload);
      setForm(initialForm);
      setEditingId(null);
      await load();
    } catch (err: any) {
      setFormError(err.message || 'Lỗi không xác định');
    } finally { setSaving(false); }
  };

  const onEdit = (item: LoaiXeResponse) => {
    setEditingId(item.id);
    setForm({ maLoaiXe: item.maLoaiXe, moTaLoaiXe: item.moTaLoaiXe, soLuongChoNgoi: String(item.soLuongChoNgoi) });
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  const onDelete = async (id: number) => {
    if (!confirm('Xóa loại xe này?')) return;
    await deleteLoaiXe(id);
    await load();
  };

  return (
    <div className="space-y-6">
      <Card>
        <CardHeader className="border-b border-[#E0E5F2] pb-4">
          <div className="flex items-center justify-between gap-4 flex-wrap">
            <CardTitle className="text-[#2B3674] text-[16px]">Quản lý Loại xe</CardTitle>
            <Button variant="outline" onClick={load}><RefreshCw size={14} className="mr-2" /> Tải lại</Button>
          </div>
        </CardHeader>
        <CardContent className="pt-6">
          <form onSubmit={onSubmit} className="grid grid-cols-1 md:grid-cols-4 gap-4 mb-6">
            <Input value={form.maLoaiXe} onChange={e => setForm({ ...form, maLoaiXe: e.target.value })} placeholder="Mã loại xe" />
            <Input value={form.moTaLoaiXe} onChange={e => setForm({ ...form, moTaLoaiXe: e.target.value })} placeholder="Mô tả loại xe" />
            <Input type="number" value={form.soLuongChoNgoi} onChange={e => setForm({ ...form, soLuongChoNgoi: e.target.value })} placeholder="Số chỗ ngồi" />
            <div className="flex gap-2">
              <Button type="submit" disabled={saving}>{editingId ? 'Cập nhật' : 'Thêm mới'}</Button>
              {editingId && <Button type="button" variant="outline" onClick={() => { setEditingId(null); setForm(initialForm); }}>Hủy</Button>}
            </div>
          </form>
          {formError && <p className="text-sm text-red-500 mb-4">⚠️ {formError}</p>}
          {error && <p className="text-sm text-red-500 mb-4">⚠️ {error}</p>}
          {loading ? <p className="py-8 text-center text-slate-500">Đang tải...</p> : (
            <Table>
              <TableHeader>
                <TableRow><TableHead>Mã loại xe</TableHead><TableHead>Mô tả</TableHead><TableHead className="text-right">Số chỗ</TableHead><TableHead className="text-right">Số xe</TableHead><TableHead className="text-center">Thao tác</TableHead></TableRow>
              </TableHeader>
              <TableBody>
                {items.map(item => (
                  <TableRow key={item.id}>
                    <TableCell className="font-medium">{item.maLoaiXe}</TableCell>
                    <TableCell>{item.moTaLoaiXe}</TableCell>
                    <TableCell className="text-right">{item.soLuongChoNgoi}</TableCell>
                    <TableCell className="text-right">{item.soLuongXe}</TableCell>
                    <TableCell className="text-center"><div className="inline-flex gap-2"><Button variant="outline" type="button" onClick={() => onEdit(item)}><Pencil size={14} className="mr-1" /> Sửa</Button><Button variant="destructive" type="button" onClick={() => onDelete(item.id)}><Trash2 size={14} className="mr-1" /> Xóa</Button></div></TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          )}
        </CardContent>
      </Card>
    </div>
  );
}