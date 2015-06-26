package io.sysmic.dedup.diff;

public class Equal implements Operation {

    public final int lenght;

    public Equal(int length) {
        this.lenght = length;
    }

    public String toString() {
        return "Equals(" + lenght +")";
    }

}