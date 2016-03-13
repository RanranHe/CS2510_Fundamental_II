//Assignment 7
//partner1-Leyi Qiang
//partner1-Drunkbug
//partner2-Ranran He
//partner2-heranran
import tester.Tester;

// to represnet a IAT
interface IAT {
    // To return the result of applying the given visitor to this List
    <R> R accept(IATVisitor<R> visitor);  
}

// to represent a unknown
class Unknown implements IAT {
    
    /*
     ... accept(IATVisitor<R> visitor) ...  -- R
     */
    
    // To return the result of applying the given visitor to this ConsList 
    public <R> R accept(IATVisitor<R> visitor) {    
        return visitor.visitUnknown(this);     
    }
}

// to represent a person
class Person implements IAT {
    String name;
    int yob;
    boolean isMale;
    IAT mom;
    IAT dad;
    
    // Constructor
    Person(String name, int yob, boolean isMale, IAT mom, IAT dad) {
        this.name = name;
        this.yob = yob;
        this.isMale = isMale;
        this.mom = mom;
        this.dad = dad;
    }
    
    /*
     ... this.name ...  -- String
     ... this.yob ...  -- int 
     ... this.isMale ...  -- boolean 
     ... this.mom ...  -- IAT 
     ... this.dad ...  -- IAT 
     
     ... accept(IATVisitor<R> visitor) ...  -- R 
     
      ... this.mom.accept(IATVisitor<R> visitor) ...  -- R 
      ... this.dad.accept(IATVisitor<R> visitor) ...  -- R 
     */
    
    // To return the result of applying the given visitor to this ConsList 
    public <R> R accept(IATVisitor<R> visitor) {    
        return visitor.visitPerson(this);     
    }
}

//to represent a list of T
interface IList<T> {
    // append two List
    IList<T> append(IList<T> l);

    // append two list
    IList<T> appendHelper(IList<T> l2);
}

//to represent an empty list
class MtList<T> implements IList<T> {
 
    /*
     ... append(IList<T> l) ...  -- IList<T>
     ... appendHelper(IList<T> l2) ...  -- IList<T>
     */
    
    // append list l to a empty list
    public IList<T> append(IList<T> l) {
        return l;
    }

    // append this list to list l2
    public IList<T> appendHelper(IList<T> l2) {
        return l2;
    }
}

// to represent a not empty list
class ConsList<T> implements IList<T> {
    T first;
    IList<T> rest;
    
    // Constructor
    ConsList(T first, IList<T> rest) {
        this.first = first;
        this.rest = rest;
    }
 
    /*
     ... this.first ...  -- T
     ... this.rest ...  -- IList<T>
 
     ... append(IList<T> l) ...  -- IList<T>
     ... appendHelper(IList<T> l2) ...  -- IList<T>
     */
    
    // append list l to a empty list
    public IList<T> append(IList<T> l) {
        return l.appendHelper(this);
    }

    // append this list to list l2
    public IList<T> appendHelper(IList<T> l2) {
        return new ConsList<T>(this.first, this.rest.appendHelper(l2));
    }
}

// to represent a IAV visitor
interface IATVisitor<R> {
    // visit unknown
    R visitUnknown(Unknown unknown);
    
    // visit person
    R visitPerson(Person person);
}

// to represent a visitor that get a list of name in a IAV
class NameVisitor implements IATVisitor<IList<String>> {
    /*
     ... visitUnknown(Unknown unknown) ...  -- IList<String>
     ... visitPerson(Person person) ...  -- IList<String>
     */
    
    // get a list of name in a IAV
    public IList<String> visitUnknown(Unknown unknown) {
        return new MtList<String>();
    }
    
    // get a list of name in a IAV
    public IList<String> visitPerson(Person person) {
        if (person.mom instanceof Unknown && person.dad instanceof Unknown) {
            return new ConsList<String>(person.name, new MtList<String>());
        }
        else {
            return new ConsList<String>(person.name, 
                    new MtList<String>()).append(person.mom.accept(this))
                    .append(person.dad.accept(this));
        }
    }
}

// examples
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

    
    // Unknown 
    Unknown unknown = new Unknown();
    
    // Person
    IAT c = new Person("C", 1904, false, this.a, this.b);
    IAT a = new Person("A", 1902, true, new Unknown(), new Unknown());
    IAT b = new Person("B", 1925, true, new Unknown(), this.a);
    IAT emma = new Person("Emma", 1906, false, new Unknown(), new Unknown());
    IAT eustace = new Person("Eustace", 1907, true, new Unknown(), new Unknown());
    IList<String> list1 = new ConsList<String>("A", new ConsList<String>("B", 
            new MtList<String>()));
    IList<String> list2 = new ConsList<String>("C", new ConsList<String>("A",
            new ConsList<String>("B", new MtList<String>())));
    

    // test accept
    boolean testVisitAccept(Tester t) {
        return t.checkExpect(this.unknown.accept(new NameVisitor()), 
                new MtList<String>()) &&
                t.checkExpect(this.b.accept(new NameVisitor()), this.list1);
                
    }
}