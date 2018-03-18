package twofour;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TreeListTest {
    @Test
    public void emptyListIsIndeedEmpty() {
        assertEquals(0, TreeList.EMPTY.size());
        assertEquals("()", TreeList.EMPTY.toString());
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
        assertEquals("(a b)", ab.toString());
    }

    @Test
    public void insertBA() {
        TreeList b = TreeList.of("b");
        TreeList ab = b.insert(0, "a");
        assertEquals("(a b)", ab.toString());
    }

    @Test
    public void insertThree0() {
        TreeList bc = TreeList.of("b", "c");
        TreeList abc = bc.insert(0, "a");
        assertEquals("(a b c)", abc.toString());
    }

    @Test
    public void insertThree1() {
        TreeList ac = TreeList.of("a", "c");
        TreeList abc = ac.insert(1, "b");
        assertEquals("(a b c)", abc.toString());
    }

    @Test
    public void insertThree2() {
        TreeList ab = TreeList.of("a", "b");
        TreeList abc = ab.insert(2, "c");
        assertEquals("(a b c)", abc.toString());
    }

    @Test
    public void insertFour0() {
        TreeList bcd = TreeList.of("b", "c", "d");
        TreeList abcd = bcd.insert(0, "a");
        assertEquals("((a b) (c d))", abcd.toString());
    }

    @Test
    public void insertFour1() {
        TreeList acd = TreeList.of("a", "c", "d");
        TreeList abcd = acd.insert(1, "b");
        assertEquals("((a b) (c d))", abcd.toString());
    }

    @Test
    public void insertFour2() {
        TreeList abd = TreeList.of("a", "b", "d");
        TreeList abcd = abd.insert(2, "c");
        assertEquals("((a b) (c d))", abcd.toString());
    }

    @Test
    public void insertFour3() {
        TreeList abc = TreeList.of("a", "b", "c");
        TreeList abcd = abc.insert(3, "d");
        assertEquals("((a b) (c d))", abcd.toString());
    }

    private static final TreeList abcd = TreeList.of("a", "b", "c", "d");

    @Test
    public void insertFive0() {
        TreeList _abcd = abcd.insert(0, "_");
        assertEquals("((_ a b) (c d))", _abcd.toString());
    }

    @Test
    public void insertFive1() {
        TreeList a_bcd = abcd.insert(1, "_");
        assertEquals("((a _ b) (c d))", a_bcd.toString());
    }

    @Test
    public void insertFive2() {
        TreeList ab_cd = abcd.insert(2, "_");
        assertEquals("((a b) (_ c d))", ab_cd.toString());
    }

    @Test
    public void insertFive3() {
        TreeList abc_d = abcd.insert(3, "_");
        assertEquals("((a b) (c _ d))", abc_d.toString());
    }

    @Test
    public void insertFive4() {
        TreeList abcd_ = abcd.insert(4, "_");
        assertEquals("((a b) (c d _))", abcd_.toString());
    }
}
