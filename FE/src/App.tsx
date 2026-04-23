import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import AdminLayout from './layouts/AdminLayout';
import Dashboard from './pages/Dashboard';
import VehicleList from './pages/VehicleList';
import AddVehicle from './pages/AddVehicle';
import NhaXePage from './pages/NhaXePage';
import TuyenPage from './pages/TuyenPage';
import LoaiXePage from './pages/LoaiXePage';
import TaiXePage from './pages/TaiXePage';
import XePage from './pages/XePage';

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<AdminLayout />}>
          <Route index element={<Dashboard />} />
          <Route path="vehicles-schedules" element={<VehicleList />} />
          <Route path="add-vehicle" element={<AddVehicle />} />
          <Route path="nha-xe" element={<NhaXePage />} />
          <Route path="tai-xe" element={<TaiXePage />} />
          <Route path="tuyen" element={<TuyenPage />} />
          <Route path="loai-xe" element={<LoaiXePage />} />
          <Route path="xe" element={<XePage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
