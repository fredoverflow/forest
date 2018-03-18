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

    @Test
    public void insertAB() {
        TreeList a = TreeList.EMPTY.insert(0, "a");
        TreeList ab = a.insert(1, "b");
        assertEquals(2, ab.size());
        assertEquals("a", ab.get(0));
        assertEquals("b", ab.get(1));
    }

    @Test
    public void insertBA() {
        TreeList b = TreeList.EMPTY.insert(0, "b");
        TreeList ab = b.insert(0, "a");
        assertEquals(2, ab.size());
        assertEquals("a", ab.get(0));
        assertEquals("b", ab.get(1));
    }
}
