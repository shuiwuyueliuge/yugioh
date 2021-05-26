package cn.mayu.yugioh.minerva.domain.aggregate.card;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import cn.mayu.yugioh.minerva.domain.aggregate.packages.CardPackageIdentity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Include extends ValueObject {

    private final CardPackageIdentity packageIdentity;

    private final String serial;

    private final String rare;

    private final String includeTime;

    public static Include init(String includeStr) {
        String[] fields = includeStr.split("\\|");
        CardPackageIdentity packageIdentity = new CardPackageIdentity(fields[0], fields[1]);
        String serial = fields[2];
        String rare = fields[3];
        String includeTime = fields[4];
        return new Include(packageIdentity, serial, rare, includeTime);
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|%s",
                packageIdentity.getPackageName(), packageIdentity.getPackShortName(),
                serial, rare, includeTime);
    }
}
