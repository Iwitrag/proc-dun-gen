package cz.iwitrag.procdungen.util;

import cz.iwitrag.procdungen.api.configuration.DungeonConfiguration;
import cz.iwitrag.procdungen.api.configuration.Room;
import cz.iwitrag.procdungen.api.configuration.RoomShape;
import cz.iwitrag.procdungen.api.configuration.RoomTemplate;

/**
 * Useful class to generate test configurations
 */
public class TestConfiguration {

    /** Creates configuration which consists of small rectangular rooms, heart-shaped rooms and bigger square BOSS rooms */
    public static DungeonConfiguration createEasyTestConfiguration(String seed) {
        DungeonConfiguration dungeonConfiguration = new DungeonConfiguration();
        if (seed != null && !seed.isEmpty())
            dungeonConfiguration.setSeed(seed);

        Room basicRoom = new Room();
        basicRoom.setName("basic");
        basicRoom.setAmount(new IntRange(10, 20));
        RoomShape basicRoomShape = new RoomShape();
        RoomTemplate basicRoomTemplate = new RoomTemplate();
        basicRoomTemplate.setName("rectangle");
        basicRoomTemplate.setParam("size-x", "5-10");
        basicRoomTemplate.setParam("size-y", "4-8");
        basicRoomShape.setName("small_rect");
        basicRoomShape.setTemplate(basicRoomTemplate);
        basicRoom.setShape(basicRoomShape);
        dungeonConfiguration.addRoom(basicRoom);
        dungeonConfiguration.addRoomShape(basicRoomShape);

        Room heartRoom = new Room();
        heartRoom.setName("heart");
        heartRoom.setAmount(new IntRange(5, 10));
        RoomShape heartRoomShape = new RoomShape();
        heartRoomShape.setName("heart");
        heartRoomShape.setShape(new TextualShape(
                "..x...x..\n"+
                        ".xxx.xxx.\n"+
                        "xxxxxxxxx\n"+
                        ".xxxxxxx.\n"+
                        "..xxxxx..\n"+
                        "...xxx...\n"+
                        "....x....\n", '.', 'x', '\n'));
        heartRoom.setShape(heartRoomShape);
        heartRoom.setRotate(true);
        dungeonConfiguration.addRoom(heartRoom);
        dungeonConfiguration.addRoomShape(heartRoomShape);

        Room bossRoom = new Room();
        bossRoom.setName("boss");
        bossRoom.setAmount(new IntRange(0, 3));
        RoomShape bossRoomShape = new RoomShape();
        RoomTemplate bossRoomTemplate = new RoomTemplate();
        bossRoomTemplate.setName("square");
        bossRoomTemplate.setParam("size", "20-25");
        bossRoomShape.setName("big_square");
        bossRoomShape.setTemplate(bossRoomTemplate);
        bossRoom.setShape(bossRoomShape);
        dungeonConfiguration.addRoom(bossRoom);
        dungeonConfiguration.addRoomShape(bossRoomShape);

        return dungeonConfiguration;
    }

