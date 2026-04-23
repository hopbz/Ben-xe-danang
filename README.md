# 🚌 Bến Xe Đà Nẵng — Hệ thống Quản lý Xe Khách

Ứng dụng web quản lý bến xe Đà Nẵng, xây dựng theo mô hình **MVC** với Spring Boot và React.

---

## 🏗️ Công nghệ sử dụng

| Thành phần | Công nghệ |
|---|---|
| **Backend** | Java 17, Spring Boot 3.3, Spring MVC, Spring Data JPA (Hibernate), Flyway, Bean Validation |
| **Frontend** | React 19, TypeScript, Vite, Tailwind CSS, React Hook Form, Zod, Recharts |
| **Database** | PostgreSQL 16 |
| **Container** | Docker, Docker Compose |

---

## 📋 Chức năng hệ thống

### Chức năng theo đề bài

| # | Chức năng | Mô tả |
|---|---|---|
| 1 | **Nhập xe** | Thêm xe mới với validate biển số (client) và hạn kiểm định (server) |
| 2 | **Danh sách xe & lịch trình** | Liệt kê xe, tìm kiếm theo tên nhà xe (LIKE), xem lịch trình |
| 3 | **Nhập lịch trình** | Thêm lịch trình cho xe, liên kết màn hình 2 ↔ 3 |
| 4 | **Thống kê doanh thu** | Bảng tổng thu nhập theo từng nhà xe |

### Quản lý danh mục

- **Nhà xe** — CRUD với mã nhà xe, tên, năm thành lập, địa chỉ, SĐT
- **Tài xế** — CRUD với mã tài xế, họ tên, CCCD, SĐT, kinh nghiệm
- **Tuyến xe** — CRUD với mã tuyến, tên, điểm đi, điểm đến, km, đơn giá
- **Loại xe** — CRUD với mã loại, mô tả, số chỗ ngồi

---

## 🗂️ Cấu trúc dự án

```
ben-xe-danang/
├── docker-compose.yml          # Chạy toàn bộ hệ thống (DB + BE + FE)
├── .env                        # Biến môi trường (DB credentials)
├── .gitignore
├── README.md
│
├── BE/                         # Spring Boot backend
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/main/java/com/quanlyxe/
│       ├── config/             # CORS config
│       ├── controller/         # REST controllers
│       ├── dto/
│       │   ├── request/        # Request DTOs
│       │   └── response/       # Response DTOs
│       ├── entity/             # JPA entities
│       ├── exception/          # Custom exceptions & handler
│       ├── repository/         # Spring Data JPA repositories
│       ├── service/            # Service interfaces
│       │   └── impl/           # Service implementations
│       ├── util/               # StringUtils
│       └── validation/         # Custom validators (HanKiemDinh)
│   └── src/main/resources/
│       ├── application.yml
│       ├── application-postgresql.yml
│       └── db/migration/postgresql/V1__init.sql
│
└── FE/                         # React frontend
    ├── Dockerfile
    ├── package.json
    ├── vite.config.ts
    └── src/
        ├── App.tsx             # Routes
        ├── layouts/
        │   └── AdminLayout.tsx # Sidebar + header layout
        ├── pages/
        │   ├── Dashboard.tsx         # Thống kê doanh thu (Chức năng 4)
        │   ├── VehicleList.tsx       # Danh sách xe (Chức năng 2)
        │   ├── AddVehicle.tsx        # Nhập xe (Chức năng 1)
        │   ├── AddScheduleModal.tsx  # Nhập lịch trình (Chức năng 3)
        │   ├── NhaXePage.tsx         # Quản lý nhà xe
        │   ├── TaiXePage.tsx         # Quản lý tài xế
        │   ├── TuyenPage.tsx         # Quản lý tuyến xe
        │   └── LoaiXePage.tsx        # Quản lý loại xe
        ├── services/
        │   └── api.ts          # Tất cả API calls
        └── components/ui/      # Button, Card, Input, Table, Label
```

