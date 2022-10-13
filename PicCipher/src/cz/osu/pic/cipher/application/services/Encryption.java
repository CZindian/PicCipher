package cz.osu.pic.cipher.application.services;

import cz.osu.pic.cipher.application.exceptions.FileOrDirectoryDoesNotExistException;
import cz.osu.pic.cipher.application.exceptions.UnsupportedImageSuffixException;

import java.io.IOException;
import java.util.Scanner;

import static cz.osu.pic.cipher.application.Utils.getConsoleInput;
import static cz.osu.pic.cipher.application.services.utils.Constant.SYMPTOM;

public class Encryption {

    //region Attributes
    private static String consoleInput;
    private static byte[] imageBytes;
    //endregion

    public static void run() {
        System.out.println("Type complete url to your image.");
        System.out.println("\t-example: root/dir/dir2/di3/image.jpg");
        listenConsoleInput();

        System.out.println("Type text to encode. Use english alphabet only!");
        encodeConsoleInput();
    }

    private static void listenConsoleInput() {
        consoleInput = getConsoleInput(
                new Scanner(System.in)
        );
        loadImage();
    }

    private static void encodeConsoleInput() {
        consoleInput = getConsoleInput(
                new Scanner(System.in)
        );
        encodeConsoleInputToImage();
    }

    //region Util methods
    private static void encodeConsoleInputToImage() {
        byte[] encodedConsoleInput = getEncodedConsoleInput();
        byte[] encodedData = getEncodedData(encodedConsoleInput);
        copyByteArraysTo(encodedData, encodedConsoleInput);

        try {
            StorageManager.saveEncodedData(encodedData);
            resetAttributes();
        } catch (IOException e) {
            System.out.println("-" + e.getMessage());
        }

    }

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
        } catch (FileOrDirectoryDoesNotExistException | IOException | UnsupportedImageSuffixException e) {
            System.out.println("-" + e.getMessage());
            System.out.println("Try again:");
            listenConsoleInput();
        }

    }
    //endregion

    private static void resetAttributes() {
        consoleInput = null;
        imageBytes = null;
    }

}
