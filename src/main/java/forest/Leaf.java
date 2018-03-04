package forest;

class Leaf extends TreeList {
    final String value;

    Leaf(String value) {
        this.value = value;
    }

    @Override
    int blackHeight() {
        return 1;
    }

    @Override
    void checkRed() {
    }

    @Override
    TreeList plusBlack() {
        return new Leaf2(value);
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public String get(int index) {
        return value;
    }

    @Override
    public TreeList set(int index, String value) {
        if (index != 0) throw new IllegalArgumentException("index: " + index);
        return new Leaf(value);
    }

    @Override
    public TreeList add(int index, String value) {
        Leaf that = new Leaf(value);
        if (index == 0) return new Black(that, 1, this);
        if (index == 1) return new Black(this, 1, that);
        throw new IllegalArgumentException("index: " + index);
    }

    @Override
    TreeList addHelper(int index, String value) {
        Leaf that = new Leaf(value);
        if (index == 0) return new Red(that, 1, this);
        if (index == 1) return new Red(this, 1, that);
        throw new IllegalArgumentException("index: " + index);
    }

    @Override
    public TreeList remove(int index) {
        return EMPTY;
    }

    @Override
    TreeList removeHelper(int index) {
        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
