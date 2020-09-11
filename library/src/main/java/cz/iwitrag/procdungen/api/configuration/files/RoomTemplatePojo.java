package cz.iwitrag.procdungen.api.configuration.files;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.Map;

/**
 * This class is holder for data loaded from input file by Jackson library<br>
 * Represents requested room shape template with params
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JacksonXmlRootElement(localName = "Template")
public class RoomTemplatePojo {

    private String name;
    private Map<String, String> params;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "TemplatePojo{" +
                "name='" + name + '\'' +
                ", params=" + params +
                '}';
    }
}
