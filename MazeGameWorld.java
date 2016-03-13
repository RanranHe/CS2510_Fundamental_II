// Assignment 10
// Schenck Robert
// schenckr
// Ranran He
// heranran


import java.awt.Color;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;


import java.util.ArrayList;

import tester.*;
import javalib.impworld.*;
import javalib.colors.*;
import javalib.worldimages.*;

//represent the position field of a node, with an equals method checking for extensional
// equality
class NodePosn extends Posn {

    //the constructor
    public NodePosn(int x, int y) {
        super(x, y);
    }
    
    /*
     * TEMPLATE 
     * FIELDS: 
     * ... this.x -- int
     * ... this.y -- int
     * METHODS: 
     * ... this.equals(nodePosn) -- boolean
     */
    
    
    //are this nodePosn and the given nodePosn the same (based on x and y position)
    boolean samePosn(NodePosn p) {
        return (this.x == p.x && this.y == p.y);
    }
    
    
}

// Represents a single square of the game area
class Node {
    
    // In logical coordinates, with the origin at the top-left corner of the scren
    NodePosn myPos;
    // the four adjacent cells to this one
    Node left;
    Node top;
    Node right;
    Node bottom;
    
    //list of booleans, are the listed directions available as paths to take
    boolean isPathTop;
    boolean isPathBottom;
    boolean isPathRight;
    boolean isPathLeft;
    
    //has the player traveled on this node at any point
    boolean hasTraveled;
    
    //is the player on this node
    boolean playersNode;

    
    //the constructor
    Node(int x, int y) {
        this.myPos = new NodePosn(x, y);
        this.left = null;
        this.right = null;
        this.top = null;
        this.bottom = null;
        isPathTop = false;
        isPathBottom = false;
        isPathRight = false;
        isPathLeft = false;
        hasTraveled = false;
        playersNode = false;
    }
    
    /*
     * TEMPLATE 
     * FIELDS: 
     * ... this.myPos -- nodePosn
     * ... this.right -- Node
     * ... this.top -- Node
     * ... this.left -- Node
     * ... this.bottom -- Node
     * ... this.isPathTop -- boolean
     * ... this.isPathRight -- boolean
     * ... this.isPathLeft -- boolean
     * ... this.isPathBottom -- boolean
     * ... this.hasTraveled -- boolean
     * ... this.playersNode -- boolean
     * METHODS: 
     * ... this.nodeImage(int) -- WorldImage
     * ... this.updateLeft(Node) -- void
     * ... this.updateRight(Node) -- void
     * ... this.updateTop(Node) -- void
     * ... this.updateBottom(Node) -- void
     * ... this.updateIsPathTop(boolean) -- void
     * ... this.updateIsPathRight(boolean) -- void
     * ... this.updateIsPathLeft(boolean) -- void
     * ... this.updateIsPathBottom(boolean) -- void
     * ... this.updateSides(Node) -- void
     */ 
    
    
    //produce an image of this node, with walls depending on the edges of the game
    public WorldImage nodeImage(int cellsize) {
        WorldImage i;
        Color linec = new Color(15, 15, 15);
        Color c;
        Posn location = new Posn(this.myPos.x * cellsize + cellsize / 2, 
                this.myPos.y * cellsize + cellsize / 2);
        Posn cornerLoc = new Posn(this.myPos.x * cellsize, this.myPos.y * cellsize);
        
        if (playersNode) {
            c = new Color(0, 102, 204);
        }
        else if (hasTraveled) {
            c = new Color(102, 178, 255);   
        }
        else {
            c = new Color(160, 160, 160);
        }
               
        i = new RectangleImage(location, cellsize, cellsize, c);
        
        if (!isPathTop) {
            i = i.overlayImages(new LineImage(cornerLoc, new Posn(cornerLoc.x + cellsize, 
                    cornerLoc.y), linec));
        }
        if (!isPathBottom) {
            i = i.overlayImages(new LineImage(new Posn(cornerLoc.x, cornerLoc.y + cellsize), 
                    new Posn(cornerLoc.x + cellsize, cornerLoc.y + cellsize), linec));
        }
        if (!isPathRight) {
            i = i.overlayImages(new LineImage(new Posn(cornerLoc.x + cellsize, cornerLoc.y), 
                    new Posn(cornerLoc.x + cellsize, cornerLoc.y + cellsize), linec));
        }
        if (!isPathLeft) {
            i = i.overlayImages(new LineImage(cornerLoc, new Posn(cornerLoc.x, 
                    cornerLoc.y + cellsize), linec));
        }
        
        return i;
    }

    
    //update this node's left-link to the given node
    //EFFECT: change this.left to the given Node object "left"
    void updateLeft(Node left) {
        this.left = left;
    }
    
