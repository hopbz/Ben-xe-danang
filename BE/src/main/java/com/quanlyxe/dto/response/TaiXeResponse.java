package com.quanlyxe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaiXeResponse {
    private Long id;
    private String maTaiXe;
    private String hoTen;
    private String soCccd;
    private String soDienThoai;
    private Integer kinhNghiemNam;
    private Integer soLuongXeDangPhuTrach;
}
