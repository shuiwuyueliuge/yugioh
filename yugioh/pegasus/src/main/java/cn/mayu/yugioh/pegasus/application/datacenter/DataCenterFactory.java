package cn.mayu.yugioh.pegasus.application.datacenter;

/**
 * 数据中心接口
 */
public interface DataCenterFactory {

    default CardData getCardData() {
        return null;
    }

    default IncludeData getIncludeData() {
        return null;
    }

    default PackageData getPackageData() {
        return null;
    }

    default LimitData getLimitData() {
        return null;
    }

    /**
     * 数据类型
     * @return { @code DataCenterEnum }
     */
    DataCenterEnum type();

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
    String domain();
}
