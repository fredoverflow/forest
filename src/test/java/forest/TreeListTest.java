package forest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class TreeListTest {
    private void assertInsertionBehavesLikeArrayList(String s) {
        ArrayList<String> arrayList = new ArrayList<>();
        TreeList treeList = TreeList.EMPTY;

        Random rng = new Random();
        int len = s.length();

        for (int i = 0; i < len; ++i) {
            int target = rng.nextInt(i + 1);
            String c = "" + s.charAt(i);

            arrayList.add(target, c);
            treeList = treeList.insert(target, c);
        }
        for (int i = 0; i < len; ++i) {
            assertEquals(arrayList.get(i), treeList.get(i));
        }
    }

    @Test
    public void insertNone() {
        assertInsertionBehavesLikeArrayList("");
    }

    @Test
    public void insertOne() {
        assertInsertionBehavesLikeArrayList("a");
    }

    @Test
    public void insertTwo() {
        assertInsertionBehavesLikeArrayList("ab");
    }

    @Test
    public void insertThree() {
        assertInsertionBehavesLikeArrayList("abc");
    }

    @Test
    public void insertAlphabet() {
        assertInsertionBehavesLikeArrayList("abcdefghijklmnopqrstuvwxyz");
    }

    private static final TreeList abcd = TreeList.EMPTY.insert(0, "b").insert(1, "c").insert(0, "a").insert(3, "d");

    @Test
    public void removeFront() {
        assertEquals("(b (c d))", abcd.remove(0).toString());
    }

    @Test
    public void removeBack() {
        assertEquals("((a b) c)", abcd.remove(3).toString());
    }

    @Test
    public void removeMiddle() {
        assertEquals("(a (c d))", abcd.remove(1).toString());
        assertEquals("((a b) d)", abcd.remove(2).toString());
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
        t = t.insert(0, "a");
        t = t.insert(0, "b");
        t = t.insert(0, "c");
        t = t.insert(0, "d");
        assertEquals(4, t.size());
    }

    @Test
    public void sizeRight() {
        TreeList t = TreeList.EMPTY;
        t = t.insert(0, "a");
        t = t.insert(1, "b");
        t = t.insert(2, "c");
        t = t.insert(3, "d");
        assertEquals(4, t.size());
    }

    @Test
    public void sizeBalanced() {
        assertEquals(4, abcd.size());
    }
}
