package forest;

import java.util.Iterator;
import java.util.function.Consumer;

class Leaf extends TreeList {
    private final String value;

    Leaf(String value) {
        this.value = value;
    }

    @Override
    int blackHeight() {
        return 1;
    }

    @Override
    void checkRed() {
    }

    @Override
    TreeList plusBlack() {
        return new DoubleBlack(this);
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public String get(int index) {
        return value;
    }

    @Override
    public TreeList set(int index, String value) {
        if (index != 0) throw new IllegalArgumentException("index: " + index);
        return new Leaf(value);
    }

    @Override
    public TreeList add(int index, String value) {
        Leaf that = new Leaf(value);
        if (index == 0) return new Black(that, 1, this);
        if (index == 1) return new Black(this, 1, that);
        throw new IllegalArgumentException("index: " + index);
    }

    @Override
    TreeList addHelper(int index, String value) {
        Leaf that = new Leaf(value);
        if (index == 0) return new Red(that, 1, this);
        if (index == 1) return new Red(this, 1, that);
        throw new IllegalArgumentException("index: " + index);
    }

    @Override
    public TreeList remove(int index) {
        return EMPTY;
    }

    @Override
    TreeList removeHelper(int index) {
        return null;
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            String element = value;

            @Override
            public boolean hasNext() {
                return element != null;
            }

            @Override
            public String next() {
                String result = element;
                element = null;
                return result;
            }
        };
    }

    @Override
    public void forEach(Consumer<? super String> action) {
        action.accept(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
