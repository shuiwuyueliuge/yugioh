package cn.mayu.yugioh.pegasus.infrastructure.datacenter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Data
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "data.center")
public class DataCenterConfiguration {

    List<DataCenterProperty> property;

    @Data
    public static class DataCenterProperty {

        private String dataType;

        private String domain;

        private String description;

        private boolean exists = Boolean.FALSE;
    }
}
