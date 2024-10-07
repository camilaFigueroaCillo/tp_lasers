package model;

import java.util.List;

public abstract class Bloque {
    private Tipo tipo;
    protected Celda celda;

    public Bloque(Celda celda, Tipo tipo) {
        this.celda = celda;
        this.tipo = tipo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public boolean desplazar(Celda destino) {
        /*
            Desplaza al bloque a la celda 'destino'
            (a excepción de ser de tipo OPACOFIJO)
        */

        if (tipo == Tipo.OPACOFIJO) {
            return false;
        }

        this.celda = destino;
        return true;
    }

    public abstract boolean dirigirLaser(Laser laser, List<Laser> recorrido, Coordenada ini, Coordenada actual);

}
