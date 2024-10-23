package model;

import java.util.List;

public class BloqueOpaco extends Bloque {

    public BloqueOpaco(Tipo tipo) {
        super(tipo);
    }

    public boolean dirigirLaser(Laser laser, List<Laser> recorrido, Celda celda) {
        return false;
    }

}
