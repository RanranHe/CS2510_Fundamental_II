import tester.Tester;
//Assignment 7
//partner1-Leyi Qiang
//partner1-Drunkbug
//partner2-Ranran He
//partner2-heranran

// To represent a book
class Book {
    String title;
    String author;
    int year;
    double price;
    
    // constructor
    Book(String title, String author, int year, double price) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.price = price;
    }
    
    /*
    ... this.title ...  -- String
    ... this.author ...  -- String
    ... this.year ...  -- int
    ... this.price ...  -- double
    */
}

// to represent a circle
class Circle {
    int radius;
    String color;
    
    // constructure
    Circle(int r, String color) {
        this.radius = r;
        this.color = color;
    }
    
    /*
     ... this.radius ...  -- int
     ... this.color ...  -- String
     */
}

// to represent a list of T
interface IList<T> {
    // append two List
    IList<T> append(IList<T> l);

    // append two list
    IList<T> appendHelper(IList<T> l2);
    
    // to map a function to each symbols in the list
    <U> IList<U> map(IFunc<T, U> f);
    
    // to get a new st of T that reach the requirements of a list of T
    IList<T> filter(IFunc<T, Boolean> f);
    
    // To return the result of applying the given visitor to this List
    <U> U accept(IListVisitor<T, U> v);  
}

// to represent an empty list
class MtList<T> implements IList<T> {
    
    /*
     ... append(IList<T> list) ...  -- IList<T>
     ... reverse(IList<T> acc) ...  -- IList<T>
     ... map(IFunc<T, U> f) ...  -- IList<T>
     ... filter(IFunc<T, Boolean> f) ...  -- IList<T>
     ... accept(IListVisitor<T, U> visitor) ...  -- U
     */
    
    // append list l to a empty list
    public IList<T> append(IList<T> l) {
        return l;
    }

    // append this list to list l2
    public IList<T> appendHelper(IList<T> l2) {
        return l2;
    }
    
    // to map a function to each symbols in the list
    public <U> IList<U> map(IFunc<T, U> f) {
        return new MtList<U>();
    }
    
    // to get a new st of T that reach the requirements of a list of T
    public IList<T> filter(IFunc<T, Boolean> f) {
        return this;
    }
    
    // To return the result of applying the given visitor to this MtList 
    public <U> U accept(IListVisitor<T, U> v) {    
        return v.visit(this);     
    }
}

// to represent a not empty list
class ConsList<T> implements IList<T> {
    T first;
    IList<T> rest;
    
    // constructor
    ConsList(T first, IList<T> rest) {
        this.first = first;
        this.rest = rest;
    }
    
    /*
    ... this.first ...  -- T
    ... this.rest ...  -- IList<T>
    
    ... append(IList<T> list) ...  -- IList<T>
    ... appendHelper(IList<T> list) ... -- IList<T>
    ... map(IFunc<T, U> f) ...  -- IList<T>
    ... filter(IFunc<T, Boolean> f) ...  -- IList<T>
    ... accept(IListVisitor<T, U> visitor) ...  -- U
    
    ... this.rest.append(IList<T> list) ...  -- IList<T>
    ... this.rest.map(IFunc<T, U> f) ...  -- IList<T>
    ... this.rest.filter(IFunc<T, Boolean> f) ...  -- IList<T>
    ... this.rest.accept(IListVisitor<T, U> visitor) ...  -- U
    */
    
    // append list l to a Conslist
    public IList<T> append(IList<T> l) {
        return l.appendHelper(this);
    }

    // append this list to l2
    public IList<T> appendHelper(IList<T> l2) {
        return new ConsList<T>(this.first, this.rest.appendHelper(l2));
    }
    
    // to map a function to each symbols in the list
    public <U> IList<U> map(IFunc<T, U> f) {
        return new ConsList<U>(f.apply(this.first), this.rest.map(f));
    }
    
    // to get a new st of T that reach the requirements of a list of T
    public IList<T> filter(IFunc<T, Boolean> f) {
        if (f.apply(this.first)) {
            return new ConsList<T>(this.first, this.rest.filter(f));
        }
        else {
            return this.rest.filter(f);
        }
    }
    
    // To return the result of applying the given visitor to this ConsList 
    public <U> U accept(IListVisitor<T, U> v) {    
        return v.visit(this);     
    }
}

// to represent a function that makes [T -> U]
interface IFunc<T, U> {
    U apply(T a);
}

// to get the title of a book
class BookTitle implements IFunc<Book, String> {
    
    /*
    ... apply(Book book) ...  -- String
    */
    
    // get the title
    public String apply(Book book) {
        return book.title;
    }
}

// to determine whether a book is expensive
class ExpensiveBook implements IFunc<Book, Boolean> {
    
    /*
    ... apply(Book book) ...  -- Boolean
    */
    
    // determine whether the price is higer than 200
    public Boolean apply(Book book) {
        return book.price >= 200;
    }
}

// to compute the area of a circle
class CircleArea implements IFunc<Circle, Double> {
    
    /*
    ... apply(Circle cir) ...  -- Double
    */
    
    // compute the area of the circle
    public Double apply(Circle cir) {
        return Math.PI * cir.radius * cir.radius;
    }
}

// to determine whether the circle is red
class RedCircle implements IFunc<Circle, Boolean> {
    /*
     ... apply(Circle cir) ...  -- Boolean
     */
    
    // determine whether the circle is red
    public Boolean apply(Circle cir) {
        return cir.color.equals("red");
    }
}

//To implement a function over List objects, returning a value of type U
interface IListVisitor<T, U> {
    // visit MtList
    U visit(MtList<T> mt);
    
