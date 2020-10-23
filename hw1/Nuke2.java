import java.io.*;

public class Nuke2 {
  public static void main(String[] args)
     throws Exception {

     BufferedReader keyboard;
     String inputLine;
     keyboard = new BufferedReader( new InputStreamReader(System.in));
     inputLine = keyboard.readLine();
     String half1 =inputLine.substring(0,1);
     String half2 =inputLine.substring(2);
     String word=half1+half2;
     System.out.println(word);

   }
}
     
