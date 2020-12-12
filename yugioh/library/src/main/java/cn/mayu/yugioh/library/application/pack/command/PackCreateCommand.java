package cn.mayu.yugioh.library.application.pack.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackCreateCommand {

    private String packageName;

    private String saleDate;

    private String packShortName;
}
