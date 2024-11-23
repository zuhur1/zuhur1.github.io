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
        // create a new node containing the element as data
        isSorted = isSortedHelper();
        // checks if list is sorted
        if (sheader == null) {
            // if sheader is null, then the list is empty so set new node as the first node
            head.setNext(nodenew);
            length++;
            isSorted = true;
            // isSort is true because list only has one node
            return true;

        } else {
            // if the list isn't empty, loop through until the last element, increasing length and sets node new as last node, checks if list issorted is true
            while ((sheader.getNext() != null)) {
                sheader = sheader.getNext();
            }
            sheader.setNext(nodenew);
            length++;
            isSorted = isSortedHelper();
        }
        return true;
    }

    @Override
    public boolean add(int index, T element) {
        if (element == null || index > this.size() || index < 0) {
            // checks if element is null or index is out of bounds
            return false;
        }
        Node<T> addelement = new Node<>(element);
        Node<T> trailer = head;
        // created two new node, a trailer to be one node behind the pointer as it traverses the list and a node with element as its data
        pointer = head.getNext();
        int count = 0;
        while (pointer != null) {
            if (count == index) {
                // if pointer is at desired index, set between trailer and pointer, length increases and issorted is adjusted accordingly
                addelement.setNext(pointer);
                trailer.setNext(addelement);
                length++;
                isSorted = isSortedHelper();
                return true;
            } else {
                // if pointer not at desired index, continued to next node in the list
                System.out.println("else statement");
                trailer = pointer;
                pointer = pointer.getNext();
                System.out.println("pointer is " + pointer.getData());
                count++;
            }
        }
        return true;
        }


    @Override
    public void clear() {
        head.setNext(null);
        //sets nodes in the list to null, clearing the list, resets length and isSorted
        length = 0;
        isSorted = true;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size()) {
            // checks if index is out of bounds
            return null;
        }
        int i = 0;
        pointer = head.getNext();
        while (i < index) {
            //loops until i isn't less than index
            pointer = pointer.getNext();
            i++;
        }
        // returns data of the pointer when the pointer reaches desired index
        return pointer.getData();
    }

    @Override
    public int indexOf(T element) {
        int count = 0;
        pointer = head.getNext();
        while (element != null && pointer != null) {
            // loops if the element isn't null and the next node isn't null
            if (pointer.getData() == element) {
                //if data of the node pointer is pointing at == element, returns the index of that node
                return count;
            } else {
                // traverses through list until reach node with element
                count++;
                pointer = pointer.getNext();
            }
        }
        return -1;
    }


    @Override
    public boolean isEmpty() {
        if (length == 0) {
            //checks if it's empty, updates isSorted
            isSorted = true;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int size() {
        return this.length;
        // returns size
    }

    @Override
    public void sort() {
        if (isSorted || head.getNext() == null) {
            // If the list is already sorted or empty, no need to sort
            return;
        }

        Node<T> sorted = null;
        Node<T> current = head.getNext();
        // created
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
        int count = 0;
        Node<T> current = this.head;
        // set current as pointer
        for (int i = 0; i < index; i++) {
            // loops while i is less than index
            current = current.getNext();
        }
        T removedElement = current.getNext().getData();
        // removes the node after the pointer
        current.setNext(current.getNext().getNext());
        // decreases length, checks isSorted
        length--;
        isSorted = isSortedHelper();
        return removedElement;
    }
    @Overridef
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
        // creates three nodes, prev, current and next
        while (current != null){
            //traverses through list
            next = current.getNext();
            current.setNext(prev);
            //switches current and prev then sets current to next
            prev = current;
            current = next;
        }
        head.setNext(prev);
        // sets 1st element as prev, checks isSorted
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
        // sets cuurent as pointer, sets it to first element in list
        T currentmin;
        currentmin = current.getData();
        // initializes currentmin to the data of current
        while (current != null){
            // loops through till end of the list
            if(current.getData().compareTo(currentmin) < 0){
                //checks if the data of current is less currentmin, if true, currentmin changes to data of current
                currentmin = current.getData();
            }
            current = current.getNext();
        }
        return currentmin;
    }

    @Override
    public T getMax() {
        if(isEmpty()){
            return null;
        }
        // checks if list is empty
        Node <T> current = head.getNext();
        T currentmax;
        currentmax = current.getData();
        while (current != null){
            // loops till the end of the list
            if(current.getData().compareTo(currentmax) > 0){
                //checks if data at current is greater than currentmax, if true, changes currentmax as data of current
                currentmax = current.getData();
            }
            current = current.getNext();
        }
        return currentmax;
    }
    public boolean isSortedHelper(){
        Node<T> current = head.getNext();
        //set current as pointer, sets it to 1st element
        for (int i = 0; i < length - 1; i++){
            //loops through length of the list and checks if current data  is less then next node data
            if (current.getData().compareTo(current.getNext().getData()) > 0) {
                return false;
            }
            // traverses through list
            current = current.getNext();
        }
        return true;
    }

    @Override
    public boolean isSorted() {
        // returns boolean isSorted
        return isSorted;
    }

}