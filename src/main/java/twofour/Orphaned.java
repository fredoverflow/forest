package twofour;

import java.util.function.Consumer;

class Orphaned extends TreeList {
    final TreeList orphan;

    Orphaned(TreeList orphan) {
        this.orphan = orphan;
    }

    @Override
    int depth() {
        throw new AssertionError();
    }

    @Override
    int slots() {
        throw new AssertionError();
    }

    @Override
    TreeList first() {
        return orphan;
    }

    @Override
    public int size() {
        throw new AssertionError();
    }

    @Override
    public String get(int index) {
        throw new AssertionError();
    }

    @Override
    public TreeList set(int index, String value) {
        throw new AssertionError();
    }

    @Override
    public TreeList insert(int index, String value) {
        throw new AssertionError();
    }

    @Override
    TreeList deleteHelper(int index) {
        throw new AssertionError();
    }

    @Override
    void appendTo(StringBuilder sb) {
        sb.append("~~~");
        sb.append(orphan);
        sb.append("~~~");
    }

    @Override
    public void forEach(Consumer<? super String> action) {
        throw new AssertionError();
    }
}
