package com.quanlyxe.controller;

import com.quanlyxe.dto.ApiResponse;
import com.quanlyxe.dto.request.LoaiXeRequest;
import com.quanlyxe.dto.request.NhaXeRequest;
import com.quanlyxe.dto.request.TaiXeRequest;
import com.quanlyxe.dto.request.TuyenRequest;
import com.quanlyxe.dto.response.LoaiXeResponse;
import com.quanlyxe.dto.response.NhaXeResponse;
import com.quanlyxe.dto.response.TaiXeResponse;
import com.quanlyxe.dto.response.TuyenResponse;
import com.quanlyxe.service.LoaiXeService;
import com.quanlyxe.service.NhaXeService;
import com.quanlyxe.service.TaiXeService;
import com.quanlyxe.service.TuyenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DanhMucController {

    private final NhaXeService nhaXeService;
    private final TaiXeService taiXeService;
    private final TuyenService tuyenService;
    private final LoaiXeService loaiXeService;

    /** Lấy danh sách nhà xe. */
    @GetMapping("/nha-xes")
    public ApiResponse<List<NhaXeResponse>> listNhaXe() {
        return ApiResponse.ok(nhaXeService.list());
    }

    /** Tạo nhà xe mới. */
    @PostMapping("/nha-xes")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<NhaXeResponse> createNhaXe(@Valid @RequestBody NhaXeRequest request) {
        return ApiResponse.ok("Thêm nhà xe thành công", nhaXeService.create(request));
    }

    /** Lấy nhà xe theo id. */
    @GetMapping("/nha-xes/{id}")
    public ApiResponse<NhaXeResponse> getNhaXe(@PathVariable Long id) {
        return ApiResponse.ok(nhaXeService.getById(id));
    }

    /** Cập nhật nhà xe. */
    @PutMapping("/nha-xes/{id}")
    public ApiResponse<NhaXeResponse> updateNhaXe(@PathVariable Long id, @Valid @RequestBody NhaXeRequest request) {
        return ApiResponse.ok("Cập nhật nhà xe thành công", nhaXeService.update(id, request));
    }

    /** Xóa nhà xe. */
    @DeleteMapping("/nha-xes/{id}")
    public ApiResponse<Void> deleteNhaXe(@PathVariable Long id) {
        nhaXeService.delete(id);
        return ApiResponse.ok("Xóa nhà xe thành công", null);
    }

    /** Lấy danh sách tài xế. */
    @GetMapping("/tai-xes")
    public ApiResponse<List<TaiXeResponse>> listTaiXe() {
        return ApiResponse.ok(taiXeService.list());
    }

    /** Tạo tài xế mới. */
    @PostMapping("/tai-xes")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TaiXeResponse> createTaiXe(@Valid @RequestBody TaiXeRequest request) {
        return ApiResponse.ok("Thêm tài xế thành công", taiXeService.create(request));
    }

    /** Lấy tài xế theo id. */
    @GetMapping("/tai-xes/{id}")
    public ApiResponse<TaiXeResponse> getTaiXe(@PathVariable Long id) {
        return ApiResponse.ok(taiXeService.getById(id));
    }

    /** Cập nhật tài xế. */
    @PutMapping("/tai-xes/{id}")
    public ApiResponse<TaiXeResponse> updateTaiXe(@PathVariable Long id, @Valid @RequestBody TaiXeRequest request) {
        return ApiResponse.ok("Cập nhật tài xế thành công", taiXeService.update(id, request));
    }

    /** Xóa tài xế. */
    @DeleteMapping("/tai-xes/{id}")
    public ApiResponse<Void> deleteTaiXe(@PathVariable Long id) {
        taiXeService.delete(id);
        return ApiResponse.ok("Xóa tài xế thành công", null);
    }

    /** Lấy danh sách tuyến xe. */
    @GetMapping("/tuyens")
    public ApiResponse<List<TuyenResponse>> listTuyen() {
        return ApiResponse.ok(tuyenService.list());
    }

    /** Tạo tuyến xe mới. */
    @PostMapping("/tuyens")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TuyenResponse> createTuyen(@Valid @RequestBody TuyenRequest request) {
        return ApiResponse.ok("Thêm tuyến thành công", tuyenService.create(request));
    }

    /** Lấy tuyến theo id. */
    @GetMapping("/tuyens/{id}")
    public ApiResponse<TuyenResponse> getTuyen(@PathVariable Long id) {
        return ApiResponse.ok(tuyenService.getById(id));
    }

    /** Cập nhật tuyến. */
    @PutMapping("/tuyens/{id}")
    public ApiResponse<TuyenResponse> updateTuyen(@PathVariable Long id, @Valid @RequestBody TuyenRequest request) {
        return ApiResponse.ok("Cập nhật tuyến thành công", tuyenService.update(id, request));
    }

    /** Xóa tuyến. */
    @DeleteMapping("/tuyens/{id}")
    public ApiResponse<Void> deleteTuyen(@PathVariable Long id) {
        tuyenService.delete(id);
        return ApiResponse.ok("Xóa tuyến thành công", null);
    }

    /** Lấy danh sách loại xe. */
    @GetMapping("/loai-xes")
    public ApiResponse<List<LoaiXeResponse>> listLoaiXe() {
        return ApiResponse.ok(loaiXeService.list());
    }

    /** Tạo loại xe mới. */
    @PostMapping("/loai-xes")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<LoaiXeResponse> createLoaiXe(@Valid @RequestBody LoaiXeRequest request) {
        return ApiResponse.ok("Thêm loại xe thành công", loaiXeService.create(request));
    }

    /** Lấy loại xe theo id. */
    @GetMapping("/loai-xes/{id}")
    public ApiResponse<LoaiXeResponse> getLoaiXe(@PathVariable Long id) {
        return ApiResponse.ok(loaiXeService.getById(id));
    }

    /** Cập nhật loại xe. */
    @PutMapping("/loai-xes/{id}")
    public ApiResponse<LoaiXeResponse> updateLoaiXe(@PathVariable Long id, @Valid @RequestBody LoaiXeRequest request) {
        return ApiResponse.ok("Cập nhật loại xe thành công", loaiXeService.update(id, request));
    }

    /** Xóa loại xe. */
    @DeleteMapping("/loai-xes/{id}")
    public ApiResponse<Void> deleteLoaiXe(@PathVariable Long id) {
        loaiXeService.delete(id);
        return ApiResponse.ok("Xóa loại xe thành công", null);
    }
}
