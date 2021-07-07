package cn.mayu.yugioh.config.domian.property;

import cn.mayu.yugioh.common.basic.domain.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @description: 服务配置信息
 * @author: YgoPlayer
 * @time: 2021/7/1 11:05 上午
 */
@Getter
@ToString
@AllArgsConstructor
public class ApplicationProperty extends Entity {

    private final String applicationName;

    private final PropertyProfile propertyDetail;
}
