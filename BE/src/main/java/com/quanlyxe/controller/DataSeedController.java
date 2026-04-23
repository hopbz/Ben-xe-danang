package com.quanlyxe.controller;

import com.quanlyxe.dto.ApiResponse;
import com.quanlyxe.entity.*;
import com.quanlyxe.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/seed")
@RequiredArgsConstructor
public class DataSeedController {

    private final NhaXeRepository nhaXeRepository;
    private final TaiXeRepository taiXeRepository;
    private final TuyenRepository tuyenRepository;
    private final LoaiXeRepository loaiXeRepository;
    private final XeRepository xeRepository;
    private final LichTrinhRepository lichTrinhRepository;

    /** Tạo dữ liệu mẫu ban đầu nếu database còn trống. */
    @PostMapping
    @Transactional
    public ApiResponse<String> seed() {
        if (nhaXeRepository.count() > 0) {
            return ApiResponse.ok("Dữ liệu mẫu đã tồn tại, bỏ qua.", null);
        }

        LoaiXe lx1 = loaiXeRepository.save(LoaiXe.builder().maLoaiXe("LX-40G").moTaLoaiXe("Xe giường nằm 40 chỗ").soLuongChoNgoi(40).build());
        LoaiXe lx2 = loaiXeRepository.save(LoaiXe.builder().maLoaiXe("LX-29G").moTaLoaiXe("Xe ghế ngồi 29 chỗ").soLuongChoNgoi(29).build());
        LoaiXe lx3 = loaiXeRepository.save(LoaiXe.builder().maLoaiXe("LX-16L").moTaLoaiXe("Limousine 16 chỗ").soLuongChoNgoi(16).build());
        LoaiXe lx4 = loaiXeRepository.save(LoaiXe.builder().maLoaiXe("LX-09L").moTaLoaiXe("Limousine 9 chỗ").soLuongChoNgoi(9).build());

        NhaXe nx1 = nhaXeRepository.save(NhaXe.builder().maNhaXe("NX01").tenNhaXe("Phương Trang").namThanhLap(2001).diaChi("123 Nguyễn Văn Linh, Đà Nẵng").soDienThoai("0236123456").build());
        NhaXe nx2 = nhaXeRepository.save(NhaXe.builder().maNhaXe("NX02").tenNhaXe("Hoàng Long").namThanhLap(1995).diaChi("456 Lê Duẩn, Đà Nẵng").soDienThoai("0236234567").build());
        NhaXe nx3 = nhaXeRepository.save(NhaXe.builder().maNhaXe("NX03").tenNhaXe("Mai Linh Express").namThanhLap(2010).diaChi("789 Hải Phòng, Đà Nẵng").soDienThoai("0236345678").build());

        TaiXe tx1 = taiXeRepository.save(TaiXe.builder().maTaiXe("TX01").hoTen("Nguyễn Văn An").soCccd("201234567890").soDienThoai("0901234567").kinhNghiemNam(8).build());
        TaiXe tx2 = taiXeRepository.save(TaiXe.builder().maTaiXe("TX02").hoTen("Trần Thị Bình").soCccd("202345678901").soDienThoai("0912345678").kinhNghiemNam(5).build());
        TaiXe tx3 = taiXeRepository.save(TaiXe.builder().maTaiXe("TX03").hoTen("Lê Văn Cường").soCccd("203456789012").soDienThoai("0923456789").kinhNghiemNam(12).build());
        TaiXe tx4 = taiXeRepository.save(TaiXe.builder().maTaiXe("TX04").hoTen("Phạm Thị Dung").soCccd("204567890123").soDienThoai("0934567890").kinhNghiemNam(3).build());

        Tuyen t1 = tuyenRepository.save(Tuyen.builder().maTuyen("DN-HN").tenTuyen("Đà Nẵng - Hà Nội").diemDi("Bến xe Đà Nẵng").diemDen("Bến xe Giáp Bát").khoangCachKm(new BigDecimal("763.00")).donGia(new BigDecimal("400000")).build());
        Tuyen t2 = tuyenRepository.save(Tuyen.builder().maTuyen("DN-HCM").tenTuyen("Đà Nẵng - TP. Hồ Chí Minh").diemDi("Bến xe Đà Nẵng").diemDen("Bến xe Miền Đông").khoangCachKm(new BigDecimal("964.00")).donGia(new BigDecimal("450000")).build());
        Tuyen t3 = tuyenRepository.save(Tuyen.builder().maTuyen("DN-NT").tenTuyen("Đà Nẵng - Nha Trang").diemDi("Bến xe Đà Nẵng").diemDen("Bến xe Nha Trang").khoangCachKm(new BigDecimal("530.00")).donGia(new BigDecimal("250000")).build());
        Tuyen t4 = tuyenRepository.save(Tuyen.builder().maTuyen("DN-DL").tenTuyen("Đà Nẵng - Đà Lạt").diemDi("Bến xe Đà Nẵng").diemDen("Bến xe Đà Lạt").khoangCachKm(new BigDecimal("680.00")).donGia(new BigDecimal("300000")).build());

        Xe x1 = xeRepository.save(Xe.builder().maXe("PT-001").hangSanXuat("THACO").bienSo("43B1-12345").hanKiemDinh(LocalDate.of(2027, 6, 15)).loaiXe(lx1).taiXe(tx1).nhaXe(nx1).build());
        Xe x2 = xeRepository.save(Xe.builder().maXe("PT-002").hangSanXuat("HYUNDAI").bienSo("43B2-23456").hanKiemDinh(LocalDate.of(2027, 8, 20)).loaiXe(lx2).taiXe(tx2).nhaXe(nx1).build());
        Xe x3 = xeRepository.save(Xe.builder().maXe("HL-001").hangSanXuat("FORD").bienSo("43H1-34567").hanKiemDinh(LocalDate.of(2026, 12, 1)).loaiXe(lx3).taiXe(tx3).nhaXe(nx2).build());
        Xe x4 = xeRepository.save(Xe.builder().maXe("ML-001").hangSanXuat("ISUZU").bienSo("43A1-45678").hanKiemDinh(LocalDate.of(2027, 3, 10)).loaiXe(lx4).taiXe(tx4).nhaXe(nx3).build());

        lichTrinhRepository.saveAll(List.of(
            LichTrinh.builder().xe(x1).tenTaiXe(tx1.getHoTen()).tuyen(t1).ngayGioXuatBen(LocalDateTime.of(2026, 5, 10, 18, 0)).donGia(t1.getDonGia()).soLuongHanhKhach(35).build(),
            LichTrinh.builder().xe(x1).tenTaiXe(tx1.getHoTen()).tuyen(t2).ngayGioXuatBen(LocalDateTime.of(2026, 5, 15, 20, 0)).donGia(t2.getDonGia()).soLuongHanhKhach(30).build(),
            LichTrinh.builder().xe(x2).tenTaiXe(tx2.getHoTen()).tuyen(t3).ngayGioXuatBen(LocalDateTime.of(2026, 5, 12, 8, 30)).donGia(t3.getDonGia()).soLuongHanhKhach(20).build(),
            LichTrinh.builder().xe(x3).tenTaiXe(tx3.getHoTen()).tuyen(t4).ngayGioXuatBen(LocalDateTime.of(2026, 5, 18, 7, 0)).donGia(t4.getDonGia()).soLuongHanhKhach(25).build(),
            LichTrinh.builder().xe(x4).tenTaiXe(tx4.getHoTen()).tuyen(t1).ngayGioXuatBen(LocalDateTime.of(2026, 5, 20, 19, 0)).donGia(t1.getDonGia()).soLuongHanhKhach(15).build()
        ));

        return ApiResponse.ok("Seed dữ liệu mẫu thành công!", null);
    }
}
