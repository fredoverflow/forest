package cons;

import org.junit.Test;

import java.util.Iterator;

import static cons.Cons.cons;
import static org.junit.Assert.*;

public class ConsTest {
    @Test
    public void emptyConsIsEmpty() {
        assertTrue(cons().isEmpty());
    }

    @Test
    public void manualConsing() {
        Cons<String> abc = cons("a", cons("b", cons("c")));
        assertEquals("a", abc.head);
        assertEquals("b", abc.tail.head);
        assertEquals("c", abc.tail.tail.head);
        assertTrue(abc.tail.tail.tail.isEmpty());
    }

    @Test
    public void factoryConsing() {
        Cons<String> abc = cons("a", "b", "c");
        assertEquals("a", abc.head);
        assertEquals("b", abc.tail.head);
        assertEquals("c", abc.tail.tail.head);
        assertTrue(abc.tail.tail.tail.isEmpty());
    }

    @Test
    public void covariantConsing() {
        StringBuilder builder = new StringBuilder();

        Cons<String> strings = cons("string");
        Cons<CharSequence> sequences = cons(builder, strings);
        Cons<Object> objects = cons(42, sequences);

        assertEquals("string", strings.head);
        assertEquals("string", sequences.tail.head);
        assertEquals("string", objects.tail.tail.head);

        assertEquals(builder, sequences.head);
        assertEquals(builder, objects.tail.head);

        assertEquals(42, objects.head);
    }

    @Test
    public void allEquals() {
        assertEquals(cons("a"), cons("a"));
        assertEquals(cons("a", "b"), cons("a", "b"));
        assertEquals(cons("a", "b", "c"), cons("a", "b", "c"));
    }

    @Test
    public void allEqualsButFirst() {
        assertNotEquals(cons("_"), cons("a"));
        assertNotEquals(cons("_", "b"), cons("a", "b"));
        assertNotEquals(cons("_", "b", "c"), cons("a", "b", "c"));
    }

    @Test
    public void allEqualsButLast() {
        assertNotEquals(cons("_"), cons("a"));
        assertNotEquals(cons("a", "_"), cons("a", "b"));
        assertNotEquals(cons("a", "b", "_"), cons("a", "b", "c"));
    }

    @Test
    public void allEqualsButMiddle() {
        assertNotEquals(cons("a", "_", "c"), cons("a", "b", "c"));
    }

    @Test
    public void leftPrefix() {
        assertNotEquals(cons(""), cons("a"));
        assertNotEquals(cons("a"), cons("a", "b"));
        assertNotEquals(cons("a", "b"), cons("a", "b", "c"));
    }

    @Test
    public void rightPrefix() {
        assertNotEquals(cons("a"), cons());
        assertNotEquals(cons("a", "b"), cons("a"));
        assertNotEquals(cons("a", "b", "c"), cons("a", "b"));
    }

    @Test
    public void hashcode() {
        assertEquals(1, cons().hashCode());
        assertEquals(31, cons("").hashCode());
        assertEquals(cons("a", "b").hashCode(), cons("b", "C").hashCode());
        assertNotEquals(cons("a", "b").hashCode(), cons("b", "c").hashCode());
    }

    @Test
    public void tostring() {
        assertEquals("[]", cons().toString());
        assertEquals("[a]", cons("a").toString());
        assertEquals("[a, b]", cons("a", "b").toString());
        assertEquals("[a, b, c]", cons("a", "b", "c").toString());
    }

    @Test
    public void forEach() {
        StringBuilder builder = new StringBuilder();
        cons("a", "b", "c").forEach(builder::append);
        assertEquals("abc", builder.toString());
    }

    @Test
    public void iterator() {
        Iterator<String> it = cons("a", "b", "c").iterator();
        assertTrue(it.hasNext());
        assertEquals("a", it.next());
        assertTrue(it.hasNext());
        assertEquals("b", it.next());
        assertTrue(it.hasNext());
        assertEquals("c", it.next());
        assertFalse(it.hasNext());
    }
}
