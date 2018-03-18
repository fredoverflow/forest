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
        throw new IllegalArgumentException("Leaf3.insert(" + index + ")");
    }
}
