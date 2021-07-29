//This class holds all the methods required for the DES process

public class DES
{
    public static String encryption(String line, String[] keys)
    {
        String left, right, temp, fBlock, cipher;
        String[] blocks;
        StringBuilder encrypt = new StringBuilder();

        //Pad line - See method for comments
        line = Format.padMsg(line);

        //Split line - See method for comments
        blocks = Format.msgSplit(line);

        //Encrypt each block of the line
        for(int i = 0; i < blocks.length; i++)
        {
            //STEP 1: Convert to Binary
            blocks[i] = Format.stringToBinary(blocks[i]);
            
            //STEP 2: Initial Permutation (IP) of line blocks
            cipher = permuteIP(blocks[i]);

            //STEP 3: Splits block into equal halves
            left = leftSplit(cipher);
            right = rightSplit(cipher);

            //STEP 4: 16 iterations of the formula:
            //        - Ln = Rn-1
            //        - Rn = Ln-1 XOR f(E(Rn-1) XOR Kn)
            //        Where E: Expansion of Rn-1 from 32bits to 48bits
            for(int j = 0; j < 16; j++)
            {
                temp = left;
                left = right;
                fBlock = fFunction(right, keys[j]);
                right = startXOR(temp, fBlock);
            }

            //STEP 5: After 16th iteration, switch left and right parts
            cipher = switchFunc(left, right);

            //STEP 6: Final Permutation to reverse bits
            cipher = permuteReverse(cipher);

            //STEP 7: Conver binary back to String
            cipher = Format.binaryToString(cipher);
            
            encrypt.append(cipher);
        }

        return encrypt.toString();
    }

    //The decryption process is basically identical to the encryption process
    //except for the keys being reversed before use in decryption.
    public static String decryption(String cipher, String[] keys)
    {
        String[] dKeys, blocks;
        String left, right, temp, fBlock, plain;
        StringBuilder decrypt = new StringBuilder();

        //Reverse order of keys
        //key[15] = dKeys[0]
        //key[14] = dKeys[1] ... etc.
        dKeys = KeyGen.reverseKeys(keys);

        cipher = Format.padMsg(cipher);
        blocks = Format.msgSplit(cipher);

        for(int i = 0; i < blocks.length; i++)
        {
            blocks[i] = Format.stringToBinary(blocks[i]);
            plain = permuteIP(blocks[i]);
            left = leftSplit(plain);
            right = rightSplit(plain);

            //Same 16 iteration as encryption, except using now reversed keys
            for(int j = 0; j < 16; j++)
            {
                temp = left;
                left = right;
                fBlock = fFunction(right, dKeys[j]);
                right = startXOR(temp, fBlock);
            }

            plain = switchFunc(left, right);
            plain = permuteReverse(plain);
            plain = Format.binaryToString(plain);

            decrypt.append(plain);
        }

        return decrypt.toString();
    }

    //Method take String input of the block message and rearranges the bits
    //according to the Initial Permutation (IP) table.
    //Returns String value to be stored back in msgArray
    public static String permuteIP(String msgBits)
    {
        Permutations perm = new Permutations();
        int[] IP;
        int permBit;
        StringBuilder permMsg = new StringBuilder();

        IP = perm.getIP();

        for(int i = 0; i < IP.length; i++)
        {
            permBit = IP[i] - 1; 
            permMsg.append(msgBits.charAt(permBit)); 
        }

        return permMsg.toString();
    }

    //Split message block (64bits) into left half (32bits)
    public static String leftSplit(String fullBlock)
    {
        String leftBlock;

        leftBlock = fullBlock.substring(0, 32);

        return leftBlock;
    }

    //Split message block (64bits) into right half (32bits)
    public static String rightSplit(String fullBlock)
    {
        String rightBlock;

        rightBlock = fullBlock.substring(32);

        return rightBlock;
    }

    public static String fFunction(String rightBits, String key)
    {
        String expansion, XORBits, address, sBits, fBits;
        Permutations getBox;
        int[][] sBox;
        int sCount;

        //STEP 1 - Expand each Right block from 32bits to 48bits using the 
        //         Expansion (E) Table
        expansion = permuteE(rightBits);

        //STEP 2 - XOR the expanded right block (48bits) with the keys.
        //       - [Kn XOR E(Rn-1)]
        XORBits = startXOR(expansion, key);

        //STEP 3 - Reduce to 32bit using S-Boxes
        sBits = "";
        sCount = 1;
        for(int i = 6; i <= XORBits.length(); i += 6)
        {
            address = XORBits.substring(i-6, i);
            getBox = new Permutations();
            sBox = getBox.getSBox(sCount);
            sBits += permuteSBox(address, sBox);
            sCount++;
        }
        
        //STEP 4 - Permute against Permutation (P) table
        fBits = permuteP(sBits);
        
        return fBits;    
    }

    //Method takes a String value of the right block and expands it from
    //32bits to 48bits using the Expansion (E) Table.
    public static String permuteE(String rightBlock)
    {
        Permutations perm = new Permutations();
        int[] E;
        int permBit;
        StringBuilder expansion = new StringBuilder();

        E = perm.getE();

        for(int i = 0; i < E.length; i++)
        {
            permBit = E[i] - 1;
            expansion.append(rightBlock.charAt(permBit));
            //checkBits(rightBlock, expansion.toString(), permBit, i);
        }

        return expansion.toString();
    }
    //This method takes the Strings from the block1 and block2 and 
    //passes each char bit to the xor() to build the resultant String to be
    //returned
    public static String startXOR(String block1, String block2)
    {
        String xor;

        xor = "";
        for(int i = 0; i < block1.length(); i++)
        {
            xor += xor(block1.charAt(i), block2.charAt(i));
        }

        return xor;
    }

    //Performs an XOR function on two imported bits and returns the
    //bit result
    private static String xor(char block1Bit, char block2Bit)
    {
        String result;

        if(block1Bit == block2Bit)
        {
            result = "0";
        }
        else
        {
            result = "1";
        }

        return result;
    }

    //This function uses the 6bit address to map to elements of the SBox.
    //This results in a reduction of bits from 48 to 32. 
    public static String permuteSBox(String address, int[][] sBox)
    {
        int row, col, sBoxValue;
        String rowBits, colBits, result;

        rowBits = Character.toString(address.charAt(0));
        rowBits += Character.toString(address.charAt(5));
        colBits = address.substring(1, 5);

        row = Integer.parseInt(rowBits, 2);
        col = Integer.parseInt(colBits, 2);

        sBoxValue = sBox[row][col];
        result = Format.intToBinary(sBoxValue, 4);
    
        return result;
    }

    public static String permuteP(String sBits)
    {
        Permutations perm = new Permutations();
        int[] P;
        int permBit;
        StringBuilder result = new StringBuilder();
        
        P = perm.getP();

        for(int i = 0; i < P.length; i++)
        {
            permBit = P[i] - 1;
            result.append(sBits.charAt(permBit));
        }

        return result.toString();
    }

    //Switch left and right blocks. 
    //Example: L16,R16 -> R16L16
    public static String switchFunc(String leftBlock, String rightBlock)
    {
        String switched;

        switched = rightBlock + leftBlock;

        return switched;
    }

    public static String permuteReverse(String input)
    {
        Permutations perm = new Permutations();
        int[] reverse;
        int permBit;
        StringBuilder finalBits = new StringBuilder();

        reverse = perm.getReverse();

        for(int i = 0; i < reverse.length; i++)
        {
            permBit = reverse[i] - 1;
            finalBits.append(input.charAt(permBit));
        }

        return finalBits.toString();
    }
}