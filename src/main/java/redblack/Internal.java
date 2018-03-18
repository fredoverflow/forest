package redblack;

import java.util.Iterator;
import java.util.Random;
import java.util.Stack;
import java.util.function.Consumer;

abstract class Internal extends TreeList {
    final TreeList left;
    final int leftCount;
    final TreeList right;

    static final Random rng = new Random();

    Internal(TreeList left, int leftCount, TreeList right) {
        this.left = left;
        assert leftCount == left.size() : leftCount + " != " + left.size();
        this.leftCount = leftCount;
        this.right = right;
    }

    Internal(TreeList left, TreeList right) {
        this.left = left;
        this.leftCount = left.size();
        this.right = right;
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
    public TreeList add(int index, String value) {
        return addHelper(index, value).blackened();
    }

    @Override
    public TreeList remove(int index) {
        return removeHelper(index).blackened();
    }

    @Override
    public Iterator<String> iterator() {
        Stack<TreeList> path = new Stack<>();
        TreeList self = this;
        while (self instanceof Internal) {
            path.push(self);
            self = self.leftChild();
        }
        path.push(self);

        return new Iterator<String>() {
            @Override
            public boolean hasNext() {
                return !path.empty();
            }

            @Override
            public String next() {
                TreeList self = path.pop();
                String result = self.get(0);
                while (path.peek().rightChild() == self) {
                    self = path.pop();
                    if (path.empty()) return result;
                }
                self = path.peek().rightChild();
                while (self instanceof Internal) {
                    path.push(self);
                    self = self.leftChild();
                }
                path.push(self);
                return result;
            }
        };
    }

    @Override
    public void forEach(Consumer<? super String> action) {
        left.forEach(action);
        right.forEach(action);
    }
}