    //update this node's right-link to the given node
    //EFFECT: change this.right to the given Node object "right"
    void updateRight(Node right) {
        this.right = right;
    }
    
    //update this node's top-link to the given node
    //EFFECT: change this.top to the given Node object "top"
    void updateTop(Node top) {
        this.top = top;
    }
    
    //update this node's bottom-link to the given node
    //EFFECT: change this.left to the given Node object "bottom"
    void updateBottom(Node bottom) {
        this.bottom = bottom;
    }
    
    //update the ability to travel from this node to the node above
    //EFFECT: change this.isPathTop to the given boolean (either true or false)
    void updateIsPathTop(boolean bool) {
        this.isPathTop = bool;
    }
    
    //update the ability to travel from this node to the node below
    //EFFECT: change this.isPathBottom to the given boolean (either true or false)
    void updateIsPathBottom(boolean bool) {
        this.isPathBottom = bool;
    }
    
    //update the ability to travel from this node to the node on the right
    //EFFECT: change this.isPathRight to the given boolean (either true or false)
    void updateIsPathRight(boolean bool) {
        this.isPathRight = bool;
    }
    
    //update the ability to travel from this node to the node on the left
    //EFFECT: change this.isPathLeft to the given boolean (either true or false)
    void updateIsPathLeft(boolean bool) {
        this.isPathLeft = bool;
    }
    
    //update this node to record that someone has traveled on it
    //EFFECT: change this.hasTraveled to the given boolean (either true or false)
    void updateHasTraveled() {
        this.hasTraveled = true;
    }
    
    //update this node as the player's  node (to be represented in blue)
    //EFFECT: change this.playersNode to the given boolean (either true or false)
    void updatePlayersNode(boolean bool) {
        this.playersNode = bool;
    }
    
    //update the ability to travel from this node to the given node
    //EFFECT: call one of the update path methods to true
    void updateSides(Node to) {
        if (this.top == to) {
            this.updateIsPathTop(true);
        }
        if (this.bottom == to) {
            this.updateIsPathBottom(true);
        }
        if (this.left == to) {
            this.updateIsPathLeft(true);
        }
        if (this.right == to) {
            this.updateIsPathRight(true);
        }
    }
  
}

//to represent an edge of the game
class Edge {
    
    Node from;
    Node to;
    
    int weight;
    
    //the constructor
    Edge(Node from, Node to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
    
    /*
     * TEMPLATE 
     * FIELDS: 
     * ... this.from -- Node
     * ... this.to -- Node
     * ... this.weight -- int
     */ 
    
}

//Comparator for Edge class (based on weight)
class EdgeComparator implements Comparator<Edge> {
    
    //compare the two edge weights
    public int compare(Edge e1, Edge e2) {
        return e1.weight - e2.weight;
    }
    
}

//to represent a function that will maintain a given hashmap (union and find in hashmap)
class UnionFind {
    
    HashMap<NodePosn, NodePosn> rep;
    
    //the constructor
    UnionFind(HashMap<NodePosn, NodePosn> rep) {
        this.rep = rep;
    }
    
    /*
     * TEMPLATE 
     * FIELDS: 
     * ... this.rep -- Hashmap<nodePosn, nodePosn>
     * METHODS: 
     * ... this.union(nodePosn, nodePosn) -- void
     * ... this.find(nodePosn) -- nodePosn
     */ 
    
    //union the two values of the hashmap
    //CHANGE: update rep value at n1
    void union(NodePosn n1, NodePosn n2) {
        rep.put(this.find(n1), this.find(n2));
    }
    
    //return the representative of this nodePosn key
    NodePosn find(NodePosn given) {
        if (given.samePosn(rep.get(given))) {
            return given;
        }
        else {
            return find(rep.get(given)); 
        }
    }
    
    
}

//to represent the player of the game
class Player {
    
    int x;
    int y;
    
    //A player is defined by his relation to a specific cell.
    Node cur;
    
    //the constructor
    Player(Node cur) {
        this.cur = cur;
        this.x = this.cur.myPos.x;
        this.y = this.cur.myPos.y;
        this.cur.updatePlayersNode(true);
    }
    
    /*
     * TEMPLATE 
     * FIELDS: 
     * ... this.x -- int
     * ... this.y -- int
     * ... this.cur -- Node
     * METHODS: 
     * ... this.updateCell(Node) -- void
     * ... this.canMove(String) -- boolean
     */ 
    
