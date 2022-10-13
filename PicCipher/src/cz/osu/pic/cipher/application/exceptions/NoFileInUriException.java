package cz.osu.pic.cipher.application.exceptions;

public class NoFileInUriException extends Exception {
    public NoFileInUriException(String uri) {
        super("\t-there is not any file in uri: '" + uri + "'");
    }
}
