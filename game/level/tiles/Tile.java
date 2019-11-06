package SRCPacman.game.level.tiles;

import SRCPacman.engine.Component;
import SRCPacman.game.Game;
import SRCPacman.graphics.Renderer;
import SRCPacman.graphics.Texture;
import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

public class Tile {
    public int x, y;
    int xo = 0 , yo = 0;
    public int size = 16;

    float[] color = new float[]{1,1,1,1};

    Tiles tile;

    public enum Tiles {
        GRASS, ROCK, WATER
    }

    public Tile(int x, int y, Tiles tile) {
        this.x = x;
        this.y = y;
        this.tile = tile;

        if(tile == Tiles.GRASS) xo = 0;
        if(tile == Tiles.ROCK) xo = 1;
        if(tile == Tiles.WATER)xo = 2;

    }

    public void render(){
        /*Début optimisation :  On ne render pas les tiles hors de la fenêtre*/
        float x0 = x + Game.xScroll / 16;
        float y0 = y + Game.yScroll / 16;   /*Mettre des +1 -1 pour voir si tiles autour ne s'affiche pas*/

        float x1 = x + 1 + Game.xScroll / 16;
        float y1 = y + 1 + Game.yScroll / 16;

        if(x1 < 0 || y1 < 0 || x0 > Component.width/16 || y0 > Component.height/16) return;;
        /*Fin optimisation*/
        Texture.tiles.bind();
        glBegin(GL_QUADS);
            //color = new float[]{1,1,1,1};
            Renderer.quadData(x*size,y*size,size,size, color, xo, yo);
            glEnd();
        Texture.tiles.unbind();

    }
}
