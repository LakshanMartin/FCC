/***************************************************************************
 * Author: Lakshan Martin
 * 
 * Purpose: This program demonstrates the RSA Algorithm by encrypting and
 *          decrypting a text file.  
 *          The range of p and q has been set to be between
 *          10,000 and 100,000.
 * 
 * Date last modified: 17/05/2020          
 ***************************************************************************/

import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        
        long p, q, n, phi, publicKey, privateKey;
        long[] exEuclid;
        RSA rsa;

        System.out.println(
            "THIS PROGRAM DEMONSTRATES RSA ENCRYPTION & DECRYPTION");

        //KEY GENERATION
        //STEP 1 - Find two distinct prime numbers p and q    
        p = KeyGen.findPrime();
        q = KeyGen.findPrime();

        //Unlikely, but better to not have p == q
        while(p == q)
        {
            q = KeyGen.findPrime();
        }

        System.out.println("\nGenerated two distinct prime numbers:");
        System.out.println("    p = " + p);
        System.out.println("    q = " + q);

        //STEP 2 - Compute n and phi
        System.out.println("\nCompute n and phi:");
        n = p * q;
        phi = (p - 1) * (q -1);     
        System.out.println("    n = " + n);
        System.out.println("    phi = " + phi);

        //STEP 3 - Generate Public Key using e
        System.out.println("\nTwo keys generated:");
        publicKey = KeyGen.genPublicKey(phi);
        System.out.println("    Public key = " + publicKey);

        //STEP 4 - Generate Private Key using d
        exEuclid = KeyGen.extEuclidean(publicKey, phi);
        privateKey = exEuclid[0];
        System.out.println("    Private key = " + privateKey);
        
        //-------------------------------------------------------------------
        //CONSTRUCT RSA OBJECT
        rsa = new RSA(n, publicKey, privateKey);
        
        //ENCRYPT & DECRYPT
        FileIO.readFile("testfile-DES.txt", rsa);
        FileIO.outputFile("Encrypted.txt", rsa);
    }
}