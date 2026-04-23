package com.quanlyxe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "xe",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_xe_ma_xe", columnNames = "ma_xe"),
                @UniqueConstraint(name = "uk_xe_bien_so", columnNames = "bien_so")
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"lichTrinhs", "loaiXe", "taiXe", "nhaXe"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Xe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "ma_xe", nullable = false, length = 50)
    private String maXe;

    @Column(name = "hang_san_xuat", nullable = false, length = 100)
    private String hangSanXuat;

    @Column(name = "bien_so", nullable = false, length = 30)
    private String bienSo;

    @Column(name = "han_kiem_dinh", nullable = false)
    private LocalDate hanKiemDinh;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loai_xe_id", nullable = false)
    private LoaiXe loaiXe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tai_xe_id", nullable = false)
    private TaiXe taiXe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nha_xe_id", nullable = false)
    private NhaXe nhaXe;

    @JsonIgnore
    @OneToMany(mappedBy = "xe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("ngayGioXuatBen DESC")
    @Builder.Default
    private List<LichTrinh> lichTrinhs = new ArrayList<>();
}
