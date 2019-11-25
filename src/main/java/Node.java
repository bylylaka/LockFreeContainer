import java.util.concurrent.atomic.AtomicMarkableReference;

class Node<T extends Comparable<T>> {
    private T value;
    private final AtomicMarkableReference<Node<T>> next;

    Node(T value, Node<T> next) {
        this.value = value;
        this.next = new AtomicMarkableReference<>(next, false);
    }

    T value() {
        return value;
    }

    Node<T> getNext() {
        return next.getReference();
    }

    boolean casNext(Node<T> old, Node<T> next) {
        return this.next.compareAndSet(old, next, false, false);
    }

    boolean mark(Node<T> next) {
        return this.next.compareAndSet(next, next, false, true);
    }

    boolean isMarked() {
        return next.isMarked();
    }
}