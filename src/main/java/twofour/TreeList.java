package twofour;

public abstract class TreeList {
    abstract int slots();

    public abstract int size();

    public abstract String get(int index);

    public abstract TreeList insert(int index, String value);

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        appendTo(sb);
        return sb.toString();
    }

    abstract void appendTo(StringBuilder sb);

    public static final TreeList EMPTY = new TreeList() {
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
        public TreeList insert(int index, String value) {
            if (index != 0) throw new IllegalArgumentException("TreeList.EMPTY.insert(" + index + ")");

            return new Leaf1(value);
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
