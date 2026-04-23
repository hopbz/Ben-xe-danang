package com.quanlyxe.service.impl;

import com.quanlyxe.dto.request.LoaiXeRequest;
import com.quanlyxe.dto.response.LoaiXeResponse;
import com.quanlyxe.entity.LoaiXe;
import com.quanlyxe.exception.ConflictException;
import com.quanlyxe.exception.NotFoundException;
import com.quanlyxe.repository.LoaiXeRepository;
import com.quanlyxe.service.LoaiXeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.quanlyxe.util.StringUtils.trim;
import static com.quanlyxe.util.StringUtils.trimUpperCase;

@Service
@RequiredArgsConstructor
@Transactional
public class LoaiXeServiceImpl implements LoaiXeService {

    private final LoaiXeRepository loaiXeRepository;

    /** Lấy danh sách loại xe theo mã tăng dần. */
    @Override
    @Transactional(readOnly = true)
    public List<LoaiXeResponse> list() {
        return loaiXeRepository.findAllByOrderByMaLoaiXeAsc().stream().map(this::toResponse).toList();
    }

    /** Lấy loại xe theo id. */
    @Override
    @Transactional(readOnly = true)
    public LoaiXeResponse getById(Long id) {
        return toResponse(findByIdOrThrow(id));
    }

    /** Tạo loại xe mới. */
    @Override
    public LoaiXeResponse create(LoaiXeRequest request) {
        String maLoaiXe = trimUpperCase(request.getMaLoaiXe());
        if (loaiXeRepository.findByMaLoaiXeIgnoreCase(maLoaiXe).isPresent()) {
            throw new ConflictException("Mã loại xe đã tồn tại: " + maLoaiXe);
        }
        LoaiXe loaiXe = LoaiXe.builder()
                .maLoaiXe(maLoaiXe)
                .moTaLoaiXe(trim(request.getMoTaLoaiXe()))
                .soLuongChoNgoi(request.getSoLuongChoNgoi())
                .build();
        return toResponse(loaiXeRepository.save(loaiXe));
    }

    /** Cập nhật loại xe. */
    @Override
    public LoaiXeResponse update(Long id, LoaiXeRequest request) {
        LoaiXe loaiXe = findByIdOrThrow(id);
        String maLoaiXe = trimUpperCase(request.getMaLoaiXe());
        loaiXeRepository.findByMaLoaiXeIgnoreCase(maLoaiXe).ifPresent(exist -> {
            if (!exist.getId().equals(id)) {
                throw new ConflictException("Mã loại xe đã tồn tại: " + maLoaiXe);
            }
        });
        loaiXe.setMaLoaiXe(maLoaiXe);
        loaiXe.setMoTaLoaiXe(trim(request.getMoTaLoaiXe()));
        loaiXe.setSoLuongChoNgoi(request.getSoLuongChoNgoi());
        return toResponse(loaiXeRepository.save(loaiXe));
    }

    /** Xóa loại xe nếu chưa có xe liên kết. */
    @Override
    public void delete(Long id) {
        LoaiXe loaiXe = findByIdOrThrow(id);
        if (loaiXe.getXes() != null && !loaiXe.getXes().isEmpty()) {
            throw new ConflictException("Không thể xóa loại xe đang được sử dụng");
        }
        loaiXeRepository.delete(loaiXe);
    }

    /** Tìm loại xe theo id. */
    @Override
    @Transactional(readOnly = true)
    public LoaiXe findByIdOrThrow(Long id) {
        return loaiXeRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy loại xe với id = " + id));
    }

    private LoaiXeResponse toResponse(LoaiXe loaiXe) {
        int soLuongXe = loaiXe.getXes() == null ? 0 : loaiXe.getXes().size();
        return LoaiXeResponse.builder()
                .id(loaiXe.getId())
                .maLoaiXe(loaiXe.getMaLoaiXe())
                .moTaLoaiXe(loaiXe.getMoTaLoaiXe())
                .soLuongChoNgoi(loaiXe.getSoLuongChoNgoi())
                .soLuongXe(soLuongXe)
                .build();
    }
}
