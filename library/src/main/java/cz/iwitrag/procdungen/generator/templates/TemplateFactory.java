package cz.iwitrag.procdungen.generator.templates;

import cz.iwitrag.procdungen.util.IntRange;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * This class is internally used to produce shape templates
 */
public class TemplateFactory {

    /** Lists all available template names */
    public static List<String> getAvailableTemplatesNames() {
        return Arrays.asList("rectangle", "square");
    }

    /**
     * Returns constructed shape template based on its name and parameters
     * @param name Name of template, must be one of supported names, see {@link #getAvailableTemplatesNames()}
     * @param params Template parameters, see {@link ParametrizedTemplate#getParamNames() getParamNames()} at appropriate template class for list of params
     * @return Constructed template, which can be used to generate shapes
     * @throws TemplateException If template does not exist or wrong params were provided
     */
    public ParametrizedTemplate getTemplate(String name, Map<String, String> params) throws TemplateException {
        if (Arrays.asList("rectangle", "rect").contains(name.toLowerCase())) {
            return getRectangleTemplate(params);
        }
        if (Arrays.asList("square", "sq").contains(name.toLowerCase())) {
            return getSquareTemplate(params);
        }

        throw new TemplateException("Template " + name + " does not exist");
    }

    private RectangleTemplate getRectangleTemplate(Map<String, String> params) throws TemplateException {
        List<String> sizeXStrings = Arrays.asList("size-x", "sizex", "x-size", "xsize", "width");
        IntRange sizeX = null;
        List<String> sizeYStrings = Arrays.asList("size-y", "sizey", "y-size", "ysize", "height");
        IntRange sizeY = null;

        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey().toLowerCase();
                String value = entry.getValue();
                try {
                    // size-x
                    if (sizeX == null && sizeXStrings.contains(key)) {
                        try {
                            sizeX = IntRange.fromString(value, "-");
                        } catch (IllegalArgumentException ex) {
                            throw new TemplateException(ex.getMessage());
                        }
                    }
                    // size-y
                    if (sizeY == null && sizeYStrings.contains(key)) {
                        try {
                            sizeY = IntRange.fromString(value, "-");
                        } catch (IllegalArgumentException ex) {
                            throw new TemplateException(ex.getMessage());
                        }
                    }
                } catch (TemplateException ex) {
                    throw new TemplateException("Invalid parameter \"" + key + "\": " + ex.getMessage());
                }
            }

            return new RectangleTemplate(sizeX, sizeY);
        } catch (Exception ex) {
            throw new TemplateException("Error in rectangle template - " + ex.getMessage());
        }
    }

    private SquareTemplate getSquareTemplate(Map<String, String> params) throws TemplateException {
        List<String> sizeStrings = Arrays.asList("size", "length", "side");
        IntRange size = null;

        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey().toLowerCase();
                String value = entry.getValue();
                try {
                    // size
                    if (size == null && sizeStrings.contains(key)) {
                        try {
                            size = IntRange.fromString(value, "-");
                        } catch (IllegalArgumentException ex) {
                            throw new TemplateException(ex.getMessage());
                        }
                    }
                } catch (TemplateException ex) {
                    throw new TemplateException("Invalid parameter \"" + key + "\": " + ex.getMessage());
                }
            }

            return new SquareTemplate(size);
        } catch (Exception ex) {
            throw new TemplateException("Error in square template - " + ex.getMessage());
        }
    }

}
