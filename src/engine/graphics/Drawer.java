package src.engine.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;

/**
 * Created by Vincent on 05/11/2019.
 */
public class Drawer {

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
}
