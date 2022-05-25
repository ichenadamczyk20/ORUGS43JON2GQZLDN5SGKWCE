import java.io.*;
import java.util.*;
import javax.imageio;

public class SpectroStegHide {
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
            System.out.println("Usage: java OGGStegHide <file to hide> <output audio>");

            String inputFile = args[0];
            String outputSound = args[1];


            File file = new File(outputSound);
            FileOutputStream out = new FileOutputStream(file);
            // byte[] data = new byte[(int)file.length()];
            // out.write(data);

            BufferedImage img = ImageIO.read(new File(inputFile));

            // byte[] tmp2 = new byte[44];
            // for(int i = 0; i < tmp2.length; i++){
            //     tmp2[i] = wav[i];
            // }
            // out.write(tmp2);

            // okay this is where thing
            //TEST THIS WITH XXD

            byte[] header = new byte[44];
            byte[] holder = converter("sample.wav");
            for(int i = 0; i < 44; i++){
              header[i] = holder[i];
            }
            /*
            byte[0] = 82;
            byte[1] = 73;
            byte[2] = 70;
            byte[3] = 70;
            byte[4] = 0;
            byte[5] = 0;
            byte[6] = 0;
            byte[7] = 0;
            byte[8] = 87;
            byte[9] = 65;
            byte[10] = 86;
            byte[11] = 69;
            byte[12] = 102;
            byte[13] = 109;
            byte[14] = 116;
            byte[15] = 32;
            byte[16] = 0;
            byte[17] = 0;
            byte[18] = 0;
            byte[19] = 16;
            byte[20] = 0;
            byte[21] = 1;
            byte[22] = 0;
            byte[23] = 1;
            byte[24] = 0;
            byte[25] = 0;
            byte[26] = 172;
            byte[27] = 68;
            byte[28] = 0;
            byte[29] = 1;
            byte[30] = 88;
            byte[31] = 136;
            byte[32] = 0;
            byte[33] = 0;
            byte[34] = 0;
            byte[35] = 0;
            byte[36] = 0;
            byte[37] = 0;
            byte[38] = 0;
            byte[39] = 0;
            byte[40] = 0;
            byte[41] = 0;
            byte[42] = 0;
            byte[43] = 0;
            byte[44] = 0;
*/

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

            for (int index: indices) {
                //System.out.println(index);
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
