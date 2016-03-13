// Assignment 5
// Schenck Robert
// schenckr
// Ranran He
// heranran


import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;

import tester.*;
import javalib.impworld.*;
import javalib.colors.*;
import javalib.worldimages.*;


//to represent a generic list of type T
interface IList<T> extends Iterable<T> {
    
    //is this IList a ConsList
    boolean isCons();
    
    //return the given IList as a ConsList, if it is an empty list, throw
    //an exception
    ConsList<T> asCons();
    
    //return the iterator for this IList
    Iterator<T> iterator();
    
    //add the given item to the front of this list
    IList<T> add(T t);
    
    //return the size of this IList
    int size();
    
    //does this list contain the given object
    boolean has(T t);
    
    //remove the given object from this list
    IList<T> remove(T t);
    
    //check equality for IList<T>
    boolean sameList(IList<T> list);
    
    //check equality for ConsList<T>
    boolean sameConsList(ConsList<T> other);
    
    //check equality for MtList<T>
    boolean sameMtList(MtList<T> other);
    
}

//to represent a generic non-empty list of type T
class ConsList<T> implements IList<T> {
    
    T first;
    IList<T> rest;
  
    //the constructor
    ConsList(T first, IList<T> rest) {
        this.first = first;
        this.rest = rest;
    }
    
    /*
     * TEMPLATE 
     * FIELDS: 
     * ... this.first -- T
     * ... this.rest -- IList<T>
     * METHODS: 
     * ... this.iterator() -- Iterator<T>
     * ... this.isCons() -- boolean
     * ... this.asCons() -- ConsList<T>
     * ... this.add(T) -- IList<T> 
     * ... this.size() -- int
     * ... this.has(T) -- boolean 
     * ... this.remove(T) -- IList<T>
     * ... this.sameList(IList<T>) -- boolean
     * ... this.sameConsList(ConsList<T>) -- boolean
     * ... this.sameMtList(MtList<T>) -- boolean
     * METHODS FOR FIELDS: 
     * ... this.rest.iterator() -- Iterator<T>
     * ... this.rest.isCons() -- boolean
     * ... this.rest.asCons() -- ConsList<T>
     * ... this.rest.add(T) -- IList<T> 
     * ... this.rest.size(T) -- int
     * ... this.rest.has(T) -- boolean 
     * ... this.rest.remove(T) -- IList<T>
     */

    //return the iterator for this ConsList
    public Iterator<T> iterator() {
        return new IListIterator<T>(this);
    }

    //is this IList a ConsList?
    public boolean isCons() {
        return true;
    }

    //return the given IList as a ConsList, if it is an empty list, throw
    //an exception
    public ConsList<T> asCons() {
        return this;
    }

    //add the given item to the front of this list
    public IList<T> add(T t) {
        return new ConsList<T>(t, this);
    }
    
    //return the size of this IList
    public int size() {
        return 1 + this.rest.size();
    }
    
    //does this list contain the given object
    public boolean has(T t) {
        return (t.equals(this.first)) || this.rest.has(t);
    }
    
    //remove the given object from this list
    public IList<T> remove(T t) {
        if (t == this.first) {
            return this.rest;
        }
        else {
            return new ConsList<T>(this.first, this.rest.remove(t));
        }
    }


    //check equality for IList<T>
    public boolean sameList(IList<T> list) {
        return list.sameConsList(this);
    }


    //check equality for ConsList<T>
    public boolean sameConsList(ConsList<T> other) {
        return this.first.equals(other.first) && other.rest.sameList(this.rest);
    }
 
    //check equality for MtList<T>
    public boolean sameMtList(MtList<T> other) {
        return false;
    }
    
}


//to represent an empty list of type T
class MtList<T> implements IList<T> {
    
    //the constructor
    MtList() {
        //this is empty because it represents an empty list
    }

    /*
     * TEMPLATE 
     * METHODS: 
     * ... this.iterator() -- Iterator<T>
     * ... this.isCons() -- boolean
     * ... this.asCons() -- ConsList<T>
     * ... this.add(T) -- IList<T> 
     * ... this.size() -- int
     * ... this.has(T) -- boolean 
     * ... this.remove(T) -- IList<T>
     * ... this.sameList(IList<T>) -- boolean
     * ... this.sameConsList(ConsList<T>) -- boolean
     * ... this.sameMtList(MtList<T>) -- boolean
     */
    
    
    //return the iterator for this MtList
    public Iterator<T> iterator() {
        return new IListIterator<T>(this);
    }

    //is this IList a ConsList?
    public boolean isCons() {
        return false;
    }

    //return the given IList as a ConsList, if it is an empty list, throw
    //an exception
    public ConsList<T> asCons() {
        throw new ClassCastException("MtList<T> cannot be cast ot ConsList<T>");
    }

    //add the given item to the front of this list
    public IList<T> add(T t) {
        return new ConsList<T>(t, this);
    }
    
    //return the size of this IList
    public int size() {
        return 0;
    }
    
    //does this list contain the given object
    public boolean has(T t) {
        return false;
    }
    
    //remove the given object from this list
    public IList<T> remove(T t) {
        return new MtList<T>();
    }


    //check equality for IList<T>
    public boolean sameList(IList<T> list) {
        return list.sameMtList(this);
    }


    //check equality for ConsList<T>
    public boolean sameConsList(ConsList<T> other) {
        return false;
    }


    //check equality for MtList<T>
    public boolean sameMtList(MtList<T> other) {
        return true;
    }
}

//to represent an iterator for the type IList<T>
class IListIterator<T> implements Iterator<T> {
    
    IList<T> items;
    
    //the constructor
    IListIterator(IList<T> items) {
        this.items = items;
    }

    /*
     * TEMPLATE 
     * FIELDS:
     * ... this.items -- IList<T>
     * METHODS: 
     * ... this.hasNext() -- boolean
     * ... this.next() -- T
     * ... this.remove() -- void
     */
    
    
    //does this iterator have a next item
    public boolean hasNext() {  
        return this.items.isCons();
    }
    
    //retrieve the next item, step the iterator forward
    public T next() {
        if (!this.hasNext()) {
            throw new RuntimeException("No Next Item");
        }
        else {
            ConsList<T> itemsAsCons = this.items.asCons();
            T answer = itemsAsCons.first;
            this.items = itemsAsCons.rest;
            return answer;
        }
    }
    
    //remove a given iterator (Do not allow this)
    public void remove() {
        throw new UnsupportedOperationException("Don't do this!");
    }
}



// Represents a single square of the game area
class Cell {
    
    // represents absolute height of this cell, in feet
    double height;
    // In logical coordinates, with the origin at the top-left corner of the scren
    int x;
    int y;
    // the four adjacent cells to this one
    Cell left;
    Cell top;
    Cell right;
    Cell bottom;
    // reports whether this cell is flooded or not
    boolean isFlooded;
    
    //the constructor
    Cell(double height, int x, int y) {
        this.height = height;
        this.x = x;
        this.y = y;
        this.isFlooded = false;
        this.left = null;
        this.right = null;
        this.top = null;
        this.bottom = null;
    }
    
