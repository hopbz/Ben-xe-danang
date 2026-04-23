package com.quanlyxe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThongKeNhaXeResponse {
    private Long nhaXeId;
    private String tenNhaXe;
    private Integer soLuongXe;
    private Integer soLuongChuyen;
    private BigDecimal tongDoanhThu;
    private Integer tongSoHanhKhach;
}
