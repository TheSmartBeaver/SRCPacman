package SRCPacman.graphics;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    public static void quadData(float x, float y, int w, int h, float[] color) {
        glColor4f(color[0], color[1], color[2], color[3]);
        glVertex2f(x,y);
        glVertex2f(x+w,y);
        glVertex2f(x+w,y+h);
        glVertex2f(x,y+h);
    }

    public static void renderQuad(float x, float y, int w, int h, float[] color) {
        glBegin(GL_QUADS);
        Renderer.quadData(x, y, w, h, color);
        glEnd();
    }

}