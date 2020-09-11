package cz.iwitrag.procdungenweb.model;

import cz.iwitrag.procdungen.generator.implementations.physical.ConfiguredGenerator;
import cz.iwitrag.procdungen.util.SupportedFileType;
import org.springframework.web.multipart.MultipartFile;

/**
 * Holder for visualisation settings set by user in form
 */
public class DrawOptions {

    /** Manually entered configuration */
    private String stringConfiguration;
    /** Configuration uploaded as file */
    private MultipartFile fileConfiguration;
    /** File type to parse manual configuration as */
    private SupportedFileType supportedFileType;
    /** Resolution of drawn graphics */
    private String resolution = "2";
    /** Whether to draw room names over them */
    private Boolean drawNames = true;
    /** Whether to draw how are rooms connected with each other in graph */
    private Boolean drawConnections = true;
    /** Whether to draw corridors between rooms */
    private Boolean drawCorridors = true;
    /** Whether to draw rooms in colors, gray color is used otherwise */
    private Boolean randomColors = true;
    /** After which phase generator should be stopped */
    private ConfiguredGenerator.Phase stopAfterPhase = ConfiguredGenerator.Phase.CREATE_CORRIDORS;

    public String getStringConfiguration() {
        return stringConfiguration;
    }

    public void setStringConfiguration(String stringConfiguration) {
        this.stringConfiguration = stringConfiguration;
    }

    public SupportedFileType getSupportedFileType() {
        return supportedFileType;
    }

    public void setSupportedFileType(SupportedFileType supportedFileType) {
        this.supportedFileType = supportedFileType;
    }

    public MultipartFile getFileConfiguration() {
        return fileConfiguration;
    }

    public void setFileConfiguration(MultipartFile fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Boolean getDrawNames() {
        return drawNames;
    }

    public void setDrawNames(Boolean drawNames) {
        this.drawNames = drawNames;
    }

    public Boolean getDrawConnections() {
        return drawConnections;
    }

    public void setDrawConnections(Boolean drawConnections) {
        this.drawConnections = drawConnections;
    }

    public Boolean getDrawCorridors() {
        return drawCorridors;
    }

    public void setDrawCorridors(Boolean drawCorridors) {
        this.drawCorridors = drawCorridors;
    }

    public Boolean getRandomColors() {
        return randomColors;
    }

    public void setRandomColors(Boolean randomColors) {
        this.randomColors = randomColors;
    }

    public ConfiguredGenerator.Phase getStopAfterPhase() {
        return stopAfterPhase;
    }

    public void setStopAfterPhase(ConfiguredGenerator.Phase stopAfterPhase) {
        this.stopAfterPhase = stopAfterPhase;
    }
}