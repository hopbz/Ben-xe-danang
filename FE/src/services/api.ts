const BASE_URL = (import.meta.env.VITE_API_URL as string) || 'http://localhost:8080';

async function request<T>(path: string, options?: RequestInit): Promise<T> {
  const res = await fetch(`${BASE_URL}${path}`, {
    headers: { 'Content-Type': 'application/json' },
    ...options,
  });
  const json = await res.json();
  if (!res.ok) {
    throw new Error(json.message || `Lỗi server: ${res.status}`);
  }
  return json.data as T;
}

// ─── Interfaces ───────────────────────────────────────────────

export interface NhaXeResponse {
  id: number;
  maNhaXe: string;
  tenNhaXe: string;
  namThanhLap: number;
  diaChi: string;
  soDienThoai: string;
  soLuongXe: number;
}

export interface TaiXeResponse {
  id: number;
  maTaiXe: string;
  hoTen: string;
  soCccd: string;
  soDienThoai: string;
  kinhNghiemNam: number;
  soLuongXeDangPhuTrach: number;
}

export interface TuyenResponse {
  id: number;
  maTuyen: string;
  tenTuyen: string;
  diemDi: string;
  diemDen: string;
  khoangCachKm: number;
  donGia: number;
  soLuongChuyen: number;
}

export interface LoaiXeResponse {
  id: number;
  maLoaiXe: string;
  moTaLoaiXe: string;
  soLuongChoNgoi: number;
  soLuongXe: number;
}

export interface XeSummaryResponse {
  maXe: string;
  hangSanXuat: string;
  bienSo: string;
  hanKiemDinh: string;
  loaiXeId: number | null;
  maLoaiXe: string | null;
  moTaLoaiXe: string | null;
  soLuongChoNgoi: number | null;
  tenTaiXe: string | null;
  tenNhaXe: string | null;
  maTuyen: string | null;
  tenTuyen: string | null;
  ngayGioXuatBen: string | null;
  soLuongLichTrinh: number;
}

export interface XeDetailResponse {
  maXe: string;
  hangSanXuat: string;
  bienSo: string;
  hanKiemDinh: string;
  loaiXeId: number | null;
  maLoaiXe: string | null;
  moTaLoaiXe: string | null;
  soLuongChoNgoi: number | null;
  taiXeId: number | null;
  tenTaiXe: string | null;
  nhaXeId: number | null;
  tenNhaXe: string | null;
  lichTrinhs: LichTrinhResponse[];
}

export interface LichTrinhResponse {
  id: number;
  maXe: string;
  bienSo: string;
  tenTaiXe: string;
  tuyenId: number;
  maTuyen: string;
  tenTuyen: string;
  ngayGioXuatBen: string;
  donGia: number;
  soLuongHanhKhach: number;
  tongTien: number;
}

export interface ThongKeNhaXeResponse {
  nhaXeId: number;
  tenNhaXe: string;
  soLuongXe: number;
  soLuongChuyen: number;
  tongDoanhThu: number;
  tongSoHanhKhach: number;
}

// ─── NhaXe ────────────────────────────────────────────────────
export const getNhaXes = () => request<NhaXeResponse[]>('/api/nha-xes');
export const getNhaXe = (id: number) => request<NhaXeResponse>(`/api/nha-xes/${id}`);
export const createNhaXe = (body: { maNhaXe: string; tenNhaXe: string; namThanhLap: number; diaChi: string; soDienThoai: string }) =>
  request<NhaXeResponse>('/api/nha-xes', { method: 'POST', body: JSON.stringify(body) });
export const updateNhaXe = (id: number, body: { maNhaXe: string; tenNhaXe: string; namThanhLap: number; diaChi: string; soDienThoai: string }) =>
  request<NhaXeResponse>(`/api/nha-xes/${id}`, { method: 'PUT', body: JSON.stringify(body) });
