//Assignment 6
//partner1-Qiang Leyi 
//partner1-Drunkbug
//partner2-He Ranran  
//partner2-heranran

import tester.Tester;

// To represent a Arith
interface IArith {
    // To return the result of applying the given visitor to this Arith
    <R> R accept(IArithVisitor<R> visitor);  
}

// to represent a const
class Const implements IArith {
    double num;
    
    // constructor
    Const(double num) {
        this.num = num;
    }
    
    /*
     ... this.num ...  -- double
     
     ... accept(IArithVisitor<R> visitor) ...  -- R
     */
    
    // To return the result of applying the given visitor to this Const
    public <R> R accept(IArithVisitor<R> visitor) { 
        return visitor.visitConst(this); 
    }
}

// to represent a formula
class Formula implements IArith {
    IFunc2<Double, Double, Double> fun;
    String name;
    IArith left;
    IArith right;
    
    // constructor
    Formula(IFunc2<Double, Double, Double> fun, String name, 
            IArith left, IArith right) {
        this.fun = fun;
        this.name = name;
        this.left = left;
        this.right = right;
    }
    
    /*
    ... this.fun ...  -- IFunc2
    ... this.name ...  -- String
    ... this.left ...  -- IArith
    ... this.right ...  -- IArith
    
    ... accept(IArithVisitor<R> visitor) ...  -- R
    
    ... this.left.accept(IArithVisitor<R> visitor) ...  -- R
    ... this.right.accept(IArithVisitor<R> visitor) ...  -- R
    */
    
    // To return the result of applying the given visitor to this Formula
    public <R> R accept(IArithVisitor<R> visitor) { 
        return visitor.visitFormula(this); 
    }
}

//Interface for two-argument function-objects with signature [A1, A2 -> R]
interface IFunc2<A1, A2, R> {
    R apply(A1 a1, A2 a2);
}

// to represent two double divided
class Div implements IFunc2<Double, Double, Double> {
    // two double divided
    public Double apply(Double a1, Double a2) {
        return a1 / a2;
    } 
}

// to represent two double minues
class Min implements IFunc2<Double, Double, Double> {
    // two double minues
    public Double apply(Double a1, Double a2) {
        return a1 - a2;
    }
}

//To implement a function over Arith objects, returning a value of type R
interface IArithVisitor<R> {
    // visit Const
    R visitConst(Const c);
    
    // visit Formula
    R visitFormula(Formula f);
}

// visits an IArith and evaluates the tree to a Double answer
class EvalVisitor implements IArithVisitor<Double> {
    // visit Const
    public Double visitConst(Const c) {
        return c.num;
    }
    
    // visit Formula
    public Double visitFormula(Formula f) {
        return f.fun.apply(f.left.accept(this), f.right.accept(this));
    }
}

// visits an IArith and produces a String 
// showing the fully-parenthesized expression
class PrintVisitor implements IArithVisitor<String> {
    // visit Const
    public String visitConst(Const c) {
        return Double.toString(c.num);
    }
    
    // visit Formula
    public String visitFormula(Formula f) {
        return "(" + f.name + " " + f.left.accept(this)
                + " " + f.right.accept(this) + ")";
    }
}

// vists an IArith and produces another IArith, 
// where every Const in the tree has been doubled
class DoublerVisitor implements IArithVisitor<IArith> {
    // visit Const
    public IArith visitConst(Const c) {
        return new Const(2 * c.num);
    }
    
    // visit Formula
    public IArith visitFormula(Formula f) {
        return new Formula(f.fun, f.name, f.left.accept(this),
                f.right.accept(this));
    }
}

// visits an IArith and produces a Boolean that is true
// if every constant in the tree is less than 10
class AllSmallVisitor implements IArithVisitor<Boolean> {
    // visit Const
    public Boolean visitConst(Const c) {
        return c.num < 10;
    }
    
    // visit Formula
    public Boolean visitFormula(Formula f) {
        return f.left.accept(this) && f.right.accept(this);
    }
}

// visits an IArith and produces a Boolean that is true 
// if anywhere there is a Formula named "div", 
// the right argument does not evaluate to roughly zero
class NoDivBy0 implements IArithVisitor<Boolean> {
    // visit Const
    public Boolean visitConst(Const c) {
        return (c.num >= 0.0001 || c.num <= -0.0001)
                && c.num != 0.0;
    }
    
    // visit Formula
    public Boolean visitFormula(Formula f) {
        return f.name.equals("div") && 
                new Const(f.right.accept(new EvalVisitor())).accept(this) &&
                f.right.accept(this);
    }
}

class Examples {
    // Const examples
    IArith const1 = new Const(1.0);
    IArith const2 = new Const(4.0);
    IArith const3 = new Const(0.0000001);
    
    // Formula examples
    IArith formula1 = new Formula(new Div(), "div", this.const1, this.const2);
    IArith formula2 = new Formula(new Min(), "min", this.const1, this.const3);
    IArith formula3 = new Formula(new Min(), "min", 
            this.const1, this.formula2);
    
    // test accept method
    boolean testAccept(Tester t) {
        return t.checkExpect(this.const1.accept(new EvalVisitor()), 1.0) &&
                t.checkExpect(this.const1.accept(new PrintVisitor()), "1.0") &&
                t.checkExpect(this.const1.accept(new DoublerVisitor()), 
                        new Const(2.0)) &&
                t.checkExpect(this.const1.accept(new AllSmallVisitor()),
                        true) &&
                t.checkExpect(this.const1.accept(new NoDivBy0()), true) &&
                t.checkExpect(this.formula1.accept(new EvalVisitor()), 0.25) &&
                t.checkExpect(this.formula1.accept(new PrintVisitor()), 
                        "(div 1.0 4.0)") &&
                t.checkExpect(this.formula1.accept(new DoublerVisitor()), 
                        new Formula(new Div(), "div", new Const(2.0),
                                new Const(8.0))) &&
                t.checkExpect(this.formula1.accept(new AllSmallVisitor()),
                        true) &&
                t.checkExpect(this.formula1.accept(new NoDivBy0()), true) &&
                t.checkExpect(this.formula3.accept(new NoDivBy0()), false);
    }
    
    // test method apply
    boolean testApply(Tester t) {
        return t.checkExpect(new Div().apply(1.0, 2.0), 0.5) &&
                t.checkExpect(new Min().apply(1.0, 1.0), 0.0);
    }
}