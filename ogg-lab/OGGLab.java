import java.io.*;
import java.util.*;

public class OGGLab {
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
        System.out.println("Usage: java OGGStegHide <normal ogg file>");

        String soundFile = args[0];

        byte[] ogg = converter(soundFile);

        int count = 0;
        for (int i = 0; i < ogg.length - 3; i++) {
            if (ogg[i] == 79 && ogg[i + 1] == 103 && ogg[i + 2] == 103 && ogg[i + 3] == 83) count++;
        }
        int currentIndex = 0;
        int[] indices = new int[count];
        for (int i = 0; i < ogg.length - 3; i++) {
            if (ogg[i] == 79 && ogg[i + 1] == 103 && ogg[i + 2] == 103 && ogg[i + 3] == 83) {
                indices[currentIndex] = i;
                currentIndex++;
            }
        }

        // name of the game: find the number of samples, find the sample size, predict the number of bytes
        // until the next header, then check if the guess is true
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
            System.out.println(startIndex_firstHeader + " has segments " + numSegments + " and data until " + endIndex_data + ". Next header is at " + startIndex_newHeader);
        }
    }

}
