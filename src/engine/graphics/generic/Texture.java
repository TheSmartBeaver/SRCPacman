package src.engine.graphics.generic;

/**
 * Created by Vincent on 06/11/2019.
 */
import org.lwjgl.BufferUtils;
import src.entities.moving.specific.PacmanSprite;
import src.entities.space.specific.TileSprite;

import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

//UTILISER Paint.NET pour faire grille textures

public class Texture {
    private int width, height;
    private int id;
    private int spriteSize;

    private List<Sprite> sprites = new ArrayList<>(); /*liste contenant toutes les coordonnées de sprites*/

    public Texture(String pngPath, int spriteSize, Object enumSprite) {
        this.spriteSize = spriteSize;
        loadTexture(pngPath); /*load png sprite*/
        setSprites(enumSprite); /*remplit liste "sprites"*/
        System.out.println(sprites); //aff coordonnées orthonormées
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

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP); /*Place fixedEntitiesTexture*/
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA4, w,h,0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

    }

    /*Parcours toutes les sprites, celles décrites dans enum TileSprite*/
    private void setSprites(Object enumSprite) {
        int x = 0;
        int y = 0;
        int nbSprite = 0;
        int nbTexturesPerRow = width / spriteSize;
        if(enumSprite == TileSprite.class) {
            nbSprite = TileSprite.values().length;
        }
        if(enumSprite == PacmanSprite.class) {
            nbSprite = PacmanSprite.values().length;
        }

        for (int spriteIndex = 0 ; spriteIndex < nbSprite ; ++spriteIndex) {
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
        glBindTexture(GL_TEXTURE_2D, id); /*Placer fixedEntitiesTexture par rapport � des coordonn�es ?*/
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
