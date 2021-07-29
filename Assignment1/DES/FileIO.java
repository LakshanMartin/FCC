import java.util.*;
import java.io.*;

public class FileIO
{
    public static void fileToString(String fileName, String[] keys)
    {
        File file;
        Scanner readFile;
        File encryptOut, decryptOut;
        PrintWriter pwEncrypt, pwDecrypt;
        String currLine, cipherText, decrypted;

        try
        {
            //Scanner used to read text file input
            file = new File(fileName);
            readFile = new Scanner(file);

            //PrintWriter used to output to text file
            encryptOut = new File("Encrypted.txt");
            decryptOut = new File("Decrypted.txt");
            pwEncrypt = new PrintWriter(encryptOut);
            pwDecrypt = new PrintWriter(decryptOut);

            while(readFile.hasNextLine())
            {
                //Encrypt and Decrypt line by line
                currLine = readFile.nextLine();
                cipherText = DES.encryption(currLine, keys);                             
                decrypted = DES.decryption(cipherText, keys);

                //Method to remove padded zeroes on end of each line
                decrypted = Format.removeTrailingZeroes(decrypted);                

                //Print lines to output file
                if(readFile.hasNextLine())
                {
                    pwEncrypt.println(cipherText);
                    pwDecrypt.println(decrypted);
                }
                else //To eliminate newline at end of output file
                {
                    pwEncrypt.print(cipherText);
                    pwDecrypt.print(decrypted);
                }
            }

            System.out.println(
                "\nEncrypted data successfully output to - [Encrypted.txt]");
            System.out.println(
                "Decrypted data successfully output to - [Decrypted.txt]");
            
            pwEncrypt.close();
            pwDecrypt.close();
        }
        catch(IOException e)
        {
            System.out.println("ERROR: " +  e.getMessage());
            System.out.println("NOTE: Filename must be [" + fileName + "]");
        }
    }
}