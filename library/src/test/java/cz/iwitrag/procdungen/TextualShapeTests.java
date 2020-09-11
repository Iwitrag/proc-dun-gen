package cz.iwitrag.procdungen;

import cz.iwitrag.procdungen.util.TextualShape;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextualShapeTests {

    private TextualShape getTextualShape(String shape) {
        return new TextualShape(shape,  '.', 'x', '\n');
    }

    @Test
    void testTopTrim() {
        TextualShape textualShape = getTextualShape("....\n....\n.x..\nxxxx");
        assertEquals(".x..\nxxxx", textualShape.getShape());
    }

    @Test
    void testBottomTrim() {
        TextualShape textualShape = getTextualShape(".x..\nxxxx\n....\n....");
        assertEquals(".x..\nxxxx", textualShape.getShape());
    }

    @Test
    void testLeftTrim() {
        TextualShape textualShape = getTextualShape("..xx\n...x\n..xx");
        assertEquals("xx\n.x\nxx", textualShape.getShape());
    }

    @Test
    void testRightTrim() {
        TextualShape textualShape = getTextualShape("xx..\nx...\nxx..");
        assertEquals("xx\nx.\nxx", textualShape.getShape());
    }

    @Test
    void testAllTrims() {
        TextualShape textualShape = getTextualShape(".....\n...x.\n..x..\n...x.\n.....\n.....");
        assertEquals(".x\nx.\n.x", textualShape.getShape());
    }

    @Test
    void testInvalidCharacters() {
        assertThrows(IllegalArgumentException.class, () -> {
            getTextualShape("..x\n..y\nxx.");
        });
    }

    @Test
    void testRowsWithUnequalLength() {
        assertThrows(IllegalArgumentException.class, () -> {
            getTextualShape("xxxx\nxxx\nxxxx");
        });
    }

}
