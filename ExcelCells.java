import tester.*;
//Assignment 2
//partner1-Leyi Qiang
//partner1-Drunkbug
//partner2-Ranran He
//partner2-heranran

class Cell {
    String column;
    int row;
    IData data;
    
    // the constructor
    Cell(String column, int row, IData data) {
        this.row = row;
        this.column = column;
        this.data = data;
    }
    
    // computes the value of this cell
    int value() {
        return this.data.value();
    }
    //computes the number of cells that 
    //contain numbers (not formulas) 
    //involved in computing the value of this Cell
    int countArgs() {
        if (this.row == 1) {
            return 1;
        }
        else {
            return this.data.countArgs();
        }
    }
    // computes the number of functions involved in computing the value of this Cell
    int countFuns() {
        if (this.row == 1) {
            return 0;
        }
        else {
            return this.data.countFuns();
        }
    }
    //compute the depth of the Cell
    int formulaDepth() {
        if (this.row == 1) {
            return 0;
        }
        else {
            return this.data.formulaDepth();
        }
    }
    //takes an integer and produces a String representing the contents for this Cell
    String formula(int a) {
        if (a < 0) {
            return "";
        }
        else {
            if (a == 0) {
                return this.column + Integer.toString(this.row);
            }
            else {
                return this.data.formula(a);
            }  
        }
    }

}

/* TEMPLATE
FIELDS              
... this.column ...   -- String 
... this.row ...  -- int        
... this.data ...   -- IData           
METHODS
... this.value() ...  -- int
... this.countArgs() ...  -- int
... this.countFuns() ...  -- int
... this.formulaDepth() ...  -- int
... this.formula(int a) ...  -- String
METHODS FOR FIELDS:
... this.data.value() ...  -- int
... this.data.countFuns() ...  -- int
... this.data.countArgs() ...  -- int
... this.data.formulaDepth() ...  -- int
... this.data.formula(int) ...  -- String
*/

interface IData {
    // computes the value of this IData
    int value();
    //computes the number of cells that 
    //contain numbers (not formulas) 
    //involved in computing the value of this IData
    int countArgs();
    // computes the number of functions involved in computing the value of the data
    int countFuns();
    //compute the depth of the IData
    int formulaDepth();
    //takes an integer and produces a String representing the contents for this Idata
    String formula(int n);
}

class Number implements IData {
    int n;
    
    // the constructor
    Number(int n) {
        this.n = n;
    }
    // computes the value of this number
    public int value() {
        return this.n;
    }
    //computes the number of cells in the Number
    public int countArgs() {
        return 1;
    }
    //computes the number of  functions in the Number
    public int countFuns() {
        return 0;
    }
    
    //computes the depth of the number
    public int formulaDepth() {
        return 1;
    }
    //takes an integer and produces a String representing the contents for this Number
    public String formula(int a) {
        return Integer.toString(this.n);
    }
}

/* TEMPLATE
FIELDS              
... this.int ...   -- int           
METHODS
... this.value() ...  -- int
... this.countArgs() ...  -- int
... this.countFuns() ...  -- int
... this.formulaDepth() ...  -- int
... this.formula(int a) ...  -- String
METHODS FOR FIELDS:
... this.n.value() ...  -- int
... this.n.countFuns() ...  -- int
... this.n.countArgs() ...  -- int
... this.n.formulaDepth() ...  -- int
... this.n.formula(int) ...  -- String
*/

class Formular implements IData {
    String func;
    Cell cell1;
    Cell cell2;
    
    // the constructor
    Formular(String func, Cell cell1, Cell cell2) {
        this.func = func;
        this.cell1 = cell1;
        this.cell2 = cell2;
    }
   // computes the value of this formula
    public int value() {
        if (this.func.equals("mul")) {
            return this.cell1.value() * this.cell2.value();
        }
        else {
            if (this.func.equals("mod")) {
                return this.cell1.value() % this.cell2.value();
            }
            else {
                return this.cell1.value() - this.cell2.value();
            }
        }
    }
   //computes the number of cells in Number
    public int countArgs() {
        return this.cell1.countArgs() + this.cell2.countArgs();
    }
    //computes the number of functions in the Formula
    public int countFuns() {
        return 1 + this.cell1.countFuns() + this.cell2.countFuns();
    }
    //computes the depth of the formula
    public int formulaDepth() {
        if (this.cell1.formulaDepth() < this.cell2.formulaDepth()) {
            return 1 + this.cell2.formulaDepth();
        }
        else {
            return 1 + this.cell1.formulaDepth();
        }
    }
    //takes an integer and produces a String representing the contents for this formula
    public String formula(int a) {
        return this.func + "(" + this.cell1.formula(a - 1) + ", " + this.cell2.formula(a - 1) + ")";
    }
}

