package src;

import org.newdawn.slick.Color;
import src.engine.ai.AStarStrategy;
import src.engine.graphics.GameRenderer;
import src.engine.graphics.generic.TextSlick2D;
import src.engine.input.GameInput;
import src.engine.input.Input;
import src.engine.physics.specific.GamePhysicsManager;
import src.engine.ai.MovingRandom;
import src.entities.fixed.specific.*;
import src.entities.moving.generic.Direction;
import src.entities.moving.generic.MovingEntity;
import src.entities.moving.specific.Ghost;
import src.entities.moving.specific.MovingEntityType;
import src.entities.moving.specific.Pacman;
import src.entities.space.generic.TileMap;
import src.entities.space.specific.TileSprite;
import src.loaders.LevelLoader;

import java.util.*;

import static java.lang.System.exit;

/**
 * Created by Vincent on 02/11/2019.
 */
public class GameMain {

    private static boolean newLevel = true;
    private static float time=0;
    private static int precedentStrawberry =0;
    private static int currentLevel = -1;

    private List<Pacman> deadPacmans = new ArrayList<>();

    public static List<Pacman> findPacmanEntities(List<MovingEntity> entities) { /*Retourne toutes les instances de PacMan*/
        List<Pacman> pacmans = new ArrayList<>();
        for (MovingEntity entity : entities) {
            if (entity.getEntityType() == MovingEntityType.PACMAN) {
                pacmans.add((Pacman)entity);
            }
        }
        return pacmans;
    }

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
                            GameState.currentLevelPlayed.setPacmanXSpawn(GameState.currentLevelPlayed.getLevelScreenOffsetLeft() + GameState.currentLevelPlayed.getTileWidth() * columnIndex + GameState.currentLevelPlayed.getTileWidth() / 2);
                            movingEntity.setPosY(
                                    GameState.currentLevelPlayed.getLevelScreenOffsetUp() +
                                    GameState.currentLevelPlayed.getTileHeight() * rowIndex +
                                    GameState.currentLevelPlayed.getTileHeight() / 2
                            );
                            GameState.currentLevelPlayed.setPacmanYSpawn(GameState.currentLevelPlayed.getLevelScreenOffsetUp() + GameState.currentLevelPlayed.getTileHeight() * rowIndex + GameState.currentLevelPlayed.getTileHeight() / 2);
                            movingEntity.setTileX(columnIndex);
                            movingEntity.setTileY(rowIndex);
                            System.out.println(movingEntity.getPosX() + " " + movingEntity.getPosY() + " " + movingEntity.getTileX() + " " + movingEntity.getTileY());
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
                            GameState.currentLevelPlayed.setGhostXSpawn(GameState.currentLevelPlayed.getLevelScreenOffsetLeft() + GameState.currentLevelPlayed.getTileWidth() * columnIndex + GameState.currentLevelPlayed.getTileWidth() / 2);

                            movingEntity.setPosY(
                                    GameState.currentLevelPlayed.getLevelScreenOffsetUp() +
                                            GameState.currentLevelPlayed.getTileHeight() * rowIndex +
                                            GameState.currentLevelPlayed.getTileHeight() / 2
                            );
                            GameState.currentLevelPlayed.setGhostYSpawn(GameState.currentLevelPlayed.getLevelScreenOffsetUp() + GameState.currentLevelPlayed.getTileHeight() * rowIndex + GameState.currentLevelPlayed.getTileHeight() / 2);

