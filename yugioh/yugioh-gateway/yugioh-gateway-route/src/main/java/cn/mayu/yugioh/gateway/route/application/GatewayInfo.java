package cn.mayu.yugioh.gateway.route.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * @description: gateway信息详情
 * @author: YgoPlayer
 * @time: 2021/7/14 4:58 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GatewayInfo {

    private String name;

    private String description;

    private LocalDateTime createTime;

    private String creator;

    private LocalDateTime modifyTime;

    private String modifier;
}
