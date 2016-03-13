//Assignment 8
//partner1-Leyi Qiang
//partner1-Drunkbug
//partner2-Ranran He
//partner2-heranran


// represent the class ANode
abstract class ANode<T> {
    ANode<T> next;
    ANode<T> prev;

    // EFFECT: count the number of Nodes int the ANode
    abstract int sizeHelper();

    // EFFECT: remove the Node from ANode
    abstract T removeHelper();

    // takes an IPred<T> and produces the first node
    // in this ANode for which the given predicate returns true
    abstract ANode<T> findHelper(IPred<T> pred);

    // remove the given node in ANode class
    abstract void removeNodeHelper();
}

/*
 * this.next ... ANode<T>
 * this.prev ... ANode<T>
 */
