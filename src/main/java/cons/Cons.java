package cons;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

public final class Cons<E> implements Iterable<E> {
    public final E head;
    public final Cons<E> tail;
    private int hash;

    private Cons(E head, Cons<E> tail) {
        this.head = head;
        this.tail = tail;
    }

    public boolean isEmpty() {
        return tail == null;
    }

    private static final Cons<?> EMPTY = new Cons<>(null, null);

    @SuppressWarnings("unchecked")
    public static <E> Cons<E> cons() {
        return (Cons<E>) EMPTY;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <E> Cons<E> cons(E head, Cons<? extends E> tail) {
        return (Cons<E>) new Cons(
                Objects.requireNonNull(head, "head"),
                Objects.requireNonNull(tail, "tail"));
    }

    public static <E> Cons<E> cons(E element) {
        return cons(element, cons());
    }

    @SafeVarargs
    public static <E> Cons<E> cons(E... elements) {
        Cons<E> result = cons();
        for (int i = elements.length - 1; i >= 0; --i) {
            result = cons(elements[i], result);
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Cons && equals(this, (Cons<?>) obj);
    }

    public static boolean equals(Cons<?> a, Cons<?> b) {
        // Equal cells will eventually converge to the SAME (possibly EMPTY) cell
        while (a != b) {
            if (!Objects.equals(a.head, b.head)) return false;
            a = a.tail;
            b = b.tail;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = this.hash;
        if (hash == 0) {
            hash = 1;
            for (Cons<E> cell = this; !cell.isEmpty(); cell = cell.tail) {
                hash = hash * 31 + cell.head.hashCode();
            }
            this.hash = hash;
        }
        return hash;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "[]";

        StringBuilder builder = new StringBuilder("[");
        builder.append(head);
        for (Cons<E> cell = tail; !cell.isEmpty(); cell = cell.tail) {
            builder.append(", ");
            builder.append(cell.head);
        }
        return builder.append("]").toString();
    }

    @Override
    public void forEach(Consumer<? super E> consumer) {
        for (Cons<E> cell = this; !cell.isEmpty(); cell = cell.tail) {
            consumer.accept(cell.head);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new ConsIterator<>(this);
    }
}

final class ConsIterator<E> implements Iterator<E> {
    private Cons<E> cell;

    ConsIterator(Cons<E> cell) {
        this.cell = cell;
    }

    @Override
    public boolean hasNext() {
        return !cell.isEmpty();
    }

    @Override
    public E next() {
        E result = cell.head;
        cell = cell.tail;
        return result;
    }
}
