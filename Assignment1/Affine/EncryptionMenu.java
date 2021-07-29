import java.util.*;

public class EncryptionMenu
{
    public static void menu(int a, int b)
    {
        Scanner sc = new Scanner(System.in);
        int selection;
        String plainText, cipherText, decryptedText, output;
        HashMap<Character, Integer> origCharCount, encryCharCount;
        
        plainText = "";
        cipherText = "";
        decryptedText = "";

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
                        "text & Encrypted text" +
                 "\n4 - Display & Output results of Encryption" +
                 "\n5 - Return to Main Menu");
            System.out.print("\nSelection: ");

            try
            {
                selection = sc.nextInt(); //Store user input
                sc.nextLine(); //Clear remaining line
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
                    plainText = FileIO.fileToString("testfile-Affine.txt");

                    //Encrypt text
                    cipherText = Affine.encryption(plainText, a, b, 27);
                    //Decrypt text
                    decryptedText = Affine.decryption(cipherText, a, b, 27);
                    
                    //Prompt user to press [Enter] before returning to menu
                    Main.pressEnterKey();

                    selection = -1; //Loop menu without error message
                break;

                case 2: //Input text manually
                    //Clear terminal screen
                    System.out.print("\033[H\033[2J");

                    System.out.print("\nEnter text to be encrypted: ");
                    plainText = sc.nextLine();
                    
                    //Encrypt text
                    cipherText = Affine.encryption(plainText, a, b, 27);
                    //Decrypt text
                    decryptedText = Affine.decryption(cipherText, a, b, 27);
                    
                    //Prompt user to press [Enter] before returning to menu
                    Main.pressEnterKey();

                    selection = -1; //Loop menu without error message
                break;

                case 3:
                    if(!plainText.isEmpty())
                    {
                        //Count and display letter distribution of 
                        //original and encrypted texts
                        origCharCount = LetterDistro.letterDistro(plainText);
                        System.out.println(
                            "\nLETTER OCCURANCES WITHIN THE ORIGINAL TEXT:");
                        LetterDistro.printDistro(origCharCount);

                        encryCharCount = LetterDistro.letterDistro(cipherText);
                        System.out.println(
                            "\nLETTER OCCURANCES WITHIN THE ENCRYPTED TEXT:");
                        LetterDistro.printDistro(encryCharCount);

                        //Output letter distributions to file
                        System.out.println("\nFILE OUTPUT:");
                        System.out.print("\t- Encryption text Letter " +
                                        "Distribution ");
                        FileIO.outputDistro(origCharCount, encryCharCount, 
                                            "EncryptLetterDistro.txt");
                                            
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
                    if(!cipherText.isEmpty())
                    {                     
                        //Clear terminal screen
                        System.out.print("\033[H\033[2J");

                        output = "ORIGINAL TEXT:\n" + plainText + 
                                 "\n\nENCRYPTED TEXT:\n" + cipherText +
                                 "\n\nDECRYPTED TEXT:\n" + decryptedText;
                        
                        System.out.println(output);

                        //Compare original text to decrypted text
                        if(plainText.compareTo(decryptedText) == 0)
                        {
                            System.out.println("\nORIGINAL TEXT AND " + 
                                            "DECRYPTED TEXT ARE THE SAME.");
                        }
                        else
                        {
                            System.out.println("NOT THE SAME");
                        }
                        
                        System.out.println("\nFILE OUTPUTS:");
                        //Output just encryption to file
                        System.out.print("\t- Encrypted text ");
                        FileIO.outputFile(cipherText, "Encryption.txt");

                        //Output full results to file
                        System.out.print("\t- Full Encryption results ");
                        FileIO.outputFile(output, "FullEncryptResults.txt");

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