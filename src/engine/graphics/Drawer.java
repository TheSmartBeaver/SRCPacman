package src.engine.graphics;

import src.Level;
import src.Main;
import src.UserParams;
import src.entities.moving.generic.Direction;
import src.entities.moving.generic.MovingEntity;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;

/**
 * Created by Vincent on 05/11/2019.
 */
public class Drawer {

    public static int time = 0;
    public static boolean switchAnim = false;

    public static void debugDrawPoint(int x, int y, Color color) {
        glColor3f(color.r, color.g, color.b);
        glBegin(GL_POINTS);
        glVertex2f(x,y);
        glEnd();
    }

    /*Draw Rectangle*/
    public static void drawRect(int x, int y, int width, int height, Color color) {
        glColor3f(color.r, color.g, color.b);
        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();
    }

    public static void drawCircle(int xCenter, int yCenter, int radius, int precision, Color color) {
        glColor3f(color.r, color.g, color.b);
        glBegin(GL_LINE_LOOP);
        for (int i = 0; i < precision ; ++i) {
            double theta = 2.0d * Math.PI * (double)i / (double)precision;
            double xAngle = radius * Math.cos(theta);
            double yAngle = radius * Math.sin(theta);

            glVertex2d(xAngle + xCenter, yAngle + yCenter);
        }
        glEnd();
    }

    /*xSprite, ySprite coordonnées orthonormées sprite dans png*/
    public static void drawSprite(float x, float y, int widthSprite, int heightSprite, int xSprite, int ySprite) {
        glBegin(GL_QUADS);
        glColor4f(1,1,1,1);
        glTexCoord2f((0 + xSprite) / 32.0f,(0 + ySprite) /32.0f); glVertex2f(x,y); /*1 pour 100% de la texture*/
        glTexCoord2f((1 + xSprite) / 32.0f,(0 + ySprite) /32.0f); glVertex2f(x+widthSprite,y);
        glTexCoord2f((1 + xSprite) / 32.0f,(1 + ySprite) /32.0f); glVertex2f(x+widthSprite,y+heightSprite);
        glTexCoord2f((0 + xSprite) / 32.0f,(1 + ySprite) /32.0f); glVertex2f(x,y+heightSprite);
        glEnd();
    }

    private static void setSwitchAnim(){
        if(Main.time%15 == 0)
                switchAnim = true;
        if(Main.time%30 == 0)
                switchAnim = false;

    }

    public static void drawPacmanSprite(MovingEntity entity, int movingEntitySpriteLength, Level level) {

        setSwitchAnim();

        if(switchAnim) {
            UserParams.pacmanTexture.bind();
            Drawer.drawSprite((int) entity.getPosX() - movingEntitySpriteLength / 2, (int) entity.getPosY() - movingEntitySpriteLength / 2, level.getTileWidth(), level.getTileHeight(), 8, 0);
            UserParams.pacmanTexture.unbind();
        }
            else{
                if (entity.getCurrentDirection() == Direction.LEFT) {
                UserParams.pacmanTexture.bind();
                Drawer.drawSprite((int) entity.getPosX() - movingEntitySpriteLength / 2, (int) entity.getPosY() - movingEntitySpriteLength / 2, level.getTileWidth(), level.getTileHeight(), 4, 0);
                UserParams.pacmanTexture.unbind();
            }
            if (entity.getCurrentDirection() == Direction.RIGHT) {
                UserParams.pacmanTexture.bind();
                Drawer.drawSprite((int) entity.getPosX() - movingEntitySpriteLength / 2, (int) entity.getPosY() - movingEntitySpriteLength / 2, level.getTileWidth(), level.getTileHeight(), 0, 0);
                UserParams.pacmanTexture.unbind();
            }
            if (entity.getCurrentDirection() == Direction.UP) {
                UserParams.pacmanTexture.bind();
                Drawer.drawSprite((int) entity.getPosX() - movingEntitySpriteLength / 2, (int) entity.getPosY() - movingEntitySpriteLength / 2, level.getTileWidth(), level.getTileHeight(), 6, 0);
                UserParams.pacmanTexture.unbind();
            }
            if (entity.getCurrentDirection() == Direction.DOWN) {
                UserParams.pacmanTexture.bind();
                Drawer.drawSprite((int) entity.getPosX() - movingEntitySpriteLength / 2, (int) entity.getPosY() - movingEntitySpriteLength / 2, level.getTileWidth(), level.getTileHeight(), 2, 0);
                UserParams.pacmanTexture.unbind();
            }
        }
    }

}
