public class Validation
{
    public static boolean validate(int a, int b, int alphaRange)
    {
        boolean isValid;

        isValid = true;

        if((a < 0) || (b > alphaRange))
        {
            isValid = false;
        }

        if(gcdFunc(a, alphaRange) != 1)
        {
            isValid = false;
        }

        if((b < 0) || (b > alphaRange))
        {
            isValid = false;
        }

        return isValid;
    }

    public static int gcdFunc(int a, int b)
    {
        int temp;

        while(b != 0)
        {
            temp = b;
            b = a % b;
            a = temp;
        }

        return a;
    }
}