/* TEMPLATE
FIELDS              
... this.func ...   -- String 
... this.cell1 ...  -- Cell        
... this.cell2 ...   -- Cell           
METHODS
... this.value() ...  -- int
... this.countArgs() ...  -- int
... this.countFuns() ...  -- int
... this.formulaDepth() ...  -- int
... this.formula(int a) ...  -- String
METHODS FOR FIELDS:
... this.cell1.value() ...  -- int
... this.cell2.value() ...  -- int
... this.cell1.countFuns() ...  -- int
... this.cell2.countFuns() ...  -- int
... this.cell1.countArgs() ...  -- int
... this.cell2.countArgs() ...  -- int
... this.cell1.formulaDepth() ...  -- int
... this.cell2.formulaDepth() ...  -- int
... this.cell1.formula(int) ...  -- String
... this.cell2.formula(int) ...  -- String
*/

/*
+---------------+                    
|     Cell      |                    
+---------------+                    
| int column    |                    
|               |                    
| int Row       |                    
|               |                    
| IData data    +-------+                      
+---------------+       |            
                        |            
                        |            
           +------------+            
           |                         
           v                         
                                     
     +-------------+                 
     |    IData    |                 
     +-------------+                 
 +---+  Number     |                 
 |   |             |                 
 |   |  Formular   +-------+         
 |   +-------------+       |         
 |                         |         
++------+                  |         
| Number|           +------+--------+
+-------+           |   Formular    |
| int n |           +---------------+
|       |           | String func   |
+-------+           |               |
                    | Cell cell1    |
                    |               |
                    | Cell cell2    |
                    +---------------+ 
 */


class ExamplesExcelCells {
    Cell cellA1 = new Cell("A", 1, new Number(13));
    Cell cellB1 = new Cell("B", 1, new Number(5));
    Cell cellC1 = new Cell("C", 1, new Number(2));
    Cell cellD1 = new Cell("D", 1, new Number(3));
    Cell cellE1 = new Cell("E", 1, new Number(7));
    Cell cellA3 = new Cell("A", 3, new Formular("mul", this.cellA1, this.cellB1));
    Cell cellB2 = new Cell("B", 2, new Formular("sub", this.cellA3, this.cellC1));
    Cell cellE2 = new Cell("E", 2, new Formular("sub", this.cellE1, this.cellD1));
    Cell cellD2 = new Cell("D", 2, new Formular("mod", this.cellB2, this.cellE2));
    Cell cellB3 = new Cell("B", 3, new Formular("mod", this.cellE1, this.cellA3));
    Cell cellD3 = new Cell("D", 3, new Formular("mul", this.cellD2, this.cellA1));
    Cell cellC4 = new Cell("C", 4, new Formular("mul", this.cellE1, this.cellD1));
    Cell cellD4 = new Cell("D", 4, new Formular("sub", this.cellC4, this.cellA1));
    Cell cellC5 = new Cell("C", 5, new Formular("sub", this.cellD4, this.cellB3));
    Cell cellA5 = new Cell("A", 5, new Formular("mod", this.cellD3, this.cellC5));
    
    IData num1 = new Number(5);
    /*
    IData A2 = new Formula("mul", this.cellA5, this.cellB3);
    Cell cellA2 = new Cell("A", 2, this.A2);
    IData C3 = new Formula("mod", this.cellB3, this.cellC4);
    Cell cellC3 = new Cell("C", 3, this.A3);
    IData D5 = new Formula("sub", this.cellD2, this.cellE2);
    Cell cellD5 = new Cell("D", 5, this.D5);
     */
    boolean testCell(Tester t) {
        return t.checkExpect(this.cellA1.value(), 13) &&
                t.checkExpect(this.cellA3.value(), 65) &&
                t.checkExpect(this.cellD3.value(), 39);
    }
    
    boolean testArgs(Tester t) {
        return t.checkExpect(this.cellA1.countArgs(), 1) &&
                t.checkExpect(this.cellD2.countArgs(), 5) &&
                t.checkExpect(this.cellC5.countArgs(), 6) &&
                t.checkExpect(this.num1.countArgs(), 1);
    }
    
    boolean testFuns(Tester t) {
        return t.checkExpect(this.cellD2.countFuns(), 4) &&
                t.checkExpect(this.cellA3.countFuns(), 1) &&
                t.checkExpect(this.cellA1.countFuns(), 0) &&
                t.checkExpect(this.cellC5.countFuns(), 5) &&
                t.checkExpect(this.num1.countFuns(), 0);
    }
    boolean testDepths(Tester t) {
        return t.checkExpect(this.cellA1.formulaDepth(), 0) &&
                t.checkExpect(this.cellD2.formulaDepth(), 3) &&
                t.checkExpect(this.cellC5.formulaDepth(), 3) &&
                t.checkExpect(this.num1.formulaDepth(), 1);
    }
    boolean testFormula(Tester t) {
        return t.checkExpect(this.cellD2.formula(4), "mod(sub(mul(13, 5), 2), sub(7, 3))") &&
                t.checkExpect(this.cellD2.formula(2), "mod(sub(A3, C1), sub(E1, D1))") &&
                t.checkExpect(this.cellA1.formula(2), "13") &&
                t.checkExpect(this.cellD2.formula(0), "D2") &&
                t.checkExpect(this.cellA1.formula(-1), "") &&
                t.checkExpect(this.cellA3.formula(3), "mul(13, 5)") &&
                t.checkExpect(this.cellB2.formula(7), "sub(mul(13, 5), 2)");
    }         
}
