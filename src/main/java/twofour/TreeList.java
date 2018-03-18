package twofour;

public abstract class TreeList {
    public abstract int size();

    public abstract String get(int index);

    public abstract TreeList insert(int index, String value);

    public static final TreeList EMPTY = new TreeList() {
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

            return new Leaf(value);
        }
    };
}
