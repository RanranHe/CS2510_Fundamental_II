//Assignment 6
//partner1-Qiang Leyi 
//partner1-Drunkbug
//partner2-He Ranran  
//partner2-heranran
import tester.Tester;

// represent the book class
class Book {
    String title;
    String author;
    int price;

    // the constructor
    Book(String title, String author, int price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    /*
    ... this.title ...  -- String
    ... this.author ...  -- String
    ... this.price ...  -- int
    
    ... compareBook(Book b) ...  -- boolean
    */
    
    // compare two books
    boolean compareBook(Book b) {
        return this.title.equals(b.title) && this.author.equals(b.author)
                && this.price == b.price;
    }
}

// to represent a list of string
interface IList<T> {
    // determine whether the two lists have the same data in same compare
    boolean sameList(IList<T> lot);

    // change the list to ABST
    ABST<T> buildTree(ABST<T> acc);

    // determine whether the given string comes before all elements of the list
    // by the given way
    boolean isSortedHelper(T t, IComparator<T> comp);

    // determine whether the list is already sorted by the given way
    boolean isSorted(IComparator<T> comp);
    
    // reverse the order of the list
    IList<T> reverse();

    // reverse the order of string by accumulate the string
    IList<T> reverseHelper(IList<T> acc);

    

}

// to represent a empty list
class Empty<T> implements IList<T> {
    
    /*
    ... sameList(IList<T> lot) ...  -- boolean
    ... buildTree(ABST<T> acc) ...  -- ABST<T>
    ... isSortedHelper(T t, IComparator<T> comp) ...  -- boolean
    ... isSorted(IComparator<T> comp) ...  -- boolean
    ... sort(IComparator<T> comp) ...  -- IList<T> 
    ... insert(IComparator<T> comp, T t) ...  -- IList<T> 
    ... reverse() ...  -- IList<T>
    ... reverseHelper(IList<T> acc) ...  -- IList<T>
    */
    
    // determine whether the two lists have the same data in same compare
    public boolean sameList(IList<T> lot) {
        return lot instanceof Empty;
    }

    // change the empty list to a binary search tree
    public ABST<T> buildTree(ABST<T> acc) {
        return acc;
    }

    // determine whether the given string comes before all elements of the list
    // by the given way
    public boolean isSortedHelper(T t, IComparator<T> comp) {
        return true;
    }

    // determine whether the list is already sorted by the given way
    public boolean isSorted(IComparator<T> comp) {
        return true;
    }
    
    // reverse the order of a empty list
    public IList<T> reverse() {
        return reverseHelper(new Empty<T>());
    }

    // reverse the order of string by accumulate the empty list
    public IList<T> reverseHelper(IList<T> acc) {
        return acc;
    }

}

// to represent a list of string
class Cons<T> implements IList<T> {
    T first;
    IList<T> rest;

    // the constructor
    Cons(T first, IList<T> rest) {
        this.first = first;
        this.rest = rest;
    }

    /*
    ... this.first ...  -- T
    ... this.rest ...  -- IList<T>
    
    ... sameList(IList<T> lot) ...  -- boolean
    ... buildTree(ABST<T> acc) ...  -- ABST<T>
    ... isSortedHelper(T t, IComparator<T> comp) ...  -- boolean
    ... isSorted(IComparator<T> comp) ...  -- boolean
    ... sort(IComparator<T> comp) ...  -- IList<T> 
    ... insert(IComparator<T> comp, T t) ...  -- IList<T> 
    ... reverse() ...  -- IList<T>
    ... reverseHelper(IList<T> acc) ...  -- IList<T>
    
    ... this.rest.sameList(IList<T> lot) ...  -- boolean
    ... this.rest.buildTree(ABST<T> acc) ...  -- ABST<T>
    ... this.rest.isSortedHelper(T t, IComparator<T> comp) ...  -- boolean
    ... this.rest.isSorted(IComparator<T> comp) ...  -- boolean
    ... this.rest.sort(IComparator<T> comp) ...  -- IList<T> 
    ... this.rest.insert(IComparator<T> comp, T t) ...  -- IList<T>
    ... this.rest.reverse() ...  -- IList<T>
    ... this.rest.reverseHelper(IList<T> acc) ...  -- IList<T>
    
    */
    
    // determine whether the two lists have the same data in same compare
    public boolean sameList(IList<T> lot) {
        return lot instanceof Cons<?> && this.sameListHelper((Cons<T>) lot);
    }

    // determine whether the two Cons is the same
    boolean sameListHelper(Cons<T> lot) {
        return this.first.equals(lot.first) && this.rest.sameList(lot.rest);
    }

