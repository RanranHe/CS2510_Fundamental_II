import java.awt.Color;
import java.util.Random;

import tester.*;
import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldcanvas.*;
import javalib.worldimages.*;
//Assignment 5
//partner1-Qiang Leyi
//partner1-Drunkbug
//partner2-He Ranran
//partner2-heranran

// represents the falling apple
class Apple {
    Posn center;
    int type;

    // the constructor
    Apple(Posn center, int type) {
        this.center = center;
        this.type = type;
    }
    /*
     * fields:
     * ... this.center ... -- center
     * ... this.type ... -- type
     * 
     * methods:
     * ... moveDown() ... -- Apple
     * ... randomAppear(int) ... -- Apple
     * .. onTheGround(int) ... --boolean
     */

    // produce the image of the apple on the current Posn
    // there are 3 types of apple (0 to 2) and one Doge(3)
    WorldImage appleImage() {
        if (this.type == 0) {
            return new FromFileImage(this.center, "red-apple.png");
        } 
        else if (this.type == 1) {
            return new FromFileImage(this.center, "small-red-apple.png");
        } 
        else if (this.type == 3) {
            return new FromFileImage(this.center, "Doge.png");
        } 
        else {
            return new FromFileImage(this.center, "yellow-apple.png");
        }
    }

    // let the apple fall down
    public Apple moveDown() {
        if (this.type == 3) {
            return new Apple(new Posn(this.center.x, this.center.y + 25),
                    this.type);
        } 
        else {
            return new Apple(new Posn(this.center.x, this.center.y + 15),
                    this.type);
        }
    }

    // produce a new Apple in a random place
    public Apple randomAppear(int n) {
        return new Apple(new Posn(this.center.x, new Random().nextInt(n)),
                this.type);
    }

    // check if the apple is on the ground
    boolean onTheGround(int height) {
        return this.center.y >= height;
    }

}

// represents the falling basket
class Basket {
    Posn center;

    // the constructor
    Basket(Posn center) {
        this.center = center;
    }
    /*
     * fields:
     * ... this.center ... -- center
     * 
     * methods:
     * ... moveOnKey(String) ... -- Basket

     */

    // move the basket to left/right by pressing the left /right arrow key
    public Basket moveOnKey(String ke) {
        if (ke.equals("left")) {
            return new Basket(new Posn(this.center.x - 5, this.center.y));
        } 
        else {
            if (ke.equals("right")) {
                return new Basket(new Posn(this.center.x + 5, this.center.y));
            } 
            else {
                return this;
            }
        }
    }

    // represent the image of the basket
    WorldImage basketImage() {
        return new FromFileImage(this.center, "easter-basket.png");
    }
}

// represents the Apple Orchard Game
public class AppleGame extends World {
    int width = 400;
    int height = 400;
    Apple a;
    Basket b;
    int score;

    // the constructor
    public AppleGame(Apple a, Basket b, int score) {
        super();
        this.a = a;
        this.b = b;
        this.score = score;
    }
    /*
     * fields:
     * ... this.width ... -- int
     * ... this.height ... -- int
     * ... this.a ... -- Apple
     * ... this.b ... -- Basket
     * ... this.score ... -- int
     * 
     * methods:
     * ... caughtApple() ... boolean
     * ... fall(int int) ... World
     * ... onTick() ... World
     * ... makeImage() ... WorldImage
     * ... onKeyEvent(String) ... -- AppleGame
     * ... WorldEnds() ... -- WorldEnd
     * 
     */

    // is the basket caught the apple(Doge)?
    public boolean caughtApple() {
        // if the basket caught Doge
        if (this.a.type == 3) {
            return this.a.center.y == this.b.center.y
                    && this.a.center.x <= this.b.center.x + 100
                    && this.a.center.x >= this.b.center.x - 100;
        } 
        else {
            return this.a.center.y == this.b.center.y
                    && this.a.center.x <= this.b.center.x + 55
                    && this.a.center.x >= this.b.center.x - 55;
        }
    }

    // produce an apple as it should appear on the next tick
    World fall(int width, int height) {
        // if the basket caught Doge, -2 score
        if (this.caughtApple() && this.a.type == 3) {
            return new AppleGame(new Apple(new Posn(
                    new Random().nextInt(width), 50), new Random().nextInt(4)),
                    this.b, this.score - 2);
        } 
        else if (this.caughtApple() && this.a.type != 3) {
            return new AppleGame(new Apple(new Posn(
                    new Random().nextInt(width), 50), new Random().nextInt(4)),
                    this.b, this.score + 1);
        } 
        else if (this.a.type == 3 && this.a.onTheGround(height)) {
            return new AppleGame(
                    new Apple(new Posn(new Random().nextInt(width), 50),
                            new Random().nextInt(4)).moveDown(), this.b,
                    this.score);
        } 
        else {
            return new AppleGame(this.a.moveDown(), this.b, this.score);
        }
    }

    // run the World that should appear on the next tick
    public World onTick() {
        return fall(width, height);
    }

