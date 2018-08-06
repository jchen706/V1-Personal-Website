import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;


/**
 * Recursive doubly-linked list for CS1331 2018 Summer HW9.
 * @author 1331 TAs and Jun Chen
 * @version 1
 * @param <E> Generic type parameter.
 */
public class RecursiveLinkedList<E> implements List<E> {

    /**
     * Do not delete this field! You must use this as your LinkedList head.
     */
    private LinkedListNode<E> head;
    private int size;


    /**
     * No-args constructor. You may add code to this, add other constructors,
     * or just leave this empty, but you may not delete this constructor
     * entirely.
     */
    public RecursiveLinkedList() {

    }

    @Override
    public boolean add(E e) {
        LinkedListNode<E> current = head;
        return add1(e, current);
    }

    private boolean add1(E e, LinkedListNode<E> current) {
        if (head == null) {
            head = new LinkedListNode(e);
            current = head;
            size++;
            return true;
        } else if (current.getNext() == null) {
            current.setNext(new LinkedListNode(e));
            (current.getNext()).setPrev(current);
            size++;
            if (size == 1) {
                head = current;
            }
            return true;
        } else {
            LinkedListNode<E> temp = current;
            current = current.getNext();
            return add1(e, current);
        }
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }



    @Override
    public boolean contains(Object o) {
        LinkedListNode<E> current = head;
        boolean containing = false;
        return contains1(o, current, containing);
    }

