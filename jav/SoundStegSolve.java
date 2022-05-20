import java.io.*;
import java.util.*;

public class SoundStegSolve {
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
      System.out.println("Usage: java SoundStegSolve <original audio> <modified audio> <file to reveal>");

      String soundFile = args[0];
      String soundFileNew = args[1];
      String outputFile = args[2];


      File file = new File(outputFile);
      FileOutputStream out = new FileOutputStream(file);

      byte[] wav = converter(soundFile);
      byte[] wav2 = converter(soundFileNew);

      int currentPowerOfTwo = 7; // goes from 7 to 0
      int currentIndex = 0;
      byte currentByte = 0B00000000 - 128;
      boolean continuing = true;

      for (int i = 44; i < wav.length; i++) {
        if (wav[i] != wav2[i]) {
          if ((wav2[i] & (0B00000001 - 128)) == 0B00000001 - 128) {
            currentByte |= (0B00000001 - 128);
          } else {
            System.out.println("FALSE");
          }
          currentPowerOfTwo = currentPowerOfTwo - 1;
          currentByte = (byte) (currentByte << 1);
          if (currentPowerOfTwo < 0) {
            out.write(currentByte);
            currentPowerOfTwo = 7;
            currentIndex += 1;
            currentByte = 0B00000000 - 128;
          }
        }
      }

      System.out.println("file written now go read it");

      out.close();
    }catch(FileNotFoundException e){
      System.out.println("ERROR: File not found");
      e.printStackTrace();
    }catch(IOException e){
      System.out.println("IO Exception");
      e.printStackTrace();
    }
  }

}
