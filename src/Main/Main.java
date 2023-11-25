package Main;
/* Imports
 * Renderer - rendering the mesh
 * Shader - Shading the mesh
 * Vertex - points that make up the mesh
 * Input - handles mouse and keyboard callbacks
 * Window - displays the window (duh)
 * Mesh - creates objects
 * Vector3f - creates a 3d float vector
 * GLFW - openGL library
 * */

/*TODO:
     Collision detection for non cube objects //SWEPT AABB - AABB detection/handling
    Collision handling for all objects
     - Geometry / compute shaders for collision?
    //Diffuse lighting
    //Normals for all objects
    more textures
    level design
        rooms
        other stuff
 */



import Engine.Graphics.*;
import Engine.IO.Input;
import Engine.IO.Window;
import Engine.Math.Vector3f;
import Engine.Objects.Camera;
import Engine.Objects.GameObject;
import Engine.Objects.Light;
import org.lwjgl.glfw.GLFW;

//Main class
public class Main implements Runnable {
    //necessary variables
    public Thread game;
    public Window window;
    public Renderer renderer;
    //A default Shader
    public Shader shader;
    public int scl = 10;

    public GameObject[] obj = new GameObject[18];

    //ok

    public Light[] light = new Light[5];

    public Camera cam = new Camera(new Vector3f(1, 0, 1), new Vector3f(0, 0, 0));

    //Starting the Game thread (game loop)
    public void start() {
        game = new Thread(this, "game");
        game.start();
    }
    //initializing variables
    public void init() {
        window = new Window(1000, 750, "test");
        shader = new Shader("resources/Shaders/MainVertex.glsl", "resources/Shaders/MainFragment.glsl");
        renderer = new Renderer(window, shader);
        float a = 5;
        obj[6] = new GameObject(new Material("/Textures/64x64/GRASS64x64.png"), new Vector3f(0, 0.45f, 0), new Vector3f(0, 0, 0), new Vector3f(40.0f, 0.1f, 10.0f), 0.5f);
        obj[0] = new GameObject(new Material("/Textures/64x64/BRICK64x64.png"), new Vector3f(0, 1.00001f + a / 2, 5), new Vector3f(0, 0, 0), new Vector3f(40, a + 1, 0.1f), 0.75f);
        obj[1] = new GameObject(new Material("/Textures/64x64/BBRICK64x64.png"), new Vector3f(-10.5f, 1.00001f + a / 2, -5), new Vector3f(0, 0, 0), new Vector3f(19, a + 1, 0.1f), 0.75f );
        obj[2] = new GameObject(new Material("/Textures/64x64/GBRICK64x64.png"), new Vector3f(20, 1.00001f + a / 2, 0), new Vector3f(0, 0, 0), new Vector3f(0.1f, a + 1, 10), 0.75f );
        obj[3] = new GameObject(new Material("/Textures/64x64/SBRICK64x64.png"), new Vector3f(-20, 1.00001f + a / 2, 0), new Vector3f(0, 0, 0), new Vector3f(0.1f, a + 1, 10), 0.75f );
        obj[4] = new GameObject(new Material("/Textures/FLESH.png"), new Vector3f(0, 1.35f, 0), new Vector3f(0, 0, 0), new Vector3f(1.0f, 1.0f, 1.0f), 0.5f );
        obj[5] = new GameObject(new Material("/Textures/64x64/GRBRICK64x64.png"), new Vector3f(0, 1.5f + a, 0), new Vector3f(0, 0, 0), new Vector3f(40.0f, 0.1f, 10.0f), 0.75f);
        obj[7] = new GameObject(new Material("/Textures/STONE.png"), new Vector3f(0, 10, 0), new Vector3f(0, 0, 0), new Vector3f(0.5f), 1);
        obj[8] = new GameObject(new Material("/Textures/STONE.png"), new Vector3f(0, 0.4f, 0), new Vector3f(0, 0, 0), new Vector3f(0.25f, 0f, 0.25f), 1);
        obj[9] = new GameObject(new Material("/Textures/64x64/DEADGRASS64x64.png"), new Vector3f(0, 0.45f, -20.0f), new Vector3f(0, 0, 0), new Vector3f(40.0f, 0.1f, 30.0f), 0.5f);

        obj[10] = new GameObject(new Material("/Textures/64x64/BBRICK64x64.png"), new Vector3f(10.5f, 1.00001f + a / 2, -5), new Vector3f(0, 0, 0), new Vector3f(19, a + 1, 0.1f), 0.75f );
        obj[11] = new GameObject(new Material("/Textures/64x64/BBRICK64x64.png"), new Vector3f(0, 2.00001f + a / 2, -5), new Vector3f(0, 0, 0), new Vector3f(2.0f, a-1, 0.1f), 0.75f );

        obj[12] = new GameObject(new Material("/Textures/64x64/WOOD64x64.png"), new Vector3f(-10, 1.35f, 0), new Vector3f(0, 0, 0), new Vector3f(1.0f, 1.0f, 1.0f), 1.0f );
        obj[13] = new GameObject(new Material("/Textures/64x64/BWOOD64x64.png"), new Vector3f(10, 1.35f, 0), new Vector3f(0, 0, 0), new Vector3f(1.0f, 1.0f, 1.0f), 1.0f );

        obj[14] = new GameObject(new Material("/Textures/64x64/METAL64x64.png"), new Vector3f(11.5f, 1.35f, 0), new Vector3f(0, 0, 0), new Vector3f(1.0f, 1.0f, 1.0f), 1.0f );
        obj[15] = new GameObject(new Material("/Textures/64x64/RMETAL64x64.png"), new Vector3f(13, 1.35f, 0), new Vector3f(0, 0, 0), new Vector3f(1.0f, 1.0f, 1.0f), 2.0f );


        obj[16] = new GameObject(new Material("/Textures/64x64/BCONCRETE64x64.png"), new Vector3f(-11.5f, 1.35f, 0), new Vector3f(0, 0, 0), new Vector3f(1.0f, 1.0f, 1.0f), 1.0f );
        obj[17] = new GameObject(new Material("/Textures/64x64/CONCRETE64x64.png"), new Vector3f(-13, 1.35f, 0), new Vector3f(0, 0, 0), new Vector3f(1.0f, 1.0f, 1.0f), 2.0f );


        light[0] = new Light(new Vector3f(3, 1, 3), new Vector3f(1.0f, 1.0f, 1.0f), 10.0f);
        light[1] = new Light(new Vector3f(-3, 1, -3), new Vector3f(1.0f, 0.0f, 0.0f), 40.0f);
        light[2] = new Light(new Vector3f(3, 1, -3), new Vector3f(0.0f, 1.0f, 0.0f), 20.0f);
        light[3] = new Light(new Vector3f(-3, 1, 3), new Vector3f(0.0f, 0.0f, 1.0f), 40.0f);

        light[4] = new Light(new Vector3f(0, 10, -20), new Vector3f(1.0f, 0.6f, 0.0f), 200.0f);


        window.background(0.0f, 0.0f, 0.0f);
        //window.background(0.4f, 0.5f, 0.6f);
        window.create();
        for (int i = 0; i < obj.length; i++) {
            obj[i].getMesh().create();
            obj[i].material.create();

        }

        shader.create();


    }

