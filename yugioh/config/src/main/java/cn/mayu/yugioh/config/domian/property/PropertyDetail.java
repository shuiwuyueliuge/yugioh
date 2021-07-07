package cn.mayu.yugioh.config.domian.property;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @description: 配置的具体信息
 * @author: YgoPlayer
 * @time: 2021/7/1 11:14 上午
 */
@Getter
@ToString
@AllArgsConstructor
public class PropertyDetail extends ValueObject {

    private final String key;

    private final String value;

    private final String description;
}
