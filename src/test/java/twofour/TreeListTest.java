package twofour;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import static org.junit.Assert.*;
import static twofour.TreeList.of;

public class TreeListTest {
    @Test
    public void emptyListIsIndeedEmpty() {
        assertEquals(0, TreeList.EMPTY.size());
        assertEquals("()", TreeList.EMPTY.toString());
    }

    @Test
    public void setLeaf1() {
        TreeList t = of("a");
        assertEquals("(_)", t.set(0, "_").toString());
    }

    @Test
    public void setLeaf2() {
        TreeList t = of("a", "b");
        assertEquals("(_ b)", t.set(0, "_").toString());
        assertEquals("(a _)", t.set(1, "_").toString());
    }

    @Test
    public void setLeaf3() {
        TreeList t = of("a", "b", "c");
        assertEquals("(_ b c)", t.set(0, "_").toString());
        assertEquals("(a _ c)", t.set(1, "_").toString());
        assertEquals("(a b _)", t.set(2, "_").toString());
    }

    @Test
    public void setInternal2() {
        TreeList t = new Internal2(of("a"), of("b"));
        assertEquals("((_) (b))", t.set(0, "_").toString());
        assertEquals("((a) (_))", t.set(1, "_").toString());
    }

    @Test
    public void setInternal3() {
        TreeList t = new Internal3(of("a"), of("b"), of("c"));
        assertEquals("((_) (b) (c))", t.set(0, "_").toString());
        assertEquals("((a) (_) (c))", t.set(1, "_").toString());
        assertEquals("((a) (b) (_))", t.set(2, "_").toString());
    }

    @Test
    public void setInternal4() {
        TreeList t = new Internal4(of("a"), of("b"), of("c"), of("d"));
        assertEquals("((_) (b) (c) (d))", t.set(0, "_").toString());
        assertEquals("((a) (_) (c) (d))", t.set(1, "_").toString());
        assertEquals("((a) (b) (_) (d))", t.set(2, "_").toString());
        assertEquals("((a) (b) (c) (_))", t.set(3, "_").toString());
    }

    @Test
    public void insertGermanKeys() {
        TreeList keys = TreeList.EMPTY;

        keys = keys.insert(0, "a"); // a
        keys = keys.insert(1, "b"); // ab
        keys = keys.insert(1, "c"); // acb
        keys = keys.insert(1, "d"); // adcb
        assertEquals("((a d) (c b))", keys.toString());

        keys = keys.insert(0, "e"); // eadcb
        keys = keys.insert(3, "f"); // eadfcb
        keys = keys.insert(4, "g"); // eadfgcb
        keys = keys.insert(5, "h"); // eadfghcb
        keys = keys.insert(1, "i"); // eiadfghcb
        keys = keys.insert(7, "j"); // eiadfghjcb
        keys = keys.insert(8, "k"); // eiadfghjkcb
        assertEquals("(((e i) (a d)) ((f g h) (j k) (c b)))", keys.toString());

        keys = keys.insert(9, "l"); // eiadfghjklcb
        keys = keys.insert(12, "m"); //eiadfghjklcbm
        keys = keys.insert(12, "n"); //eiadfghjklcbnm
        keys = keys.insert(2, "o"); // eioadfghjklcbnm
        keys = keys.insert(3, "p"); // eiopadfghjklcbnm
        keys = keys.insert(0, "q"); // qeiopadfghjklcbnm
        keys = keys.insert(2, "r"); // qeriopadfghjklcbnm
        keys = keys.insert(7, "s"); // qeriopasdfghjklcbnm
        keys = keys.insert(3, "t"); // qertiopasdfghjklcbnm
        keys = keys.insert(4, "u"); // qertuiopasdfghjklcbnm
        keys = keys.insert(18, "v"); //qertuiopasdfghjklcvbnm
        keys = keys.insert(1, "w"); // qwertuiopasdfghjklcvbnm
        keys = keys.insert(18, "x"); //qwertuiopasdfghjklxcvbnm
        keys = keys.insert(18, "y"); //qwertuiopasdfghjklyxcvbnm
        keys = keys.insert(5, "z"); // qwertzuiopasdfghjklyxcvbnm
        assertEquals("(((q w) (e r) (t z u) (i o)) ((p a) (s d)) ((f g h) (j k) (l y x)) ((c v b) (n m)))", keys.toString());

        assertEquals(26, keys.size());
        assertEquals("q", keys.get(0));
        assertEquals("m", keys.get(25));
    }

