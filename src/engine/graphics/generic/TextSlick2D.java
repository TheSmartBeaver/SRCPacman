package src.engine.graphics.generic;



import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.io.InputStream;

public class TextSlick2D {

    private static Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
    private static TrueTypeFont font = new TrueTypeFont(awtFont, true);

    private static Font awtFont2 = new Font("Verdana", Font.BOLD, 24);
    private static TrueTypeFont font2 = new TrueTypeFont(awtFont, true);



    public static void drawText(int x, int y, String Text, Color c){
        font2.drawString(x,y,"",c);
        //font.drawString(x,y,"",c);
        font.drawString(x,y,Text,c);
    }
}