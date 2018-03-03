package forest;

import java.util.Random;

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
    TreeList blackened() {
        return new Internal(false, left, leftCount, right);
    }

    @Override
    TreeList leftChild() {
        return left;
    }

    @Override
    TreeList rightChild() {
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
                    return new Internal(true, left.leftChild().blackened(), new Internal(false, left.rightChild(), right));
                } else if (left.rightChild().isRed()) {
                    return new Internal(true, new Internal(false, left.leftChild(), left.rightChild().leftChild()), new Internal(false, left.rightChild().rightChild(), right));
                }
            }
            return new Internal(isRed, left, leftCount + 1, right);
        } else {
            TreeList right = this.right.insertHelper(index - leftCount, value);
            if (!isRed && right.isRed()) {
                if (right.rightChild().isRed()) {
                    return new Internal(true, new Internal(false, left, right.leftChild()), right.rightChild().blackened());
                } else if (right.leftChild().isRed()) {
                    return new Internal(true, new Internal(false, left, right.leftChild().leftChild()), new Internal(false, right.leftChild().rightChild(), right.rightChild()));
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
