package eg.edu.alexu.csd.oop.game67;


import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class iterator<T> implements Iterator<T> {
    private List<T> list;
    private int i = 0;

    public iterator(List<T> list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        if(i >= list.size()|| list.get(i)==null)
            return false;
        else
            return true;
    }

    @Override
    public T next() {
        return list.get(i++);
    }

    @Override
    public void remove() {
        list.remove(--i);
    }

    @Override
    public void forEachRemaining(Consumer<? super T> action) {

    }
}