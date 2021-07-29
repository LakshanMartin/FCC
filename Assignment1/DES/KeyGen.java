//This class contains all the methods used to generate the 16 48bit keys

public class KeyGen
{
    //DES key generation process
    public static String[] keyGen(String key)
    {
        String binKey, trim, permKey, leftKey, rightKey;
        String[] shiftedKeys = new String[16];

        //Adjustments to key
        key = Format.padOrChopKey(key);

        //Convert key to binary form
        binKey = Format.stringToBinary(key);

        //trim whitespaces
        trim = binKey.replaceAll("\\s", "");

        //GENERATE KEYS
        //Step 1: 56-bit permutation (PC1)
        permKey = permutePC1(trim);

        //Step 2: Left and Right 28-bit sub keys
        leftKey = leftKey(permKey);
        rightKey = rightKey(permKey);

        //Step 3: Create keys of merged left and right shifted subkeys 
        shiftedKeys = shiftKeys(leftKey, rightKey);

        //Step 4: Permutate shifted keys
        for(int i = 0; i < shiftedKeys.length; i++)
        {
            shiftedKeys[i] = permutePC2(shiftedKeys[i]);
        }

        //Return String array of 16 keys
        return shiftedKeys;
    }

    //Step 1: 56-bit permutation (PC1)
    public static String permutePC1(String fullKey)
    {
        StringBuilder permKey;
        int[] PC1;
        int permBit;
        Permutations permFullKey = new Permutations();

        PC1 = permFullKey.getPC1();

        permKey = new StringBuilder();

        for(int i = 0; i < 56; i++)
        {
            permBit = PC1[i];
            permKey.append(fullKey.charAt(permBit));
        }

        return permKey.toString();
    }

    //Step 2: Split key into equal halves
    public static String leftKey(String fullKey)
    {
        String leftKey;

        leftKey = fullKey.substring(0, 28);

        return leftKey;
    } 
    
    public static String rightKey(String fullKey)
    {
        String rightKey;

        rightKey = fullKey.substring(28);

        return rightKey;
    } 

    //Step 3: Create 16 separate keys
    public static String[] shiftKeys(String leftKey, String rightKey)
    {
        Permutations shifts = new Permutations();
        int[] shiftValue = new int[15];
        String[] shiftedKeys = new String[16]; //Merged L and R after shift
        String tempLeft, tempRight;

        shiftValue = shifts.getShift(); //Get shift table        
        tempLeft = leftKey;
        tempRight = rightKey;

        for(int i = 0; i < shiftedKeys.length; i++)
        {
            SubKeys subs = new SubKeys(tempLeft, tempRight, shiftValue[i]);
            shiftedKeys[i] = subs.getShiftedKey();
            tempLeft = leftKey(shiftedKeys[i]);
            tempRight = rightKey(shiftedKeys[i]);
        }

        return shiftedKeys;
    }

    public static String permutePC2(String key)
    {
        StringBuilder permkey;
        int[] PC2;
        int permBit;
        Permutations permFullKey = new Permutations();

        PC2 = permFullKey.getPC2();

        permkey = new StringBuilder();

        for(int i = 0; i < PC2.length; i++)
        {
            //-1 from PC2 values to align with starting [0] index of key
            permBit = PC2[i] - 1;
            permkey.append(key.charAt(permBit));
        }

        return permkey.toString();
    }

    //This method is used to reverse the order of the encryption keys
    //to be used in the decryption process 
    public static String[] reverseKeys(String[] keys)
    {
        String[] revKeys = new String[keys.length];
        int size;

        size = revKeys.length;

        for(int i = 0; i < revKeys.length; i++)
        {
            revKeys[size - 1] = keys[i];
            size--;
        }

        return revKeys;
    }
}