package forest;

import java.util.function.Consumer;

class DoubleBlack extends TreeList {
    private final TreeList black;

    DoubleBlack(TreeList black) {
        assert black instanceof Leaf || black instanceof Black;
        this.black = black;
    }

    DoubleBlack(TreeList left, TreeList right) {
        this.black = new Black(left, right);
    }

    @Override
    int blackHeight() {
        throw new AssertionError("DoubleBlack.blackHeight");
    }

    @Override
    void checkRed() {
        throw new AssertionError("DoubleBlack.checkRed");
    }

    @Override
    TreeList blackened() {
        return black;
    }

    @Override
    public int size() {
        throw new AssertionError("DoubleBlack.size");
    }

    @Override
    public String get(int index) {
        throw new AssertionError("DoubleBlack.get");
    }

    @Override
    public TreeList set(int index, String value) {
        throw new AssertionError("DoubleBlack.set");
    }

    @Override
    public TreeList add(int index, String value) {
        throw new AssertionError("DoubleBlack.add");
    }

    @Override
    TreeList addHelper(int index, String value) {
        throw new AssertionError("DoubleBlack.addHelper");
    }

    @Override
    public TreeList remove(int index) {
        throw new AssertionError("DoubleBlack.remove");
    }

    @Override
    TreeList removeHelper(int index) {
        throw new AssertionError("DoubleBlack.removeHelper");
    }

    @Override
    public void forEach(Consumer<? super String> action) {
        throw new AssertionError("DoubleBlack.forEach");
    }
}
