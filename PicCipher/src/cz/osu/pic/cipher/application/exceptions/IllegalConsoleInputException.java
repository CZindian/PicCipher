package cz.osu.pic.cipher.application.exceptions;

public class IllegalConsoleInputException extends Exception {
    public IllegalConsoleInputException(String input) {
        super("-unexpected value: '" + input + "'.");
    }
}
