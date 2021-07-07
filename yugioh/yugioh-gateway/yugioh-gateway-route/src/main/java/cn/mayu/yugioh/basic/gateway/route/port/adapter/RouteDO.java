package cn.mayu.yugioh.basic.gateway.route.port.adapter;

import cn.mayu.yugioh.basic.gateway.route.domain.RouteStatus;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "t_route", uniqueConstraints = {@UniqueConstraint(columnNames="route_id")})
public class RouteDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;
	
    @Column(name = "route_id")
	private String routeId;
	
    @Column(name = "uri")
	private String uri;
	
    @Column(name = "sort")
	private Integer sort;
	
    @Column(name = "state", columnDefinition="tinyint default 0")
	private RouteStatus routeStatus;

    @Column(name = "modify_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifyTime;
    
    @Column(insertable = false, updatable = false, name = "create_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createTime;

    @Column(name = "predicates", columnDefinition="TEXT")
    private String predicates;

    @Column(name = "filters", columnDefinition="TEXT")
    private String filters;
}
