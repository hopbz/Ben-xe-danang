package com.quanlyxe.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TuyenRequest {

    @NotBlank(message = "Mã tuyến không được để trống")
    @Size(max = 50, message = "Mã tuyến tối đa 50 ký tự")
    private String maTuyen;

    @NotBlank(message = "Tên tuyến không được để trống")
    @Size(max = 150, message = "Tên tuyến tối đa 150 ký tự")
    private String tenTuyen;

    @NotBlank(message = "Điểm đi không được để trống")
    @Size(max = 150, message = "Điểm đi tối đa 150 ký tự")
    private String diemDi;

    @NotBlank(message = "Điểm đến không được để trống")
    @Size(max = 150, message = "Điểm đến tối đa 150 ký tự")
    private String diemDen;

    @NotNull(message = "Khoảng cách không được để trống")
    @DecimalMin(value = "0.1", message = "Khoảng cách phải lớn hơn 0")
    private BigDecimal khoangCachKm;

    @NotNull(message = "Đơn giá không được để trống")
    @DecimalMin(value = "0", inclusive = true, message = "Đơn giá phải lớn hơn hoặc bằng 0")
    private BigDecimal donGia;
}
