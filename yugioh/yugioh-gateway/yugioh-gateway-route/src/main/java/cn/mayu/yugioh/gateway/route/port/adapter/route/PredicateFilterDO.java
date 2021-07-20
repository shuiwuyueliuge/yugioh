package cn.mayu.yugioh.gateway.route.port.adapter.route;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "t_predicate_filter", uniqueConstraints = {@UniqueConstraint(columnNames="type_name")})
public class PredicateFilterDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;
	
    @Column(name = "type")
	private String type;
	
    @Column(name = "type_name")
	private String typeName;

    @Column(name = "description", columnDefinition="TEXT")
    private String description;
	
    @Column(name = "example")
	private String example;
	
    @Column(name = "status", columnDefinition="tinyint default 0 COMMENT '0 默认不能修改，1 自定义可以修改'")
	private int status;

    @Column(name = "modify_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifyTime;
    
    @Column(insertable = false, updatable = false, name = "create_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createTime;
}
