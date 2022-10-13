package cz.osu.pic.cipher.application.exceptions;

public class AnyTextToDecryptException extends Exception {
    public AnyTextToDecryptException(){
        super("\t-there is no text to decrypt.");
    }
}
