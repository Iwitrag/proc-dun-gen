package cz.iwitrag.procdungen.generator.implementations.physical;

import cz.iwitrag.procdungen.api.configuration.Room;
import cz.iwitrag.procdungen.api.configuration.RoomTemplate;
import cz.iwitrag.procdungen.api.data.Dungeon;
import cz.iwitrag.procdungen.api.data.Point;
import cz.iwitrag.procdungen.generator.GeneratorException;
import cz.iwitrag.procdungen.generator.templates.TemplateException;
import cz.iwitrag.procdungen.generator.templates.TemplateFactory;
import cz.iwitrag.procdungen.util.RandomGenerator;
import cz.iwitrag.procdungen.util.TextualShape;

/**
 * Populates dungeon with rooms
 */
public class RoomPopulator {

    private Dungeon dungeon;
    private RandomGenerator randomGenerator;
    private TemplateFactory templateFactory = new TemplateFactory();
    private int freeRoomId = 0;

    public RoomPopulator(Dungeon dungeon, RandomGenerator randomGenerator) {
        this.dungeon = dungeon;
        this.randomGenerator = randomGenerator;
    }

    public void generateRoomsFromConfig(Room room, Point<Integer> origin, int XDeviation, int YDeviation) throws GeneratorException {
        try {
            createRooms(room, origin, XDeviation, YDeviation);
        } catch (Exception ex) {
            throw new GeneratorException("Error while generating rooms: " + ex.getMessage());
        }
    }

    public void createRooms(Room room, Point<Integer> origin, int XDeviation, int YDeviation) throws GeneratorException {
        XDeviation = Math.abs(XDeviation);
        YDeviation = Math.abs(YDeviation);
        if (origin == null)
            origin = new Point<>(0, 0);
        int amount = room.getAmount().getRandom(randomGenerator);
        if (amount <= 0)
            return;
        for (int i = 0; i < amount; i++) {
            TextualShape shape = room.getShape().getShape();
            if (shape == null) {
                RoomTemplate template = room.getShape().getTemplate();
                try {
                    shape = templateFactory.getTemplate(template.getName(), template.getParams()).createShape(randomGenerator);
                } catch (TemplateException ex) {
                    throw new GeneratorException("Template error: " + ex.getMessage());
                }
            }
            if (room.isRotate()) {
                shape = new TextualShape(shape);
                shape.rotate(randomGenerator.randomInt(0, 4));
            }
            int x = randomGenerator.randomInt(origin.getX() - XDeviation, origin.getX() + XDeviation+1);
            int y = randomGenerator.randomInt(origin.getY() - YDeviation, origin.getY() + YDeviation+1);
            cz.iwitrag.procdungen.api.data.Room newRoom = new cz.iwitrag.procdungen.api.data.Room(freeRoomId++, room.getName(), x - (shape.getWidth()/2), y - (shape.getHeight()/2), shape);
            newRoom.setShapeName(room.getShape().getName());
            dungeon.addRoom(newRoom);
        }
    }

}
