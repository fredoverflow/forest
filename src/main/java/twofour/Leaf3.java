package twofour;

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
    public TreeList insert(int index, String value) {
        if (index == 0) return new Internal2Split(new Leaf2(value, a), new Leaf2(b, c));
        if (index == 1) return new Internal2Split(new Leaf2(a, value), new Leaf2(b, c));
        if (index == 2) return new Internal2Split(new Leaf2(a, b), new Leaf2(value, c));
        if (index == 3) return new Internal2Split(new Leaf2(a, b), new Leaf2(c, value));

        throw new IllegalArgumentException("Leaf3.insert(" + index + ")");
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
}