                            movingEntity.setTileX(columnIndex);
                            movingEntity.setTileY(rowIndex);
                            //TODO : WARNING CODE DE TEST A PARTIR DE MAINTENANT A ENLEVER IMPERATIVEMENT PLUS TARD
                            movingEntity.setCurrentDirection(Direction.LEFT);
                            ((Ghost)movingEntity).setInput(Input.UP);
                        }
                    }
                }
            }
        }
    }

    public static void update(double deltaTime) {

        if (newLevel) {
            GameState.currentLevelPlayed = LevelLoader.levels.get(++currentLevel);
            System.out.println("On joue le niveau numéro : "+currentLevel);
            GameState.currentEntities.add(new Pacman(1,3.0f));
            GameState.currentEntities.add(new Pacman(2,3.0f));
            //GameState.currentEntities.add(new Ghost(2.5f, null, new MovingRandom(GameState.currentLevelPlayed.getTileMap())));
            //GameState.currentEntities.add(new Ghost(2.5f, null, new MovingRandom(GameState.currentLevelPlayed.getTileMap())));
            GameState.currentEntities.add(new Ghost(2.5f, null, new MovingRandom(GameState.currentLevelPlayed.getTileMap())));
            GameState.currentEntities.add(new Ghost(2.5f, null, new MovingRandom(GameState.currentLevelPlayed.getTileMap())));
            Pacman unPacman = findPacmanEntities(GameState.currentEntities).get(0);
            Pacman deuxiemePacman = findPacmanEntities(GameState.currentEntities).get(1);
            Ghost chasingGhost = new Ghost(2.5f, unPacman, new AStarStrategy(GameState.currentLevelPlayed.getTilesForA()));
            Ghost chasingGhost2 = new Ghost(2.5f, deuxiemePacman, new AStarStrategy(GameState.currentLevelPlayed.getTilesForA()));
            chasingGhost.isAChaser = true;
            chasingGhost2.isAChaser = true;
            GameState.currentEntities.add(chasingGhost);
            GameState.currentEntities.add(chasingGhost2);


            initEntitiesPosition();
            newLevel = false;

        }



        List<Pacman> pacmans = findPacmanEntities(GameState.currentEntities);

        if(pacmans.size()==0) {
            TextSlick2D.drawText(0, 120, "PERDU", Color.red);
        }

        for (Pacman pacman : pacmans) {
            if(pacman.getLiveCount() < 1)
            GameState.currentEntities.remove(pacman);
        }

        pacmans = findPacmanEntities(GameState.currentEntities);

        for (Pacman pacman : pacmans) {
            if (pacman.getId() == 1) {
                pacman.setInput(GameInput.getInputFirst());
            }
            else if (pacman.getId() == 2) {
                pacman.setInput(GameInput.getInputSecond());
            }
        }

        GamePhysicsManager.updateEntitiesPositions(deltaTime, GameState.currentEntities, GameState.currentLevelPlayed);

        //TODO : a voir ou on met ce code
        TileMap tileMap = GameState.currentLevelPlayed.getTileMap();
        for (MovingEntity entity : GameState.currentEntities) {
            if (entity.getEntityType() == MovingEntityType.PACMAN) {
                Pacman pacmanEntity = (Pacman)entity;
                if (pacmanEntity.isInMiddleOfTile()) {
                    TileContentPacman tileContent = (TileContentPacman)tileMap.get(pacmanEntity.getTileY(), pacmanEntity.getTileX()).getContent();
                    if (tileContent != null) {
                        tileContent.execute(pacmanEntity);
                        //System.out.println((pacmanEntity).getScore());
                        if(tileContent.getContentType() == TileContentType.BERRY)
                            GameState.currentLevelPlayed.decrementNbBerryForWin();
                        tileMap.get(pacmanEntity.getTileY(), pacmanEntity.getTileX()).setContent(null); /*On enlève les fruits que le Pacman mange.*/
                    }
                }
                List<PowerUp> tmpPower = new ArrayList<>(pacmanEntity.getActivePowerUps());
                if (pacmanEntity.getId() == 1)
                    System.out.println(tmpPower.size());
                for (PowerUp currentPowerUp: tmpPower) {
                    currentPowerUp.decDurationMS(deltaTime);
                    if (currentPowerUp.isFinished()) {
                        currentPowerUp.end(pacmanEntity);
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
                if (precedentStrawberry <3) {
                    tileMap.get(pair_choisi.getX(), pair_choisi.getY()).setContent(new Strawberry());
                    tileMap.get(pair_choisi.getX(), pair_choisi.getY()).getContent().setSprite(TileSprite.STRAWBERRY);
                    precedentStrawberry++;
                }
                else{
                    tileMap.get(pair_choisi.getX(), pair_choisi.getY()).setContent(new Cherry());
                    tileMap.get(pair_choisi.getX(), pair_choisi.getY()).getContent().setSprite(TileSprite.CHERRY);
                    precedentStrawberry =0;
                }
            }
            time=0;
        }
        if(GameState.currentLevelPlayed.getNbBerryForWin() == 0) /*On va passer au niveau suivant si tout mangé*/
            goNextLevel();

    }

    public static void render(){

        //TODO : idée : faire une classe GlobalRenderer qui prend tous les paramètres qu'il faut et appeler dedans les sous-renderers ?
        GameRenderer.renderLevel(GameState.currentLevelPlayed, GameState.currentEntities);

    }

    public static void goNextLevel(){
        if(LevelLoader.getLevels().size() == currentLevel+1) {
            System.out.println("Tous les niveaux ont été fini");
            exit(1);
        }
        newLevel = true;
        GameState.currentEntities = new ArrayList<>();;
    }
}
