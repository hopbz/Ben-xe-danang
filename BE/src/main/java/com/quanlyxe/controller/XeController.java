package com.quanlyxe.controller;

import com.quanlyxe.dto.ApiResponse;
import com.quanlyxe.dto.request.LichTrinhCreateRequest;
import com.quanlyxe.dto.request.XeCreateRequest;
import com.quanlyxe.dto.response.LichTrinhResponse;
import com.quanlyxe.dto.response.XeDetailResponse;
import com.quanlyxe.dto.response.XeSummaryResponse;
import com.quanlyxe.service.XeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/xes")
@RequiredArgsConstructor
public class XeController {

    private final XeService xeService;

    /** Lấy danh sách xe. */
    @GetMapping
    public ApiResponse<List<XeSummaryResponse>> list(@RequestParam(required = false) String tenNhaXe) {
        return ApiResponse.ok(xeService.list(tenNhaXe));
    }

    /** Tạo xe mới. */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<XeDetailResponse> create(@Valid @RequestBody XeCreateRequest request) {
        return ApiResponse.ok("Thêm xe thành công", xeService.create(request));
    }

    /** Lấy chi tiết xe theo mã xe. */
    @GetMapping("/{maXe}")
    public ApiResponse<XeDetailResponse> detail(@PathVariable String maXe) {
        return ApiResponse.ok(xeService.getDetail(maXe));
    }

    /** Cập nhật xe. */
    @PutMapping("/{maXe}")
    public ApiResponse<XeDetailResponse> update(@PathVariable String maXe, @Valid @RequestBody XeCreateRequest request) {
        return ApiResponse.ok("Cập nhật xe thành công", xeService.update(maXe, request));
    }

    /** Xóa xe. */
    @DeleteMapping("/{maXe}")
    public ApiResponse<Void> delete(@PathVariable String maXe) {
        xeService.delete(maXe);
        return ApiResponse.ok("Xóa xe thành công", null);
    }

    /** Tạo lịch trình cho xe. */
    @PostMapping("/{maXe}/lich-trinhs")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<LichTrinhResponse> createLichTrinh(@PathVariable String maXe,
                                                          @Valid @RequestBody LichTrinhCreateRequest request) {
        return ApiResponse.ok("Thêm lịch trình thành công", xeService.createLichTrinh(maXe, request));
    }

    /** Lấy lịch trình của xe. */
    @GetMapping("/{maXe}/lich-trinhs")
    public ApiResponse<List<LichTrinhResponse>> listLichTrinh(@PathVariable String maXe) {
        return ApiResponse.ok(xeService.listLichTrinhByXe(maXe));
    }
}
