package cz.iwitrag.procdungen.api.data.files;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import cz.iwitrag.procdungen.api.data.GridCell;

/**
 * This class is holder for data outputted to file by Jackson library<br>
 * Represents cell of grid with its relative position in grid, type and room or corridor it belongs to
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GridCellPojo {

    private PositionPojo position;
    private GridCell.Type type;
    private Integer room;
    private Integer corridor;

    public PositionPojo getPosition() {
        return position;
    }

    public void setPosition(PositionPojo position) {
        this.position = position;
    }

    public GridCell.Type getType() {
        return type;
    }

    public void setType(GridCell.Type type) {
        this.type = type;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public Integer getCorridor() {
        return corridor;
    }

    public void setCorridor(Integer corridor) {
        this.corridor = corridor;
    }
}
