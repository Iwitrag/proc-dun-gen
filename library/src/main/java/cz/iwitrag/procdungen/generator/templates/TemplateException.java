package cz.iwitrag.procdungen.generator.templates;

/**
 * Represents error which occurred during template loading or generating of shape
 */
public class TemplateException extends Exception {
    public TemplateException(String message) {
        super(message);
    }
}