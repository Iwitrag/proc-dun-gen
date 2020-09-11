package cz.iwitrag.procdungen.api.data.files;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

/**
 * This class is holder for data outputted to file by Jackson library<br>
 * Represents room with its id, name, shape (list of rows), position, rotation and rooms connected to it
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomPojo {

    private int id;
    private String name;
    @JacksonXmlElementWrapper(useWrapping=false)
    private List<String> shape;
    private String shapeName;
    private PositionPojo position;
    @JacksonXmlProperty(localName = "connection")
    @JacksonXmlElementWrapper(localName = "connections")
    private List<Integer> connections;
    private int rotated;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getShape() {
        return shape;
    }

    public void setShape(List<String> shape) {
        this.shape = shape;
    }

    public String getShapeName() {
        return shapeName;
    }

    public void setShapeName(String shapeName) {
        this.shapeName = shapeName;
    }

    public PositionPojo getPosition() {
        return position;
    }

    public void setPosition(PositionPojo position) {
        this.position = position;
    }

    public List<Integer> getConnections() {
        return connections;
    }

    public void setConnections(List<Integer> connections) {
        this.connections = connections;
    }

    public int getRotated() {
        return rotated;
    }

    public void setRotated(int rotated) {
        this.rotated = rotated;
    }
}
