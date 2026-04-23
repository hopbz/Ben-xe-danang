package com.quanlyxe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LichTrinhResponse {
    private Long id;
    private String maXe;
    private String bienSo;
    private String tenTaiXe;
    private Long tuyenId;
    private String maTuyen;
    private String tenTuyen;
    private LocalDateTime ngayGioXuatBen;
    private BigDecimal donGia;
    private Integer soLuongHanhKhach;
    private BigDecimal tongTien;
}
