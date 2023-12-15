package Engine.Objects;

import Engine.Graphics.Mesh;
import Engine.Graphics.Vertex;
import Engine.IO.Input;
import Engine.IO.Window;
import Engine.Math.Vector2f;
import Engine.Math.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjglx.Sys;
import org.lwjglx.test.spaceinvaders.Game;

public class Camera {
    public Vector3f position, rotation;
    public float speed = 5.0f;
    public float side = 0.3f;
    float mult = 1.0f;
    public Vector3f bounds = new Vector3f(0.2f, 1.0f, 0.2f);
    private double pMouseX = 0, pMouseY = 0;
    private double mouseX, mouseY;
    private float mouseS = 0.1f;
    private float g = -0.2f;
    private Vector3f vel = new Vector3f(0, 0, 0);
    private boolean isOnGround = true;
    private boolean keyLock = false;
    private float left, right, front, back;
    private float lTime = 0;
    public float deltaTime = 0;

    private Vector3f dirA = new Vector3f(0, 0, 0);

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public void update(Window w, GameObject[] obj) {
        mouseX = Input.getMouseX();
        mouseY = Input.getMouseY();


        //calc delta time///
        float cTime = (float) GLFW.glfwGetTime();
        deltaTime = (cTime - lTime);
        lTime = cTime;
        ////////////////////


        //update gravity lol
        vel.y += g;
        //calc direction coeff.
        float dir1_ = (left - right);
        float dir2_ = (front - back);

        //if our direction is tiny, just set it to zero
        if (Math.abs(dir1_) < 0.1f) {
            dir1_ = 0;
        }
        if (Math.abs(dir2_) < 0.1f) {
            dir2_ = 0;
        }


        //if we have 2 inputs at once, set both coeffs to 1/sqrt(2) so that the magnitude it 1
        if (Vector2f.length(new Vector2f(dir1_, dir2_)) > 1) {
            dir1_ = (float) (dir1_ / Math.sqrt(2));
            dir2_ = (float) (dir2_ / Math.sqrt(2));
        }


        //update position
        position.x += vel.x * deltaTime;
        position.y += vel.y * deltaTime;
        position.z += vel.z * deltaTime;

//      position = Vector3f.add(position, Vector3f.mult(vel, new Vector3f(deltaTime)));


        for (GameObject o : obj) {
            collides(o);
        }

        if (position.y < 1) {
            position.y = 1;
            vel.y = 0;
            isOnGround = true;
        }


        float x = (float) Math.sin(Math.toRadians(rotation.y)) * speed * mult;
        float z = (float) Math.cos(Math.toRadians(rotation.y)) * speed * mult;

        float x1 = (float) Math.sin(Math.toRadians(rotation.y) + (Math.PI / 2)) * speed * mult;
        float z1 = (float) Math.cos(Math.toRadians(rotation.y) + (Math.PI / 2)) * speed * mult;


        if (isOnGround) {


            vel.x = -(x * dir2_) - (x1 * dir1_);
            vel.z = -(z * dir2_) - (z1 * dir1_);

            dirA.x = vel.x;
            dirA.z = vel.z;
        }

        if (!isOnGround) {

            vel.x = dirA.x;
            vel.z = dirA.z;

            System.out.println(dirA.x + " , " + dirA.z);
        }


        float damp = 0.9f;
        if (Input.isKeyDown(GLFW.GLFW_KEY_D)) {
            //position = Vector3f.add(position, new Vector3f(z, 0, -x));
            right = 1;
        } else {
            if(isOnGround){
                right *= damp;
            }
        }


        if (Input.isKeyDown(GLFW.GLFW_KEY_A)) {
            //position = Vector3f.add(position, new Vector3f(-z, 0, x));
            left = 1;
        } else {
            if(isOnGround){
                left *= damp;
            }
        }

        if (Input.isKeyDown(GLFW.GLFW_KEY_W)) {
            //position = Vector3f.add(position, new Vector3f(-x, 0, -z));
            front = 1;
        } else {
            if(isOnGround){
                front *= damp;
            }

        }


        if (Input.isKeyDown(GLFW.GLFW_KEY_S)) {
            //position = Vector3f.add(position, new Vector3f(x, 0, z));
            back = 1;
        } else {
            if(isOnGround){
                back *= damp;
            }
        }


        if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE) && isOnGround && !keyLock) {
            float jump = 6.0f;
            vel.y = jump;
            isOnGround = false;
            keyLock = true;
        }
        if (!Input.isKeyDown(GLFW.GLFW_KEY_SPACE) && isOnGround && keyLock) {

            keyLock = false;
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            mult = 1.5f;
        } else {
            mult = 1;
        }

        if (Input.isKeyDown(GLFW.GLFW_KEY_C)) {
            w.fov = 10.0f;
        } else {
            w.fov = 70.0f;
        }

        float dx = (float) (mouseX - pMouseX);
        float dy = (float) (mouseY - pMouseY);

        float angleX = -dy * mouseS;
        float angleY = -dx * mouseS;



        if(rotation.x > 89.9f){
            rotation.x = 89.9f;
            angleX = 0;
        }
        if(rotation.x < -89.9f){
            rotation.x = -89.9f;
            angleX = 0;
        }
        rotation.x += angleX;


        rotation.y += angleY;

        //System.out.println(Math.toRadians(rotation.x));
        //rotation = Vector3f.add(rotation, new Vector3f(angleX, angleY, 0));

        pMouseX = mouseX;
        pMouseY = mouseY;

    }

    public void collides(GameObject o) {

        for(int i = 0; i < o.getMesh().getVertices().length; i++){
            Vector3f vPos = Vector3f.add(o.getMesh().getVertices()[i].pos, o.getPosition());
           
        }





    }

    private void checkTriangle(Vector3f p1, Vector3f p2, Vector3f p3) {

    }

    private float signedDist(Vector3f p, Vector3f normal, Vector3f o) {
        return Vector3f.dot(p, normal);

    }


}
