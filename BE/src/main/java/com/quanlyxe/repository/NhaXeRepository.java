package com.quanlyxe.repository;

import com.quanlyxe.entity.NhaXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NhaXeRepository extends JpaRepository<NhaXe, Long> {
    List<NhaXe> findAllByOrderByTenNhaXeAsc();
    Optional<NhaXe> findByMaNhaXeIgnoreCase(String maNhaXe);
    Optional<NhaXe> findByTenNhaXeIgnoreCase(String tenNhaXe);
    Optional<NhaXe> findBySoDienThoai(String soDienThoai);
}
