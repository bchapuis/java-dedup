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
        List<DiffUtils.Operation> diff = DiffUtils.diff(a, b);
        List<String> c = DiffUtils.patch(diff, a);
        assertEquals(b, c);
    }

}
