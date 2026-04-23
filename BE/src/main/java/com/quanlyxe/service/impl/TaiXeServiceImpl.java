package com.quanlyxe.service.impl;

import com.quanlyxe.dto.request.TaiXeRequest;
import com.quanlyxe.dto.response.TaiXeResponse;
import com.quanlyxe.entity.TaiXe;
import com.quanlyxe.exception.ConflictException;
import com.quanlyxe.exception.NotFoundException;
import com.quanlyxe.repository.TaiXeRepository;
import com.quanlyxe.service.TaiXeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.quanlyxe.util.StringUtils.trim;
import static com.quanlyxe.util.StringUtils.trimUpperCase;

@Service
@RequiredArgsConstructor
@Transactional
public class TaiXeServiceImpl implements TaiXeService {

    private final TaiXeRepository taiXeRepository;

    /** Lấy danh sách tài xế sắp xếp theo họ tên. */
    @Override
    @Transactional(readOnly = true)
    public List<TaiXeResponse> list() {
        return taiXeRepository.findAllByOrderByHoTenAsc().stream().map(this::toResponse).toList();
    }

    /** Lấy tài xế theo id. */
    @Override
    @Transactional(readOnly = true)
    public TaiXeResponse getById(Long id) {
        return toResponse(findByIdOrThrow(id));
    }

    /** Tạo tài xế mới. */
    @Override
    public TaiXeResponse create(TaiXeRequest request) {
        String ma = trimUpperCase(request.getMaTaiXe());
        String cccd = trim(request.getSoCccd());
        String sdt = trim(request.getSoDienThoai());

        if (taiXeRepository.findByMaTaiXeIgnoreCase(ma).isPresent()) {
            throw new ConflictException("Mã tài xế đã tồn tại: " + ma);
        }
        if (taiXeRepository.findBySoCccd(cccd).isPresent()) {
            throw new ConflictException("Số CCCD đã tồn tại: " + cccd);
        }
        if (taiXeRepository.findBySoDienThoai(sdt).isPresent()) {
            throw new ConflictException("Số điện thoại đã tồn tại: " + sdt);
        }

        TaiXe taiXe = TaiXe.builder()
                .maTaiXe(ma)
                .hoTen(trim(request.getHoTen()))
                .soCccd(cccd)
                .soDienThoai(sdt)
                .kinhNghiemNam(request.getKinhNghiemNam())
                .build();
        return toResponse(taiXeRepository.save(taiXe));
    }

    /** Cập nhật tài xế. */
    @Override
    public TaiXeResponse update(Long id, TaiXeRequest request) {
        TaiXe taiXe = findByIdOrThrow(id);
        String ma = trimUpperCase(request.getMaTaiXe());
        String cccd = trim(request.getSoCccd());
        String sdt = trim(request.getSoDienThoai());

        taiXeRepository.findByMaTaiXeIgnoreCase(ma).ifPresent(exist -> {
            if (!exist.getId().equals(id)) throw new ConflictException("Mã tài xế đã tồn tại: " + ma);
        });
        taiXeRepository.findBySoCccd(cccd).ifPresent(exist -> {
            if (!exist.getId().equals(id)) throw new ConflictException("Số CCCD đã tồn tại: " + cccd);
        });
        taiXeRepository.findBySoDienThoai(sdt).ifPresent(exist -> {
            if (!exist.getId().equals(id)) throw new ConflictException("Số điện thoại đã tồn tại: " + sdt);
        });

        taiXe.setMaTaiXe(ma);
        taiXe.setHoTen(trim(request.getHoTen()));
        taiXe.setSoCccd(cccd);
        taiXe.setSoDienThoai(sdt);
        taiXe.setKinhNghiemNam(request.getKinhNghiemNam());
        return toResponse(taiXeRepository.save(taiXe));
    }

    /** Xóa tài xế nếu không có xe liên kết. */
    @Override
    public void delete(Long id) {
        TaiXe taiXe = findByIdOrThrow(id);
        if (taiXe.getXes() != null && !taiXe.getXes().isEmpty()) {
            throw new ConflictException("Không thể xóa tài xế đang được phân công cho xe");
        }
        taiXeRepository.delete(taiXe);
    }

    /** Tìm tài xế theo id, ném exception nếu không tìm thấy. */
    @Override
    @Transactional(readOnly = true)
    public TaiXe findByIdOrThrow(Long id) {
        return taiXeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tài xế với id = " + id));
    }

    private TaiXeResponse toResponse(TaiXe taiXe) {
        return TaiXeResponse.builder()
                .id(taiXe.getId())
                .maTaiXe(taiXe.getMaTaiXe())
                .hoTen(taiXe.getHoTen())
                .soCccd(taiXe.getSoCccd())
                .soDienThoai(taiXe.getSoDienThoai())
                .kinhNghiemNam(taiXe.getKinhNghiemNam())
                .soLuongXeDangPhuTrach(taiXe.getXes() == null ? 0 : taiXe.getXes().size())
                .build();
    }
}
