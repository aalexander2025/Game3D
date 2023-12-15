package Engine.Graphics;

import Engine.Math.Vector2f;
import Engine.Math.Vector3f;

public class Vertex {
    public Vector3f pos, color;
    public Vector2f textureCoord;

    public Vertex(Vector3f p, Vector2f tc){
        pos = p;
        color = new Vector3f(1, 1, 1);
        textureCoord = tc;
    }




}
