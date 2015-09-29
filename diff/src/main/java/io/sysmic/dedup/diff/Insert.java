package io.sysmic.dedup.diff;

public class Insert<T> implements Operation {

    public final T value;

    public Insert(T value) {
        this.value = value;
    }

    public String toString() {
        return "Insert(" + value +")";
    }

}