    // update the cell of this player
    // CHANGE: update "cur", the "x" and "y" coordinates and the "playersNode" field in
    // the past and current cell
    void updateCell(Node node) {
        this.cur.updatePlayersNode(false);
        this.cur = node;
        this.x = this.cur.myPos.x;
        this.y = this.cur.myPos.y;
        this.cur.updatePlayersNode(true);
    }
    
    // can the player move in the specified direction?
    boolean canMove(String direction) {
        boolean can = false;
        if (direction.equals("up") && this.cur.isPathTop) {
            can = true;
        }
        if (direction.equals("down") && this.cur.isPathBottom) {
            can = true;
        }
        if (direction.equals("left") && this.cur.isPathLeft) {
            can = true;
        }
        if (direction.equals("right") && this.cur.isPathRight) {
            can = true;
        }
        return can;
    }
    
}
 
//to represent the Maze Game World
class MazeGameWorld extends World {
  
    // Defines an int constant
    static final int GAME_HEIGHT = 60;
    static final int GAME_WIDTH = 100;
    
    
    static final int WINDOW_HEIGHT = 600;
    static final int WINDOW_WIDTH = 1000;
    

    //height and width of the game, originally set to GAME_HEIGHT and GAME_WIDTH
    //the user can overwrite it using the second constructor
    
    int gameHeight;
    int gameWidth;
   
    int cellsize;
    
    Random rand;

    // All the edges of the game
    ArrayList<Edge> edgesInTree;
    
    // All the edges of the game
    ArrayList<Edge> worklist;
    
    // All the nodes of the game
    ArrayList<ArrayList<Node>> board;
    
    //
    UnionFind unionfind; 
    
    Player p;
    Node endNode;
    
    //booleans to activate the searches
    boolean depth;
    boolean breadth;
    
    //the constructor
    MazeGameWorld() {
        
        gameHeight = GAME_HEIGHT;
        gameWidth = GAME_WIDTH;
        
        
        cellsize = WINDOW_WIDTH / gameWidth;
        this.board = this.createNodes(gameWidth, gameHeight);
        this.unionfind = new UnionFind(this.mapReps(this.board));
        rand = new Random();
        this.worklist = this.createLinks(this.board);
        this.edgesInTree = this.createTree(this.worklist, this.unionfind);
        this.updateNodes(edgesInTree);
        p = new Player(this.board.get(0).get(0));
        endNode = this.board.get(gameHeight - 3).get(gameWidth - 1);
        
        depth = false;
        breadth = false;
    }
    
    
    
    //the constructor
    MazeGameWorld(int width, int height) {     
        gameHeight = height;
        gameWidth = width;
        
        cellsize = WINDOW_WIDTH / gameWidth;  
        this.board = this.createNodes(gameWidth, gameHeight);
        this.unionfind = new UnionFind(this.mapReps(this.board));
        rand = new Random();
        this.worklist = this.createLinks(this.board);
        this.edgesInTree = this.createTree(this.worklist, this.unionfind);
        this.updateNodes(edgesInTree);  
        p = new Player(this.board.get(0).get(0));    
        endNode = this.board.get(gameHeight - 3).get(gameWidth - 1);
        
        depth = false;
        breadth = false;
    }
    
    
    
    /*
     * TEMPLATE 
     * CONSTANTS:
     * ... this.GAME_HEIGHT -- int
     * ... this.GAME_WIDTH -- int
     * ... this.WINDOW_HEIGHT -- int
     * ... this.WINDOW_WIDTH -- int
     * FIELDS: 
     * ... this.gameHeight -- int
     * ... this.gameWidth -- int
     * ... this.cellsize -- int
     * ... this.rand -- Random
     * ... this.player -- Player
     * ... this.endNode -- Player
     * ... this.edgesInTree -- ArrayList<Edge>
     * ... this.worklist -- ArrayList<Edge>
     * ... this.board -- ArrayList<ArrayList<Node>>
     * ... this.depth -- boolean
     * ... this.breadth -- boolean
     * ... this.unionfind -- UnionFind
     * METHODS: 
     * ... this.createNodes(int, int) -- ArrayList<ArrayList<Node>>
     * ... this.mapRepss(ArrayList<ArrayList<Node>>) -- HashMap<nodePosn, nodePosn>
     * ... this.createLinks(ArrayList<ArrayList<Node>>) -- ArrayList<Edge>
     * ... this.createTree(ArrayList<Edge>, UnionFind) -- ArrayList<Edge> 
     * ... this.updateNodes(ArrayList<Edge>) -- void 
     * ... this.makeImage() -- WorldImage 
     * ... this.drawAll(ArrayList<ArrayList<Node>>) -- WorldImage  
     * ... this.drawRow(ArrayList<Node>) -- WorldImage 
     * ... this.startEndImage() -- WorldImage 
     * ... this.onKeyEvent(String) -- void 
     * ... this.onTick() -- void 
     * ... this.worldEnds() -- WorldEnd
     * ... this.inkCells(ArrayList<ArrayList<Node>>) -- void l
     */ 
    
