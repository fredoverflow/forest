package twofour;

public abstract class TreeList {
    abstract int depth();

    abstract int slots();

    TreeList first() {
        throw new AssertionError();
    }

    TreeList second() {
        throw new AssertionError();
    }

    public abstract int size();

    public abstract String get(int index);

    public abstract TreeList set(int index, String value);

    public abstract TreeList insert(int index, String value);

    public TreeList delete(int index) {
        TreeList temp = deleteHelper(index);
        return temp instanceof Orphaned ? temp.first() : temp;
    }

    abstract TreeList deleteHelper(int index);

    TreeList shiftLeftOrMergeWith(TreeList leftOrphaned) {
        throw new AssertionError();
    }

    TreeList shiftRightOrMergeWith(TreeList rightOrphaned) {
        throw new AssertionError();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        appendTo(sb);
        return sb.toString();
    }

    abstract void appendTo(StringBuilder sb);

    public static final TreeList EMPTY = new TreeList() {
        @Override
        int depth() {
            return 0;
        }

        @Override
        int slots() {
            return 0;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public String get(int index) {
            throw new AssertionError("TreeList.EMPTY.get(" + index + ")");
        }

        @Override
        public TreeList set(int index, String value) {
            throw new AssertionError("TreeList.EMPTY.set(" + index + ", " + value + ")");
        }

        @Override
        public TreeList insert(int index, String value) {
            if (index != 0) throw new IllegalArgumentException("TreeList.EMPTY.insert(" + index + ")");

            return new Leaf1(value);
        }

        @Override
        TreeList deleteHelper(int index) {
            throw new AssertionError("TreeList.EMPTY.deleteHelper(" + index + ")");
        }

        @Override
        public String toString() {
            return "()";
        }

        @Override
        void appendTo(StringBuilder sb) {
            throw new AssertionError("TreeList.EMPTY.appendTo");
        }
    };

    public static TreeList of(String value) {
        return new Leaf1(value);
    }

    public static TreeList of(String a, String b) {
        return new Leaf2(a, b);
    }

    public static TreeList of(String a, String b, String c) {
        return new Leaf3(a, b, c);
    }

    public static TreeList of(String... values) {
        TreeList result = TreeList.EMPTY;
        int index = 0;
        for (String s : values) {
            result = result.insert(index++, s);
        }
        return result;
    }
}
