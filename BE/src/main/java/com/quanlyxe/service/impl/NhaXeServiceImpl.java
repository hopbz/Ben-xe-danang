package com.quanlyxe.service.impl;

import com.quanlyxe.dto.request.NhaXeRequest;
import com.quanlyxe.dto.response.NhaXeResponse;
import com.quanlyxe.entity.NhaXe;
import com.quanlyxe.exception.ConflictException;
import com.quanlyxe.exception.NotFoundException;
import com.quanlyxe.repository.NhaXeRepository;
import com.quanlyxe.service.NhaXeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.quanlyxe.util.StringUtils.trim;
import static com.quanlyxe.util.StringUtils.trimUpperCase;

@Service
@RequiredArgsConstructor
@Transactional
public class NhaXeServiceImpl implements NhaXeService {

    private final NhaXeRepository nhaXeRepository;

    /** Lấy danh sách nhà xe sắp xếp theo tên. */
    @Override
    @Transactional(readOnly = true)
    public List<NhaXeResponse> list() {
        return nhaXeRepository.findAllByOrderByTenNhaXeAsc().stream().map(this::toResponse).toList();
    }

    /** Lấy nhà xe theo id. */
    @Override
    @Transactional(readOnly = true)
    public NhaXeResponse getById(Long id) {
        return toResponse(findByIdOrThrow(id));
    }

    /** Tạo nhà xe mới. */
    @Override
    public NhaXeResponse create(NhaXeRequest request) {
        String ma = trimUpperCase(request.getMaNhaXe());
        String ten = trim(request.getTenNhaXe());
        String sdt = trim(request.getSoDienThoai());

        if (nhaXeRepository.findByMaNhaXeIgnoreCase(ma).isPresent()) {
            throw new ConflictException("Mã nhà xe đã tồn tại: " + ma);
        }
        if (nhaXeRepository.findByTenNhaXeIgnoreCase(ten).isPresent()) {
            throw new ConflictException("Tên nhà xe đã tồn tại: " + ten);
        }
        if (nhaXeRepository.findBySoDienThoai(sdt).isPresent()) {
            throw new ConflictException("Số điện thoại đã tồn tại: " + sdt);
        }

        NhaXe nhaXe = NhaXe.builder()
                .maNhaXe(ma)
                .tenNhaXe(ten)
                .namThanhLap(request.getNamThanhLap())
                .diaChi(trim(request.getDiaChi()))
                .soDienThoai(sdt)
                .build();
        return toResponse(nhaXeRepository.save(nhaXe));
    }

    /** Cập nhật nhà xe. */
    @Override
    public NhaXeResponse update(Long id, NhaXeRequest request) {
        NhaXe nhaXe = findByIdOrThrow(id);
        String ma = trimUpperCase(request.getMaNhaXe());
        String ten = trim(request.getTenNhaXe());
        String sdt = trim(request.getSoDienThoai());

        nhaXeRepository.findByMaNhaXeIgnoreCase(ma).ifPresent(exist -> {
            if (!exist.getId().equals(id)) throw new ConflictException("Mã nhà xe đã tồn tại: " + ma);
        });
        nhaXeRepository.findByTenNhaXeIgnoreCase(ten).ifPresent(exist -> {
            if (!exist.getId().equals(id)) throw new ConflictException("Tên nhà xe đã tồn tại: " + ten);
        });
        nhaXeRepository.findBySoDienThoai(sdt).ifPresent(exist -> {
            if (!exist.getId().equals(id)) throw new ConflictException("Số điện thoại đã tồn tại: " + sdt);
        });

        nhaXe.setMaNhaXe(ma);
        nhaXe.setTenNhaXe(ten);
        nhaXe.setNamThanhLap(request.getNamThanhLap());
        nhaXe.setDiaChi(trim(request.getDiaChi()));
        nhaXe.setSoDienThoai(sdt);
        return toResponse(nhaXeRepository.save(nhaXe));
    }

    /** Xóa nhà xe nếu không có xe liên kết. */
    @Override
    public void delete(Long id) {
        NhaXe nhaXe = findByIdOrThrow(id);
        if (nhaXe.getXes() != null && !nhaXe.getXes().isEmpty()) {
            throw new ConflictException("Không thể xóa nhà xe đang có xe liên kết");
        }
        nhaXeRepository.delete(nhaXe);
    }

    /** Tìm nhà xe theo id, ném exception nếu không tìm thấy. */
    @Override
    @Transactional(readOnly = true)
    public NhaXe findByIdOrThrow(Long id) {
        return nhaXeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy nhà xe với id = " + id));
    }

    private NhaXeResponse toResponse(NhaXe nhaXe) {
        return NhaXeResponse.builder()
                .id(nhaXe.getId())
                .maNhaXe(nhaXe.getMaNhaXe())
                .tenNhaXe(nhaXe.getTenNhaXe())
                .namThanhLap(nhaXe.getNamThanhLap())
                .diaChi(nhaXe.getDiaChi())
                .soDienThoai(nhaXe.getSoDienThoai())
                .soLuongXe(nhaXe.getXes() == null ? 0 : nhaXe.getXes().size())
                .build();
    }
}
