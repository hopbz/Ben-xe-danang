package com.quanlyxe.service.impl;

import com.quanlyxe.dto.request.LichTrinhCreateRequest;
import com.quanlyxe.dto.request.XeCreateRequest;
import com.quanlyxe.dto.response.LichTrinhResponse;
import com.quanlyxe.dto.response.XeDetailResponse;
import com.quanlyxe.dto.response.XeSummaryResponse;
import com.quanlyxe.entity.LichTrinh;
import com.quanlyxe.entity.LoaiXe;
import com.quanlyxe.entity.NhaXe;
import com.quanlyxe.entity.TaiXe;
import com.quanlyxe.entity.Tuyen;
import com.quanlyxe.entity.Xe;
import com.quanlyxe.exception.ConflictException;
import com.quanlyxe.exception.NotFoundException;
import com.quanlyxe.repository.LichTrinhRepository;
import com.quanlyxe.repository.XeRepository;
import com.quanlyxe.service.LoaiXeService;
import com.quanlyxe.service.NhaXeService;
import com.quanlyxe.service.TaiXeService;
import com.quanlyxe.service.TuyenService;
import com.quanlyxe.service.XeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import static com.quanlyxe.util.StringUtils.trimUpperCase;

@Service
@RequiredArgsConstructor
@Transactional
public class XeServiceImpl implements XeService {

    private final XeRepository xeRepository;
    private final LoaiXeService loaiXeService;
    private final NhaXeService nhaXeService;
    private final TaiXeService taiXeService;
    private final TuyenService tuyenService;
    private final LichTrinhRepository lichTrinhRepository;

    /** Lấy danh sách xe, có lọc theo tên nhà xe. */
    @Override
    @Transactional(readOnly = true)
    public List<XeSummaryResponse> list(String tenNhaXe) {
        List<Xe> xes = (tenNhaXe == null || tenNhaXe.isBlank())
                ? xeRepository.findAllByOrderByMaXeAsc()
                : xeRepository.findByNhaXe_TenNhaXeContainingIgnoreCaseOrderByMaXeAsc(tenNhaXe.trim());
        return xes.stream().map(this::toSummaryResponse).toList();
    }

    /** Lấy chi tiết xe theo mã xe. */
    @Override
    @Transactional(readOnly = true)
    public XeDetailResponse getDetail(String maXe) {
        return toDetailResponse(findXeByMaXeOrThrow(trimUpperCase(maXe)));
    }

    /** Tạo xe mới. */
    @Override
    public XeDetailResponse create(XeCreateRequest request) {
        String maXe = trimUpperCase(request.getMaXe());
        String bienSo = trimUpperCase(request.getBienSo());
        checkXeUniqueness(null, maXe, bienSo);

        TaiXe taiXe = taiXeService.findByIdOrThrow(request.getTaiXeId());
        NhaXe nhaXe = nhaXeService.findByIdOrThrow(request.getNhaXeId());
        LoaiXe loaiXe = loaiXeService.findByIdOrThrow(request.getLoaiXeId());

        Xe xe = Xe.builder()
                .maXe(maXe)
                .hangSanXuat(request.getHangSanXuat().trim())
                .bienSo(bienSo)
                .hanKiemDinh(request.getHanKiemDinh())
                .loaiXe(loaiXe)
                .taiXe(taiXe)
                .nhaXe(nhaXe)
                .build();
        return toDetailResponse(xeRepository.save(xe));
    }

    /** Cập nhật xe theo mã xe. */
    @Override
    public XeDetailResponse update(String maXe, XeCreateRequest request) {
        Xe xe = findXeByMaXeOrThrow(trimUpperCase(maXe));
        String newMaXe = trimUpperCase(request.getMaXe());
        String bienSo = trimUpperCase(request.getBienSo());
        checkXeUniqueness(xe.getId(), newMaXe, bienSo);

        xe.setMaXe(newMaXe);
        xe.setHangSanXuat(request.getHangSanXuat().trim());
        xe.setBienSo(bienSo);
        xe.setHanKiemDinh(request.getHanKiemDinh());
        xe.setLoaiXe(loaiXeService.findByIdOrThrow(request.getLoaiXeId()));
        xe.setTaiXe(taiXeService.findByIdOrThrow(request.getTaiXeId()));
        xe.setNhaXe(nhaXeService.findByIdOrThrow(request.getNhaXeId()));
        return toDetailResponse(xeRepository.save(xe));
    }

    /** Xóa xe theo mã xe. */
    @Override
    public void delete(String maXe) {
        xeRepository.delete(findXeByMaXeOrThrow(trimUpperCase(maXe)));
    }

    /** Tạo lịch trình cho một xe. */
    @Override
    public LichTrinhResponse createLichTrinh(String maXe, LichTrinhCreateRequest request) {
        Xe xe = findXeByMaXeOrThrow(trimUpperCase(maXe));
        Tuyen tuyen = tuyenService.findByIdOrThrow(request.getTuyenId());

        if (lichTrinhRepository.existsByXe_MaXeAndNgayGioXuatBen(xe.getMaXe(), request.getNgayGioXuatBen())) {
            throw new ConflictException("Xe đã có lịch trình tại thời điểm này");
        }

        LichTrinh lichTrinh = LichTrinh.builder()
                .xe(xe)
                .tenTaiXe(request.getTenTaiXe().trim())
                .tuyen(tuyen)
                .ngayGioXuatBen(request.getNgayGioXuatBen())
                .donGia(request.getDonGia())
                .soLuongHanhKhach(request.getSoLuongHanhKhach())
                .build();
        return toLichTrinhResponse(lichTrinhRepository.save(lichTrinh));
    }

