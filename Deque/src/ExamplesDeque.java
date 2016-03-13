import tester.*;
//Assignment 8
//partner1-Leyi Qiang
//partner1-Drunkbug
//partner2-Ranran He
//partner2-heranran
// represent examples of Deque
public class ExamplesDeque {

    Sentinel<String> sent1;
    Sentinel<String> sent2;
    Sentinel<Integer> sent3;
    Sentinel<String> senttest;

    ANode<String> node1;
    ANode<String> node2;
    ANode<String> node3;
    ANode<String> node4;

    ANode<Integer> nodea;
    ANode<Integer> nodeb;
    ANode<Integer> nodec;
    ANode<Integer> noded;
    ANode<Integer> nodee;

    Deque<String> deque1;
    Deque<String> deque2;
    Deque<Integer> deque3;
    Deque<String> dequetest;

    // initialize the Deque list
    void initDeque() {
        sent1 = new Sentinel<String>();
        sent2 = new Sentinel<String>();
        node1 = new Node<String>("abc", sent2, sent2);
        node2 = new Node<String>("bcd", sent2, node1);
        node3 = new Node<String>("cde", sent2, node2);
        node4 = new Node<String>("def", sent2, node3);

        sent3 = new Sentinel<Integer>();
        nodea = new Node<Integer>(1, sent3, sent3);
        nodeb = new Node<Integer>(2, sent3, nodea);
        nodec = new Node<Integer>(3, sent3, nodeb);
        noded = new Node<Integer>(4, sent3, nodec);
        nodee = new Node<Integer>(5, sent3, noded);

        deque1 = new Deque<String>();
        deque2 = new Deque<String>(this.sent2);
        deque3 = new Deque<Integer>(this.sent3);

        senttest = new Sentinel<String>();
        dequetest = new Deque<String>(this.senttest);
    }

    // test the update method
    void testUpdate(Tester t) {
        // check initialize
        this.initDeque();
        t.checkExpect(this.deque2.header, this.sent2);
        t.checkExpect(this.sent2.prev, this.node4);
        t.checkExpect(this.node1.prev, this.sent2);
        t.checkExpect(this.node2.next, this.node3);
        t.checkExpect(this.node3.prev, this.node1.next);
        t.checkExpect(this.node1 == null, false);
        t.checkExpect(this.sent1.prev, this.sent1);
        t.checkExpect(this.sent1.next.next, this.sent1);
        t.checkExpect(this.sent2.next, this.node1);

    }

    // test the method size
    void testSize(Tester t) {
        // initialize
        this.initDeque();
        t.checkExpect(this.sent2.size(), 4);
        t.checkExpect(this.sent1.size(), 0);
        t.checkExpect(this.deque2.size(), 4);
        t.checkExpect(this.deque1.size(), 0);
        t.checkExpect(this.deque3.size(), 5);
    }

    // NOTE: Supposed to fail!
    ANode<String> constructNullNode() {
        return new Node<String>("", new Node<String>(""), null);
    }

    void testConstructingNullNodes(Tester t) {
        t.checkException(new IllegalArgumentException("trying to add null into Node"), this,
                "constructNullNode");
    }

    // test the method addAtHead
    void testAddAtHead(Tester t) {

        // test the initialize
        initDeque();
        t.checkExpect(this.sent2.next, this.node1);
        t.checkExpect(this.sent1.next, this.sent1);

        // initialize
        initDeque();
        this.deque1.addAtHead("def");
        this.deque1.addAtHead("cde");
        this.deque1.addAtHead("bcd");
        this.deque1.addAtHead("abc");

        t.checkExpect(deque1, deque2);
    }

    // test the method addAdTail
    void testAddAtTail(Tester t) {

        // initialize
        initDeque();

        this.dequetest.addAtTail("abc");
        this.dequetest.addAtTail("bcd");
        this.dequetest.addAtTail("cde");
        this.dequetest.addAtTail("def");
        t.checkExpect(this.dequetest, this.deque2);

    }

    // test the method removeFromHead
    void testRemoveFromHead(Tester t) {
        // initialize
        initDeque();
        this.sent2.removeFromHead();
        this.sent2.removeFromHead();
        this.sent2.removeFromHead();
        this.sent2.removeFromHead();
        t.checkExpect(this.sent2, this.sent1);
        t.checkException(new RuntimeException(
                "Can't try to remove on a Sentinel!"), sent1, "removeFromHead");
        initDeque();
        this.deque2.removeFromHead();
        t.checkExpect(this.deque2.header.next, this.node2);
        t.checkExpect(this.deque2.header.prev, this.node4);
    }

    // test the method removeFromTail
    void testRemoveFromTail(Tester t) {
        // initialize
        initDeque();
        this.sent2.removeFromTail();
        t.checkExpect(this.sent2.next, this.node1);
        t.checkExpect(this.sent2.prev, this.node3);
        t.checkException(new RuntimeException(
                "Can't try to remove on a Sentinel!"), sent1, "removeFromTail");
        // initialize
        initDeque();
        this.deque2.removeFromTail();
        t.checkExpect(this.deque2.header.next, this.node1);

    }

    // test the method find
    void testFind(Tester t) {
        // initialize
        initDeque();
        t.checkExpect(this.deque2.find(new SameString("abc")), this.sent2.next);
        t.checkExpect(this.deque2.find(new SameString("haha")), this.sent2);
        t.checkExpect(this.deque1.find(new SameString("none")), this.sent1);
    }

    // test the method removeNode
    void testRemoveNode(Tester t) {
        // initialize
        initDeque();
        this.deque2.removeNode(node2);
        t.checkExpect(this.deque2.header.next.next, this.node3);
        
        this.deque1.removeNode(node2);
        t.checkExpect(this.deque1.header, sent1);
        
        initDeque();
        this.sent1.removeNodeHelper();
        t.checkExpect(sent1.next, sent1);
        initDeque();
        this.deque1.addAtHead("def");
        this.deque1.addAtHead("bcd");
        this.deque1.addAtHead("abc");
        deque2.removeNode(node3);
        t.checkExpect(deque2, this.deque1);
    }
}
