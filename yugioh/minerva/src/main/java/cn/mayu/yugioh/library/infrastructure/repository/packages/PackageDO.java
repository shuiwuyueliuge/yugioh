package cn.mayu.yugioh.library.infrastructure.repository.packages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_package")
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

    public PackageDO(String packageName, String saleDate, String packShortName) {
        this.packageName = packageName;
        this.packShortName = packShortName;
        this.saleDate = saleDate;
    }
}
