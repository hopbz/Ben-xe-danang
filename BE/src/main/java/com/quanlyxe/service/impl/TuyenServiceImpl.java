package com.quanlyxe.service.impl;

import com.quanlyxe.dto.request.TuyenRequest;
import com.quanlyxe.dto.response.TuyenResponse;
import com.quanlyxe.entity.Tuyen;
import com.quanlyxe.exception.ConflictException;
import com.quanlyxe.exception.NotFoundException;
import com.quanlyxe.repository.TuyenRepository;
import com.quanlyxe.service.TuyenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.quanlyxe.util.StringUtils.trim;
import static com.quanlyxe.util.StringUtils.trimUpperCase;

@Service
@RequiredArgsConstructor
@Transactional
public class TuyenServiceImpl implements TuyenService {

    private final TuyenRepository tuyenRepository;

    /** Lấy danh sách tuyến xe sắp xếp theo mã tuyến. */
    @Override
    @Transactional(readOnly = true)
    public List<TuyenResponse> list() {
        return tuyenRepository.findAllByOrderByMaTuyenAsc().stream().map(this::toResponse).toList();
    }

    /** Lấy tuyến theo id. */
    @Override
    @Transactional(readOnly = true)
    public TuyenResponse getById(Long id) {
        return toResponse(findByIdOrThrow(id));
    }

    /** Tạo tuyến mới. */
    @Override
    public TuyenResponse create(TuyenRequest request) {
        String ma = trimUpperCase(request.getMaTuyen());
        if (tuyenRepository.findByMaTuyenIgnoreCase(ma).isPresent()) {
            throw new ConflictException("Mã tuyến đã tồn tại: " + ma);
        }
        Tuyen tuyen = Tuyen.builder()
                .maTuyen(ma)
                .tenTuyen(trim(request.getTenTuyen()))
                .diemDi(trim(request.getDiemDi()))
                .diemDen(trim(request.getDiemDen()))
                .khoangCachKm(request.getKhoangCachKm())
                .donGia(request.getDonGia())
                .build();
        return toResponse(tuyenRepository.save(tuyen));
    }

    /** Cập nhật tuyến. */
    @Override
    public TuyenResponse update(Long id, TuyenRequest request) {
        Tuyen tuyen = findByIdOrThrow(id);
        String ma = trimUpperCase(request.getMaTuyen());
        tuyenRepository.findByMaTuyenIgnoreCase(ma).ifPresent(exist -> {
            if (!exist.getId().equals(id)) throw new ConflictException("Mã tuyến đã tồn tại: " + ma);
        });
        tuyen.setMaTuyen(ma);
        tuyen.setTenTuyen(trim(request.getTenTuyen()));
        tuyen.setDiemDi(trim(request.getDiemDi()));
        tuyen.setDiemDen(trim(request.getDiemDen()));
        tuyen.setKhoangCachKm(request.getKhoangCachKm());
        tuyen.setDonGia(request.getDonGia());
        return toResponse(tuyenRepository.save(tuyen));
    }

    /** Xóa tuyến nếu không còn lịch trình. */
    @Override
    public void delete(Long id) {
        Tuyen tuyen = findByIdOrThrow(id);
        if (tuyen.getLichTrinhs() != null && !tuyen.getLichTrinhs().isEmpty()) {
            throw new ConflictException("Không thể xóa tuyến đang có lịch trình");
        }
        tuyenRepository.delete(tuyen);
    }

    /** Tìm tuyến theo id, ném exception nếu không tìm thấy. */
    @Override
    @Transactional(readOnly = true)
    public Tuyen findByIdOrThrow(Long id) {
        return tuyenRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tuyến với id = " + id));
    }

    private TuyenResponse toResponse(Tuyen tuyen) {
        return TuyenResponse.builder()
                .id(tuyen.getId())
                .maTuyen(tuyen.getMaTuyen())
                .tenTuyen(tuyen.getTenTuyen())
                .diemDi(tuyen.getDiemDi())
                .diemDen(tuyen.getDiemDen())
                .khoangCachKm(tuyen.getKhoangCachKm())
                .donGia(tuyen.getDonGia())
                .soLuongChuyen(tuyen.getLichTrinhs() == null ? 0 : tuyen.getLichTrinhs().size())
                .build();
    }
}
