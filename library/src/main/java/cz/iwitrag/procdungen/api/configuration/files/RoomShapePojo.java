package cz.iwitrag.procdungen.api.configuration.files;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * This class is holder for data loaded from input file by Jackson library<br>
 * Represents room shape, which can be defined as parametrized template or ASCII shape
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JacksonXmlRootElement(localName = "RoomShape")
public class RoomShapePojo {

    private String name;
    private RoomTemplatePojo template;
    @JacksonXmlElementWrapper(useWrapping=false)
    private List<String> shape;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomTemplatePojo getTemplate() {
        return template;
    }

    public void setTemplate(RoomTemplatePojo template) {
        this.template = template;
    }

    public List<String> getShape() {
        return shape;
    }

    public void setShape(List<String> shape) {
        this.shape = shape;
    }

    @Override
    public String toString() {
        return "RoomShapePojo{" +
                "name='" + name + '\'' +
                ", template=" + template +
                ", shape='" + shape + '\'' +
                '}';
    }
}
