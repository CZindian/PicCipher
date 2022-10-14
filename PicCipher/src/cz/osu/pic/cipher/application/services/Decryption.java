package cz.osu.pic.cipher.application.services;

import cz.osu.pic.cipher.application.exceptions.NoFileInUriException;
import cz.osu.pic.cipher.application.exceptions.FileOrDirectoryDoesNotExistException;
import cz.osu.pic.cipher.application.exceptions.AnyTextToDecryptException;
import cz.osu.pic.cipher.application.exceptions.UnsupportedImageSuffixException;

import java.io.IOException;
import java.util.Scanner;

import static cz.osu.pic.cipher.application.Utils.getConsoleInput;
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
     * @throws AnyTextToDecryptException when there is any text to decrypt in given image
     */
    public static void run() throws AnyTextToDecryptException {
        System.out.println("Type complete url to your image.\n\t-example: root/dir/dir2/di3/image.jpg");
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
                 UnsupportedImageSuffixException | NoFileInUriException e) {
            System.out.println(e.getMessage());
            System.out.println("Try again:");
            listenConsoleInput();
        }

    }

    /**
     * Decrypts encoded text from given image.
     * @throws AnyTextToDecryptException when there is any text to decrypt in given image
     */
    private static void decryptImage() throws AnyTextToDecryptException {
        String decryptedText = null;

        try {
            decryptedText = getDecryptedText();
            System.out.println(decryptedText);

            StorageManager.deleteExisting();
        } catch (IOException e) {
            System.out.println(e.getMessage());
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

    private static String getDataToDelete(String encodedText) {
        return SYMPTOM + encodedText + SYMPTOM;
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
