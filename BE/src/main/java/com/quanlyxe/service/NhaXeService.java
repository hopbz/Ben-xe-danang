package com.quanlyxe.service;

import com.quanlyxe.dto.request.NhaXeRequest;
import com.quanlyxe.dto.response.NhaXeResponse;
import com.quanlyxe.entity.NhaXe;

import java.util.List;

public interface NhaXeService {
    List<NhaXeResponse> list();
    NhaXeResponse getById(Long id);
    NhaXeResponse create(NhaXeRequest request);
    NhaXeResponse update(Long id, NhaXeRequest request);
    void delete(Long id);
    NhaXe findByIdOrThrow(Long id);
}
