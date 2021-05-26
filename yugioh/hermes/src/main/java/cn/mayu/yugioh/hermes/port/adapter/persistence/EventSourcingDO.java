package cn.mayu.yugioh.hermes.port.adapter.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

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
    private Long eventId;

    @Column(name = "occurred_on")
    private Long occurredOn;

    @Column(name = "type")
    private String type;

    @Column(name = "payload", columnDefinition="TEXT")
    private String payload;

    @Column(name = "status")
    private Integer status;

    @Column(name = "routing_key")
    private String routingKey;

    public EventSourcingDO(Long eventId, Long occurredOn, String type, String payload, Integer status, String routingKey) {
        this.eventId = eventId;
        this.occurredOn = occurredOn;
        this.type = type;
        this.payload = payload;
        this.status = status;
        this.routingKey = routingKey;
    }
}
