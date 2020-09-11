// Generate dungeon
DungeonConfiguration dungeonConfiguration = createConfiguration();
Dungeon dungeon = null;
try {
    dungeon = new ProcDunGenApi().generateDungeon(dungeonConfiguration);
} catch (InvalidConfigurationException | GeneratorException ex) {
    ex.printStackTrace();
}

// Place dungeon
if (dungeon != null && newWorld != null) {
    for (GridCell gridCell : dungeon.getGrid()) {
        if (gridCell.getType() == GridCell.Type.NONE)
            continue;
        Material material = Material.GOLD_BLOCK;
        int iterations = 1;
        if (gridCell.getType() == GridCell.Type.ROOM) {
            material = Material.IRON_BLOCK;
            iterations = 5;
        }
        else if (gridCell.getType() == GridCell.Type.CORRIDOR) {
            material = Material.LAPIS_BLOCK;
            iterations = 2;
        }
        for (int i = 0; i < iterations; i++) {
            Block block = newWorld.getBlockAt(gridCell.getPosition().getX(), 80 + i, gridCell.getPosition().getY());
            block.setType(material, false);
        }
    }
}