    // change the cons to a binary search tree
    public ABST<T> buildTree(ABST<T> acc) {
        return this.rest.buildTree(acc.insert(this.first));
    }

    // determine whether the list is already sorted by the given way
    public boolean isSorted(IComparator<T> comp) {
        return this.rest.isSortedHelper(this.first, comp)
                && this.rest.isSorted(comp);
    }

    // determine whether the given string comes before all elements of the list
    // by the given way
    public boolean isSortedHelper(T t, IComparator<T> comp) {
        return comp.compare(t, this.first) <= 0
                && this.rest.isSortedHelper(t, comp);
    }
    
    // reverse the order of this list
    public IList<T> reverse() {
        return this.reverseHelper(new Empty<T>());
    }

    // reverse the order of the list by accumulate the string
    public IList<T> reverseHelper(IList<T> acc) {
        return this.rest.reverseHelper(new Cons<T>(this.first, acc));
    }

}

// //////////////////////////////////////////////////////////////////////////////

// represent the binary search tree
abstract class ABST<T> {
    IComparator<T> order;

    // the constructor
    ABST(IComparator<T> order) {
        this.order = order;
    }

    /*
    ... this.order ...  -- IComparator<T> 
    */
    
    // insert a item by compare
    abstract ABST<T> insert(T t);

    // get the leftmost item contained in this tree
    abstract T getLeftmost();

    // get the right side except the leftmost item
    abstract ABST<T> getRight();

    // is this ABST the same as that ABST?
    abstract boolean sameTree(ABST<T> bst);

    // is this ABST has the same data as that ABST?
    abstract boolean sameData(ABST<T> bst);

    // is this ABST the same as that list
    abstract boolean sameAsList(IList<T> l);

    // change the ABST to a list
    abstract IList<T> buildList(IList<T> acc);

}

// represent the leaf in binary search tree
class Leaf<T> extends ABST<T> {
    // the constructor
    Leaf(IComparator<T> order) {
        super(order);
    }

    /*
    ... this.order ...  -- IComparator<T>
    
    ... insert(T t) ...  -- ABST<T> 
    ... getLeftmost() ...  -- T 
    ... getRight() ...  -- ABST<T> 
    ... sameTree(ABST<T> abst) ...  -- boolean 
    ... sameData(ABST<T> abst) ...  -- boolean
    ... sameAsList(IList<T> l) ...  -- boolean
    ... buildList(IList<T> acc) ...  -- IList<T>
    */
    
    // insert a item to a leaf
    ABST<T> insert(T t) {
        return new Node<T>(order, t, new Leaf<T>(this.order), new Leaf<T>(
                this.order));
    }

    // the leftmost item is a leaf
    T getLeftmost() {
        throw new RuntimeException("No leftmost item of an empty tree");
    }

    // get all but the leftmost item in a leaf
    ABST<T> getRight() {
        throw new RuntimeException("No right of an empty tree");
    }

    // is this leaf the same as that leaf?
    boolean sameTree(ABST<T> abst) {
        return abst instanceof Leaf;
    }

    // this this leaf has the same data as that leaf?
    boolean sameData(ABST<T> abst) {
        return abst instanceof Leaf;
    }

    // is this Leaf the same as that list?
    boolean sameAsList(IList<T> l) {
        return l instanceof Empty;
    }

    // change leaf to a list
    IList<T> buildList(IList<T> acc) {
        return acc;
    }

}

// represent the node in binary search tree
class Node<T> extends ABST<T> {
    T data;
    ABST<T> left;
    ABST<T> right;

    // the constructor
    Node(IComparator<T> order, T data, ABST<T> left, ABST<T> right) {
        super(order);
        this.data = data;
        this.left = left;
        this.right = right;
    }

