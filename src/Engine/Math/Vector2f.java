package Engine.Math;

import java.util.Objects;

public class Vector2f {


    public float x, y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }


    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2f add(Vector2f a, Vector2f b){
        Vector2f result = new Vector2f(a.x + b.x, a.y + b.y);
        return result;
    }

    public static Vector2f sub(Vector2f a, Vector2f b){
        Vector2f result = new Vector2f(a.x - b.x, a.y - b.y);
        return result;
    }

    public static Vector2f mult(Vector2f a, Vector2f b){
        Vector2f result = new Vector2f(a.x * b.x, a.y * b.y);
        return result;
    }

    public static Vector2f div(Vector2f a, Vector2f b){
        Vector2f result = new Vector2f(a.x / b.x, a.y / b.y);
        return result;
    }

    public static float length(Vector2f v){
        return (float)Math.sqrt(v.x * v.x + v.y * v.y);
    }

    public static float length(Vector2f a, Vector2f b){
        return a.x * b.x + a.y * b.y;
    }

    public static Vector2f normalize(Vector2f n){
        return new Vector2f(n.x/length(n), n.y/length(n));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2f vector2f = (Vector2f) o;
        return Float.compare(x, vector2f.x) == 0 && Float.compare(y, vector2f.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