    //create the game by creating all the nodes and link them together
    ArrayList<ArrayList<Node>> createNodes(int width, int height) {
        ArrayList<ArrayList<Node>> grid = new ArrayList<ArrayList<Node>>();
        
        for (int index = 0; index < height; index += 1) {
            ArrayList<Node> row = new ArrayList<Node>();
            grid.add(row);
            for (int index2 = 0; index2 < width; index2 += 1) {
                grid.get(index).add(new Node(index2, index));   
            }
        }     
        this.linkCells(grid);
        return grid;
    }
    
    //create the hashmap of the game and map all nodes to themselves
    HashMap<NodePosn, NodePosn> mapReps(ArrayList<ArrayList<Node>> nodes) {   
        HashMap<NodePosn, NodePosn> reps = new HashMap<NodePosn, NodePosn>();
        
        for (int index = 0; index < nodes.size(); index++) {
            for (int index2 = 0; index2 < nodes.get(index).size(); index2 += 1) {  
                reps.put(nodes.get(index).get(index2).myPos, nodes.get(index).get(index2).myPos);
            }

        }
        return reps;
    }
    
    
    //create an ArrayList<Edge> for the walls of the map and sort them by weight
    ArrayList<Edge> createLinks(ArrayList<ArrayList<Node>> nodes) {
        ArrayList<Edge> dest = new ArrayList<Edge>();
        Node curNode;
        for (int index = 0; index < nodes.size(); index += 1) {
            for (int index2 = 0; index2 < nodes.get(index).size(); index2 += 1) {
                curNode = nodes.get(index).get(index2);
                //Top to bottom edges
                if (index < nodes.size()) {
                    dest.add(new Edge(curNode, curNode.bottom, this.rand.nextInt(1000) + 1));   
                }
                else {
                    //Do Nothing                   
                }
                // left to right edges
                if (index2 < nodes.get(index).size()) {
                    dest.add(new Edge(curNode, curNode.right, this.rand.nextInt(1000) + 1));   
                }
                else {
                    //Do Nothing                   
                }
            }
        }  
        
        Collections.sort(dest, new EdgeComparator());
        return dest;
    }
    
    
    //create the tree of the game by selecting or not selecting the cheapest edges
    ArrayList<Edge> createTree(ArrayList<Edge> source, UnionFind uf) {  
        ArrayList<Edge> dest = new ArrayList<Edge>();
        int index = 0;
        int totalNodes = gameHeight * gameWidth;
        Edge curEdge;
        while (dest.size() < totalNodes && index < source.size()) {
            curEdge = source.get(index);
            NodePosn key1 = this.unionfind.find(curEdge.from.myPos);
            NodePosn key2 = this.unionfind.find(curEdge.to.myPos);
            if (uf.find(key1).samePosn(uf.find(key2))) {
                //discard this edge by doing nothing
            }
            else {
                dest.add(curEdge);
                uf.union(key1, key2); 
            }
            index += 1;
        }
        
        return dest;
    }
    
    //update the 
    void updateNodes(ArrayList<Edge> edges) {
        for (int index = 0; index < edges.size(); index += 1) {
            edges.get(index).from.updateSides(edges.get(index).to);
            edges.get(index).to.updateSides(edges.get(index).from);
        }

    }
    

    
    //produce an image of the world
    public WorldImage makeImage() {
        return this.drawAll(this.board);
    }
    
    //draw the nodes
    public WorldImage drawAll(ArrayList<ArrayList<Node>> all) {
        WorldImage i = new RectangleImage(new Posn(0, 0), 0, 0, new Blue());
        
        for (int index = 0; index < all.size(); index += 1) {
            i = i.overlayImages(this.drawRow(all.get(index)));
        }
        
        return i.overlayImages(this.startEndImage());
    }
    
    //draw a row of nodes
    public WorldImage drawRow(ArrayList<Node> row) {
        WorldImage i = new RectangleImage(new Posn(0, 0), 0, 0, new Blue());
        
        for (int index = 0; index < row.size(); index += 1) {
            i = i.overlayImages(row.get(index).nodeImage(cellsize));
        }
        
        return i;
    }
    
