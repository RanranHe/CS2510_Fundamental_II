

interface ITaco { }

class EmptyShell implements ITaco {
    Boolean softShell;
    
    // the constructor
    EmptyShell(Boolean softShell) {
        this.softShell = softShell;
    }
    /*
     * this.soft_shell ... Boolean
     */
}

class Filled implements ITaco {
    ITaco taco;
    String filling;
    
    // the constructor
    Filled(ITaco taco, String filling) {
        this.taco = taco;
        this.filling = filling;
    }
    /*
     * this.filling ... String
     */
}


class ExamplesTaco {
    ITaco soft = new EmptyShell(true);
    ITaco hard = new EmptyShell(false);
    
    ITaco fill1 = new Filled(this.soft, "carnitas");
    ITaco fill2 = new Filled(this.fill1, "salsa");
    ITaco fill3 = new Filled(this.fill2, "lettuce");
    
    ITaco fill4 = new Filled(this.hard, "veggies");
    ITaco fill5 = new Filled(this.fill4, "guacamole");
    
    ITaco order1 = new Filled(this.fill3, "cheddar cheese");
    ITaco order2 = new Filled(this.fill5, "sour cream"); 
    
}