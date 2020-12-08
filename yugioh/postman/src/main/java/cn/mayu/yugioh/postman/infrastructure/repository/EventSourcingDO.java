package cn.mayu.yugioh.postman.infrastructure.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_event_sourcing")
public class EventSourcingDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "event_id")
    private String eventId;

    @Column(name = "occurred_on")
    private String occurredOn;

    @Column(name = "type")
    private String type;

    @Column(name = "body", length = 5000)
    private String body;


    @Column(name = "status", insertable = false, columnDefinition="TINYINT(1) default '0'")
    private Integer status;

    public EventSourcingDO(String eventId, String occurredOn, String type, String body) {
        this.eventId = eventId;
        this.occurredOn = occurredOn;
        this.type = type;
        this.body = body;
    }
}
