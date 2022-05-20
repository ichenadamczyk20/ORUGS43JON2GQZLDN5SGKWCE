import java.io.*;
import java.util.*;

public class SoundStegSolve{
  public static byte[] converter (String filename){
    try{
      File file = new File(filename);
      byte[] data = new byte[(int)file.length()];
      FileInputStream in = new FileInputStream(file);
      in.read(data);
      in.close();

      return data;
    }catch(FileNotFoundException e){
      System.out.println("ERROR: File not found");
      e.printStackTrace();
      return null;
    }catch(IOException e){
      System.out.println("IO Exception");
      e.printStackTrace();
      return null;
    }

  }

  public static void main(String[] args){
    byte[] x = converter("giveup.wav");
    for(int i = 0; i < x.length; i++){
      System.out.print(x[i] + " ");
    }
  }

}
