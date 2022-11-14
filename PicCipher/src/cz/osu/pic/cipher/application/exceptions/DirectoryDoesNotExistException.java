package cz.osu.pic.cipher.application.exceptions;

public class DirectoryDoesNotExistException extends Exception {
    public DirectoryDoesNotExistException(String imgUri) {
        super("\t-directory '" + imgUri + "' does not exist");
    }
}
