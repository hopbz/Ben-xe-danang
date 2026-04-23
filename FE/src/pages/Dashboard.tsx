import React, { useEffect, useState } from 'react';
import { getThongKeNhaXe, getXes, type ThongKeNhaXeResponse } from '../services/api';
import { Card, CardContent, CardHeader, CardTitle } from '../components/ui/card';
import {
  PieChart, Pie, Cell, ResponsiveContainer,
  Tooltip as RechartsTooltip, Legend,
  BarChart, Bar, XAxis, YAxis, CartesianGrid,
} from 'recharts';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '../components/ui/table';

const COLORS = ['#003366', '#FF6600', '#2dd4bf', '#fbbf24', '#f87171'];

const formatCurrency = (amount: number) =>
  new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount);

export default function Dashboard() {
  const [stats, setStats] = useState<ThongKeNhaXeResponse[]>([]);
  const [totalXe, setTotalXe] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    Promise.all([getThongKeNhaXe(), getXes()])
      .then(([thongKe, xes]) => {
        setStats(thongKe);
        setTotalXe(xes.length);
      })
      .catch(err => setError(err.message))
      .finally(() => setLoading(false));
  }, []);

  const totalRevenue = stats.reduce((s, r) => s + Number(r.tongDoanhThu), 0);
  const totalPassengers = stats.reduce((s, r) => s + r.tongSoHanhKhach, 0);
  const totalTrips = stats.reduce((s, r) => s + r.soLuongChuyen, 0);

  const chartData = stats.map(r => ({
    companyName: r.tenNhaXe,
    totalRevenue: Number(r.tongDoanhThu),
    totalPassengers: r.tongSoHanhKhach,
    trips: r.soLuongChuyen,
  }));

  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <p className="text-[#A3AED0] text-sm animate-pulse">Đang tải dữ liệu...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex items-center justify-center h-64">
        <p className="text-red-500 text-sm">⚠️ Lỗi kết nối BE: {error}</p>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      {/* KPI Cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-[20px]">
        <div className="bg-white px-[20px] py-[16px] rounded-[16px] shadow-[0px_4px_12px_rgba(0,0,0,0.06)]">
          <p className="text-[#A3AED0] text-[12px] font-semibold uppercase">Tổng Doanh Thu</p>
          <p className="text-[20px] font-[800] mt-[4px] text-[#003366]">{formatCurrency(totalRevenue)}</p>
        </div>
        <div className="bg-white px-[20px] py-[16px] rounded-[16px] shadow-[0px_4px_12px_rgba(0,0,0,0.06)]">
          <p className="text-[#A3AED0] text-[12px] font-semibold uppercase">Tổng Lượt Khách</p>
          <p className="text-[20px] font-[800] mt-[4px] text-[#003366]">{totalPassengers.toLocaleString('vi-VN')}</p>
        </div>
        <div className="bg-white px-[20px] py-[16px] rounded-[16px] shadow-[0px_4px_12px_rgba(0,0,0,0.06)]">
          <p className="text-[#A3AED0] text-[12px] font-semibold uppercase">Tổng Chuyến Đi</p>
          <p className="text-[20px] font-[800] mt-[4px] text-[#003366]">{totalTrips} lượt</p>
        </div>
        <div className="bg-white px-[20px] py-[16px] rounded-[16px] shadow-[0px_4px_12px_rgba(0,0,0,0.06)]">
          <p className="text-[#A3AED0] text-[12px] font-semibold uppercase">Tổng Số Xe</p>
          <p className="text-[20px] font-[800] mt-[4px] text-[#003366]">{totalXe} xe</p>
        </div>
      </div>

      {/* Charts */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <Card>
          <CardHeader>
            <CardTitle className="text-[#003366]">Biểu đồ Doanh thu (VND)</CardTitle>
          </CardHeader>
          <CardContent className="h-[300px]">
            {chartData.length === 0 ? (
              <p className="text-[#A3AED0] text-sm text-center pt-10">Chưa có dữ liệu lịch trình</p>
            ) : (
              <ResponsiveContainer width="100%" height="100%">
                <BarChart data={chartData} margin={{ top: 20, right: 30, left: 20, bottom: 5 }}>
                  <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#e2e8f0" />
                  <XAxis dataKey="companyName" tick={{ fill: '#64748b', fontSize: 11 }} axisLine={false} tickLine={false} />
                  <YAxis
                    width={80}
                    tickFormatter={v => `${(v / 1_000_000).toFixed(0)}M`}
                    tick={{ fill: '#64748b', fontSize: 11 }}
                    axisLine={false}
                    tickLine={false}
                  />
                  <RechartsTooltip
                    formatter={(value: number) => formatCurrency(value)}
                    cursor={{ fill: '#f1f5f9' }}
                    contentStyle={{ borderRadius: '8px', border: 'none', boxShadow: '0 4px 6px -1px rgb(0 0 0 / 0.1)' }}
                  />
                  <Bar dataKey="totalRevenue" fill="#003366" radius={[4, 4, 0, 0]} barSize={40} />
                </BarChart>
              </ResponsiveContainer>
            )}
          </CardContent>
        </Card>

        <Card>
          <CardHeader>
            <CardTitle className="text-[#003366]">Tỷ trọng Doanh thu</CardTitle>
          </CardHeader>
          <CardContent className="h-[300px]">
            {chartData.filter(c => c.totalRevenue > 0).length === 0 ? (
              <p className="text-[#A3AED0] text-sm text-center pt-10">Chưa có dữ liệu lịch trình</p>
            ) : (
              <ResponsiveContainer width="100%" height="100%">
                <PieChart>
                  <Pie
                    data={chartData.filter(c => c.totalRevenue > 0)}
                    cx="50%"
                    cy="50%"
                    innerRadius={60}
                    outerRadius={100}
                    paddingAngle={5}
                    dataKey="totalRevenue"
                    nameKey="companyName"
                  >
                    {chartData.map((_, index) => (
                      <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                    ))}
                  </Pie>
                  <RechartsTooltip formatter={(value: number) => formatCurrency(value)} />
                  <Legend layout="horizontal" verticalAlign="bottom" align="center" wrapperStyle={{ fontSize: '12px' }} />
                </PieChart>
              </ResponsiveContainer>
            )}
          </CardContent>
        </Card>
      </div>

      {/* Table */}
      <Card>
        <CardHeader className="border-b border-[#E0E5F2] pb-[16px]">
          <CardTitle className="text-[#2B3674] text-[16px]">Chi tiết Doanh thu Nhà xe</CardTitle>
        </CardHeader>
        <CardContent className="pt-4">
          <div className="overflow-x-auto">
            <Table>
              <TableHeader>
                <TableRow className="border-b border-[#E0E5F2]">
                  <TableHead>Tên Nhà Xe</TableHead>
                  <TableHead className="text-right">Số Xe</TableHead>
                  <TableHead className="text-right">Số Chuyến</TableHead>
                  <TableHead className="text-right">Lượt Khách</TableHead>
                  <TableHead className="text-right">Tổng Doanh Thu</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {stats.length === 0 ? (
                  <TableRow>
                    <TableCell colSpan={5} className="text-center py-8 text-[#A3AED0]">
                      Chưa có dữ liệu
                    </TableCell>
                  </TableRow>
                ) : (
                  stats.map((item, i) => (
                    <TableRow key={i}>
                      <TableCell className="font-medium text-[#2B3674]">{item.tenNhaXe}</TableCell>
                      <TableCell className="text-right">{item.soLuongXe}</TableCell>
                      <TableCell className="text-right">{item.soLuongChuyen}</TableCell>
                      <TableCell className="text-right">{item.tongSoHanhKhach.toLocaleString('vi-VN')}</TableCell>
                      <TableCell className="text-right font-bold text-[#003366]">
                        {formatCurrency(Number(item.tongDoanhThu))}
                      </TableCell>
                    </TableRow>
                  ))
                )}
              </TableBody>
            </Table>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
