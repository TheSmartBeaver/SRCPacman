package SRCPacman.game.level;

import SRCPacman.game.level.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

public class Level {
    public int width, height;

    List<Tile> tiles = new ArrayList<Tile>();

    public Level(int width, int height) {
        this.width = width;
        this.height = height;

        generate();
    }

    public void generate(){
        for (int x=0;x<width;x++){
            for (int y=0;y<height;y++){
                tiles.add(new Tile(x,y, Tile.Tiles.WATER));
                if(Math.random() > 0.8f) {
                    tiles.add(new Tile(x,y, Tile.Tiles.GRASS));
                }
                if(Math.random() > 0.95f) {
                    tiles.add(new Tile(x,y, Tile.Tiles.ROCK));
                }
            }
        }
    }

    public void init(){

    }

    public void update(){

    }

    public void render(){
        for (Tile tile : tiles){
            tile.render();
        }
    }
}
