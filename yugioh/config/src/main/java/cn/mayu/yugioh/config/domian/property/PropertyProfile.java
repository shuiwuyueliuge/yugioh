package cn.mayu.yugioh.config.domian.property;

import cn.mayu.yugioh.common.basic.domain.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

/**
 * @description: 指定profile和label服务配置
 * @author: YgoPlayer
 * @time: 2021/7/1 11:10 上午
 */
@Getter
@ToString
@AllArgsConstructor
public class PropertyProfile extends Entity {

    private final String profile; // dev，pro，test

    private final String label; // 应用版本分支

    private final List<PropertyDetail> propertyDetails;
}
