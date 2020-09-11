package cz.iwitrag.procdungen.util;

import cz.iwitrag.procdungen.api.ProcDunGenApi;
import cz.iwitrag.procdungen.api.configuration.DungeonConfiguration;
import cz.iwitrag.procdungen.api.configuration.InvalidConfigurationException;
import cz.iwitrag.procdungen.api.data.Dungeon;
import cz.iwitrag.procdungen.api.data.Grid;
import cz.iwitrag.procdungen.api.data.GridCell;
import cz.iwitrag.procdungen.api.data.Point;
import cz.iwitrag.procdungen.generator.GeneratorException;
import cz.iwitrag.procdungen.generator.implementations.physical.ConfiguredGenerator;

import java.util.HashMap;
import java.util.Map;

/** Useful class to calculate heat map of dungeon cells */
public class HeatMap {

    /**
     * Calculates amounts of room cells placed on respective coordinates
     * @param configuration Dungeon configuration to use
     * @param amountOfDungeons Amount of dungeons to generate
     * @return Calculated histogram
     */
    public static Map<Point<Integer>, Integer> heatMap(DungeonConfiguration configuration, int amountOfDungeons) {
        ProcDunGenApi api = new ProcDunGenApi();
        Map<Point<Integer>, Integer> heatMap = new HashMap<>();

        int lowestCellX = Integer.MAX_VALUE;
        int lowestCellY = Integer.MAX_VALUE;
        for (int i = 0; i < amountOfDungeons; i++) {
            // Generate dungeon
            Dungeon dungeon;
            try {
                dungeon = api.generateDungeonPartially(configuration, ConfiguredGenerator.Phase.SPREAD_ROOMS);
            } catch (InvalidConfigurationException | GeneratorException e) {
                e.printStackTrace();
                dungeon = new Dungeon();
            }
            // Add cells to heatmap, grid will be shifted, center on [0, 0]
            if (dungeon != null) {
                Grid grid = dungeon.getGrid();
                for (GridCell gridCell : dungeon.getGrid()) {
                    int x = gridCell.getPosition().getX() - (grid.getWidth()/2);
                    int y = gridCell.getPosition().getY() - (grid.getHeight()/2);
                    lowestCellX = Math.min(lowestCellX, x);
                    lowestCellY = Math.min(lowestCellY, y);
                    Point<Integer> point = new Point<>(x, y);
                    heatMap.put(point, heatMap.getOrDefault(point, 0) + (gridCell.getType() == GridCell.Type.NONE ? 0 : 1));
                }
            }
        }
        // Normalize heat map (positive coordinates only)
        int absLowestCellX = Math.abs(lowestCellX);
        int absLowestCellY = Math.abs(lowestCellY);
        Map<Point<Integer>, Integer> normalizedHeatMap = new HashMap<>();
        for (Map.Entry<Point<Integer>, Integer> entry : heatMap.entrySet()) {
            normalizedHeatMap.put(entry.getKey().getShifted(absLowestCellX, absLowestCellY), entry.getValue());
        }
        return normalizedHeatMap;
    }

}
