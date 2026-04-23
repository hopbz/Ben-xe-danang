package com.quanlyxe.service;

import com.quanlyxe.dto.request.TaiXeRequest;
import com.quanlyxe.dto.response.TaiXeResponse;
import com.quanlyxe.entity.TaiXe;

import java.util.List;

public interface TaiXeService {
    List<TaiXeResponse> list();
    TaiXeResponse getById(Long id);
    TaiXeResponse create(TaiXeRequest request);
    TaiXeResponse update(Long id, TaiXeRequest request);
    void delete(Long id);
    TaiXe findByIdOrThrow(Long id);
}
