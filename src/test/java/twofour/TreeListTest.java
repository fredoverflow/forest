package twofour;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TreeListTest {
    @Test
    public void emptyListIsIndeedEmpty() {
        assertEquals(0, TreeList.EMPTY.size());
    }

    @Test
    public void insertA() {
        TreeList a = TreeList.EMPTY.insert(0, "a");
        assertEquals(1, a.size());
        assertEquals("a", a.get(0));
    }
}
