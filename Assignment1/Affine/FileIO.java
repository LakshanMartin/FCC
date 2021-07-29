import java.io.*;
import java.util.*;

public class FileIO
{
    public static void outputValidKeys(int alphaRange)
    {
        PrintWriter pw;

        try
        {
            pw = new PrintWriter("validKeys.txt");

            for(int a = 0; a < alphaRange; a++)
            {
                for(int b = 0; b < alphaRange; b++)
                {
                    if(Validation.validate(a, b, alphaRange))
                    {
                        pw.println("[" + a + ", " + b + "]");
                    }
                }
            }

            pw.close();
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static String fileToString(String fileName)
    {
        File file;
        Scanner readFile;
        StringBuilder plainText;
        String currLine;

        plainText = new StringBuilder("");
        
        try
        {
            file = new File(fileName);
            readFile = new Scanner(file);
            
            while(readFile.hasNextLine())
            {
                currLine = readFile.nextLine();
                plainText.append(currLine);
                plainText.append("\n");
            }

            System.out.println("Successfully read file - [" + fileName + "]");
        }
        catch(IOException e)
        {
            System.out.println("ERROR: " +  e.getMessage());
            System.out.println("NOTE: Filename must be [" + fileName + "]");
        }

        return plainText.toString();
    }    

    public static void outputFile(String text, String filename)
    {
        PrintWriter pw;

        try
        {
            pw = new PrintWriter(filename);
            pw.println(text);
            System.out.print("successfully written to [" + filename +
                                "]\n");
            
            pw.close();
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void outputDistro(HashMap<Character, Integer> origCount,
                                    HashMap<Character, Integer> encryCount,
                                    String filename)
    {
        PrintStream file, console;
        
        console = System.out;

        try
        {
            file = new PrintStream(new File(filename));

            System.setOut(file);

            System.out.println("\nLETTER OCCURANCES WITHIN THE PLAIN TEXT:");
            LetterDistro.printDistro(origCount);

            System.out.println("\nLETTER OCCURANCES WITHIN THE ENCRYPTED TEXT:");
            LetterDistro.printDistro(encryCount);

            System.setOut(console);

            System.out.print("successfully written to [" + filename + "]\n");

            file.close();
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
