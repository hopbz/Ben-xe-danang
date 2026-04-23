-- ========================
-- SCHEMA BẾN XE ĐÀ NẴNG
-- ========================

CREATE TABLE nha_xe (
    id BIGSERIAL PRIMARY KEY,
    ma_nha_xe VARCHAR(50) NOT NULL,
    ten_nha_xe VARCHAR(150) NOT NULL,
    nam_thanh_lap INT,
    dia_chi VARCHAR(255) NOT NULL,
    so_dien_thoai VARCHAR(20) NOT NULL,
    CONSTRAINT uk_nha_xe_ma_nha_xe UNIQUE (ma_nha_xe),
    CONSTRAINT uk_nha_xe_ten_nha_xe UNIQUE (ten_nha_xe),
    CONSTRAINT uk_nha_xe_so_dien_thoai UNIQUE (so_dien_thoai)
);

CREATE TABLE loai_xe (
    id BIGSERIAL PRIMARY KEY,
    ma_loai_xe VARCHAR(50) NOT NULL,
    mo_ta_loai_xe VARCHAR(255) NOT NULL,
    so_luong_cho_ngoi INT NOT NULL,
    CONSTRAINT uk_loai_xe_ma_loai_xe UNIQUE (ma_loai_xe)
);

CREATE TABLE tai_xe (
    id BIGSERIAL PRIMARY KEY,
    ma_tai_xe VARCHAR(50) NOT NULL,
    ho_ten VARCHAR(150) NOT NULL,
    so_cccd VARCHAR(20) NOT NULL,
    so_dien_thoai VARCHAR(20) NOT NULL,
    kinh_nghiem_nam INT NOT NULL,
    CONSTRAINT uk_tai_xe_ma_tai_xe UNIQUE (ma_tai_xe),
    CONSTRAINT uk_tai_xe_so_cccd UNIQUE (so_cccd),
    CONSTRAINT uk_tai_xe_so_dien_thoai UNIQUE (so_dien_thoai)
);

CREATE TABLE tuyen (
    id BIGSERIAL PRIMARY KEY,
    ma_tuyen VARCHAR(50) NOT NULL,
    ten_tuyen VARCHAR(150) NOT NULL,
    diem_di VARCHAR(150) NOT NULL,
    diem_den VARCHAR(150) NOT NULL,
    khoang_cach_km NUMERIC(10,2) NOT NULL,
    don_gia NUMERIC(12,2) NOT NULL,
    CONSTRAINT uk_tuyen_ma_tuyen UNIQUE (ma_tuyen)
);

CREATE TABLE xe (
    id BIGSERIAL PRIMARY KEY,
    ma_xe VARCHAR(50) NOT NULL,
    hang_san_xuat VARCHAR(100) NOT NULL,
    bien_so VARCHAR(20) NOT NULL,
    han_kiem_dinh DATE NOT NULL,
    loai_xe_id BIGINT NOT NULL,
    tai_xe_id BIGINT NOT NULL,
    nha_xe_id BIGINT NOT NULL,
    CONSTRAINT uk_xe_ma_xe UNIQUE (ma_xe),
    CONSTRAINT uk_xe_bien_so UNIQUE (bien_so),
    CONSTRAINT fk_xe_loai_xe FOREIGN KEY (loai_xe_id) REFERENCES loai_xe(id),
    CONSTRAINT fk_xe_tai_xe FOREIGN KEY (tai_xe_id) REFERENCES tai_xe(id),
    CONSTRAINT fk_xe_nha_xe FOREIGN KEY (nha_xe_id) REFERENCES nha_xe(id)
);

CREATE TABLE lich_trinh (
    id BIGSERIAL PRIMARY KEY,
    xe_id BIGINT NOT NULL,
    tuyen_id BIGINT NOT NULL,
    ten_tai_xe VARCHAR(150) NOT NULL,
    ngay_gio_xuat_ben TIMESTAMP NOT NULL,
    so_luong_hanh_khach INT NOT NULL,
    don_gia NUMERIC(12,2) NOT NULL,
    CONSTRAINT uk_lich_trinh_xe_gio UNIQUE (xe_id, ngay_gio_xuat_ben),
    CONSTRAINT fk_lt_xe FOREIGN KEY (xe_id) REFERENCES xe(id),
    CONSTRAINT fk_lt_tuyen FOREIGN KEY (tuyen_id) REFERENCES tuyen(id)
);

CREATE INDEX idx_xe_bien_so ON xe(bien_so);
CREATE INDEX idx_lt_xe ON lich_trinh(xe_id);
CREATE INDEX idx_lt_tuyen ON lich_trinh(tuyen_id);
