import java.sql.SQLOutput;

public class LinkedList <T extends Comparable<T>> implements List<T> {
    private Node<T> head;
    private int length;
    private boolean isSorted;
    private Node<T> pointer;
    private boolean isEmpty;
    private int size;

    public LinkedList() {
        head = new Node<T>(null);
        pointer = head;
        isSorted = true;
        isEmpty = true;
        length = 0;
    }

    @Override
    public boolean add(T element) {
        if (element == null) {
            return false;
        }
        Node<T> nodenew = new Node<>(element);
        Node<T> sheader = head.getNext();
        isSorted = isSortedHelper();
        if (sheader == null) {
            head.setNext(nodenew);
            length++;
            isSorted = true;
            return true;

        } else {
            while ((sheader.getNext() != null)) {
                sheader = sheader.getNext();
            }
            sheader.setNext(nodenew);
            length++;
            isSorted = isSortedHelper();
        }
//        pointer = head.getNext();
//        boolean currentsort = false;
//        while(pointer.getNext() != null){
//            if(pointer.getData().compareTo(pointer.getNext().getData()) > 0){
//                pointer = pointer.getNext();
//                currentsort = true;
//                isSorted = isSortedHelper();
//
//            }else{
//                isSorted = false;
//                break;
//            }
//        }
        return true;
    }

    @Override
    public boolean add(int index, T element) {
        if (element == null || index > this.size() || index < 0) {
            return false;
        }
        Node<T> addelement = new Node<>(element);
        Node<T> trailer = head;
        pointer = head.getNext();
        int count = 0;
        while (pointer != null) {
            System.out.println("count is " + count + ", index is " + index);
            if (count == index) {
                System.out.println("if statement");
                addelement.setNext(pointer);
                trailer.setNext(addelement);
                length++;
                isSorted = isSortedHelper();
                return true;
            } else {
                System.out.println("else statement");
                trailer = pointer;
                pointer = pointer.getNext();
                System.out.println("pointer is " + pointer.getData());
                count++;
            }
        }
//        pointer = head.getNext();
//        boolean currentsort = false;
//        while (pointer.getNext() != null) {
//            if (pointer.getData().compareTo(pointer.getNext().getData()) < 0) {
//                pointer = pointer.getNext();
//            } else {
//                length++;
//                currentsort = true;
//                break;
//            }
//        }
        return true;
        }


