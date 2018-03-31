package twofour;

import java.util.Collections;
import java.util.Iterator;
import java.util.function.Consumer;

public abstract class TreeList implements Iterable<String> {
    abstract int height();

    abstract int slots();

    TreeList slot(int index) {
        throw new AssertionError();
    }

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

    @Override
    public Iterator<String> iterator() {
        return new TreeListIterator(this);
    }

    private static class TreeListIterator implements Iterator<String> {
        TreeList[] path;
        long index;

        TreeListIterator(TreeList treeList) {
            final int height = treeList.height();
            path = new TreeList[height];
            index = 0;

            path[height - 1] = treeList;
            for (int i = height - 1; i > 0; --i) {
                path[i - 1] = path[i].slot(0);
            }
        }

        @Override
        public boolean hasNext() {
            return path != null;
        }

        @Override
        public String next() {
            String result = path[0].get(currentIndex());
            final int height = path.length;
            int i;
            for (i = 0; i < height; ++i) {
                if (currentIndex() + 1 < path[i].slots()) break;
                popIndex();
            }
            if (i == height) {
                path = null;
            } else {
                ++index;
                for (; i > 0; --i) {
                    path[i - 1] = path[i].slot(currentIndex());
                    pushIndex();
                }
            }
            return result;
        }

        int currentIndex() {
            return (int) index & 3;
        }

        void popIndex() {
            index >>>= 2;
        }

        void pushIndex() {
            index <<= 2;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TreeList)) return false;
        TreeList that = (TreeList) obj;
        if (this.size() != that.size()) return false;

        Iterator<String> a = this.iterator();
        Iterator<String> b = that.iterator();
        while (a.hasNext()) {
            if (!a.next().equals(b.next())) return false;
        }
        return true;
        // note: hashCode() is implemented in leaves and internal nodes!
    }

    public static final TreeList EMPTY = new TreeList() {
        @Override
        int height() {
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

        @Override
        public Iterator<String> iterator() {
            return Collections.emptyIterator();
        }

        @Override
        public boolean equals(Object obj) {
            // There is only ever one empty list instance in memory.
            return this == obj;
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
