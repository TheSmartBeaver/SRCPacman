package src.engine.graphics;

/**
 * Created by Vincent on 06/11/2019.
 */
import javafx.util.Pair;
import org.lwjgl.BufferUtils;
import src.UserParams;
import src.entities.space.TileSprite;

import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

//UTILISER Paint.NET pour faire grille textures

public class Texture {
    private int width, height;
    private int id;
    private int spriteSize;

    private List<Sprite> sprites = new ArrayList<>();

    public Texture(String pngPath, int spriteSize) {
        this.spriteSize = spriteSize;
        loadTexture(pngPath);
        setSprites();
        System.out.println(sprites);
    }

    private void loadTexture(String path) {
        BufferedImage image = null;
        try {
            System.out.println(path);
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int w = image.getWidth();
        width = w;
        int h = image.getWidth();
        height = h;

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
        this.id = id;

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP); /*Place texture*/
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA4, w,h,0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

    }

    private void setSprites() {
        int x = 0;
        int y = 0;
        int nbTexturesPerRow = width / spriteSize;
        for (int spriteIndex = 0 ; spriteIndex < TileSprite.values().length ; ++spriteIndex) {
            sprites.add(new Sprite(x,y));
            ++x;
            if (x == nbTexturesPerRow) {
                x = 0;
                ++y;
            }
        }
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

    public List<Sprite> getSprites() {
        return sprites;
    }

    public int getId() {
        return id;
    }
}
