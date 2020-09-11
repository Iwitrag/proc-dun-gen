package cz.iwitrag.procdungen.api.configuration;

import cz.iwitrag.procdungen.util.TextualShape;

/**
 * Shape of a room. Can be used in more room definitions.<br>
 * Shape can be defined either manually as {@link TextualShape textual shape} or by procedural {@link RoomTemplate} template.<br>
 * The field <i>name</i> is used as identificator when loading {@link DungeonConfiguration dungeon configuration} from input file.
 */
public class RoomShape {

    /** Unique name of this room shape */
    private String name;
    /** Room shape defined as text  (mutually exclusive with {@link cz.iwitrag.procdungen.api.configuration.RoomShape#template template})  */
    private TextualShape shape;
    /** Room shape defined as template (mutually exclusive with {@link cz.iwitrag.procdungen.api.configuration.RoomShape#shape shape}) */
    private RoomTemplate template;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TextualShape getShape() {
        return shape;
    }

    public void setShape(TextualShape shape) {
        this.shape = shape;
    }

    public RoomTemplate getTemplate() {
        return template;
    }

    public void setTemplate(RoomTemplate template) {
        this.template = template;
    }

    @Override
    public String toString() {
        return "RoomShape{" +
                "name='" + name + '\'' +
                ", shape=" + shape +
                ", template=" + template +
                '}';
    }
}
