package com.quanlyxe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XeDetailResponse {
    private String maXe;
    private String hangSanXuat;
    private String bienSo;
    private LocalDate hanKiemDinh;
    private Long loaiXeId;
    private String maLoaiXe;
    private String moTaLoaiXe;
    private Integer soLuongChoNgoi;
    private Long taiXeId;
    private String tenTaiXe;
    private Long nhaXeId;
    private String tenNhaXe;
    private List<LichTrinhResponse> lichTrinhs;
}
