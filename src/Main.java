package src;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;
import src.engine.graphics.GameRenderer;
import src.engine.graphics.Texture;
import src.engine.input.InputGetter;
import src.entities.moving.PacmanSprite;
import src.entities.moving.SquareTest;
import src.entities.space.Tile;
import src.entities.space.TileSprite;
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

    private static int targetFPS = 30;
    private static long targetMSPerFrame = 1000 / targetFPS;
    public static int time;

    DisplayMode mode;

    private double getDelta(long start, long end) {
        return (double) ((start - end) * 1000 / getTimerResolution());
    }

    public Main(){
        try {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            int width = screenSize.width / scale;
            int height = screenSize.height / scale;
            UserParams.resolutionScale = scale;
            UserParams.screenWidth = width;
            UserParams.screenHeight = height;
            UserParams.userDir = System.getProperty("user.dir");

            UserParams.minLevelScreenOffsetUp = UserParams.screenHeight / 16;
            UserParams.minLevelScreenOffsetDown = UserParams.screenHeight / 64;
            UserParams.minLevelScreenOffsetLeft = UserParams.screenWidth / 64;
            UserParams.minLevelScreenOffsetRight = UserParams.screenWidth / 64;

            UserParams.maxLevelScreenHeight = UserParams.screenHeight -  UserParams.minLevelScreenOffsetUp -  UserParams.minLevelScreenOffsetDown;
            UserParams.maxLevelScreenWidth = UserParams.screenWidth -  UserParams.minLevelScreenOffsetLeft -  UserParams.minLevelScreenOffsetRight;

            mode = new DisplayMode(width, height);

            Display.setDisplayMode(mode);
            Display.setResizable(true);
            Display.setFullscreen(false);
            Display.setTitle(windows_title);
            Display.create();

            initGL();

            UserParams.texture = new Texture(UserParams.userDir + "/assets/textures/textures_test.png", 8, TileSprite.class);
            UserParams.pacmanTexture = new Texture(UserParams.userDir + "/assets/textures/pacman.png", 8, PacmanSprite.class);

        }
        catch (LWJGLException e){
            e.printStackTrace();
        }
    }

    private  void initGL() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluOrtho2D(0, UserParams.screenWidth, UserParams.screenHeight,0);
        glMatrixMode(GL_MODELVIEW); /*On revient à la vue d'origine*/
        glLoadIdentity();
        glEnable(GL_TEXTURE_2D);
    }

    public void start(){
        LevelLoader.loadLevels(UserParams.userDir + "/assets/maps");
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

        while (running){
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

            InputGetter.getInputs();
            GameMain.update(deltaTime);

            GameMain.render();

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
            time++;
            //System.out.println(time);
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
