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
    public void insertOne() {
        TreeList a = TreeList.EMPTY.insert(0, "a");
        assertEquals("(a)", a.toString());
    }

    private static final TreeList a = TreeList.of("a");

    @Test
    public void insertTwo0() {
        TreeList _a = a.insert(0, "_");
        assertEquals("(_ a)", _a.toString());
    }

    @Test
    public void insertTwo1() {
        TreeList a_ = a.insert(1, "_");
        assertEquals("(a _)", a_.toString());
    }

    private static final TreeList ab = TreeList.of("a", "b");

    @Test
    public void insertThree0() {
        TreeList _ab = ab.insert(0, "_");
        assertEquals("(_ a b)", _ab.toString());
    }

    @Test
    public void insertThree1() {
        TreeList a_b = ab.insert(1, "_");
        assertEquals("(a _ b)", a_b.toString());
    }

    @Test
    public void insertThree2() {
        TreeList ab_ = ab.insert(2, "_");
        assertEquals("(a b _)", ab_.toString());
    }

    private static final TreeList abc = TreeList.of("a", "b", "c");

    @Test
    public void insertFour0() {
        TreeList _abc = abc.insert(0, "_");
        assertEquals("((_ a) (b c))", _abc.toString());
    }

    @Test
    public void insertFour1() {
        TreeList a_bc = abc.insert(1, "_");
        assertEquals("((a _) (b c))", a_bc.toString());
    }

    @Test
    public void insertFour2() {
        TreeList ab_c = abc.insert(2, "_");
        assertEquals("((a b) (_ c))", ab_c.toString());
    }

    @Test
    public void insertFour3() {
        TreeList abc_ = abc.insert(3, "_");
        assertEquals("((a b) (c _))", abc_.toString());
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
        assertEquals("((a b _) (c d))", ab_cd.toString());
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

    private static TreeList abcde = TreeList.of("a", "b", "c", "d", "e");

    @Test
    public void insertSix0() {
        TreeList _abcde = abcde.insert(0, "_");
        assertEquals("((_ a b) (c d e))", _abcde.toString());
    }

    @Test
    public void insertSix1() {
        TreeList a_bcde = abcde.insert(1, "_");
        assertEquals("((a _ b) (c d e))", a_bcde.toString());
    }

    @Test
    public void insertSix2() {
        TreeList ab_cde = abcde.insert(2, "_");
        assertEquals("((a b _) (c d e))", ab_cde.toString());
    }

    @Test
    public void insertSix3() {
        TreeList abc_de = abcde.insert(3, "_");
        assertEquals("((a b) (c _) (d e))", abc_de.toString());
    }

    @Test
    public void insertSix4() {
        TreeList abcd_e = abcde.insert(4, "_");
        assertEquals("((a b) (c d) (_ e))", abcd_e.toString());
    }

    @Test
    public void insertSix5() {
        TreeList abcde_ = abcde.insert(5, "_");
        assertEquals("((a b) (c d) (e _))", abcde_.toString());
    }

    private static TreeList abcdef = TreeList.of("a", "b", "c", "d", "e", "f");

    @Test
    public void insertSeven0() {
        TreeList _abcdef = abcdef.insert(0, "_");
        assertEquals("((_ a b) (c d) (e f))", _abcdef.toString());
    }

    @Test
    public void insertSeven1() {
        TreeList a_bcdef = abcdef.insert(1, "_");
        assertEquals("((a _ b) (c d) (e f))", a_bcdef.toString());
    }

    @Test
    public void insertSeven2() {
        TreeList ab_cdef = abcdef.insert(2, "_");
        assertEquals("((a b _) (c d) (e f))", ab_cdef.toString());
    }

    @Test
    public void insertSeven3() {
        TreeList abc_def = abcdef.insert(3, "_");
        assertEquals("((a b) (c _ d) (e f))", abc_def.toString());
    }

    @Test
    public void insertSeven4() {
        TreeList abcd_ef = abcdef.insert(4, "_");
        assertEquals("((a b) (c d _) (e f))", abcd_ef.toString());
    }

    @Test
    public void insertSeven5() {
        TreeList abcde_f = abcdef.insert(5, "_");
        assertEquals("((a b) (c d) (e _ f))", abcde_f.toString());
    }

    @Test
    public void insertSeven6() {
        TreeList abcdef_ = abcdef.insert(6, "_");
        assertEquals("((a b) (c d) (e f _))", abcdef_.toString());
    }
}
