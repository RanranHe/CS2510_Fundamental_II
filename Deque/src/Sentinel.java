// represent the sentinel
public class Sentinel<T> extends ANode<T> {

    // the constructor
    Sentinel() {
        this.next = this;
        this.prev = this;

    }
    
    /*
     * this.next ... Sentinel<T>
     * this.prev ... Sentinel<T>
     * 
     * this.size() ... int
     * this.addAtHead(T) ... void
     * this.addAtTail(T) ... void
     * this.removeFromHead() ... T 
     * this.removeFromeTail() ... T 
     * this.removeHelper() ... T
     * this.find(IPred<T>) ... ANode<T> 
     * this.findHelper(IPred<T>) ... ANode<T> 
     * this.removeNodeHelper() ... void
     * 
     * this.next.removeFromHead() ... T 
     * this.next.removeFromeTail() ... T 
     * this.next.find(IPred<T>) ... ANode<T> 
     * this.next.findHelper(IPred<T>) ... ANode<T> 
     * this.prev.removeFromHead() ... T 
     * this.prev.prev.removeFromeTail() ... T 
     * this.prev.find(IPred<T>) ... ANode<T> 
     * this.prev.findHelper(IPred<T>) ... ANode<T> 
     */

    // count the number of nodes in the Deque list
    int size() {
        return this.next.sizeHelper();
    }

    // count the number of nodes in the sentinel
    int sizeHelper() {
        return 0;
    }
    
    // EFFECT: add note at the head of sentinel
    void addAtHead(T t) {
        new Node<T>(t, this.next, this);
    }
    
    void addAtTail(T t) {
        new Node<T>(t, this, this.prev);
    }

    // EFFECT: remove the Head of the node from the sentinel
    T removeFromHead() {
        return this.next.removeHelper();
    }

    // EFFECT: remove the tail of the node from the sentinel
    T removeFromTail() {
        return this.prev.removeHelper();
    }

    // EFFECT: remove the node from A Sentinel
    T removeHelper() {
        throw new RuntimeException("Can't try to remove on a Sentinel!");
    }

    // takes an IPred<T> and produces the first node
    // in this Sentinel for which the given predicate returns true
    ANode<T> find(IPred<T> pred) {
        return this.next.findHelper(pred);
    }

    // takes an IPred<T> and produces the first node
    // in this Sentinel for which the given predicate returns true
    ANode<T> findHelper(IPred<T> pred) {
        return this;
    }

    // remove the given node in Sentinel class
    void removeNodeHelper() {
        return;
    }
}
