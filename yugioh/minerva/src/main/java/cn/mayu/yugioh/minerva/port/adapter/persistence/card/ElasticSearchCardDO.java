package cn.mayu.yugioh.minerva.port.adapter.persistence.card;

import cn.mayu.yugioh.minerva.domain.aggregate.card.Include;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Document(indexName = "ygo_card", shards= 1, replicas = 0)
public class ElasticSearchCardDO {

    @Id
    private String password;

    private String name;

    private String desc;

    private String typeVal;

    private String link;

    private String def;

    private String pend;

    private String race;

    private String attribute;

    private String level;

    private String atk;

    private List<String> typeSt;

    private List<String> linkArrow;

    private List<Include> include;

    private String adjust;
}
