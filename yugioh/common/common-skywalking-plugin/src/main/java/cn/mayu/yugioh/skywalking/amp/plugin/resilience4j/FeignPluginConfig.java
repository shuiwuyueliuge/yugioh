package cn.mayu.yugioh.skywalking.amp.plugin.resilience4j;

import org.apache.skywalking.apm.agent.core.boot.PluginConfig;

public class FeignPluginConfig {
    public static class Plugin {
        @PluginConfig(root = FeignPluginConfig.class)
        public static class Feign {
            /**
             * This config item controls that whether the Feign plugin should collect the http body of the request.
             */
            public static boolean COLLECT_REQUEST_BODY = false;

            /**
             * When either {@link Feign#COLLECT_REQUEST_BODY} is enabled, how many characters to keep and send to the OAP
             * backend, use negative values to keep and send the complete body.
             */
            public static int FILTER_LENGTH_LIMIT = 1024;

            /**
             * When either {@link Feign#COLLECT_REQUEST_BODY} is enabled and content-type start with SUPPORTED_CONTENT_TYPES_PREFIX, collect the body of the request
             */
            public static String SUPPORTED_CONTENT_TYPES_PREFIX = "application/json,text/";
        }
    }
}
