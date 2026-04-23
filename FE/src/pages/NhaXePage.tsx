import React, { useEffect, useState } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '../components/ui/card';
import { Button } from '../components/ui/button';
import { Input } from '../components/ui/input';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '../components/ui/table';
import { Pencil, RefreshCw, Trash2 } from 'lucide-react';
import { getNhaXes, createNhaXe, updateNhaXe, deleteNhaXe, type NhaXeResponse } from '../services/api';

const initialForm = { maNhaXe: '', tenNhaXe: '', namThanhLap: '', diaChi: '', soDienThoai: '' };

export default function NhaXePage() {
  const [items, setItems] = useState<NhaXeResponse[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [form, setForm] = useState(initialForm);
  const [editingId, setEditingId] = useState<number | null>(null);
  const [saving, setSaving] = useState(false);
  const [formError, setFormError] = useState('');

  const load = async () => {
    setLoading(true); setError('');
    try { setItems(await getNhaXes()); }
    catch (e: any) { setError(e.message || 'Lỗi tải dữ liệu'); }
    finally { setLoading(false); }
  };

  useEffect(() => { load(); }, []);

  const onSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setSaving(true); setFormError('');
    if (!form.maNhaXe.trim() || !form.tenNhaXe.trim() || form.namThanhLap === '' || !form.diaChi.trim() || !form.soDienThoai.trim()) {
      setFormError('Vui lòng điền đầy đủ các thông tin bắt buộc');
      setSaving(false);
      return;
    }

    try {
      const payload = { ...form, namThanhLap: Number(form.namThanhLap) };
      if (editingId) await updateNhaXe(editingId, payload);
      else await createNhaXe(payload);
      setForm(initialForm); setEditingId(null);
      await load();
    } catch (err: any) {
      setFormError(err.message || 'Lỗi không xác định');
    } finally { setSaving(false); }
  };

  const onEdit = (item: NhaXeResponse) => {
    setEditingId(item.id);
    setForm({ maNhaXe: item.maNhaXe, tenNhaXe: item.tenNhaXe, namThanhLap: String(item.namThanhLap ?? ''), diaChi: item.diaChi, soDienThoai: item.soDienThoai });
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  const onDelete = async (id: number) => {
    if (!confirm('Xóa nhà xe này?')) return;
    try { await deleteNhaXe(id); await load(); }
    catch (err: any) { setError(err.message || 'Lỗi khi xóa'); }
  };

  return (
    <div className="space-y-6">
      <Card>
        <CardHeader className="border-b border-[#E0E5F2] pb-4">
          <div className="flex items-center justify-between gap-4 flex-wrap">
            <CardTitle className="text-[#2B3674] text-[16px]">Quản lý Nhà xe</CardTitle>
            <Button variant="outline" onClick={load}><RefreshCw size={14} className="mr-2" />Tải lại</Button>
          </div>
        </CardHeader>
        <CardContent className="pt-6">
          <form onSubmit={onSubmit} className="grid grid-cols-1 md:grid-cols-3 lg:grid-cols-6 gap-3 mb-6">
            <Input value={form.maNhaXe} onChange={e => setForm({ ...form, maNhaXe: e.target.value })} placeholder="Mã nhà xe *" disabled={!!editingId} />
            <Input value={form.tenNhaXe} onChange={e => setForm({ ...form, tenNhaXe: e.target.value })} placeholder="Tên nhà xe *" />
            <Input type="number" value={form.namThanhLap} onChange={e => setForm({ ...form, namThanhLap: e.target.value })} placeholder="Năm thành lập *" />
            <Input value={form.diaChi} onChange={e => setForm({ ...form, diaChi: e.target.value })} placeholder="Địa chỉ *" />
            <Input value={form.soDienThoai} onChange={e => setForm({ ...form, soDienThoai: e.target.value })} placeholder="Số điện thoại *" />
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
                    <TableHead>Mã NX</TableHead><TableHead>Tên nhà xe</TableHead>
                    <TableHead>Năm TL</TableHead><TableHead>Địa chỉ</TableHead>
                    <TableHead>SĐT</TableHead><TableHead className="text-right">Số xe</TableHead>
                    <TableHead className="text-center">Thao tác</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {items.length === 0 ? (
                    <TableRow><TableCell colSpan={7} className="text-center py-8 text-slate-400">Chưa có nhà xe nào</TableCell></TableRow>
                  ) : items.map(item => (
                    <TableRow key={item.id}>
                      <TableCell className="font-mono font-bold text-[#003366]">{item.maNhaXe}</TableCell>
                      <TableCell className="font-medium">{item.tenNhaXe}</TableCell>
                      <TableCell>{item.namThanhLap}</TableCell>
                      <TableCell>{item.diaChi}</TableCell>
                      <TableCell>{item.soDienThoai}</TableCell>
                      <TableCell className="text-right">{item.soLuongXe}</TableCell>
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
