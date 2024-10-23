package model;

import model.Direccion;

public interface Espejar {

    Coordenada espejarLaser(Direccion direccion, Coordenada c, Celda celda);
}