---

## 🗄️ Lược đồ cơ sở dữ liệu

```
NHAXE                    LOAIXE
├── id (PK)              ├── id (PK)
├── ma_nha_xe (UNIQUE)   ├── ma_loai_xe (UNIQUE)
├── ten_nha_xe           ├── mo_ta_loai_xe
├── nam_thanh_lap        └── so_luong_cho_ngoi
├── dia_chi
└── so_dien_thoai        TAIXE
                         ├── id (PK)
XE                       ├── ma_tai_xe (UNIQUE)
├── id (PK)              ├── ho_ten
├── ma_xe (UNIQUE)       ├── so_cccd (UNIQUE)
├── hang_san_xuat        ├── so_dien_thoai (UNIQUE)
├── bien_so (UNIQUE)     └── kinh_nghiem_nam
├── han_kiem_dinh
├── loai_xe_id (FK)      TUYEN
├── tai_xe_id (FK)       ├── id (PK)
└── nha_xe_id (FK)       ├── ma_tuyen (UNIQUE)
                         ├── ten_tuyen
LICHTRINHXE              ├── diem_di
├── id (PK)              ├── diem_den
├── xe_id (FK)           ├── khoang_cach_km
├── tuyen_id (FK)        └── don_gia
├── ten_tai_xe
├── ngay_gio_xuat_ben
├── don_gia
└── so_luong_hanh_khach
```

**Quan hệ:**
- Một nhà xe → nhiều xe
- Một tài xế → nhiều xe
- Một loại xe → nhiều xe
- Một xe → nhiều lịch trình
- Một tuyến → nhiều lịch trình

---

## 🚀 Hướng dẫn chạy dự án

### Yêu cầu

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) đã được cài đặt và đang chạy
- Không cần cài Java, Maven, Node.js — Docker xử lý hết

### Bước 1 — Clone / Giải nén dự án

```bash
# Giải nén file zip đã tải về
unzip ben-xe-danang-fixed.zip
cd ben-xe-danang-fixed
```

### Bước 2 — Chạy toàn bộ hệ thống

```bash
docker-compose up --build
```

Lần đầu chạy sẽ mất 3–5 phút để build. Các lần sau chỉ mất ~30 giây.

Khi thấy log `Started QuanLyXeApplication`, hệ thống đã sẵn sàng:

| Service | URL |
|---|---|
| **Frontend** | http://localhost:3000 |
| **Backend API** | http://localhost:8080 |
| **PostgreSQL** | localhost:5432 |

### Bước 3 — Seed dữ liệu mẫu

Mở terminal mới và chạy:

```bash
curl -X POST http://localhost:8080/api/seed
```

Dữ liệu mẫu gồm: 3 nhà xe, 4 tài xế, 4 tuyến, 4 loại xe, 4 xe, 5 lịch trình.

### Bước 4 — Truy cập ứng dụng

Mở trình duyệt và vào **http://localhost:3000**

### Dừng hệ thống

```bash
# Dừng nhưng giữ dữ liệu
docker-compose down

# Dừng và xóa toàn bộ dữ liệu (reset)
docker-compose down -v
```

---

## 🔌 API Reference

**Base URL:** `http://localhost:8080`

Tất cả response theo format:
```json
{
  "success": true,
  "message": "...",
  "data": ...
}
```

### Nhà xe — `/api/nha-xes`
| Method | Endpoint | Mô tả |
|---|---|---|
| GET | `/api/nha-xes` | Danh sách nhà xe |
| POST | `/api/nha-xes` | Thêm nhà xe |
| GET | `/api/nha-xes/{id}` | Chi tiết nhà xe |
| PUT | `/api/nha-xes/{id}` | Cập nhật nhà xe |
| DELETE | `/api/nha-xes/{id}` | Xóa nhà xe |

