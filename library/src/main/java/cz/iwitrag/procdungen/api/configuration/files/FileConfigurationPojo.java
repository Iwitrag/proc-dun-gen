package cz.iwitrag.procdungen.api.configuration.files;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import cz.iwitrag.procdungen.util.SupportedFileType;

import java.util.List;

/**
 * This class is holder for data loaded from input file by Jackson library<br>
 * Represents root configuration with all rooms, their shapes and other options
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JacksonXmlRootElement(localName = "DungeonConfiguration")
public class FileConfigurationPojo {

    private List<RoomShapePojo> roomShapes;
    private List<RoomPojo> rooms;
    private SupportedFileType outputFileType;
    private String seed;

    public List<RoomShapePojo> getRoomShapes() {
        return roomShapes;
    }

    public void setRoomShapes(List<RoomShapePojo> roomShapes) {
        this.roomShapes = roomShapes;
    }

    public List<RoomPojo> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomPojo> rooms) {
        this.rooms = rooms;
    }

    public SupportedFileType getOutputFileType() {
        return outputFileType;
    }

    public void setOutputFileType(SupportedFileType outputFileType) {
        this.outputFileType = outputFileType;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    @Override
    public String toString() {
        return "FileConfigurationPojo{" +
                "roomShapes=" + roomShapes +
                ", rooms=" + rooms +
                ", outputFileType=" + outputFileType +
                ", seed='" + seed + '\'' +
                '}';
    }
}
