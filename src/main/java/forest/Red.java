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
    int blackHeight() {
        int leftHeight = left.blackHeight();
        int rightHeight = right.blackHeight();
        if (leftHeight != rightHeight) {
            throw new AssertionError(this);
        }
        return leftHeight;
    }

    @Override
    void checkRed() {
        if (left.isRed()) {
            throw new AssertionError(left + " in " + this);
        }
        if (right.isRed()) {
            throw new AssertionError(right + " in " + this);
        }
        left.checkRed();
        right.checkRed();
    }

    @Override
    TreeList plusBlack() {
        return new Black(left, leftCount, right);
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
    TreeList addHelper(int index, String value) {
        if (index < leftCount || (index == leftCount && rng.nextBoolean())) {
            TreeList left = this.left.addHelper(index, value);
            return new Red(left, leftCount + 1, right);
        } else {
            TreeList right = this.right.addHelper(index - leftCount, value);
            return new Red(left, leftCount, right);
        }
    }

    @Override
    TreeList removeHelper(int index) {
        if (index < leftCount) {
            TreeList left = this.left.removeHelper(index);
            if (left == null) return right;

            if (!left.isDoubleBlack()) return new Red(left, leftCount - 1, right);

            TreeList c = right.leftChild();
            TreeList d = right.rightChild();
            if (!c.isRed()) return new Black(new Red(left.blackened(), c), d);

            TreeList A = left.blackened();
            TreeList B = c.leftChild();
            TreeList C = c.rightChild();
            TreeList D = d;
            return new Red(new Black(A, B), new Black(C, D));
        } else {
            TreeList right = this.right.removeHelper(index - leftCount);
            if (right == null) return left;

            if (!right.isDoubleBlack()) return new Red(left, leftCount, right);

            TreeList a = left.leftChild();
            TreeList b = left.rightChild();
            if (!b.isRed()) return new Black(a, new Red(b, right.blackened()));

            TreeList A = a;
            TreeList B = b.leftChild();
            TreeList C = b.rightChild();
            TreeList D = right.blackened();
            return new Red(new Black(A, B), new Black(C, D));
        }
    }

    @Override
    public String toString() {
        return "<" + left + " " + right + ">";
    }
}
