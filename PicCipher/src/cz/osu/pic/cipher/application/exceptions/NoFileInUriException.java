package cz.osu.pic.cipher.application.exceptions;

/**
 * Should be called, when program catches any file in given Uri.
 */
public class NoFileInUriException extends Exception {
    public NoFileInUriException(String uri) {
        super("\t-there is not a file in this path : '" + uri + "'");
    }
}
