package com.quanlyxe.controller;

import com.quanlyxe.dto.ApiResponse;
import com.quanlyxe.dto.response.ThongKeNhaXeResponse;
import com.quanlyxe.service.ThongKeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thong-ke")
@RequiredArgsConstructor
public class ThongKeController {

    private final ThongKeService thongKeService;

    /** Lấy thống kê thu nhập của nhà xe. */
    @GetMapping("/nha-xe-thu-nhap")
    public ApiResponse<List<ThongKeNhaXeResponse>> thongKeThuNhapNhaXe() {
        return ApiResponse.ok(thongKeService.thongKeThuNhapNhaXe());
    }
}
