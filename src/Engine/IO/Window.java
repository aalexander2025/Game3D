package Engine.IO;
/* Imports
 * GLFW - openGL library
 * GLFWVidMode - allows the video Mode to be changed
 * GLFWWindowSizeCallback - allows callbacks of change in window size
 * GL, GL11 - GL library
 * Vector3f - creates a 3d float vector
 * */
import Engine.Math.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import Engine.Math.Vector3f;
import org.lwjgl.opengl.GL11C;
import org.lwjgl.opengl.GL20;

public class Window {
    //window variable
    private long window;
    //window properties
    private int width, height;
    private final String title;
    private Vector3f bg = new Vector3f(0.0f);
    private boolean isResized;
    private boolean isFullScreen;
    private final int[] wPosX = new int[1];
    private final int[] wPosY = new int[1];
    private GLFWWindowSizeCallback sizeCallback;
    //timing and input
    public int frames;
    public static long time;
    public Input input;
    public Matrix4f projection;
    public float fov = 70.0f;

    public Window(int w, int h, String t){
        width = w;
        height = h;
        title = t;

    }
    //creates window and checks for any errors
    public void create(){
        if(!GLFW.glfwInit()){
            System.err.println("ERROR: GLFW failed to initialize! (You failure piece of crap)");
            return;
        }
        input = new Input();
        window = GLFW.glfwCreateWindow(width, height, title, isFullScreen ? GLFW.glfwGetPrimaryMonitor(): 0, 0);
        if(window == 0){
            System.err.println("ERROR: Window failed to initialize! (You failure piece of crap)");
            return;
        }

        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        wPosX[0] = (videoMode.width() - width)/2;
        wPosY[0] = (videoMode.height() - height)/2;
        GLFW.glfwSetWindowPos(window, wPosX[0], wPosY[0]);
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        //GL11.glDepthFunc(GL11.GL_LESS);

        createCallbacks();
        GLFW.glfwShowWindow(window);

        //frame rate
        GLFW.glfwSwapInterval(1);
        time = System.currentTimeMillis();
    }
    //creates callbacks for the window
    private void createCallbacks(){
        sizeCallback = new GLFWWindowSizeCallback() {
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
                isResized = true;
            }
        };
        GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
        GLFW.glfwSetCursorPosCallback(window, input.getMouseCallback());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonCallback());
        GLFW.glfwSetScrollCallback(window, input.getScrollCallback());
        GLFW.glfwSetWindowSizeCallback(window, sizeCallback);
    }
    //update window
    public void update(){
        projection = Matrix4f.project((float)width/(float)height, 0.1f, 1000.0f, fov);
        if(isResized){
            GL11.glViewport(0, 0, width, height);
            isResized = false;
        }


        GL11.glClearColor(bg.x, bg.y, bg.z, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        GLFW.glfwPollEvents();
        frames++;

        if(System.currentTimeMillis() > time + 1000){
            GLFW.glfwSetWindowTitle(window, title + " | FPS: " + frames);
            time = System.currentTimeMillis();
            frames = 0;
        }
    }
    //swaps window buffers
    public void swapBuffers(){
        GLFW.glfwSwapBuffers(window);
    }
    //destroys callbacks and window
    public void destroy(){
        input.destroy();
        sizeCallback.free();
        GLFW.glfwWindowShouldClose(window);
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    public boolean shouldClose(){
        return GLFW.glfwWindowShouldClose(window);
    }

    public void background(float r, float g, float b){
        bg.set(r, g, b);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isResized() {
        return isResized;
    }

    public void setResized(boolean resized) {
        isResized = resized;
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }
    //sets the window to fullscreen mode
    public void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
        isResized = true;
        if(isFullScreen){
            GLFW.glfwGetWindowPos(window, wPosX, wPosY);
            GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), 0, 0, width, height, 0);
        }
        else{
            GLFW.glfwSetWindowMonitor(window, 0, wPosX[0], wPosY[0], width, height, 0);
        }
    }

    public void mouseState(boolean l){
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, l ? GLFW.GLFW_CURSOR_DISABLED : GLFW.GLFW_CURSOR_NORMAL);
    }

    public GLFWWindowSizeCallback getSizeCallback() {
        return sizeCallback;
    }

    public void setSizeCallback(GLFWWindowSizeCallback sizeCallback) {
        this.sizeCallback = sizeCallback;
    }
}
