/*
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable
*/

import java.util.*;
import java.util.function.Consumer;

class MyArrayList<E> implements List<E> {
    private Object[] objects;
    private int itemCount = 0;

    public MyArrayList(int size) {
        objects = new Object[size];
        System.out.println(objects.length);
        itemCount = 0;
    }

    public E get(int index) {
        rangeCheck(index);
        return (E) objects[index];
    }

    public E set(int index, E element) {
        rangeCheck(index);
        E oldObj = (E) objects[index];
        objects[index] = element;
        return oldObj;
    }

    private Object[] grow() {
        return objects = Arrays.copyOf(objects, objects.length * 2);
    }

    static <E> E elementAt(Object[] objList, int index) {
        return (E) objList[index];
    }

    public E remove(int index) {
        rangeCheck(index);
        E oldValue = (E) objects[index];
        int newSize = itemCount - 1;
        if (newSize > index)
            System.arraycopy(objects, index + 1, objects, index, newSize - index);
        objects[itemCount = newSize] = null;
        return oldValue;
    }

    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    public ListIterator<E> listIterator(int index) {
        rangeCheck(index);
        return new MyListItr(index);
    }

    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    public int size() {
        return this.itemCount;
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    public Iterator<E> iterator() {
        return new MyListItr(0);
    }

    public Object[] toArray() {
        return Arrays.copyOf(objects, objects.length);
    }

    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    public boolean add(E e) {
        add(e, objects/*, size*/);
        return true;
    }

    private void add(E e, Object[] objects/*, int size*/) {
        if (objects.length == itemCount)
            objects = grow();
        objects[itemCount] = e;
        itemCount++;
    }


    public void add(int index, E element) {
        rangeCheck(index);
        Object[] newObjects = objects;
        if (itemCount == objects.length)
        {
            newObjects = grow();
        }
        System.arraycopy(objects, index,
                newObjects, index + 1,
                objects.length - index);
        objects[index] = element;
        itemCount++;
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    private void rangeCheck(int index) {
        if (index < 0 || index > itemCount)
            throw new IndexOutOfBoundsException(index);
    }


    private class MyIterator implements Iterator<E> {
        int cursor;
        int lastItem = -1;

        MyIterator() { }

        public boolean hasNext() {
            return cursor != itemCount;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            //System.out.println("itemCount = " + itemCount + "; cursor = " + cursor);
            int i = cursor;
            if (i >= itemCount)
                throw new NoSuchElementException();
            Object[] localObjects = objects;
            if (i >= localObjects.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            //System.out.println("item[" + i + "] = " + localObjects[i]);
            return (E) localObjects[lastItem = i];
        }

        public void remove() {
            if (lastItem < 0)
                throw new IllegalStateException();
            try {
                MyArrayList.this.remove(lastItem);
                cursor = lastItem;
                lastItem = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            final int localSize = itemCount;
            int i = cursor;
            if (i < localSize) {
                final Object[] es = objects;
                if (i >= es.length)
                    throw new ConcurrentModificationException();
                for (; i < localSize /*&&  modCount == expectedModCount*/; i++)
                    action.accept(elementAt(es, i));
                // update once at end to reduce heap write traffic
                cursor = i;
                lastItem = i - 1;
            }
        }
    }


    private class MyListItr extends MyIterator implements ListIterator<E> {
        MyListItr(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        @SuppressWarnings("unchecked")
        public E previous() {
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] elementData = objects;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i;
            return (E) elementData[lastItem = i];
        }

        public void set(E e) {
            if (lastItem < 0)
                throw new IllegalStateException();

            try {
                MyArrayList.this.set(lastItem, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(E e) {
            try {
                int i = cursor;
                MyArrayList.this.add(i, e);
                cursor = i + 1;
                lastItem = -1;
                //expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    public void sort(Comparator<? super E> c) {
        //final int expectedModCount = modCount;
        Arrays.sort((E[]) objects, 0, itemCount, c);
        //if (modCount != expectedModCount)
            //throw new ConcurrentModificationException();
        //modCount++;
    }
}
/*

то есть нужно написать только те методы, которые позволяют работать
addAll(Collection<? super T> c, T... elements)
static <T> void copy(List<? super T> dest, List<? extends T> src)
static <T> void sort(List<T> list, Comparator<? super T> c)
из java.util.Collections
корректно

 */