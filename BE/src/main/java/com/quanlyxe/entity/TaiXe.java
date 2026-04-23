package com.quanlyxe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tai_xe",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_tai_xe_ma_tai_xe", columnNames = "ma_tai_xe"),
                @UniqueConstraint(name = "uk_tai_xe_so_cccd", columnNames = "so_cccd"),
                @UniqueConstraint(name = "uk_tai_xe_so_dien_thoai", columnNames = "so_dien_thoai")
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "xes")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TaiXe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "ma_tai_xe", nullable = false, length = 50)
    private String maTaiXe;

    @Column(name = "ho_ten", nullable = false, length = 150)
    private String hoTen;

    @Column(name = "so_cccd", nullable = false, length = 20)
    private String soCccd;

    @Column(name = "so_dien_thoai", nullable = false, length = 20)
    private String soDienThoai;

    @Column(name = "kinh_nghiem_nam", nullable = false)
    private Integer kinhNghiemNam;

    @JsonIgnore
    @OneToMany(mappedBy = "taiXe", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Xe> xes = new ArrayList<>();
}
