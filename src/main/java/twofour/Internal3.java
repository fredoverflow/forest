package twofour;

class Internal3 extends TreeList {
    final TreeList a;
    final int aCount;
    final TreeList b;
    final int abCount;
    final TreeList c;

    Internal3(TreeList a, TreeList b, TreeList c) {
        this.a = a;
        aCount = a.size();
        this.b = b;
        abCount = aCount + b.size();
        this.c = c;
    }

    @Override
    public int size() {
        return abCount + c.size();
    }

    @Override
    public String get(int index) {
        if (index < aCount) return a.get(index);
        if (index < abCount) return b.get(index - aCount);
        return c.get(index - abCount);
    }

    @Override
    public TreeList insert(int index, String value) {
        return null;
    }

    @Override
    void appendTo(StringBuilder sb) {
        sb.append('(');
        a.appendTo(sb);
        sb.append(' ');
        b.appendTo(sb);
        sb.append(' ');
        c.appendTo(sb);
        sb.append(')');
    }
}
