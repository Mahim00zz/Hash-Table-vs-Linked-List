import java.util.Iterator;
import java.util.Vector;




public class LinkedList<E extends KeyedElementInterface<K>, K> implements ListInterface<E, K> {

    // Define the Node class
    private static class Node<E extends KeyedElementInterface<K>, K> {
        private E element;
        private Node<E, K> next;

        public Node(E element, Node<E, K> next) {
            this.element = element;
            this.next = next;
        }
    }

    // Fields
    private Node<E, K> head;
    private int size;

    // Constructor
    public LinkedList() {
        head = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public ListInterface<E, K> copy() {
        LinkedList<E, K> copyList = new LinkedList<>();
        Node<E, K> current = head;
        while (current != null) {
            // Create a new instance of the element by copying its contents
            E elementCopy = (E) current.element.copy(); // Assuming the element has a copy method
            // Add the copied element to the copied list
            copyList.add(elementCopy);
            current = current.next;
        }
        return copyList;
    }





    @Override
    public E add(E element) {
        // Check if element with the same key already exists
        Node<E, K> current = head;
        Node<E, K> prev = null;
        while (current != null) {
            if (current.element.getKey().equals(element.getKey())) {
                E replacedElement = current.element;
                // Replace the existing element
                current.element = element;
                return replacedElement;
            }
            prev = current;
            current = current.next;
        }

        // Add the element to the end of the list
        if (head == null) {
            head = new Node<>(element, null);
        } else {
            Node<E, K> newNode = new Node<>(element, null);
            current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
        return null;
    }


    @Override
    public E get(K key) {
        Node<E, K> current = head;
        while (current != null) {
            if (current.element.getKey().equals(key)) {
                return current.element;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public E replace(E element) {
        Node<E, K> current = head;
        while (current != null) {
            if (current.element.getKey().equals(element.getKey())) {
                E replacedElement = current.element;
                current.element = element;
                return replacedElement;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public E remove(K key) {
        Node<E, K> current = head;
        Node<E, K> prev = null;
        while (current != null) {
            if (current.element.getKey().equals(key)) {
                if (prev == null) {
                    head = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.element;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    @Override
    public void removeAll() {
        head = null;
        size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        Vector<E> elements = new Vector<>();
        Node<E, K> current = head;
        while (current != null) {
            elements.add(current.element);
            current = current.next;
        }
        return new ElementIterator<>(elements);
    }







}
