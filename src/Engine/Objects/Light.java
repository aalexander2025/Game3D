package Engine.Objects;

import Engine.Math.Vector3f;

public class Light {
    public Vector3f pos;
    public Vector3f color;
    public float intensity;

    public Light(Vector3f position, Vector3f clr, float intense){
        pos = position;
        color = clr;
        intensity = intense;
    }
}
