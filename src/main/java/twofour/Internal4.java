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
    int depth() {
        return 1 + a.depth();
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
    public TreeList set(int index, String value) {
        if (index < aCount) return new Internal4(a.set(index, value), b, c, d);
        if (index < abCount) return new Internal4(a, b.set(index - aCount, value), c, d);
        if (index < abcCount) return new Internal4(a, b, c.set(index - abCount, value), d);
        return new Internal4(a, b, c, d.set(index - abcCount, value));
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
        if (index < abCount || index == abCount && b.slots() <= c.slots()) {
            TreeList B = b.insert(index - aCount, value);
            if (!(B instanceof Internal2Split)) return new Internal4(a, B, c, d);

            Internal2Split S = (Internal2Split) B;
            Internal3 aB = new Internal3(this.a, S.a, S.b);
            Internal2 cd = new Internal2(c, d);
            return new Internal2Split(aB, cd);
        }
        if (index < abcCount || index == abcCount && c.slots() <= d.slots()) {
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
    TreeList deleteHelper(int index) {
        if (index < aCount) {
            TreeList A = a.deleteHelper(index);
            if (A == TreeList.EMPTY) return new Internal3(b, c, d);
            if (!(A instanceof Orphaned)) return new Internal4(A, b, c, d);
            TreeList B = b.shiftLeftOrMergeWith(A);
            if (B instanceof Orphaned) return new Internal3(B.first(), c, d);
            return new Internal4(B.first(), B.second(), c, d);
        }
        if (index < abCount) {
            TreeList B = b.deleteHelper(index - aCount);
            if (B == TreeList.EMPTY) return new Internal3(a, c, d);
            if (!(B instanceof Orphaned)) return new Internal4(a, B, c, d);
            TreeList C = c.shiftLeftOrMergeWith(B);
            if (C instanceof Orphaned) return new Internal3(a, C.first(), d);
            return new Internal4(a, C.first(), C.second(), d);
        }
        if (index < abcCount) {
            TreeList C = c.deleteHelper(index - abCount);
            if (C == TreeList.EMPTY) return new Internal3(a, b, d);
            if (!(C instanceof Orphaned)) return new Internal4(a, b, C, d);
            TreeList B = b.shiftRightOrMergeWith(C);
            if (B instanceof Orphaned) return new Internal3(a, B.first(), d);
            return new Internal4(a, B.first(), B.second(), d);
        }
        {
            TreeList D = d.deleteHelper(index - abcCount);
            if (D == TreeList.EMPTY) return new Internal3(a, b, c);
            if (!(D instanceof Orphaned)) return new Internal4(a, b, c, D);
            TreeList C = c.shiftRightOrMergeWith(D);
            if (C instanceof Orphaned) return new Internal3(a, b, C.first());
            return new Internal4(a, b, C.first(), C.second());
        }
    }

    @Override
    TreeList shiftLeftOrMergeWith(TreeList leftOrphaned) {
        // shift
        return new Internal2(new Internal2(leftOrphaned.first(), a), new Internal3(b, c, d));
    }

    @Override
    TreeList shiftRightOrMergeWith(TreeList rightOrphaned) {
        // shift
        return new Internal2(new Internal3(a, b, c), new Internal2(d, rightOrphaned.first()));
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
