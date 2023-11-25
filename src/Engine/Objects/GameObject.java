package Engine.Objects;

import Engine.Graphics.Material;
import Engine.Graphics.Mesh;
import Engine.Graphics.Vertex;
import Engine.Math.Vector2f;
import Engine.Math.Vector3f;

public class GameObject {
    private Vector3f rotation, position, scale, scl;

    private Mesh mesh;
    public Material material;

    public GameObject(Mesh mesh, Vector3f position, Vector3f rotation, Vector3f scale) {
        this.rotation = rotation;
        this.position = position;
        this.scale = scale;
        this.mesh = mesh;
    }
    public GameObject(Material texture, Vector3f position, Vector3f rotation, Vector3f scale, float scl_) {
        this.rotation = rotation;


        material = material = new Material("/Textures/PALETTES/test2.png");
        this.position = position;
        this.scale = scale;
        this.scl = Vector3f.mult(scale, new Vector3f(scl_, scl_, scl_));
        //this.scl = new Vector3f(1);
        mesh = new Mesh(new Vertex[]{
                //Back face
                new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),      
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, scl.y)),
                new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(scl.x, scl.y)),
                new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(scl.x, 0.0f)),

                //Front face
                new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(0.0f, scl.y)),
                new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(scl.x, scl.y)),
                new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(scl.x, 0.0f)),

                //Right face
                new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(0.0f, scl.y)),
                new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(scl.z, scl.y)),
                new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(scl.z, 0.0f)),

                //Left face
                new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, scl.y)),
                new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(scl.z, scl.y)),
                new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(scl.z, 0.0f)),

                //Top face
                new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.0f, scl.z)),
                new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(scl.x, scl.z)),
                new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(scl.x, 0.0f)),

                //Bottom face
                new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(0.0f, 0.0f)),
                new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, scl.z)),
                new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(scl.x, scl.z)),
                new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(scl.x, 0.0f)),
        }, new int[]{
                0, 1, 2, //
                2, 3, 0,

                6, 5, 4, //1
                4, 7, 6,

                8, 9, 10, //1
                10, 11, 8,

                14, 13, 12,//
                12, 15, 14,

                16, 17, 18,//
                18, 19, 16,

                22, 21, 20,//
                20, 23, 22

        }, texture);

    }
    public void update(){
        //rotation = Vector3f.add(rotation, new Vector3f(0, 1, 0));
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }
}
