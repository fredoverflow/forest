package twofour;

import java.util.Iterator;
import java.util.function.Consumer;

class Leaf3 extends TreeList {
    final String a;
    final String b;
    final String c;

    Leaf3(String a, String b, String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    int height() {
        return 1;
    }

    @Override
    int slots() {
        return 3;
    }

    @Override
    public int size() {
        return 3;
    }

    @Override
    public String get(int index) {
        if (index == 0) return a;
        if (index == 1) return b;
        if (index == 2) return c;

        throw new IllegalArgumentException("Leaf3.get(" + index + ")");
    }

    @Override
    public TreeList set(int index, String value) {
        if (index == 0) return new Leaf3(value, b, c);
        if (index == 1) return new Leaf3(a, value, c);
        if (index == 2) return new Leaf3(a, b, value);

        throw new IllegalArgumentException("Leaf3.set(" + index + ", " + value + ")");
    }

    @Override
    public TreeList insert(int index, String value) {
        if (index == 0) return new Internal2Split(new Leaf2(value, a), new Leaf2(b, c));
        if (index == 1) return new Internal2Split(new Leaf2(a, value), new Leaf2(b, c));
        if (index == 2) return new Internal2Split(new Leaf2(a, b), new Leaf2(value, c));
        if (index == 3) return new Internal2Split(new Leaf2(a, b), new Leaf2(c, value));

        throw new IllegalArgumentException("Leaf3.insert(" + index + ")");
    }

    @Override
    TreeList deleteHelper(int index) {
        if (index == 0) return new Leaf2(b, c);
        if (index == 1) return new Leaf2(a, c);
        if (index == 2) return new Leaf2(a, b);

        throw new IllegalArgumentException("Leaf3.deleteHelper(" + index + ")");
    }

    @Override
    void appendTo(StringBuilder sb) {
        sb.append('(');
        sb.append(a);
        sb.append(' ');
        sb.append(b);
        sb.append(' ');
        sb.append(c);
        sb.append(')');
    }

    @Override
    public void forEach(Consumer<? super String> action) {
        action.accept(a);
        action.accept(b);
        action.accept(c);
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < 3;
            }

            @Override
            public String next() {
                return get(index++);
            }
        };
    }
}
