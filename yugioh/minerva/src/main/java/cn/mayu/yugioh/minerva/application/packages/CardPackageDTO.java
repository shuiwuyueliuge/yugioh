package cn.mayu.yugioh.minerva.application.packages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardPackageDTO {

    private String packageName;

    private String packShortName;

    private String saleDate;

    private  String volume;

    private Set<String> cardPasswords;
}
