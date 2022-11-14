package cz.osu.pic.cipher.application.exceptions;

public class IllegalPathEndException extends Exception {
    public IllegalPathEndException(String imgUri) {
        super("\t-path '" + imgUri + "' should be ended with '\\'");
    }
}
