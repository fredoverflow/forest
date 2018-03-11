package forest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TreeListTest {
    private void assertAddBehavesLikeArrayList(String... values) {
        ArrayList<String> arrayList = new ArrayList<>();
        TreeList treeList = TreeList.EMPTY;

        Random rng = new Random();
        int len = values.length;

        for (int i = 0; i < len; ++i) {
            int target = rng.nextInt(i + 1);
            arrayList.add(target, values[i]);
            treeList = treeList.add(target, values[i]);
        }
        for (int i = 0; i < len; ++i) {
            assertEquals(arrayList.get(i), treeList.get(i));
        }
    }

    @Test
    public void addNone() {
        assertAddBehavesLikeArrayList();
    }

    @Test
    public void addOne() {
        assertAddBehavesLikeArrayList("a");
    }

    @Test
    public void addTwo() {
        assertAddBehavesLikeArrayList("a", "b");
    }

    @Test
    public void addThree() {
        assertAddBehavesLikeArrayList("a", "b", "c");
    }

    @Test
    public void addAlphabet() {
        assertAddBehavesLikeArrayList(alphabet);
    }

    private static final String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    private static final TreeList abcd = TreeList.of("a", "b", "c", "d");

    @Test
    public void removeFront() {
        assertEquals("(<b c> d)", abcd.remove(0).toString());
    }

    @Test
    public void removeBack() {
        assertEquals("(a <b c>)", abcd.remove(3).toString());
    }

    @Test
    public void removeMiddle() {
        assertEquals("(<a c> d)", abcd.remove(1).toString());
        assertEquals("(a <b d>)", abcd.remove(2).toString());
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
        assertEquals("((_ b) (c d))", abcd.set(0, "_").toString());
        assertEquals("((a _) (c d))", abcd.set(1, "_").toString());
        assertEquals("((a b) (_ d))", abcd.set(2, "_").toString());
        assertEquals("((a b) (c _))", abcd.set(3, "_").toString());
    }

    @Test
    public void stressTestRemove() {
        TreeList a = TreeList.of(alphabet);
        a.blackHeight();
        a.checkRed();
        Random rng = new Random(0);
        for (int i = 0; i < 10_000; ++i) {
            TreeList t = a;
            for (int n = t.size(); n > 0; --n) {
                TreeList u = t.remove(rng.nextInt(n));
                u.blackHeight();
                u.checkRed();
                t = u;
            }
        }
    }

    @Test
    public void forEach() {
        StringBuilder sb = new StringBuilder(26);
        TreeList.of(alphabet).forEach(sb::append);
        assertEquals("abcdefghijklmnopqrstuvwxyz", sb.toString());
    }

    @Test
    public void forEmpty() {
        for (String s : TreeList.EMPTY) {
            fail("TreeList.EMPTY loop body entered");
        }
    }

    @Test
    public void forLeaf() {
        StringBuilder sb = new StringBuilder(4);
        for (String s : new Leaf("leaf")) {
            sb.append(s);
        }
        assertEquals("leaf", sb.toString());
    }

    @Test
    public void forAlphabet() {
        StringBuilder sb = new StringBuilder(26);
        for (String s : TreeList.of(alphabet)) {
            sb.append(s);
        }
        assertEquals("abcdefghijklmnopqrstuvwxyz", sb.toString());
    }
}
