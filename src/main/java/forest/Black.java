package forest;

class Black extends Internal {
    Black(TreeList left, int leftCount, TreeList right) {
        super(left, leftCount, right);
    }

    Black(TreeList left, TreeList right) {
        super(left, right);
    }

    @Override
    boolean isRed() {
        return false;
    }

    @Override
    TreeList blackened() {
        return this;
    }

    @Override
    public TreeList set(int index, String value) {
        if (index < leftCount) {
            return new Black(left.set(index, value), leftCount, right);
        } else {
            return new Black(left, leftCount, right.set(index - leftCount, value));
        }
    }

    @Override
    TreeList addHelper(int index, String value) {
        if (index < leftCount || (index == leftCount && rng.nextBoolean())) {
            TreeList left = this.left.addHelper(index, value);
            if (left.isRed()) {
                if (left.leftChild().isRed()) {
                    return new Red(left.leftChild().blackened(), new Black(left.rightChild(), right));
                } else if (left.rightChild().isRed()) {
                    return new Red(new Black(left.leftChild(), left.rightChild().leftChild()), new Black(left.rightChild().rightChild(), right));
                }
            }
            return new Black(left, leftCount + 1, right);
        } else {
            TreeList right = this.right.addHelper(index - leftCount, value);
            if (right.isRed()) {
                if (right.rightChild().isRed()) {
                    return new Red(new Black(left, right.leftChild()), right.rightChild().blackened());
                } else if (right.leftChild().isRed()) {
                    return new Red(new Black(left, right.leftChild().leftChild()), new Black(right.leftChild().rightChild(), right.rightChild()));
                }
            }
            return new Black(left, leftCount, right);
        }
    }

    @Override
    public TreeList remove(int index) {
        if (index < leftCount) {
            TreeList newLeft = left.remove(index);
            // TODO balance
            return (newLeft == EMPTY) ? right : new Black(newLeft, leftCount - 1, right);
        } else {
            TreeList newRight = right.remove(index - leftCount);
            // TODO balance
            return (newRight == EMPTY) ? left : new Black(left, leftCount, newRight);
        }
    }

    @Override
    public String toString() {
        return "(" + left + " " + right + ")";
    }
}
