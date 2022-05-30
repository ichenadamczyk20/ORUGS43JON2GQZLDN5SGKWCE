import java.io.*;
import java.util.*;

public class WAVStegHide {
    //converts input to bytes
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
            System.out.println("Usage: java WAVStegHide <original audio> <output audio> <file to hide>");

            //take inputs
            String soundFile = args[0];
            String outputSound = args[1];
            String inputFile = args[2];


            File file = new File(outputSound);
            FileOutputStream out = new FileOutputStream(file);


            byte[] wav = converter(soundFile);
            byte[] inp = converter(inputFile);

            //copy the first 44 bytes (which should not be changed since changing them may corrupt the file)
            byte[] tmp2 = new byte[44];
            for(int i = 0; i < tmp2.length; i++){
                tmp2[i] = wav[i];
            }
            out.write(tmp2);

            int currentPowerOfTwo = 7;
            int currentIndex = 0;
            int currentInt = inp[currentIndex];
            int leftMost = 0B10000000;
            boolean continuing = true;
            int numberOfBits = 0;

            //edit the next bytes if the last 4 bits are 1 - then change every following bit to have the last 3 bits equal 1 to indicate hidden bits to steg solve
            for (int i = 44; i < wav.length - 1; i++) {
                int tmp = wav[i];
                if ((tmp & 7) == 7) {
                    if (continuing) {
                        int bit = currentInt & leftMost;
                        if (bit != 0) {
                            tmp = tmp - 1;
                        } else {
                            tmp = tmp - 2;
                        }
                        currentPowerOfTwo --;
                        out.write(tmp);
                        wav[i+1] |= 3;
                        out.write(wav[i + 1]);
                        i++;
                        if (currentPowerOfTwo < 0) {
                            currentPowerOfTwo = 7;
                            currentIndex += 1;
                            if (currentIndex >= inp.length) {
                                continuing = false;
                            } else {
                                currentInt = inp[currentIndex];
                            }
                        } else {
                            currentInt = currentInt << 1;
                        }
                        numberOfBits++;
                    }else{
                      tmp -= 1;
                      out.write(tmp);
                    }
                }else{
                  //all other bytes ending with 3 1s will be altered so they don't send false signals to the solver
                  if((tmp & 3) == 3){
                    tmp -= 1;
                  }
                  out.write(tmp);
                }
              }
            out.write(wav[wav.length - 2]);
            out.write(wav[wav.length - 1]);

            System.out.println("file written now play it and see if you hear the difference");
            System.out.println("Number of bytes added for the file");
            System.out.println(numberOfBits / 8);

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
