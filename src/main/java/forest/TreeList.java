package forest;

import java.util.Random;

public abstract class TreeList {
    public abstract String get(int index);

    public abstract TreeList insert(int index, String value);

    public static final TreeList EMPTY = new TreeList() {
        @Override
        public String get(int index) {
            throw new AssertionError("TreeList.EMPTY.get");
        }

        @Override
        public TreeList insert(int index, String value) {
            return new Leaf(value);
        }

        @Override
        public String toString() {
            return "()";
        }
    };
}

class Leaf extends TreeList {
    private final String value;

    Leaf(String value) {
        this.value = value;
    }

    @Override
    public String get(int index) {
        return value;
    }

    @Override
    public TreeList insert(int index, String value) {
        Leaf that = new Leaf(value);
        if (index == 0) return new Internal(that, 1, this);
        if (index == 1) return new Internal(this, 1, that);
        throw new IllegalArgumentException("index: " + index);
    }

    @Override
    public String toString() {
        return value;
    }
}

class Internal extends TreeList {
    private final TreeList left;
    private final int leftCount;
    private final TreeList right;

    private static final Random rng = new Random();

    Internal(TreeList left, int leftCount, TreeList right) {
        this.left = left;
        this.leftCount = leftCount;
        this.right = right;
    }

    @Override
    public String get(int index) {
        return (index < leftCount) ? left.get(index) : right.get(index - leftCount);
    }

    @Override
    public TreeList insert(int index, String value) {
        if (index < leftCount || (index == leftCount && rng.nextBoolean())) {
            return new Internal(left.insert(index, value), leftCount + 1, right);
        }
        return new Internal(left, leftCount, right.insert(index - leftCount, value));
    }

    @Override
    public String toString() {
        return "(" + left + " " + right + ")";
    }
}
