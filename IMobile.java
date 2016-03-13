import tester.*;
import javalib.colors.*;
import javalib.worldimages.*;
import javalib.worldcanvas.*;
//Assignment 3
//partner1-Leyi Qiang
//partner1-Drunkbug
//partner2-Ranran He
//partner2-heranran

// to represent a mobile
interface IMobile {
    // computes the weight of the mobile
    int totalWeight();

    // computes the height of the mobile
    int totalHeight();

    // check if the mobile is balanced or not
    boolean isBalanced();

    // combine two balance mobile to a new balance mobile by given the length of
    // string
    // and the total length of the left/right side
    IMobile buildMobile(int l, int total, IMobile that);

    // count the length of the side to make mobile balance
    int buildHelper(int total, int acc, IMobile that);

    // count the width of the given mobile
    int curWidth();

    // count the left side width of a mobile
    int countLeft();

    // count the right side width of a mobile
    int countRight();

    // produce the image of this mobile that haning on the given posn
    // assume 1 length = 20 pixels
    WorldImage drawMobile(Posn p);
}

// to represent a simple mobile
class Simple implements IMobile {
    int length;
    int weight;
    IColor color;

    // the constructor
    Simple(int length, int weight, IColor color) {
        this.length = length;
        this.weight = weight;
        this.color = color;
    }

    // computes the weight of the simple mobile
    public int totalWeight() {
        return this.weight;
    }

    // computes the height of the mobile
    public int totalHeight() {
        return this.length + this.weight / 10;
    }

    // check if the simple mobile is balanced or not
    public boolean isBalanced() {
        return true;
    }

    // combine a balanced mobile to a simple mobile
    public IMobile buildMobile(int l, int total, IMobile that) {
        return new Complex(l, this.buildHelper(total, 0, that), total
                - this.buildHelper(total, 0, that), this, that);
    }

    // count the length of the side to make mobile balance
    public int buildHelper(int total, int acc, IMobile that) {
        if (new Complex(this.length, total - acc, acc, this, that).isBalanced()) {
            return total - acc;
        } 
        else {
            return this.buildHelper(total, acc + 1, that);
        }
    }

    // count the width of a simple mobile
    public int curWidth() {
        return this.countLeft() + this.countRight();
    }

    // count the left side of a simple mobile
    public int countLeft() {
        if (this.weight % 20 == 0) {
            return this.weight / 20;
        }
        else {
            return this.weight / 20 + 1;
        }
    }

    // count the right side of a simple mobile
    public int countRight() {
        if (this.weight % 20 == 0) {
            return this.weight / 20;
        }
        else {
            return this.weight / 20 + 1;
        }
    }

    // draw the simple mobile that haning on the given posn
    // assume 1 length = 20 pixels
    public WorldImage drawMobile(Posn p) {
        return new LineImage(p, new Posn(p.x, p.y + (this.length * 20)),
                new Black()).overlayImages(new RectangleImage(new Posn(p.x, p.y
                        + (this.length * 20) + (this.weight)),
                        this.weight * 2, this.weight * 2, this.color));
    }
}

/* TEMPLATE
FIELDS              
... this.length ...                                         -- int
... this.weight ...                                         -- int
... this.color ...                                          -- IColor
METHODS
... this.totalWeight() ...                                  -- int
... this.totalHeight() ...                                  -- int
... this.isBalanced() ...                                   -- boolean
... this.buildMobile(int l, int total, IMobile that) ...    -- IMobile
... this.buildHelper(int total, int acc, IMobile that) ...  -- int
... this.curWidth() ...                                     -- int
... this.countLeft() ...                                    -- int
... this.countRight() ...                                   -- int
... this.drawMobile(Posn p) ...                             -- WorldImage
METHODS FOR FIELDS: none
*/

// to represent a complex mobile
class Complex implements IMobile {
    int length;
    int leftside;
    int rightside;
    IMobile left;
    IMobile right;

    // the constructor
    Complex(int length, int leftside, int rightside, IMobile left, IMobile right) {
        this.length = length;
        this.leftside = leftside;
        this.rightside = rightside;
        this.left = left;
        this.right = right;
    }

    // computes the weight of the complex mobile
    public int totalWeight() {
        return this.left.totalWeight() + this.right.totalWeight();
    }

    // compute the height of the complex mobile
    public int totalHeight() {
        if (this.left.totalHeight() < this.right.totalHeight()) {
            return this.length + this.right.totalHeight();
        } 
        else {
            return this.length + this.left.totalHeight();
        }
    }

    // check if the complex mobile is balanced
    public boolean isBalanced() {
        if (this.leftside * this.left.totalWeight() == this.rightside
                * this.right.totalWeight()) {
            return this.left.isBalanced() && this.right.isBalanced();
        } 
        else {
            return false;
        }
    }

    // combine a balanced mobile to a complex mobile
    public IMobile buildMobile(int l, int total, IMobile that) {
        return new Complex(l, this.buildHelper(total, 0, that), total
                - this.buildHelper(total, 0, that), this, that);
    }

