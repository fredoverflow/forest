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
        TreeList a = TreeList.of("a");
        assertEquals(1, a.size());
        assertEquals("a", a.get(0));
    }

    @Test
    public void insertAB() {
        TreeList a = TreeList.of("a");
        TreeList ab = a.insert(1, "b");
        assertEquals(2, ab.size());
        assertEquals("a", ab.get(0));
        assertEquals("b", ab.get(1));
    }

    @Test
    public void insertBA() {
        TreeList b = TreeList.of("b");
        TreeList ab = b.insert(0, "a");
        assertEquals(2, ab.size());
        assertEquals("a", ab.get(0));
        assertEquals("b", ab.get(1));
    }

    @Test
    public void insertThree0() {
        TreeList bc = TreeList.of("b", "c");
        TreeList abc = bc.insert(0, "a");
        assertEquals(3, abc.size());
        assertEquals("a", abc.get(0));
        assertEquals("b", abc.get(1));
        assertEquals("c", abc.get(2));
    }

    @Test
    public void insertThree1() {
        TreeList ac = TreeList.of("a", "c");
        TreeList abc = ac.insert(1, "b");
        assertEquals(3, abc.size());
        assertEquals("a", abc.get(0));
        assertEquals("b", abc.get(1));
        assertEquals("c", abc.get(2));
    }

    @Test
    public void insertThree2() {
        TreeList ab = TreeList.of("a", "b");
        TreeList abc = ab.insert(2, "c");
        assertEquals(3, abc.size());
        assertEquals("a", abc.get(0));
        assertEquals("b", abc.get(1));
        assertEquals("c", abc.get(2));
    }
}
