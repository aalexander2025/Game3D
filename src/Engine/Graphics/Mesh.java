package Engine.Graphics;

import Engine.Math.Vector3f;
import jdk.jfr.Unsigned;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;
import org.lwjglx.Sys;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh {
    private Vertex[] vertices;
    private Vector3f[] normals;
    public Vector3f color = new Vector3f(1.0f);
    private int[] indices;
    private int VAO, IBO, VBO, CBO, TBO, NBO;
    private Material material;
    public Vector3f testColor = new Vector3f(1.0f);

    public Mesh(Vertex[] verts, int[] ins, Material material){
        vertices = verts;
        indices = ins;
        this.material = material;
        normals = new Vector3f[vertices.length * 3];



    }

    public Mesh(Vertex[] verts, int[] ins, Vector3f c){
        vertices = verts;
        indices = ins;
        color = c;
        this.material = new Material("/Textures/CKER.png");
        normals = new Vector3f[vertices.length * 3];

    }

    public void create(){
        material.create();
        VAO = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(VAO);


        /////
        //VBO
        FloatBuffer vertexBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        float[] vertexData = new float[vertices.length * 3];
        for(int i = 0; i < vertices.length; i++){
            vertexData[i * 3] = vertices[i].pos.x;
            vertexData[(i * 3) + 1] = vertices[i].pos.y;
            vertexData[(i * 3) + 2] = vertices[i].pos.z;
        }
        vertexBuffer.put(vertexData).flip();
        VBO = storeData(vertexBuffer, 0, 3);
        ////////
        //CBO
        FloatBuffer colorBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        float[] colorData = new float[vertices.length * 3];
        for(int i = 0; i < vertices.length; i++){

            colorData[i * 3] = color.x;
            colorData[(i * 3) + 1] = color.y;
            colorData[(i * 3) + 2] = color.z;
        }
        colorBuffer.put(colorData).flip();
        CBO = storeData(colorBuffer, 1, 3);


        //texture
        FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(vertices.length * 2);
        float[] textureData = new float[vertices.length * 2];
        for(int i = 0; i < vertices.length; i++){
            textureData[i * 2] = vertices[i].textureCoord.x;
            textureData[(i * 2) + 1] = vertices[i].textureCoord.y;
        }
        textureBuffer.put(textureData).flip();
        TBO = storeData(textureBuffer, 2, 2);


        //VBO
        FloatBuffer normalBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        float[] normalData = new float[vertices.length * 3];
        for(int i = 0; i < indices.length; i+= 3){

            int in1 = indices[i];
            int in2 = indices[i + 1];
            int in3 = indices[i + 2];

            //System.out.println(indices[2] + "<<<<<<<<<<<<");

            Vector3f a = new Vector3f(vertexData[in1 * 3], vertexData[(in1 * 3) + 1], vertexData[(in1 * 3) +2]);
            Vector3f b = new Vector3f(vertexData[in2 * 3], vertexData[(in2 * 3) +1], vertexData[(in2 * 3) +2]);
            Vector3f c = new Vector3f(vertexData[in3 * 3], vertexData[(in3 * 3) +1], vertexData[(in3 * 3) +2]);

//            Vector3f a = new Vector3f(vertexData[i * 3], vertexData[(i * 3) + 1], vertexData[(i * 3) + 2]);
//            Vector3f b = new Vector3f(vertexData[(i * 3) + 3], vertexData[(i * 3) + 4], vertexData[(i * 3) + 5]);
//            Vector3f c = new Vector3f(vertexData[(i * 3) + 6], vertexData[(i * 3) + 7], vertexData[(i * 3) + 8]);

            Vector3f dir1 = Vector3f.sub(b , a);
            Vector3f dir2 = Vector3f.sub(b, c);
//
            Vector3f crs = Vector3f.normalize(Vector3f.cross(dir1, dir2));

            //System.out.println(dir1.x + "------");

            normalData[(i * 2)] = crs.x;
            normalData[(i * 2) + 1] = crs.y;
            normalData[(i * 2) + 2] = crs.z;

            normalData[(i * 2) + 3] = crs.x;
            normalData[(i * 2) + 4] = crs.y;
            normalData[(i * 2) + 5] = crs.z;



           //System.out.println(i + ": " + dir1.x + " , " + dir1.y + " , " + dir1.z);
//           System.out.println(i+1 + ": " + b.x + " , " + b.y + " , " + b.z);
//           System.out.println(i+2 + ": " + c.x + " , " + c.y + " , " + c.z);
//
//           System.out.println(normalData.length);



        }

        for(int i = 0; i < normalData.length; i+=3){
            normals[i] = new Vector3f(i, i+1, i+2);

        }

        normalBuffer.put(normalData).flip();
        NBO = storeData(normalBuffer, 3, 3);



        //IBO
        IntBuffer indexBuffer = MemoryUtil.memAllocInt(indices.length);

        indexBuffer.put(indices).flip();

        IBO = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, IBO);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    private int storeData(FloatBuffer buffer, int index, int size){
        int bufferID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        return bufferID;
    }

    public void destroy(){
        GL15.glDeleteBuffers(VBO);
        GL15.glDeleteBuffers(IBO);
        GL15.glDeleteBuffers(CBO);
        GL15.glDeleteBuffers(TBO);
        GL15.glDeleteBuffers(NBO);
        GL30.glDeleteVertexArrays(VAO);
        material.destroy();
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public Vector3f[] getNormals() {
        return normals;
    }


    public void callColorBuffer(){

    }

    public int[] getIndices() {
        return indices;
    }

    public int getVAO() {
        return VAO;
    }

    public int getIBO() {
        return IBO;
    }

    public int getVBO() {
        return VBO;
    }

    public int getCBO() {
        return CBO;
    }

    public int getTBO(){
        return TBO;
    }

    public int getNBO(){
        return NBO;
    }

    public Material getMaterial(){
        return material;
    }
}
