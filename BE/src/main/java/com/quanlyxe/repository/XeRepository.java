package com.quanlyxe.repository;

import com.quanlyxe.entity.Xe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface XeRepository extends JpaRepository<Xe, Long> {
    Optional<Xe> findByMaXe(String maXe);
    Optional<Xe> findByBienSo(String bienSo);
    List<Xe> findAllByOrderByMaXeAsc();
    List<Xe> findByNhaXe_TenNhaXeContainingIgnoreCaseOrderByMaXeAsc(String tenNhaXe);
}
