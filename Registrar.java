import tester.*;
//Assignment 7
//partner1-Leyi Qiang
//partner1-Drunkbug
//partner2-Ranran He
//partner2-heranran

// represent the IRegistrar interface
interface IRegistrar {

}

// represent the course class
class Course implements IRegistrar {
    String name;
    Instructor ins;
    IList<Student> students;

    // the constructor
    Course(String name) {
        this.name = name;
        this.ins = null;
        this.students = new Mt<Student>();

    }

    // enroll a student into this course
    void enrollHelper(Student s) {
        this.students = new Cons<Student>(s, this.students);
    }
    
    // enroll a instructor into the course
    void techHelper(Instructor ins) {
        this.ins = ins;
    }
    /*
     * this.name -- String
     * this.ins -- Insturcot
     * this.students -- IList<Student>
     * 
     * this.enrollHelper(Student) -- void
     * this.techHelper(Instructor) -- void
     */
}

// represent the instructor class
class Instructor implements IRegistrar {
    String name;
    IList<Course> cour;

    // the constructor
    Instructor(String name) {
        this.name = name;
        this.cour = new Mt<Course>();
    }

    // determines whether the given Student is in more
    // than one of this Instructor’s Courses.
    boolean dejavu(Student s) {
        return this.cour.count(new HaveStudent(s)) > 1;
    }
    
    // add courses the instructor techs
    void tech(Course c) {
        this.cour = new Cons<Course>(c, this.cour);
        c.techHelper(this);
    }
    /*
     * this.name -- String
     * this.cour -- IList<Course>
     * 
     * this.dejavu(Student) -- boolean
     * this.tech(Course) --void
     */

}

// represent the student class
class Student implements IRegistrar {
    String name;
    int id;
    IList<Course> cour;

    // the constructor
    Student(String name, int id) {
        this.name = name;
        this.id = id;
        this.cour = new Mt<Course>();
    }

    // enrolls a Student in the given Course
    void enroll(Course c) {
        this.cour = new Cons<Course>(c, this.cour);
        c.enrollHelper(this);
    }

    // determines whether the given Student is in any
    // of the same classes as this Student
    boolean classmates(Student s) {
        return this.cour.ormap(new HaveStudent(s));
    }
    
    /*
     * this.name -- String
     * this.id -- int
     * this.cour -- IList<Course>
     * 
     * this.enroll(Course) -- void
     * this.classmates(Student) -- boolean
     */

}

// represent IList interface
interface IList<T> {
    // does any element of the list satisfy the situation?
    boolean ormap(IPredicate<T> pred);

    // is that thing included in the list?
    boolean include(IComparator<T> comp, T t);

    // count the appear times of T
    int count(IPredicate<T> pred);
}

// represent empty lists class
class Mt<T> implements IList<T> {
    // is this empty list contains T?
    public boolean include(IComparator<T> comp, T t) {
        return false;
    }

    // does any element of the empty list satisfy the situation?
    public boolean ormap(IPredicate<T> pred) {
        return false;
    }

    // count appear times in the empty list
    public int count(IPredicate<T> pred) {
        return 0;
    }
    
    /*
     * this.include(IComparator<T>, T) -- boolean
     * this.ormap(IPredicate<T>) -- boolean
     * this.count(IPredicate<T>) -- int
     */
}

// represent cons lists class
class Cons<T> implements IList<T> {
    T first;
    IList<T> rest;

    // the constructor
    Cons(T first, IList<T> rest) {
        this.first = first;
        this.rest = rest;
    }

    // Does this Cons list conatin T?
    public boolean include(IComparator<T> comp, T t) {
        return comp.apply(this.first, t) || this.rest.include(comp, t);
    }

    // Does any element of the cons list satisfy the situation?
    public boolean ormap(IPredicate<T> pred) {
        return pred.apply(this.first) || this.rest.ormap(pred);
    }

    // count appear times in the cons list
    public int count(IPredicate<T> pred) {
        if (pred.apply(this.first)) {
            return 1 + this.rest.count(pred);
        }
        else {
            return this.rest.count(pred);
        }
    }
    
    /*
     * this.first -- T
     * this.rest -- IList<T>
     * 
     * this.include(IComparator<T>, T) -- boolean
     * this.ormap(IPredicate<T>) -- boolean
     * this.count(IPredicate<T>) -- int
     * 
     * this.rest.include(IComparator<T>, T) -- boolean
     * this.rest.ormap(IPredicate<T>) -- boolean
     * this.rest.count(IPredicate<T>) -- int
     */

}

// represent the IPredicate interface
interface IPredicate<T> {
    boolean apply(T t);
}

// does this course have this studet?
class HaveStudent implements IPredicate<Course> {
    Student s;

    // the constructor
    HaveStudent(Student s) {
        this.s = s;
    }

    // is the course has student c?
    public boolean apply(Course c) {
        return c.students.include(new SameStudent(), this.s);
    }
}

