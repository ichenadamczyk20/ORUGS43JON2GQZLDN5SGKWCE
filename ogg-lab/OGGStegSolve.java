import java.io.*;
import java.util.*;

public class OGGStegSolve {
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

      // okay looking back things don't suck that much
      int offset = 0;
      for(int i = 0; i < wav2.length; i++){
        if(wav[i - offset] != wav2[i]){
          out.write(wav2[i]);
          offset ++;
        }

      }


      System.out.println("file written now go read it");

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
