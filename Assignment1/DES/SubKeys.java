public class SubKeys
{
    //CLASS FIELDS
    private String shiftedKey; //L + R keys merged after shifts 

    //CONSTRUCTOR
    public SubKeys(String inLeft, String inRight, int shifts)
    {
        setShiftedKey(inLeft, inRight, shifts);
    }

    //SETTERS
    private void setShiftedKey(String inLeft, String inRight, int shifts)
    {
        if(shifts == 1)
        {
            leftShift1(inLeft, inRight);
        }
        else
        {
            leftShift2(inLeft, inRight);
        }
    }

    //GETTERS
    public String getShiftedKey()
    {
        return this.shiftedKey;
    }
    
    //FUNCTIONS
    private void leftShift1(String inLeft, String inRight)
    {
        StringBuilder left = new StringBuilder();
        StringBuilder right = new StringBuilder();

        for(int i = 0; i < inLeft.length() - 1; i++)
        {
            left.append(inLeft.charAt(i + 1));
            right.append(inRight.charAt(i + 1));
        }

        //Append first bits from left and right sub keys
        left.append(inLeft.charAt(0));
        right.append(inRight.charAt(0));

        this.shiftedKey = left.toString() + right.toString(); //Merge subkeys 
    }

    private void leftShift2(String inLeft, String inRight)
    {
        StringBuilder left = new StringBuilder();
        StringBuilder right = new StringBuilder();

        for(int i = 0; i < inLeft.length() - 2; i++)
        {
            left.append(inLeft.charAt(i + 2));
            right.append(inRight.charAt(i + 2));
        }

        //Append first bits from left and right sub keys
        left.append(inLeft.charAt(0));
        right.append(inRight.charAt(0));
        
        //Append second bits from the left and right sub keys
        left.append(inLeft.charAt(1));
        right.append(inRight.charAt(1));

        this.shiftedKey = left.toString() + right.toString(); //Merge subkeys
    }
}