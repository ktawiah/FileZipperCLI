package dev.practice.filezipper.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzipper {

    /**
     * Extracts the content of a zip file into the specified destination directory.
     * @param zipFilePath The path to the zip file.
     * @param destDirPath The directory where the zip content will be extracted.
     * @throws IOException If an I/O error occurs during extraction.
     */
    public void unzip(String zipFilePath, String destDirPath) throws IOException {
        File destDir = new File(destDirPath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        try (FileInputStream fis = new FileInputStream(zipFilePath);
             ZipInputStream zis = new ZipInputStream(fis)) {

            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                File extractedFile = new File(destDir, zipEntry.getName());

                if (zipEntry.isDirectory()) {
                    createDirectory(extractedFile);
                } else {
                    extractFile(zis, extractedFile);
                }

                zis.closeEntry();
            }
        }
    }

    /**
     * Creates a directory if it doesn't already exist.
     * @param dir The directory to be created.
     */
    private void createDirectory(File dir) {
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * Extracts a single file from the zip archive.
     * @param zis The ZipInputStream to read the file from.
     * @param file The file to be extracted.
     * @throws IOException If an I/O error occurs while writing the file.
     */
    private void extractFile(ZipInputStream zis, File file) throws IOException {
        // Ensure the parent directories exist
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = zis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }
}
