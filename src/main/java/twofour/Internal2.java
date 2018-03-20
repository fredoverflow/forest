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
    int depth() {
        return 1 + a.depth();
    }

    @Override
    int slots() {
        return 2;
    }

    @Override
    TreeList first() {
        return a;
    }

    @Override
    TreeList second() {
        return b;
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
    public TreeList set(int index, String value) {
        if (index < aCount) return new Internal2(a.set(index, value), b);
        return new Internal2(a, b.set(index - aCount, value));
    }

    @Override
    public TreeList insert(int index, String value) {
        if (index < aCount || index == aCount && a.slots() <= b.slots()) {
            TreeList A = a.insert(index, value);
            if (!(A instanceof Internal2Split)) return new Internal2(A, b);

            Internal2Split S = (Internal2Split) A;
            return new Internal3(S.a, S.b, b);
        }
        {
            TreeList B = b.insert(index - aCount, value);
            if (!(B instanceof Internal2Split)) return new Internal2(a, B);

            Internal2Split S = (Internal2Split) B;
            return new Internal3(a, S.a, S.b);
        }
    }

    @Override
    TreeList deleteHelper(int index) {
        if (index < aCount) {
            TreeList A = a.deleteHelper(index);
            if (A == TreeList.EMPTY) return new Orphaned(b);
            if (!(A instanceof Orphaned)) return new Internal2(A, b);
            return b.shiftLeftOrMergeWith(A);
        }
        {
            TreeList B = b.deleteHelper(index - aCount);
            if (B == TreeList.EMPTY) return new Orphaned(a);
            if (!(B instanceof Orphaned)) return new Internal2(a, B);
            return a.shiftRightOrMergeWith(B);
        }
    }

    @Override
    TreeList shiftLeftOrMergeWith(TreeList leftOrphaned) {
        // merge
        return new Orphaned(new Internal3(leftOrphaned.first(), a, b));
    }

    @Override
    TreeList shiftRightOrMergeWith(TreeList rightOrphaned) {
        // merge
        return new Orphaned(new Internal3(a, b, rightOrphaned.first()));
    }

    @Override
    void appendTo(StringBuilder sb) {
        sb.append('(');
        a.appendTo(sb);
        sb.append(' ');
        b.appendTo(sb);
        sb.append(')');
    }
}
