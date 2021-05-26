package cn.mayu.yugioh.minerva.port.adapter.persistence.packages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_package", uniqueConstraints =
    @UniqueConstraint(columnNames = {"package_name"})
)
public class PackageDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "package_name")
    private String packageName;

    @Column(name = "sale_date")
    private String saleDate;

    @Column(name = "pack_short_name")
    private String packShortName;

    @Column(name = "volume")
    private String volume;

    @Column(name = "cards", columnDefinition = "TEXT")
    private String cards;

    public PackageDO(String packageName, String saleDate, String packShortName, String volume, String cards) {
        this.packageName = packageName;
        this.packShortName = packShortName;
        this.saleDate = saleDate;
        this.cards = cards;
        this.volume = volume;
    }
}
