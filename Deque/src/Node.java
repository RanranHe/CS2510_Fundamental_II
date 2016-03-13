// represent the class Node 
class Node<T> extends ANode<T> {
    T data;

    // the constructor
    Node(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    Node(T data, ANode<T> next, ANode<T> prev) {
        this(data);
        this.next = next;
        this.prev = prev;
        if (next == null || prev == null) {
            throw new IllegalArgumentException("trying to add null into Node");
        }
        else {
            next.prev = this;
            prev.next = this;
        }
    }
    
    /*
     * this.data ... T
     * this.next ... ANode<T>
     * this.prev ... ANode<T>
     * 
     * this.sizeHelper() ... int 
     * this.removeHelper() ... T
     * this.findHelper(IPred<T>) ... ANode<T> 
     * this.removeNodeHeloer() ... void
     * 
     * this.next.sizeHelper() ... int 
     * this.next.findHelper(IPred<T>) ... ANode<T> 
     * this.next.sizeHelper() ... int 
     * this.next.findHelper(IPred<T>) ... ANode<T> 
     */

    // count the number of nodes in the Node
    int sizeHelper() {
        return 1 + this.next.sizeHelper();
    }

    // EFFECT: remove the node from Node
    T removeHelper() {
        prev.next = this.next;
        next.prev = this.prev;
        return this.data;
    }

    // takes an IPred<T> and produces the first node
    // in this Node for which the given predicate returns true
    ANode<T> findHelper(IPred<T> pred) {
        if (pred.apply(this.data)) {
            return this;
        }
        else {
            return this.next.findHelper(pred);
        }
    }

    // remove the given node in Node class
    void removeNodeHelper() {
        this.removeHelper();
    }

}