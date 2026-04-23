package com.quanlyxe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "nha_xe",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_nha_xe_ma_nha_xe", columnNames = "ma_nha_xe"),
                @UniqueConstraint(name = "uk_nha_xe_ten_nha_xe", columnNames = "ten_nha_xe"),
                @UniqueConstraint(name = "uk_nha_xe_so_dien_thoai", columnNames = "so_dien_thoai")
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "xes")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NhaXe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "ma_nha_xe", nullable = false, length = 50)
    private String maNhaXe;

    @Column(name = "ten_nha_xe", nullable = false, length = 150)
    private String tenNhaXe;

    @Column(name = "nam_thanh_lap")
    private Integer namThanhLap;

    @Column(name = "dia_chi", nullable = false, length = 255)
    private String diaChi;

    @Column(name = "so_dien_thoai", nullable = false, length = 20)
    private String soDienThoai;

    @JsonIgnore
    @OneToMany(mappedBy = "nhaXe", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Xe> xes = new ArrayList<>();
}
