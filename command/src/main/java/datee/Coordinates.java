package datee;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private final long x; //Значение поля должно быть больше -847
    private final Integer y; //Поле не может быть null

    public Coordinates(long x, Integer y){
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
}