    /** Creates configuration which consists of many small rectangular and square rooms, two kinds of shaped rooms, bigger square BOSS rooms and one huge MEGA room */
    public static DungeonConfiguration createMediumTestConfiguration(String seed) {
        DungeonConfiguration dungeonConfiguration = new DungeonConfiguration();
        if (seed != null && !seed.isEmpty())
            dungeonConfiguration.setSeed(seed);

        Room basicRoom = new Room();
        basicRoom.setName("basic");
        basicRoom.setAmount(new IntRange(50, 100));
        RoomShape basicRoomShape = new RoomShape();
        RoomTemplate basicRoomTemplate = new RoomTemplate();
        basicRoomTemplate.setName("rectangle");
        basicRoomTemplate.setParam("size-x", "5-10");
        basicRoomTemplate.setParam("size-y", "4-8");
        basicRoomShape.setName("small_rect");
        basicRoomShape.setTemplate(basicRoomTemplate);
        basicRoom.setShape(basicRoomShape);
        dungeonConfiguration.addRoom(basicRoom);
        dungeonConfiguration.addRoomShape(basicRoomShape);

        Room basic2Room = new Room();
        basic2Room.setName("basic2");
        basic2Room.setAmount(new IntRange(50, 100));
        RoomShape basic2RoomShape = new RoomShape();
        RoomTemplate basic2RoomTemplate = new RoomTemplate();
        basic2RoomTemplate.setName("square");
        basic2RoomTemplate.setParam("size", "5-10");
        basic2RoomShape.setName("small_sq");
        basic2RoomShape.setTemplate(basic2RoomTemplate);
        basic2Room.setShape(basic2RoomShape);
        dungeonConfiguration.addRoom(basic2Room);
        dungeonConfiguration.addRoomShape(basic2RoomShape);

        Room heartRoom = new Room();
        heartRoom.setName("heart");
        heartRoom.setAmount(new IntRange(20, 40));
        RoomShape heartRoomShape = new RoomShape();
        heartRoomShape.setName("heart");
        heartRoomShape.setShape(new TextualShape(
                "..x...x..\n"+
                        ".xxx.xxx.\n"+
                        "xxxxxxxxx\n"+
                        ".xxxxxxx.\n"+
                        "..xxxxx..\n"+
                        "...xxx...\n"+
                        "....x....\n", '.', 'x', '\n'));
        heartRoom.setShape(heartRoomShape);
        heartRoom.setRotate(true);
        dungeonConfiguration.addRoom(heartRoom);
        dungeonConfiguration.addRoomShape(heartRoomShape);

        Room diamondRoom = new Room();
        diamondRoom.setName("diamond");
        diamondRoom.setAmount(new IntRange(10, 20));
        RoomShape diamondRoomShape = new RoomShape();
        diamondRoomShape.setName("diamond");
        diamondRoomShape.setShape(new TextualShape(
                ".....x.....\n"+
                        "....xxx....\n"+
                        "...xxxxx...\n"+
                        "..xxxxxxx..\n"+
                        ".xxxxxxxxx.\n"+
                        "xxxxxxxxxxx\n"+
                        ".xxxxxxxxx.\n"+
                        "..xxxxxxx..\n"+
                        "...xxxxx...\n"+
                        "....xxx....\n"+
                        ".....x.....\n", '.', 'x', '\n'));
        diamondRoom.setShape(diamondRoomShape);
        diamondRoom.setRotate(true);
        dungeonConfiguration.addRoom(diamondRoom);
        dungeonConfiguration.addRoomShape(diamondRoomShape);

        Room bossRoom = new Room();
        bossRoom.setName("boss");
        bossRoom.setAmount(new IntRange(1, 5));
        RoomShape bossRoomShape = new RoomShape();
        RoomTemplate bossRoomTemplate = new RoomTemplate();
        bossRoomTemplate.setName("square");
        bossRoomTemplate.setParam("size", "20-25");
        bossRoomShape.setName("big_square");
        bossRoomShape.setTemplate(bossRoomTemplate);
        bossRoom.setShape(bossRoomShape);
        dungeonConfiguration.addRoom(bossRoom);
        dungeonConfiguration.addRoomShape(bossRoomShape);

        Room megaRoom = new Room();
        megaRoom.setName("mega");
        megaRoom.setAmount(new IntRange(1, 1));
        RoomShape megaRoomShape = new RoomShape();
        RoomTemplate megaRoomTemplate = new RoomTemplate();
        megaRoomTemplate.setName("rectangle");
        megaRoomTemplate.setParam("size-x", "50-75");
        megaRoomTemplate.setParam("size-y", "50-75");
        megaRoomShape.setName("mega room");
        megaRoomShape.setTemplate(megaRoomTemplate);
        megaRoom.setShape(megaRoomShape);
        dungeonConfiguration.addRoom(megaRoom);
        dungeonConfiguration.addRoomShape(megaRoomShape);

        return dungeonConfiguration;
    }

