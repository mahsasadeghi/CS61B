/* OpenCommercial.java */

import java.net.*;
import java.io.*;

/**  A class that provides a main function to read five lines of a commercial
 *   Web page and print them in reverse order, given the name of a company.
 */

class OpenCommercial {

  /** Prompts the user for the name X of a company (a single string), opens
   *  the Web site corresponding to www.X.com, and prints the first five lines
   *  of the Web page in reverse order.
   *  @param arg is not used.
   *  @exception Exception thrown if there are any problems parsing the 
   *             user's input or opening the connection.
   */
  public static void main(String[] arg) throws Exception {

    BufferedReader keyboard;
    String inputLine;   

    keyboard = new BufferedReader(new InputStreamReader(System.in));

    System.out.print("Please enter the name of a company (without spaces): ");
    System.out.flush();        /* Make sure the line is printed immediately. */
    inputLine = keyboard.readLine();

    URL userURL = new URL("http://www." + inputLine + ".com/");
    BufferedReader in = new BufferedReader(
           new InputStreamReader(
           userURL.openStream()));
    
    String inputline;
    String[] lines = new String[5];

          for (int i=0; i<5; i++)
            {
               lines[i]= in.readLine();
            }
 
    System.out.println(lines[4]);
    System.out.println(lines[3]);
    System.out.println(lines[2]);
    System.out.println(lines[1]);
    System.out.println(lines[0]);

    in.close();

  }
}
