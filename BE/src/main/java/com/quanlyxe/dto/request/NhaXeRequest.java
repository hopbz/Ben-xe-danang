package com.quanlyxe.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class NhaXeRequest {

    @NotBlank(message = "Mã nhà xe không được để trống")
    @Size(max = 50, message = "Mã nhà xe tối đa 50 ký tự")
    private String maNhaXe;

    @NotBlank(message = "Tên nhà xe không được để trống")
    @Size(max = 150, message = "Tên nhà xe tối đa 150 ký tự")
    private String tenNhaXe;

    @NotNull(message = "Năm thành lập không được để trống")
    @Min(value = 1900, message = "Năm thành lập không hợp lệ")
    @Max(value = 2100, message = "Năm thành lập không hợp lệ")
    private Integer namThanhLap;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Size(max = 255, message = "Địa chỉ tối đa 255 ký tự")
    private String diaChi;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^[0-9]{9,11}$", message = "Số điện thoại phải từ 9 đến 11 chữ số")
    private String soDienThoai;
}
