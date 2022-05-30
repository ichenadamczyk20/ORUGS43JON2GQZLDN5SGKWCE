import java.io.*;
import java.util.*;

public class OGGStegHide {
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
            System.out.println("Usage: java OGGStegHide <original audio> <output audio> <file to hide>");



            String soundFile = args[0];
            String outputSound = args[1];
            String inputFile = args[2];
            byte[] ogg = converter(soundFile);

            File file = new File(outputSound);
            FileOutputStream out = new FileOutputStream(file);

            byte[] inp = converter(inputFile);

            int count = 0;
            for (int i = 0; i < ogg.length - 3; i++)
                if (ogg[i] == 79 && ogg[i + 1] == 103 && ogg[i + 2] == 103 && ogg[i + 3] == 83) count++;

            int currentIndex = 0;
            int[] indices = new int[count];
            int[] endIndices = new int[count];
            for (int i = 0; i < ogg.length - 3; i++) {
                if (ogg[i] == 79 && ogg[i + 1] == 103 && ogg[i + 2] == 103 && ogg[i + 3] == 83) {
                    indices[currentIndex] = i;
                    currentIndex++;
                }
            }

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

            // now insert 100 bytes of data before each header
            boolean writing = true;
            int cursor = 0;
            int numberOfBytes = 0;
            for (int i = 0; i < indices.length; i++) {
                int startIndex = indices[i];
                int endIndex = endIndices[i];
                for (int wri = startIndex; wri <= endIndex; wri++) {
                    out.write(ogg[wri]);
                }
                if (writing) {
                    for (int j = 0; j < 1000; j++) {
                        if (cursor + j >= inp.length) {
                            writing = false;
                            break;
                        }
                        if (inp[cursor + j] == 83 && cursor + j >= 3) {
                            if (inp[cursor + j - 1] == 103 && inp[cursor+j-2] == 103 && inp[cursor+j-3] == 79) {
                                cursor += j - 1000;
                                break;
                            }
                        }
                        out.write(inp[cursor + j]);
                        numberOfBytes++;
                    }
                    cursor += 1000;
                }

            }

            System.out.println("file written now play it and see if you hear the difference");
            System.out.println("Number of bytes added for the file");
            System.out.println(numberOfBytes);
            System.out.println("Size of file");
            System.out.println(inp.length);
            if (writing) System.out.println("Warning: hidden file too large to fit, good luck decoding it");

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
