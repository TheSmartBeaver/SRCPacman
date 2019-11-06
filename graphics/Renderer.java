package SRCPacman.graphics;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    public static void quadData(float x, float y, int w, int h, float[] color, int xo, int yo) {
        glColor4f(color[0], color[1], color[2], color[3]);
        glTexCoord2f((0 + xo) / 32.0f,(0 + yo) /32.0f); glVertex2f(x,y); /*1 pour 100% de la texture*/
        glTexCoord2f((1 + xo) / 32.0f,(0 + yo) /32.0f); glVertex2f(x+w,y);
        glTexCoord2f((1 + xo) / 32.0f,(1 + yo) /32.0f); glVertex2f(x+w,y+h);
        glTexCoord2f((0 + xo) / 32.0f,(1 + yo) /32.0f); glVertex2f(x,y+h);
    }

    public static void renderQuad(float x, float y, int w, int h, float[] color, int xo, int yo) {
        glBegin(GL_QUADS);
        Renderer.quadData(x, y, w, h, color, xo, yo); /* o -> offset*/
        glEnd();
    }

}