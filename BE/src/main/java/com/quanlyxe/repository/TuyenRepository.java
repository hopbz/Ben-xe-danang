package com.quanlyxe.repository;

import com.quanlyxe.entity.Tuyen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TuyenRepository extends JpaRepository<Tuyen, Long> {
    Optional<Tuyen> findByMaTuyenIgnoreCase(String maTuyen);
    List<Tuyen> findAllByOrderByMaTuyenAsc();
}
