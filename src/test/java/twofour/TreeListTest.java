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

    @Test
    public void insertFront() {
        TreeList bc = TreeList.EMPTY.insert(0, "b").insert(1, "c");
        TreeList abc = bc.insert(0, "a");
        assertEquals(3, abc.size());
        assertEquals("a", abc.get(0));
        assertEquals("b", abc.get(1));
        assertEquals("c", abc.get(2));
    }

    @Test
    public void insertMiddle() {
        TreeList ac = TreeList.EMPTY.insert(0, "a").insert(1, "c");
        TreeList abc = ac.insert(1, "b");
        assertEquals(3, abc.size());
        assertEquals("a", abc.get(0));
        assertEquals("b", abc.get(1));
        assertEquals("c", abc.get(2));
    }

    @Test
    public void insertBack() {
        TreeList ab = TreeList.EMPTY.insert(0, "a").insert(1, "b");
        TreeList abc = ab.insert(2, "c");
        assertEquals(3, abc.size());
        assertEquals("a", abc.get(0));
        assertEquals("b", abc.get(1));
        assertEquals("c", abc.get(2));
    }
}