    /*
     * TEMPLATE 
     * FIELDS: 
     * ... this.height -- double
     * ... this.x -- int
     * ... this.y -- int
     * ... this.left -- Cell
     * ... this.right -- Cell
     * ... this.top -- Cell
     * ... this.bottom -- Cell
     * ... this.isFlooded -- boolean
     * METHODS: 
     * ... this.cellImage(int) -- WorldImage
     * ... this.updateLeft(Cell) -- void
     * ... this.updateRight(Cell) -- void
     * ... this.updateTop(Cell) -- void
     * ... this.updateBottom(Cell) -- void
     * ... this.coastAHolic() -- boolean
     * ... this.flood(int) -- void
     * METHODS FOR FIELDS: 
     * ... this.left.cellImage(int) -- WorldImage
     * ... this.left.updateLeft(Cell) -- void
     * ... this.left.updateRight(Cell) -- void
     * ... this.left.updateTop(Cell) -- void
     * ... this.left.updateBottom(Cell) -- void
     * ... this.left.coastAHolic() -- boolean
     * ... this.left.flood(int) -- void
     * ... this.right.cellImage(int) -- WorldImage
     * ... this.right.updateLeft(Cell) -- void
     * ... this.right.updateRight(Cell) -- void
     * ... this.right.updateTop(Cell) -- void
     * ... this.right.updateBottom(Cell) -- void
     * ... this.right.coastAHolic() -- boolean
     * ... this.right.flood(int) -- void
     * ... this.top.cellImage(int) -- WorldImage
     * ... this.top.updateLeft(Cell) -- void
     * ... this.top.updateRight(Cell) -- void
     * ... this.top.updateTop(Cell) -- void
     * ... this.top.updateBottom(Cell) -- void
     * ... this.top.coastAHolic() -- boolean
     * ... this.top.flood(int) -- void
     * ... this.bottom.cellImage(int) -- WorldImage
     * ... this.bottom.updateLeft(Cell) -- void
     * ... this.bottom.updateRight(Cell) -- void
     * ... this.bottom.updateTop(Cell) -- void
     * ... this.bottom.updateBottom(Cell) -- void
     * ... this.bottom.coastAHolic() -- boolean
     * ... this.bottom.flood(int) -- void
     */ 
    
    
    //produce the image of this cell. There are three types
    // 1) isFlooded -> Blue, becoming darker as waterHeight
    // 2) !isFlooded but still below waterHeight -> Red, more so as waterHeight 
    // increases with respect to this.height
    // 3) !isFlooded and above waterHeight -> White to regular green as the 
    // difference between waterHeight and height shrinks
    public WorldImage cellImage(int waterHeight) {
        Color c;
        if (this.isFlooded) {
            c = new Color(0, 0, 
                    (int)Math.min(254, 254 - (waterHeight - this.height) * 5));
        }
        else if (this.height <= waterHeight) {
            c = new Color(
                (int)Math.min(150, 200 - (waterHeight - this.height) * 7),
                70 - waterHeight * 2, 0);
        }
        else {
            c = new Color(
                (int)Math.min(255, (this.height - waterHeight) * 7), 
                (int)Math.min(255, 203 + (this.height - waterHeight) * 6),
                (int)Math.min(255, (this.height - waterHeight) * 7));    
        }
               
        return new RectangleImage(new Posn(
               this.x * 10 + 5, this.y * 10 + 5), 10, 10, c);
    }

    //update this cell's left-link to the given cell
    //EFFECT: change this.left to the given Cell object "left"
    void updateLeft(Cell left) {
        this.left = left;
    }
    
    //update this cell's right-link to the given cell
    //EFFECT: change this.right to the given Cell object "right"
    void updateRight(Cell right) {
        this.right = right;
    }
    
    //update this cell's top-link to the given cell
    //EFFECT: change this.top to the given Cell object "top"
    void updateTop(Cell top) {
        this.top = top;
    }
    
    //update this cell's bottom-link to the given cell
    //EFFECT: change this.left to the given Cell object "bottom"
    void updateBottom(Cell bottom) {
        this.bottom = bottom;
    }
    
    //is this cell a coast cell (is it not flooded, yet another cell directly
    //linked to it is)?
    boolean coastAHolic() {
        return !this.isFlooded &&
               (this.left.isFlooded ||
               this.right.isFlooded ||
               this.top.isFlooded ||
               this.bottom.isFlooded);
    }

    //flood this cell if it is under the waterHeight and not already flooded
    //also flood adjacent cells with the same properties (to simulate real flooding)
    //EFFECT: changes this.isFlooded boolean flag for this cell
    void flood(int waterHeight) {
        if (this.height <= waterHeight) {
            this.isFlooded = true;
            if (!this.left.isFlooded) { 
                this.left.flood(waterHeight);
            }
            if (!this.right.isFlooded) { 
                this.right.flood(waterHeight);
            }
            if (!this.top.isFlooded) { 
                this.top.flood(waterHeight);
            }
            if (!this.bottom.isFlooded) { 
                this.bottom.flood(waterHeight);
            }
        }
    }
  
}

//Represents a ocean variant of a square of the game area
class OceanCell extends Cell {

    //the constructor
    OceanCell(double height, int x, int y) {
        super(height, x, y);
        this.isFlooded = true;
    }
    
    /*
     * TEMPLATE 
     * INHERITED FIELDS: 
     * ... this.height -- double
     * ... this.x -- int
     * ... this.y -- int
     * ... this.left -- Cell
     * ... this.right -- Cell
     * ... this.top -- Cell
     * ... this.bottom -- Cell
     * ... this.isFlooded -- boolean
     * INHERITED METHODS: 
     * ... this.cellImage(int) -- WorldImage
     * ... this.updateLeft(Cell) -- void
     * ... this.updateRight(Cell) -- void
     * ... this.updateTop(Cell) -- void
     * ... this.updateBottom(Cell) -- void
     * ... this.coastAHolic() -- boolean
     * ... this.flood(int) -- void
     * OVERWRITTEN METHODS:
     * ... this.cellImage(int) -- WorldImage
     * METHODS FOR FIELDS: 
     * ... this.left.cellImage(int) -- WorldImage
     * ... this.left.updateLeft(Cell) -- void
     * ... this.left.updateRight(Cell) -- void
     * ... this.left.updateTop(Cell) -- void
     * ... this.left.updateBottom(Cell) -- void
     * ... this.left.coastAHolic() -- boolean
     * ... this.left.flood(int) -- void
     * ... this.right.cellImage(int) -- WorldImage
     * ... this.right.updateLeft(Cell) -- void
     * ... this.right.updateRight(Cell) -- void
     * ... this.right.updateTop(Cell) -- void
     * ... this.right.updateBottom(Cell) -- void
     * ... this.right.coastAHolic() -- boolean
     * ... this.right.flood(int) -- void
     * ... this.top.cellImage(int) -- WorldImage
     * ... this.top.updateLeft(Cell) -- void
     * ... this.top.updateRight(Cell) -- void
     * ... this.top.updateTop(Cell) -- void
     * ... this.top.updateBottom(Cell) -- void
     * ... this.top.coastAHolic() -- boolean
     * ... this.top.flood(int) -- void
     * ... this.bottom.cellImage(int) -- WorldImage
     * ... this.bottom.updateLeft(Cell) -- void
     * ... this.bottom.updateRight(Cell) -- void
     * ... this.bottom.updateTop(Cell) -- void
     * ... this.bottom.updateBottom(Cell) -- void
     * ... this.bottom.coastAHolic() -- boolean
     * ... this.bottom.flood(int) -- void
     */ 
        
