package cn.mayu.yugioh.minerva.application.packages.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

/**
 * 卡包创建命令对象
 */
@Data
@AllArgsConstructor
public class PackCreateCommand {

    private String packageName;

    private String saleDate;

    private String packShortName;

    private String volume;

    private List<String> cardPasswords;
}
