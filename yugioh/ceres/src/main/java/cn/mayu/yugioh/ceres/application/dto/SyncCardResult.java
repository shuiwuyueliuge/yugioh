package cn.mayu.yugioh.ceres.application.dto;

import cn.mayu.yugioh.common.facade.minerva.model.CardCreateCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 卡片同步结果
 * @author: YgoPlayer
 * @time: 2021/5/13 5:08 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SyncCardResult {

    private CardCreateCommand cardCreateCommand;

    private Integer cardCount;

    private Integer catchCount;

    private Integer completionRate;
}