    @Override
    public void clear() {
        head.setNext(null);
        length = 0;
        isSorted = true;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size()) {
            return null;
        }
        int i = 0;
        Node<T> p = head.getNext();
        while (i < index) {
            p = p.getNext();
            i++;
        }
        return p.getData();
    }

    @Override
    public int indexOf(T element) {
        int count = 0;
        Node<T> pointers = head.getNext();
        while (element != null && pointers != null) {
            if (pointers.getData() == element) {
                return count;
            } else {
                count++;
                pointers = pointers.getNext();
            }
        }
        return -1;
    }


    @Override
    public boolean isEmpty() {
        if (length == 0) {
            isSorted = true;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int size() {
        return this.length;
        //returns one less than actual
    }

    @Override
    public void sort() {
        if (isSorted || head.getNext() == null) {
            // If the list is already sorted or empty, no need to sort
            return;
        }

        Node<T> sorted = null;
        Node<T> current = head.getNext();

        while (current != null) {
            Node<T> next = current.getNext();

            if (sorted == null || sorted.getData().compareTo(current.getData()) > 0) {
                    // Insert at the beginning of the sorted list
                current.setNext(sorted);
                sorted = current;
            } else {
                    // Insert into the sorted list
                Node<T> temp = sorted;
                while (temp.getNext() != null && temp.getNext().getData().compareTo(current.getData()) <= 0) {
                    temp = temp.getNext();
                }
                current.setNext(temp.getNext());
                temp.setNext(current);
                }

                current = next;
            }

            // Update the head to point to the sorted list
            head.setNext(sorted);
            isSorted = isSortedHelper(); //sets isSorted value to true or 
        }


        @Override
    public T remove(int index) {
        if (index < 0 || index >= size()) {
            return null; // Index out of bounds
        }
        //System.out.println("pointer" + pointer.getData());
        int count = 0;
        Node<T> current = this.head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        T removedElement = current.getNext().getData();
        current.setNext(current.getNext().getNext());
        length--;
        if (index > 0 && !isSorted) {
            Node<T> p = head.getNext();
            while (p.getNext() != null && p.getData().compareTo(p.getNext().getData()) > 0) {
                isSorted = true;
                break;
            }
        }
        isSorted = isSortedHelper();
        return removedElement;
    }
    @Override
    public void equalTo(T element) {
        if (element == null) {
            return; // If element is null, don't do anything
        }

        Node<T> current = head.getNext();
        Node<T> trailer = head;

        while (current != null) {
            if (current.getData().compareTo(element) != 0) {
                // Remove the current node if it does not match the target element
                trailer.setNext(current.getNext());
                length--;

            } else {
                // Move to the next node
                trailer = current;
            }
            current = current.getNext();
            isSorted = isSortedHelper();

        }
        isSorted = isSortedHelper();

        // Update isSorted accordingly
    }


    @Override
    public void reverse() {
        Node <T> prev = null;
        Node<T> current = head.getNext();
        Node <T> next;
        while (current != null){
            next = current.getNext();
            current.setNext(prev);
            prev = current;
            current = next;
        }
        head.setNext(prev);
        isSorted = isSortedHelper();
    }

    @Override
    public void intersect(List<T> otherList) {
        if (otherList == null || otherList.isEmpty()) {
            // If the other list is null or empty, there can be no intersection
            clear();
            return;
        }

        LinkedList<T> intersection = new LinkedList<>();

        Node<T> current = head.getNext();
        while (current != null) {
            if (otherList.indexOf(current.getData()) != -1) {
                intersection.add(current.getData());
            }
            current = current.getNext();
        }

        // Update the current list to contain only the elements in the intersection
        head.setNext(intersection.head.getNext());
        length = intersection.size();
        isSorted = isSortedHelper();

    }

    @Override
    public T getMin() {
        if(isEmpty()){
            return null;
        }
        Node <T> current = head.getNext();
        T currentmin;
        currentmin = current.getData();
        System.out.println(currentmin);
        while (current != null){
            if(current.getData().compareTo(currentmin) < 0){
                System.out.println("pointer.getData"+ pointer.getData());
                System.out.println("currentmin" + currentmin);
                currentmin = current.getData();
            }
            current = current.getNext();
        }
        System.out.println(currentmin);
        return currentmin;
    }

    @Override
    public T getMax() {
        if(isEmpty()){
            return null;
        }
        Node <T> current = head.getNext();
        T currentmax;
        currentmax = current.getData();
        System.out.println(currentmax);
        //size 0
        //if its sorted
        while (current != null){
            if(current.getData().compareTo(currentmax) > 0){
                System.out.println("pointer.getData"+ pointer.getData());
                System.out.println("currentmin" + currentmax);
                currentmax = current.getData();
            }
            current = current.getNext();
        }
        System.out.println(currentmax);
        return currentmax;
    }
    public boolean isSortedHelper(){
        Node<T> current = head.getNext();
        for (int i = 0; i < length - 1; i++){
            if (current.getData().compareTo(current.getNext().getData()) > 0) {
                return false;
            }
            current = current.getNext();
        }
        return true;
    }

    @Override
    public boolean isSorted() {
        return isSorted;
    }

    public void print() {
        Node<T> temp = head;
        System.out.println("size:" + length);
        while (temp!=null) {
            System.out.println(temp.getData());
            temp=temp.getNext();
        }
    }

    public static void main(String[] args){
        LinkedList<Integer> list = new LinkedList<>();
        list.add(2);
        list.add(1);
        list.add(3);
        list.add(4);
        list.add(5);
        System.out.println("size:" + list.size());
        list.print();

    }
}