    // count the length of the side to make mobile balance
    public int buildHelper(int total, int acc, IMobile that) {
        if (new Complex(this.length, total - acc, acc, this, that).isBalanced()) {
            return total - acc;
        } 
        else {
            return this.buildHelper(total, acc + 1, that);
        }
    }

 // count the width of the given mobile
    public int curWidth() {
        return Math.max(this.leftside + this.left.countLeft(), 
                this.right.countLeft() - this.rightside)
                + Math.max(this.left.countRight() - this.leftside, 
                        this.rightside + this.right.countRight());
    }

    // count the left side of a given mobile
    public int countLeft() {
        return Math.max(this.leftside + this.left.countLeft(), 
                this.right.countLeft() - this.rightside);
    }

    // count the right side of a given mobile
    public int countRight() {
        return Math.max(this.left.countRight() - this.leftside, 
                this.rightside + this.right.countRight());
    }
    // draw the complex mobile that hanging on the given posn
    // assume 1 length = 20 pixels
    public WorldImage drawMobile(Posn p) {
        return new LineImage(p, new Posn(p.x, p.y + this.length * 20),
                new Black()).overlayImages(
                        (new LineImage(new Posn(p.x, p.y + this.length * 20), 
                                new Posn(p.x - this.leftside * 20, p.y + this.length * 20), 
                                new Black())), 
                        (new LineImage(new Posn(p.x, p.y + this.length * 20), 
                                new Posn(p.x + this.rightside * 20, p.y + this.length * 20), 
                                new Black())), 
                        (this.left.drawMobile(new Posn(p.x - this.leftside * 20, p.y
                                + this.length * 20))), 
                        (this.right.drawMobile(new Posn(p.x + this.rightside * 20, p.y 
                                + this.length * 20))));
    }
}

/* TEMPLATE
FIELDS              
... this.length ...                                           -- int
... this.leftside ...                                         -- int
... this.rightside ...                                        -- int
... this.left ...                                             -- IMobile
... this.right ...                                            -- IMobile
METHODS
... this.totalWeight() ...                                    -- int
... this.totalHeight() ...                                    -- int
... this.isBalanced() ...                                     -- boolean
... this.buildMobile(int l, int total, IMobile that) ...      -- IMobile
... this.buildHelper(int total, int acc, IMobile that) ...    -- int
... this.curWidth() ...                                       -- int
... this.countLeft() ...                                      -- int
... this.countRight() ...                                     -- int
... this.drawMobile(Posn p) ...                               -- WorldImage
METHODS FOR FIELDS:
... this.left.totalWeight() ...                               -- int
... this.right.totalWeight() ...                              -- int
... this.left.totalHeight() ...                               -- int
... this.right.totalHeight() ...                              -- int
... this.left.isBalanced() ...                                -- boolean
... this.right.isBalanced() ...                               -- boolean
... this.left.buildMobile(int l, int total, IMobile that) ... -- IMobile
... this.right.buildMobile(int l, int total, IMobile that)... -- IMobile
... this.left.curWidth() ...                                  -- int
... this.right.curWidth() ...                                 -- int
... this.left.countLeft() ...                                 -- int
... this.right.countLeft() ...                                -- int
... this.left.countRight() ...                                -- int
... this.right.countRight() ...                               -- int
... this.left.drawMobile(Posn p) ...                          -- WorldImage
... this.right.drawMobile(Posn p) ...                         -- WorldImage
*/

// to represent different examples of mobiles
class ExamplesMobiles {
    // a simple mobile
    IMobile exampleSimple = new Simple(2, 20, new Blue());

    IMobile exampleSimple1 = new Simple(1, 36, new Blue());
    IMobile exampleSimple2 = new Simple(1, 12, new Red());
    IMobile exampleSimple3 = new Simple(2, 36, new Red());
    IMobile exampleSimple4 = new Simple(1, 60, new Green());
    IMobile exampleComplex1 = new Complex(2, 5, 3, this.exampleSimple3,
            this.exampleSimple4);
    IMobile exampleComplex2 = new Complex(2, 8, 1, this.exampleSimple2,
            this.exampleComplex1);
    // a complex mobile
    IMobile exampleComplex = new Complex(1, 9, 3, this.exampleSimple1,
            this.exampleComplex2);
    IMobile example3 = new Complex(3, 5, 5, this.exampleComplex,
            this.exampleComplex1);
    // examples for tests
    IMobile example4 = new Complex(2, 240, 144, this.exampleComplex, 
            this.example3);
    IMobile example5 = new Complex(2, 5, 5, this.exampleComplex, 
            this.exampleComplex);
    IMobile example6 = new Complex(2, 3, 4, this.exampleSimple1, this.exampleSimple2);

    // test the method totalWeight of a mobile
    boolean testWeight(Tester t) {
        return t.checkExpect(this.exampleSimple.totalWeight(), 20)
                && t.checkExpect(this.exampleComplex.totalWeight(), 144);
    }

    // test the method totalHeight of a mobile
    boolean testHeight(Tester t) {
        return t.checkExpect(this.exampleSimple.totalHeight(), 4)
                && t.checkExpect(this.exampleComplex.totalHeight(), 12)
                && t.checkExpect(this.example3.totalHeight(), 15);
    }