    /** Creates configuration which consists of same rooms as medium configuration, but ten times more */
    public static DungeonConfiguration createHardTestConfiguration(String seed) {
        DungeonConfiguration dungeonConfiguration = new DungeonConfiguration();
        if (seed != null && !seed.isEmpty())
            dungeonConfiguration.setSeed(seed);

        Room basicRoom = new Room();
        basicRoom.setName("basic");
        basicRoom.setAmount(new IntRange(500, 1000));
        RoomShape basicRoomShape = new RoomShape();
        RoomTemplate basicRoomTemplate = new RoomTemplate();
        basicRoomTemplate.setName("rectangle");
        basicRoomTemplate.setParam("size-x", "5-10");
        basicRoomTemplate.setParam("size-y", "4-8");
        basicRoomShape.setName("small_rect");
        basicRoomShape.setTemplate(basicRoomTemplate);
        basicRoom.setShape(basicRoomShape);
        dungeonConfiguration.addRoom(basicRoom);
        dungeonConfiguration.addRoomShape(basicRoomShape);

        Room basic2Room = new Room();
        basic2Room.setName("basic2");
        basic2Room.setAmount(new IntRange(500, 1000));
        RoomShape basic2RoomShape = new RoomShape();
        RoomTemplate basic2RoomTemplate = new RoomTemplate();
        basic2RoomTemplate.setName("square");
        basic2RoomTemplate.setParam("size", "5-10");
        basic2RoomShape.setName("small_sq");
        basic2RoomShape.setTemplate(basic2RoomTemplate);
        basic2Room.setShape(basic2RoomShape);
        dungeonConfiguration.addRoom(basic2Room);
        dungeonConfiguration.addRoomShape(basic2RoomShape);

        Room heartRoom = new Room();
        heartRoom.setName("heart");
        heartRoom.setAmount(new IntRange(200, 400));
        RoomShape heartRoomShape = new RoomShape();
        heartRoomShape.setName("heart");
        heartRoomShape.setShape(new TextualShape(
                "..x...x..\n"+
                        ".xxx.xxx.\n"+
                        "xxxxxxxxx\n"+
                        ".xxxxxxx.\n"+
                        "..xxxxx..\n"+
                        "...xxx...\n"+
                        "....x....\n", '.', 'x', '\n'));
        heartRoom.setShape(heartRoomShape);
        heartRoom.setRotate(true);
        dungeonConfiguration.addRoom(heartRoom);
        dungeonConfiguration.addRoomShape(heartRoomShape);

        Room diamondRoom = new Room();
        diamondRoom.setName("diamond");
        diamondRoom.setAmount(new IntRange(100, 200));
        RoomShape diamondRoomShape = new RoomShape();
        diamondRoomShape.setName("diamond");
        diamondRoomShape.setShape(new TextualShape(
                ".....x.....\n"+
                        "....xxx....\n"+
                        "...xxxxx...\n"+
                        "..xxxxxxx..\n"+
                        ".xxxxxxxxx.\n"+
                        "xxxxxxxxxxx\n"+
                        ".xxxxxxxxx.\n"+
                        "..xxxxxxx..\n"+
                        "...xxxxx...\n"+
                        "....xxx....\n"+
                        ".....x.....\n", '.', 'x', '\n'));
        diamondRoom.setShape(diamondRoomShape);
        diamondRoom.setRotate(true);
        dungeonConfiguration.addRoom(diamondRoom);
        dungeonConfiguration.addRoomShape(diamondRoomShape);

        Room bossRoom = new Room();
        bossRoom.setName("boss");
        bossRoom.setAmount(new IntRange(10, 50));
        RoomShape bossRoomShape = new RoomShape();
        RoomTemplate bossRoomTemplate = new RoomTemplate();
        bossRoomTemplate.setName("square");
        bossRoomTemplate.setParam("size", "20-25");
        bossRoomShape.setName("big_square");
        bossRoomShape.setTemplate(bossRoomTemplate);
        bossRoom.setShape(bossRoomShape);
        dungeonConfiguration.addRoom(bossRoom);
        dungeonConfiguration.addRoomShape(bossRoomShape);

        Room megaRoom = new Room();
        megaRoom.setName("mega");
        megaRoom.setAmount(new IntRange(1, 10));
        RoomShape megaRoomShape = new RoomShape();
        RoomTemplate megaRoomTemplate = new RoomTemplate();
        megaRoomTemplate.setName("rectangle");
        megaRoomTemplate.setParam("size-x", "50-75");
        megaRoomTemplate.setParam("size-y", "50-75");
        megaRoomShape.setName("mega room");
        megaRoomShape.setTemplate(megaRoomTemplate);
        megaRoom.setShape(megaRoomShape);
        dungeonConfiguration.addRoom(megaRoom);
        dungeonConfiguration.addRoomShape(megaRoomShape);

        return dungeonConfiguration;
    }

