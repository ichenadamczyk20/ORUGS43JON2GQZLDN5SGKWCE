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

            byte[] header = new byte[]{82, 73, 70, 70, 36, -90, 14, 0, 87, 65, 86, 69, 102, 109, 116, 32, 16, 0, 0, 0, 1, 0, 1, 0, -128, -69, 0, 0, 0, 119, 1, 0, 2, 0, 16, 0, 100, 97, 116, 97, 0, -90, 14, 0};
            byte[] conclusion = new byte[960000 + 44];
            for(int i = 0; i < 44; i++){
                conclusion[i] = header[i];
            }

            int width = img.getWidth();
            int height = img.getHeight();

            int t = 10; // HARD CODED to TEN SECOND o KAY?
            int lowFreq = 200;
            int highFreq = 8000;
            double factor = (highFreq - lowFreq) / ((double) height);
            int samplesPerPx = (int) ((int) 480000 / (int) img.getWidth());
            int x = -1;
            int start = 0;
            int factorx = 2;
            for (int s = 0; s < 480000; s++) {
                double ty = s * t / ((double) 480000 );
                double val = 0;
                if ((s % samplesPerPx) == 0 && (x < width - 1)) {
                    x++;
                    start += 1;
                    start %= factorx;
                }
                for (int y = start; y < height; y += factorx) {
                    int color = img.getRGB(x, y);
                    double blue = color & 0xff;
                    double green = (color & 0xff00) >> 8;
                    double red = (color & 0xff0000) >> 16;
                    double grey = (0.2126 * red + 0.7152 * green + 0.0722 * blue) / ((double) 768);
                    double freq = ((height - y) * factor) + lowFreq;
                    val += grey * Math.sin(ty * freq * 2 * 3.1415926);
                }

                /*if (((s % samplesPerPx) > (samplesPerPx * 0.95)) && (x < width - 1)) {
                    for (int y = 0; y < height; y++) {
                        int color = (int) ((0.5 * img.getRGB(x,y)) + (0.5 * img.getRGB(x + 1, y)));
                        int blue = color & 0xff;
                        int green = (color & 0xff00) >> 8;
                        int red = (color & 0xff0000) >> 16;
                        double grey = (red + green + blue) / ((double) 768);
                        double freq = ((height - y) * factor) + lowFreq;
                        val += grey * Math.sin(ty * freq * 2 * 3.1415926);
                    }
                }*/
                byte theActual;
                if (val > 0) theActual = (byte) ((int) (32 * Math.pow(val, 0.3)));
                else theActual = (byte) ((int) (-32 * Math.pow(-1 * val, 0.3)));
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