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
    String soundFile = args[0];
    String outputSound = args[1];
    String inputFile = args[2];


    File file = new File(outputSound);
    FileInputStream out = new FileInputStream(file);
    // byte[] data = new byte[(int)file.length()];
    // out.write(data);

    byte[] wav = converter(soundFile);
    byte[] inp = converter(inputFile);

    int currentPowerOfTwo = 7; // goes from 7 to 0
    int currentIndex = 0;

    byte[] tmp = new byte[44];
    for(int i = 0; i < tmp.length; i++){
      tmp[i] = wav[i]
    }
    out.write(tmp);

    for (int i = 44; i < wav.length; i++) {
      byte[] tmp = new byte[1];
      if (wav[i] & 7 == 0) {

      }

      out.write(tmp);
    }

    out.close();
  }

}
