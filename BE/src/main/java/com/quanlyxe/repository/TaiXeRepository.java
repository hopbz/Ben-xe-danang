package com.quanlyxe.repository;

import com.quanlyxe.entity.TaiXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaiXeRepository extends JpaRepository<TaiXe, Long> {
    Optional<TaiXe> findByMaTaiXeIgnoreCase(String maTaiXe);
    Optional<TaiXe> findBySoCccd(String soCccd);
    Optional<TaiXe> findBySoDienThoai(String soDienThoai);
    List<TaiXe> findAllByOrderByHoTenAsc();
}