    //print the starting and ending nodes
    public WorldImage startEndImage() {
        
        WorldImage i = new RectangleImage(new Posn(cellsize  / 2, cellsize / 2), 
                cellsize, cellsize, new Green());
        i = i.overlayImages(new RectangleImage(
                new Posn((gameWidth * cellsize) - (cellsize / 2),
                        (gameHeight * (WINDOW_HEIGHT / gameHeight)) - (cellsize / 2)), 
                        cellsize, 
                cellsize, new Color(102, 0, 204)));
        return i;
        
    }


    //handle key inputs, the arrow keys move the player
    //EFFECT: reset the world by resetting its various properties or changing p.cur
    //also activate depth first search
    public void onKeyEvent(String ke) {
        if (ke.equals("n")) {
            this.board = this.createNodes(gameWidth, gameHeight);
            this.unionfind = new UnionFind(this.mapReps(this.board));
            rand = new Random();
            this.worklist = this.createLinks(this.board);
            this.edgesInTree = this.createTree(this.worklist, this.unionfind);      
            this.updateNodes(edgesInTree);
            p = new Player(this.board.get(0).get(0));
        } 
        if (ke.equals("b") && !depth) {
            //TODO
            //activates the boolean breadth first search
            breadth = true;
        } 
        if (ke.equals("d") && !breadth) {
            //TODO
            //activates the boolean depth first search
            depth = true;
        } 
        if (ke.equals("left") && p.canMove("left") && !(p.cur.left.equals(p.cur))) {
            this.p.cur.updateHasTraveled();
            this.p.updateCell(p.cur.left);
            //TODO
        } 
        if (ke.equals("up") && p.canMove("up") && !(p.cur.top.equals(p.cur))) {
            this.p.cur.updateHasTraveled();
            this.p.updateCell(p.cur.top);
            
        } 
        if (ke.equals("down") && p.canMove("down") && !(p.cur.bottom.equals(p.cur))) {
            this.p.cur.updateHasTraveled();
            this.p.updateCell(p.cur.bottom);
        } 
        if (ke.equals("right") && p.canMove("right") && !(p.cur.right.equals(p.cur))) {
            this.p.cur.updateHasTraveled();
            this.p.updateCell(p.cur.right);
            
        } 
        else {
            return;
        }
    }
    
    
    
    
    //BREADTH FIRST AND DEPTH FIRST PSUEDOCODE
    /*
    // In Graph
    boolean bfs(Vertex from, Vertex to) {
      return searchHelp(from, to, new Queue<Vertex>());
    }
    boolean dfs(Vertex from, Vertex to) {
      return searchHelp(from, to, new Stack<Vertex>());
    }
    boolean searchHelp(Vertex from, Vertex to, ICollection<Vertex> worklist) {
      Deque<Vertex> alreadySeen = new Deque<Vertex>();
     
      // Initialize the worklist with the from vertex
      worklist.add(from);
      // As long as the worklist isn't empty...
      while (!worklist.isEmpty()) {
        Vertex next = worklist.remove();
        if (next.equals(to)) {
          return true; // Success!
        }
        else if (alreadySeen.contains(next)) {
          // do nothing: we've already seen this one
        }
        else {
          // add all the neighbors of next to the worklist for further processing
          for (Edge e : next.outEdges) {
            worklist.add(e.to);
          }
          // add next to alreadySeen, since we're done with it
          alreadySeen.addAtHead(next);
        }
      }
      // We haven't found the to vertex, and there are no more to try
      return false;
    }
    */
    
    
    
    // Should advance the depth and breadth first searches if activated
    public void onTick() {
        // made a call to the Breadth and depth first searches here
        // searchHelp()
    }
    

    
    // ends the game if the player gets to the end of the maze
    public WorldEnd worldEnds() {
        if (this.p.cur.myPos.x == this.endNode.myPos.x && 
                this.p.cur.myPos.y == this.endNode.myPos.y - 1) { 
            return new WorldEnd(true, new OverlayImages(this.makeImage(),
                    new TextImage(new Posn(320, 320), "You Won!", 
                            23, 4, new Color(102, 0, 204))));
        } 
        else {
            return new WorldEnd(false, this.makeImage());
        }
    }
    
