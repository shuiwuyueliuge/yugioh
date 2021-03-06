package cn.mayu.yugioh.ceres.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncludeDTO {

    private String packageName;

    private String saleDate;

    private String serial;

    private String rare;

    private String packShortName;

    private String password;
}
