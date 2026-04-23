package com.quanlyxe.service;

import com.quanlyxe.dto.request.TuyenRequest;
import com.quanlyxe.dto.response.TuyenResponse;
import com.quanlyxe.entity.Tuyen;

import java.util.List;

public interface TuyenService {
    List<TuyenResponse> list();
    TuyenResponse getById(Long id);
    TuyenResponse create(TuyenRequest request);
    TuyenResponse update(Long id, TuyenRequest request);
    void delete(Long id);
    Tuyen findByIdOrThrow(Long id);
}
