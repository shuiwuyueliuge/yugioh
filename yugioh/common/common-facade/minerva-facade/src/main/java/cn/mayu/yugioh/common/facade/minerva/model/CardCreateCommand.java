package cn.mayu.yugioh.common.facade.minerva.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * 卡片创建参数对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardCreateCommand {

    private List<CardInfo> cardInfos;
}
