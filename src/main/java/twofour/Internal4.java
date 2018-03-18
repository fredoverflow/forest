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
        if (index < aCount || index == aCount && a.slots() <= b.slots()) {
            TreeList A = a.insert(index, value);
            if (!(A instanceof Internal2Split)) return new Internal4(A, b, c, d);

            Internal2Split S = (Internal2Split) A;
            Internal3 Ab = new Internal3(S.a, S.b, b);
            Internal2 cd = new Internal2(c, d);
            return new Internal2Split(Ab, cd);
        }
        if (index < abCount || index == abCount && b.slots() <= c.slots())
        {
            TreeList B = b.insert(index - aCount, value);
            if (!(B instanceof Internal2Split)) return new Internal4(a, B, c, d);

            Internal2Split S = (Internal2Split) B;
            Internal3 aB = new Internal3(this.a, S.a, S.b);
            Internal2 cd = new Internal2(c, d);
            return new Internal2Split(aB, cd);
        }
        if (index < abcCount ||index == abcCount && c.slots() <= d.slots())
        {
            TreeList C = c.insert(index - abCount, value);
            if (!(C instanceof Internal2Split)) return new Internal4(a, b, C, d);

            Internal2Split S = (Internal2Split) C;
            Internal2 ab = new Internal2(this.a, b);
            Internal3 Cd = new Internal3(S.a, S.b, d);
            return new Internal2Split(ab, Cd);
        }
        {
            TreeList D = d.insert(index - abcCount, value);
            if (!(D instanceof Internal2Split)) return new Internal4(a, b, c, D);

            Internal2Split S = (Internal2Split) D;
            Internal2 ab = new Internal2(this.a, b);
            Internal3 cD = new Internal3(c, S.a, S.b);
            return new Internal2Split(ab, cD);
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
