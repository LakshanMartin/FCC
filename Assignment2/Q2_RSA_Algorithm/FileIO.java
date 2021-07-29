/***************************************************************************
 * Author: Lakshan Martin
 * 
 * Purpose: This class contains the functions used to read in a text file for
 *          RSA encryption and output the decrypted version.
 * 
 * Date last modified: 17/05/2020          
 ***************************************************************************/

import java.util.*;
import java.io.*;

public class FileIO
{
    //Function to input the text file for RSA Encryption.
    public static void readFile(String filename, RSA rsa)
    {
        File file;
        Scanner readFile;
        File encryptOut;
        PrintWriter pwEncrypt;
        String encrypted;

        try
        {
            //Scanner used to read text file input
            file = new File(filename);
            readFile = new Scanner(file);

            //PrintWriter used to output to text file
            encryptOut = new File("Encrypted.txt");
            pwEncrypt = new PrintWriter(encryptOut);

            while(readFile.hasNextLine())
            {
                //Pass line by line to encryption method
                encrypted = rsa.encryption(readFile.nextLine());
                
                //This if statement is used to prevent the extra line
                //at the bottom of the text file.
                if(readFile.hasNextLine())
                {
                    pwEncrypt.println(encrypted);
                }
                else
                {
                    pwEncrypt.print(encrypted);
                }
            }

            System.out.println(
                "\nEncrypted data successfully output to - [Encrypted.txt]");
            
            pwEncrypt.close();
        }
        catch(IOException e)
        {
            System.out.println("ERROR: " +  e.getMessage());
            System.out.println("NOTE: Filename must be [" + filename + "]");
        }
    }

    //Function to read encrypted text file then output decryption to file
    public static void outputFile(String filename, RSA rsa)
    {
        File file;
        Scanner readFile;
        File decryptOut;
        PrintWriter pwDecrypt;
        String currLine, decrypted;

        try
        {
            //Scanner used to read text file input
            file = new File(filename);
            readFile = new Scanner(file);

            //PrintWriter used to output to text file
            decryptOut = new File("Decrypted.txt");
            pwDecrypt = new PrintWriter(decryptOut);

            while(readFile.hasNextLine())
            {
                currLine = readFile.nextLine();

                if(currLine.equals(""))
                {
                    //Skip empty lines. Nothing to decrypt
                    pwDecrypt.println("");
                }
                else
                {
                    decrypted = rsa.decryption(currLine.trim());

                    if(readFile.hasNextLine())
                    {
                        pwDecrypt.println(decrypted);
                    }
                    else
                    {
                        pwDecrypt.print(decrypted);
                    }
                }
                
            }

            System.out.println(
                "Decrypted data successfully output to - [Decrypted.txt]");

            pwDecrypt.close();
        }
        catch(IOException e)
        {
            System.out.println("ERROR: " +  e.getMessage());
            System.out.println("NOTE: Filename must be [" + filename + "]");
        }
    }
}