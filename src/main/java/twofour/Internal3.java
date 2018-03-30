package twofour;

import java.util.function.Consumer;

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
    int height() {
        return 1 + a.height();
    }

    @Override
    int slots() {
        return 3;
    }

    @Override
    TreeList slot(int index) {
        switch (index) {
            case 0: return a;
            case 1: return b;
            case 2: return c;
        }
        throw new IllegalArgumentException("slot(" + index + ")");
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
    public TreeList set(int index, String value) {
        if (index < aCount) return new Internal3(a.set(index, value), b, c);
        if (index < abCount) return new Internal3(a, b.set(index - aCount, value), c);
        return new Internal3(a, b, c.set(index - abCount, value));
    }

    @Override
    public TreeList insert(int index, String value) {
        if (index < aCount || index == aCount && a.slots() <= b.slots()) {
            TreeList A = a.insert(index, value);
            if (!(A instanceof Internal2Split)) return new Internal3(A, b, c);

            Internal2Split S = (Internal2Split) A;
            return new Internal4(S.a, S.b, b, c);
        }
        if (index < abCount || index == abCount && b.slots() <= c.slots()) {
            TreeList B = b.insert(index - aCount, value);
            if (!(B instanceof Internal2Split)) return new Internal3(a, B, c);

            Internal2Split S = (Internal2Split) B;
            return new Internal4(a, S.a, S.b, c);
        }
        {
            TreeList C = c.insert(index - abCount, value);
            if (!(C instanceof Internal2Split)) return new Internal3(a, b, C);

            Internal2Split S = (Internal2Split) C;
            return new Internal4(a, b, S.a, S.b);
        }
    }

    @Override
    TreeList deleteHelper(int index) {
        if (index < aCount) {
            TreeList A = a.deleteHelper(index);
            if (A == TreeList.EMPTY) return new Internal2(b, c);
            if (!(A instanceof Orphaned)) return new Internal3(A, b, c);
            TreeList B = b.shiftLeftOrMergeWith(A);
            if (B instanceof Orphaned) return new Internal2(B.first(), c);
            return new Internal3(B.first(), B.second(), c);
        }
        if (index < abCount) {
            TreeList B = b.deleteHelper(index - aCount);
            if (B == TreeList.EMPTY) return new Internal2(a, c);
            if (!(B instanceof Orphaned)) return new Internal3(a, B, c);
            TreeList C = c.shiftLeftOrMergeWith(B);
            if (C instanceof Orphaned) return new Internal2(a, C.first());
            return new Internal3(a, C.first(), C.second());
        }
        {
            TreeList C = c.deleteHelper(index - abCount);
            if (C == TreeList.EMPTY) return new Internal2(a, b);
            if (!(C instanceof Orphaned)) return new Internal3(a, b, C);
            TreeList B = b.shiftRightOrMergeWith(C);
            if (B instanceof Orphaned) return new Internal2(a, B.first());
            return new Internal3(a, B.first(), B.second());
        }
    }

    @Override
    TreeList shiftLeftOrMergeWith(TreeList leftOrphaned) {
        // shift
        return new Internal2(new Internal2(leftOrphaned.first(), a), new Internal2(b, c));
    }

    @Override
    TreeList shiftRightOrMergeWith(TreeList rightOrphaned) {
        // shift
        return new Internal2(new Internal2(a, b), new Internal2(c, rightOrphaned.first()));
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

    @Override
    public void forEach(Consumer<? super String> action) {
        a.forEach(action);
        b.forEach(action);
        c.forEach(action);
    }

    @Override
    public int hashCode() {
        return (a.hashCode() * 31 + b.hashCode()) * 31 + c.hashCode();
    }
}
