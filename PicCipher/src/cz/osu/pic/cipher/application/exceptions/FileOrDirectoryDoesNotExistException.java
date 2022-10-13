package cz.osu.pic.cipher.application.exceptions;

public class FileOrDirectoryDoesNotExistException extends Exception {
    public FileOrDirectoryDoesNotExistException(String input) {
        super("-file or directory does not exist. Check last entered input: '" + input + "'.");
    }
}