    //produce the image of this cell, which is always blue.
    public WorldImage cellImage(int waterHeight) {
        return new RectangleImage(new Posn(this.x * 10 + 5, this.y * 10 + 5), 10, 10,
                new Color(0, 0, 255));
    }
    

}

//to represent the player of the ForbiddenIslandWorld
class Player {
    
    int x;
    int y;
    int parts;
    
    //A player is defined by his relation to a specific cell.
    Cell cur;
    
    //the constructor
    Player(Cell cur) {
        this.cur = cur;
        this.x = this.cur.x;
        this.y = this.cur.y;
        this.parts = 0;
    }
    
    /*
     * TEMPLATE 
     * FIELDS: 
     * ... this.x -- int
     * ... this.y -- int
     * ... this.parts -- int
     * ... this.cur -- Cell
     * METHODS: 
     * ... this.updateCell(Cell) -- void
     * ... this.partCollected() -- void
     * ... this.playerImage() -- WorldImage
     * METHODS FOR FIELDS: 
     * ... this.cur.cellImage(int) -- WorldImage
     * ... this.cur.updateLeft(Cell) -- void
     * ... this.cur.updateRight(Cell) -- void
     * ... this.cur.updateTop(Cell) -- void
     * ... this.cur.updateBottom(Cell) -- void
     * ... this.cur.coastAHolic() -- boolean
     * ... this.cur.flood(int) -- void
     */ 
    
    
    //update the cell connected with the player
    //EFFECT: changes this.cur, this.x and this.y
    void updateCell(Cell newCell) {
        this.cur = newCell;
        this.x = this.cur.x;
        this.y = this.cur.y;
    }
    
    //update the amount of parts the player has by 1, signifying that he/she has 
    //collected a part
    //EFFECT: change this.parts by increasing 1.
    void partCollected() {
        this.parts += 1;
    }
    
    //produce the image of this player at his/her specified position.
    public WorldImage playerImage() {
        return new FromFileImage(new Posn(this.x * 10 + 5,
               this.y * 10 + 5), "guy.png");
    }
    
}

//to represent a helicopter part, a target for the player
class Target {
    
    int x;
    int y;
    
    //A player is defined by his relation to a specific cell.
    Cell cur;
    
    Target(Cell cur) {
        this.cur = cur;
        this.x = this.cur.x;
        this.y = this.cur.y;
    }
    
    /*
     * TEMPLATE 
     * FIELDS: 
     * ... this.x -- int
     * ... this.y -- int
     * ... this.cur -- Cell
     * METHODS: 
     * ... this.Image() -- WorldImage
     * METHODS FOR FIELDS: 
     * ... this.cur.cellImage(int) -- WorldImage
     * ... this.cur.updateLeft(Cell) -- void
     * ... this.cur.updateRight(Cell) -- void
     * ... this.cur.updateTop(Cell) -- void
     * ... this.cur.updateBottom(Cell) -- void
     * ... this.cur.coastAHolic() -- boolean
     * ... this.cur.flood(int) -- void
     */ 
    
      
    public WorldImage image() {
        return new FromFileImage(new Posn(this.x * 10 + 5,
                this.y * 10 + 5), "cog.png");
    }
}

class HelicopterTarget extends Target {
    
    HelicopterTarget(Cell cur) {
        super(cur);
    }
    
    /*
     * TEMPLATE 
     * INHERITED FIELDS: 
     * ... this.x -- int
     * ... this.y -- int
     * ... this.cur -- Cell
     * INHERITED METHODS: 
     * ... this.Image() -- WorldImage
     * OVERWRITTEN METHODS: 
     * ... this.Image() -- WorldImage
     * METHODS FOR FIELDS: 
     * ... this.cur.cellImage(int) -- WorldImage
     * ... this.cur.updateLeft(Cell) -- void
     * ... this.cur.updateRight(Cell) -- void
     * ... this.cur.updateTop(Cell) -- void
     * ... this.cur.updateBottom(Cell) -- void
     * ... this.cur.coastAHolic() -- boolean
     * ... this.cur.flood(int) -- void
     */ 
    
    
    public WorldImage image() {
        return new FromFileImage(new Posn(this.x * 10 + 5, this.y * 10 + 5),
                "helicopter.png");
    }
}

 
class ForbiddenIslandWorld extends World {
  
    // Defines an int constant
    static final int ISLAND_SIZE = 64;
    
    // maximum height of the cells
    static final double GAME_HEIGHT = 31;

    // All the cells of the game, including the ocean
    IList<Cell> board;
  
    // the current height of the ocean
    int waterHeight;
    
    int count;
    
    Random rand;
    
    Player player;
    
    int numParts;
    
    IList<Target> parts;
    
    HelicopterTarget copter;
    
    int score;
  
    //the constructor
    ForbiddenIslandWorld() {
        this.waterHeight = 0;
        this.count = 0;
        this.rand = new Random();
        this.board = this.createBoard(this.createCells(this.mountainConstruct(
                ISLAND_SIZE)));
        this.player = this.playerConstruct();
        this.numParts = 3;
        this.parts = this.createParts(this.numParts);
        this.copter = this.createCopter();
        score = 0;
    }
    
    
    /*
     * TEMPLATE 
     * CONSTANTS:
     * ... this.ISLAND_SIZE -- int
     * ... this.GAME_HEIGHT -- double
     * FIELDS: 
     * ... this.board -- IList<Cell>
     * ... this.waterHeight -- int
     * ... this.count -- int
     * ... this.rand -- Random
     * ... this.player -- Player
     * ... this.numParts -- int
     * ... this.parts -- IList<Target>
     * ... this.copter -- HelicopterTarget
     * ... this.score -- int
     * METHODS: 
     * ... this.playerConstruct() -- Player
     * ... this.createParts(int) -- IList<Target>
     * ... this.createCopter() -- HelicopterTarget
     * ... this.randomCell() -- Cell
     * ... this.land() -- IList<Cell>
     * ... this.createBoard(ArrayList<ArrayList<Cell>>) -- IList<Cell>
     * ... this.createCells(ArrayList<ArrayList<Double>>) -- ArrayList<ArrayList<Cell>>
     * ... this.mountainConstruct(int) -- ArrayList<ArrayList<Double>>
     * ... this.randomConstruct(int) -- ArrayList<ArrayList<Double>>
     * ... this.terrainConstruct(int) -- ArrayList<ArrayList<Double>>
     * ... this.terrainHelp(ArrayList<ArrayList<Double>>, int, int, int, int) -- void
     * ... this.onKeyEvent(String) -- void
     * ... this.collection() -- void
     * ... this.onTick() -- void
     * ... this.coast() -- IList<Cell>
     * ... this.floodingRooni() -- void
     * ... this.worldEnds() -- WorldEnd
     * ... this.linkCells(ArrayList<ArrayList<Cell>>) -- void
     * ... this.makeImage() -- WorldImage
     * ... this.heliImage() -- WorldImage
     * ... this.boardImage() -- WorldImage
     * ... this.score() -- WorldImage
     * METHODS FOR FIELDS: 
     * ... this.board.iterator() -- Iterator<Cell>
     * ... this.board.isCons() -- boolean
     * ... this.board.asCons() -- ConsList<Cell>
     * ... this.board.add(Cell) -- IList<Cell> 
     * ... this.board.size(Cell) -- int
     * ... this.board.has(Cell) -- boolean 
     * ... this.board.remove(Cell) -- IList<Cell>
     * ... this.parts.iterator() -- Iterator<Target>
     * ... this.parts.isCons() -- boolean
     * ... this.parts.asCons() -- ConsList<Target>
     * ... this.parts.add(Target) -- IList<Target> 
     * ... this.parts.size(Target) -- int
     * ... this.parts.has(Target) -- boolean 
     * ... this.parts.remove(Target) -- IList<Target>
     * ... this.player.updateCell(Cell) -- void
     * ... this.player.partCollected() -- void
     * ... this.player.playerImage() -- WorldImage
     * ... this.copter.Image() -- WorldImage
     */ 
    
    
    //construct a new person for the game
    Player playerConstruct() {
        return new Player(this.randomCell()); 
    }
    