### Tài xế — `/api/tai-xes`
| Method | Endpoint | Mô tả |
|---|---|---|
| GET | `/api/tai-xes` | Danh sách tài xế |
| POST | `/api/tai-xes` | Thêm tài xế |
| PUT | `/api/tai-xes/{id}` | Cập nhật tài xế |
| DELETE | `/api/tai-xes/{id}` | Xóa tài xế |

### Tuyến xe — `/api/tuyens`
| Method | Endpoint | Mô tả |
|---|---|---|
| GET | `/api/tuyens` | Danh sách tuyến |
| POST | `/api/tuyens` | Thêm tuyến |
| PUT | `/api/tuyens/{id}` | Cập nhật tuyến |
| DELETE | `/api/tuyens/{id}` | Xóa tuyến |

### Loại xe — `/api/loai-xes`
| Method | Endpoint | Mô tả |
|---|---|---|
| GET | `/api/loai-xes` | Danh sách loại xe |
| POST | `/api/loai-xes` | Thêm loại xe |
| PUT | `/api/loai-xes/{id}` | Cập nhật loại xe |
| DELETE | `/api/loai-xes/{id}` | Xóa loại xe |

### Xe — `/api/xes`
| Method | Endpoint | Mô tả |
|---|---|---|
| GET | `/api/xes?tenNhaXe=` | Danh sách xe (lọc theo tên nhà xe) |
| POST | `/api/xes` | Thêm xe mới |
| GET | `/api/xes/{maXe}` | Chi tiết xe |
| PUT | `/api/xes/{maXe}` | Cập nhật xe |
| DELETE | `/api/xes/{maXe}` | Xóa xe |
| GET | `/api/xes/{maXe}/lich-trinhs` | Lịch trình của xe |
| POST | `/api/xes/{maXe}/lich-trinhs` | Thêm lịch trình |

### Thống kê — `/api/thong-ke`
| Method | Endpoint | Mô tả |
|---|---|---|
| GET | `/api/thong-ke/nha-xe-thu-nhap` | Doanh thu theo nhà xe |

### Seed dữ liệu — `/api/seed`
| Method | Endpoint | Mô tả |
|---|---|---|
| POST | `/api/seed` | Tạo dữ liệu mẫu (chỉ chạy khi DB trống) |

---

## ✅ Validate dữ liệu

### Biển số xe (validate client — Chức năng 1)
- Định dạng: `xxYx-xxxxx` (x là chữ số, Y là chữ in hoa)
- Ví dụ hợp lệ: `43B1-12345`, `51H2-67890`
- Regex: `^\d{2}[A-Z]\d-\d{5}$`

### Hạn kiểm định (validate server — Chức năng 1)
- Phải lớn hơn thời điểm hiện tại ít nhất **1 tháng**
- Nếu sai trả về: `"Han kiem dinh khong dung, han kiem dinh phai lon hon thoi gian hien tai la 1 thang"`

### Tính doanh thu (Chức năng 4)
```
Doanh thu mỗi chuyến = Đơn giá × Số lượng hành khách
```

---

## 🔧 Chạy local (không dùng Docker)

### Backend

```bash
cd BE

# Cần có PostgreSQL đang chạy ở localhost:5432
# Tạo database: CREATE DATABASE quanlyxe;

# Tạo file .env trong thư mục BE/
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/quanlyxe
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres123

# Chạy
./mvnw spring-boot:run
```

### Frontend

```bash
cd FE
npm install
npm run dev
# Truy cập: http://localhost:3000
```

---

## 🐛 Xử lý sự cố thường gặp

**Backend không khởi động được:**
```bash
# Xem log
docker logs quanlyxe-be

# Reset hoàn toàn
docker-compose down -v
docker-compose up --build
```

**Port 3000 hoặc 8080 bị chiếm:**
```bash
# Kiểm tra process đang dùng port
lsof -i :8080
lsof -i :3000
```

**Frontend báo lỗi kết nối API:**
- Kiểm tra backend đã chạy chưa: http://localhost:8080
- Kiểm tra file `FE/.env` có `VITE_API_URL=http://localhost:8080`

---

