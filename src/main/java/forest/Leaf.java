package forest;

class Leaf extends TreeList {
    private final String value;

    Leaf(String value) {
        this.value = value;
    }

    @Override
    boolean isRed() {
        return false;
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
    public TreeList insert(int index, String value) {
        Leaf that = new Leaf(value);
        if (index == 0) return new Black(that, 1, this);
        if (index == 1) return new Black(this, 1, that);
        throw new IllegalArgumentException("index: " + index);
    }

    @Override
    TreeList insertHelper(int index, String value) {
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
    public String toString() {
        return value;
    }
}
