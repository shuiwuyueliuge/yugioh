package cn.mayu.yugioh.pegasus.application.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardInfoCreateCommand {

    private String dataCenter;
}
