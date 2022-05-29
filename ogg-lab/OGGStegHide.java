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


            File file = new File(outputSound);
            FileOutputStream out = new FileOutputStream(file);
            // byte[] data = new byte[(int)file.length()];
            // out.write(data);

            byte[] ogg = converter(soundFile);
            byte[] inp = converter(inputFile);

            // byte[] tmp2 = new byte[44];
            // for(int i = 0; i < tmp2.length; i++){
            //     tmp2[i] = wav[i];
            // }
            // out.write(tmp2);

            // okay this is where thing

            int[] indices = new int[(int) (inp.length / 100) + 1];
            int currentIndex = 0;
            for (int i = 1; i < ogg.length - 3; i++) {
                if (ogg[i] == 79) {
                    if (ogg[i + 1] == 103)
                        if (ogg[i + 2] == 103)
                            if (ogg[i + 3] == 83) {
                                //System.out.println(i);
                                if (currentIndex >= indices.length) break;
                                indices[currentIndex] = i;
                                currentIndex ++;
                            }
                }
            }

            // now insert 100 bytes of data before each header
            currentIndex = 0;
            int cursor = 0;
            boolean writing = true;
            int numberOfBytes = 0;
            for (int i = 0; i < ogg.length; i++) {
                if (currentIndex < indices.length && i == indices[currentIndex] && writing) {
                    for (int j = 0; j < 100; j++) {
                        if (cursor + j >= inp.length) {
                            writing = false;
                            break;
                        }
                        if (inp[cursor + j] == 83 && cursor + j >= 3) {
                            if (inp[cursor + j - 1] == 103 && inp[cursor+j-2] == 103 && inp[cursor+j-3] == 79) {
                                cursor += j - 100;
                                break;
                            }
                        }
                        out.write(inp[cursor + j]);
                        numberOfBytes++;
                    }
                    cursor += 100;
                    currentIndex++;
                }
                out.write(ogg[i]);
            }

            System.out.println("file written now play it and see if you hear the difference");
            System.out.println("Number of bytes added for the file");
            System.out.println(numberOfBytes);
            System.out.println("Size of file");
            System.out.println(inp.length);

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
