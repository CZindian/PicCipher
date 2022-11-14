package cz.osu.pic.cipher.application.exceptions;

/**
 * Should be called, when program catches unexpected console value.
 */
public class IllegalConsoleInputException extends Exception {
    public IllegalConsoleInputException(String input) {
        super("\t-unexpected value: '" + input + "'");
    }
}
