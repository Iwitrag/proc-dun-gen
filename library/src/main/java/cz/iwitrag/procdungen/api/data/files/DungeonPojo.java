package cz.iwitrag.procdungen.api.data.files;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * This class is holder for data outputted to file by Jackson library<br>
 * Represents whole dungeon with lists of rooms and corridors, used seed and grid composed of cells
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JacksonXmlRootElement(localName = "dungeon")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DungeonPojo {

    private GridPojo grid;
    @JacksonXmlProperty(localName = "room")
    @JacksonXmlElementWrapper(localName = "rooms")
    private List<RoomPojo> rooms;
    @JacksonXmlProperty(localName = "corridor")
    @JacksonXmlElementWrapper(localName = "corridors")
    private List<CorridorPojo> corridors;
    private long seed;

    public GridPojo getGrid() {
        return grid;
    }

    public void setGrid(GridPojo grid) {
        this.grid = grid;
    }

    public List<RoomPojo> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomPojo> rooms) {
        this.rooms = rooms;
    }

    public List<CorridorPojo> getCorridors() {
        return corridors;
    }

    public void setCorridors(List<CorridorPojo> corridors) {
        this.corridors = corridors;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }
}
