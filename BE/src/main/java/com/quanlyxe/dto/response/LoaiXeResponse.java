package com.quanlyxe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoaiXeResponse {
    private Long id;
    private String maLoaiXe;
    private String moTaLoaiXe;
    private Integer soLuongChoNgoi;
    private Integer soLuongXe;
}