    // link all the nodes in an ArrayList<ArrayList<Node>> 
    //EFFECT: change left, right, top and bottom nodes
    public void linkCells(ArrayList<ArrayList<Node>> nodes) {
        for (int index = 0; index < nodes.size(); index += 1) {           
            for (int index2 = 0; index2 < nodes.get(index).size(); index2 += 1) {                
                Node curNode = nodes.get(index).get(index2);
                
                if (index2 < nodes.get(index).size() - 1) {
                    curNode.updateRight(nodes.get(index).get(index2 + 1));
                }
                else {
                    curNode.updateRight(curNode);
                }
                if (index2 > 0) {
                    curNode.updateLeft(nodes.get(index).get(index2 - 1));
                }
                else {
                    curNode.updateLeft(curNode);
                }
                if (index > 0) {
                    curNode.updateTop(nodes.get(index - 1).get(index2));
                }
                else {
                    curNode.updateTop(curNode);
                }
                if (index < nodes.size() - 1) {
                    curNode.updateBottom(nodes.get(index + 1).get(index2));
                }
                else {
                    curNode.updateBottom(curNode);
                }
            }           
        }
    }
    

}


//examples for game MazeGame
class ExamplesMazeGameWorld {

    //examples of MazeGameWorld
    MazeGameWorld w1;
    MazeGameWorld w2;
    ArrayList<Edge> test;
    ArrayList<ArrayList<Node>> test1;
    ArrayList<ArrayList<Node>> test2;
    
    NodePosn posn1 = new NodePosn(12, 13);
    NodePosn posn2 = new NodePosn(12, 13);
    NodePosn posn3 = new NodePosn(13, 13);
    
    Node node1;
    Node node2;
    Node node3;
    Node node4;
    Node node5;
    
    Node node6;
    Node node7;
    Node node8;
    Node node9;
    
    Edge edge1;
    Edge edge2;
    Edge edge3;
    Edge edge4;
    
    Player p1;
    
    HashMap<NodePosn, NodePosn> hm;
    
    EdgeComparator ed = new EdgeComparator();
    
    UnionFind uf;

    void setUpTests() {
        w1 = new MazeGameWorld(20, 15);
        w2 = new MazeGameWorld(100, 60);
        test1 = w1.createNodes(2, 2);
        test2 = w1.createNodes(1, 1);

        node1 = new Node(12, 13);
        node2 = new Node(12, 12);
        node3 = new Node(12, 11);
        node4 = new Node(13, 12);
        node5 = new Node(11, 12);
        
        node6 = new Node(0, 0);
        node7 = new Node(1, 0);
        node8 = new Node(0, 1);
        node9 = new Node(1, 1);
        
        node6.updateBottom(node8);
        node6.updateTop(node6);
        node6.updateRight(node7);
        node6.updateLeft(node6);
        
        node7.updateBottom(node9);
        node7.updateTop(node7);
        node7.updateRight(node7);
        node7.updateLeft(node6);
        
        node8.updateBottom(node8);
        node8.updateTop(node6);
        node8.updateRight(node9);
        node8.updateLeft(node8);
        
        node9.updateBottom(node9);
        node9.updateTop(node7);
        node9.updateRight(node9);
        node9.updateLeft(node8);
        
        hm = new HashMap<NodePosn, NodePosn>();
        
        uf = new UnionFind(hm);
        
        edge1 = new Edge(node2, node1, 2);
        edge2 = new Edge(node2, node4, 7);
        edge3 = new Edge(node2, node3, 4);
        edge4 = new Edge(node2, node5, 1);
    }
   
    
    void testSamePosn(Tester t) {
        t.checkExpect(posn1.samePosn(posn2), true);
        t.checkExpect(posn1.samePosn(posn3), false);
    }
    
    void testNodeImage(Tester t) {
        this.setUpTests();

        Color linec = new Color(15, 15, 15);
        t.checkExpect(node1.nodeImage(10), new RectangleImage(new Posn(125, 135), 10, 10,
            new Color(160, 160, 160)).overlayImages(
                new LineImage(new Posn(120, 130), new Posn(130, 130), linec
                )).overlayImages(new LineImage(new Posn(120, 140), 
                        new Posn(130, 140), linec)).overlayImages(new LineImage(
                            new Posn(130, 130), new Posn(130, 140), 
                                linec)).overlayImages(new LineImage(
                                    new Posn(120, 130), new Posn(120, 140), linec)));
        
        node1.updateHasTraveled();
        
        t.checkExpect(node1.nodeImage(10), new RectangleImage(new Posn(125, 135), 10, 10,
                new Color(102, 178, 255)).overlayImages(
                    new LineImage(new Posn(120, 130), new Posn(130, 130), linec
                    )).overlayImages(new LineImage(new Posn(120, 140), 
                            new Posn(130, 140), linec)).overlayImages(new LineImage(
                                new Posn(130, 130), new Posn(130, 140), 
                                    linec)).overlayImages(new LineImage(
                                        new Posn(120, 130), new Posn(120, 140), linec)));
        
        node1.updatePlayersNode(true);
        
        t.checkExpect(node1.nodeImage(10), new RectangleImage(new Posn(125, 135), 10, 10,
                new Color(0, 102, 204)).overlayImages(
                    new LineImage(new Posn(120, 130), new Posn(130, 130), linec
                    )).overlayImages(new LineImage(new Posn(120, 140), 
                            new Posn(130, 140), linec)).overlayImages(new LineImage(
                                new Posn(130, 130), new Posn(130, 140), 
                                    linec)).overlayImages(new LineImage(
                                        new Posn(120, 130), new Posn(120, 140), linec)));
        
    }
     
