package cz.osu.pic.cipher.application.exceptions;

/**
 * Should be called, when program catches not existing
 * file or directory.
 */
public class FileOrDirectoryDoesNotExistException extends Exception {
    public FileOrDirectoryDoesNotExistException(String input) {
        super("\t-file or directory does not exist. Check last entered input: '" + input + "'.");
    }
}
