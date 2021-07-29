/***************************************************************************
 * Author: Lakshan Martin
 * 
 * Purpose: This program demonstrates the implementation of the revised 
 *          Affine Cipher (using MOD 27).
 *          The user is given the option to utilise both the Encryption and 
 *          Decrpytion functions of the program by either:
 *              - Reading text from a .txt file
 *                  OR
 *              - Inputting a line of text themselves
 * 
 * Date last modified: 02/04/2020          
 ***************************************************************************/

import java.util.*;

public class Main 
{
    public static void main(String[] args) 
    {
        int a, b;
        boolean isValid;

        //Clear terminal screen
        System.out.print("\033[H\033[2J");

        System.out.println(
            "THIS PROGRAM DEMONSTRATES THE IMPLEMENTATION OF THE REVISED " + 
            "AFFINE CIPHER (using MOD 27)\n" +
            "------------------------------------------------------------" +
            "----------------------------\n");

        System.out.println("\nTo begin, please enter the cipher keys.");
        
        //Retrieve and validate cipher keys
        //Loop until keys entered are valid
        do
        {
            //User input keys
            a = inputKey('a');
            b = inputKey('b');

            //Validate keys
            isValid = Validation.validate(a, b, 27);
        
            if (isValid == true) 
            {
                System.out.println("\nThe keys entered are valid.");
            } 
            else 
            {
                System.out.println("\nThe keys entered aren't valid. " +
                                      "Try again.\n");
            }
        }
        while(isValid == false);

        mainMenu(a, b);
    }

    public static void mainMenu(int a, int b)
    {
        Scanner sc = new Scanner(System.in);
        int selection;

        do
        {
            //Main menu options
            System.out.println(
                "\nPlease select from the following options: " +
                "\n1 - Encrypt text" +
                "\n2 - Decrypt text" +
                "\n3 - Output to file valid keys" +
                "\n4 - Exit to terminal");
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
                //Encrypt text
                case 1: 
                    EncryptionMenu.menu(a, b);
                break;

                case 2:
                    DecryptionMenu.menu(a, b);
                break;

                case 3:
                    //Clear terminal screen
                    System.out.print("\033[H\033[2J");

                    FileIO.outputValidKeys(27);
                    System.out.println(
                        "Valid keys output to file: [validKeys.txt]\n");
                    
                        //Prompt user to press [Enter] before returning to menu
                    pressEnterKey();
                break;

                case 4:
                    System.out.println("\nExiting to Terminal. Thank you.\n");
                break;

                default:
                    //Clear terminal screen
                    System.out.print("\033[H\033[2J");

                    System.out.println(
                        "ERROR: Selection must be an integer between " +
                        "1 - 4.\n");
                    
                    pressEnterKey();
                    selection = 0;
            }
        }
        while(selection != 4); //Loop until 4 (exit) is selected
    }

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

    public static void inputError()
    {
        //Clear terminal screen
        System.out.print("\033[H\033[2J");

        System.out.println(
            "ERROR: Must perform either of the following first:" + 
            "\n\t- Read from text file" +
            "\n\t\t-OR-" +
            "\n\t- Input text manually\n");

        //Prompt user to press [Enter] before returning to menu
        pressEnterKey();
    }
}