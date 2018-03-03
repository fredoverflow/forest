package forest;

class Red extends Internal {
    Red(TreeList left, int leftCount, TreeList right) {
        super(left, leftCount, right);
    }

    Red(TreeList left, TreeList right) {
        super(left, right);
    }

    @Override
    boolean isRed() {
        return true;
    }

    @Override
    TreeList blackened() {
        return new Black(left, leftCount, right);
    }

    @Override
    public TreeList set(int index, String value) {
        if (index < leftCount) {
            return new Red(left.set(index, value), leftCount, right);
        } else {
            return new Red(left, leftCount, right.set(index - leftCount, value));
        }
    }

    @Override
    TreeList insertHelper(int index, String value) {
        if (index < leftCount || (index == leftCount && rng.nextBoolean())) {
            TreeList left = this.left.insertHelper(index, value);
            return new Red(left, leftCount + 1, right);
        } else {
            TreeList right = this.right.insertHelper(index - leftCount, value);
            return new Red(left, leftCount, right);
        }
    }

    @Override
    public TreeList remove(int index) {
        if (index < leftCount) {
            TreeList newLeft = left.remove(index);
            // TODO balance
            return (newLeft == EMPTY) ? right : new Red(newLeft, leftCount - 1, right);
        } else {
            TreeList newRight = right.remove(index - leftCount);
            // TODO balance
            return (newRight == EMPTY) ? left : new Red(left, leftCount, newRight);
        }
    }

    @Override
    public String toString() {
        return "<" + left + " " + right + ">";
    }
}