    // test the method isBalanced of a mobile
    boolean testBalanced(Tester t) {
        return t.checkExpect(this.exampleSimple.isBalanced(), true)
                && t.checkExpect(this.exampleComplex.isBalanced(), true)
                && t.checkExpect(this.example3.isBalanced(), false)
                && t.checkExpect(this.example4.isBalanced(), false)
                && t.checkExpect(this.example5.isBalanced(), true);
    }

    // test the method buildMobile of two mobile
    boolean testBuild(Tester t) {
        return t.checkExpect(
                this.exampleSimple.buildMobile(1, 10, this.exampleSimple),
                new Complex(1, 5, 5, this.exampleSimple, this.exampleSimple))
                && t.checkExpect(this.exampleComplex.buildMobile(2, 10,
                        this.exampleComplex), new Complex(2, 5, 5,
                            this.exampleComplex, this.exampleComplex))
                && t.checkExpect(this.exampleSimple.buildMobile(3, 41,
                        this.exampleComplex), new Complex(3, 36, 5,
                            this.exampleSimple, this.exampleComplex))
                && t.checkExpect(this.exampleComplex.buildMobile(3, 41,
                        this.exampleSimple), new Complex(3, 5, 36,
                            this.exampleComplex, this.exampleSimple));
    }
    // test the method buildHelper of a mobile
    boolean testBuildHelper(Tester t) {
        return t.checkExpect(this.exampleSimple1.buildHelper(12, 3,
                this.exampleComplex2), 9) 
                && 
                t.checkExpect(this.exampleSimple1.buildHelper(12, 1, 
                        this.exampleComplex2), 9);
    }

    // test the method curWidth of a mobile
    boolean testCur(Tester t) {
        return t.checkExpect(this.exampleSimple.curWidth(), 2)
                && t.checkExpect(this.exampleComplex.curWidth(), 21);
    }
    // test the method countLeft of a mobile
    boolean testCountLeft(Tester t) {
        return t.checkExpect(this.exampleSimple4.countLeft(), 3) &&
                t.checkExpect(this.exampleSimple2.countLeft(), 1) &&
                t.checkExpect(this.exampleComplex1.countLeft(), 7);
    }
    
    // test the method countRight of a mobile
    boolean testCountRight(Tester t) {
        return t.checkExpect(this.exampleSimple4.countRight(), 3) &&
                t.checkExpect(this.exampleSimple2.countRight(), 1) &&
                t.checkExpect(this.exampleComplex1.countRight(), 6);
    }
    

    
    
    // test the method drawMobile of a mobile
    boolean testDrawMobile(Tester t) {
        return t.checkExpect(this.exampleSimple.drawMobile(new Posn(300, 
                200)), new LineImage(new Posn(300, 200), 
                        new Posn(300, 240), 
                        new Black()).overlayImages(new 
                                RectangleImage(new Posn(300, 260), 
                                        40, 40, new Blue())))                                     
               && t.checkExpect(this.example6.drawMobile(new Posn(500, 0)),
                       new LineImage(new Posn(500, 0), new Posn(500, 0 + 40),
                               new Black()).overlayImages(
                                       (new LineImage(new Posn(500, 0 + 40), 
                                               new Posn(500 - 3 * 20, 0 + 2 * 20), 
                                               new Black())), 
                                       (new LineImage(new Posn(500, 0 + 2 * 20), 
                                               new Posn(500 + 4 * 20, 0 + 2 * 20), 
                                               new Black())), 
                                       (this.exampleSimple1.drawMobile(new Posn(500 - 3 * 20, 0
                                               + 2 * 20))), 
                                       (this.exampleSimple2.drawMobile(new Posn(500 + 4 * 20, 0 
                                               + 2 * 20)))));
    }
    
    

}

class ExamplesDraws {
    IMobile exampleSimple = new Simple(2, 20, new Blue());

    IMobile exampleSimple1 = new Simple(1, 36, new Blue());
    IMobile exampleSimple2 = new Simple(1, 12, new Red());
    IMobile exampleSimple3 = new Simple(2, 36, new Red());
    IMobile exampleSimple4 = new Simple(1, 60, new Green());
    IMobile exampleComplex1 = new Complex(2, 5, 3, this.exampleSimple3,
            this.exampleSimple4);
    IMobile exampleComplex2 = new Complex(2, 8, 1, this.exampleSimple2,
            this.exampleComplex1);
    // a complex mobile
    IMobile exampleComplex = new Complex(1, 9, 3, this.exampleSimple1,
            this.exampleComplex2);
    // test the method drawMobile of a mobile
    // assume 1 length = 20 pixels
    boolean checkDrawImage(Tester t) {
        WorldCanvas c = new WorldCanvas(1000, 1000);
        return t.checkExpect(
                c.show() 
                && c.drawImage(this.exampleComplex.drawMobile(new Posn(500, 
                        250))),
                true)
                && t.checkExpect(
                        c.show()
                        && c.drawImage(this.exampleSimple.drawMobile(new Posn( 0, 250))));
        
    }
}