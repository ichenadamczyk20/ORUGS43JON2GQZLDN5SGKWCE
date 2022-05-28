import java.io.*;
import java.util.*;

public class AIFFStegSolve {
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
    try{
      System.out.println("Usage: java AIFFStegSolve <original audio> <modified audio> <file to reveal>");

      String soundFile = args[0];
      String soundFileNew = args[1];
      String outputFile = args[2];


      File file = new File(outputFile);
      FileOutputStream out = new FileOutputStream(file);

      byte[] wav = converter(soundFile);
      byte[] wav2 = converter(soundFileNew);

      int currentPowerOfTwo = 7;
      int currentInt = 0;
      boolean continuing = true;
      int bitsFound = 0;

      for (int i = 42; i < wav.length; i++) {
        int tmp1 = wav[i];
        int tmp2 = wav2[i];
        if (tmp1 != tmp2) {
          bitsFound ++;
          if ((tmp2 & 7) == 6) {
            currentInt = currentInt | 1;
          }
          currentPowerOfTwo = currentPowerOfTwo - 1;
          if (currentPowerOfTwo < 0) {
            out.write(currentInt);
            currentPowerOfTwo = 7;
            currentInt = 0;
          } else {
            currentInt = currentInt << 1;
          }
        }
      }

      System.out.println("file written now go read it");
      System.out.println("Number of bytes found");
      System.out.println(bitsFound / 8);

      out.close();
    } catch(FileNotFoundException e){
      System.out.println("ERROR: File not found");
      e.printStackTrace();
    } catch(IOException e){
      System.out.println("IO Exception");
      e.printStackTrace();
    }
  }

}
