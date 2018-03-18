package twofour;

class Internal2 extends TreeList {
    final TreeList a;
    final int aCount;
    final TreeList b;

    Internal2(TreeList a, TreeList b) {
        this.a = a;
        aCount = a.size();
        this.b = b;
    }

    @Override
    public int size() {
        return aCount + b.size();
    }

    @Override
    public String get(int index) {
        if (index < aCount) return a.get(index);
        return b.get(index - aCount);
    }

    @Override
    public TreeList insert(int index, String value) {
        return null;
    }
}
