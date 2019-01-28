package ru.otus.hw3arraylist;

import java.util.*;
import java.util.function.Consumer;

class MyArrayList<E> implements List<E> {
    private Object[] objects;
    private int itemCount = 0;

    public MyArrayList(int size) {
        objects = new Object[size];
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

    private Object[] grow(Object[] objects) {
        Object[] result = Arrays.copyOf(objects, objects.length * 2);
        return result;
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
        if (objects.length == itemCount)
            objects = grow(objects);
        objects[itemCount] = e;
        itemCount++;
        return true;
    }

    public void add(int index, E element) {
        rangeCheck(index);
        Object[] newObjects = objects;
        if (itemCount == objects.length)
        {
            newObjects = grow(objects);
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

    private class MyListItr implements ListIterator<E> {
        int cursor;
        int lastItem = -1;

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
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }


        public boolean hasNext() {
            return cursor != itemCount;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            int i = cursor;
            if (i >= itemCount)
                throw new NoSuchElementException();
            Object[] localObjects = objects;
            if (i >= localObjects.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
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
                for (; i < localSize; i++)
                    action.accept(elementAt(es, i));
                cursor = i;
                lastItem = i - 1;
            }
        }
    }

    public void sort(Comparator<? super E> c) {
        Arrays.sort((E[]) objects, 0, itemCount, c);
    }
}