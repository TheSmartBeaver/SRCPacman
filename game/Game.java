package SRCPacman.game;

import SRCPacman.engine.Component;
import SRCPacman.game.level.Level;
import SRCPacman.graphics.Renderer;

public class Game {

    Level level;
    public Game() {
        level = new Level(10,20);
    }

    public void init(){
        level.init();
    }

    public void update(){
        level.update();
    }

    public void render(){
        level.render();
    }
}
