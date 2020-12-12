package cn.mayu.yugioh.pegasus.infrastructure.repository.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_event")
public class EventDO {

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

    @Column(name = "body", length = 5000)
    private String body;

    public EventDO(Long eventId, Long occurredOn, String type, String body) {
        this.eventId = eventId;
        this.occurredOn = occurredOn;
        this.type = type;
        this.body = body;
    }
}
