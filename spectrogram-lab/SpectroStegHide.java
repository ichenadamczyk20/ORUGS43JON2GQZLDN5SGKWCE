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
            System.out.println("Usage: java SpectroStegHide <file to hide> <output audio>");
            String inputFile = args[0];
            String outputSound = args[1];

            File file = new File(outputSound);
            FileOutputStream out = new FileOutputStream(file);

            BufferedImage img = ImageIO.read(new File(inputFile));

            byte[] header = new byte[]{82, 73, 70, 70, -84, 88, 1, 0, 87, 65, 86, 69, 102, 109, 116, 32, 16, 0, 0, 0, 1, 0, 1, 0, 68, -84, 0, 0, -120, 88, 1, 0, 2, 0, 16, 0, 100, 97, 116, 97, -120, 88, 1, 0};
            byte[] conclusion = new byte[96000 + 44];
            for(int i = 0; i < 44; i++){
              conclusion[i] = header[i];
            }

            int width = img.getWidth();
            int height = img.getHeight();

            int t = 1; // HARD CODED to ONE SECOND o KAY?
            int lowFreq = 200;
            int highFreq = 8000;
            int samplesPerPx = (int) ((int) 48000 / (int) img.getWidth());
            int x = -1;
            for (int s = 0; s < 48000; s++) {
                double ty = t / ((double) s);
                double val = 0;
                if ((s % samplesPerPx) == 0 && (x < width - 1))
                    x++;
                for (int y = 0; y < height; y++) {
                  color = img.getRGB(x,y);
                    int blue = color & 0xff;
                    int green = (color & 0xff00) >> 8;
                    int red = (color & 0xff0000) >> 16;
                    double grey = (red + green + blue) / ((double) 768);
                    double freq = (((double) y) * (highFreq - lowFreq) / height) + lowFreq;
                    val += grey * Math.sin(ty * freq * 2 * 3.1415926);
                }
                byte theActual = (byte) ((int) (64 * val));
                conclusion[s * 2 + 44] = 0;
                conclusion[s * 2 + 1 + 44] = theActual;
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
