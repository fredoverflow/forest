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
    int slots() {
        return 3;
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
        if (index < aCount || index == aCount && a.slots() <= b.slots()) {
            TreeList A = a.insert(index, value);
            if (!(A instanceof Internal2Split)) return new Internal3(A, b, c);

            Internal2Split S = (Internal2Split) A;
            return split(S.a, S.b, b, c);
        }
        if (index < abCount || index == abCount && b.slots() <= c.slots())
        {
            TreeList B = b.insert(index - aCount, value);
            if (!(B instanceof Internal2Split)) return new Internal3(a, B, c);

            Internal2Split S = (Internal2Split) B;
            return split(a, S.a, S.b, c);
        }
        {
            TreeList C = c.insert(index - abCount, value);
            if (!(C instanceof Internal2Split)) return new Internal3(a, b, C);

            Internal2Split S = (Internal2Split) C;
            return split(a, b, S.a, S.b);
        }
    }

    private static TreeList split(TreeList a, TreeList b, TreeList c, TreeList d) {
        return new Internal2Split(new Internal2(a, b), new Internal2(c, d));
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
