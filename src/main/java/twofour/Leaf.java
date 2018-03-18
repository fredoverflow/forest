package twofour;

class Leaf extends TreeList {
    final String value;

    Leaf(String value) {
        this.value = value;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public String get(int index) {
        if (index != 0) throw new IllegalArgumentException("Leaf.insert(" + index + ")");

        return value;
    }

    @Override
    public TreeList insert(int index, String value) {
        return null;
    }
}
