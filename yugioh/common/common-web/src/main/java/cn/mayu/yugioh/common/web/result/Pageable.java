package cn.mayu.yugioh.common.web.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pageable<T> {

    private Long total;

    private Flux<T> data;
}
