package cn.mayu.yugioh.library.domain.aggregate.packages;

import cn.mayu.yugioh.common.basic.domain.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class CardPackage extends Entity {

    private CardPackageIdentity packageIdentity;

    private String saleDate;
}
