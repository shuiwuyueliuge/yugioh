package cn.mayu.yugioh.pegasus.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardInfoDTO {

    private String cardImg;

    private String cardName;

    private String cardUrl;
}
