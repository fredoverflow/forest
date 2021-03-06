package twofour;

import java.util.function.Consumer;

class Leaf2 extends TreeList {
    final String a;
    final String b;

    Leaf2(String a, String b) {
        this.a = a;
        this.b = b;
    }

    @Override
    int height() {
        return 1;
    }

    @Override
    int slots() {
        return 2;
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public String get(int index) {
        if (index == 0) return a;
        if (index == 1) return b;

        throw new IllegalArgumentException("Leaf2.get(" + index + ")");
    }

    @Override
    public TreeList set(int index, String value) {
        if (index == 0) return new Leaf2(value, b);
        if (index == 1) return new Leaf2(a, value);

        throw new IllegalArgumentException("Leaf2.set(" + index + ", " + value + ")");
    }

    @Override
    public TreeList insert(int index, String value) {
        if (index == 0) return new Leaf3(value, a, b);
        if (index == 1) return new Leaf3(a, value, b);
        if (index == 2) return new Leaf3(a, b, value);

        throw new IllegalArgumentException("Leaf2.insert(" + index + ")");
    }

    @Override
    TreeList deleteHelper(int index) {
        if (index == 0) return new Leaf1(b);
        if (index == 1) return new Leaf1(a);

        throw new IllegalArgumentException("Leaf2.deleteHelper(" + index + ")");
    }

    @Override
    void appendTo(StringBuilder sb) {
        sb.append('(');
        sb.append(a);
        sb.append(' ');
        sb.append(b);
        sb.append(')');
    }

    @Override
    public void forEach(Consumer<? super String> action) {
        action.accept(a);
        action.accept(b);
    }

    @Override
    public int hashCode() {
        return a.hashCode() * 31 + b.hashCode();
    }
}
