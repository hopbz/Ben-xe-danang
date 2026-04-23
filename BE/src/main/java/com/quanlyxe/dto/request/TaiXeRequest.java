package com.quanlyxe.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TaiXeRequest {

    @NotBlank(message = "Mã tài xế không được để trống")
    @Size(max = 50, message = "Mã tài xế tối đa 50 ký tự")
    private String maTaiXe;

    @NotBlank(message = "Họ tên không được để trống")
    @Size(max = 150, message = "Họ tên tối đa 150 ký tự")
    private String hoTen;

    @NotBlank(message = "Số CCCD không được để trống")
    @Pattern(regexp = "^[0-9]{9,12}$", message = "Số CCCD phải có 9 đến 12 chữ số")
    private String soCccd;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^[0-9]{9,11}$", message = "Số điện thoại phải từ 9 đến 11 chữ số")
    private String soDienThoai;

    @NotNull(message = "Kinh nghiệm không được để trống")
    @Min(value = 0, message = "Kinh nghiệm phải lớn hơn hoặc bằng 0")
    private Integer kinhNghiemNam;
}
