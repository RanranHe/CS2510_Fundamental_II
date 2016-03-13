interface IHabitation { }

class Planet implements IHabitation { 
    String name;
    int population; // in thousands of people
    int spaceports;
    
    // the constructor
    Planet(String name, int population, int spaceports) {
        this.name = name;    
        this.population = population;
        this.spaceports = spaceports;
    }
    
    /*
     * this.name        ... String
     * this.population  ... int
     * this.spacesports ... int
     */
}

class SpaceStation implements IHabitation { 
    String name;
    int population; 
    int transporterPads;
    
    // the constructor
    SpaceStation(String name, int population, int transporterPads) {
        this.name = name;
        this.population = population;
        this.transporterPads = transporterPads;
    }
    
    /*
     * this.name             ... String
     * this.population       ... int
     * this.transporterPads ... int
     */
}

interface ITransportation {
}

class Shuttle implements ITransportation {
    IHabitation from;
    IHabitation to;
    int fuel; // to the nearest kilogram
    int capacity;
    
    // the constructor
    Shuttle(IHabitation from, IHabitation to, int fuel, int capacity) {
        this.from = from;
        this.to = to;
        this.fuel = fuel;
        this.capacity = capacity;
    }
    
    /*
     * this.from     ... IHabitation
     * this.to       ... IHabitation
     * this.fuel     ... int
     * this.capacity ... int
     */
    

}

class SpaceElevator implements ITransportation {
    IHabitation from;
    IHabitation to;
    int supply;
    
    // the constructor
    SpaceElevator(IHabitation from, IHabitation to, int supply) {
        this.from = from;
        this.to = to;
        this.supply = supply;
    }
    /*
     * this.from    ... IHabitation
     * this.to      ... IHabitation
     * this.supply  ... int
     */
}

class Transporter implements ITransportation {
    IHabitation from;
    IHabitation to;
    
    // the constructor
    Transporter(IHabitation from, IHabitation to) {
        this.from = from;
        this.to = to;
    }
    /*
     * this.from ... IHabitation
     * this.to   ... IHabitation
     */
}

class ExamplesTravel {
    IHabitation nausicant = new Planet("Nausicant", 6000000, 8);
    IHabitation earth = new Planet("Earth", 9000000, 14);
    IHabitation remus = new Planet("Remus", 18000000, 23);
    IHabitation watcherGrid = new SpaceStation("WatcherGrid", 1, 0);
    IHabitation deepSpace42 = new SpaceStation("Deep Space 42", 7, 8);
    IHabitation lol = new SpaceStation("lol", 500, 3);
    
    ITransportation shuttle1 = new Shuttle(this.earth, this.deepSpace42, 50, 5);
    ITransportation shuttle2 = new Shuttle(this.nausicant, this.remus, 300, 70);
    ITransportation elevator1 = new SpaceElevator(this.remus, this.lol, 10);
    ITransportation elevator2 = new SpaceElevator(this.watcherGrid, this.remus, 500);
    ITransportation transporter1 = new Transporter(this.lol, this.nausicant);
    ITransportation transporter2 = new Transporter(this.earth, this.deepSpace42);
}
