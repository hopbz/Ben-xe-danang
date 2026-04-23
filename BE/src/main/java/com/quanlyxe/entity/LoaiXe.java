package com.quanlyxe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "loai_xe",
        uniqueConstraints = @UniqueConstraint(name = "uk_loai_xe_ma_loai_xe", columnNames = "ma_loai_xe"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "xes")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LoaiXe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "ma_loai_xe", nullable = false, length = 50)
    private String maLoaiXe;

    @Column(name = "mo_ta_loai_xe", nullable = false, length = 255)
    private String moTaLoaiXe;

    @Column(name = "so_luong_cho_ngoi", nullable = false)
    private Integer soLuongChoNgoi;

    @JsonIgnore
    @OneToMany(mappedBy = "loaiXe", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Xe> xes = new ArrayList<>();
}