    /** Creates configuration which consists of exactly AMOUNT rooms - small rectangular and square rooms, two kinds of shaped rooms, bigger square BOSS rooms and one huge MEGA room */
    public static DungeonConfiguration create500RoomsTestConfiguration(String seed, int amount) {
        DungeonConfiguration dungeonConfiguration = new DungeonConfiguration();
        if (seed != null && !seed.isEmpty())
            dungeonConfiguration.setSeed(seed);

        Room basicRoom = new Room();
        basicRoom.setName("basic");
        basicRoom.setAmount(new IntRange((int)(amount*0.3), (int)(amount*0.3)));
        RoomShape basicRoomShape = new RoomShape();
        RoomTemplate basicRoomTemplate = new RoomTemplate();
        basicRoomTemplate.setName("rectangle");
        basicRoomTemplate.setParam("size-x", "5-10");
        basicRoomTemplate.setParam("size-y", "4-8");
        basicRoomShape.setName("small_rect");
        basicRoomShape.setTemplate(basicRoomTemplate);
        basicRoom.setShape(basicRoomShape);
        dungeonConfiguration.addRoom(basicRoom);
        dungeonConfiguration.addRoomShape(basicRoomShape);

        Room basic2Room = new Room();
        basic2Room.setName("basic2");
        basic2Room.setAmount(new IntRange((int)(amount*0.3), (int)(amount*0.3)));
        RoomShape basic2RoomShape = new RoomShape();
        RoomTemplate basic2RoomTemplate = new RoomTemplate();
        basic2RoomTemplate.setName("square");
        basic2RoomTemplate.setParam("size", "5-10");
        basic2RoomShape.setName("small_sq");
        basic2RoomShape.setTemplate(basic2RoomTemplate);
        basic2Room.setShape(basic2RoomShape);
        dungeonConfiguration.addRoom(basic2Room);
        dungeonConfiguration.addRoomShape(basic2RoomShape);

        Room heartRoom = new Room();
        heartRoom.setName("heart");
        heartRoom.setAmount(new IntRange((int)(amount*0.2), (int)(amount*0.2)));
        RoomShape heartRoomShape = new RoomShape();
        heartRoomShape.setName("heart");
        heartRoomShape.setShape(new TextualShape(
                "..x...x..\n"+
                        ".xxx.xxx.\n"+
                        "xxxxxxxxx\n"+
                        ".xxxxxxx.\n"+
                        "..xxxxx..\n"+
                        "...xxx...\n"+
                        "....x....\n", '.', 'x', '\n'));
        heartRoom.setShape(heartRoomShape);
        heartRoom.setRotate(true);
        dungeonConfiguration.addRoom(heartRoom);
        dungeonConfiguration.addRoomShape(heartRoomShape);

        Room diamondRoom = new Room();
        diamondRoom.setName("diamond");
        diamondRoom.setAmount(new IntRange((int)(amount*0.1), (int)(amount*0.1)));
        RoomShape diamondRoomShape = new RoomShape();
        diamondRoomShape.setName("diamond");
        diamondRoomShape.setShape(new TextualShape(
                ".....x.....\n"+
                        "....xxx....\n"+
                        "...xxxxx...\n"+
                        "..xxxxxxx..\n"+
                        ".xxxxxxxxx.\n"+
                        "xxxxxxxxxxx\n"+
                        ".xxxxxxxxx.\n"+
                        "..xxxxxxx..\n"+
                        "...xxxxx...\n"+
                        "....xxx....\n"+
                        ".....x.....\n", '.', 'x', '\n'));
        diamondRoom.setShape(diamondRoomShape);
        diamondRoom.setRotate(true);
        dungeonConfiguration.addRoom(diamondRoom);
        dungeonConfiguration.addRoomShape(diamondRoomShape);

        Room bossRoom = new Room();
        bossRoom.setName("boss");
        bossRoom.setAmount(new IntRange((int)(amount*0.1)-1, (int)(amount*0.1)-1));
        RoomShape bossRoomShape = new RoomShape();
        RoomTemplate bossRoomTemplate = new RoomTemplate();
        bossRoomTemplate.setName("square");
        bossRoomTemplate.setParam("size", "20-25");
        bossRoomShape.setName("big_square");
        bossRoomShape.setTemplate(bossRoomTemplate);
        bossRoom.setShape(bossRoomShape);
        dungeonConfiguration.addRoom(bossRoom);
        dungeonConfiguration.addRoomShape(bossRoomShape);

        Room megaRoom = new Room();
        megaRoom.setName("mega");
        megaRoom.setAmount(new IntRange(1, 1));
        RoomShape megaRoomShape = new RoomShape();
        RoomTemplate megaRoomTemplate = new RoomTemplate();
        megaRoomTemplate.setName("rectangle");
        megaRoomTemplate.setParam("size-x", "50-75");
        megaRoomTemplate.setParam("size-y", "50-75");
        megaRoomShape.setName("mega room");
        megaRoomShape.setTemplate(megaRoomTemplate);
        megaRoom.setShape(megaRoomShape);
        dungeonConfiguration.addRoom(megaRoom);
        dungeonConfiguration.addRoomShape(megaRoomShape);

        return dungeonConfiguration;
    }

}
