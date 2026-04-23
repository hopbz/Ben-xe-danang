package com.quanlyxe.service;

import com.quanlyxe.dto.request.LichTrinhCreateRequest;
import com.quanlyxe.dto.request.XeCreateRequest;
import com.quanlyxe.dto.response.LichTrinhResponse;
import com.quanlyxe.dto.response.XeDetailResponse;
import com.quanlyxe.dto.response.XeSummaryResponse;

import java.util.List;

public interface XeService {
    List<XeSummaryResponse> list(String tenNhaXe);
    XeDetailResponse getDetail(String maXe);
    XeDetailResponse create(XeCreateRequest request);
    XeDetailResponse update(String maXe, XeCreateRequest request);
    void delete(String maXe);
    LichTrinhResponse createLichTrinh(String maXe, LichTrinhCreateRequest request);
    List<LichTrinhResponse> listLichTrinhByXe(String maXe);
}
