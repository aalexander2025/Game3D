package Engine.Graphics;

import Engine.IO.Window;
import Engine.Math.Matrix4f;
import Engine.Math.Vector3f;
import Engine.Objects.Camera;
import Engine.Objects.GameObject;
import Engine.Objects.Light;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Renderer {
    private Shader shader;
    private Window window;
    private float temp;

    public Renderer(Window w, Shader s){
        shader = s;
        window = w;


    }

    public void renderMesh(GameObject obj, Camera cam, Light[] light){

        Vector3f[] lpos = new Vector3f[light.length];
        Vector3f[] lcolor = new Vector3f[light.length];
        Vector3f[] ldata = new Vector3f[light.length];

        for (int i = 0; i < light.length; i++) {
            lpos[i] = light[i].pos;
            lcolor[i] = light[i].color;
            ldata[i] = new Vector3f(light[i].intensity);

        }

        GL30.glBindVertexArray(obj.getMesh().getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        GL30.glEnableVertexAttribArray(3);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, obj.getMesh().getIBO());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, obj.getMesh().getMaterial().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, obj.material.getTextureID());

        shader.bind();
        shader.setUni("text1", 0);
        shader.setUni("text2", 1);
        shader.setUni("obj", Matrix4f.transform(obj.getPosition(), obj.getRotation(), obj.getScale()));
        shader.setUni("view", Matrix4f.view(cam.position, cam.rotation));
        shader.setUni("proj", window.projection);

        shader.setUni("SIZE", light.length);
        shader.setUni("lightPos", lpos);
        shader.setUni("lightColor", lcolor);
        shader.setUni("lightData", ldata);

//        shader.setUni("light2", new Vector3f(-3, 1, -3));
//        shader.setUni("light3", new Vector3f(3, 1, -3));
//        shader.setUni("light4", new Vector3f(-3, 1, 3));
        //shader.setUni("paletteMapping", new Material("/Textures/STONE.png").getTextureID());
        //new Material("/Textures/STONE.png")
        GL11.glDrawElements(GL11.GL_TRIANGLES, obj.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        shader.unbind();
        GL30.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glDisableVertexAttribArray(3);
        GL30.glBindVertexArray(0);

    }

}
