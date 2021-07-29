/****************************************************************************
 * Author: Lakshan Martin
 * 
 * Program: DES Algorithm to demonstrate the encryption and decryption
 *          process by reading data from a text file.
 * 
 * Data last modified: 20/04/2020
 ***************************************************************************/

import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        String key;
        String[] permKeys;

        //Clear terminal screen
        System.out.print("\033[H\033[2J");
        
        System.out.println(
            "THIS PROGRAM DEMONSTRATES THE IMPLEMENTATION OF THE DATA " +
            "ENCRYPTION STANDARD (DES)\n" +
            "--------------------------------------------------------" +
            "--------------------------");

        System.out.print("To begin, please enter the Encryption key: ");
        key = sc.nextLine();
      
        //Generate 16 keys 48bit keys from user input key
        permKeys = KeyGen.keyGen(key); 

        //Read in data from text file to demonstrate DES
        FileIO.fileToString("testfile-DES.txt", permKeys);
    }
}