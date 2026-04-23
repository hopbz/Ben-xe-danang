package com.quanlyxe.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoaiXeRequest {

    @NotBlank(message = "Mã loại xe không được để trống")
    @Size(max = 50, message = "Mã loại xe tối đa 50 ký tự")
    private String maLoaiXe;

    @NotBlank(message = "Mô tả loại xe không được để trống")
    @Size(max = 255, message = "Mô tả loại xe tối đa 255 ký tự")
    private String moTaLoaiXe;

    @NotNull(message = "Số lượng chỗ ngồi không được để trống")
    @Min(value = 1, message = "Số lượng chỗ ngồi phải lớn hơn 0")
    private Integer soLuongChoNgoi;
}
