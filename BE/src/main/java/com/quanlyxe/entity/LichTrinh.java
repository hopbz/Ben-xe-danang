package com.quanlyxe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lich_trinh",
        uniqueConstraints = @UniqueConstraint(name = "uk_lich_trinh_xe_gio", columnNames = {"xe_id", "ngay_gio_xuat_ben"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LichTrinh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "xe_id", nullable = false)
    private Xe xe;

    @Column(name = "ten_tai_xe", nullable = false, length = 150)
    private String tenTaiXe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tuyen_id", nullable = false)
    private Tuyen tuyen;

    @Column(name = "ngay_gio_xuat_ben", nullable = false)
    private LocalDateTime ngayGioXuatBen;

    @Column(name = "don_gia", nullable = false, precision = 15, scale = 2)
    private BigDecimal donGia;

    @Column(name = "so_luong_hanh_khach", nullable = false)
    private Integer soLuongHanhKhach;
}
