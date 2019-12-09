package src.engine.ai.util;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class AStar {
    private final List<Node> open;
    private final List<Node> closed;
    private final List<Node> path;
    private final int[][] maze;
    private Node now;
    private final int xstart;
    private final int ystart;
    private int xend, yend;
    private final boolean diag;


    public class Node implements Comparable {
        public Node parent;
        public int x, y;
        public double g;
        public double h;
        Node(Node parent, int xpos, int ypos, double g, double h) {
            this.parent = parent;
            this.x = xpos;
            this.y = ypos;
            this.g = g;
            this.h = h;
        }
        @Override
        public int compareTo(Object o) {
            Node that = (Node) o;
            return (int)((this.g + this.h) - (that.g + that.h));
        }
    }

    public AStar(int[][] maze, int xstart, int ystart, boolean diag) {
        this.open = new ArrayList<>();
        this.closed = new ArrayList<>();
        this.path = new ArrayList<>();
        this.maze = maze;
        this.now = new Node(null, xstart, ystart, 0, 0);
        this.xstart = xstart;
        this.ystart = ystart;
        this.diag = diag; /*BOOLEEN pour autoriser diagonale*/
    }

    /*Trouve court chemin vers case (xend,yend)*/
    public List<Node> findPathTo(int xend, int yend) {
        this.xend = xend;
        this.yend = yend;
        this.closed.add(this.now);
        addNeigborsToOpenList();
        while (this.now.x != this.xend || this.now.y != this.yend) {
            if (this.open.isEmpty()) { /*Rien à examiner*/
                return null;
            }
            this.now = this.open.get(0); // 1er noeud score le plus bas
            this.open.remove(0);
            this.closed.add(this.now);
            addNeigborsToOpenList();
        }
        this.path.add(0, this.now);
        while (this.now.x != this.xstart || this.now.y != this.ystart) {
            this.now = this.now.parent;
            this.path.add(0, this.now);
        }
        return this.path;
    }
    private static boolean findNeighborInList(List<Node> array, Node node) {
        return array.stream().anyMatch((n) -> (n.x == node.x && n.y == node.y));
    }
    /*calcule distance entre this.now et point(xend,yend)*/
    private double distance(int dx, int dy) {
        if (this.diag) { // if diagonal movement is alloweed
            return Math.hypot(this.now.x + dx - this.xend, this.now.y + dy - this.yend); // return hypothenuse
        } else {
            return Math.abs(this.now.x + dx - this.xend) + Math.abs(this.now.y + dy - this.yend); // else return "Manhattan distance"
        }
    }
    private void addNeigborsToOpenList() {
        Node node;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (!this.diag && x != 0 && y != 0) {
                    continue; // on passe si diagonale non autorisée
                }
                node = new Node(this.now, this.now.x + x, this.now.y + y, this.now.g, this.distance(x, y));
                if ((x != 0 || y != 0) // not this.now
                        && this.now.x + x >= 0 && this.now.x + x < this.maze[0].length // teste bords labyrinthe
                        && this.now.y + y >= 0 && this.now.y + y < this.maze.length
                        && this.maze[this.now.y + y][this.now.x + x] != -1 // check if square is walkable
                        && !findNeighborInList(this.open, node) && !findNeighborInList(this.closed, node)) { // Si pas encore fait
                    node.g = node.parent.g + 1.; // coût = 1
                    node.g += maze[this.now.y + y][this.now.x + x]; // On ajoute un coût de mouvement pour cette case

                    this.open.add(node);
                }
            }
        }
        Collections.sort(this.open);
    }

    public static void printPath(int[][] myMap){
        for (int[] maze_row : myMap) {
            for (int maze_entry : maze_row) {
                switch (maze_entry) {
                    case 0:
                        System.out.print("_"); /*rien*/
                        break;
                    case -1:
                        System.out.print("*"); /*Chemin*/
                        break;
                    default:
                        System.out.print("#"); /*Mur*/
                }
            }
            System.out.println();
        }
    }

}