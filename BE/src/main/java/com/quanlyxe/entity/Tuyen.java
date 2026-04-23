package com.quanlyxe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tuyen",
        uniqueConstraints = @UniqueConstraint(name = "uk_tuyen_ma_tuyen", columnNames = "ma_tuyen"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "lichTrinhs")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Tuyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "ma_tuyen", nullable = false, length = 50)
    private String maTuyen;

    @Column(name = "ten_tuyen", nullable = false, length = 150)
    private String tenTuyen;

    @Column(name = "diem_di", nullable = false, length = 150)
    private String diemDi;

    @Column(name = "diem_den", nullable = false, length = 150)
    private String diemDen;

    @Column(name = "khoang_cach_km", nullable = false, precision = 10, scale = 2)
    private BigDecimal khoangCachKm;

    @Column(name = "don_gia", nullable = false, precision = 12, scale = 2)
    private BigDecimal donGia;

    @JsonIgnore
    @OneToMany(mappedBy = "tuyen", fetch = FetchType.LAZY)
    @Builder.Default
    private List<LichTrinh> lichTrinhs = new ArrayList<>();
}