    //construct the helicopter parts for the game 
    IList<Target> createParts(int num) {
        IList<Target> iparts = new MtList<Target>();
        Cell c = new Cell(31.0, 31, 31);
        for (int index = num; index > 0; index -= 1) {
            c = this.randomCell();
            iparts = iparts.add(new Target(c));
        }
        return iparts;
    }
    
    //create the helicopter for the game
    HelicopterTarget createCopter() {
        IList<Cell> land = this.land();
        Iterator<Cell> landiter = land.iterator();
        HelicopterTarget heli;
        Cell prev = landiter.next();
        Cell cur = landiter.next();
        while (landiter.hasNext()) {
            if (cur.height >= prev.height) {
                prev = cur;
            }
            cur = landiter.next();
        }
        heli = new HelicopterTarget(prev);
        return heli;    
    }
    
    //pick a random cell from the land cells (not including the coast cells)
    public Cell randomCell() {
        IList<Cell> land = this.land();
        int index = this.rand.nextInt(land.size());
        Iterator<Cell> landiter = land.iterator();
        Cell c = landiter.next();
        index -= 1;
        while (index >= 0) {
            Cell cur = landiter.next();
            if (index == 0) {
                if (cur.coastAHolic()) {
                    landiter = land.iterator();
                    index = this.rand.nextInt(land.size());
                }
                else {
                    c = cur;                    
                }
            }
            index -= 1;
        }   
        
        return c;
    }
    
    //create a list of all the land cells (not including the coast cells)
    IList<Cell> land() {
        Iterator<Cell> iter = this.board.iterator();
        IList<Cell> dest = new MtList<Cell>();
        while (iter.hasNext()) {
            Cell next = iter.next();
            if (!next.isFlooded &&
                    (!next.left.isFlooded ||
                     !next.right.isFlooded ||
                     !next.bottom.isFlooded ||
                     !next.top.isFlooded)) {
                dest = dest.add(next);
            }
        }
        return dest; 
    }
 
    //center is at (31, 31)
    //turn the ArrayList<ArrayList<Cell>> into an IList
    IList<Cell> createBoard(ArrayList<ArrayList<Cell>> cells) {
        IList<Cell> dest = new MtList<Cell>();
        for (int index = 0; index < cells.size(); index += 1) {
            for (int index2 = 0; index2 < cells.get(index).size(); index2 += 1) {
                dest = dest.add(cells.get(index).get(index2));
            }   
        }
        return dest;
    }
    
    //create a ArrayList<ArrayList<Cell>> from an ArrayList<ArrayList<Double>>
    ArrayList<ArrayList<Cell>> createCells(ArrayList<ArrayList<Double>> height) {
        ArrayList<ArrayList<Cell>> dest = new ArrayList<ArrayList<Cell>>();
        for (int index = 0; index < height.size(); index += 1) {
            ArrayList<Cell> row = new ArrayList<Cell>();
            dest.add(row);        
            for (int index2 = 0; index2 < height.get(index).size(); index2 += 1) {
                if (height.get(index).get(index2) > 0) {
                    dest.get(index).add(
                        new Cell(height.get(index).get(index2).intValue(), 
                            index2, index));
                }
                else {
                    dest.get(index).add(new OceanCell(0, 
                            index2, index));  
                }  
            }                   
        }
        this.linkCells(dest);
        return dest;
    }
    
    // Index - Y coordinate, outer ArrayList
    // Index2 - X coordinate, inner ArrayList
    
    //HEIGHT FUNCTONS BEGIN ----
    
    //create an ArrayList<ArrayList<Double>> for a mountain structure of size size
    ArrayList<ArrayList<Double>> mountainConstruct(int size) {
        ArrayList<ArrayList<Double>> dest = new ArrayList<ArrayList<Double>>();
        Posn center = new Posn(size / 2 - 1, size / 2 - 1);
        for (int index = 0; index < size; index += 1) {
            ArrayList<Double> row = new ArrayList<Double>();
            dest.add(row);
            for (int index2 = 0; index2 < size; index2 += 1) {
                dest.get(index).add(GAME_HEIGHT - (Math.abs(center.x - index2) 
                        + Math.abs(center.y - index)));
            }
        }    
        return dest;
    }
    
    //create an ArrayList<ArrayList<Double>> for a random structure of size size  
    ArrayList<ArrayList<Double>> randomConstruct(int size) {
        ArrayList<ArrayList<Double>> dest = new ArrayList<ArrayList<Double>>();
        double holder;
        Posn center = new Posn(ISLAND_SIZE / 2 - 1, ISLAND_SIZE / 2 - 1);
        for (int index = 0; index < size; index += 1) {
            ArrayList<Double> row = new ArrayList<Double>();
            dest.add(row);
            for (int index2 = 0; index2 < size; index2 += 1) {
                holder = GAME_HEIGHT - (Math.abs(center.x - index2) 
                        + Math.abs(center.y - index));
                if (holder <= 0.0) {
                    dest.get(index).add(0.0);   
                }
                else {
                    dest.get(index).add((double)rand.nextInt(30) + 1);                    
                }
            }
        }    
        return dest;
    }
    
    //create an ArrayList<ArrayList<Double>> for a terrain structure of size size
    ArrayList<ArrayList<Double>> terrainConstruct(int size) {
        ArrayList<ArrayList<Double>> dest = new ArrayList<ArrayList<Double>>();
        for (int index = 0; index <= size; index += 1) {
            ArrayList<Double> row = new ArrayList<Double>();
            dest.add(row);
            for (int index2 = 0; index2 <= size; index2 += 1) {
                dest.get(index).add(0.0);
            }
        }

        // set the first layer of heights
        int mid = size / 2;
        dest.get(0).set(mid, 1.0);
        dest.get(mid).set(size - 1, 1.0);
        dest.get(mid).set(0, 1.0);
        dest.get(size - 1).set(mid, 1.0);
        dest.get(size / 2).set(size / 2, GAME_HEIGHT);
        //call terrain help
        this.terrainHelp(dest, 0, 0, size - 1, size - 1);
       
        return dest;
    }
    
