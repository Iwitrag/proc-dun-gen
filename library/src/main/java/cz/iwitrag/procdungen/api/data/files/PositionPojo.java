package cz.iwitrag.procdungen.api.data.files;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * This class is holder for data outputted to file by Jackson library<br>
 * Represents point in 2-dimensional space
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PositionPojo {

    private int x;
    private int y;

    @JsonCreator
    public PositionPojo(String str) {
        String[] split = str.split(",");
        x = Integer.parseInt(split[0]);
        y = Integer.parseInt(split[1]);
    }

    public PositionPojo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    @JsonValue
    public String toString() {
        return x + "," + y;
    }
}
