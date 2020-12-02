package cn.mayu.yugioh.pegasus.port.adapter.datacenter;

import cn.mayu.yugioh.pegasus.domain.aggregate.Card;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * 数据中心接口
 */
public interface DataCenter {

    /**
     * 是否存在
     *
     * @return true 存在， false 不存在
     */
    boolean exists();

    /**
     * 数据中心描述
     *
     * @return 描述信息
     */
    String description();

    /**
     * 数据中心的域名
     *
     * @return 默认 Optional.empty()
     */
    default Optional<String> domain() {
        return Optional.empty();
    }

    /**
     * 数据中心类型
     *
     * @return { @code DataCenterEnum }
     */
    DataCenterEnum type();

    /**
     * 获取卡片信息
     */
    Iterator<List<Card>> obtainCards();
}
