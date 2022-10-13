package cz.osu.pic.cipher.application.exceptions;

public class UnsupportedImageSuffixException extends Exception {
    public UnsupportedImageSuffixException(String suffix) {
        super("-image suffix: '" + suffix + "' is not supported");
    }
}
