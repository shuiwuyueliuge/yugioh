package cn.mayu.yugioh.gateway.route.port.adapter.gateway;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @description: gateway 数据库对象
 * @author: YgoPlayer
 * @time: 2021/7/14 5:33 下午
 */
@Data
@Entity
@Table(name = "t_gateway", uniqueConstraints = {@UniqueConstraint(columnNames="name")})
public class GatewayDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "creator", updatable = false)
    private String creator;

    @Column(name = "description")
    private String description;

    @Column(name = "modifier")
    private String modifier;

    @Column(name = "modify_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifyTime;

    @Column(insertable = false, updatable = false, name = "create_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createTime;
}