    //assign values to the given random terrain heights
    //EFFECT: changes input
    void terrainHelp(ArrayList<ArrayList<Double>> input, int lox, int loy,
            int hix, int hiy) {

        //midpoints to recur on
        int midx = (int)Math.ceil((lox + hix) / 2.0);
        int midy = (int)Math.ceil((loy + hiy) / 2.0);
        int area = (hiy - loy) * (hix - lox);
        double scale = Math.sqrt(area / Math.pow(ISLAND_SIZE, 1.96));
        
        //base case (do not recur if the size of area is too small)
        if (area < 2) {
            return;
        }
        //assign values and recur
        else {
            
            //heights of corners and midpoints
            double tl = input.get(loy).get(lox);
            double bl = input.get(hiy).get(lox);
            double tr = input.get(loy).get(hix);
            double br = input.get(hiy).get(hix);
            double t = (this.rand.nextInt((int)(GAME_HEIGHT / 1.25)) - GAME_HEIGHT / 4) * scale +
                    (tl + tr) / 2;
            double b = (this.rand.nextInt((int)(GAME_HEIGHT / 1.25)) - GAME_HEIGHT / 4) * scale +
                    (bl + br) / 2;
            double l = (this.rand.nextInt((int)(GAME_HEIGHT / 1.25)) - GAME_HEIGHT / 4) * scale +
                    (tl + bl) / 2;
            double r = (this.rand.nextInt((int)(GAME_HEIGHT / 1.25)) - GAME_HEIGHT / 4) * scale +
                    (tr + br) / 2;
            double m = (this.rand.nextInt((int)(GAME_HEIGHT / 1.25)) - GAME_HEIGHT / 5) * scale +
                    (tl + tr + bl + br) / 4;
            
            //middle
            input.get(midy).set(midx, m); 
            //top
            input.get(loy).set(midx, t);
            //left
            input.get(midy).set(lox, l);

            if (input.get(midy).get(hix) == 0) {
                input.get(midy).set(hix, r);
            }
            if (input.get(hiy).get(midx) == 0) {
                input.get(hiy).set(midx, b);
            }
        
            /*
            tl --- t ---- tr
            |   1  |   2  |
            l ---- m ---- r
            |   4  |   3  |
           bl ---- b ---- br
            */
            
            //3rd quad.
            this.terrainHelp(input, midx, midy, hix, hiy);
            //first quadrant recursion
            this.terrainHelp(input, lox, loy, midx, midy);
            //2nd quad.
            this.terrainHelp(input, midx, loy, hix, midy);
            //4th quad.
            this.terrainHelp(input, lox, midy, midx, hiy);
        }
    } 
    // HEIGHT FUNCTIONS END---
    

    //handle key inputs, the arrow keys move the player
    //EFFECT: change this.player.cur or reset the world by resetting
    //its various properties
    //"m", "r" and "t" change the type of map during game and resets
    public void onKeyEvent(String ke) {
        if (ke.equals("m")) {
            this.count = 0;
            this.waterHeight = 0;
            this.score = 0;
            this.board = this.createBoard(this.createCells(
                    this.mountainConstruct(ISLAND_SIZE)));
            this.player = this.playerConstruct();
            this.parts = this.createParts(this.numParts);
            this.copter = this.createCopter();
        } 
        if (ke.equals("r")) {
            this.count = 0;
            this.waterHeight = 0;
            this.score = 0;
            this.board = this.createBoard(this.createCells(
                    this.randomConstruct(ISLAND_SIZE)));
            this.player = this.playerConstruct();
            this.parts = this.createParts(this.numParts);
            this.copter = this.createCopter();
        } 
        if (ke.equals("t")) {
            this.count = 0;
            this.waterHeight = 0;
            this.score = 0;
            this.board = this.createBoard(this.createCells(
                    this.terrainConstruct(ISLAND_SIZE)));
            this.player = this.playerConstruct();
            this.parts = this.createParts(this.numParts);
            this.copter = this.createCopter();
        } 
        if (ke.equals("left") && !this.player.cur.left.isFlooded) {
            this.player.updateCell(this.player.cur.left);
            this.collection();
            this.score += 1;
        } 
        if (ke.equals("up") && !this.player.cur.top.isFlooded) {
            this.player.updateCell(this.player.cur.top);
            this.collection();
            this.score += 1;
        } 
        if (ke.equals("down") && !this.player.cur.bottom.isFlooded) {
            this.player.updateCell(this.player.cur.bottom);
            this.collection();
            this.score += 1;
        } 
        if (ke.equals("right") && !this.player.cur.right.isFlooded) {
            this.player.updateCell(this.player.cur.right);
            this.collection();
            this.score += 1;
        } 
        else {
            return;
        }
    }
    
    //Call this when a person moves
    //EFFECT: change player.parts and this.parts
    
    public void collection() {
        Iterator<Target> iter = this.parts.iterator();
        while (iter.hasNext()) {
            Target t = iter.next();
            if (t.cur == this.player.cur) {
                this.player.partCollected();
                this.parts = this.parts.remove(t);
            }
        }
    }
    
    // produces a new world on tick, floods every 10 ticks
    //EFFECT: change this.count, this.waterHeight and flood the cells
    public void onTick() {
        this.count += 1;
        if (this.count == 10) {
            this.waterHeight += 1;
            this.count = 0;
            
        } 
        this.floodingRooni();
    }
    
    // create a list of coast cells (cells not flooded but touching water)
    public IList<Cell> coast() {
        IList<Cell> s8coasting = new MtList<Cell>();
        Iterator<Cell> iter = this.board.iterator();

        while (iter.hasNext()) {
            Cell nextaroo = iter.next();
            if (nextaroo.coastAHolic()) {
                s8coasting = s8coasting.add(nextaroo);
            }
        }
        
        return s8coasting;    
    }
    
    // flood the coast cells
    //EFFECT: edit the isFlooded flags of certain coast cells and their 
    // adjacent cells
    public void floodingRooni() {
        Iterator<Cell> iter = this.coast().iterator();
        
        while (iter.hasNext()) {
            Cell nextaroo = iter.next();
            nextaroo.flood(this.waterHeight);
        }
        
    }
    
    // ends the game if the player gets to the helicopter with parts or
    // if he loses by his cell getting flooded
    public WorldEnd worldEnds() {
        if (this.player.parts == this.numParts && 
                this.player.cur == this.copter.cur) {
            return new WorldEnd(true, new OverlayImages(this.makeImage(),
                    new TextImage(new Posn(320, 320), "You Won! How'd you do it?!", 
                            23, 4, new Color(102, 0, 204))));
        }
        if (this.player.cur.isFlooded) { 
            return new WorldEnd(true, new OverlayImages(this.makeImage(),
                    new TextImage(new Posn(320, 320), "You Drowned! Game Over.", 
                            23, 4, new Color(102, 0, 204))));
        } 
        else {
            return new WorldEnd(false, this.makeImage());
        }
    }
    
