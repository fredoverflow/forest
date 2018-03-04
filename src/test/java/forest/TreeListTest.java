package forest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class TreeListTest {
    private void assertAddBehavesLikeArrayList(String s) {
        ArrayList<String> arrayList = new ArrayList<>();
        TreeList treeList = TreeList.EMPTY;

        Random rng = new Random();
        int len = s.length();

        for (int i = 0; i < len; ++i) {
            int target = rng.nextInt(i + 1);
            String c = "" + s.charAt(i);

            arrayList.add(target, c);
            treeList = treeList.add(target, c);
        }
        for (int i = 0; i < len; ++i) {
            assertEquals(arrayList.get(i), treeList.get(i));
        }
    }

    @Test
    public void addNone() {
        assertAddBehavesLikeArrayList("");
    }

    @Test
    public void addOne() {
        assertAddBehavesLikeArrayList("a");
    }

    @Test
    public void addTwo() {
        assertAddBehavesLikeArrayList("ab");
    }

    @Test
    public void addThree() {
        assertAddBehavesLikeArrayList("abc");
    }

    @Test
    public void addAlphabet() {
        assertAddBehavesLikeArrayList("abcdefghijklmnopqrstuvwxyz");
    }

    private static final TreeList abcd = TreeList.EMPTY.add(0, "b").add(1, "c").add(0, "a").add(3, "d");

    @Test
    public void removeFront() {
        assertEquals("(b <c d>)", abcd.remove(0).toString());
    }

    @Test
    public void removeBack() {
        assertEquals("(<a b> c)", abcd.remove(3).toString());
    }

    @Test
    public void removeMiddle() {
        assertEquals("(a <c d>)", abcd.remove(1).toString());
        assertEquals("(<a b> d)", abcd.remove(2).toString());
    }

    @Test
    public void sizeEmpty() {
        assertEquals(0, TreeList.EMPTY.size());
    }

    @Test
    public void sizeLeaf() {
        assertEquals(1, new Leaf("a").size());
    }

    @Test
    public void sizeLeft() {
        TreeList t = TreeList.EMPTY;
        t = t.add(0, "a");
        t = t.add(0, "b");
        t = t.add(0, "c");
        t = t.add(0, "d");
        assertEquals(4, t.size());
    }

    @Test
    public void sizeRight() {
        TreeList t = TreeList.EMPTY;
        t = t.add(0, "a");
        t = t.add(1, "b");
        t = t.add(2, "c");
        t = t.add(3, "d");
        assertEquals(4, t.size());
    }

    @Test
    public void sizeBalanced() {
        assertEquals(4, abcd.size());
    }

    @Test
    public void ofNone() {
        assertEquals("()", TreeList.of().toString());
    }

    @Test
    public void ofOne() {
        assertEquals("a", TreeList.of("a").toString());
    }

    @Test
    public void ofTwo() {
        assertEquals("(a b)", TreeList.of("a", "b").toString());
    }

    @Test
    public void ofThree() {
        assertEquals("(a <b c>)", TreeList.of("a", "b", "c").toString());
    }

    @Test
    public void ofFour() {
        assertEquals("((a b) (c d))", TreeList.of("a", "b", "c", "d").toString());
    }

    @Test
    public void ofFive() {
        assertEquals("((a b) (c <d e>))", TreeList.of("a", "b", "c", "d", "e").toString());
    }

    @Test
    public void setAbcd() {
        assertEquals("(<_ b> <c d>)", abcd.set(0, "_").toString());
        assertEquals("(<a _> <c d>)", abcd.set(1, "_").toString());
        assertEquals("(<a b> <_ d>)", abcd.set(2, "_").toString());
        assertEquals("(<a b> <c _>)", abcd.set(3, "_").toString());
    }
}
