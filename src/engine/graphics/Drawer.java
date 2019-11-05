package src.engine.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;

/**
 * Created by Vincent on 05/11/2019.
 */
public class Drawer {

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

}
