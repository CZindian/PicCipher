package cz.osu.pic.cipher.application;

import cz.osu.pic.cipher.application.exceptions.IllegalConsoleInputException;
import cz.osu.pic.cipher.application.services.Decryption;
import cz.osu.pic.cipher.application.services.Encryption;

import java.util.Scanner;

import static cz.osu.pic.cipher.application.Utils.getConsoleInput;

public class PicCipher {

    private static String consoleInput;

    public static void run() {
        System.out.println("Welcome!\nThis app will encrypt / decrypt your message.");
        System.out.println("To start choose 'e' for encryption or 'd' for decryption.");
        listenConsoleInput();
    }

    private static void listenConsoleInput() {
        consoleInput = getConsoleInput(
                new Scanner(System.in)
        );
        processConsoleInput();
    }

    private static void processConsoleInput() {

        try {
            switch (consoleInput) {
                case "e" -> Encryption.run();
                case "d" -> Decryption.run();
                default -> throw new IllegalConsoleInputException(consoleInput);
            }
        } catch (IllegalConsoleInputException e) {
            System.out.println(e.getMessage());
            System.out.println("Choose 'e' for encryption or 'd' for decryption");
            listenConsoleInput();
        }

    }

}