    /*
    ... this.order ...  -- IComparator<T>
    ... this.data ...  -- T
    ... this.left ...  -- ABST<T>
    ... this.right ...  -- ABST<T>
    
    ... insert(T t) ...  -- ABST<T>
    ... getLeftmost() ...  -- T
    ... getRight() ...  -- ABST<T>
    ... sameTree(ABST<T> abst) ...  -- boolean
    ... sameData(ABST<T> abst) ...  -- boolean
    ... sameAsList(IList<T> l) ...  -- boolean
    ... buildList(IList<T> acc) ...  -- IList<T>
    
    ... this.left.insert(T t) ...  -- ABST<T>
    ... this.left.getLeftmost() ...  -- T
    ... this.left.getRight() ...  -- ABST<T>
    ... this.left.sameTree(ABST<T> abst) ...  -- boolean
    ... this.left.sameData(ABST<T> abst) ...  -- boolean
    ... this.left.sameAsList(IList<T> l) ...  -- boolean
    ... this.left.buildList(IList<T> acc) ...  -- IList<T>
    ... this.right.insert(T t) ...  -- ABST<T>
    ... this.right.getLeftmost() ...  -- T
    ... this.right.getRight() ...  -- ABST<T>
    ... this.right.sameTree(ABST<T> abst) ...  -- boolean
    ... this.right.sameData(ABST<T> abst) ...  -- boolean
    ... this.right.sameAsList(IList<T> l) ...  -- boolean
    ... this.right.buildList(IList<T> acc) ...  -- IList<T>
    */
    
    // insert a item to a node
    ABST<T> insert(T t) {
        if (order.compare(t, this.data) >= 0) {
            return new Node<T>(this.order, this.data, this.left,
                    this.right.insert(t));
        }
        else {
            return new Node<T>(this.order, this.data, this.left.insert(t),
                    this.right);
        }
    }

    // find the left most of the Node
    T getLeftmost() {
        if (this.left instanceof Leaf) {
            return this.data;
        }
        else {
            return this.left.getLeftmost();
        }
    }

    // get all but the leftmost item in a Node
    ABST<T> getRight() {
        if (this.left instanceof Leaf) {
            return this.right;
        }
        else {
            return new Node<T>(order, this.data,
                    ((Node<T>) this.left).getRight(), this.right);
        }
    }

    // is this BST the same as that BST?
    boolean sameTree(ABST<T> abst) {
        if (abst instanceof Node) {
            return order.compare(this.data, ((Node<T>) abst).data) == 0
                    && this.left.sameTree(((Node<T>) abst).left)
                    && this.right.sameTree(((Node<T>) abst).right);
        }
        else {
            return false;
        }
    }

    // is this BST has the same data as that BST?
    boolean sameData(ABST<T> abst) {
        if (abst instanceof Node) {
            return order.compare(this.getLeftmost(), abst.getLeftmost()) == 0
                    && this.getRight().sameData(((Node<T>) abst).getRight());
        }
        else {
            return false;
        }
    }

    // is this BST the same as that list?
    boolean sameAsList(IList<T> l) {
        return this.buildList(new Empty<T>()).reverse().sameList(l);
    }

    // change the node to a list
    IList<T> buildList(IList<T> acc) {
        return this.getRight().buildList(new Cons<T>(this.getLeftmost(), acc));
    }
}

// compare two types of things
interface IComparator<T> {
    int compare(T t1, T t2);

   /* int compareAll(T t1, T t2);*/ 
    // I tried to use compareAll to compare all fields of a class
    // but web-cat can't pass the test
    
}

// compare two books by title 
class BooksByTitle implements IComparator<Book> {
    public int compare(Book b1, Book b2) {
        return b1.title.compareTo(b2.title);
    }

    /*//compare two books
    public int compareAll(Book b1, Book b2) {
        if (b1.compareBook(b2)) {
            return 0;
        }
        else {
            return -1;
        }
    }*/
}


// compare two books by author
class BooksByAuthor implements IComparator<Book> {
    public int compare(Book b1, Book b2) {
        return b1.author.compareTo(b2.author);
    }

    // compare two books
    public int compareAll(Book b1, Book b2) {
        if (b1.compareBook(b2)) {
            return 0;
        }
        else {
            return -1;
        }
    }
}

// compare two books by price
class BooksByPrice implements IComparator<Book> {
    public int compare(Book b1, Book b2) {
        return b1.price - b2.price;
    }

    /*// compare two books
    public int compareAll(Book b1, Book b2) {
        if (b1.compareBook(b2)) {
            return 0;
        }
        else {
            return -1;
        }
    }*/

}

// compare two strings lexicographically
class StringLexCompGen implements IComparator<String> {
    public int compare(String s1, String s2) {
        return s1.compareTo(s2);
    }

    /*// compare two strings
    public int compareAll(String s1, String s2) {
        return s1.compareTo(s2);
    }*/
}

// compare the two strings depend on their length
class StringLengthCompGen implements IComparator<String> {
    public int compare(String s1, String s2) {
        if (s1.length() < s2.length()) {
            return -1;
        }
        else if (s1.length() == s2.length()) {
            return 0;
        }
        else {
            return 1;
        }
    }

    /*// compare two strings
    public int compareAll(String s1, String s2) {
        return s1.compareTo(s2);
    }*/
    
}

// represent the examples of binary search tree
class ExamplesABST {