    // link all the cells in an ArrayList<ArrayList<Cells>> cells
    //EFFECT: change left, right, top and bottom cells of each cell in cells
    public void linkCells(ArrayList<ArrayList<Cell>> cells) {
        for (int index = 0; index < cells.size(); index += 1) {           
            for (int index2 = 0; index2 < cells.size(); index2 += 1) {                
                Cell curCell = cells.get(index).get(index2);
                
                if (index2 < cells.size() - 1) {
                    curCell.updateRight(cells.get(index).get(index2 + 1));
                }
                else {
                    curCell.updateRight(curCell);
                }
                if (index2 > 0) {
                    curCell.updateLeft(cells.get(index).get(index2 - 1));
                }
                else {
                    curCell.updateLeft(curCell);
                }
                if (index > 0) {
                    curCell.updateTop(cells.get(index - 1).get(index2));
                }
                else {
                    curCell.updateTop(curCell);
                }
                if (index < cells.size() - 1) {
                    curCell.updateBottom(cells.get(index + 1).get(index2));
                }
                else {
                    curCell.updateBottom(curCell);
                }
            }           
        }
    }

    //produce an image of the world
    public WorldImage makeImage() {
        return this.boardImage().overlayImages(this.heliImage()).overlayImages(
                this.player.playerImage());
    }
    
    // produce an image of the helicopter and the helicopter parts
    public WorldImage heliImage() {
        WorldImage i = new RectangleImage(new Posn(0, 0), 0, 0, new White());
        Iterator<Target> iter = this.parts.iterator(); 
        while (iter.hasNext()) {
            i = i.overlayImages(iter.next().image());
        }
        i = i.overlayImages(this.copter.image());
        i = i.overlayImages(this.score());
        return i;
    }
    
    //create an image of the board
    public WorldImage boardImage() {
        WorldImage boardImage = new RectangleImage(
                new Posn(0, 0), 0, 0, new White());
        Iterator<Cell> iter = this.board.iterator();  
        while (iter.hasNext()) {
            Cell nextaroo = iter.next();
            boardImage = boardImage.overlayImages(nextaroo.cellImage(waterHeight));
        }
        return boardImage; 
    }
    
    // produce an image of the score of the game (based on the steps taken)
    public WorldImage score() {
        return new TextImage(new Posn(100, 50), "Score: ".concat(
                Integer.toString(this.score)), 
                23, 4, new Color(255, 255, 255));
    }
}


//examples for game AppleGame
class ExamplesForbiddenIslandWorld {

    //examples of ForbiddenIslandWorld, World
    ForbiddenIslandWorld w1;
    IList<Cell> list1;
    ArrayList<ArrayList<Cell>> test;
    ArrayList<ArrayList<Double>> test1;
    ArrayList<ArrayList<Double>> test2;
    Cell cell2121;
    Cell cell00;
    Cell cell1212;  
    Cell cell3101;
    
    Cell testCell;
    Cell testCellLeft;
    Cell testCellRight;
    Cell testCellTop;
    Cell testCellBottom;
    OceanCell testOcean = new OceanCell(0.0, 1, 1);
    Player p1;
    Target t1;
    Target t2;
    Target t3;
    IList<Target> it;
    HelicopterTarget h1;
    HelicopterTarget h2;
    
    IList<String> testerIList1 = new ConsList<String>("Hello", 
            new ConsList<String>("My name is", new MtList<String>()));
    IList<String> testerIList2 = new MtList<String>();
    String hello = "Hello";
    String myname = "My name is";
    Iterator<String> iter1 = testerIList1.iterator();
       
    ArrayList<Double> inner1;
    ArrayList<Double> inner2;
    ArrayList<ArrayList<Double>> test3;

    void setUpTests() {
        w1 = new ForbiddenIslandWorld();
        test = w1.createCells(w1.mountainConstruct(64));
        test1 = w1.mountainConstruct(64);
        test2 = w1.mountainConstruct(2);
        list1 = new MtList<Cell>();
        cell2121 = test.get(21).get(21);
        cell00 = test.get(0).get(0);
        cell1212 = test.get(12).get(12);
        cell3101 = test.get(31).get(1);
        testerIList1 = new ConsList<String>("Hello", 
            new ConsList<String>("My name is", new MtList<String>()));
        testerIList2 = new MtList<String>();
        iter1 = testerIList1.iterator();
        testCell = new Cell(2, 1, 1);
        testCellLeft = new Cell(1, 0, 1);
        testCellRight = new Cell(1, 2, 1);
        testCellTop = new Cell(1, 1, 0);
        testCellBottom = new Cell(1, 1, 2);
        testCell.updateBottom(testCellBottom);
        testCell.updateTop(testCellTop);
        testCell.updateRight(testCellRight);
        testCell.updateLeft(testCellLeft);
        testCellLeft.updateBottom(testCellLeft);
        testCellLeft.updateTop(testCellLeft);
        testCellLeft.updateRight(testCell);
        testCellLeft.updateLeft(testCellLeft);
        testCellRight.updateBottom(testCellRight);
        testCellRight.updateTop(testCellRight);
        testCellRight.updateRight(testCellRight);
        testCellRight.updateLeft(testCell);
        testCellTop.updateBottom(testCell);
        testCellTop.updateTop(testCellTop);
        testCellTop.updateRight(testCellTop);
        testCellTop.updateLeft(testCellTop);
        testCellBottom.updateBottom(testCellBottom);
        testCellBottom.updateTop(testCell);
        testCellBottom.updateRight(testCellBottom);
        testCellBottom.updateLeft(testCellBottom);
        p1 = new Player(testCell);
        t1 = new Target(testCell);
        t2 = new Target(testCell);
        t3 = new Target(testCell);
        it = new ConsList<Target>(t1, new ConsList<Target>(t2,
            new ConsList<Target>(t3, new MtList<Target>())));
        h1 = new HelicopterTarget(testCell);
        inner1 = new ArrayList<Double>() {
            { 
                add(0.0);
                add(0.0);
            }   
        };
        inner2 = new ArrayList<Double>() {
            { 
                add(0.0);
                add(0.0);
            }
        };
        test3 = new ArrayList<ArrayList<Double>>() {   
            {
                add(inner1);
                add(inner2); 
            }   
        };        
        h2 = w1.createCopter();
    }
    

    //Test for the iterator() method for IList<T>
    void testIterator(Tester t) {
        this.setUpTests();
        t.checkExpect(iter1.next().equals(hello), true);
    }
    
    //Test for the isCons() method for IList<T>
    void testisCons(Tester t) {
        this.setUpTests();
        t.checkExpect(testerIList1.isCons(), true);
        t.checkExpect(testerIList2.isCons(), false);
    }
    
    //Test for the asCons() method for IList<T>
    void testasCons(Tester t) {
        this.setUpTests();
        t.checkExpect(testerIList1.asCons().sameList(testerIList1), true);
        t.checkException(new ClassCastException(
                "MtList<T> cannot be cast ot ConsList<T>"), testerIList2, "asCons");
    }
    
