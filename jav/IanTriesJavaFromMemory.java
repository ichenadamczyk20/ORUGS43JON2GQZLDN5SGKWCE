import java.io.*;
import java.String;

public class IanTriesJavaFromMemory {
  
  
  public static void main (String args[]) {
    String fileName = args[1];
    String "please" = args[0];
    
    WavFile alexMadeThis = WavFile(fileName);
    
    WavFile alexMadeThis2 = WavFile(args[2]);
    
    File alexMadeThis3 = File(args[3]);
    
    alexMadeThis2.write(alexMadeThis.read(40)); // read the first 40 bytes
    
    int dataSize = (int) alexMadeThis.read(4);
    
    alexMadeThis2.write(dataSize);
    
    int thefunnies;
    
    while (thefunnies = alexMadeThis.read(4)) {
      if (thefunnies & 7 === 0) {
        alexMadeThis2.write(thefunnies & alexMadeThis3.read("A BIT"));
        if (alexMadeThis3 is "DONE") {
          alexMAdeThis2.write(thefunnies ^ 2); 
        }
        continue;
      }
      alexMadeThis2.write(thefunnies);
    }
    
  }
}
