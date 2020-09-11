package cz.iwitrag.procdungen.util;

import org.apache.commons.io.FilenameUtils;

/**
 * Enum of supported file types for configurations and dungeon output
 */
public enum SupportedFileType {
    JSON, XML, YML, YAML;
    public static SupportedFileType valueOfIgnoreCase(String value) {
        for (SupportedFileType supportedFileType : SupportedFileType.values()) {
            if (supportedFileType.name().equalsIgnoreCase(value))
                return supportedFileType;
        }
        return null;
    }
    public static SupportedFileType fromFileName(String fileName) {
        String extension = FilenameUtils.getExtension(fileName);
        return SupportedFileType.valueOfIgnoreCase(extension);
    }
}
