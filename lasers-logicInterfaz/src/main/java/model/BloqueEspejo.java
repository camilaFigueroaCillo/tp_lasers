package model;

import java.util.List;

public class BloqueEspejo extends Bloque implements Espejar {

    public BloqueEspejo(Celda celda, Tipo tipo) {
        super(celda, tipo);
    }

    public boolean dirigirLaser(Laser laser, List<Laser> recorrido, Coordenada ini, Coordenada actual) {
        Coordenada nueva = espejarLaser(laser.getDireccion(), actual);
        Coordenada nuevaPos = actual.sumar(nueva);
        Direccion dir = Direccion.coordToDir(nueva);
        laser.cambiarDireccion(dir);
        laser.agregarPos(nuevaPos);
        return true;
    }

    public Coordenada espejarLaser(Direccion direccion, Coordenada c) {
        var dirCoord = direccion.getPos();
        var normal1 = new Coordenada(-dirCoord.getY(), dirCoord.getX());
        var normal2 = new Coordenada(dirCoord.getY(), -dirCoord.getX());
        var nuevaPos1 = c.sumar(normal1);
        var nuevaPos2 = c.sumar(normal2);

        List<Coordenada> centros = this.celda.getImpares();

        for (Coordenada centro: centros) {
            if (centro.equals(nuevaPos1)) {
                return normal2;
            }
            if (centro.equals(nuevaPos2)) {
                return normal1;
            }
        }

        return normal1;
    }
}
