package forest;

import java.util.Random;

public abstract class TreeList {
    abstract boolean isRed();

    TreeList blackened() {
        throw new AssertionError("blackened");
    }

    TreeList leftChild() {
        throw new AssertionError("leftChild");
    }

    TreeList rightChild() {
        throw new AssertionError("rightChild");
    }


    public abstract int size();

    public abstract String get(int index);

    public abstract TreeList set(int index, String value);

    public abstract TreeList insert(int index, String value);

    abstract TreeList insertHelper(int index, String value);

    public abstract TreeList remove(int index);

    public static final TreeList EMPTY = new TreeList() {
        @Override
        boolean isRed() {
            return false;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public String get(int index) {
            throw new AssertionError("TreeList.EMPTY.get");
        }

        @Override
        public TreeList set(int index, String value) {
            throw new AssertionError("TreeList.EMPTY.set");
        }

        @Override
        public TreeList insert(int index, String value) {
            return new Leaf(value);
        }

        @Override
        TreeList insertHelper(int index, String value) {
            throw new AssertionError("TreeList.EMPTY.insertHelper");
        }

        @Override
        public TreeList remove(int index) {
            throw new AssertionError("TreeList.EMPTY.remove");
        }

        @Override
        public String toString() {
            return "()";
        }
    };

    public static TreeList of(String... values) {
        int len = values.length;
        if (len == 0) return EMPTY;

        TreeList[] temp = new TreeList[len];
        for (int i = 0; i < len; ++i) {
            temp[i] = new Leaf(values[i]);
        }
        int leftCount = 1;
        boolean isRed = true;
        while (len > 1) {
            int i, k;
            for (i = 0, k = 0; k + 1 < len; ++i, k += 2) {
                temp[i] = new Internal(isRed, temp[k], leftCount, temp[k + 1]);
            }
            if (k < len) {
                temp[i++] = temp[k];
            }
            leftCount *= 2;
            isRed = !isRed;
            len = i;
        }
        return temp[0];
    }
}

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
        if (index == 0) return new Internal(false, that, 1, this);
        if (index == 1) return new Internal(false, this, 1, that);
        throw new IllegalArgumentException("index: " + index);
    }

    @Override
    TreeList insertHelper(int index, String value) {
        Leaf that = new Leaf(value);
        if (index == 0) return new Internal(true, that, 1, this);
        if (index == 1) return new Internal(true, this, 1, that);
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

class Internal extends TreeList {
    private final boolean isRed;
    private final TreeList left;
    private final int leftCount;
    private final TreeList right;

    private static final Random rng = new Random();

    Internal(boolean isRed, TreeList left, int leftCount, TreeList right) {
        this.isRed = isRed;
        this.left = left;
        this.leftCount = leftCount;
        this.right = right;
    }

    Internal(boolean isRed, TreeList left, TreeList right) {
        this(isRed, left, left.size(), right);
    }

    @Override
    boolean isRed() {
        return isRed;
    }

    @Override
    public TreeList blackened() {
        return new Internal(false, left, leftCount, right);
    }

    @Override
    public TreeList leftChild() {
        return left;
    }

    @Override
    public TreeList rightChild() {
        return right;
    }

    @Override
    public int size() {
        return leftCount + right.size();
    }

    @Override
    public String get(int index) {
        if (index < leftCount) {
            return left.get(index);
        } else {
            return right.get(index - leftCount);
        }
    }

    @Override
    public TreeList set(int index, String value) {
        if (index < leftCount) {
            return new Internal(isRed, left.set(index, value), leftCount, right);
        } else {
            return new Internal(isRed, left, leftCount, right.set(index - leftCount, value));
        }
    }

    @Override
    public TreeList insert(int index, String value) {
        return insertHelper(index, value).blackened();
    }

    @Override
    TreeList insertHelper(int index, String value) {
        if (index < leftCount || (index == leftCount && rng.nextBoolean())) {
            TreeList left = this.left.insertHelper(index, value);
            if (!isRed && left.isRed()) {
                if (left.leftChild().isRed()) {
                    if (right.isRed()) {
                        return new Internal(true, left.blackened(), right.blackened());
                    } else {
                        return new Internal(false, left.leftChild(), new Internal(true, left.rightChild(), right));
                    }
                } else if (left.rightChild().isRed()) {
                    if (right.isRed()) {
                        return new Internal(true, left.blackened(), right.blackened());
                    } else {
                        return new Internal(false, new Internal(true, left.leftChild(), left.rightChild().leftChild()), new Internal(true, left.rightChild().rightChild(), right));
                    }
                }
            }
            return new Internal(isRed, left, leftCount + 1, right);
        } else {
            TreeList right = this.right.insertHelper(index - leftCount, value);
            if (!isRed && right.isRed()) {
                if (right.rightChild().isRed()) {
                    if (left.isRed()) {
                        return new Internal(true, left.blackened(), right.blackened());
                    } else {
                        return new Internal(false, new Internal(true, left, right.leftChild()), right.rightChild());
                    }
                } else if (right.leftChild().isRed()) {
                    if (left.isRed()) {
                        return new Internal(true, left.blackened(), right.blackened());
                    } else {
                        return new Internal(false, new Internal(true, left, right.leftChild().leftChild()), new Internal(true, right.leftChild().rightChild(), right.rightChild()));
                    }
                }
            }
            return new Internal(isRed, left, leftCount, right);
        }
    }

    @Override
    public TreeList remove(int index) {
        if (index < leftCount) {
            TreeList newLeft = left.remove(index);
            // TODO balance
            return (newLeft == EMPTY) ? right : new Internal(isRed, newLeft, leftCount - 1, right);
        } else {
            TreeList newRight = right.remove(index - leftCount);
            // TODO balance
            return (newRight == EMPTY) ? left : new Internal(isRed, left, leftCount, newRight);
        }
    }

    @Override
    public String toString() {
        if (isRed) {
            // return "<" + left + " " + right + ">";
        }
        return "(" + left + " " + right + ")";
    }
}
