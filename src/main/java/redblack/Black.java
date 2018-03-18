package redblack;

class Black extends Internal {
    Black(TreeList left, int leftCount, TreeList right) {
        super(left, leftCount, right);
    }

    Black(TreeList left, TreeList right) {
        super(left, right);
    }

    @Override
    int blackHeight() {
        int leftHeight = left.blackHeight();
        int rightHeight = right.blackHeight();
        if (leftHeight != rightHeight) {
            throw new AssertionError(this);
        }
        return leftHeight + 1;
    }

    @Override
    void checkRed() {
        left.checkRed();
        right.checkRed();
    }

    @Override
    TreeList plusBlack() {
        return new DoubleBlack(this);
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
            if (left instanceof Red) {
                if (left.leftChild() instanceof Red) {
                    return new Red(left.leftChild().blackened(), new Black(left.rightChild(), right));
                } else if (left.rightChild() instanceof Red) {
                    return new Red(new Black(left.leftChild(), left.rightChild().leftChild()), new Black(left.rightChild().rightChild(), right));
                }
            }
            return new Black(left, leftCount + 1, right);
        } else {
            TreeList right = this.right.addHelper(index - leftCount, value);
            if (right instanceof Red) {
                if (right.rightChild() instanceof Red) {
                    return new Red(new Black(left, right.leftChild()), right.rightChild().blackened());
                } else if (right.leftChild() instanceof Red) {
                    return new Red(new Black(left, right.leftChild().leftChild()), new Black(right.leftChild().rightChild(), right.rightChild()));
                }
            }
            return new Black(left, leftCount, right);
        }
    }

    @Override
    TreeList removeHelper(int index) {
        if (index < leftCount) {
            TreeList left = this.left.removeHelper(index);
            if (left == null) return right.plusBlack();

            if (!(left instanceof DoubleBlack)) return new Black(left, leftCount - 1, right);

            if (right instanceof Red) {
                TreeList c = right.leftChild().leftChild();
                TreeList d = right.leftChild().rightChild();
                TreeList e = right.rightChild();
                if (!(c instanceof Red)) return new Black(new Black(new Red(left.blackened(), c), d), e);

                TreeList A = left.blackened();
                TreeList B = c.leftChild();
                TreeList C = c.rightChild();
                TreeList D = d;
                return new Black(new Red(new Black(A, B), new Black(C, D)), e);
            }

            TreeList c = right.leftChild();
            TreeList d = right.rightChild();
            if (!(c instanceof Red)) return new DoubleBlack(new Red(left.blackened(), c), d);

            TreeList A = left.blackened();
            TreeList B = c.leftChild();
            TreeList C = c.rightChild();
            TreeList D = d;
            return new Black(new Black(A, B), new Black(C, D));
        } else {
            TreeList right = this.right.removeHelper(index - leftCount);
            if (right == null) return left.plusBlack();

            if (!(right instanceof DoubleBlack)) return new Black(left, leftCount, right);

            if (left instanceof Red) {
                TreeList a = left.leftChild();
                TreeList b = left.rightChild().leftChild();
                TreeList c = left.rightChild().rightChild();
                if (!(c instanceof Red)) return new Black(a, new Black(b, new Red(c, right.blackened())));

                TreeList A = b;
                TreeList B = c.leftChild();
                TreeList C = c.rightChild();
                TreeList D = right.blackened();
                return new Black(a, new Red(new Black(A, B), new Black(C, D)));
            }

            TreeList a = left.leftChild();
            TreeList b = left.rightChild();
            if (!(b instanceof Red)) return new DoubleBlack(a, new Red(b, right.blackened()));

            TreeList A = a;
            TreeList B = b.leftChild();
            TreeList C = b.rightChild();
            TreeList D = right.blackened();
            return new Black(new Black(A, B), new Black(C, D));
        }
    }

    @Override
    public String toString() {
        return "(" + left + " " + right + ")";
    }
}