    void testUpdateLeft(Tester t) {
        this.setUpTests();
        t.checkExpect(node2.left == null);
        node2.updateLeft(node5);
        t.checkExpect(node2.left == node5);
    }
    
    void testUpdateRight(Tester t) {
        this.setUpTests();
        t.checkExpect(node2.right == null);
        node2.updateRight(node5);
        t.checkExpect(node2.right == node5);
    }
    
    void testUpdateTop(Tester t) {
        this.setUpTests();
        t.checkExpect(node2.top == null);
        node2.updateTop(node5);
        t.checkExpect(node2.top == node5);
    }
    
    void testUpdateBottom(Tester t) {
        this.setUpTests();
        t.checkExpect(node2.bottom == null);
        node2.updateBottom(node5);
        t.checkExpect(node2.bottom == node5);
    }
     
    void testUpdateIsPathLeft(Tester t) {
        this.setUpTests();
        t.checkExpect(!node2.isPathLeft);
        node2.updateIsPathLeft(true);
        t.checkExpect(node2.isPathLeft);
    }
    
    void testUpdateIsPathRight(Tester t) {
        this.setUpTests();
        t.checkExpect(!node2.isPathRight);
        node2.updateIsPathRight(true);
        t.checkExpect(node2.isPathRight);
    }
    
    void testUpdateIsPathTop(Tester t) {
        this.setUpTests();
        t.checkExpect(!node2.isPathTop);
        node2.updateIsPathTop(true);
        t.checkExpect(node2.isPathTop);
    }
    
    void testUpdateIsPathBottom(Tester t) {
        this.setUpTests();
        t.checkExpect(!node2.isPathBottom);
        node2.updateIsPathBottom(true);
        t.checkExpect(node2.isPathBottom);
    }
    
    void testUpdateSides(Tester t) {
        this.setUpTests();
        t.checkExpect(!node2.isPathBottom);
        t.checkExpect(!node2.isPathTop);
        t.checkExpect(!node2.isPathLeft);
        t.checkExpect(!node2.isPathRight);
        node2.updateBottom(node1);
        node2.updateTop(node5);
        node2.updateRight(node4);
        node2.updateLeft(node3);
        node2.updateSides(node1);
        t.checkExpect(node2.isPathBottom);
        node2.updateSides(node5);
        t.checkExpect(node2.isPathTop);
        node2.updateSides(node4);
        t.checkExpect(node2.isPathRight);
        node2.updateSides(node3);
        t.checkExpect(node2.isPathLeft);
    }
    
    void testUpdateCell(Tester t) {
        this.setUpTests();
        p1 = new Player(node1);
        t.checkExpect(p1.cur.equals(node1));
        p1.updateCell(node3);
        t.checkExpect(p1.cur.equals(node3)); 
    }
    
    void testCanMove(Tester t) {
        this.setUpTests();
        p1 = new Player(node1);
        p1.updateCell(node2);
        t.checkExpect(p1.canMove("up"), false);
        t.checkExpect(p1.canMove("down"), false);
        t.checkExpect(p1.canMove("left"), false);
        t.checkExpect(p1.canMove("right"), false);
        node2.updateIsPathRight(true);
        node2.updateIsPathLeft(true);
        node2.updateIsPathTop(true);
        node2.updateIsPathBottom(true);
        t.checkExpect(p1.canMove("up"), true);
        t.checkExpect(p1.canMove("down"), true);
        t.checkExpect(p1.canMove("left"), true);
        t.checkExpect(p1.canMove("right"), true);
    }
    
