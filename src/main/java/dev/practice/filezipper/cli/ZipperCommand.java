package dev.practice.filezipper.cli;

import dev.practice.filezipper.helper.Unzipper;
import dev.practice.filezipper.helper.Zipper;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

import java.io.IOException;

@Command
public class ZipperCommand {
    private final Zipper zipper;
    private final Unzipper unzipper;

    public ZipperCommand() {
        this.zipper = new Zipper();
        this.unzipper = new Unzipper();
    }

    /**
     * Shell command that compresses input file or directory into a .zip file
     * @param inputPath Path of source file or directory
     * @param outputPath Expected Path of zip file
     * @return String message indicating that source file or directory has been compressed
     * @throws IOException If an I/O Exception occurs during file or directory compression
     */
    @Command(command = "compress")
    public String compressFileOrDirectory (
            @Option(longNames = "input", shortNames = 'i', description = "File input path.") String inputPath,
            @Option(longNames = "output", shortNames = 'o', description = "Zip file output path.") String outputPath
    ) throws IOException {
        zipper.zip(inputPath, outputPath);
        return "Input %s successfully compressed to %s".concat(System.lineSeparator()).formatted(inputPath, outputPath);
    }

    /**
     * Decompresses zipped file into original file or directory.
     * @param inputPath Path of zipped file.
     * @param outputPath Path for decompressed file or folder.
     * @return String message indicating that zipped file has been decompressed
     * @throws IOException If an I/O Exception occurs during zipped file decompression.
     */
    @Command(command = "decompress")
    public String decompressFileOrDirectory (
            @Option(longNames = "input", shortNames = 'i', description = "File input path.") String inputPath,
            @Option(longNames = "output", shortNames = 'o', description = "Zip file output path.") String outputPath
    ) throws IOException {
        unzipper.unzip(inputPath, outputPath);
        return "Input %s successfully compressed to %s".concat(System.lineSeparator()).formatted(inputPath, outputPath);
    }

}

