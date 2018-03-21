package twofour;

import java.util.function.Consumer;

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

    public abstract void forEach(Consumer<? super String> action);

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

        @Override
        public void forEach(Consumer<? super String> action) {
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
        int len = values.length;
        if (len == 0) return TreeList.EMPTY;

        TreeList[] temp = new TreeList[(len + 2) / 3];
        int i, k;
        for (i = 0, k = 0; k + 2 < len; ++i, k += 3) {
            temp[i] = new Leaf3(values[k], values[k + 1], values[k + 2]);
        }
        switch (len - k) {
            case 0:
                break;
            case 1:
                temp[i++] = new Leaf1(values[k]);
                break;
            case 2:
                temp[i++] = new Leaf2(values[k], values[k + 1]);
                break;
            default:
                throw new AssertionError(len - k + "");
        }
        len = i;
        while (len > 1) {
            for (i = 0, k = 0; k + 3 + 2 < len; ++i, k += 4) {
                temp[i] = new Internal4(temp[k], temp[k + 1], temp[k + 2], temp[k + 3]);
            }
            switch (len - k) {
                case 2:
                    temp[i++] = new Internal2(temp[k], temp[k + 1]);
                    break;
                case 3:
                    temp[i++] = new Internal3(temp[k], temp[k + 1], temp[k + 2]);
                    break;
                case 4:
                    temp[i++] = new Internal4(temp[k], temp[k + 1], temp[k + 2], temp[k + 3]);
                    break;
                case 5:
                    temp[i++] = new Internal3(temp[k], temp[k + 1], temp[k + 2]);
                    temp[i++] = new Internal2(temp[k + 3], temp[k + 4]);
                    break;
                default:
                    throw new AssertionError(len - k + "");
            }
            len = i;
        }
        return temp[0];
    }
}
