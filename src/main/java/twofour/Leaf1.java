package twofour;

class Leaf1 extends TreeList {
    final String value;

    Leaf1(String value) {
        this.value = value;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public String get(int index) {
        if (index == 0) return value;

        throw new IllegalArgumentException("Leaf1.get(" + index + ")");
    }

    @Override
    public TreeList insert(int index, String value) {
        if (index == 0) return new Leaf2(value, this.value);
        if (index == 1) return new Leaf2(this.value, value);

        throw new IllegalArgumentException("Leaf1.insert(" + index + ")");
    }
}
