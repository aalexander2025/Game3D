package Engine.IO;

import org.lwjgl.glfw.*;

public class Input {

    private static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private static boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

    private GLFWKeyCallback keyboard;
    private GLFWCursorPosCallback mouse;
    private GLFWMouseButtonCallback mouseButton;
    private GLFWScrollCallback scroll;

    private static double mouseX, mouseY;
    private static double scrollX, scrollY;

    public Input() {
        keyboard = new GLFWKeyCallback() {
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keys[key] = (action != GLFW.GLFW_RELEASE);
            }
        };
        mouse = new GLFWCursorPosCallback() {
            public void invoke(long window, double xpos, double ypos) {
                mouseX = xpos;
                mouseY = ypos;
            }
        };
        mouseButton = new GLFWMouseButtonCallback() {
            public void invoke(long window, int button, int action, int mods) {
                buttons[button] = (action != GLFW.GLFW_RELEASE);
            }
        };

        scroll = new GLFWScrollCallback() {
            public void invoke(long window, double offX, double offY){
                scrollX += offX;
                scrollY += offY;
            }
        };
    }

    public static boolean isKeyDown(int key){
        return keys[key];
    }
    public static boolean isMouseDown(int button){
        return buttons[button];
    }

    public void destroy(){
        keyboard.free();
        mouse.free();
        mouseButton.free();
        scroll.free();
    }

    public GLFWKeyCallback getKeyboardCallback() {
        return keyboard;
    }

    public GLFWCursorPosCallback getMouseCallback() {
        return mouse;
    }

    public GLFWScrollCallback getScrollCallback() {
        return scroll;
    }

    public GLFWMouseButtonCallback getMouseButtonCallback() {
        return mouseButton;
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

    public static double getScrollX() {
        return scrollX;
    }

    public static double getScrollY() {
        return scrollY;
    }
}
