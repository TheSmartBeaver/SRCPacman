package SRCPacman.game.level.tiles;

import SRCPacman.engine.Component;
import SRCPacman.game.Game;
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
