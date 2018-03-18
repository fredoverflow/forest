package twofour;

class Internal4 extends TreeList {
    final TreeList a;
    final int aCount;
    final TreeList b;
    final int abCount;
    final TreeList c;
    final int abcCount;
    final TreeList d;

    Internal4(TreeList a, TreeList b, TreeList c, TreeList d) {
        this.a = a;
        aCount = a.size();
        this.b = b;
        abCount = aCount + b.size();
        this.c = c;
        abcCount = abCount + c.size();
        this.d = d;
    }

    @Override
    int slots() {
        return 4;
    }

    @Override
    public int size() {
        return abcCount + d.size();
    }

    @Override
    public String get(int index) {
        if (index < aCount) return a.get(index);
        if (index < abCount) return b.get(index - aCount);
        if (index < abcCount) return c.get(index - abCount);
        return d.get(index - abcCount);
    }

    @Override
    public TreeList insert(int index, String value) {
        if (index < abCount || index == abCount && b.slots() <= c.slots())
        {
            TreeList ab = new Internal2(a, b).insert(index, value);
            TreeList cd = new Internal2(c, d);
            return new Internal2Split(ab, cd);
        }
        {
            TreeList ab = new Internal2(a, b);
            TreeList cd = new Internal2(c, d).insert(index - abCount, value);
            return new Internal2Split(ab, cd);
        }
    }

    @Override
    void appendTo(StringBuilder sb) {
        sb.append('(');
        a.appendTo(sb);
        sb.append(' ');
        b.appendTo(sb);
        sb.append(' ');
        c.appendTo(sb);
        sb.append(' ');
        d.appendTo(sb);
        sb.append(')');
    }
}
