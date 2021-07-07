package cn.mayu.yugioh.authorization.port.adapter.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @description: 客户端信息
 * @author: YgoPlayer
 * @time: 2021/6/29 2:27 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_client", uniqueConstraints =
@UniqueConstraint(columnNames = { "client_id" })
)
public class ClientDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "client_id")
    private String clientId; // 客户端id

    @Column(name = "client_secret")
    private String clientSecret; // 客户端访问密匙

    @Column(name = "scope")
    private String scope; // 客户端申请的权限范围

    @Column(name = "resource_ids")
    private String resourceIds; // 客户端所能访问的资源id集合

    @Column(name = "authorized_grant_types")
    private String authorizedGrantTypes; // 授权类型

    @Column(name = "registered_redirect_uris", columnDefinition="TEXT")
    private String registeredRedirectUris; // 客户端重定向URI

    @Column(name = "auto_approve_scopes")
    private String autoApproveScopes;

    @Column(name = "authorities")
    private String authorities; // 客户端权限

    @Column(name = "access_token_validity_seconds")
    private Integer accessTokenValiditySeconds;

    @Column(name = "refresh_token_validity_seconds")
    private Integer refreshTokenValiditySeconds;

    @Column(name = "additional_information", columnDefinition="TEXT")
    private String additionalInformation; // 预留字段，JSON格式

    @Column(name = "modify_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifyTime;

    @Column(insertable = false, updatable = false, name = "create_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createTime;
}
