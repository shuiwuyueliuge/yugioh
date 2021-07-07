package cn.mayu.yugioh.config.port.adapter.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @description:
 * @author: YgoPlayer
 * @time: 2021/7/1 11:21 上午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_property")
public class PropertyDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "application_name")
    private String applicationName;

    @Column(name = "profile")
    private String profile;
    
    @Column(name = "label")
    private String label;

    @Column(name = "p_key")
    private String pKey;

    @Column(name = "p_value")
    private String pValue;

    @Column(name = "description")
    private String description;

    @Column(name = "modify_time")
    private LocalDateTime modifyTime;

    @Column(insertable = false, updatable = false, name = "create_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createTime;
}
