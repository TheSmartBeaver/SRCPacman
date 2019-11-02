package src.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;
import src.Game;
import src.Level;
import src.SquareTest;
import src.loaders.LevelLoader;

import static java.lang.Thread.sleep;
import static org.lwjgl.Sys.getTime;
import static org.lwjgl.Sys.getTimerResolution;
import static org.lwjgl.opengl.GL11.*;


public class Main {
    private boolean running = false;
    private static String windows_title = "PACMAN";
    private static int scale = 3; /*Car on aura pas besoin d'aussi grosse résolution ?? */
    private static int widdth = 720/scale;
    private static int height = 480/scale;

    private static int targetFPS = 60;
    private static long targetMSPerFrame = 1000 / targetFPS;

    DisplayMode mode = new DisplayMode(widdth*scale, height*scale);

    private long getDelta(long start, long end) {
        return (start - end) * 1000 / getTimerResolution();
    }

    public Main(){
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
        long lastBeginningFrameTime = 0;
        long currentBeginningFrameTime = 0;
        long endFrameTime = 0;

        boolean isFirstFrame = true;
        SquareTest squareTest = new SquareTest();

        while (running){
            if (isFirstFrame) {
                squareTest = new SquareTest(50,50, 16);
            }
            if(Display.isCloseRequested()) stop(); /*Si on appuie sur croix fermeture*/

            currentBeginningFrameTime = getTime();
            //TODO : deltaTime va servir à calculer la nouvelle position des entités
            //TODO : deltaTime en double au lieu de long ?
            long deltaTime = getDelta(currentBeginningFrameTime, lastBeginningFrameTime);
            if (isFirstFrame) {
                deltaTime = 0;
                isFirstFrame = false;
            }

            lastBeginningFrameTime = currentBeginningFrameTime;

            Game.update(deltaTime, squareTest);

            render(squareTest);

            endFrameTime = getTime();
            long timeElapsed = getDelta(endFrameTime, currentBeginningFrameTime);

            if (timeElapsed < targetMSPerFrame) {
                try {
                    sleep(targetMSPerFrame - timeElapsed);
                    //TODO : mettre à 60 FPS si les calculs prennent tout le temps moins de 16ms.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                //TODO : diminuer le nombre de FPS (pas de minimum)
            }

            Display.update();
            cleanBuffer();
        }
        exit();
    }

    private void render(SquareTest squareTest){

        float x = squareTest.getPosX();
        float y = squareTest.getPosY();
        int size = squareTest.getLength();

        glBegin(GL_QUADS);
        glColor3f(0.5f,0.2f,0.9f);
        glVertex2f(x, y);
        glVertex2f(x + size, y);
        glVertex2f(x + size, y + size);
        glVertex2f(x, y + size);
        glEnd();
    }

    private void cleanBuffer() {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f );
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public static void main(String args[]){
        Main main = new Main();
        //TODO : rechercher le dossier maps dans l'arborescence
        //LevelLoader.loadLevels("C:\\Users\\Vincent\\IdeaProjects\\SRCPacman\\maps");
        //System.out.println(LevelLoader.levels.get(0).getTileMap());
        main.start();
    }
}
