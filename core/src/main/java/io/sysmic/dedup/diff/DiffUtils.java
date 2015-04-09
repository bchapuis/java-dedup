package io.sysmic.dedup.diff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class DiffUtils {

    private DiffUtils() {}

    public interface Operation {}

    public static class Equals implements Operation {

        public final int lenght;

        public Equals(int length) {
            this.lenght = length;
        }

        public String toString() {
            return "Equals(" + lenght +")";
        }

    }

    public static class Insert<T> implements Operation {

        public final T value;

        public Insert(T value) {
            this.value = value;
        }

        public String toString() {
            return "Insert(" + value +")";
        }

    }

    public static class Remove<T> implements Operation {

        public final T value;

        public Remove(T value) {
            this.value = value;
        }

        public String toString() {
            return "Remove(" + value +")";
        }

    }

    private static <T> int[][] matrix(List<T> x, List<T> y) {
        int m = x.size();
        int n = y.size();
        int[][] c = new int[m+1][n+1];
        for (int i = 0; i <= m; i++)
            c[i][0] = 0;
        for (int j = 0; j <= n; j++)
            c[0][j] = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (x.get(i).equals(y.get(j))) {
                    c[i + 1][j + 1] = c[i][j] + 1;
                } else {
                    c[i + 1][j + 1] = Math.max(c[i + 1][j], c[i][j + 1]);
                }
            }
        }
        return c;
    }

    private static <T> List<Operation> diff(int[][] c, List<T> x, List<T> y) {
        int i = x.size();
        int j = x.size();
        int l = 0;
        List<Operation> diff = new ArrayList<Operation>();
        while(i > 0 || j > 0) {
            if (i > 0 && j > 0 && x.get(i - 1) == y.get(j - 1)) {
                i -= 1;
                j -= 1;
                l += 1;
            } else if (j > 0 && (i == 0 || c[i][j-1] >= c[i-1][j])) {
                if (l > 0) {
                    diff.add(new Equals(l));
                    l = 0;
                }
                diff.add(new Insert(y.get(j-1)));
                j -= 1;
            } else if (i > 0 && (j == 0 || c[i][j-1] < c[i-1][j])) {
                if (l > 0) {
                    diff.add(new Equals(l));
                    l = 0;
                }
                diff.add(new Remove(x.get(i-1)));
                i -= 1;
            } else {
                i -= 1;
                j -= 1;
            }
        }
        if (l > 0) {
            diff.add(new Equals(l));
        }
        return diff;
    }

    public static <T> List<Operation> diff(List<T> x, List<T> y) {
        List<Operation> diff = diff(matrix(x, y), x, y);
        Collections.reverse(diff);
        return diff;
    }

    public static <T> List<T> patch(List<Operation> diff, List<T> x) {
        Iterator<T> it = x.iterator();
        List<T> y = new ArrayList();
        for (Operation operation : diff) {
            if (operation instanceof Equals) {
                Equals equals = (Equals) operation;
                for (int i = 0; i < equals.lenght; i ++) {
                    T elem = it.next();
                    y.add(elem);
                }
            }
            if (operation instanceof Insert) {
                Insert<T> insert = (Insert) operation;
                y.add(insert.value);
            }
            if (operation instanceof Remove) {
                it.next();
            }
        }
        return y;
    }

    public static List<Operation> reverse(List<Operation> diff) {
        List<Operation> invert = new ArrayList<Operation>();
        for (Operation operation: diff) {
            if (operation instanceof Equals) {
                invert.add(operation);
            }
            if (operation instanceof Insert) {
                Insert insert = (Insert) operation;
                invert.add(new Remove(insert.value));
            }
            if (operation instanceof Remove) {
                Remove remove = (Remove) operation;
                invert.add(new Insert(remove.value));
            }
        }
        return invert;
    }

}
