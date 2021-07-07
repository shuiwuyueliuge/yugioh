package cn.mayu.yugioh.common.web.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pageable<T> {

    private Long total;

    private List<T> data;
}
