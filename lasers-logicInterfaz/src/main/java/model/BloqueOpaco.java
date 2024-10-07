package model;

import java.util.List;

public class BloqueOpaco extends Bloque {

    public BloqueOpaco(Celda celda, Tipo tipo) {
        super(celda, tipo);
    }

    public boolean dirigirLaser(Laser laser, List<Laser> recorrido, Coordenada ini, Coordenada actual) {
        return false;
    }

}
