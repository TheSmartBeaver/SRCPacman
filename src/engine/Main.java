package src.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;
import src.Game;
import src.ScreenParams;
import src.entities.moving.SquareTest;
import src.loaders.LevelLoader;

import java.awt.*;

import static java.lang.Thread.sleep;
import static org.lwjgl.Sys.getTime;
import static org.lwjgl.Sys.getTimerResolution;
import static org.lwjgl.opengl.GL11.*;


public class Main {
    private boolean running = false;
    private static String windows_title = "PACMAN";

    private static int scale = 2;
    private static ScreenParams screenParams;

    private static int targetFPS = 60;
    private static long targetMSPerFrame = 1000 / targetFPS;

    DisplayMode mode;

    private double getDelta(long start, long end) {
        return (double) ((start - end) * 1000 / getTimerResolution());
    }

    public Main(){
        try {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            int width = screenSize.width / scale;
            int height = screenSize.height / scale;
            screenParams = new ScreenParams(2, width, height);
            mode = new DisplayMode(width, height);

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
        GLU.gluOrtho2D(0, screenParams.width,screenParams.height,0);
        glMatrixMode(GL_MODELVIEW); /*On revient à la vue d'origine*/
        glLoadIdentity();
    }

    public void start(){
        //TODO : rechercher le dossier maps dans l'arborescence
        System.out.println(System.getProperty("user.home"));
        //LevelLoader.loadLevels("C:\\Users\\Vincent\\IdeaProjects\\SRCPacman\\maps");
        LevelLoader.loadLevels("/amuhome/w16002657/IdeaProjects/SRCPacman/maps");
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
                squareTest = new SquareTest(50,50, 16, 100.0f);
            }
            if(Display.isCloseRequested()) stop(); /*Si on appuie sur croix fermeture*/

            currentBeginningFrameTime = getTime();
            //TODO : deltaTime va servir à calculer la nouvelle position des entités
            //TODO : deltaTime en double au lieu de long ?
            double deltaTime = getDelta(currentBeginningFrameTime, lastBeginningFrameTime);
            if (isFirstFrame) {
                deltaTime = 0;
                isFirstFrame = false;
            }

            lastBeginningFrameTime = currentBeginningFrameTime;

            Game.update(deltaTime, squareTest);

            Game.render(squareTest);

            endFrameTime = getTime();
            double timeElapsed = getDelta(endFrameTime, currentBeginningFrameTime);

            if (timeElapsed < targetMSPerFrame) {
                try {
                    sleep((int)(targetMSPerFrame - timeElapsed));
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



    private void cleanBuffer() {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f );
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public static void main(String args[]){
        Main main = new Main();

        //System.out.println(LevelLoader.levels.get(0).getTileMap());
        main.start();
    }
}
