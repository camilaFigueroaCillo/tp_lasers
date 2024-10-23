package model;

import model.Direccion;

import java.util.List;

public class BloqueEspejo extends Bloque implements Espejar {

    public BloqueEspejo(Tipo tipo) {
        super(tipo);
    }

    public boolean dirigirLaser(Laser laser, List<Laser> recorrido, Celda celda) {
        Direccion direccionLaser = laser.getDireccion();
        Coordenada ultPos = laser.getUltimaPos();
        Coordenada nueva = espejarLaser(direccionLaser, ultPos, celda);
        Coordenada nuevaPos = ultPos.sumar(nueva);
        Direccion dir = direccionLaser.coordToDir(nueva);
        laser.cambiarDireccion(dir);
        laser.agregarPos(nuevaPos);
        return true;
    }

    public Coordenada espejarLaser(Direccion direccion, Coordenada c, Celda celda) {
        Coordenada dirCoord = direccion.getPos();
        List<Coordenada> normales = dirCoord.getNormales();

        var normal1 = normales.get(0);
        var normal2 = normales.get(1);

        var nuevaPos1 = c.sumar(normal1);
        var nuevaPos2 = c.sumar(normal2);

        List<Coordenada> centros = celda.getImpares();

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
