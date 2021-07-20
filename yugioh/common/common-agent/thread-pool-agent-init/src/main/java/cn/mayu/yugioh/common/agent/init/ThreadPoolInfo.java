package cn.mayu.yugioh.common.agent.init;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: YgoPlayer
 * @time: 2021/7/19 5:56 下午
 */
@Data
public class ThreadPoolInfo {

    private static final Map<String, ThreadPoolInfo> POOL_INFO_MAP = new ConcurrentHashMap<>();

    private int corePoolSize;

    private int maximumPoolSize;

    private int poolSize;

    private int largestPoolSize;

    private int activeCount;

    private long completedTaskCount;

    private long taskCount;

    private String poolName;

    public void add() {
        POOL_INFO_MAP.put(poolName, this);
    }

    public static Map<String, ThreadPoolInfo> getAll() {
        return POOL_INFO_MAP;
    }
}
