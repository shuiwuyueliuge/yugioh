package cn.mayu.yugioh.common.basic.exception.convert;

import com.google.common.collect.Lists;
import java.util.List;

/**
 * 模型转换工厂
 * @param <S> source
 * @param <T> target
 */
public interface ModelConvertFactory<S, T> {

    default T convert(S source) {
        return null;
    }

    default S apply(T target) {
        return null;
    }

    default List<T> convert(List<S> sourceList) {
        List<T> targetList = Lists.newArrayList();
        for(S source : sourceList) {
            targetList.add(convert(source));
        }

        return targetList;
    }

    default List<S> apply(List<T> targetList) {
        List<S> sourceList = Lists.newArrayList();
        for(T target : targetList) {
            sourceList.add(apply(target));
        }

        return sourceList;
    }
}
