package com.quanlyxe.repository;

import com.quanlyxe.entity.LichTrinh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface LichTrinhRepository extends JpaRepository<LichTrinh, Long> {

    List<LichTrinh> findByXe_MaXeOrderByNgayGioXuatBenDesc(String maXe);

    boolean existsByXe_MaXeAndNgayGioXuatBen(String maXe, LocalDateTime ngayGioXuatBen);

    @Query("""
            SELECT nx.id, nx.tenNhaXe,
                   COUNT(DISTINCT x.id),
                   COUNT(lt.id),
                   COALESCE(SUM(lt.donGia * lt.soLuongHanhKhach), 0),
                   COALESCE(SUM(lt.soLuongHanhKhach), 0)
            FROM LichTrinh lt
            JOIN lt.xe x
            JOIN x.nhaXe nx
            GROUP BY nx.id, nx.tenNhaXe
            ORDER BY nx.tenNhaXe
            """)
    List<Object[]> thongKeDoanhThuTheoNhaXe();
}
