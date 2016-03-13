import tester.*;
//Assignment 8
//partner1-Leyi Qiang
//partner1-Drunkbug
//partner2-Ranran He
//partner2-heranran

// represents a Person with a user name and a list of buddies
class Person {

    String username;
    ILoBuddy buddies;

    // the constructor
    Person(String username) {
        this.username = username;
        this.buddies = new MTLoBuddy();
    }

    // returns true if this Person has that as a direct buddy
    boolean hasDirectBuddy(Person that) {
        return this.buddies.findPerson(that);
    }

    // returns the number of people that are direct buddies
    // of both this and that person
    int countCommonBuddies(Person that) {
        return this.buddies.countComBuddies(that);
    }

    // will the given person be invited to a party
    // organized by this person?
    boolean hasDistantBuddy(Person that) {
        return this.buddies.countDisBuddies(that);
    }

    // EFFECT:
    // Change this person's buddy list so that it includes the given person
    void addBuddy(Person buddy) {
        this.buddies = new ConsLoBuddy(buddy, this.buddies);
    }

    // returns the number of people who will show up at the party
    // given by this person
    int partyCount() {
        return 1 + this.buddies.countBuddies(new ConsLoBuddy(this,
                new MTLoBuddy()));
    }
    
    /*
     * this.username ... String
     * this.buddies ... MtLoBuddy()
     * 
     * this.hasDirectBuddy(Person) ... boolean
     * this.countCommonBuddies(Person) ... int
     * this.hasDistantBuddy(Person) ... boolean
     * this.addBuddy(Person) ... void
     * this.partyCount() ... int
     */

}

// represents a list of Person's buddies
interface ILoBuddy {

    // is this person in the list of buddy?
    boolean findPerson(Person that);

    // count the common buddies of the two person
    int countComBuddies(Person that);

    // will the given person be invited to a party
    // organized by this person?
    boolean countDisBuddies(Person that);

    // count the Buddies that will be invited.
    int countBuddies(ILoBuddy acc);

}

// represents an empty list of Person's buddies
class MTLoBuddy implements ILoBuddy {

    // is this person in the empty list of buddy?
    public boolean findPerson(Person that) {
        return false;
    }

    // How many common buddy between that person and this empty list?
    public int countComBuddies(Person that) {
        return 0;
    }

    // will the given person be invited to a party
    // organized by this person?
    public boolean countDisBuddies(Person that) {
        return false;
    }

    // count the buddies in the empty list
    public int countBuddies(ILoBuddy acc) {
        return 0;
    }
}

// represents a list of Person's buddies
class ConsLoBuddy implements ILoBuddy {

    Person first;
    ILoBuddy rest;

    /*
     * this.first ... Person
     * this.rest ... ILoBuddy
     * 
     * this.findPerson(Person) ... boolean
     * this.countComBuddies(Person) ... int
     * this.countDisBuddies(Person) ... boolean
     * this.countBuddies(ILoBuddy) ... int
     * 
     * this.rest.findPerson(Person) ... boolean
     * this.rest.countComBuddies(Person) ... int
     * this.rest.countDisBuddies(Person) ... boolean
     * this.rest.countBuddies(ILoBuddy) ... int
     */
    
    // the constructor
    ConsLoBuddy(Person first, ILoBuddy rest) {
        this.first = first;
        this.rest = rest;
    }

    // is this person in the cons list of buddy?
    public boolean findPerson(Person that) {
        return this.first.username.equals(that.username)
                || this.rest.findPerson(that);
    }

    // How many common buddy between that person and this cons list?
    public int countComBuddies(Person that) {
        if (that.hasDirectBuddy(this.first)) {
            return 1 + this.rest.countComBuddies(that);
        }
        else {
            return this.rest.countComBuddies(that);
        }
    }

    // will the given person be invited to a party
    // organized by this person?
    public boolean countDisBuddies(Person that) {
        return this.first.hasDirectBuddy(that)
                || this.rest.countDisBuddies(that);
    }

    // count the buddies in the cons list
    public int countBuddies(ILoBuddy acc) {
        if (acc.findPerson(this.first)) {
            return this.rest.countBuddies(acc);
        }
        else {
            acc = new ConsLoBuddy(this.first, acc);
            return 1
                    + this.first.buddies.countBuddies(acc)
                    + this.rest.countBuddies(acc)
                    - this.countComBuddies(this.first);
        }
    }

}

// runs tests for the buddies problem
class ExamplesBuddies {

