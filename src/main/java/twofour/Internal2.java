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
    int slots() {
        return 2;
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
    void appendTo(StringBuilder sb) {
        sb.append('(');
        a.appendTo(sb);
        sb.append(' ');
        b.appendTo(sb);
        sb.append(')');
    }
}