    //Test for the add(T) method for IList<T>
    void testAdd(Tester t) {
        this.setUpTests();
        testerIList2 = testerIList2.add(myname);
        t.checkExpect(testerIList2.sameList(new ConsList<String>("My name is", 
                new MtList<String>())), true);
        testerIList2 = testerIList2.add(hello);
        t.checkExpect(testerIList2.sameList(testerIList1), true);
    }
    
    //Test for the size() method for IList<T>
    void testSize(Tester t) {
        this.setUpTests();
        t.checkExpect(testerIList1.size() == 2, true);
        t.checkExpect(testerIList2.size() == 0, true);
    }
    
    //Test for the has(T) method for IList<T>
    void testHas(Tester t) {
        this.setUpTests();
        t.checkExpect(testerIList1.has(hello), true);
        t.checkExpect(testerIList2.has(hello), false);
    }
    
    //Test for the remove(T) method for IList<T>
    void testRemove(Tester t) {
        this.setUpTests();
        testerIList1 = testerIList1.remove(hello);
        t.checkExpect(testerIList1.sameList(new ConsList<String>("My name is" , 
                new MtList<String>())), true);
    }
    
    //test the methods in IListIterator<T>
    void testIterMethods(Tester t) {
        this.setUpTests();
        t.checkExpect(iter1.hasNext(), true);
        t.checkExpect(iter1.next().equals(hello), true);
        t.checkException(new UnsupportedOperationException("Don't do this!"),
                iter1, "remove");
    }
     
    //test the cellImage methods in Cell and OceanCell
    void testCellImage(Tester t) {
        this.setUpTests();
        t.checkExpect(cell00.cellImage(0), (new RectangleImage(
                new Posn(5, 5), 10, 10,
                new Color(0, 0, 255))));
        t.checkExpect(testCell.cellImage(0), (new RectangleImage(new Posn(
                this.testCell.x * 10 + 5, this.testCell.y * 10 + 5), 10, 10,
                new Color(14, 215, 14))));
        t.checkExpect(testCell.cellImage(2), (new RectangleImage(new Posn(
                this.testCell.x * 10 + 5, this.testCell.y * 10 + 5), 10, 10,
                new Color(150, 66, 0))));
        testCell.flood(2);                   
        t.checkExpect(testCell.cellImage(2), (new RectangleImage(new Posn(
                this.testCell.x * 10 + 5, this.testCell.y * 10 + 5), 10, 10,
                new Color(0, 0, (int)(254 - (2 - this.testCell.height) * 5)))));
    }
    
    //test the update methods in Cell
    void testUpdateSides(Tester t) {
        this.setUpTests();
        testCell.updateLeft(testCellTop);
        t.checkExpect(testCell.left == testCellTop, true);
        testCell.updateTop(testCellRight);
        t.checkExpect(testCell.top == testCellRight, true);
        testCell.updateBottom(testCellLeft);
        t.checkExpect(testCell.bottom == testCellLeft, true);
        testCell.updateRight(testCellTop);
        t.checkExpect(testCell.right == testCellTop, true);
    }
    
    //test the coastAHolic() methods in Cell
    void testCoastAHolic(Tester t) {
        this.setUpTests();
        t.checkExpect(testCell.coastAHolic(), false);
        t.checkExpect(cell3101.coastAHolic(), true);
    }
    
    //test the flood(int) methods in Cell
    void testFlood(Tester t) {
        this.setUpTests();
        t.checkExpect(testCell.isFlooded, false);
        testCell.flood(3);
        t.checkExpect(testCell.isFlooded, true);
    }
    
    //test the updateCell(Cell) methods in Player
    void testUpdateCell(Tester t) {
        this.setUpTests();
        t.checkExpect(p1.cur == testCell, true);
        p1.updateCell(testCellTop);
        t.checkExpect(p1.cur == testCellTop, true);
    }
    
    //test the partCollected() methods in Player
    void testPartsCollected(Tester t) {
        this.setUpTests();
        t.checkExpect(p1.parts == 0, true);
        p1.partCollected();
        t.checkExpect(p1.parts == 1, true);
    }
    
    //test the playerImage() methods in Player
    void testPlayerImage(Tester t) {
        this.setUpTests();
        t.checkExpect(p1.playerImage(), 
            new FromFileImage(new Posn(this.p1.x * 10 + 5,
                this.p1.y * 10 + 5), "guy.png"));
    }
    
    //test the image() methods in Helicopter
    void testTargetImage(Tester t) {
        this.setUpTests();
        t.checkExpect(t1.image(), 
            new FromFileImage(new Posn(this.t1.x * 10 + 5,
                this.t1.y * 10 + 5), "cog.png"));
    }
    
    //test the image() methods in HepicopterImage
    void testImage(Tester t) {
        this.setUpTests();
        t.checkExpect(h1.image(), 
            new FromFileImage(new Posn(this.h1.x * 10 + 5, this.h1.y * 10 + 5),
                "helicopter.png"));
    }
    
    //test the onTick() methods in ForbiddenIslandWorld
    void testOnTick(Tester t) {
        this.setUpTests();
        t.checkExpect(w1.count == 0, true);
        w1.onTick();
        t.checkExpect(w1.count == 1, true);
        t.checkExpect(w1.waterHeight == 0);
        w1.onTick();
        w1.onTick();
        w1.onTick();
        w1.onTick();
        w1.onTick();
        w1.onTick();
        w1.onTick();
        w1.onTick();
        w1.onTick();
        w1.onTick();
        w1.onTick();
        w1.onTick();
        t.checkExpect(w1.waterHeight == 1);
    }
    
    //test the score() methods in ForbiddenIslandWorld
    void testScore(Tester t) {
        this.setUpTests();
        t.checkExpect(w1.score(), 
            new TextImage(new Posn(100, 50), "Score: ".concat(
                Integer.toString(this.w1.score)), 
                   23, 4, new Color(255, 255, 255)));
    }
    
    //test the heliImage() methods in ForbiddenIslandWorld
    void testHeliImage(Tester t) {
        this.setUpTests();
        Iterator<Target> iter = this.w1.parts.iterator();
        t.checkExpect(w1.heliImage(), new RectangleImage(
            new Posn(0, 0), 0, 0, new White()).overlayImages(iter.next().image()).overlayImages(
                iter.next().image()).overlayImages(iter.next().image()).overlayImages(
                    w1.copter.image()).overlayImages(w1.score()));
            
    }
    
    //test the mountainConstructor() methods in ForbiddenIslandWorld
    void testMountainConstructor(Tester t) {
        this.setUpTests();
        test1 = w1.mountainConstruct(64);
        t.checkExpect(test1.get(31).get(31) == 31, true);
        t.checkExpect(test1.get(21).get(21) == 11, true);
    }
    
    //test the terrainConstructor() methods in ForbiddenIslandWorld
    void testTerrainConstructor(Tester t) {
        this.setUpTests();
        test1 = w1.terrainConstruct(64);
        t.checkExpect(test1.get(0).get(0) == 0, true);
        t.checkExpect(test1.get(21).get(21) >= 1, true);
    }
    
