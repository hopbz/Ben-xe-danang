package com.quanlyxe.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class LichTrinhCreateRequest {

    @NotBlank(message = "Tên tài xế không được để trống")
    @Size(max = 150, message = "Tên tài xế tối đa 150 ký tự")
    private String tenTaiXe;

    @NotNull(message = "Tuyến không được để trống")
    private Long tuyenId;

    @NotNull(message = "Ngày giờ xuất bến không được để trống")
    @Future(message = "Ngày giờ xuất bến phải ở tương lai")
    private LocalDateTime ngayGioXuatBen;

    @NotNull(message = "Đơn giá không được để trống")
    @Min(value = 0, message = "Đơn giá phải lớn hơn hoặc bằng 0")
    private BigDecimal donGia;

    @NotNull(message = "Số lượng hành khách không được để trống")
    @Min(value = 1, message = "Số lượng hành khách phải lớn hơn 0")
    private Integer soLuongHanhKhach;
}