    // represent the background image of the world
    public WorldImage appleTree = new RectangleImage(new Posn(200, 200),
            this.width, this.height, new White())
            .overlayImages(new FromFileImage(new Posn(200, 200),
                    "apple-tree.png"));

    // represent the background image and the apple/basket image of the world
    public WorldImage makeImage() {
        return new OverlayImages(this.appleTree, new OverlayImages(
                this.a.appleImage(), new OverlayImages(this.b.basketImage(),
                        new TextImage(new Posn(350, 50),
                                Integer.toString(this.score), 20, 4,
                                Color.black))));
    }

    // represent the world's key event
    public AppleGame onKeyEvent(String ke) {
        return new AppleGame(this.a, this.b.moveOnKey(ke), this.score);

    }

    // check if game over or not
    public WorldEnd worldEnds() {
        if (this.a.onTheGround(height) && this.a.type != 3) {
            return new WorldEnd(true, new OverlayImages(this.makeImage(),
                    new TextImage(new Posn(200, 50),
                            "Apple falls on the ground!", Color.black)));
        } 
        else if (this.score < 0) {
            return new WorldEnd(true, new OverlayImages(this.makeImage(),
                    new TextImage(new Posn(200, 50), "Doge Ate All the Apple!",
                            Color.black)));
        } 
        else if (this.score == 10) {
            return new WorldEnd(true, new OverlayImages(this.makeImage(),
                    new TextImage(new Posn(200, 50),
                            "Congraluations! You Got 10 Apples!", Color.black)));
        } 
        else {
            return new WorldEnd(false, this.makeImage());
        }
    }

}

// contain examples of the game and tests for all methods
class ExameplesAppleGame {
    AppleGame w1 = new AppleGame(new Apple(new Posn(200, 50),
            new Random().nextInt(4)), new Basket(new Posn(200, 350)), 0);

    boolean runAnimation = this.w1.bigBang(400, 400, 0.3);

    AppleGame w2 = new AppleGame(new Apple(new Posn(200, 350), 1), new Basket(
            new Posn(200, 350)), 0);

    AppleGame wd = new AppleGame(new Apple(new Posn(300, 350), 3), new Basket(
            new Posn(200, 350)), 0);

    // worlds that are game over
    AppleGame we = new AppleGame(new Apple(new Posn(200, 500), 1), new Basket(
            new Posn(200, 350)), 0);

    AppleGame we2 = new AppleGame(new Apple(new Posn(300, 350), 3), new Basket(
            new Posn(200, 350)), -2);

    AppleGame we3 = new AppleGame(new Apple(new Posn(300, 350), 3), new Basket(
            new Posn(200, 350)), 10);

    Apple a1 = new Apple(new Posn(200, 50), 1);
    Apple a2 = new Apple(new Posn(200, 500), 1);

    Apple d1 = new Apple(new Posn(200, 50), 3);

    Basket b1 = new Basket(new Posn(200, 350));

    // test the AppleImage method for different types of apples
    boolean testAppleImage(Tester t) {
        return t.checkExpect(this.a1.appleImage(), new FromFileImage(
                this.a1.center, "small-red-apple.png"));
    }

    // test the moveDown method for different types of apples
    boolean testMoveDown(Tester t) {
        return t.checkExpect(this.a1.moveDown(), new Apple(new Posn(
                this.a1.center.x, this.a1.center.y + 15), 1))
                && t.checkExpect(this.d1.moveDown(), new Apple(new Posn(
                        this.d1.center.x, this.a1.center.y + 25), 3));
    }

    // test the method onTheGround for different types of apples
    boolean testOnTheGround(Tester t) {
        return t.checkExpect(this.a1.onTheGround(400), false)
                && t.checkExpect(this.a2.onTheGround(400), true);
    }

    // test the method moveOnKey for basket
    boolean testMoveOnKey(Tester t) {
        return t.checkExpect(this.b1.moveOnKey("left"), new Basket(new Posn(
                195, 350)))
                && t.checkExpect(this.b1.moveOnKey("x"), new Basket(new Posn(
                        200, 350)));
    }

    // test the method caughtApple for AppleGame
    boolean testCaughtApple(Tester t) {
        return t.checkExpect(this.w1.caughtApple(), false)
                && t.checkExpect(this.w2.caughtApple(), true)
                && t.checkExpect(this.wd.caughtApple(), true);
    }

    // test the method WorldEnd for AppleGame
    boolean testFall(Tester t) {
        return t.checkExpect(this.we2.worldEnds(), new WorldEnd(true,
                new OverlayImages(this.we2.makeImage(), new TextImage(new Posn(
                        200, 50), "Doge Ate All the Apple!", Color.black))))
                && t.checkExpect(this.we.worldEnds(), new WorldEnd(true,
                        new OverlayImages(this.we.makeImage(), new TextImage(
                                new Posn(200, 50),
                                "Apple falls on the ground!", Color.black))))
                && t.checkExpect(this.we3.worldEnds(), new WorldEnd(true,
                        new OverlayImages(this.we3.makeImage(), new TextImage(
                                new Posn(200, 50),
                                "Congraluations! You Got 10 Apples!",
                                Color.black))));
    }
}