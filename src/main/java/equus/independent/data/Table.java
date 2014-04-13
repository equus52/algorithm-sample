package equus.independent.data;

import java.util.HashMap;

public class Table<R, C, V> {

    private final HashMap<R, HashMap<C, V>> table = new HashMap<>();

    public V put(R rowKey, C columnKey, V value) {
        if (!table.containsKey(rowKey)) {
            HashMap<C, V> map = new HashMap<>();
            table.put(rowKey, map);
        }
        return table.get(rowKey).put(columnKey, value);
    }

    public V get(R rowKey, C columnKey) {
        if (!table.containsKey(rowKey)) {
            return null;
        }
        return table.get(rowKey).get(columnKey);

    }

    public V remove(R rowKey, C columnKey) {
        if (!table.containsKey(rowKey)) {
            return null;
        }
        return table.get(rowKey).remove(columnKey);

    }
}
