package src.engine.ai;

import src.engine.input.Input;
import src.entities.moving.generic.Direction;
import src.entities.moving.generic.MovingEntity;
import src.entities.space.generic.TileMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 19/11/2019.
 */
public class AIUtil {

    static List<Input> determinePossibleDirections(MovingEntity entity, TileMap tileMap) {
        List<Input> possibleInputs = new ArrayList<>();
        int entityTileX = entity.getTileX();
        int entityTileY = entity.getTileY();
        switch (entity.getCurrentDirection()) {
            case UP:
            {
                if (!tileMap.get(entityTileY - 1, entityTileX).isWall()) {
                    possibleInputs.add(Input.UP);
                }
                if (!tileMap.get(entityTileY, entityTileX - 1).isWall()) {
                    possibleInputs.add(Input.LEFT);
                }
                if (!tileMap.get(entityTileY, entityTileX + 1).isWall()) {
                    possibleInputs.add(Input.RIGHT);
                }
                break;
            }
            case DOWN:
            {
                if (!tileMap.get(entityTileY + 1, entityTileX).isWall()) {
                    possibleInputs.add(Input.DOWN);
                }
                if (!tileMap.get(entityTileY, entityTileX - 1).isWall()) {
                    possibleInputs.add(Input.LEFT);
                }
                if (!tileMap.get(entityTileY, entityTileX + 1).isWall()) {
                    possibleInputs.add(Input.RIGHT);
                }
                break;
            }
            case LEFT:
            {
                if (!tileMap.get(entityTileY + 1, entityTileX).isWall()) {
                    possibleInputs.add(Input.DOWN);
                }
                if (!tileMap.get(entityTileY, entityTileX - 1).isWall()) {
                    possibleInputs.add(Input.LEFT);
                }
                if (!tileMap.get(entityTileY - 1, entityTileX).isWall()) {
                    possibleInputs.add(Input.UP);
                }
                break;
            }
            case RIGHT:
            {
                if (!tileMap.get(entityTileY - 1, entityTileX).isWall()) {
                    possibleInputs.add(Input.UP);
                }
                if (!tileMap.get(entityTileY, entityTileX + 1).isWall()) {
                    possibleInputs.add(Input.RIGHT);
                }
                if (!tileMap.get(entityTileY + 1, entityTileX).isWall()) {
                    possibleInputs.add(Input.DOWN);
                }
                break;
            }
        }
        return possibleInputs;
    }

    static Direction convertInputToDirection(Input input) {
        switch (input) {
            case UP:
            {
                return Direction.UP;
            }
            case DOWN:
            {
                return Direction.DOWN;
            }
            case LEFT:
            {
                return Direction.LEFT;
            }
            case RIGHT:
            {
                return Direction.RIGHT;
            }
            default:
                return null;
        }
    }

    static boolean isEntityAtIntersection(MovingEntity entity, TileMap tileMap) {
        //return (determinePossibleDirections(entity, tileMap).size() >= 1);
        int entityTileX = entity.getTileX();
        int entityTileY = entity.getTileY();
        switch (entity.getCurrentDirection()) {
            case UP:
            case DOWN:
            {
                if (!tileMap.get(entityTileY, entityTileX - 1).isWall() || !tileMap.get(entityTileY, entityTileX + 1).isWall()) {
                    return true;
                }
                break;
            }
            case LEFT:
            case RIGHT:
            {
                if (!tileMap.get(entityTileY - 1, entityTileX).isWall() || !tileMap.get(entityTileY + 1, entityTileX).isWall()) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    static void reverseEntityDirection(MovingEntity movingEntity) {
        switch (movingEntity.getCurrentDirection()) {
            case UP:
            {
                movingEntity.setCurrentDirection(Direction.DOWN);
                break;
            }
            case DOWN:
            {
                movingEntity.setCurrentDirection(Direction.UP);
                break;
            }
            case LEFT:
            {
                movingEntity.setCurrentDirection(Direction.RIGHT);
                break;
            }
            case RIGHT:
            {
                movingEntity.setCurrentDirection(Direction.LEFT);
                break;
            }
        }
    }

}
