package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Coordenada {
    private int x;
    private int y;

    public Coordenada(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordenada sumar(Coordenada b) {
        return new Coordenada(this.x + b.getX(), this.y + b.getY());
    }

    public List<Coordenada> getNormales() {
        List<Coordenada> normales = new ArrayList<>();
        normales.add(new Coordenada(-y, x));
        normales.add(new Coordenada(y, -x));
        return normales;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Coordenada other = (Coordenada) obj;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