    // visit ConsList
    U visit(ConsList<T> cons);
}

// visit IList<T> and return a list of U
class MapVisitor<T, U> implements IListVisitor<T, IList<U>> {
    IFunc<T, U> f;
    
    // constructor
    MapVisitor(IFunc<T, U> f) {
        this.f = f;
    }
    
    /*
    ... this.f ...  -- IFunc<T, U>
    
    ... visit(MtList<T> mt) ...  -- IList<U>
    ... visit(ConsList<T> cons) ...  -- IList<U> 
    */
    
    // visit MtList
    public IList<U> visit(MtList<T> mt) {
        return new MtList<U>();
    }
    
    // visit ConsList
    public IList<U> visit(ConsList<T> cons) {
        return new ConsList<U>(f.apply(cons.first), cons.rest.accept(this));
    }
    
}

// a way to filter
class FilterVisitor<T> implements IListVisitor<T, IList<T>> {
    IFunc<T, Boolean> f;
    
    // constructor
    FilterVisitor(IFunc<T, Boolean> f) {
        this.f = f;
    }
    
    /*
    ... this.f ...  -- IFunc<T, Boolean>
    
    ... visit(MtList<T> mt) ...  -- IList<T>
    ... visit(ConsList<T> cons) ...  -- IList<T> 
    */
    
    // visit MtList
    public IList<T> visit(MtList<T> mt) {
        return new MtList<T>();
    }
    
    // visit ConsList
    public IList<T> visit(ConsList<T> cons) {
        if (f.apply(cons.first)) {
            return new ConsList<T>(cons.first, cons.rest.accept(this));
        }
        else {
            return cons.rest.accept(this);
        }
    }
}

// give examples to above
class Examples {
    
    // examples to test append and reverse
    IList<Integer> mt = new MtList<Integer>();
    IList<Integer> l1 = new ConsList<Integer>(1, new ConsList<Integer>(2, 
            new ConsList<Integer>(3, mt)));
    IList<Integer> l2 = new ConsList<Integer>(4, new ConsList<Integer>(5, 
            new ConsList<Integer>(6, mt)));
    IList<Integer> l3 = new ConsList<Integer>(1, new ConsList<Integer>(2, 
            new ConsList<Integer>(3, this.l2)));
    IList<Integer> l4 = new ConsList<Integer>(3, new ConsList<Integer>(2, 
            new ConsList<Integer>(1, mt)));
    
    // test the method append
    void testAppend(Tester t) {
        t.checkExpect(this.l2.append(this.l1), this.l3);
        t.checkExpect(this.mt.append(this.l1), this.l1);
        t.checkExpect(this.l1.append(this.mt), this.l1);
    }
    
    // test the method append helper
    void testAppendHelper(Tester t) {
        t.checkExpect(this.l1.appendHelper(this.l2), this.l3);
        t.checkExpect(this.mt.appendHelper(this.l1), this.l1);
        t.checkExpect(this.l1.appendHelper(this.mt), this.l1);
    }

    
    
    // Books
    Book book1 = new Book("AB", "PO", 2001, 160);
    Book book2 = new Book("CD", "KL", 1954, 250);
    Book book3 = new Book("HHK", "FF", 1982, 50);
    
    // Circles
    Circle c1 = new Circle(10, "red");
    Circle c2 = new Circle(30, "red");
    Circle c3 = new Circle(30, "blue");
     
    // lists of Books
    IList<Book> mtlist = new MtList<Book>();
    IList<Book> list1 = new ConsList<Book>(this.book1, 
            new ConsList<Book>(this.book2, this.mtlist));
    IList<Book> someBookList = new ConsList<Book>(this.book3, this.list1);
    
    // Lists of Circles
    IList<Circle> mtcir = new MtList<Circle>();
    IList<Circle> list2 = new ConsList<Circle>(this.c1, 
            new ConsList<Circle>(this.c2, 
                    new ConsList<Circle>(this.c3, this.mtcir)));
    
    // IFunc
    IFunc<Book, String> bookTitle = new BookTitle();
    IFunc<Book, Boolean> expensiveBook = new ExpensiveBook();
    IFunc<Circle, Double> circleArea = new CircleArea();
    IFunc<Circle, Boolean> redCircle = new RedCircle();
    
    // Visitor
    MapVisitor<Book, String> mapBook2TitleVisitor = 
            new MapVisitor<Book, String>(this.bookTitle);
    FilterVisitor<Book> filterBook2TitleVisitor = 
            new FilterVisitor<Book>(this.expensiveBook);
    MapVisitor<Circle, Double> mapCircle = 
            new MapVisitor<Circle, Double>(this.circleArea);
    FilterVisitor<Circle> filterCircle = 
            new FilterVisitor<Circle>(this.redCircle);
    
    // test Map and MapVisitor
    void testMap(Tester t) {
        t.checkExpect(someBookList.map(bookTitle),
                someBookList.accept(mapBook2TitleVisitor));
        
        t.checkExpect(list2.map(circleArea), list2.accept(mapCircle));
        
        t.checkExpect(mtcir.map(circleArea), mtcir.accept(mapCircle));
        
        
    }

    // test Filter and FilterVisitor
    void testFilter(Tester t) {
        t.checkExpect(someBookList.filter(expensiveBook), 
                someBookList.accept(filterBook2TitleVisitor));
        
        t.checkExpect(list2.filter(redCircle), 
                        list2.accept(filterCircle));
        
        t.checkExpect(mtcir.filter(redCircle), mtcir);
    }
}