package SRCPacman.game.level.tiles;

import SRCPacman.graphics.Renderer;

import java.util.Random;

public class Tile {
    public int x, y;
    public int size = 16;

    float[] color;

    Random random = new Random();

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;

        color = new float[]{random.nextFloat(),random.nextFloat(),random.nextFloat(),1};
    }

    public void render(){
        Renderer.renderQuad(x*size,y*size,size,size, color);
    }
}