    void testCompare(Tester t) {
        this.setUpTests();
        edge1 = new Edge(node2, node1, 2);
        edge2 = new Edge(node2, node4, 7);
        edge3 = new Edge(node2, node3, 4);
        edge4 = new Edge(node2, node5, 1);
        t.checkExpect(ed.compare(edge1, edge2), -5);
    }
    
    void testOnTick(Tester t) {
        this.setUpTests();
        w1.onTick();
    }
    
    void testCreateNodes(Tester t) {
        this.setUpTests(); 

        t.checkExpect(test1.get(1).get(1), node9);
    }
    
    void testMapReps(Tester t) {
        this.setUpTests();
        hm = w1.mapReps(test1);
        t.checkExpect(hm.get(test1.get(1).get(1).myPos), node9.myPos);
    }
    
    void testCreateLink(Tester t) {
        this.setUpTests();
        w1.linkCells(test1);
        t.checkExpect(test1.get(1).get(1).right, node9);
        t.checkExpect(test1.get(0).get(1).right, node7);
    }
    
    void testStartEndImage(Tester t) {
        this.setUpTests();
        t.checkExpect(w1.startEndImage(), new RectangleImage(new Posn(25, 25), 50, 
                50, new Green()).overlayImages(new RectangleImage(
                        new Posn(975, 575), 50, 50, new Color(102, 0, 204)))); 
    }
    
    void testDrawRow(Tester t) {
        this.setUpTests();
        Color linec = new Color(15, 15, 15);
        t.checkExpect(w1.drawRow(test2.get(0)), new RectangleImage(new Posn(0, 0), 0, 0,
            new Color(0, 0, 255)).overlayImages(new RectangleImage(new Posn(25, 25), 50, 50,
                new Color(160, 160, 160)).overlayImages(
                    new LineImage(new Posn(0, 0), new Posn(50, 0), linec
                    )).overlayImages(new LineImage(new Posn(0, 50), 
                            new Posn(50, 50), linec)).overlayImages(new LineImage(
                                new Posn(50, 0), new Posn(50, 50), 
                                    linec)).overlayImages(new LineImage(
                                        new Posn(0, 0), new Posn(0, 50), linec)))); 
    }
    
    void testWorldEnds(Tester t) {
        this.setUpTests();
        t.checkExpect(w1.worldEnds(), new WorldEnd(false, this.w1.makeImage()));
        
        //this.w1.p.updateCell(this.w1.endNode.cur);
        //t.checkExpect(w1.worldEnds(), new WorldEnd(true, new OverlayImages(
        //    this.w1.makeImage(), new TextImage(new Posn(320, 320), "You Won!", 
         //       23, 4, new Color(102, 0, 204)))));
    }
    
    void testCreateLinks(Tester t) {
        this.setUpTests();
        Node guy = new Node(0, 0);
        guy.updateBottom(guy);
        guy.updateTop(guy);
        guy.updateRight(guy);
        guy.updateLeft(guy);
        
        t.checkExpect(w1.createLinks(test2).get(0).from, guy);
        t.checkExpect(w1.createLinks(test2).get(0).to, guy);
        t.checkRange(w1.createLinks(test2).get(0).weight, 0, 1000);
        
    }
    
    void testUpdateNodes(Tester t) {
        this.setUpTests();
        test = w1.createLinks(test2);
        w1.board = test2;
        w1.updateNodes(test);
        t.checkExpect(w1.board.get(0).get(0).isPathBottom, true);
    }
    
    void testOnKeyEvent(Tester t) {
        this.setUpTests();
        w1.board = test2;
        w1.p.updateCell(node6);
        this.w1.onKeyEvent("b");
        t.checkExpect(w1.breadth, true);
        w1.breadth = false;
        this.w1.onKeyEvent("d");
        t.checkExpect(w1.depth, true);
        this.w1.onKeyEvent("n");
        t.checkExpect(w1.p.cur.myPos.samePosn(new NodePosn(0, 0)), true);
        this.w1.onKeyEvent("left");
        t.checkExpect(w1.p.cur.myPos.samePosn(new NodePosn(0, 0)), true);
        this.w1.onKeyEvent("up");
        t.checkExpect(w1.p.cur.myPos.samePosn(new NodePosn(0, 0)), true);
        
    }
    
    void testCreateTree(Tester t) {
        this.setUpTests();
        w1.board = test2;
        t.checkExpect(w1.equals(w1));
    }

    /*
    // RUN THE GAME BY UNCOMMENTING THE LINES BELOW    
    void testAnimation(Tester t) {
        this.setUpTests();
        this.w2.bigBang(1000, 600, .1);
    }
    */
}




