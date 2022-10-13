package cz.osu.pic.cipher.application.services;

import cz.osu.pic.cipher.application.exceptions.FileOrDirectoryDoesNotExistException;
import cz.osu.pic.cipher.application.exceptions.UnsupportedImageSuffixException;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StorageManager {

    private static String imgUri;

    public static byte[] loadImageBytes(String uri)
            throws FileOrDirectoryDoesNotExistException, UnsupportedImageSuffixException, IOException {
        checkValidityOf(uri);
        checkValidityOfImgType(uri);
        setImgUri(uri);
        return getImageBytes(uri);
    }

    public static void saveEncodedData(byte[] data) throws IOException {
        OutputStream out = new FileOutputStream(imgUri);
        out.write(data);
        out.flush();
        out.close();
        resetAttributes();
    }

    public static void deleteExisting() throws IOException {
        File f = new File(imgUri);
        if (!f.delete()) throw new IOException("File was not deleted");
    }

    //region Setters
    private static void setImgUri(String uri) {
        imgUri = uri;
    }
    //endregion

    //region Util methods
    private static byte[] getImageBytes(String uri) throws IOException {
        return FileUtils.readFileToByteArray(
                new File(uri)
        );
    }

    private static void checkValidityOf(String uri) throws FileOrDirectoryDoesNotExistException {
        Path path = Paths.get(uri);
        if (!Files.exists(path))
            throw new FileOrDirectoryDoesNotExistException(uri);
    }

    private static void checkValidityOfImgType(String uri) throws UnsupportedImageSuffixException {
        int dotIndex = uri.lastIndexOf(".");
        String suffix = uri.substring(dotIndex);

        switch (suffix) {
            case ".jpeg", ".jpg", ".png" -> {
            }
            default -> throw new UnsupportedImageSuffixException(suffix);
        }
    }
    //endregion

    private static void resetAttributes() {
        imgUri = null;
    }

}
