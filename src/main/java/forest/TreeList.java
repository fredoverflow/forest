package forest;

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
