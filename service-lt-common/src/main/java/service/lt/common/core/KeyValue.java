package service.lt.common.core;

import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Key Value 的键值对
 *
 * @author 芋道源码
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class KeyValue<K, V> {

    private K key;
    private V value;

}
