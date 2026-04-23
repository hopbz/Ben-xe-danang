package com.quanlyxe.dto.request;

import com.quanlyxe.validation.HanKiemDinhHopLe;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class XeCreateRequest {

    @NotBlank(message = "Mã xe không được để trống")
    @Size(max = 50, message = "Mã xe tối đa 50 ký tự")
    private String maXe;

    @NotBlank(message = "Hãng sản xuất không được để trống")
    @Size(max = 100, message = "Hãng sản xuất tối đa 100 ký tự")
    private String hangSanXuat;

    @NotBlank(message = "Biển số không được để trống")
    @Size(max = 30, message = "Biển số tối đa 30 ký tự")
    @Pattern(regexp = "^\\d{2}[A-Z]\\d-\\d{5}$", message = "Biển số phải đúng dạng xxYx-xxxxx")
    private String bienSo;

    @NotNull(message = "Hạn kiểm định không được để trống")
    @HanKiemDinhHopLe
    private LocalDate hanKiemDinh;

    @NotNull(message = "Mã loại xe không được để trống")
    private Long loaiXeId;

    @NotNull(message = "Tài xế không được để trống")
    private Long taiXeId;

    @NotNull(message = "Nhà xe không được để trống")
    private Long nhaXeId;
}