// represent the IComparator interface
interface IComparator<T> {
    boolean apply(T t1, T t2);
}

// is that student the same as another student?
class SameStudent implements IComparator<Student> {

    // compare two students
    public boolean apply(Student s1, Student s2) {
        return s1.id == s2.id;
    }
}

// represent examples of registrar
class Examples {
    // students
    Student s1 = new Student("Alice", 1);
    Student s2 = new Student("Bob", 2);
    Student s3 = new Student("David", 3);
    Student s4 = new Student("Tippsie", 4);
    Student s5 = new Student("Daniel", 5);

    // lists of students
    IList<Student> mts = new Mt<Student>();
    IList<Student> ls1 = new Cons<Student>(s1, new Cons<Student>(s2, mts));
    IList<Student> ls2 = new Cons<Student>(s1, new Cons<Student>(s2,
            new Cons<Student>(s3, mts)));
    IList<Student> ls3 = new Cons<Student>(s1, new Cons<Student>(s2,
            new Cons<Student>(s3, new Cons<Student>(s4, new Cons<Student>(s5,
                    mts)))));
    IList<Student> ls4 = new Cons<Student>(s3, new Cons<Student>(s2,
            new Cons<Student>(s1, mts)));

    // instructors
    Instructor i1 = new Instructor("Kevin");
    Instructor i2 = new Instructor("Ben");
    
    // courses
    Course c1 = new Course("fundies1");
    Course c2 = new Course("fundies2");
    Course c3 = new Course("GameDesign");
    Course c4 = new Course("Math");
    
    
    // lists of courses
    IList<Course> mtc = new Mt<Course>();
    IList<Course> lc1 = new Cons<Course>(c1, new Cons<Course>(c2, mtc));
    IList<Course> lc2 = new Cons<Course>(c1, new Cons<Course>(c3, mtc));
    IList<Course> lc3 = new Cons<Course>(c1, new Cons<Course>(c2, new Cons<Course>(c3,
            new Cons<Course>(c4, mtc))));
    IList<Course> lc4 = new Cons<Course>(c1, new Cons<Course>(c2, new Cons<Course>(c3, mtc)));
    IList<Course> lc5 = new Cons<Course>(c3, new Cons<Course>(c4, mtc));

// enroll all student in courses
    void initialize() {
        
        // Kevin teches fundies1 and fundies2
        i1.tech(c2);
        i1.tech(c1);
        
        // Ben teches GameDesign and Math
        i2.tech(c4);
        i2.tech(c3);
        
        // Alice took 3 courses
        s1.enroll(c3);
        s1.enroll(c2);
        s1.enroll(c1);
        
        // Bob took 2 courses
        s2.enroll(c2);
        s2.enroll(c1);
        
        // David took 3 courses
        s3.enroll(c3);
        s3.enroll(c2);
        s3.enroll(c1);
        
        // Tippsie hate going to school
        
        // Daniel took one courses
        s5.enroll(c4);
    }
    
    // test the method enroll
    void testEnroll(Tester t) {
        this.s1.cour = mtc;
        this.c1.students = mts;
        this.i1.cour = mtc;
        
        t.checkExpect(s1.cour, mtc);
        t.checkExpect(c1.students, mts);
        t.checkExpect(i1.cour, mtc);
        
        initialize();
        t.checkExpect(c1.students.include(new SameStudent(), s1));
    }

    // test the classmates method
    void testClassmates(Tester t) {
        initialize();
        t.checkExpect(s1.classmates(s5), false);
        t.checkExpect(s2.classmates(s1), true);
        t.checkExpect(s3.classmates(s4), false);
    }
    
    // test the method count
    void testCount(Tester t) {
        initialize();
        t.checkExpect(lc1.count(new HaveStudent(s1)), 2);
        t.checkExpect(mtc.count(new HaveStudent(s1)), 0);
        t.checkExpect(lc1.count(new HaveStudent(s4)), 0);
    }
    // test the dejavu method
    void testDejavu(Tester t) {
        initialize();
        t.checkExpect(i1.dejavu(s1), true);
        t.checkExpect(i1.dejavu(s4), false);
        t.checkExpect(i1.dejavu(s5), false);
    }
    
    // test the oramp method
    void testOrmap(Tester t) {
        initialize();
        t.checkExpect(lc1.ormap(new HaveStudent(s1)), true);
        t.checkExpect(mtc.ormap(new HaveStudent(s1)), false);
        t.checkExpect(lc2.ormap(new HaveStudent(s4)), false);
        t.checkExpect(lc2.ormap(new HaveStudent(s5)), false);
    }
    
    // test include method
    void testInclude(Tester t) {
        initialize();
        t.checkExpect(ls1.include(new SameStudent(), s1), true);
        t.checkExpect(ls2.include(new SameStudent(), s5), false);
        t.checkExpect(mts.include(new SameStudent(), s1), false);
        t.checkExpect(ls2.include(new SameStudent(), s4), false);
    }
}