    // examples of list of Strings
    IList<String> mt = new Empty<String>();
    // not sorted lists
    IList<String> ln1 = new Cons<String>("F", new Cons<String>("A",
            new Cons<String>("C", this.mt)));
    IList<String> lln1 = new Cons<String>("aa", new Cons<String>("a",
            new Cons<String>("aabbccdd", new Cons<String>("aabbc",
                    new Cons<String>("aabb", new Empty<String>())))));

    // sorted lists by lex
    IList<String> l1 = new Cons<String>("A", new Cons<String>("C",
            new Cons<String>("F", this.mt)));
    IList<String> l2 = new Cons<String>("B", new Cons<String>("E",
            new Cons<String>("G", new Cons<String>("H", 
                    new Empty<String>()))));
    IList<String> l3 = new Cons<String>("A", new Cons<String>("B",
            new Cons<String>("C", new Cons<String>("E", new Cons<String>("F",
                    new Cons<String>("G", new Cons<String>("H",
                            new Empty<String>())))))));

    // sorted lists by length
    IList<String> ll1 = new Cons<String>("a", new Cons<String>("aa",
            new Cons<String>("aabb", new Cons<String>("aabbc",
                    new Cons<String>("aabbccdd", new Empty<String>())))));
    IList<String> ll2 = new Cons<String>("aaa", new Cons<String>("aabbcc",
            new Cons<String>("aabbccd", new Cons<String>("aabbccdde",
                    new Empty<String>()))));
    IList<String> ll3 = new Cons<String>("a", new Cons<String>("aa",
            new Cons<String>("aaa", new Cons<String>("aabb", new Cons<String>(
                    "aabbc", new Cons<String>("aabbcc", new Cons<String>(
                            "aabbccd", new Cons<String>("aabbccdd",
                                    new Cons<String>("aabbccdde",
                                            new Empty<String>())))))))));

    // test the method sameList
    boolean testSameList(Tester t) {
        return t.checkExpect(this.l1.sameList(ln1), false)
                && t.checkExpect(this.mt.sameList(this.mt), true)
                && t.checkExpect(this.ll1.sameList(ln1), false)
                && t.checkExpect(this.lln1.sameList(ll1), false);
    }

    // test isSortedHelper(String s, IStringsCompare comp)
    boolean testIsSortedHelper(Tester t) {
        return t.checkExpect(
                this.mt.isSortedHelper("a", new StringLexCompGen()), true)
                && t.checkExpect(
                        this.l2.isSortedHelper("A", new StringLexCompGen()),
                        true)
                && t.checkExpect(
                        this.mt.isSortedHelper("a", new StringLengthCompGen()),
                        true)
                && t.checkExpect(
                        this.ll2.isSortedHelper("a", 
                                new StringLengthCompGen()), true);

    }

    // test the method isSorted
    boolean testIsSorted(Tester t) {
        return t.checkExpect(this.mt.isSorted(new StringLexCompGen()), true)
                && t.checkExpect(this.l1.isSorted(new StringLexCompGen()), 
                        true)
                && t.checkExpect(this.ln1.isSorted(new StringLexCompGen()),
                        false)
                && t.checkExpect(this.lln1.isSorted(new StringLengthCompGen()),
                        false)
                && t.checkExpect(this.ll1.isSorted(new StringLengthCompGen()),
                        true)
                && t.checkExpect(this.mt.isSorted(new StringLengthCompGen()),
                        true);
    }

    // ////////////////////////////////////////////////////////////////////////
    // examples for books
    Book book0 = new Book("titleA", "authorA", 100);
    Book book1 = new Book("titleB", "authorB", 500);
    Book book2 = new Book("How to Design a Game", "Leyi Qiang", 1);
    Book bookx = new Book("How to Design a Game", "Leyi Qiang", 2);

    // examples for comparator
    IComparator<Book> bbt = new BooksByTitle();
    IComparator<Book> bba = new BooksByAuthor();
    IComparator<Book> bbp = new BooksByPrice();

    // examples of books
    IList<Book> mtb = new Empty<Book>();

    IList<Book> bl1 = new Cons<Book>(book0, new Cons<Book>(book1,
            new Cons<Book>(book2, new Empty<Book>())));

    IList<Book> bl2 = new Cons<Book>(book2, new Cons<Book>(book0,
            new Cons<Book>(book1, new Empty<Book>())));

    // examples for binary search tree
    ABST<Book> leaf = new Leaf<Book>(this.bbt);