export const deleteNhaXe = (id: number) => request<void>(`/api/nha-xes/${id}`, { method: 'DELETE' });

// ─── TaiXe ────────────────────────────────────────────────────
export const getTaiXes = () => request<TaiXeResponse[]>('/api/tai-xes');
export const createTaiXe = (body: { maTaiXe: string; hoTen: string; soCccd: string; soDienThoai: string; kinhNghiemNam: number }) =>
  request<TaiXeResponse>('/api/tai-xes', { method: 'POST', body: JSON.stringify(body) });
export const updateTaiXe = (id: number, body: { maTaiXe: string; hoTen: string; soCccd: string; soDienThoai: string; kinhNghiemNam: number }) =>
  request<TaiXeResponse>(`/api/tai-xes/${id}`, { method: 'PUT', body: JSON.stringify(body) });
export const deleteTaiXe = (id: number) => request<void>(`/api/tai-xes/${id}`, { method: 'DELETE' });

// ─── Tuyen ────────────────────────────────────────────────────
export const getTuyens = () => request<TuyenResponse[]>('/api/tuyens');
export const createTuyen = (body: { maTuyen: string; tenTuyen: string; diemDi: string; diemDen: string; khoangCachKm: number; donGia: number }) =>
  request<TuyenResponse>('/api/tuyens', { method: 'POST', body: JSON.stringify(body) });
export const updateTuyen = (id: number, body: { maTuyen: string; tenTuyen: string; diemDi: string; diemDen: string; khoangCachKm: number; donGia: number }) =>
  request<TuyenResponse>(`/api/tuyens/${id}`, { method: 'PUT', body: JSON.stringify(body) });
export const deleteTuyen = (id: number) => request<void>(`/api/tuyens/${id}`, { method: 'DELETE' });

// ─── LoaiXe ───────────────────────────────────────────────────
export const getLoaiXes = () => request<LoaiXeResponse[]>('/api/loai-xes');
export const createLoaiXe = (body: { maLoaiXe: string; moTaLoaiXe: string; soLuongChoNgoi: number }) =>
  request<LoaiXeResponse>('/api/loai-xes', { method: 'POST', body: JSON.stringify(body) });
export const updateLoaiXe = (id: number, body: { maLoaiXe: string; moTaLoaiXe: string; soLuongChoNgoi: number }) =>
  request<LoaiXeResponse>(`/api/loai-xes/${id}`, { method: 'PUT', body: JSON.stringify(body) });
export const deleteLoaiXe = (id: number) => request<void>(`/api/loai-xes/${id}`, { method: 'DELETE' });

// ─── Xe ───────────────────────────────────────────────────────
export const getXes = (tenNhaXe?: string) =>
  request<XeSummaryResponse[]>(`/api/xes${tenNhaXe ? `?tenNhaXe=${encodeURIComponent(tenNhaXe)}` : ''}`);
export const getXeDetail = (maXe: string) => request<XeDetailResponse>(`/api/xes/${encodeURIComponent(maXe)}`);
export const createXe = (body: { maXe: string; hangSanXuat: string; bienSo: string; hanKiemDinh: string; loaiXeId: number; taiXeId: number; nhaXeId: number }) =>
  request<XeDetailResponse>('/api/xes', { method: 'POST', body: JSON.stringify(body) });
export const createLichTrinh = (maXe: string, body: { tenTaiXe: string; tuyenId: number; ngayGioXuatBen: string; donGia: number; soLuongHanhKhach: number }) =>
  request<LichTrinhResponse>(`/api/xes/${encodeURIComponent(maXe)}/lich-trinhs`, { method: 'POST', body: JSON.stringify(body) });

// ─── Thống kê ─────────────────────────────────────────────────
export const getThongKeNhaXe = () => request<ThongKeNhaXeResponse[]>('/api/thong-ke/nha-xe-thu-nhap');
export const seedData = () => fetch(`${BASE_URL}/api/seed`, { method: 'POST' }).then(r => r.json());