    private boolean contains1(Object o, LinkedListNode<E> current,
        boolean containing) {
        if (current == null) {
            return containing;
        } else if ((current.getData()).equals(o)) {
            containing = true;
        } else {
            current = current.getNext();
            return contains1(o, current, containing);
        }
        return containing;
    }




    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (!(o instanceof List)) {
            return false;
        } else if (this.size != ((List) o).size()) {
            return false;
        } else if (this.size == 0 && ((List) o).size() == 0) {
            return true;
        } else {
            Iterator oIterator = ((List) o).iterator();
            boolean equals2 = false;
            return equals1(oIterator, head, equals2);
        }
    }

    private boolean equals1(Iterator oIterator,
        LinkedListNode<E> current, boolean equals2) {

        if (oIterator.hasNext()) {
            System.out.println(current);
            if ((oIterator.next()).equals(current.getData())) {
                equals2 = true;
            } else {
                return  false;
            }
            current = current.getNext();
            return equals1(oIterator, current, equals2);
        }

        return equals2;
    }



    @Override
    public String toString() {
        String str = "[";
        LinkedListNode<E> temp = head;
        if (temp == null) {
            str = "[]";
            return str;
        }

        if (size != 0) {
            str += head.getData();
        }
        temp = temp.getNext();

        for (int i = 0; i < size - 1; i++) {
            if (temp != null) {
                str += ", " + temp.getData();
                temp = temp.getNext();
            }
        }

        return str + "]";
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        for (E element : this) {
            hashCode = 31 * hashCode
                + (element == null ? 0 : element.hashCode());
        }
        return hashCode;
    }


    @Override
    public int indexOf(Object o) {
        int num = 0;
        for (E element : this) {
            if (element.equals(o)) {
                return num;
            }
            num++;
        }
        return -1;
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    /**
    * A Interator for the RecursiveLinkedList
    * @param <E> type E for generic
    */

    private class LinkedListIterator implements Iterator<E> {

        private LinkedListNode<E> current1 = head;

        /**
        * @return boolean if there's has next element
        */
        @Override
        public boolean hasNext() {
            return (current1 != null);
        }

        /**
        * @return value of next element
        */
        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            E e = current1.getData();
            current1 = current1.getNext();
            return e;
        }

    }

    @Override
    public E get(int index) {
        LinkedListNode<E> now = head;

        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(index
                + " is out of bounds in list of size " + this.size);
        }

        for (int i = 0; i < index; i++) {
            now = now.getNext();
            // if (now == null) {
            //     throw new IndexOutOfBoundsException(index +
            // " is out of bounds in list of size " + this.size);
            // }
        }



        return now.getData();


    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean remove(Object o) {
        LinkedListNode<E> current = head;

        if (contains(o)) {
            return remove1(o, current);
        } else {
            return false;
        }

    }

    private boolean remove1(Object o, LinkedListNode<E> current) {
        if (head == null) {
            return false;
        } else if ((head.getData()).equals(o)) {
            if (!(current.getNext() == null)) {
                head = head.getNext();
            } else {
                head = null;
            }
            size--;
            return true;
        } else if ((current.getData()).equals(o)) {
            if (!(current.getNext() == null)) {
                LinkedListNode<E> next1 = current.getNext();
                LinkedListNode<E> prev1 = current.getPrev();
                prev1.setNext(next1);
            } else {
                LinkedListNode<E> prev1 = current.getPrev();
                prev1.setNext(null);
            }

            this.size--;
            return true;
        } else {
            current = current.getNext();
            return remove1(o, current);
        }
    }



    @Override
    public Object[] toArray() {
        LinkedListNode<E> now = head;
        Object[] newArray = new Object[size];
        // Check

        if (head == null) {
            return null;
        }
        for (int i = 0; i < size; i++) {
            newArray[i] = now.getData();
            now = now.getNext();
        }

        return newArray;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean added = false;

        if (c == null) {
            throw new NullPointerException("The collection passed "
                + "in is empty.");
        } else if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException(index
                + " is out of bounds in list of size " + this.size);
        }

        LinkedListNode<E> now = head;
        LinkedListNode<E> temp = head;

        if (index == 0) {
            boolean updated = false;
            for (E e: c) {
                LinkedListNode<E> newNode = new LinkedListNode(e);
                if (!updated) {
                    now.setPrev(newNode);
                    head = newNode;
                    head.setNext(now);
                    now = head;
                    updated = true;
                } else {
                    now.setNext(newNode);
                    now = newNode;
                    now.setNext(temp);
                }
                added = true;
                this.size++;
            }
            return added;
        } else {
            for (int i = 0; i < index - 1; i++) {
                now = now.getNext();
            }
        }


        if (!(now.getNext() == null) && !(now.getPrev() == null)
            && index != 0) {
            for (E e: c) {
                LinkedListNode<E> newNode = new LinkedListNode(e);
                LinkedListNode<E> nowNext = now.getNext();
                now.setNext(newNode);
                newNode.setNext(nowNext);
                now = newNode;
                added = true;
                this.size++;
            }
        } else if (now.getNext() == null) {
            for (E e: c) {
                LinkedListNode<E> newNode = new LinkedListNode(e);
                now.setNext(newNode);
                now = newNode;
                added = true;
                this.size++;
            }
        } else if (!(now.getNext() == null) && index == 0) {
            boolean updated = false;
            for (E e: c) {
                LinkedListNode<E> newNode = new LinkedListNode(e);
                if (!updated) {
                    now.setPrev(newNode);
                    head = newNode;
                    head.setNext(now);
                    now = head;
                    updated = true;
                } else {
                    now.setNext(newNode);
                    now = newNode;
                    now.setNext(temp);
                }
                added = true;
                this.size++;
            }
        }
        return added;
    }


    @Override
    public <T> T[] toArray(T[] a) {
        if (a == null) {
            throw new NullPointerException("The array passed in "
                + "toArray is null.");
        } else if (a.length < size) {
            T[] newArray = (T[]) new Object[size];
            // Check
            LinkedListNode<E> temp = head;
            for (int i = 0; i < size; i++) {
                newArray[i] = (T) temp.getData();
                temp = temp.getNext();
            }
            return newArray;
        } else {
            LinkedListNode<E> temp = head;
            for (int i = 0; i < size; i++) {
                a[i] = (T) temp.getData();
                temp = temp.getNext();
            }
            if (a.length == size) {
                return a;
            }
            a[size] = null;
            return a;
        }
    }

    @Override
    public int lastIndexOf(Object o) {
        int counter = 0;
        int indexNum = 0;
        LinkedListNode<E> current = head;
        if (!contains(o)) {
            return -1;
        }
        return lastIndexOf1(o, counter, indexNum, current);
    }


    private int lastIndexOf1(Object o, int counter, int indexNum,
        LinkedListNode<E> current) {
        if (head == null) {
            return -1;
        } else if (current == null) {
            return indexNum;
        } else {
            if (current.getData().equals(o)) {
                indexNum = counter;
            }
            current = current.getNext();
            counter++;
            return lastIndexOf1(o, counter, indexNum, current);
        }
    }


    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("The collection passed "
                + "for the retainAll method is null.");
        } else if (head == null || size == 0) {
            return false;
        } else {
            LinkedListNode<E> current = head;
            boolean removed = false;
            return retainAll1(c, current, removed);
        }
    }


    private boolean retainAll1(Collection<?> c, LinkedListNode<E> current,
        boolean removed) {
        if (!(current == null)) {
            if (c.contains(current.getData())) {
                current = current.getNext();
                return retainAll1(c, current, removed);
            } else {
                remove(current.getData());
                removed = true;
                current = current.getNext();
                return retainAll1(c, current, removed);
            }
        }

        return removed;

    }



    @Override
    public E set(int index, E element) {
        // You should stub this method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        // You should stub this method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // You should stub this method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        // You should stub this method
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // You should stub this method'
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

}
