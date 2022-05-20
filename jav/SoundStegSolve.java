<<<<<<< HEAD
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
=======
// read wav file

// if the first 4 bytes of the sound are 1000, then we make the last byte the data and the second-last byte 0

// at end of data , make the second-last byte 1 always
>>>>>>> caeaa6de18f3c5b2c5130dd4e97b704a6e3a8aa8
