package cn.mayu.yugioh.library.domain.service;

import cn.mayu.yugioh.library.domain.aggregate.card.CardIdentity;
import cn.mayu.yugioh.library.domain.aggregate.include.Include;
import cn.mayu.yugioh.library.domain.aggregate.include.IncludeIdentity;
import cn.mayu.yugioh.library.domain.aggregate.packages.CardPackageIdentity;
import org.springframework.stereotype.Component;

@Component
public class IncludeService {

    public Include createInclude(String packageName, String saleDate, String serial, String rare, String packShortName, String password) {
        IncludeIdentity includeIdentity = new IncludeIdentity();
        CardIdentity cardIdentity = new CardIdentity(password);
        CardPackageIdentity cardPackageIdentity = new CardPackageIdentity(packageName, packShortName);
        return new Include(includeIdentity, cardPackageIdentity, cardIdentity, serial, rare, saleDate);
    }
}
