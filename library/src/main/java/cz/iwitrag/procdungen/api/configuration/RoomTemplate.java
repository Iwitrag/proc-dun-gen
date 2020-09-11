package cz.iwitrag.procdungen.api.configuration;

import cz.iwitrag.procdungen.generator.templates.ParametrizedTemplate;
import cz.iwitrag.procdungen.generator.templates.TemplateFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Room template, which is used to generate shapes for rooms based on their parameters.<br>
 * For list of available template names refer to {@link TemplateFactory#getAvailableTemplatesNames()} and
 * for list of params refer to {@link ParametrizedTemplate#getParamNames()}.
 */
public class RoomTemplate {

    /** Which room template should be used, see {@link TemplateFactory#getAvailableTemplatesNames()} */
    private String name;
    /** Parameters provided to this template, see {@link cz.iwitrag.procdungen.generator.templates.ParametrizedTemplate#getParamNames()} at wanted template class */
    private Map<String, String> params = new LinkedHashMap<>();

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

    public void setParam(String name, String value) {
        if (name == null)
            return;
        if (value == null)
            params.remove(name);
        params.put(name, value);
    }

    @Override
    public String toString() {
        return "RoomTemplate{" +
                "name='" + name + '\'' +
                ", params=" + params +
                '}';
    }
}
