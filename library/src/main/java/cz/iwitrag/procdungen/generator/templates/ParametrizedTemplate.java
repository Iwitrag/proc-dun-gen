package cz.iwitrag.procdungen.generator.templates;

import cz.iwitrag.procdungen.util.RandomGenerator;
import cz.iwitrag.procdungen.util.TextualShape;

import java.util.List;

/**
 * Shape template to generate shapes based on parameters
 */
public interface ParametrizedTemplate {

    /** Create textual shape from this template */
    TextualShape createShape();

    /** Create textual shape from this template using provided random generator */
    TextualShape createShape(RandomGenerator randomGenerator);

    /** Returns required parameter names */
    List<String> getParamNames();

}