    /** Lấy danh sách lịch trình của một xe. */
    @Override
    @Transactional(readOnly = true)
    public List<LichTrinhResponse> listLichTrinhByXe(String maXe) {
        String code = trimUpperCase(maXe);
        findXeByMaXeOrThrow(code);
        return lichTrinhRepository.findByXe_MaXeOrderByNgayGioXuatBenDesc(code).stream().map(this::toLichTrinhResponse).toList();
    }

    @Transactional(readOnly = true)
    public Xe findXeByMaXeOrThrow(String maXe) {
        return xeRepository.findByMaXe(maXe).orElseThrow(() -> new NotFoundException("Không tìm thấy xe với mã xe = " + maXe));
    }

    private void checkXeUniqueness(Long currentId, String maXe, String bienSo) {
        xeRepository.findByMaXe(maXe).ifPresent(exist -> {
            if (currentId == null || !exist.getId().equals(currentId)) {
                throw new ConflictException("Mã xe đã tồn tại: " + maXe);
            }
        });
        xeRepository.findByBienSo(bienSo).ifPresent(exist -> {
            if (currentId == null || !exist.getId().equals(currentId)) {
                throw new ConflictException("Biển số đã tồn tại: " + bienSo);
            }
        });
    }

    private XeSummaryResponse toSummaryResponse(Xe xe) {
        LichTrinh latest = xe.getLichTrinhs().stream().max(Comparator.comparing(LichTrinh::getNgayGioXuatBen)).orElse(null);
        String tenTaiXe = latest != null ? latest.getTenTaiXe() : (xe.getTaiXe() != null ? xe.getTaiXe().getHoTen() : null);
        return XeSummaryResponse.builder()
                .maXe(xe.getMaXe())
                .hangSanXuat(xe.getHangSanXuat())
                .bienSo(xe.getBienSo())
                .hanKiemDinh(xe.getHanKiemDinh())
                .loaiXeId(xe.getLoaiXe() != null ? xe.getLoaiXe().getId() : null)
                .maLoaiXe(xe.getLoaiXe() != null ? xe.getLoaiXe().getMaLoaiXe() : null)
                .moTaLoaiXe(xe.getLoaiXe() != null ? xe.getLoaiXe().getMoTaLoaiXe() : null)
                .soLuongChoNgoi(xe.getLoaiXe() != null ? xe.getLoaiXe().getSoLuongChoNgoi() : null)
                .tenTaiXe(tenTaiXe)
                .tenNhaXe(xe.getNhaXe() != null ? xe.getNhaXe().getTenNhaXe() : null)
                .maTuyen(latest != null ? latest.getTuyen().getMaTuyen() : null)
                .tenTuyen(latest != null ? latest.getTuyen().getTenTuyen() : null)
                .ngayGioXuatBen(latest != null ? latest.getNgayGioXuatBen() : null)
                .soLuongLichTrinh(xe.getLichTrinhs().size())
                .build();
    }

    private XeDetailResponse toDetailResponse(Xe xe) {
        return XeDetailResponse.builder()
                .maXe(xe.getMaXe())
                .hangSanXuat(xe.getHangSanXuat())
                .bienSo(xe.getBienSo())
                .hanKiemDinh(xe.getHanKiemDinh())
                .loaiXeId(xe.getLoaiXe() != null ? xe.getLoaiXe().getId() : null)
                .maLoaiXe(xe.getLoaiXe() != null ? xe.getLoaiXe().getMaLoaiXe() : null)
                .moTaLoaiXe(xe.getLoaiXe() != null ? xe.getLoaiXe().getMoTaLoaiXe() : null)
                .soLuongChoNgoi(xe.getLoaiXe() != null ? xe.getLoaiXe().getSoLuongChoNgoi() : null)
                .taiXeId(xe.getTaiXe() != null ? xe.getTaiXe().getId() : null)
                .tenTaiXe(xe.getTaiXe() != null ? xe.getTaiXe().getHoTen() : null)
                .nhaXeId(xe.getNhaXe() != null ? xe.getNhaXe().getId() : null)
                .tenNhaXe(xe.getNhaXe() != null ? xe.getNhaXe().getTenNhaXe() : null)
                .lichTrinhs(xe.getLichTrinhs().stream().map(this::toLichTrinhResponse).toList())
                .build();
    }

    private LichTrinhResponse toLichTrinhResponse(LichTrinh lt) {
        return LichTrinhResponse.builder()
                .id(lt.getId())
                .maXe(lt.getXe().getMaXe())
                .bienSo(lt.getXe().getBienSo())
                .tenTaiXe(lt.getTenTaiXe())
                .tuyenId(lt.getTuyen().getId())
                .maTuyen(lt.getTuyen().getMaTuyen())
                .tenTuyen(lt.getTuyen().getTenTuyen())
                .ngayGioXuatBen(lt.getNgayGioXuatBen())
                .donGia(lt.getDonGia())
                .soLuongHanhKhach(lt.getSoLuongHanhKhach())
                .tongTien(lt.getDonGia().multiply(BigDecimal.valueOf(lt.getSoLuongHanhKhach())))
                .build();
    }
}
