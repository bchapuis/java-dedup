package io.sysmic.dedup.diff;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

public class DiffUtilsTest {

    @Test
    public void mustDiffAndPatch() {
        List<String> a = Arrays.asList("a", "b", "c", "d", "e");
        List<String> b = Arrays.asList("a", "f", "g", "d", "e");
        List<Operation> diff = Diff.diff(a, b);
        List<String> c = Diff.patch(diff, a);
        assertEquals(b, c);
    }

}
