package SRCPacman.game;

import SRCPacman.engine.Component;
import SRCPacman.game.level.Level;
import SRCPacman.graphics.Renderer;
import org.lwjgl.opengl.GL11;

import javax.swing.*;

public class Game {

    Level level;

    public static float xScroll, yScroll;

    public Game() {
        level = new Level(Component.width/16,Component.width/16);
    }

    public void init(){
        level.init();
    }

    public void translateView(float xa, float ya){
        xScroll += xa;
        yScroll += ya;
    }

    public void update(){
        translateView(-0.5f, -0.5f); /*Scroll/Translation*/
        level.update();
    }

    public void render(){
        GL11.glTranslatef(xScroll,yScroll,0); /*Scroll/Translation*/
        level.render();
    }
}
