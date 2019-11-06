package SRCPacman.game.level.tiles;

import SRCPacman.engine.Component;
import SRCPacman.game.Game;
import SRCPacman.graphics.Renderer;

import java.util.Random;

public class Tile {
    public int x, y;
    public int size = 16;

    float[] color;

    Tiles tile;

    public enum Tiles {
        GRASS, ROCK, WATER
    }

    public Tile(int x, int y, Tiles tile) {
        this.x = x;
        this.y = y;
        this.tile = tile;

        if(tile == Tiles.GRASS) color = new float[]{0.1f, 0.5f, 0, 1};
        if(tile == Tiles.ROCK) color = new float[]{0.5f, 0.5f, 0.5f, 1};
        if(tile == Tiles.WATER) color = new float[]{0.5f, 0.5f, 0.1f, 1};

    }

    public void render(){
        /*Début optimisation :  On ne render pas les tiles hors de la fenêtre*/
        float x0 = x + Game.xScroll / 16;
        float y0 = y + Game.yScroll / 16;   /*Mettre des +1 -1 pour voir si tiles autour ne s'affiche pas*/

        float x1 = x + 1 + Game.xScroll / 16;
        float y1 = y + 1 + Game.yScroll / 16;

        if(x1 < 0 || y1 < 0 || x0 > Component.width/16 || y0 > Component.height/16) return;;
        /*Fin optimisation*/

        Renderer.renderQuad(x*size,y*size,size,size, color);
    }
}
