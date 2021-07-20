package cn.mayu.yugioh.gateway.route.domain.route;

import cn.mayu.yugioh.common.basic.domain.ValueObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

/**
 * @description: 路由详细信息
 * @author: YgoPlayer
 * @time: 2021/6/24 1:37 下午
 */
@Data
@AllArgsConstructor
public class RouteInfo extends ValueObject {

    private final String id;

    private final String uri;

    private final int order;

    /**
     * "predicates": [{
     * 			"name": "Path",
     * 			"args": {
     * 				"_genkey_0": "/**"
     *                        }* 		}, {
     * 			"name": "Method",
     * 			"args": {
     * 				"_genkey_0": "POST",
     * 				"_genkey_1": "GET"
     *            }
     *        }, {
     * 			"name": "After",
     * 			"args": {
     * 				"_genkey_0": "2017-01-20T17:42:47.789-07:00[America/Denver]"
     *            }
     *        }, {
     * 			"name": "Before",
     * 			"args": {
     * 				"_genkey_0": "2021-01-20T17:42:47.789-07:00[America/Denver]"
     *            }
     *        }, {
     * 			"name": "Between",
     * 			"args": {
     * 				"_genkey_0": "2017-01-20T17:42:47.789-07:00[America/Denver]",
     * 				"_genkey_1": "2017-01-21T17:42:47.789-07:00[America/Denver]"
     *            }
     *        }, {
     * 			"name": "Cookie",
     * 			"args": {
     * 				"_genkey_0": "chocolate",
     * 				"_genkey_1": "ch.p"
     *            }
     *        }, {
     * 			"name": "Header",
     * 			"args": {
     * 				"_genkey_0": "X-Request-Id",
     * 				"_genkey_1": "\\d+"
     *            }
     *        }, {
     * 			"name": "Host",
     * 			"args": {
     * 				"_genkey_0": "**.somehost.org",
     * 				"_genkey_1": "**.anotherhost.org"
     *            }
     *        }, {
     * 			"name": "Query",
     * 			"args": {
     * 				"_genkey_0": "red",
     * 				"_genkey_1": "gree."
     *            }
     *        }, {
     * 			"name": "RemoteAddr",
     * 			"args": {
     * 				"_genkey_0": "192.168.1.1/24"
     *            }
     *        }, {
     * 			"name": "Weight",
     * 			"args": {
     * 				"_genkey_0": "group1",
     * 				"_genkey_1": "8"
     *            }
     *        }]
     */
    private final List<Rule> predicates;

    /**
     * "filters": [{
     * 			"name": "StripPrefix",
     * 			"args": {
     * 				"_genkey_0": "1"
     *                        }* 		}]
     */
    private final List<Rule> filters;
}
