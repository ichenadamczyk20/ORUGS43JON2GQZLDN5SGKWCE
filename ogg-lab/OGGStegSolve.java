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
      System.out.println("Usage: java OGGStegSolve <modified audio file> <file to reveal>");

      String soundFile = args[0];
      String outputFile = args[1];
      File file = new File(outputFile);
      FileOutputStream out = new FileOutputStream(file);

      byte[] ogg = converter(soundFile);

      int count = 0;
      for (int i = 0; i < ogg.length - 3; i++) {
        if (ogg[i] == 79 && ogg[i + 1] == 103 && ogg[i + 2] == 103 && ogg[i + 3] == 83) count++;
      }
      int currentIndex = 0;
      int[] indices = new int[count + 1];
      int[] endIndices = new int[count];
      for (int i = 0; i < ogg.length - 3; i++) {
        if (ogg[i] == 79 && ogg[i + 1] == 103 && ogg[i + 2] == 103 && ogg[i + 3] == 83) {
          indices[currentIndex] = i;
          currentIndex++;
        }
      }
      indices[count] = ogg.length;

      // name of the game: find the number of samples, find the sample size, predict the number of bytes
      // until the next header, then assume all of the excess is steganographized data
      for (int i = 0; i < count - 1; i++) {
        int startIndex_firstHeader = indices[i];
        int startIndex_newHeader = indices[i + 1];
        int numSegments = ogg[startIndex_firstHeader + 26] & 0xFF;
        int dataLength = 0;
        for (int j = startIndex_firstHeader + 27; j < startIndex_firstHeader + 27 + numSegments; j++) {
          int segmentLength = ogg[j] & 0xFF;
          //System.out.println(segmentLength);
          dataLength += segmentLength;
        }

        int endIndex_data = startIndex_firstHeader + 26 + numSegments + dataLength;
        endIndices[i] = endIndex_data;
      }

      int offset = 0;
      boolean diff = false;
      for (int i = 1; i < indices.length; i++) {
        int nextStartIndex = indices[i];
        int dataEndIndex = endIndices[i - 1];
        for (int j = dataEndIndex + 1; j < nextStartIndex; j++) {
          out.write(ogg[j]);
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