    // Examples of Persons
    Person ann = new Person("Ann");
    Person bob = new Person("Bob");
    Person cole = new Person("Cole");
    Person dan = new Person("Dan");
    Person ed = new Person("Ed");
    Person fay = new Person("Fay");
    Person gabi = new Person("Gabi");
    Person hank = new Person("Hank");
    Person jan = new Person("Jan");
    Person kim = new Person("Kim");
    Person len = new Person("Len");

    // Examples of buddy lists
    ILoBuddy mtb = new MTLoBuddy();
    ILoBuddy bl1 = new ConsLoBuddy(this.cole, new ConsLoBuddy(this.bob,
            new MTLoBuddy()));

    // initializes every person’s buddy lists
    void initBuddies() {
        this.ann.buddies = new ConsLoBuddy(this.bob, new ConsLoBuddy(this.cole,
                this.mtb));
        this.bob.buddies = new ConsLoBuddy(this.ann, new ConsLoBuddy(this.ed,
                new ConsLoBuddy(this.hank, this.mtb)));
        this.cole.buddies = new ConsLoBuddy(this.dan, this.mtb);
        this.dan.buddies = new ConsLoBuddy(this.cole, this.mtb);
        this.ed.buddies = new ConsLoBuddy(this.fay, this.mtb);
        this.fay.buddies = new ConsLoBuddy(this.ed, new ConsLoBuddy(this.gabi,
                this.mtb));
        this.gabi.buddies = new ConsLoBuddy(this.ed, new ConsLoBuddy(this.fay,
                this.mtb));
        this.jan.buddies = new ConsLoBuddy(this.kim, new ConsLoBuddy(this.len,
                this.mtb));
        this.kim.buddies = new ConsLoBuddy(this.jan, new ConsLoBuddy(this.len,
                this.mtb));
        this.len.buddies = new ConsLoBuddy(this.jan, new ConsLoBuddy(this.kim,
                this.mtb));
    }

    
    // test the method addBuddy
    void testAddBuddy(Tester t) {
        initBuddies();
        t.checkExpect(ann.hasDirectBuddy(this.ed), false);
        this.ann.addBuddy(this.ed);
        t.checkExpect(ann.hasDirectBuddy(this.ed), true);
    }
    // test the method hasDirectBuddy
    void testHasDirectBuddy(Tester t) {
        initBuddies();
        t.checkExpect(this.ann.hasDirectBuddy(this.cole), true);
        t.checkExpect(this.ann.hasDirectBuddy(this.ed), false);
        t.checkExpect(this.ed.hasDirectBuddy(this.ed), false);
        t.checkExpect(this.dan.hasDirectBuddy(this.cole), true);
        t.checkExpect(this.cole.hasDirectBuddy(this.dan), true);
    }

    // test the method findPerson
    void testFindPerson(Tester t) {
        initBuddies();
        t.checkExpect(this.ann.buddies.findPerson(this.bob), true);
        t.checkExpect(this.ann.buddies.findPerson(this.ed), false);
        t.checkExpect(this.ed.buddies.findPerson(this.bob), false);
    }

    // test the method countComBuddies
    void testCountComBuddies(Tester t) {
        initBuddies();
        t.checkExpect(this.ann.buddies.countComBuddies(this.bob), 0);
        t.checkExpect(this.ann.buddies.countComBuddies(this.dan), 1);
        t.checkExpect(this.bob.buddies.countComBuddies(this.jan), 0);
    }

    // test the method countCommonBuddies
    void testCountCommonBuddies(Tester t) {
        initBuddies();
        t.checkExpect(this.ann.countCommonBuddies(this.bob), 0);
        t.checkExpect(this.ann.countCommonBuddies(this.dan), 1);
        t.checkExpect(this.bob.countCommonBuddies(this.jan), 0);
    }

    // test the method countCommonBuddies
    void testParty(Tester t) {
        initBuddies();
        t.checkExpect(this.cole.partyCount(), 2);
        t.checkExpect(this.ann.partyCount(), 8);
        t.checkExpect(this.hank.partyCount(), 1);
        t.checkExpect(this.jan.partyCount(), 3);
    }
    
    // test the method hasDistantBuddy
    void testHasDistantBuddy(Tester t) {
        initBuddies();
        t.checkExpect(this.cole.hasDistantBuddy(this.dan), false);
        t.checkExpect(this.ann.hasDistantBuddy(this.dan), true);
        initBuddies();
        t.checkExpect(this.ann.buddies.countDisBuddies(this.dan), true);
    }
    
 
}