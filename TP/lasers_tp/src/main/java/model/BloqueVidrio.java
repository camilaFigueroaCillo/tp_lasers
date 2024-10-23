package model;

import java.util.List;

public class BloqueVidrio extends Bloque implements Espejar {

    public BloqueVidrio(Tipo tipo) {super(tipo);}

    public boolean dirigirLaser(Laser laser, List<Laser> recorrido, Celda celda) {
        Laser nuevo = difractarLaser(laser, laser.getUltimaPos(), laser.getDireccionPos(), laser.getDireccion(), celda);
        recorrido.add(nuevo);
        return true;
    }

    protected Laser difractarLaser(Laser laser, Coordenada ultPos, Coordenada dirPos, Direccion direccion, Celda celda) {
        // Primero obtengo la direccion contraria, llamando a espejarLaser
        // crea un nuevo laser con la nueva direccion
        // agrega una nueva posicion al laser actual
        // retorna el nuevo laser, asi luego se agrega al recorrido general

        Coordenada nuevaCoordenada = espejarLaser(direccion, ultPos, celda);
        Direccion dir = Direccion.coordToDir(nuevaCoordenada);
        Laser nuevoLaser = new Laser(ultPos, dir);
        nuevoLaser.agregarPos(ultPos.sumar(nuevaCoordenada));
        laser.agregarPos(ultPos.sumar(dirPos));
        ultPos = laser.getUltimaPos();

        //Si necesito agregar una nueva posicion para salir de la celda, lo hago
        for (Coordenada c: celda.getImpares()) {
            if (c.equals(ultPos)) {
                laser.agregarPos(ultPos.sumar(dirPos));
            }
        }
        return nuevoLaser;
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
