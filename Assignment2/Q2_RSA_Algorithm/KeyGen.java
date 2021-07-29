/***************************************************************************
 * Author: Lakshan Martin
 * 
 * Purpose: This class contains all the functions required to produce the 
 *          Public and Private keys for the RSA algorithm.
 * 
 * Date last modified: 17/05/2020          
 ***************************************************************************/

import java.math.BigInteger;
import java.util.*;

public class KeyGen
{
    //Find random prime number between 10,000 - 100,000
    public static long findPrime()
    {
        Random rand; 
        int min, max;
        boolean isPrime;
        long prime;

        rand = new Random();
        min = 10000;
        max = 100000;
        isPrime = false;
        prime = 0;
        
        //Loop until prime number is found
        while(!isPrime)
        {
            //Generate random number within min and max range
            prime = rand.nextInt((max - min) + 1) + min;

            //Check if random number generated is prime
            isPrime = lehmannAlgo(prime);
        }

        return prime;
    }

    /****
    * LEHMANN ALGORITHM:
    * r = (a^ ((p-1) / 2)) % p
    *
    *   - If r is not 1 or -1, then p is definitely not a prime.
    *   - If r = 1 or -1, then 50% chance p is prime.
    *   - Probability p is prime based on iterations (i):
    *       p = 1 - (1/2^i)
    *     Probability of prime increased by 50% with each iteration
    *****/
    private static boolean lehmannAlgo(long p)
    {
        Random rand;
        boolean isPrime;
        int a, exp, numTries;
        BigInteger r, bigP, bigPminus1;

        isPrime = true;
        bigP = BigInteger.valueOf(p);
        bigPminus1 = BigInteger.valueOf(p - 1);

        if(p % 2 == 0)
        {
            isPrime = false;
        }
        else
        {
            //During lab 2 Eric suggested a max of 50 iterations
            numTries = 50;
            rand = new Random();
            
            //Isolate the exponent part of the Lehmann Algo just for 
            //code readability
            exp = (int)((p - 1) / 2);
            
            for(int i = 0; i < numTries; i++)
            {
                //Random int between 2 and p
                a = rand.nextInt(((int)p - 2) + 1) + 2;
            
                r = bigFastModExp(a, exp, p).mod(BigInteger.valueOf(p));

                //if((r % p) == 1 || (r % p) == (p - 1))
                if((r.mod(bigP)).equals(BigInteger.ONE) || (r.mod(bigP)).equals(bigPminus1))
                {
                    isPrime = true;
                }
                else
                {
                    isPrime = false;
                    return isPrime;
                }
            }
        }

        return isPrime;
    }

    /****
     * As per the text book and wikipedia (referenced below) it is suggested
     * that 'Fast Modular Exponentiation' be used to reduce the number of
     * computations to perform modular exponentiation. 
     * 
     * Functions works in the following steps:
     * Step 1 - Convert the exponent to binary
     * Step 2 - Perform modular calculations based on each bit value of the 
     *          binary exponent
     * Step 3 - First bit is assigned the base value
     * Step 4 - If the next bit is 1, then calculate as follows
     *          - previous value^2 mod n. Result is then multiplied by
     *            previous value, then mod n for final calculation for that bit.
     *            Example: 3^2 mod 15 = 9 --> 9*3 mod 15 = 12.
     *          If the next bit is 0, then calculate as above, except without
     *          second modular calculation.
     * Step 5 - The result for the calculation on the final bit is the result
     *          of the fast modular exponentiation calculation.
     * 
     * REFERENCES:
     *  - W. Stallings. "Cryptography & Network Security: Principles & Practice".
     *    Sixth Edition.
     *  - Sams Tutorials. "Fast Modular Exponentiation Explained". 
     *    https://youtu.be/2wdhIJssH9Qs. Accessed 10/05/2020.
     *  - Wikipedia. "Modular Exponentiation". 
     *    https://en.wikipedia.org/wiki/Modular_exponentiation. Accessed 12/05/2020.
     ****/
    public static BigInteger bigFastModExp(long base, long exp, long mod)
    {
        String binExp;
        BigInteger result, tempBase;

        binExp = Long.toBinaryString(exp); //Convert exp to binary
        result = BigInteger.ZERO;
        tempBase = BigInteger.valueOf(base);

        for(int i = 1; i < binExp.length(); i++)
        {
            result = (tempBase.multiply(tempBase)).mod(BigInteger.valueOf(mod));

            if(binExp.charAt(i) == '1')
            {
                //Secondary modular calculation if bit is 1
                result = (result.multiply(BigInteger.valueOf(base)).mod(BigInteger.valueOf(mod)));
            }

            tempBase = result;
        }

        return result;
    }

    /****
     * The value for e must be: 
     *      1 < e < phi, such that gcd(e, phi) = 1
     * 
     * Text suggests that 3 would vulnerable to attacks, so will manually
     * test with 5, 7, 17, 257, 65537 as suggested by the reference below.
     * 
     * REFERENCE:
     *  - RSA Algorithm. https://www.di-mgt.com.au/rsa_alg.html#note2.
     *    Accessed 10/05/2020.
     ****/
    public static long genPublicKey(long phi)
    {
        long gcd, publicKey;
        long[] eValues, exEuclid;
        int i;

        eValues = new long[5];
        eValues[0] = 5;
        eValues[1] = 7;
        eValues[2] = 17;
        eValues[3] = 257;
        eValues[4] = 65537;


        gcd = 0;
        i = -1;

        while(gcd != 1)
        {
            i++;
            exEuclid = extEuclidean(eValues[i], phi);
            gcd = exEuclid[1];
        }

        publicKey = eValues[i];

        return publicKey;
    }

    /****
     * Extended Euclidean Algorithm is used to find the Multiplicated Modular
     * Inverse of a modulo n and gcd
     ****/    
    public static long[] extEuclidean(long a, long b)
    {
        long x1, x2, y1, y2, tempX, tempY, temp, q, importB;
        long[] results = new long[2]; //Stored in the form {x^-1, gcd}

        x1 = 1;
        x2 = 0;
        y1 = 0;
        y2 = 1;
        temp = 0;
        importB = b;
        
        while(b != 0)
        {
            q = a / b;
            temp = b;
            b = a % b;
            a = temp;

            //Record new x and y values
            tempX = x2;
            tempY = y2;
            x2 = x1 - (q * x2);
            y2 = y1 - (q * y2);
            x1 = tempX;
            y1 = tempY;  
        }

        if(x1 < 0)
        {
            x1 = importB + x1;
        }

        results[0] = x1;
        results[1] = a;

        return results;
    }
}