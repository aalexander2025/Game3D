package Engine.Graphics;

import Engine.Math.Vector2f;
import Engine.Math.Vector3f;
import Engine.Utils.FileUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryUtil;
import Engine.Math.Matrix4f;


import java.nio.FloatBuffer;

public class Shader {

    private String vertexFile, fragmentFile;
    private int vertexID, fragmentID, programID;

    public Shader(String vertexPath, String fragmentPath){
        vertexFile = FileUtils.loadAsString(vertexPath);
        fragmentFile = FileUtils.loadAsString(fragmentPath);

    }

    public void create(){
        programID = GL20.glCreateProgram();
        vertexID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);

        //System.out.println(programID);

        GL20.glShaderSource(vertexID, vertexFile);
        GL20.glCompileShader(vertexID);

        if(GL20.glGetShaderi(vertexID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
            System.err.println("Error with V.S: " + GL20.glGetShaderInfoLog(vertexID));
        }

        fragmentID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        GL20.glShaderSource(fragmentID, fragmentFile);
        GL20.glCompileShader(fragmentID);

        if(GL20.glGetShaderi(fragmentID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
            System.err.println("Error with F.S: " + GL20.glGetShaderInfoLog(fragmentID));
        }

        GL20.glAttachShader(programID, vertexID);
        GL20.glAttachShader(programID, fragmentID);

        GL20.glLinkProgram(programID);
        if(GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE){
            System.err.println("Error with Link: " + GL20.glGetProgramInfoLog(programID));
        }
        GL20.glValidateProgram(programID);
        if(GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE){
            System.err.println("Error with Validate: " + GL20.glGetProgramInfoLog(programID));
        }

    }

    public int getUniLoc(String name){
        return GL20.glGetUniformLocation(programID, name);
    }

    //integers
    public void setUni(String name, int value){
        GL20.glUniform1i(getUniLoc(name), value);
    }
    //floats
    public void setUni(String name, float value){
        GL20.glUniform1f(getUniLoc(name), value);
    }
    //booleans
    public void setUni(String name, boolean value){
        GL20.glUniform1i(getUniLoc(name), value ? 1 : 0);
    }
    //Vector 2D
    public void setUni(String name, Vector2f value){
        GL20.glUniform2f(getUniLoc(name), value.x, value.y);
    }
    //Vector 3D
    public void setUni(String name, Vector3f value){
        GL20.glUniform3f(getUniLoc(name), value.x, value.y, value.z);
    }

    public void setUni(String name, Vector3f[] values){
        float[] temp = new float[values.length * 3];
        for(int i = 0; i < values.length; i++){
            temp[(i * 3)] = values[i].x;
            temp[(i * 3) + 1] = values[i].y;
            temp[(i * 3) + 2] = values[i].z;
        }

        GL20.glUniform3fv(getUniLoc(name), temp);
        //GL20.glUniform3f(getUniLoc(name), value.x, value.y, value.z);
    }
    //Matrix 4x4
    public void setUni(String name, Matrix4f value){
        FloatBuffer matrix = MemoryUtil.memAllocFloat(Matrix4f.size * Matrix4f.size);
        matrix.put(value.getAll()).flip();
        GL20.glUniformMatrix4fv(getUniLoc(name), true, matrix);





    }


    public void bind(){
        //System.out.println(programID);
        GL20.glUseProgram(programID);
    }
    public void unbind(){
        GL20.glUseProgram(0);
    }
    public void destroy(){
        GL20.glDetachShader(programID, vertexID);
        GL20.glDetachShader(programID, fragmentID);
        GL20.glDeleteShader(vertexID);
        GL20.glDeleteShader(fragmentID);
        GL20.glDeleteProgram(programID);
    }
}
