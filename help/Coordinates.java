package please.help;

import java.util.Objects;

public class Coordinates implements Cloneable{

    private int x;
    private int y;

    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (getClass() != other.getClass()) return false;
        return ((Coordinates) other).getX() == this.x &&
                ((Coordinates) other).getY() == this.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinates{ " + "x = " + x + "; y = " + y + " }";
    }


    @Override
    public Coordinates clone() throws CloneNotSupportedException {
        return (Coordinates) super.clone();
    }
}
