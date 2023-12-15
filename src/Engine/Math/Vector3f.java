package Engine.Math;

import java.util.Objects;

public class Vector3f {



    public float x, y, z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3f(float v) {
        this.x = v;
        this.y = v;
        this.z = v;
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(Vector3f s) {
        this.x = s.x;
        this.y = s.y;
        this.z = s.z;
    }


    public static Vector3f add(Vector3f a, Vector3f b){
        Vector3f result = new Vector3f(a.x + b.x, a.y + b.y, a.z + b.z);
        return result;
    }

    public static Vector3f sub(Vector3f a, Vector3f b){
        Vector3f result = new Vector3f(a.x - b.x, a.y - b.y, a.z - b.z);
        return result;
    }

    public static Vector3f mult(Vector3f a, Vector3f b){
        Vector3f result = new Vector3f(a.x * b.x, a.y * b.y, a.z * b.z);
        return result;
    }

    public static Vector3f div(Vector3f a, Vector3f b){
        Vector3f result = new Vector3f(a.x / b.x, a.y / b.y, a.z / b.z);
        return result;
    }

    public static float dot(Vector3f a, Vector3f b){
        return ((a.x * b.x) + (a.y * b.y) + (a.z * b.z));

    }

    public static boolean isFrontFacing(Vector3f a, Vector3f b){
        return ((a.x * b.x) + (a.y * b.y) + (a.z * b.z)) <= 0;

    }

    public static float length(Vector3f v){
        return (float)Math.sqrt((v.x * v.x) + (v.y * v.y) + (v.z * v.z));
    }

    public static float length(Vector3f a, Vector3f b){
        return (float) Math.sqrt(a.x * b.x + a.y * b.y + a.z * b.z);
    }

    //cx = aybz − azby
    //cy = azbx − axbz
    //cz = axby − aybx

    public static Vector3f cross(Vector3f a, Vector3f b){
        Vector3f result = new Vector3f(0);
        result.x = (a.y*b.z) - (a.z*b.y);
        result.y = (a.z*b.x) - (a.x*b.z);
        result.z = (a.x*b.y) - (a.y*b.x);

        return result;

    }

    public static Vector3f normalize(Vector3f n){


        return new Vector3f(n.x/length(n), n.y/length(n), n.z/length(n));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3f vector3f = (Vector3f) o;
        return Float.compare(x, vector3f.x) == 0 && Float.compare(y, vector3f.y) == 0 && Float.compare(z, vector3f.z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
