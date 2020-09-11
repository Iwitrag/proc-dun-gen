package cz.iwitrag.procdungen.api.configuration;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import cz.iwitrag.procdungen.api.configuration.files.FileConfigurationPojo;
import cz.iwitrag.procdungen.api.configuration.files.RoomPojo;
import cz.iwitrag.procdungen.api.configuration.files.RoomShapePojo;
import cz.iwitrag.procdungen.api.configuration.files.RoomTemplatePojo;
import cz.iwitrag.procdungen.generator.templates.TemplateException;
import cz.iwitrag.procdungen.generator.templates.TemplateFactory;
import cz.iwitrag.procdungen.util.IntRange;
import cz.iwitrag.procdungen.util.SupportedFileType;
import cz.iwitrag.procdungen.util.TextualShape;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Entire dungeon configuration used as input for {@link cz.iwitrag.procdungen.api.ProcDunGenApi ProcDunGen} class<br>
 * To create configuration for generator proceed as follows:
 * <ol>
 *     <li>Create instances of {@link Room}, {@link RoomShape} and {@link RoomTemplate} as needed</li>
 *     <li>Configure them</li>
 *     <li>Add them to this class by calling appropriate methods</li>
 *     <li>Configure additional properties like {@link DungeonConfiguration#setSeed(String) seed}</li>
 * </ol>
 */
public class DungeonConfiguration {

    /** Room shapes to be used by rooms */
    private Set<RoomShape> roomShapes = new LinkedHashSet<>();
    /** Room definitions */
    private Set<Room> rooms = new LinkedHashSet<>();
    /** Which file type to use when outputting dungeon to file (optional) */
    private SupportedFileType outputFileType;
    /** Explicit seed to use as base for procedural generation (optional) */
    private String seed;

    /**
     * Constructs configuration from file
     * @param file File to load
     * @return Constructed configuration
     * @throws InvalidConfigurationException If configuration is invalid or failed to parse
     */
    public static DungeonConfiguration fromFile(File file) throws InvalidConfigurationException {
        Objects.requireNonNull(file, "Cannot parse null file");
        SupportedFileType supportedFileType = SupportedFileType.fromFileName(file.getName());
        if (supportedFileType == null)
            throw new InvalidConfigurationException("Unsupported file extension, supported file extensions are " + StringUtils.join(SupportedFileType.values(), ", "));
        ObjectMapper mapper = prepareMapper(supportedFileType);

        FileConfigurationPojo fileConfiguration;
        try {
            fileConfiguration = mapper.readValue(file, FileConfigurationPojo.class);
        } catch (IOException ex) {
            throw new InvalidConfigurationException("Error parsing dungeon configuration file: " + ex.getMessage());
        }
        return pojoToConfiguration(fileConfiguration, supportedFileType);
    }

    /**
     * Constructs configuration from raw String
     * @param string Input data
     * @param supportedFileType Which format should be used when parsing
     * @return Constructed configuration
     * @throws InvalidConfigurationException If configuration is invalid or failed to parse
     */
    public static DungeonConfiguration fromString(String string, SupportedFileType supportedFileType) throws InvalidConfigurationException {
        Objects.requireNonNull(string, "Cannot parse null string");
        Objects.requireNonNull(supportedFileType, "Cannot parse with unknown fileType (was null)");
        ObjectMapper mapper = prepareMapper(supportedFileType);

        FileConfigurationPojo fileConfiguration;
        try {
            fileConfiguration = mapper.readValue(string, FileConfigurationPojo.class);
        } catch (IOException ex) {
            throw new InvalidConfigurationException("Error parsing dungeon configuration file: " + ex.getMessage());
        }
        return pojoToConfiguration(fileConfiguration, supportedFileType);
    }

    /**
     * Saves this configuration to YAML
     * @param file File to save to
     * @throws IOException When I/O error occurs
     */
    public void toYaml(File file) throws IOException {
        YAMLFactory yamlFactory = new YAMLFactory();
        yamlFactory.disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        toFile(file, new ObjectMapper(yamlFactory), null, SupportedFileType.YAML);
    }

    /**
     * Saves this configuration to JSON
     * @param file File to save to
     * @throws IOException When I/O error occurs
     */
    public void toJson(File file) throws IOException {
        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
        toFile(file, new ObjectMapper(), prettyPrinter, SupportedFileType.JSON);
    }

    /**
     * Saves this configuration to XML
     * @param file File to save to
     * @throws IOException When I/O error occurs
     */
    public void toXml(File file) throws IOException {
        toFile(file, new XmlMapper(), null, SupportedFileType.XML);
    }

    private void toFile(File file, ObjectMapper mapper, DefaultPrettyPrinter prettyPrinter, SupportedFileType outputFileType) throws IOException {
        Objects.requireNonNull(file, "Cannot write configuration to null file");
        Objects.requireNonNull(mapper, "Cannot write configuration with null mapper");
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        FileConfigurationPojo configurationPojo = configurationToPojo(this, outputFileType);
        if (prettyPrinter == null)
            mapper.writeValue(file, configurationPojo);
        else
            mapper.writer(prettyPrinter).writeValue(file, configurationPojo);
    }

    private static ObjectMapper prepareMapper(SupportedFileType supportedFileType) {
        ObjectMapper mapper;
        if (supportedFileType == SupportedFileType.YAML || supportedFileType == SupportedFileType.YML)
            mapper = new ObjectMapper(new YAMLFactory());
        else if (supportedFileType == SupportedFileType.JSON)
            mapper = new ObjectMapper();
        else
            mapper = new XmlMapper();
        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        return mapper;
    }

    private static DungeonConfiguration pojoToConfiguration(FileConfigurationPojo fileConfiguration, SupportedFileType outputFileType) throws InvalidConfigurationException {
        DungeonConfiguration dungeonConfiguration = new DungeonConfiguration();

        // Room shapes
        List<RoomShapePojo> roomShapes = fileConfiguration.getRoomShapes();
        if (roomShapes != null) {
            for (RoomShapePojo roomShapePojo : roomShapes) {
                RoomShape newRoomShape = new RoomShape();
                newRoomShape.setName(roomShapePojo.getName());
                if (roomShapePojo.getShape() != null && !roomShapePojo.getShape().isEmpty() && !roomShapePojo.getShape().get(0).isEmpty()) {
                    try {
                        newRoomShape.setShape(new TextualShape(StringUtils.join(roomShapePojo.getShape(), "\n"), '.', 'x', '\n'));
                    } catch (IllegalArgumentException ex) {
                        throw new InvalidConfigurationException("Room shape " + roomShapePojo.getName() + " is invalid: " + ex.getMessage());
                    }
                }
                if (roomShapePojo.getTemplate() != null) {
                    RoomTemplate newRoomTemplate = new RoomTemplate();
                    newRoomTemplate.setName(roomShapePojo.getTemplate().getName());
                    for (Map.Entry<String, String> entry : roomShapePojo.getTemplate().getParams().entrySet()) {
                        newRoomTemplate.setParam(entry.getKey(), entry.getValue());
                    }
                    newRoomShape.setTemplate(newRoomTemplate);
                }
                dungeonConfiguration.addRoomShape(newRoomShape);
            }
        }

        // Rooms
        List<RoomPojo> rooms = fileConfiguration.getRooms();
        if (rooms != null) {
            for (RoomPojo roomPojo : rooms) {
                Room newRoom = new Room();
                newRoom.setName(roomPojo.getName());
                if (roomPojo.getShape() != null) {
                    RoomShape roomShape = dungeonConfiguration.getRoomShape(roomPojo.getShape());
                    if (roomShape == null)
                        throw new InvalidConfigurationException("Room " + roomPojo.getName() + " uses shape " + roomPojo.getShape() + " which is not defined");
                    newRoom.setShape(roomShape);
                }
                if (roomPojo.getAmount() != null) {
                    try {
                        newRoom.setAmount(IntRange.fromString(roomPojo.getAmount(), "-"));
                    } catch (IllegalArgumentException ex) {
                        throw new InvalidConfigurationException("Room " + roomPojo.getName() + " has invalid amount: " + ex.getMessage());
                    }
                } else
                    newRoom.setAmount(new IntRange(1));

                if (roomPojo.getRotate() == null)
                    roomPojo.setRotate("false");
                else {
                    if (Arrays.asList("yes", "true", "1").contains(roomPojo.getRotate().toLowerCase()))
                        newRoom.setRotate(true);
                    else if (Arrays.asList("no", "false", "0").contains(roomPojo.getRotate().toLowerCase()))
                        newRoom.setRotate(false);
                    else
                        throw new InvalidConfigurationException("Cannot determine, whether room " + roomPojo.getName() + " should be rotated");
                }

                dungeonConfiguration.addRoom(newRoom);
            }
        }

        // Wanted output file extension (if provided), extension from input file otherwise
        SupportedFileType specifiedFileType = fileConfiguration.getOutputFileType();
        if (specifiedFileType == null)
            dungeonConfiguration.setOutputFileType(outputFileType);
        else
            dungeonConfiguration.setOutputFileType(specifiedFileType);

        dungeonConfiguration.setSeed(fileConfiguration.getSeed());

        return dungeonConfiguration;
    }

    private static FileConfigurationPojo configurationToPojo(DungeonConfiguration configuration, SupportedFileType outputFileType) {
        FileConfigurationPojo configurationPojo = new FileConfigurationPojo();

        // Room shapes
        List<RoomShapePojo> shapesList = new ArrayList<>();
        for (RoomShape roomShape : configuration.roomShapes) {
            RoomShapePojo newRoomShape = new RoomShapePojo();
            newRoomShape.setName(roomShape.getName());
            if (roomShape.getShape() != null) {
                newRoomShape.setShape(roomShape.getShape().getShapeRows());
            }
            if (roomShape.getTemplate() != null) {
                RoomTemplatePojo newRoomTemplate = new RoomTemplatePojo();
                newRoomTemplate.setName(roomShape.getTemplate().getName());
                newRoomTemplate.setParams(roomShape.getTemplate().getParams());
                newRoomShape.setTemplate(newRoomTemplate);
            }
            shapesList.add(newRoomShape);
        }
        configurationPojo.setRoomShapes(shapesList);

        // Rooms
        List<RoomPojo> roomsList = new ArrayList<>();
        for (Room room : configuration.getRooms()) {
            RoomPojo newRoom = new RoomPojo();
            newRoom.setName(room.getName());
            if (room.getShape() != null) {
                newRoom.setShape(room.getShape().getName());
            }
            if (room.getAmount() != null) {
                newRoom.setAmount(room.getAmount().getMin() + "-" + room.getAmount().getMax());
            }
            else
                newRoom.setAmount("1-1");

            newRoom.setRotate(room.isRotate() ? "true" : "false");

            roomsList.add(newRoom);
        }
        configurationPojo.setRooms(roomsList);

        // Wanted output file extension (if provided), default otherwise
        SupportedFileType specifiedFileType = configuration.getOutputFileType();
        if (specifiedFileType == null)
            configurationPojo.setOutputFileType(outputFileType);
        else
            configurationPojo.setOutputFileType(specifiedFileType);

        configurationPojo.setSeed(configuration.getSeed());

        return configurationPojo;
    }

    /**
     * Validates correctness of this configuration, used by generator automatically
     * @throws InvalidConfigurationException If error in configuration were found
     */
    public void validate() throws InvalidConfigurationException {
        // Room shapes
        for (RoomShape roomShape : roomShapes) {
            String roomShapeName = roomShape.getName();
            RoomTemplate roomShapeTemplate = roomShape.getTemplate();
            TextualShape roomShapeShape = roomShape.getShape();
            if (roomShapeName == null)
                throw new InvalidConfigurationException("Found room shape without name");
            if (roomShapes.stream().filter((rs) -> rs.getName().equalsIgnoreCase(roomShapeName)).count() > 1)
                throw new InvalidConfigurationException("Found duplicated room shape: " + roomShapeName);
            if (roomShapeShape == null && roomShapeTemplate == null)
                throw new InvalidConfigurationException("Room shape " + roomShapeName + " does not define shape nor template");
            if (roomShapeShape != null && roomShapeTemplate != null)
                throw new InvalidConfigurationException("Room shape " + roomShapeName + " defines both shape and template");
            if (roomShapeShape != null) {
                if (roomShapeShape.getWidth() < 3 || roomShapeShape.getHeight() < 3)
                    throw new InvalidConfigurationException("One or both sides of room shape " + roomShapeName + " are smaller than 3");
            }
            if (roomShapeTemplate != null) {
                if (roomShapeTemplate.getName() == null)
                    throw new InvalidConfigurationException("Room shape " + roomShapeName + " uses template without name");
                try {
                    new TemplateFactory().getTemplate(roomShapeTemplate.getName(), roomShapeTemplate.getParams());
                } catch (TemplateException ex) {
                    throw new InvalidConfigurationException("Room shape" + roomShapeName + " template error: " + ex.getMessage());
                }
            }
        }

        // Rooms
        for (Room room : rooms) {
            String roomName = room.getName();
            RoomShape roomShape = room.getShape();
            IntRange roomAmount = room.getAmount();
            if (roomName == null)
                throw new InvalidConfigurationException("Found room without name");
            if (rooms.stream().filter((r) -> r.getName().equalsIgnoreCase(roomName)).count() > 1)
                throw new InvalidConfigurationException("Found duplicated room: " + roomName);
            if (roomShape == null)
                throw new InvalidConfigurationException("Room " + roomName + " uses no shape");
            if (roomAmount.getMin() < 0 || roomAmount.getMax() < 0)
                throw new InvalidConfigurationException("Room " + roomName + " has negative amount");
        }
    }

    public Set<RoomShape> getRoomShapes() {
        return roomShapes;
    }

    public RoomShape getRoomShape(String name) {
        return roomShapes.stream().filter((shape) -> shape.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

    public void setRoomShapes(Set<RoomShape> roomShapes) {
        this.roomShapes = roomShapes;
    }

    public void addRoomShape(RoomShape roomShape) {
        roomShapes.add(roomShape);
    }

    public void removeRoomShape(RoomShape roomShape) {
        roomShapes.remove(roomShape);
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public Room getRoom(String name) {
        return rooms.stream().filter((room) -> room.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void removeRoom(Room room) {
        rooms.remove(room);
    }

    public SupportedFileType getOutputFileType() {
        return outputFileType;
    }

    public void setOutputFileType(SupportedFileType outputFileType) {
        this.outputFileType = outputFileType;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    @Override
    public String toString() {
        return "DungeonConfiguration{" +
                "roomShapes=" + roomShapes +
                ", rooms=" + rooms +
                ", outputFileType=" + outputFileType +
                ", seed='" + seed + '\'' +
                '}';
    }
}
