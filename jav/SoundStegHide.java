import java.io.*;
import java.util.*;

public class SoundStegHide {
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
            System.out.println("Usage: java SoundStegHide <original audio> <output audio> <file to hide>");

            String soundFile = args[0];
            String outputSound = args[1];
            String inputFile = args[2];


            File file = new File(outputSound);
            FileOutputStream out = new FileOutputStream(file);
            // byte[] data = new byte[(int)file.length()];
            // out.write(data);

            byte[] wav = converter(soundFile);
            byte[] inp = converter(inputFile);

            int currentPowerOfTwo = 7; // goes from 7 to 0
            int currentIndex = 0;
            boolean continuing = true;

            byte[] tmp2 = new byte[44];
            for(int i = 0; i < tmp2.length; i++){
                tmp2[i] = wav[i];
            }
            out.write(tmp2);

            for (int i = 44; i < wav.length; i++) {
                byte tmp = wav[i];
                if ((tmp & 0B00000111) == 0) {
                    if (continuing) {
                        int bit = inp[currentIndex] & ((byte) (int) Math.pow(2, currentPowerOfTwo));
                        if (bit != 0) {
                            tmp |= 0B00000001;
                        } else {
                            tmp &= 0B11111110;
                        }
                        currentPowerOfTwo --;
                        if (currentPowerOfTwo < 0) {
                            currentPowerOfTwo = 7;
                            currentIndex += 1;
                            if (currentIndex > inp.length) {
                                continuing = false;
                            }
                        }
                    } else {
                        tmp != 0B00000010;
                    }
                }

                out.write(tmp);
            }

            System.out.println("file written now play it and see if you hear the difference");

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