    ABST<Book> Node0 = new Node<Book>(this.bbt, this.book0, new Node<Book>(
            this.bbt, this.book2, this.leaf, this.leaf), this.leaf);

    ABST<Book> Nodex = new Node<Book>(this.bba, this.book0, new Node<Book>(
            this.bbt, this.bookx, this.leaf, this.leaf), this.leaf);

    ABST<Book> Node0copy = new Node<Book>(this.bbt, this.book0, new Node<Book>(
            this.bbt, this.book2, this.leaf, this.leaf), this.leaf);

    ABST<Book> Node0same = new Node<Book>(this.bba, this.book2, this.leaf,
            new Node<Book>(this.bbt, this.book0, this.leaf, this.leaf));

    ABST<Book> Nodebl1 = new Node<Book>(this.bbt, this.book0, new Node<Book>(
            this.bbt, this.book2, this.leaf, this.leaf), 
            new Node<Book>(this.bbt, this.book1, this.leaf, this.leaf));

    ABST<Book> Nodebl2 = new Node<Book>(this.bba, this.book1, new Node<Book>(
            this.bba, this.book0, this.leaf, this.leaf), 
            new Node<Book>(this.bba, this.book1, this.leaf, this.leaf));

    // test the buildTree method
    boolean testBuildTree(Tester t) {
        return t.checkExpect(this.mtb.buildTree(this.leaf), this.leaf)
                && t.checkExpect(this.bl1.buildTree(this.leaf), Nodebl1);
    }

    // test the method insert
    boolean testInsert(Tester t) {
        return t.checkExpect(this.leaf.insert(book0), new Node<Book>(this.bbt,
                this.book0, this.leaf, this.leaf))
                && t.checkExpect(this.Node0.insert(this.book1), this.Nodebl1);
    }

    // test the method getLeftMost
    boolean testGetLeftMost(Tester t) {
        return t.checkException(new RuntimeException(
                "No leftmost item of an empty tree"), this.leaf, "getLeftmost")
                && t.checkExpect(this.Node0.getLeftmost(), this.book2);
    }

    // test the method getRight
    boolean testGetRight(Tester t) {
        return t.checkExpect(this.Node0.getRight(), new Node<Book>(this.bbt,
                this.book0, this.leaf, this.leaf))
                && t.checkException(new RuntimeException(
                        "No right of an empty tree"), this.leaf, "getRight")
                && t.checkExpect(this.Node0same.getRight(), new Node<Book>(
                        this.bbt, this.book0, this.leaf, this.leaf));
    }

    // test the method sameTree
    boolean testSameTree(Tester t) {
        return t.checkExpect(this.Node0.sameTree(this.Node0copy), true)
                && t.checkExpect(this.Node0.sameTree(this.leaf), false)
                && t.checkExpect(this.Node0.sameTree(this.Nodex), true)
                && t.checkExpect(this.leaf.sameTree(this.leaf), true);
    }

    // test the method sameData
    boolean testSameData(Tester t) {
        return t.checkExpect(this.Node0.sameData(this.Nodex), true)
                && t.checkExpect(this.Node0.sameData(this.leaf), false)
                && t.checkExpect(this.leaf.sameData(this.leaf), true)
                && t.checkExpect(this.Node0.sameData(this.Nodebl1), false);
    }

    // test sameBuildList
    boolean testBuildList(Tester t) {
        return t.checkExpect(this.Nodebl1.buildList(this.mtb), new Cons<Book>(
                book1, new Cons<Book>(book0, new Cons<Book>(book2,
                        new Empty<Book>()))));
    }

    // test sameAsList
    boolean testSameAsList(Tester t) {
        return t.checkExpect(this.Nodebl1.sameAsList(this.bl2), true) &&
                t.checkExpect(this.leaf.sameAsList(this.bl2), false) &&
                t.checkExpect(this.leaf.sameAsList(this.mtb), true);
    }
    
    // test compare
    boolean testCompare(Tester t) {
        return t.checkExpect(new BooksByAuthor().compare(book0, book1), -1) &&
                t.checkExpect(new BooksByPrice().compare(book0, book1), -400);
    }
    // test compareAll
    boolean testCompareAll(Tester t) {
        return t.checkExpect(new BooksByAuthor().compareAll(this.book1, 
                this.book2), -1) &&
                t.checkExpect(new BooksByAuthor().compareAll(this.book1, 
                        this.book1), 0);
    }
    
    // test reverse
    boolean testReverse(Tester t) {
        return t.checkExpect(this.mt.reverse(), this.mt);
    }
}