import java.util.*;

public class LetterDistro
{
    public static HashMap<Character, Integer> letterDistro(String text)
    {
        HashMap<Character, Integer> charCountMap;

        charCountMap = new HashMap<Character, Integer>();
        char[] strArray;

        strArray = text.toLowerCase().toCharArray();

        for(char c : strArray)
        {
            if(charCountMap.containsKey(c))
            {
                charCountMap.put(c, charCountMap.get(c) + 1);
            }
            else
            {
                charCountMap.put(c, 1);
            }
        }

        return charCountMap;
    }

    public static void printDistro(HashMap<Character, Integer> charCount)
    {
        int frequency;

        for(Map.Entry entry : charCount.entrySet())
        {
            //Only output a-z charcter count
            if(((char)entry.getKey() >= 'a') && ((char)entry.getKey() <= 'z'))
            {
                System.out.print(entry.getKey() + " ");
                frequency = (int)entry.getValue();

                for(int i = 0; i < frequency; i++)
                {
                    System.out.print("#");
                }

                System.out.print(" " + frequency + "\n");
            }
        }
    }
}