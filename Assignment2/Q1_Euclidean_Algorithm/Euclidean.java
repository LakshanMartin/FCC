/***************************************************************************
 * Author: Lakshan Martin
 * 
 * Purpose: This program demonstrates the Euclidean Algorithm. 
 *          Algorithm can be run with either:
 *              - Default values (2543, 1672)
 *                  OR
 *              - By user entering values
 * 
 * Date last modified: 11/05/2020          
 ***************************************************************************/

import java.util.*;

public class Euclidean
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int selection, gcd, a, b;

        //Clear terminal screen
        System.out.print("\033[H\033[2J");


        do
        {
            System.out.println("THIS PROGRAM DEMONSTRATES THE EUCLIDEAN ALGORITHM");

            //Main menu options
            System.out.println(
                "\nPlease select from the following options to compute GCD: " +
                "\n1 - Use default values (2543, 1672)" +
                "\n2 - Enter own values" +
                "\n3 - Exit to terminal");
            System.out.print("\nSelection: ");

            try
            {
                selection = sc.nextInt(); //Store user input
            }
            catch(InputMismatchException e)
            {
                sc.next(); //Clear buffer
                selection = 0; //Initialise incorrect value to force loop
            }

            switch(selection)
            {
                case 1:
                    //Clear terminal screen
                    System.out.print("\033[H\033[2J");
        
                    gcd = euclidean(2543, 1672);
                    
                    System.out.println("\nGCD(2543, 1672) = " + gcd);
                    
                    pressEnterKey();
                break;

                case 2:
                    //Clear terminal screen
                    System.out.print("\033[H\033[2J");

                    a = inputKey('a');
                    b = inputKey('b');

                    //Clear terminal screen
                    System.out.print("\033[H\033[2J");

                    gcd = euclidean(a, b);
                    
                    System.out.println("\nGCD(" + a + ", " + b + ") = " + gcd);

                    pressEnterKey();
                break;

                case 3:
                    System.out.println("\nExiting to Terminal. Thank you.\n");
                break;

                default:
                    //Clear terminal screen
                    System.out.print("\033[H\033[2J");

                    System.out.println(
                        "ERROR: Selection must be an integer between " +
                        "1 - 3.\n");
                    
                    pressEnterKey();
                    selection = 0;
            }
        }
        while(selection != 3);
    }

    public static int euclidean(int a, int b)
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

    //Validate user input
    public static int inputKey(char value)
    {
        int input;
        String error;
        Scanner sc = new Scanner(System.in);

        error = "ERROR: Input must be an integer. Please try again.\n";

        //Validate integer input
        System.out.print("Enter interger value for [" + value + "]: ");
        while(!sc.hasNextInt())
        {
            System.out.println(error);
            System.out.print("Enter interger value for [" + value + "]: ");
            sc.next();
        }
        input = sc.nextInt();

        error = "ERROR: Input must be a positive integer. Please try again.\n";

        if(input < 0)
        {
            System.out.println(error);
            input = inputKey(value);
        }

        return input;
    }

    public static void pressEnterKey()
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("\nPress [ENTER] to return to Main Menu...");
        sc.nextLine();
        
        //Clear terminal screen
        System.out.print("\033[H\033[2J");
    }
}