import java.io.*;
import java.util.*;
import javax.imageio.*;
import java.nio.file.Files;
import java.awt.image.BufferedImage;
import java.lang.Math;

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
            System.out.println("Usage: java SpectroStegHide <input audio> <file to hide> <output audio>");

            String inputSound = args[0];
            String inputFile = args[1];
            String outputSound = args[2];


            File iFile = new File(inputSound);
            byte[] beginner = Files.readAllBytes(iFile.toPath());

            File file = new File(outputSound);
            FileOutputStream out = new FileOutputStream(file);

            BufferedImage img = ImageIO.read(new File(inputFile));

            byte[] header = new byte[]{-46, -55, -58, -58, 44, -40, -127, -128, -41, -63, -42, -59, -26, -19, -12, -96, -112, -128, -128, -128, -127, -128, -127, -128, -60, 44, -128, -128, 8, -40, -127, -128, -126, -128, -112, -128, -28, -31, -12, -31, 8, -40, -127, -128};
            byte[] conclusion = new byte[beginner.length];
            for(int i = 0; i < 44; i++){
              conclusion[i] = header[i];
            }

            int width = img.getWidth();
            int height = img.getHeight();

            int t = 1; // HARD CODED to ONE SECOND o KAY?
            int lowFreq = 200;
            int highFreq = 8000;
            for (int x = 0; x < width; x++) {
                double ty = t / ((double) x);
                double val = 0;
                for (int y = 0; y < height; y++) {
                    int red = img.getRGB(x, y) / (256 * 256);
                    int green = (img.getRGB(x, y) / 256) % 256;
                    int blue = img.getRGB(x, y) % 256;
                    double grey = (red + green + blue) / ((double) 768);
                    double freq = (((double) y) * (highFreq - lowFreq) / height) + lowFreq;
                    val += grey * Math.sin(ty * freq * 2 * 3.1415926);
                }
                byte theActual = (byte) ((int) (64 * val));
                conclusion[x + 44] = theActual;
            }
            out.write(conclusion);
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
