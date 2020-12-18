package cn.mayu.yugioh.library.application.include.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncludeCreateCommand {

    private String packageName;

    private String saleDate;

    private String serial;

    private String rare;

    private String packShortName;

    private String password;

    private String channel;
}
