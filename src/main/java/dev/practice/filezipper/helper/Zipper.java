package dev.practice.filezipper.helper;

import java.io.*;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper {

    /**
     * Compresses the given file or directory into a zip archive.
     * @param sourcePath The path of the file or directory to be zipped.
     * @param zipFileName The name of the output zip file (without the ".zip" extension).
     * @throws IOException If an I/O error occurs during zipping.
     */
    public void zip(String sourcePath, String zipFileName) throws IOException {
        File sourceFile = new File(sourcePath);

        if (!sourceFile.exists()) {
            throw new FileNotFoundException("Source not found: " + sourcePath);
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(zipFileName + ".zip");
             ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)) {

            zipRecursively(sourceFile, sourceFile.getName(), zipOutputStream);
        }
    }

    /**
     * Recursively zips files and directories.
     * @param fileOrDir The file or directory to be zipped.
     * @param zipEntryName The name of the current entry in the zip file.
     * @param zos The ZipOutputStream to write entries to.
     * @throws IOException If an I/O error occurs while reading files or writing to the zip stream.
     */
    private void zipRecursively(File fileOrDir, String zipEntryName, ZipOutputStream zos) throws IOException {
        if (fileOrDir.isDirectory()) {
            zipDirectory(fileOrDir, zipEntryName, zos);
        } else {
            zipFile(fileOrDir, zipEntryName, zos);
        }
    }

    /**
     * Adds a directory and its contents to the zip archive.
     * @param dir The directory to be zipped.
     * @param parentDir The name of the parent directory in the zip file.
     * @param zos The ZipOutputStream to write the directory and its contents to.
     * @throws IOException If an I/O error occurs.
     */
    private void zipDirectory(File dir, String parentDir, ZipOutputStream zos) throws IOException {
        // Add a trailing '/' to the zip entry for directories
        if (Objects.requireNonNull(dir.listFiles()).length == 0) {
            zos.putNextEntry(new ZipEntry(parentDir + "/"));
            zos.closeEntry(); // Close empty directory entry
        } else {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                zipRecursively(file, parentDir + "/" + file.getName(), zos);
            }
        }
    }

    /**
     * Adds a single file to the zip archive.
     * @param file The file to be zipped.
     * @param zipEntryName The name of the file in the zip archive.
     * @param zos The ZipOutputStream to write the file entry to.
     * @throws IOException If an I/O error occurs while reading the file or writing to the zip stream.
     */
    private void zipFile(File file, String zipEntryName, ZipOutputStream zos) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            ZipEntry zipEntry = new ZipEntry(zipEntryName);
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                zos.write(buffer, 0, bytesRead);
            }

            zos.closeEntry();
        }
    }
}
