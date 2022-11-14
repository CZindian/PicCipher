package cz.osu.pic.cipher.application.services;

import cz.osu.pic.cipher.application.exceptions.*;

import java.io.IOException;
import java.util.Scanner;

import static cz.osu.pic.cipher.utils.Utils.getConsoleInput;
import static cz.osu.pic.cipher.application.services.utils.Constant.SYMPTOM;

/**
 * Encrypts user text to given image.
 */
public class Encryption {

    //region Attributes
    private static String consoleInput;
    private static String consoleInputPath;
    private static byte[] imageBytes;
    //endregion

    /**
     * Main encryption method.
     */
    public static void run() {
        System.out.println("Type current path to your image.");
        System.out.println("\t-example: C:\\Users\\x\\y\\image.jpg");
        listenConsoleInput();

        System.out.println("Type text to encode:");
        consoleInput = getConsoleInput(
                new Scanner(System.in)
        );

        System.out.println("Type current path, where to save image with hidden text:");
        System.out.println("\t-example: C:\\Users\\x\\y\\");
        listenEncode();

        resetAttributes();
    }

    private static void listenConsoleInput() {

        consoleInput = getConsoleInput(
                new Scanner(System.in)
        );
        loadImage();

    }

    private static void listenEncode() {

        consoleInputPath = getConsoleInput(
                new Scanner(System.in)
        );
        encodeConsoleInputToImage();

    }

    /**
     * Encodes user text to given image.
     */
    private static void encodeConsoleInputToImage() {

        byte[] encodedConsoleInput = getEncodedConsoleInput();
        byte[] encodedData = getEncodedData(encodedConsoleInput);
        copyByteArraysTo(encodedData, encodedConsoleInput);

        try {
            StorageManager.saveEncodedData(encodedData, consoleInputPath);

        } catch (IOException e) {
            System.out.println("\t-" + e.getMessage());

        } catch (DirectoryDoesNotExistException | FileOrDirectoryDoesNotExistException e) {
            System.out.println(e.getMessage());
            System.out.println("Type valid path:");
            listenEncode();

        }

    }

    //region Util methods
    private static void copyByteArraysTo(byte[] encodedData, byte[] encodedConsoleInput) {
        System.arraycopy(imageBytes, 0, encodedData, 0, imageBytes.length);
        System.arraycopy(encodedConsoleInput, 0, encodedData, imageBytes.length, encodedConsoleInput.length);
    }

    private static byte[] getEncodedData(byte[] encodedConsoleInput) {
        return new byte[imageBytes.length + encodedConsoleInput.length];
    }

    private static byte[] getEncodedConsoleInput() {
        consoleInput = getConsoleInputWithSymptom();
        return consoleInput.getBytes();
    }

    private static String getConsoleInputWithSymptom() {
        return SYMPTOM + consoleInput + SYMPTOM;
    }

    private static void loadImage() {

        try {
            imageBytes = StorageManager.loadImageBytes(consoleInput);

        } catch (FileOrDirectoryDoesNotExistException | IOException |
                 NoFileInUriException | UnsupportedImageSuffixException e) {
            System.out.println(e.getMessage());
            System.out.println("Try again:");
            listenConsoleInput();

        }

    }
    //endregion

    /**
     * Resets class attributes.
     */
    private static void resetAttributes() {

        consoleInput = null;
        consoleInputPath = null;
        imageBytes = null;

    }

}
