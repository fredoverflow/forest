package twofour;

class Orphaned extends TreeList {
    final TreeList orphan;

    Orphaned(TreeList orphan) {
        this.orphan = orphan;
    }

    @Override
    int slots() {
        return 1;
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
    public TreeList insert(int index, String value) {
        throw new AssertionError();
    }

    @Override
    TreeList deleteHelper(int index) {
        throw new AssertionError();
    }

    @Override
    void appendTo(StringBuilder sb) {
        sb.append('<');
        sb.append(orphan);
        sb.append('>');
    }
}
