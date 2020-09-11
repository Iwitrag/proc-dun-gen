package cz.iwitrag.procdungen.api.data.files;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

/**
 * This class is holder for data outputted to file by Jackson library<br>
 * Represents whole dungeon space composed of cells
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GridPojo {

    private int width;
    private int height;
    @JacksonXmlProperty(localName = "cell")
    @JacksonXmlElementWrapper(localName = "cells")
    private List<GridCellPojo> cells;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<GridCellPojo> getCells() {
        return cells;
    }

    public void setCells(List<GridCellPojo> cells) {
        this.cells = cells;
    }
}
