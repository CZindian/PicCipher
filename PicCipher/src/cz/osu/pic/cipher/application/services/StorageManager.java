package cz.osu.pic.cipher.application.services;

import cz.osu.pic.cipher.application.exceptions.NoFileInUriException;
import cz.osu.pic.cipher.application.exceptions.FileOrDirectoryDoesNotExistException;
import cz.osu.pic.cipher.application.exceptions.UnsupportedImageSuffixException;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Manipulates save, load, delete.
 */
public class StorageManager {

    private static String imgUri;

    /**
     * Loads image from disc
     * @param uri complete uri to img file (includes image name too)
     * @return image asy byte array
     * @throws FileOrDirectoryDoesNotExistException occurs, when directory or file that does not exist
     * @throws UnsupportedImageSuffixException occurs, when file contain unsupported suffix (.jpg .jpeg .png are supported)
     * @throws IOException might occur, when loading data from disc
     * @throws NoFileInUriException occurs, when uri does not contain file
     */
    public static byte[] loadImageBytes(String uri)
            throws FileOrDirectoryDoesNotExistException, UnsupportedImageSuffixException,
            IOException, NoFileInUriException {
        checkValidityOf(uri);
        checkValidityOfImgType(uri);
        setImgUri(uri);
        return getImageBytes(uri);
    }

    /**
     * Saves encrypted image to disc
     * @param data encrypted image byte array
     * @throws IOException might occur, when write data to disc
     */
    public static void saveEncodedData(byte[] data) throws IOException {
        OutputStream out = new FileOutputStream(imgUri);
        out.write(data);
        out.flush();
        out.close();
        resetAttributes();
    }

    /**
     * Deletes file from disc. Should be used, when text is encrypted.
     * @throws IOException might occur, when file cannot be deleted
     */
    public static void deleteExisting() throws IOException {
        File f = new File(imgUri);
        if (!f.delete())
            throw new IOException("File was not deleted");
        resetAttributes();
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

    private static void checkValidityOf(String uri)
            throws FileOrDirectoryDoesNotExistException, NoFileInUriException {
        Path path = Paths.get(uri);
        if (!Files.exists(path))
            throw new FileOrDirectoryDoesNotExistException(uri);
    }

    private static void checkValidityOfImgType(String uri)
            throws UnsupportedImageSuffixException, NoFileInUriException {
        int dotIndex = uri.lastIndexOf(".");
        String suffix;

        try {
            suffix = uri.substring(dotIndex);
        } catch (StringIndexOutOfBoundsException e) {
            throw new NoFileInUriException(uri);
        }

        switch (suffix) {
            case ".jpeg", ".jpg", ".png" -> {
            }
            default -> throw new UnsupportedImageSuffixException(suffix);
        }

    }
    //endregion

    /**
     * Resets class attributes.
     */
    private static void resetAttributes() {
        imgUri = null;
    }

}
