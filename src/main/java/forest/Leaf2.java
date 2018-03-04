package forest;

public class Leaf2 extends Leaf {
    Leaf2(String value) {
        super(value);
    }

    @Override
    boolean isDoubleBlack() {
        return true;
    }

    @Override
    int blackHeight() {
        throw new AssertionError("blackHeight");
    }

    @Override
    TreeList blackened() {
        return new Leaf(value);
    }
}
