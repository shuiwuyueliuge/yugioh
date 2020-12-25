package cn.mayu.yugioh.library.infrastructure.repository.include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_include", uniqueConstraints =
    @UniqueConstraint(columnNames = {"package_name", "password", "rare"})
)
public class IncludeDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "package_name")
    private String packageName;

    @Column(name = "sale_date")
    private String saleDate;

    @Column(name = "serial")
    private String serial;

    @Column(name = "rare")
    private String rare;

    @Column(name = "pack_short_name")
    private String packShortName;

    @Column(name = "password")
    private String password;

    public IncludeDO(String packageName, String saleDate, String serial, String rare, String packShortName, String password) {
        this.packageName = packageName;
        this.packShortName = packShortName;
        this.saleDate =saleDate;
        this.serial = serial;
        this.rare = rare;
        this.password = password;
    }
}
