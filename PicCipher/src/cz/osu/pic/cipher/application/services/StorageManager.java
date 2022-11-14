package cz.osu.pic.cipher.application.services;

import cz.osu.pic.cipher.application.exceptions.*;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * Manipulates save, load, delete.
 */
public class StorageManager {

    private static String imgName;

    /**
     * Loads image from disc
     *
     * @param uri complete uri to img file (includes image name too)
     * @return image asy byte array
     * @throws FileOrDirectoryDoesNotExistException occurs, when directory or file that does not exist
     * @throws UnsupportedImageSuffixException      occurs, when file contain unsupported suffix (.jpg .jpeg .png are supported)
     * @throws IOException                          might occur, when loading data from disc
     * @throws NoFileInUriException                 occurs, when uri does not contain file
     */
    public static byte[] loadImageBytes(String uri)
            throws FileOrDirectoryDoesNotExistException, UnsupportedImageSuffixException,
            IOException, NoFileInUriException {
        checkValidityOf(uri);
        checkValidityOfImgType(uri);

        imgName = new File(uri).getName();
        return getImageBytes(uri);
    }

    /**
     * Saves encrypted image to disc
     *
     * @param data encrypted image byte array
     * @throws IOException might occur, when write data to disc
     */
    public static void saveEncodedData(byte[] data, String imgUri)
            throws IOException, DirectoryDoesNotExistException, IllegalPathEndException {
        String newUri = getNewUri(imgUri);
        new File(newUri);

        OutputStream out = new FileOutputStream(newUri);
        out.write(data);
        out.flush();
        out.close();
    }

    /**
     * Deletes file from disc. Should be used, when text is encrypted.
     *
     * @throws IOException might occur, when file cannot be deleted
     */
    public static void deleteExisting(String imgUri) throws IOException {
        File f = new File(imgUri);
        if (!f.delete())
            throw new IOException("File was not deleted");
    }

    private static String getNewUri(String imgUri) throws DirectoryDoesNotExistException, IllegalPathEndException {
        isDirectoryValid(imgUri);
        isPathValid(imgUri);
        String[] imgNameParts = imgName.split("\\.");
        return imgUri + imgNameParts[0] + "_" + getLocalDateTime() + "." + imgNameParts[1];
    }

    private static void isPathValid(String imgUri) throws IllegalPathEndException {
        char lastChar = 'x';
        for (int i = 0; i < imgUri.length(); i++) {
            if (i == imgUri.length()-1){
                lastChar = imgUri.charAt(i);
            }
        }
        if (lastChar != '\\'){
            throw new IllegalPathEndException(imgUri);
        }
    }

    //region Util methods
    private static byte[] getImageBytes(String uri) throws IOException {
        return FileUtils.readFileToByteArray(
                new File(uri)
        );
    }

    private static void checkValidityOf(String uri)
            throws FileOrDirectoryDoesNotExistException {
        Path path = Paths.get(uri);
        if (!Files.exists(path))
            throw new FileOrDirectoryDoesNotExistException(uri);
    }

    private static void isDirectoryValid(String imgUri) throws DirectoryDoesNotExistException {
        Path parent = Paths.get(imgUri).getParent();
        if (!Files.isDirectory(parent)) {
            throw new DirectoryDoesNotExistException(imgUri);
        }
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

    private static String getLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        String ret = localDateTime.getHour() + "_" + localDateTime.getMinute() + "_" +
                localDateTime.getSecond() + "_" + localDateTime.getDayOfMonth() + "_" +
                localDateTime.getMonthValue() + "_" + localDateTime.getYear();
        return ret;
    }
    //endregion

}
