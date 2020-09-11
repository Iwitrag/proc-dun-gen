package cz.iwitrag.procdungen;

import cz.iwitrag.procdungen.api.ProcDunGenApi;
import cz.iwitrag.procdungen.api.configuration.DungeonConfiguration;
import cz.iwitrag.procdungen.api.configuration.InvalidConfigurationException;
import cz.iwitrag.procdungen.api.data.Dungeon;
import cz.iwitrag.procdungen.generator.GeneratorException;
import cz.iwitrag.procdungen.util.SupportedFileType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

/**
 * Entry point of application when launched from JAR file
 */
public class Main {

    /**
     * Entry point of application when launched from JAR file, see {@link #standardExecution()}
     */
    public static void main(String[] args) {
        standardExecution();
    }

    /**
     * This method gets executed when JAR file is launched<br>
     * Prompts user to provide path to input configuration file, reads it
     * and generates dungeon, which is saved to output file
     */
    private static void standardExecution() {
        // read stdin as URL to configuration file
        System.out.print("Enter path to configuration file: ");
        String input;
        try (InputStreamReader inputStream = new InputStreamReader(System.in);
             BufferedReader inputReader = new BufferedReader(inputStream)) {
            input = inputReader.readLine();
        } catch (IOException ex) {
            error("Error reading path: " + ex.getMessage() + "\n\n" + ExceptionUtils.getStackTrace(ex));
            return;
        }

        // Read configuration file
        File configurationFile = new File(input);
        try {
            if (!configurationFile.isFile()) {
                error("Error - file was not found or is invalid");
                return;
            }
        } catch (SecurityException ex) {
            error("Error reading configuration file: " + ex.getMessage() + "\n\n" + ExceptionUtils.getStackTrace(ex));
            return;
        }
        DungeonConfiguration dungeonConfiguration;
        try {
            dungeonConfiguration = DungeonConfiguration.fromFile(configurationFile);
        } catch (InvalidConfigurationException ex) {
            error("Error reading configuration: " + ex.getMessage() + "\n\n" + ExceptionUtils.getStackTrace(ex));
            return;
        }

        // Generate dungeon
        Dungeon dungeon;
        try {
            dungeon = new ProcDunGenApi().generateDungeon(dungeonConfiguration);
        } catch (InvalidConfigurationException ex) {
            error("Error in configuration while generating dungeon: " + ex.getMessage() + "\n\n" + ExceptionUtils.getStackTrace(ex));
            return;
        } catch (GeneratorException ex) {
            error("Error generating dungeon: " + ex.getMessage() + "\n\n" + ExceptionUtils.getStackTrace(ex));
            return;
        }

        // Save dungeon to file
        SupportedFileType fileType = dungeonConfiguration.getOutputFileType();
        File outputFile = new File("dungeon-" + getCurrentTimeStamp() + "." + fileType.toString().toLowerCase());
        try {
            if (fileType.equals(SupportedFileType.JSON))
                dungeon.toJson(outputFile);
            else if (fileType.equals(SupportedFileType.XML))
                dungeon.toXml(outputFile);
            else
                dungeon.toYaml(outputFile);
        } catch (IOException ex) {
            error("Error saving dungeon to file: " + ex.getMessage() + "\n\n" + ExceptionUtils.getStackTrace(ex));
        }
    }

    /**
     * Helper method to save configuration to file
     * @param configuration Created configuration to be saved
     * @param fileName Name of file, must use supported extension otherwise nothing happens
     */
    public static void saveDungeonConfiguration(DungeonConfiguration configuration, String fileName) {
        SupportedFileType supportedFileType = SupportedFileType.fromFileName(fileName);
        if (supportedFileType == null)
            return;
        try {
            if (supportedFileType == SupportedFileType.JSON)
                configuration.toJson(new File(fileName));
            else if (supportedFileType == SupportedFileType.XML)
                configuration.toXml(new File(fileName));
            else
                configuration.toYaml(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write error to error file with timestamp in name
     * @param content Error to be saved, raw text
     */
    private static void error(String content) {
        try {
            File errorFile = new File("err-" + getCurrentTimeStamp() + ".err");
            FileUtils.writeStringToFile(errorFile, content, StandardCharsets.UTF_8, false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Generates string timestamp from current date and time
     * @return String timestamp in format "dd.MM.yyyy-HH.mm.ss"
     */
    private static String getCurrentTimeStamp() {
        return new SimpleDateFormat("dd.MM.yyyy-HH.mm.ss").format(new java.util.Date());
    }

}
