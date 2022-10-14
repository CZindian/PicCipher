package cz.osu.pic.cipher.application.exceptions;

/**
 * Should be called, when program catches any text to decode.
 */
public class AnyTextToDecryptException extends Exception {
    public AnyTextToDecryptException(){
        super("\t-there is no text to decrypt.");
    }
}
