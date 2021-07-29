public class Format
{
    public static String padOrChopKey(String input)
    {
        int padding;

        if(input.length() < 8) //Pad input to 8 char length (64 bits)
        {
            padding = 8 - input.length();
            
            for(int i = 0; i < padding; i++)
            {
                input = input + "0"; //Padding with zeroes
            }
        }
        else //Chop input down to 8 char length (64 bits)
        {
            input = input.substring(0, 8);
        }

        return input;
    }    

    public static String stringToBinary(String input)
    {
        String binary;
        
        binary = "";

        for(int i = 0; i < input.length(); i++)
        {
            binary += binaryRep(input.charAt(i)); 
        }        
        
        return binary;
    }

    //Adapted from program created by me for 
    //Lab 1, Question 4: BinaryRep.java
    private static String binaryRep(char ch)
    {
        String binary;
        int padding;

        binary = Integer.toBinaryString(ch);
        
        //Pad zeroes to maintain 8bit formatting [01100001]
        if(binary.length() < 8)
        {
            padding = 8 - binary.length();

            for(int i = 0; i < padding; i++)
            {
                binary = "0" + binary;
            }
        }

        return binary;
    }

    //Message String must be split into separate substrings of length 8, 
    //to accommodate the 64bit DES Initial Permutation (IP) table. 
    //This method pads the message according to the message length MOD 8.
    //Example: 
    //  Message length = 300
    //  padding = length MOD 8 = 4
    //  Therefore, pad message by 4 characters to split the message evenly 
    //  into 38 substrings of 8 characters (64bits each)
    public static String padMsg(String input)
    {
        int length, mod, padding;

        length = input.length();
        mod = length % 8;
        padding = 8 - mod; 

        if(mod != 0)
        {
            for(int i = 0; i < padding; i++)
            {
                input += "0"; //Padding with zeroes

            }
        }
        
        return input;
    }

    //Split the full message into substrings of 8 characters (64bit) to 
    //accommodate the 64bit DES Initial Permuation (IP) table.
    //Returns a String array containing substrings
    public static String[] msgSplit(String input)
    {
        int size, strInc;
        String[] split;

        //Initialise array size according to number of substrings required 
        //from input
        size = input.length() / 8;  
        split = new String[size];
        strInc = 0; //Incrementing value to capture substrings of 8 chars

        for(int i = 0; i < split.length; i++)
        {
            split[i] = input.substring(strInc, strInc + 8);
            strInc += 8;
        }

        return split;
    }

    //Method used to display or output bit Strings in an easy to read format
    //Method takes the bit String, and an int to determine the number of bits 
    //per block to display.
    //Example: bitString = 000000000000
    //         size = 6
    //         result = 000000 000000 <--- blocks of 6 bits
    /*public static String formatBitString(String bitString, int size)
    {
        StringBuilder result = new StringBuilder();

        result.append(bitString);

        for(int i = size; i <= bitString.length(); i += size+1)
        {
            result.insert(i, " ");
        }


        return result.toString();
    }*/

    public static String intToBinary(int input, int num)
    {
        String binary;
        int padding;

        binary = Integer.toBinaryString(input);
        padding = num - binary.length();
    
        if(padding > 0)
        {
            for(int i = 0; i < padding; i++)
            {
                binary = "0" + binary;
            }
        }

        return binary;
    }

    public static String binaryToString(String binary)
    {
        int charVal;
        String binVal;
        StringBuilder str = new StringBuilder();

        for(int i = 8; i <= binary.length(); i += 8)
        {
            binVal = binary.substring(i-8, i);
            charVal = Integer.parseInt(binVal, 2);
            str.append((char)charVal);
        }
    
        return str.toString();
    }

    //Removes trailing zeroes from end of each decrypted text line
    public static String removeTrailingZeroes(String line)
    {
        StringBuilder fixed = new StringBuilder(line);

        while(fixed.length() > 0 && fixed.charAt(fixed.length() - 1) == '0')
        {
            fixed.setLength(fixed.length() - 1);
        }

        return fixed.toString();
    } 
}