import { Outlet, Link, useLocation } from 'react-router-dom';
import { Bus, LayoutDashboard, CalendarDays, PlusCircle, Menu, Bell, ChevronLeft, Search, Building2, Route, Ticket, UserCheck } from 'lucide-react';
import { cn } from '../lib/utils';
import React, { useState } from 'react';

const navItems = [
  { name: 'Báo cáo Doanh thu',      path: '/',                   icon: <LayoutDashboard size={20} /> },
  { name: 'Quản lý Xe & Lịch trình', path: '/vehicles-schedules', icon: <CalendarDays size={20} /> },
  { name: 'Thêm mới Xe',             path: '/add-vehicle',        icon: <PlusCircle size={20} /> },
  { name: 'Quản lý Nhà xe',          path: '/nha-xe',             icon: <Building2 size={20} /> },
  { name: 'Quản lý Tài xế',          path: '/tai-xe',             icon: <UserCheck size={20} /> },
  { name: 'Quản lý Tuyến xe',        path: '/tuyen',              icon: <Route size={20} /> },
  { name: 'Quản lý Loại xe',         path: '/loai-xe',            icon: <Ticket size={20} /> },
];

export default function AdminLayout() {
  const location = useLocation();
  const [collapsed, setCollapsed] = useState(false);

  return (
    <div className="flex h-screen bg-[#F4F7FE] overflow-hidden">
      {/* Sidebar */}
      <aside className={cn(
        'flex flex-col bg-[#003366] text-white transition-all duration-300 shrink-0',
        collapsed ? 'w-[64px]' : 'w-[240px]'
      )}>
        {/* Logo */}
        <div className="flex items-center gap-3 px-4 py-5 border-b border-white/10">
          <Bus size={28} className="text-[#F27D26] shrink-0" />
          {!collapsed && (
            <div>
              <p className="text-[13px] font-bold leading-tight">Bến Xe Đà Nẵng</p>
              <p className="text-[10px] text-white/50">Quản lý hệ thống</p>
            </div>
          )}
        </div>

        {/* Nav */}
        <nav className="flex-1 py-4 overflow-y-auto">
          {navItems.map(item => {
            const isActive = item.path === '/' ? location.pathname === '/' : location.pathname.startsWith(item.path);
            return (
              <Link
                key={item.path}
                to={item.path}
                title={collapsed ? item.name : undefined}
                className={cn(
                  'flex items-center gap-3 px-4 py-3 transition-all duration-150 text-[13px]',
                  collapsed ? 'justify-center' : '',
                  isActive
                    ? 'bg-white/10 border-r-4 border-[#F27D26] text-white'
                    : 'text-white/60 hover:text-white hover:bg-white/5'
                )}
              >
                <span className="shrink-0">{item.icon}</span>
                {!collapsed && <span>{item.name}</span>}
              </Link>
            );
          })}
        </nav>

        {/* Collapse toggle */}
        <button
          onClick={() => setCollapsed(!collapsed)}
          className="flex items-center justify-center gap-2 py-4 text-white/40 hover:text-white border-t border-white/10 text-[12px]"
        >
          <ChevronLeft size={16} className={cn('transition-transform', collapsed ? 'rotate-180' : '')} />
          {!collapsed && 'Thu gọn'}
        </button>
      </aside>

      {/* Main */}
      <div className="flex flex-col flex-1 overflow-hidden">
        {/* Header */}
        <header className="flex items-center justify-between bg-white px-6 py-3 shadow-sm shrink-0">
          <div className="flex items-center gap-3">
            <button onClick={() => setCollapsed(!collapsed)} className="text-[#A3AED0] hover:text-[#003366]">
              <Menu size={20} />
            </button>
            <div className="relative hidden md:block">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 text-[#A3AED0]" size={15} />
              <input
                type="text"
                placeholder="Tìm kiếm..."
                className="w-[220px] h-[36px] bg-[#F4F7FE] rounded-[20px] pl-9 pr-4 text-[13px] text-[#2B3674] focus:outline-none placeholder:text-[#A3AED0]"
              />
            </div>
          </div>
          <div className="flex items-center gap-4">
            <button className="relative text-[#A3AED0] hover:text-[#003366]">
              <Bell size={20} />
              <span className="absolute -top-1 -right-1 w-4 h-4 bg-[#F27D26] text-white text-[9px] rounded-full flex items-center justify-center">3</span>
            </button>
            <div className="flex items-center gap-2">
              <div className="w-8 h-8 rounded-full bg-[#003366] flex items-center justify-center text-white text-[12px] font-bold">AD</div>
              <div className="hidden sm:block">
                <p className="text-[13px] font-semibold text-[#2B3674]">Admin</p>
                <p className="text-[11px] text-[#A3AED0]">Bến xe Đà Nẵng</p>
              </div>
            </div>
          </div>
        </header>

        {/* Content */}
        <main className="flex-1 overflow-y-auto p-6">
          <Outlet />
        </main>
      </div>
    </div>
  );
}
