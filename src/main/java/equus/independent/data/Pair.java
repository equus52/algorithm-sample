package equus.independent.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

@SuppressWarnings("serial")
public class Pair<T> implements Serializable {

    private final T first;

    private final T second;

    public Pair(T value1, T value2) {
        Objects.requireNonNull(value1);
        Objects.requireNonNull(value2);
        first = value1;
        second = value2;
    }

    public T getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

    public Collection<? extends T> getCollection() {
        Collection<T> collection = new HashSet<>();
        collection.add(first);
        collection.add(second);
        return Collections.unmodifiableCollection(collection);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Pair) {
            Pair otherPair = (Pair) o;
            Object otherFirst = otherPair.getFirst();
            Object otherSecond = otherPair.getSecond();
            return (this.first.equals(otherFirst) && this.second.equals(otherSecond))
                            || (this.first.equals(otherSecond) && this.second.equals(otherFirst));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return first.hashCode() + second.hashCode();
    }

    @Override
    public String toString() {
        return "<" + first.toString() + ", " + second.toString() + ">";
    }
}
