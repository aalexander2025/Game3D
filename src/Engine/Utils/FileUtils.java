package Engine.Utils;

import java.io.*;
import java.net.URL;

public class FileUtils {
    public static String loadAsString(String path){
        StringBuilder result = new StringBuilder();
        File file = new File(path);


        try(BufferedReader reader = new BufferedReader(new FileReader(file))){

            String text = "";
            while ((text = reader.readLine()) != null) {
                result.append(text).append("\n");
            }
            System.out.println(result.toString());
        }
        catch (IOException e){
            System.err.println("could not read file at " + path + ". (L + Bozo + ratio)\n" + e );
        }

        //System.out.println();
//        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(path).openStream()))){
//
//            String line = "";
//            while((line = reader.readLine()) != null){
//                result.append(line).append("\n");
//            }
//        }
//        catch (IOException e){
//            System.err.println("could not read file at " + path + ". (L + Bozo + ratio)\n" + e );
//        }
        return result.toString();
    }

//    public static String loadAsTexture(String path){
//
//    }
}
