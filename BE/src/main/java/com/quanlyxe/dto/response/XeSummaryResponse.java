package com.quanlyxe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XeSummaryResponse {
    private String maXe;
    private String hangSanXuat;
    private String bienSo;
    private LocalDate hanKiemDinh;
    private Long loaiXeId;
    private String maLoaiXe;
    private String moTaLoaiXe;
    private Integer soLuongChoNgoi;
    private String tenTaiXe;
    private String tenNhaXe;
    private String maTuyen;
    private String tenTuyen;
    private LocalDateTime ngayGioXuatBen;
    private Integer soLuongLichTrinh;
}
