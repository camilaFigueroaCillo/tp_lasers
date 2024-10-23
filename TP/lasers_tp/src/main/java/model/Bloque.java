package model;

import java.util.List;

public abstract class Bloque {
    private Tipo tipo;

    public Bloque(Tipo tipo) {
        this.tipo = tipo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public boolean desplazar() {
        /*
            Verifica si el bloque es desplazable
        */

        if (tipo == Tipo.OPACOFIJO) {
            return false;
        }
        return true;
    }

    public abstract boolean dirigirLaser(Laser laser, List<Laser> recorrido, Celda celda);

}
