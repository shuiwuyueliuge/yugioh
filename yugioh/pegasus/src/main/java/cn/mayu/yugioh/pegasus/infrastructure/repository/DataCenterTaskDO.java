package cn.mayu.yugioh.pegasus.infrastructure.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_task", uniqueConstraints =
    @UniqueConstraint(columnNames = { "data_center", "start_time", "type" })
)
public class DataCenterTaskDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "data_center")
    private String dataCenter;

    @Column(name = "start_time")
    private long startTime;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private String status;

    @Column(name = "operate_channel")
    private String operateChannel;

    @Column(name = "end_time")
    private long endTime;

    @Column(name = "parent_task")
    private String parentTask;

    public DataCenterTaskDO(String dataCenter, long startTime, String type,
                            String status, String operateChannel, long endTime,
                            String parentTask) {
        this.dataCenter = dataCenter;
        this.startTime = startTime;
        this.type = type;
        this.status = status;
        this.operateChannel = operateChannel;
        this.endTime = endTime;
        this.parentTask = parentTask;
    }
}