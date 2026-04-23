import React, { useState, useEffect, useCallback } from 'react';
import { getXes, type XeSummaryResponse } from '../services/api';
import { Card, CardContent, CardHeader, CardTitle } from '../components/ui/card';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '../components/ui/table';
import { Search, Plus, RefreshCw } from 'lucide-react';
import AddScheduleModal from './AddScheduleModal';

export default function VehicleList() {
  const [xeList, setXeList] = useState<XeSummaryResponse[]>([]);
  const [search, setSearch] = useState('');
  const [selectedMaXe, setSelectedMaXe] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const fetchData = useCallback(() => {
    setLoading(true);
    setError('');
    getXes(search.trim() || undefined)
      .then(setXeList)
      .catch(err => setError(err.message))
      .finally(() => setLoading(false));
  }, [search]);

  useEffect(() => {
    const timer = setTimeout(fetchData, 400);
    return () => clearTimeout(timer);
  }, [fetchData]);

  const formatDate = (dateStr: string | null) => dateStr ? new Date(dateStr).toLocaleDateString('vi-VN') : '—';
  const formatDateTime = (dateStr: string | null) => dateStr ? new Date(dateStr).toLocaleString('vi-VN') : '—';
  const isExpiringSoon = (dateStr: string) => new Date(dateStr).getTime() - Date.now() < 30 * 24 * 60 * 60 * 1000;

  return (
    <div className="space-y-6">
      <Card>
        <CardHeader className="pb-4">
          <div className="flex flex-col sm:flex-row justify-between items-center gap-4 border-b border-[#E0E5F2] pb-[16px]">
            <CardTitle className="text-[#2B3674] text-[16px]">Danh sách Xe và Lịch trình</CardTitle>
            <div className="flex items-center gap-3 w-full sm:w-auto">
              <div className="relative w-full sm:w-[250px]">
                <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-[#A3AED0]" size={16} />
                <input type="text" placeholder="Tìm kiếm nhà xe..." value={search} onChange={e => setSearch(e.target.value)} className="w-full h-[36px] bg-[#F4F7FE] border-none rounded-[20px] pl-10 pr-4 text-[13px] text-[#2B3674] focus:outline-none focus:ring-1 focus:ring-[#003366]/20 placeholder:text-[#A3AED0]" />
              </div>
              <button onClick={fetchData} className="flex items-center gap-1 h-[36px] px-3 rounded-[20px] bg-[#F4F7FE] text-[#A3AED0] hover:text-[#003366] text-[13px]" title="Làm mới"><RefreshCw size={14} className={loading ? 'animate-spin' : ''} /></button>
            </div>
          </div>
        </CardHeader>

        <CardContent>
          {error && <div className="text-red-500 text-sm py-4 text-center">⚠️ {error}</div>}
          {loading && !error && <div className="text-[#A3AED0] text-sm py-8 text-center animate-pulse">Đang tải...</div>}

          {!loading && !error && (
            <div className="overflow-x-auto">
              <Table>
                <TableHeader>
                  <TableRow className="border-b border-[#E0E5F2]">
                    <TableHead>Mã Xe</TableHead>
                    <TableHead>Hãng SX</TableHead>
                    <TableHead>Biển Số</TableHead>
                    <TableHead>Loại Xe</TableHead>
                    <TableHead>Tài Xế</TableHead>
                    <TableHead>Nhà Xe</TableHead>
                    <TableHead>Tuyến Gần Nhất</TableHead>
                    <TableHead>Giờ Xuất Bến</TableHead>
                    <TableHead>Hạn KĐ</TableHead>
                    <TableHead className="text-right">Số Lịch Trình</TableHead>
                    <TableHead className="text-center">Thao Tác</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {xeList.length === 0 ? (
                    <TableRow><TableCell colSpan={11} className="text-center py-10 text-[#A3AED0]">{search ? 'Không tìm thấy xe phù hợp' : 'Chưa có xe nào trong hệ thống'}</TableCell></TableRow>
                  ) : xeList.map(xe => (
                    <TableRow key={xe.maXe} className="hover:bg-[#F4F7FE]/50">
                      <TableCell className="font-mono font-bold text-[#003366]">{xe.maXe}</TableCell>
                      <TableCell>{xe.hangSanXuat}</TableCell>
                      <TableCell className="font-medium">{xe.bienSo}</TableCell>
                      <TableCell>{xe.maLoaiXe ? `${xe.maLoaiXe} (${xe.soLuongChoNgoi ?? '—'} chỗ)` : '—'}</TableCell>
                      <TableCell>{xe.tenTaiXe ?? '—'}</TableCell>
                      <TableCell>{xe.tenNhaXe ?? '—'}</TableCell>
                      <TableCell className="text-[13px]">{xe.tenTuyen ?? '—'}</TableCell>
                      <TableCell className="text-[13px]">{formatDateTime(xe.ngayGioXuatBen)}</TableCell>
                      <TableCell><span className={`text-[12px] font-medium px-2 py-0.5 rounded-full ${isExpiringSoon(xe.hanKiemDinh) ? 'bg-red-100 text-red-600' : 'bg-green-100 text-green-700'}`}>{formatDate(xe.hanKiemDinh)}</span></TableCell>
                      <TableCell className="text-right">{xe.soLuongLichTrinh}</TableCell>
                      <TableCell className="text-center"><button onClick={() => setSelectedMaXe(xe.maXe)} className="inline-flex items-center gap-1 text-[12px] bg-[#003366] text-white px-3 py-1.5 rounded-lg hover:bg-[#003366]/90"><Plus size={12} /> Lịch trình</button></TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </div>
          )}
        </CardContent>
      </Card>

      {selectedMaXe && <AddScheduleModal maXe={selectedMaXe} onClose={() => setSelectedMaXe(null)} onSuccess={fetchData} />}
    </div>
  );
}
