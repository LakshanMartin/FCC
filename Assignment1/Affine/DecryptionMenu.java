import java.util.*;

public class DecryptionMenu
{
    public static void menu(int a, int b)
    {
        Scanner sc = new Scanner(System.in);
        int selection;
        String encryptedText, decryptedText, cipherText, output;
        HashMap<Character, Integer> origCharCount, decryCharCount;
        
        encryptedText = "";
        decryptedText = "";
        cipherText = "";

        //Clear terminal screen
        System.out.print("\033[H\033[2J");

        do
        {
            //Menu1 options
            System.out.println(
                "\nPlease select from the following options: " +
                 "\n1 - Read from text file" +
                 "\n2 - Input text manually" +
                 "\n3 - Display & Output letter distribution of Original " +
                        "text & Decrypted text" +
                 "\n4 - Display & Output results of Decryption" +
                 "\n5 - Return to Main Menu");
            System.out.print("\nSelection: ");

            try
            {
                selection = sc.nextInt(); //Store user input
                sc.nextLine(); //Clear remianing line
            }
            catch(InputMismatchException e) //Catch no-int data types
            {
                sc.next(); //Clear buffer
                selection = 0; //Initialise incorrect value to force loop  
            }

            switch(selection)
            {
                //Read in text file
                case 1: 
                    //Clear terminal screen
                    System.out.print("\033[H\033[2J");

                    //Read file to string variable
                    encryptedText = FileIO.fileToString("Encryption.txt");

                    //Decrypt text
                    decryptedText = Affine.decryption(encryptedText, a, b, 27);
                    //Encrypt text
                    cipherText = Affine.encryption(decryptedText, a, b, 27);
                    
                    //Prompt user to press [Enter] before returning to menu
                    Main.pressEnterKey();

                    selection = -1; //Loop menu without error message
                break;

                case 2: //Input text manually
                    //Clear terminal screen
                    System.out.print("\033[H\033[2J");

                    System.out.print("\nEnter text to be decrypted: ");
                    encryptedText = sc.next();
                    
                    //Decrypt text
                    decryptedText = Affine.decryption(encryptedText, a, b, 27);
                    //Encrypt text
                    cipherText = Affine.encryption(decryptedText, a, b, 27);
                    
                    System.out.println("\nSuccessfully decrypted text.");
                    //Prompt user to press [Enter] before returning to menu
                    Main.pressEnterKey();

                    selection = -1; //Loop menu without error message
                break;

                case 3:
                    if(!encryptedText.isEmpty())
                    {
                        //Count and display letter distribution of 
                        //encrypted and decrypted texts
                        origCharCount = LetterDistro.letterDistro(encryptedText);
                        System.out.println(
                            "\nLETTER OCCURANCES WITHIN THE ORIGINAL TEXT:");
                        LetterDistro.printDistro(origCharCount);

                        decryCharCount = LetterDistro.letterDistro(decryptedText);
                        System.out.println(
                            "\nLETTER OCCURANCES WITHIN THE DECRYPTED TEXT:");
                        LetterDistro.printDistro(decryCharCount);

                        //Output letter distributions to file
                        System.out.println("\nFILE OUTPUT:");
                        System.out.print("\t- Decryption text Letter " +
                                        "Distribution ");
                        FileIO.outputDistro(origCharCount, decryCharCount,
                                            "DecryptLetterDistro.txt");
                        
                        //Prompt user to press [Enter] before returning to menu
                        Main.pressEnterKey();
                    }
                    else
                    {
                        Main.inputError();  
                        selection = -1;
                    }    
                break;

                //Output valid keys to file
                case 4:
                    if(!decryptedText.isEmpty())
                    {                     
                        //Clear terminal screen
                        System.out.print("\033[H\033[2J");

                        output = "ORIGINAL TEXT:\n" + encryptedText + 
                                 "\n\nDECRYPTED TEXT:\n" + decryptedText +
                                 "\n\nENCRYPTED TEXT:\n" + cipherText;
                        
                        System.out.println(output);

                        //Compare original text to encrypted text
                        if(encryptedText.compareTo(cipherText) == 0)
                        {
                            System.out.println("\nORIGINAL TEXT AND " +
                                            "ENCRYPTED TEXT ARE THE SAME.");
                        }
                        else
                        {
                            System.out.println("NOT THE SAME");
                        }
                        
                        System.out.println("\nFILE OUTPUTS:");
                        //Output just decryption to file
                        System.out.print("\t- Decrypted text ");
                        FileIO.outputFile(decryptedText, "Decryption.txt");

                        //Output full results to file
                        System.out.print("\t- Full Decryption results ");
                        FileIO.outputFile(output, "FullDecryptResults.txt");

                        Main.pressEnterKey();
                    }  
                    else
                    {
                        Main.inputError();
                        selection = -1;
                    }                      
                break;

                case 5: //Exit
                    //Clear screen
                    System.out.print("\033[H\033[2J");
                break;

                default:
                    //Clear terminal screen
                    System.out.print("\033[H\033[2J");

                    System.out.println(
                        "ERROR: Selection must be an integer between " +
                        "1 - 5.\n");
                    Main.pressEnterKey();
                    selection = 0;
            }
        }
        while(selection != 5); //Loop until 5 (exit) is selected
    }
}