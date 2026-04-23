package com.quanlyxe.service;

import com.quanlyxe.dto.request.LoaiXeRequest;
import com.quanlyxe.dto.response.LoaiXeResponse;
import com.quanlyxe.entity.LoaiXe;

import java.util.List;

public interface LoaiXeService {
    List<LoaiXeResponse> list();
    LoaiXeResponse getById(Long id);
    LoaiXeResponse create(LoaiXeRequest request);
    LoaiXeResponse update(Long id, LoaiXeRequest request);
    void delete(Long id);
    LoaiXe findByIdOrThrow(Long id);
}
