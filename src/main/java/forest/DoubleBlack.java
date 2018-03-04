package forest;

public class DoubleBlack extends Black {
    DoubleBlack(TreeList left, int leftCount, TreeList right) {
        super(left, leftCount, right);
    }

    DoubleBlack(TreeList left, TreeList right) {
        super(left, right);
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
        return new Black(left, leftCount, right);
    }
}