    //run the main class
    public void run() {
        init();
        while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            update();
            render();
            if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) {
                window.setFullScreen(!window.isFullScreen());
            }
        }
        close();
    }

    private void close() {
        window.destroy();
        for (int i = 0; i < obj.length; i++) {
            obj[i].getMesh().destroy();
        }

        shader.destroy();
    }

    //updates our window and all other functions that need to be constantly updated
    private void update() {

        window.update();
        cam.update(window, obj);
        //cam.position.y = 0.6f;
        for (int i = 0; i < obj.length; i++) {
            obj[i].update();
        }





        Vector3f a = obj[4].getPosition();
        Vector3f b = cam.position;
        Vector3f T = new Vector3f(0.004f);

        //obj[4].setPosition(new Vector3f(obj[4].getPosition().x + (dir.x * 0.03f), obj[4].getPosition().y, obj[4].getPosition().z + (dir.z * 0.03f)));

        obj[8].setPosition(new Vector3f(cam.position.x, obj[8].getPosition().y, cam.position.z));
        obj[8].setScale(new Vector3f(cam.bounds.x * 2, obj[8].getPosition().y, cam.bounds.z * 2));
       // obj[4].setRotation(new Vector3f(0.0f, (float)Math.toDegrees(Math.atan2(dir.z, dir.x)), 0.0f));


        if (Input.isMouseDown(GLFW.GLFW_MOUSE_BUTTON_1)) {
            window.mouseState(true);
            //System.out.printf("\033[96;52m" + " " + Input.getScrollX() + " , " + Input.getScrollY() + " " + "\033[0m" + "\n");
        }
        if (Input.isMouseDown(GLFW.GLFW_MOUSE_BUTTON_2)) {
            window.mouseState(false);
        }

    }

    //renders our meshes
    private void render() {

//        }

        for (int i = 0; i < obj.length; i++) {
            renderer.renderMesh(obj[i], cam, light);
        }

        window.swapBuffers();
    }

    //Main program that runs the thread
    public static void main(String[] args) {
        new Main().start();
    }
}