public class Affine
{
    public static String encryption(String plainText, int a, int b, 
                                    int alphaRange)
    {
        StringBuilder cipherText;
        char plainChar, cipherChar;

        cipherText = new StringBuilder(plainText);

        for(int i = 0; i < plainText.length(); i++)
        {
            plainChar = plainText.charAt(i);

            //Identify lower case characters
            if(plainChar >= 'a' && plainChar <= 'z')
            {
                cipherChar = (char)(plainChar - 'a');
                cipherChar = (char)(((a * cipherChar) + b) % alphaRange);
                cipherChar = (char)(cipherChar + 'a');
                cipherText.setCharAt(i, cipherChar);
            }
            //Identify upper case characters
            else if(plainChar >= 'A' && plainChar <= 'Z')
            {
                cipherChar = (char)(plainChar - 'A');
                cipherChar = (char)(((a * cipherChar) + b) % alphaRange);
                cipherChar = (char)(cipherChar + 'A');
                cipherText.setCharAt(i, cipherChar);
            }
            //Identify and ignore non-letter symbols
            else
            {
                cipherText.setCharAt(i, plainChar);
            }
        }

        return cipherText.toString();
    }

    public static String decryption(String cipherText, int a, int b,
                                    int alphaRange)
    {
        StringBuilder plainText;
        char cipherChar, plainChar;
        int modInverse;

        plainText = new StringBuilder(cipherText);

        modInverse = modInverse(a, alphaRange);

        for(int i = 0; i < cipherText.length(); i++)
        {
            cipherChar = cipherText.charAt(i);

            if((cipherChar >= 'a' && cipherChar <= 'z') || (cipherChar == '{'))
            {
                plainChar = (char)(cipherChar - 'a');


                plainChar = (char)((modInverse * (plainChar - b + 
                            alphaRange)) % alphaRange);
                plainChar = (char)(plainChar + 'a');
                plainText.setCharAt(i, plainChar);
            }
            else if((cipherChar >= 'A' && cipherChar <= 'Z') ||
                    (cipherChar == '['))
            {
                plainChar = (char)(cipherChar - 'A');
                plainChar = (char)((modInverse * (plainChar - b +
                             alphaRange)) % alphaRange);
                plainChar = (char)(plainChar + 'A');
                plainText.setCharAt(i, plainChar);
            }
            else
            {
                plainText.setCharAt(i, cipherChar);
            }
        }
        
        return plainText.toString();
    }

    public static int modInverse(int a, int m)
    {
        int modInverse, check;

        modInverse = 0;

        for(int i = 0; i < m; i++)
        {
            check = (a * i) % m;

            if(check == 1)
            {
                modInverse = i;
            }
        }

        return modInverse;
    }
}