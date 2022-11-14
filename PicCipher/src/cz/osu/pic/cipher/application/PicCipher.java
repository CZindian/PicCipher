package cz.osu.pic.cipher.application;

import cz.osu.pic.cipher.application.exceptions.AnyTextToDecryptException;
import cz.osu.pic.cipher.application.exceptions.IllegalConsoleInputException;
import cz.osu.pic.cipher.application.services.Decryption;
import cz.osu.pic.cipher.application.services.Encryption;

import java.util.Scanner;

import static cz.osu.pic.cipher.utils.Utils.getConsoleInput;

public class PicCipher {

    private static String consoleInput;

    /**
     * Main program run method.
     */
    public static void run() {

        System.out.println("Welcome!");
        System.out.println("This app will encrypt / decrypt your message.");
        System.out.println("\t-note: as input image use .jpg, .png or .jpeg");
        listenConsoleInput();

    }

    //region Util methods
    private static void listenConsoleInput() {

        resetConsoleInput();
        System.out.println("To start, choose 'e' for encryption or 'd' for decryption.");
        consoleInput = getConsoleInput(
                new Scanner(System.in)
        );
        processConsoleInput();

    }

    /**
     * Decides to run encryption or decryption on user console input.
     */
    private static void processConsoleInput() {

        try {
            switch (consoleInput) {
                case "e" -> Encryption.run();
                case "d" -> Decryption.run();
                default -> throw new IllegalConsoleInputException(consoleInput);
            }
            listenConsoleInputExit();

        } catch (IllegalConsoleInputException e) {
            System.out.println(e.getMessage());
            System.out.println("Choose 'e' for encryption or 'd' for decryption");
            listenConsoleInput();

        }

    }

    private static void listenConsoleInputExit() {

        System.out.println("Do you want to exit the program? (y/n)");
        consoleInput = getConsoleInput(
                new Scanner(System.in)
        );

        if (consoleInput.equals("y"))
            System.exit(1);
        else
            listenConsoleInput();

    }
    //endregion

    /**
     * Resets user console input, if program continues
     * after listenConsoleInputExit() is called.
     */
    private static void resetConsoleInput() {
        consoleInput = null;
    }

}
