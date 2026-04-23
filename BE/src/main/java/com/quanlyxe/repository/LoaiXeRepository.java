package com.quanlyxe.repository;

import com.quanlyxe.entity.LoaiXe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoaiXeRepository extends JpaRepository<LoaiXe, Long> {
    List<LoaiXe> findAllByOrderByMaLoaiXeAsc();
    Optional<LoaiXe> findByMaLoaiXeIgnoreCase(String maLoaiXe);
}
