package com.quanlyxe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NhaXeResponse {
    private Long id;
    private String maNhaXe;
    private String tenNhaXe;
    private Integer namThanhLap;
    private String diaChi;
    private String soDienThoai;
    private int soLuongXe;
}
