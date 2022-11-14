package cz.osu.pic.cipher.application.services;

import cz.osu.pic.cipher.application.exceptions.*;

import java.io.IOException;
import java.util.Scanner;

import static cz.osu.pic.cipher.utils.Utils.getConsoleInput;
import static cz.osu.pic.cipher.application.services.utils.Constant.SYMPTOM;

/**
 * Decrypts secret text from image.
 */
public class Decryption {

    //region Attributes
    private static String consoleInput;
    private static byte[] imageBytes;
    private static String imageString;
    private static String encryptedText;
    //endregion

    /**
     * Main decryption method.
     *
     * @throws AnyTextToDecryptException when there is any text to decrypt in given image
     */
    public static void run() {

        System.out.println("Type current path to your image.");
        System.out.println("\t-example: C:\\Users\\x\\y\\image.jpg");
        listenConsoleInput();

        System.out.println("Decrypted message:");
        decryptImage();
        resetAttributes();

    }

    private static void listenConsoleInput() {

        consoleInput = getConsoleInput(
                new Scanner(System.in)
        );
        loadImage();

    }

    /**
     * Loads image from disc and cashes for later use.
     */
    private static void loadImage() {

        try {
            imageBytes = StorageManager.loadImageBytes(consoleInput);

        } catch (FileOrDirectoryDoesNotExistException | IOException |
                 UnsupportedImageSuffixException | NoFileInUriException |
                 DirectoryDoesNotExistException e) {
            System.out.println(e.getMessage());
            System.out.println("Try again:");
            listenConsoleInput();

        }

    }

    /**
     * Decrypts encoded text from given image.
     *
     * @throws AnyTextToDecryptException when there is any text to decrypt in given image
     */
    private static void decryptImage() {

        try {
            String decryptedText = getDecryptedText();
            System.out.println(decryptedText);
            StorageManager.deleteExisting(consoleInput);

        } catch (AnyTextToDecryptException e) {
            System.out.println(e.getMessage());

        } catch (IOException e) {
            System.out.println("\t-" + e.getMessage());

        }

    }

    //region Util methods
    private static String getDecryptedText() throws AnyTextToDecryptException {

        imageString = new String(imageBytes);
        encryptedText = getEncryptedText(imageString);
        return encryptedText;

    }

    private static String getEncryptedText(String input) throws AnyTextToDecryptException {

        checkEncryptedImageValidity(input);
        encryptedText = getStringBetween(input);
        return encryptedText;

    }

    private static String getStringBetween(String input) {

        int lastSlashIndex = getLastSymptomIndex(input);
        int penultimateSlashIndex = getPenultimateSlashIndex(input, lastSlashIndex);
        return input.substring(penultimateSlashIndex, lastSlashIndex);

    }

    private static int getLastSymptomIndex(String input) {
        return input.lastIndexOf(SYMPTOM);
    }

    private static int getPenultimateSlashIndex(String input, int lastSlashIndex) {

        StringBuilder sb = new StringBuilder(input);
        sb.deleteCharAt(lastSlashIndex);
        return getLastSymptomIndex(sb.toString()) + SYMPTOM.length();

    }

    private static void checkEncryptedImageValidity(String input) throws AnyTextToDecryptException {

        if (!input.contains(SYMPTOM))
            throw new AnyTextToDecryptException();

    }
    //endregion

    /**
     * Resets class attributes.
     */
    private static void resetAttributes() {

        consoleInput = null;
        imageBytes = null;
        encryptedText = null;
        imageString = null;

    }

}
