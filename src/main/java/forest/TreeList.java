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

    public abstract TreeList add(int index, String value);

    abstract TreeList addHelper(int index, String value);

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
        public TreeList add(int index, String value) {
            return new Leaf(value);
        }

        @Override
        TreeList addHelper(int index, String value) {
            throw new AssertionError("TreeList.EMPTY.addHelper");
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
        TreeList result = EMPTY;
        for (int i = 0; i < values.length; ++i) {
            result = result.add(i, values[i]);
        }
        return result;
    }
}
