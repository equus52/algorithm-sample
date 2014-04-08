package equus.independent.data;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class LinkGroupBuilder<K> {

    private final Map<K, Set<K>> sets = new HashMap<>();

    @SuppressWarnings("unchecked")
    public void addLink(K... keys) {
        Set<K> foundGroup = null;
        for (K key : keys) {
            K currentGroupKey = null;
            Set<K> currentGroup = null;
            for (Map.Entry<K, Set<K>> entry : sets.entrySet()) {
                K groupKey = entry.getKey();
                Set<K> set = entry.getValue();
                if (set.contains(key)) {
                    currentGroupKey = groupKey;
                    currentGroup = set;
                    break;
                }
            }
            if (currentGroup == null) {
                currentGroupKey = key;
                currentGroup = new HashSet<>();
                currentGroup.add(key);
                sets.put(currentGroupKey, currentGroup);
            }
            if (foundGroup == null) {
                foundGroup = currentGroup;
            } else {
                foundGroup.addAll(currentGroup);
                sets.remove(currentGroupKey);
            }
        }
    }

    public Collection<Collection<K>> build() {
        Collection<Collection<K>> collection = new HashSet<>();
        for (Set<K> set : sets.values()) {
            collection.add(Collections.unmodifiableCollection(set));
        }
        return Collections.unmodifiableCollection(collection);
    }
}
