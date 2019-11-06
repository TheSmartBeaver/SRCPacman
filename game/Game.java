package SRCPacman.game;

import SRCPacman.engine.Component;
import SRCPacman.graphics.Renderer;

public class Game {
    public Game() {

    }

    public void init(){

    }

    public void update(){

    }

    public void render(){
        for (int x=0; x < Component.width /16; x++){
            for (int y=0; y < Component.height /16; y++){
                Renderer.renderQuad(x*17, y*17, 16, 16, new float[]{1,0,1,1}); /*Go voir spriteSheets*/

            }
        }
    }
}
