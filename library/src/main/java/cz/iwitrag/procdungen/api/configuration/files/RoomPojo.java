package cz.iwitrag.procdungen.api.configuration.files;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * This class is holder for data loaded from input file by Jackson library<br>
 * Represents one room with its name, name of defined shape and amount to generate
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JacksonXmlRootElement(localName = "Room")
public class RoomPojo {

    private String name;
    private String shape;
    private String amount;
    private String rotate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRotate() {
        return rotate;
    }

    public void setRotate(String rotate) {
        this.rotate = rotate;
    }

    @Override
    public String toString() {
        return "RoomPojo{" +
                "name='" + name + '\'' +
                ", shape='" + shape + '\'' +
                ", amount='" + amount + '\'' +
                ", rotate=" + rotate +
                '}';
    }
}
