package cz.iwitrag.procdungen.api.data.files;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

/**
 * This class is holder for data outputted to file by Jackson library<br>
 * Represents corridor connecting rooms, its id, shape (list of rows), position and which rooms it connects
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CorridorPojo {

    private int id;
    @JacksonXmlElementWrapper(useWrapping=false)
    private List<String> shape;
    private PositionPojo position;
    @JacksonXmlProperty(localName = "connection")
    @JacksonXmlElementWrapper(localName = "connections")
    private List<Integer> connections;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getShape() {
        return shape;
    }

    public void setShape(List<String> shape) {
        this.shape = shape;
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
}
