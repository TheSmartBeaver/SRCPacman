package SRCPacman.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;

import javax.swing.text.html.CSS;

import static org.lwjgl.opengl.GL11.*;


public class Component {
    private boolean running = false;
    private static String windows_title = "PACMAN";
    private static int scale = 3; /*Car on aura pas besoin d'aussi grosse résolution ?? */
    private static int widdth = 720/scale;
    private static int height = 480/scale;

    DisplayMode mode = new DisplayMode(widdth*scale, height*scale);

    public Component(){
        try {


            Display.setDisplayMode(mode);
            Display.setResizable(true);
            Display.setFullscreen(false);
            Display.setTitle(windows_title);
            Display.create();

            initGL();
        }
        catch (LWJGLException e){
            e.printStackTrace();
        }
    }

    private  void initGL() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluOrtho2D(0,widdth,height,0);
        glMatrixMode(GL_MODELVIEW); /*On revient à la vue d'origine*/
        glLoadIdentity();
    }

    public void start(){
        running = true;
        loop();
    }

    public void stop(){
        running = false;
    }

    public void exit(){
        Display.destroy();
        System.exit(0);
    }

    public void loop(){
        while (running == true){
            if(Display.isCloseRequested()) stop(); /*Si on appuie sur croix fermeture*/
            Display.update();

            render();
        }
        exit();
    }

    public void render(){
        
    }

    public static void main(String args[]){
        Component main = new Component();
        main.start();
    }
}
