/***************************************************************************
 * Author: Lakshan Martin
 * 
 * Purpose: This class contains all the variables and functions required for
 *          RSA Encryption and Decryption.
 * 
 * Date last modified: 17/05/2020          
 ***************************************************************************/

import java.math.BigInteger;

public class RSA
{
    //CLASS FIELDS
    long n;
    long publicKey;
    long privateKey;

    //CONSTRUCTOR
    public RSA(long inN, long inPubKey, long inPrivKey)
    {
        setN(inN);
        setPubKey(inPubKey);
        setPrivKey(inPrivKey);
    }

    //SETTERS
    private void setN(long inN)
    {
        this.n = inN;
    }

    private void setPubKey(long inPubKey)
    {
        this.publicKey = inPubKey;
    }
    
    private void setPrivKey(long inPrivKey)
    {
        this.privateKey = inPrivKey;
    }

    //FUNCTIONS
    /**** 
    * Encryption method that imports a plain text line from the text file 
    * and returns an encrypted line.
    *
    * ENCRYPTION PROCESS:
    *          c = m^e mod n, where:
    *  c = cipher text
    *  m = message (plain text)
    *  e = public key
    *  n = p * q 
    ****/        
    public String encryption(String plainText)
    {
        long m;
        BigInteger c = BigInteger.ZERO;
        String cipher;

        cipher = "";

        //Encrypt imported line character by character
        for(int i = 0; i < plainText.length(); i++)
        {
            m = plainText.charAt(i);
            c = fastModExp(m, publicKey, n);            
            cipher += c.toString();
            cipher += " ";
        }

        return cipher;
    }

    /****
    * Decryption method that imports a cipher text line from the text file
    * and returns a decrypted line.
    *
    * DECRYPTION PROCESS:
    *          m = c^d mod n, where:
    *  m = decrypted text
    *  c = cipher text
    *  d = private key
    *  n = p * q
    ****/
    public String decryption(String cipherText)
    {
        long c, toLong;
        String[] lineArray;
        BigInteger m = BigInteger.ZERO;
        char toChar;
        String decrypted;

        decrypted = "";

        //Split line into individual values for decryption
        lineArray = cipherText.split(" ");

        for(int i = 0; i < lineArray.length; i++)
        {
            c = Long.parseLong(lineArray[i]);
            m = fastModExp(c, privateKey, n);
            toLong = m.longValue();
            toChar = (char)toLong;
        
            decrypted += toChar;
        }

        return decrypted;
    }

    /****
     * This version of the Fast Modular Exponentiation is based off the
     * algorithm from the text book, as per the assignment requirements
     * for encryption.
     * 
     * See code comments below to see how code has been adapted from the
     * text book.
     * 
     * REFERENCES:
     *  - W. Stallings. "Cryptography & Network Security: Principles & Practice".
     *    Sixth Edition. Page 269, Figure 9.8.
     ****/
    private BigInteger fastModExp(long text, long key, long n)
    {
        String binExp;
        BigInteger c, f;

        binExp = Long.toBinaryString(key);
        c = BigInteger.valueOf(0); //c = 0
        f = BigInteger.valueOf(1); //f = 1

        for(int i = 0; i < binExp.length(); i++)
        {
            //c = 2 * c
            c = c.multiply(BigInteger.valueOf(2));

            //f = (f * f) mod n
            f = (f.multiply(f)).mod(BigInteger.valueOf(n));

            if(binExp.charAt(i) == '1')
            {
                //c = c + 1
                c = c.add(BigInteger.valueOf(1));

                //f = (f * m) mod n
                f = (f.multiply(BigInteger.valueOf(text))).mod(BigInteger.valueOf(n));
            }
        }

        return f;
    }
}