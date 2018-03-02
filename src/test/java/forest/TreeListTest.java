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
}