    //test the terrainHelper() methods in ForbiddenIslandWorld
    void testTerrainHelper(Tester t) {
        this.setUpTests();
        test1 = w1.terrainConstruct(64);
        t.checkExpect(test1.get(0).get(0) == 0, true);
        t.checkExpect(test1.get(63).get(63) == 0, true);
        w1.terrainHelp(test1, 0, 0, 63, 63);
        t.checkExpect(test1.get(0).get(0) == 0, true);
        t.checkExpect(test1.get(63).get(63) == 0, true);
    }
    
    //test the linkCells() methods in ForbiddenIslandWorld
    void testLinkCells(Tester t) {
        this.setUpTests();
        t.checkExpect(h1.image(), 
            new FromFileImage(new Posn(this.h1.x * 10 + 5, this.h1.y * 10 + 5),
                "helicopter.png"));
    }
    
    //test the collection() methods in ForbiddenIslandWorld
    void testCollection(Tester t) {
        this.setUpTests();
        w1.player = p1;
        w1.parts = new ConsList<Target>(t1, new MtList<Target>());
        t.checkExpect(p1.parts == 0, true);
        w1.collection();
        t.checkExpect(p1.parts == 1, true);
    }
    
    //test the playerConstruct() methods in ForbiddenIslandWorld
    void testPlayerConstruct(Tester t) {
        this.setUpTests();
        w1.playerConstruct();
        t.checkExpect(p1.cur.equals(p1.cur), true);
    }
    
    //test the randomConstructor() methods in ForbiddenIslandWorld
    void testRandomConstructor(Tester t) {
        this.setUpTests();
        test1 = w1.randomConstruct(64);
        t.checkExpect(test1.get(0).get(0) == 0, true);
    }

    //test the createCopter() methods in ForbiddenIslandWorld
    void testCreateCopter(Tester t) {
        this.setUpTests();
        test = w1.createCells((w1.mountainConstruct(64)));
        t.checkExpect(w1.copter.cur.height == test.get(31).get(31).height);
        t.checkExpect(w1.copter.cur.x == test.get(31).get(31).x);
        t.checkExpect(w1.copter.cur.y == test.get(31).get(31).y);
    }
    
    //test the randomCell() methods in ForbiddenIslandWorld
    void testRandomCell(Tester t) {
        this.setUpTests();
        t.checkExpect(w1.land().has(w1.randomCell()));
    }
    
    //test the land() methods in ForbiddenIslandWorld
    void testLand(Tester t) {
        this.setUpTests();
        test = w1.createCells(w1.mountainConstruct(64));
        w1.board = w1.createBoard(test);
        list1 = w1.land();
        t.checkExpect(list1.has(test.get(31).get(31)), true);
        t.checkExpect(list1.has(test.get(0).get(0)), false);
    }
    
    //test the createBoard() methods in ForbiddenIslandWorld
    void testCreateBoard(Tester t) {
        this.setUpTests();
        list1 = w1.createBoard(w1.createCells(w1.mountainConstruct(64)));
        t.checkExpect(w1.board.equals(w1.board), true);
    }

    //test the createCells() methods in ForbiddenIslandWorld
    void testCreateCells(Tester t) {
        this.setUpTests();
        test = w1.createCells(test3);
        t.checkExpect(test.get(0).get(0).height == 0, true);
        t.checkExpect(test.get(0).get(0).right.equals(test.get(0).get(1)), true);
        t.checkExpect(test.get(0).get(1).bottom.equals(test.get(1).get(1)), true);
    }
    
    //test the coast() methods in ForbiddenIslandWorld
    void testCoast(Tester t) {
        this.setUpTests();
        test = w1.createCells(w1.mountainConstruct(64));
        w1.board = w1.createBoard(test);
        list1 = w1.coast();
        t.checkExpect(list1.has(test.get(31).get(1)), true);
    }
    
    //test the createParts() methods in ForbiddenIslandWorld
    void testCreateParts(Tester t) {
        this.setUpTests();
        t.checkExpect(w1.parts.equals(w1.parts), true);
    }
    
    //test the floodingRooni() methods in ForbiddenIslandWorld
    void testFloodingRooni(Tester t) {
        this.setUpTests();
        w1.waterHeight = 1;
        test = w1.createCells(w1.mountainConstruct(64));
        w1.board = w1.createBoard(test);
        t.checkExpect(w1.board.has(test.get(31).get(1)), true);
        w1.floodingRooni();
        t.checkExpect(test.get(31).get(1).isFlooded, true);
        t.checkExpect(w1.board.has(test.get(31).get(2)), true);
    }
    
    //test the makeImage() methods in ForbiddenIslandWorld
    void testMakeImage(Tester t) {
        this.setUpTests();
        w1.board = w1.createBoard(w1.createCells(w1.mountainConstruct(2)));
        t.checkExpect(w1.makeImage(), this.w1.boardImage().overlayImages(
                this.w1.heliImage()).overlayImages(
                        this.w1.player.playerImage()));
    }
    
    //test the makeImage() methods in ForbiddenIslandWorld
    void testboardImage(Tester t) {
        this.setUpTests();
        test = w1.createCells(w1.mountainConstruct(2));
        w1.board = w1.createBoard(test);
        t.checkExpect(w1.boardImage(), 
                new RectangleImage(new Posn(0, 0), 0, 0, new White()).overlayImages(
                        test.get(1).get(1).cellImage(0)).overlayImages(
                        test.get(1).get(0).cellImage(0)).overlayImages(
                        test.get(0).get(1).cellImage(0)).overlayImages(
                        test.get(0).get(0).cellImage(0)));
    }
    
    //test the onKey() methods in ForbiddenIslandWorld
    void testOnKey(Tester t) {
        this.setUpTests();
        test = w1.createCells(w1.mountainConstruct(64));
        w1.board = w1.createBoard(w1.createCells(w1.mountainConstruct(64)));
        w1.player.cur = test.get(31).get(31);
        t.checkExpect(w1.player.cur == test.get(31).get(31), true);
        w1.onKeyEvent("up");
        t.checkExpect(w1.player.cur == test.get(30).get(31), true);
        w1.onKeyEvent("down");
        t.checkExpect(w1.player.cur == test.get(31).get(31), true);
        w1.onKeyEvent("left");
        t.checkExpect(w1.player.cur == test.get(31).get(30), true);
        w1.onKeyEvent("right");
        t.checkExpect(w1.player.cur == test.get(31).get(31), true);
        w1.waterHeight = 20;
        w1.count = 9;
        w1.onKeyEvent("m");
        t.checkExpect(w1.waterHeight == 0, true);
        t.checkExpect(w1.count == 0, true);
        w1.waterHeight = 20;
        w1.count = 9;
        w1.onKeyEvent("r");
        t.checkExpect(w1.waterHeight == 0, true);
        t.checkExpect(w1.count == 0, true);
        w1.waterHeight = 20;
        w1.count = 9;
        w1.onKeyEvent("t");
        t.checkExpect(w1.waterHeight == 0, true);
        t.checkExpect(w1.count == 0, true);
    }
    
    
    /*
    // RUN THE GAME BY REMOVING THE COMMENTS ON THE LINE BELOW    
    void testAnimation(Tester t) {
        this.setUpTests();
        this.w1.bigBang(640, 640, .1);
    }*/
}

