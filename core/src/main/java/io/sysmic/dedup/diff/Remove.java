package io.sysmic.dedup.diff;

public class Remove<T> implements Operation {

    public final T value;

    public Remove(T value) {
        this.value = value;
    }

    public String toString() {
        return "Remove(" + value +")";
    }

}