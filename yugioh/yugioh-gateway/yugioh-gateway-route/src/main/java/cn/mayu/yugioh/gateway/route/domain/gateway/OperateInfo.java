package cn.mayu.yugioh.gateway.route.domain.gateway;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDateTime;

/**
 * @description: gateway操作信息
 * @author: YgoPlayer
 * @time: 2021/7/14 5:00 下午
 */
@Getter
@ToString
@AllArgsConstructor
public class OperateInfo extends ValueObject {

    private final LocalDateTime createTime;

    private final String creator;

    private final LocalDateTime modifyTime;

    private final String modifier;
}
