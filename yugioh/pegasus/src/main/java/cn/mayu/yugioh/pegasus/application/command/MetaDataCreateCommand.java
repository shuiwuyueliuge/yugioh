package cn.mayu.yugioh.pegasus.application.command;

import lombok.Data;
import java.util.Collection;

/**
 * 元数据创建命令
 */
@Data
public class MetaDataCreateCommand {

    // 数据中心
    private String dataCenter;

    // 数据类型
    private String dataType;

    // 资源地址
    private Collection<String> resources;
}
