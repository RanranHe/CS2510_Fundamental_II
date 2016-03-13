
class Person {
    String name;
    int yob;
    String state;
    boolean citizen;
    
    
    // the constructor
    Person(String name, int yob, String state, boolean citizen) {
        this.name = name;
        this.yob = yob;
        this.state = state;
        this.citizen = citizen;
    }
    /*
     * this.name    ... String
     * this.yob     ... int
     * this.state   ... String
     * this.citizen ... boolean
     */
}


class ExamplesPerson {
    Person jackie = new Person("Jackie Robinson", 1920, "NY", true);
    Person golda = new Person("Golda Meir", 1930, "MA", false);
    Person leyi = new Person("Leyi Qiang", 1995, "MA", false);
}