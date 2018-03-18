package twofour;

class Leaf2 extends TreeList {
    final String a;
    final String b;

    Leaf2(String a, String b) {
        this.a = a;
        this.b = b;
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
    public TreeList insert(int index, String value) {
        if (index == 0) return new Leaf3(value, a, b);
        if (index == 1) return new Leaf3(a, value, b);
        if (index == 2) return new Leaf3(a, b, value);

        throw new IllegalArgumentException("Leaf2.insert(" + index + ")");
    }

    @Override
    void appendTo(StringBuilder sb) {
        sb.append('(');
        sb.append(a);
        sb.append(' ');
        sb.append(b);
        sb.append(')');
    }
}
