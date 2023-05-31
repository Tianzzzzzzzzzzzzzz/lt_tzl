package service.lt.common.util.stream;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Stream 公共方法
 * @author hm
 * @date 2022/12/2 14:05
 */
public class StreamUtil {
    /**
    * Java 8 stream 方法 按照某个字段去重
    * https://stackoverflow.com/questions/23699371/java-8-distinct-by-property
    * @param keyExtractor
    * @param <T>
    * @return
    */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

}