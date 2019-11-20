package src;

import src.engine.graphics.GameRenderer;
import src.engine.input.GameInput;
import src.engine.input.Input;
import src.engine.physics.MovementPhysics;
import src.engine.ai.MovingRandom;
import src.entities.fixed.Cherry;
import src.entities.fixed.Strawberry;
import src.entities.fixed.TileContent;
import src.entities.moving.*;
import src.entities.space.TileMap;
import src.entities.space.TileSprite;
import src.entities.space.TileTeleport;
import src.loaders.LevelLoader;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * Created by Vincent on 02/11/2019.
 */
public class GameMain {

    private static boolean newLevel = true;
    private static float time=0;
    private static int presedentStrawberry=0;

    //TODO : méthode de test, à mettre autre part ou à enlever
    private static void initEntitiesPosition() {
        TileMap tileMap = GameState.currentLevelPlayed.getTileMap();
        int rowCount = tileMap.getRowCount();
        int columnCount = tileMap.getColumnCount();
        for (int rowIndex = 0 ; rowIndex < rowCount ; ++rowIndex) {
            for (int columnIndex = 0 ; columnIndex < columnCount ; ++columnIndex) {
                if (tileMap.get(rowIndex, columnIndex).isPacmanSpawnTile()) {
                    for (MovingEntity movingEntity : GameState.currentEntities) {
                        if (movingEntity.getEntityType() == MovingEntityType.PACMAN) {
                            movingEntity.setTileX(columnIndex);
                            movingEntity.setTileY(rowIndex);
                            movingEntity.setPosX(
                                    GameState.currentLevelPlayed.getLevelScreenOffsetLeft() +
                                    GameState.currentLevelPlayed.getTileWidth() * columnIndex +
                                    GameState.currentLevelPlayed.getTileWidth() / 2
                            );
                            movingEntity.setPosY(
                                    GameState.currentLevelPlayed.getLevelScreenOffsetUp() +
                                    GameState.currentLevelPlayed.getTileHeight() * rowIndex +
                                    GameState.currentLevelPlayed.getTileHeight() / 2
                            );
                            movingEntity.setTileX(columnIndex);
                            movingEntity.setTileY(rowIndex);
                            System.out.println(movingEntity.getPosX() + " " + movingEntity.getPosY());
                        }
                    }
                }
                else if (tileMap.get(rowIndex, columnIndex).isGhostSpawnTile()) {
                    for (MovingEntity movingEntity : GameState.currentEntities) {
                        if (movingEntity.getEntityType() == MovingEntityType.GHOST) {
                            movingEntity.setTileX(columnIndex);
                            movingEntity.setTileY(rowIndex);
                            movingEntity.setPosX(
                                    GameState.currentLevelPlayed.getLevelScreenOffsetLeft() +
                                            GameState.currentLevelPlayed.getTileWidth() * columnIndex +
                                            GameState.currentLevelPlayed.getTileWidth() / 2
                            );
                            movingEntity.setPosY(
                                    GameState.currentLevelPlayed.getLevelScreenOffsetUp() +
                                            GameState.currentLevelPlayed.getTileHeight() * rowIndex +
                                            GameState.currentLevelPlayed.getTileHeight() / 2
                            );
                            movingEntity.setTileX(columnIndex);
                            movingEntity.setTileY(rowIndex);
                            //TODO : WARNING CODE DE TEST A PARTIR DE MAINTENANT A ENLEVER IMPERATIVEMENT PLUS TARD
                            movingEntity.setCurrentDirection(Direction.LEFT);
                            ArrayDeque<Input> inputInitPourTest = new ArrayDeque<>();
                            inputInitPourTest.push(Input.UP);
                            ((Ghost)movingEntity).setInputs(inputInitPourTest);
                        }
                    }
                }
            }
        }
    }

    public static void update(double deltaTime) {

        if (newLevel) {
            GameState.currentLevelPlayed = LevelLoader.levels.get(0);
            GameState.currentEntities.add(new Pacman(1,3.0f));
            GameState.currentEntities.add(new Pacman(2,3.0f));
            GameState.currentEntities.add(new Ghost(3.0f, null, new MovingRandom(GameState.currentLevelPlayed.getTileMap())));
            GameState.currentEntities.add(new Ghost(5.0f, null, new MovingRandom(GameState.currentLevelPlayed.getTileMap())));
            GameState.currentEntities.add(new Ghost(1.0f, null, new MovingRandom(GameState.currentLevelPlayed.getTileMap())));
            GameState.currentEntities.add(new Ghost(10.0f, null, new MovingRandom(GameState.currentLevelPlayed.getTileMap())));
            initEntitiesPosition();
            newLevel = false;
        }

        List<Pacman> pacmans = MovingEntity.findPacmanEntities(GameState.currentEntities);
        for (Pacman pacman : pacmans) {
            if (pacman.getId() == 1) {
                pacman.setInput(GameInput.getInputFirst());
            }
            else if (pacman.getId() == 2) {
                pacman.setInput(GameInput.getInputSecond());
            }
        }

        MovementPhysics.updateEntitiesPositions(deltaTime, GameState.currentEntities, GameState.currentLevelPlayed);

        //TODO : a voir ou on met ce code
        TileMap tileMap = GameState.currentLevelPlayed.getTileMap();
        for (MovingEntity entity : GameState.currentEntities) {
            if (entity.getEntityType() == MovingEntityType.PACMAN) {
                if (entity.isInMiddleOfTile()) {
                    TileContent tileContent = tileMap.get(entity.getTileY(), entity.getTileX()).getContent();
                    if (tileContent != null) {
                        tileContent.execute((Pacman)entity);
                        System.out.println(((Pacman)entity).getScore());
                        tileMap.get(entity.getTileY(), entity.getTileX()).setContent(null);
                    }
                }
            }
        }
        time += deltaTime;
        if (time>60000){
            Vector<Pair> case_vides=new Vector<Pair>();
            for (int i=0;i<tileMap.getRowCount();i++) {
                for (int j = 0; j < tileMap.getColumnCount(); j++) {
                    if (!tileMap.get(i, j).isGhostSpawnTile() && !tileMap.get(i, j).isTeleportTile() && !tileMap.get(i, j).isWall() && tileMap.get(i, j).getContent() == null) {
                        case_vides.add(new Pair(i, j));
                    }
                }
            }
            Random rand=new Random();

            if (case_vides.size() != 0) {
                int nb_rand = rand.nextInt(case_vides.size());
                Pair pair_choisi=case_vides.get(nb_rand);
                if (presedentStrawberry<3) {
                    tileMap.get(pair_choisi.getX(), pair_choisi.getY()).setContent(new Strawberry());
                    tileMap.get(pair_choisi.getX(), pair_choisi.getY()).getContent().setSprite(TileSprite.STRAWBERRY);
                    presedentStrawberry++;
                }
                else{
                    tileMap.get(pair_choisi.getX(), pair_choisi.getY()).setContent(new Cherry());
                    tileMap.get(pair_choisi.getX(), pair_choisi.getY()).getContent().setSprite(TileSprite.CHERRY);
                    presedentStrawberry=0;
                }
            }
            time=0;
        }
    }

    public static void render(){

        //TODO : idée : faire une classe GlobalRenderer qui prend tous les paramètres qu'il faut et appeler dedans les sous-renderers ?
        GameRenderer.renderLevel(GameState.currentLevelPlayed, GameState.currentEntities);

    }
}
