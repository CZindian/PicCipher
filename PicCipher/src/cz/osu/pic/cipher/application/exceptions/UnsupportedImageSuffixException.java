package cz.osu.pic.cipher.application.exceptions;

/**
 * Should be called, when program catches unsupported suffix format.
 */
public class UnsupportedImageSuffixException extends Exception {
    public UnsupportedImageSuffixException(String suffix) {
        super("-image suffix: '" + suffix + "' is not supported");
    }
}