    private static final String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    @Test
    public void stressTestAlphabet() {
        for (int i = 0; i < 10_000; ++i) {
            assertBehavesLikeArrayList(alphabet);
        }
    }

    private Random rng = new Random(0);

    private void assertBehavesLikeArrayList(String... values) {
        ArrayList<String> arrayList = new ArrayList<>();
        TreeList treeList = TreeList.EMPTY;

        final int len = values.length;

        for (int i = 0; i < len; ++i) {
            int index = rng.nextInt(i + 1);
            arrayList.add(index, values[i]);
            treeList = treeList.insert(index, values[i]);
            assertListEquals(arrayList, treeList);
        }

        for (int i = len; i > 0; --i) {
            int index = rng.nextInt(i);
            arrayList.remove(index);
            treeList = treeList.delete(index);
            assertListEquals(arrayList, treeList);
        }
    }

    private static void assertListEquals(ArrayList<String> arrayList, TreeList treeList) {
        int size = arrayList.size();
        assertEquals(size, treeList.size());
        for (int i = 0; i < size; ++i) {
            assertEquals(arrayList.get(i), treeList.get(i));
        }
        assertBalanced(treeList);
    }

    private static void assertBalanced(TreeList t) {
        final int height = t.height();
        int current = 0;
        String s = t.toString();
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            switch (c) {
                case '(':
                    ++current;
                    break;

                case ')':
                    --current;
                    break;

                default:
                    if (current != height) {
                        fail("unbalanced tree " + t);
                    }

                case ' ':
            }
        }
    }

    @Test
    public void forEachEmpty() {
        StringBuilder sb = new StringBuilder();
        TreeList t = TreeList.EMPTY;
        t.forEach(sb::append);
        assertEquals("", sb.toString());
    }

    @Test
    public void forEachLeaf1() {
        StringBuilder sb = new StringBuilder();
        TreeList t = of("a");
        t.forEach(sb::append);
        assertEquals("a", sb.toString());
    }

    @Test
    public void forEachLeaf2() {
        StringBuilder sb = new StringBuilder();
        TreeList t = of("a", "b");
        t.forEach(sb::append);
        assertEquals("ab", sb.toString());
    }

    @Test
    public void forEachLeaf3() {
        StringBuilder sb = new StringBuilder();
        TreeList t = of("a", "b", "c");
        t.forEach(sb::append);
        assertEquals("abc", sb.toString());
    }

    @Test
    public void forEachInternal2() {
        StringBuilder sb = new StringBuilder();
        TreeList t = new Internal2(of("a"), of("b"));
        t.forEach(sb::append);
        assertEquals("ab", sb.toString());
    }

    @Test
    public void forEachInternal3() {
        StringBuilder sb = new StringBuilder();
        TreeList t = new Internal3(of("a"), of("b"), of("c"));
        t.forEach(sb::append);
        assertEquals("abc", sb.toString());
    }

    @Test
    public void forEachInternal4() {
        StringBuilder sb = new StringBuilder();
        TreeList t = new Internal4(of("a"), of("b"), of("c"), of("d"));
        t.forEach(sb::append);
        assertEquals("abcd", sb.toString());
    }

    @Test
    public void ofAlphabetPrefixes() {
        assertEquals("()", prefix(0));
        assertEquals("(a)", prefix(1));
        assertEquals("(a b)", prefix(2));
        assertEquals("(a b c)", prefix(3));

        assertEquals("((a b c) (d))", prefix(4));
        assertEquals("((a b c) (d e))", prefix(5));
        assertEquals("((a b c) (d e f))", prefix(6));

        assertEquals("((a b c) (d e f) (g))", prefix(7));
        assertEquals("((a b c) (d e f) (g h))", prefix(8));
        assertEquals("((a b c) (d e f) (g h i))", prefix(9));

        assertEquals("((a b c) (d e f) (g h i) (j))", prefix(10));
        assertEquals("((a b c) (d e f) (g h i) (j k))", prefix(11));
        assertEquals("((a b c) (d e f) (g h i) (j k l))", prefix(12));

        assertEquals("(((a b c) (d e f) (g h i)) ((j k l) (m)))", prefix(13));
        assertEquals("(((a b c) (d e f) (g h i)) ((j k l) (m n)))", prefix(14));
        assertEquals("(((a b c) (d e f) (g h i)) ((j k l) (m n o)))", prefix(15));

        assertEquals("(((a b c) (d e f) (g h i) (j k l)) ((m n o) (p)))", prefix(16));
        assertEquals("(((a b c) (d e f) (g h i) (j k l)) ((m n o) (p q)))", prefix(17));
        assertEquals("(((a b c) (d e f) (g h i) (j k l)) ((m n o) (p q r)))", prefix(18));

        assertEquals("(((a b c) (d e f) (g h i) (j k l)) ((m n o) (p q r) (s)))", prefix(19));
        assertEquals("(((a b c) (d e f) (g h i) (j k l)) ((m n o) (p q r) (s t)))", prefix(20));
        assertEquals("(((a b c) (d e f) (g h i) (j k l)) ((m n o) (p q r) (s t u)))", prefix(21));

        assertEquals("(((a b c) (d e f) (g h i) (j k l)) ((m n o) (p q r) (s t u) (v)))", prefix(22));
        assertEquals("(((a b c) (d e f) (g h i) (j k l)) ((m n o) (p q r) (s t u) (v w)))", prefix(23));
        assertEquals("(((a b c) (d e f) (g h i) (j k l)) ((m n o) (p q r) (s t u) (v w x)))", prefix(24));

        assertEquals("(((a b c) (d e f) (g h i) (j k l)) ((m n o) (p q r) (s t u)) ((v w x) (y)))", prefix(25));
        assertEquals("(((a b c) (d e f) (g h i) (j k l)) ((m n o) (p q r) (s t u)) ((v w x) (y z)))", prefix(26));
    }

    private String prefix(int length) {
        return of(Arrays.copyOf(alphabet, length)).toString();
    }

    @Test
    public void iteratorAllVariants() {
        TreeList abc = of("a", "b", "c");
        TreeList de = of("d", "e");
        TreeList f = of("f");
        TreeList g = of("g");

        TreeList h = of("h");
        TreeList i = of("i");
        TreeList j = of("j");

        TreeList x = new Internal4(abc, de, f, g);
        TreeList y = new Internal3(h, i, j);
        TreeList z = new Internal2(x, y);

        assertIterableBehavesLikeForEach(z);
    }

    @Test
    public void iteratorDeep() {
        TreeList ab = new Internal2(of("a"), of("b"));
        TreeList cd = new Internal2(of("c"), of("d"));
        TreeList abcd = new Internal2(ab, cd);
        TreeList ef = new Internal2(of("e"), of("f"));
        TreeList gh = new Internal2(of("g"), of("h"));
        TreeList efgh = new Internal2(ef, gh);
        TreeList abcdefgh = new Internal2(abcd, efgh);

        TreeList ij = new Internal2(of("i"), of("j"));
        TreeList kl = new Internal2(of("k"), of("l"));
        TreeList ijkl = new Internal2(ij, kl);
        TreeList mn = new Internal2(of("m"), of("n"));
        TreeList op = new Internal2(of("o"), of("p"));
        TreeList mnop = new Internal2(mn, op);
        TreeList ijklmnop = new Internal2(ijkl, mnop);

        TreeList abcdefghijklmnop = new Internal2(abcdefgh, ijklmnop);
        assertIterableBehavesLikeForEach(abcdefghijklmnop);
    }

    @Test
    public void iteratorEmpty() {
        assertIterableBehavesLikeForEach(TreeList.EMPTY);
    }

    @Test
    public void iteratorLeaf1() {
        assertIterableBehavesLikeForEach(of("a"));
    }

    @Test
    public void iteratorLeaf2() {
        assertIterableBehavesLikeForEach(of("a", "b"));
    }

    @Test
    public void iteratorLeaf3() {
        assertIterableBehavesLikeForEach(of("a", "b", "c"));
    }

    private void assertIterableBehavesLikeForEach(TreeList treeList) {
        Iterator<String> it = treeList.iterator();
        treeList.forEach(string -> {
            assertTrue(it.hasNext());
            assertEquals(string, it.next());
        });
        assertFalse(it.hasNext());
    }
}
