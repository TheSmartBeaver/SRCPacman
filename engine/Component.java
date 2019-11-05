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

    int time = 0;

    public static boolean tick = false;
    public static boolean render = false;


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

        long before = System.nanoTime();
        double elapsed = 0;
        double nanoSeconds = 1000000000.0 / 60.0;

        long timer = System.currentTimeMillis();

        int ticks = 0; /*Nombre de fois qu'on update en 1s*/
        int frames = 0;

        while (running == true){
            if(Display.isCloseRequested()) stop(); /*Si on appuie sur croix fermeture*/
            Display.update();

            tick = false;
            render = false;

            long now = System.nanoTime();
            elapsed = now - before;

            if(elapsed > nanoSeconds){
                before += nanoSeconds;
                tick = true;
                ticks++;
            } else {
                render = true;
                frames++;
            }

            if(tick) update();
            if(render) render();

            if (System.currentTimeMillis() - timer > 1000){
                timer +=1000;
                Display.setTitle(windows_title+" ticks: " + ticks + "fps: "+ frames);
                ticks = 0;
                frames = 0;
            }
        }
        exit();
    }

    public void update() {
        time++;
    }

    public void render(){
        glClear(GL_COLOR_BUFFER_BIT); /*On enlève tous les résidus coloré --> clear ?*/
        glClearColor(0.8f, 0.9f, 1.0f, 1.0f); /*Définit la couleur d'arrière plan du clear*/
        int x = 16 + time;
        int y = 16 + time;
        int size = 16;

        System.out.println("Je render avec "+x +" "+ y);
        System.out.println("time /1000 = "+ (time /1000));

        glBegin(GL_QUADS);
        glColor3f(0.5f,0.2f,0.9f);
        glVertex2f(x, y);
        glVertex2f(x + size, y);
        glVertex2f(x + size, y + size);
        glVertex2f(x, y + size);
        glEnd();
    }

    public static void main(String args[]){
        Component main = new Component();
        main.start();
    }
}
