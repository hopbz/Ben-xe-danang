package com.quanlyxe.service.impl;

import com.quanlyxe.dto.response.ThongKeNhaXeResponse;
import com.quanlyxe.repository.LichTrinhRepository;
import com.quanlyxe.service.ThongKeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ThongKeServiceImpl implements ThongKeService {

    private final LichTrinhRepository lichTrinhRepository;

    /** Thống kê doanh thu và hành khách theo từng nhà xe. */
    @Override
    @Transactional(readOnly = true)
    public List<ThongKeNhaXeResponse> thongKeThuNhapNhaXe() {
        return lichTrinhRepository.thongKeDoanhThuTheoNhaXe().stream()
                .map(row -> ThongKeNhaXeResponse.builder()
                        .nhaXeId((Long) row[0])
                        .tenNhaXe((String) row[1])
                        .soLuongXe(((Long) row[2]).intValue())
                        .soLuongChuyen(((Long) row[3]).intValue())
                        .tongDoanhThu((BigDecimal) row[4])
                        .tongSoHanhKhach(((Long) row[5]).intValue())
                        .build())
                .toList();
    }
}
