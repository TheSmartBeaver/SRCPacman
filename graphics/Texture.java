package SRCPacman.graphics;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

//UTILISER Paint.NET pour faire grille textures

public class Texture {
    int width, height;
    int id;

    public static  Texture tiles = loadTexture("res/textures.png");

    public Texture(int width, int height, int id) {
        this.width = width;
        this.height = height;
        this.id = id;
    }

    public static Texture loadTexture(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int w = image.getWidth();
        int h = image.getWidth();

        int[] pixels = new int[w * h];
        image.getRGB(0,0,w,h,pixels,0,w);

        ByteBuffer buffer = BufferUtils.createByteBuffer(w * h * 4); /* mult par 4 car r g b alpha*/

        for (int y=0; y<w; y++){
            for (int x=0; x<h; x++){
                int i = pixels[x + y * w];
                buffer.put((byte)((i >> 16) & 0xFF)); //r
                buffer.put((byte)((i >> 8) & 0xFF)); //g
                buffer.put((byte)((i) & 0xFF)); //b
                buffer.put((byte)((i >> 24) & 0xFF)); //alpha
            }
        }
        buffer.flip();

        int id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP); /*Place texture*/
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA4, w,h,0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        return new Texture(w, h, id);

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id); /*Placer texture par rapport à des coordonnées ?*/
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
