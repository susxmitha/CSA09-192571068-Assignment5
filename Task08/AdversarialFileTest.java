package Task08;
import java.nio.file.*;import java.io.*;
public class AdversarialFileTest {
 public static void main(String[]x)throws Exception{Path p=Files.createTempFile("damaged",".csv");Files.writeString(p,"P1,192571001,Anu,4,2500.0\nP2,192571002,WrongFields\nP3,192571003,NonNumeric,7,abc\n\nP4,192571004,Devi,2,3200.0\n");
  int good=0,bad=0,n=0;try(BufferedReader r=Files.newBufferedReader(p)){String line;while((line=r.readLine())!=null){n++;String[]f=line.split(",",-1);try{if(f.length!=5)throw new IllegalArgumentException("wrong field count");Integer.parseInt(f[3]);Double.parseDouble(f[4]);good++;System.out.println("Line "+n+" VALID "+f[0]);}catch(Exception e){bad++;System.out.println("Line "+n+" REJECTED "+e.getMessage());}}}
  System.out.println("Valid="+good+", damaged="+bad);Files.deleteIfExists(p);}
}
