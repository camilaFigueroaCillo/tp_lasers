package model;

import java.util.List;

public class BloqueVidrio extends Bloque implements Espejar {

    public BloqueVidrio(Celda celda, Tipo tipo) {super(celda, tipo);}

    public boolean dirigirLaser(Laser laser, List<Laser> recorrido) {
        Laser nuevo = difractarLaser(laser, laser.getUltimaPos(), laser.getDireccionPos(), laser.getDireccion());
        recorrido.add(nuevo);
        return true;
    }

    protected Laser difractarLaser(Laser laser, Coordenada ultPos, Coordenada dirPos, Direccion direccion) {
        // Primero obtengo la direccion contraria, llamando a espejarLaser
        // crea un nuevo laser con la nueva direccion
        // agrega una nueva posicion al laser actual
        // retorna el nuevo laser, asi luego se agrega al recorrido general

        Coordenada nuevaCoordenada = espejarLaser(direccion, ultPos);
        Direccion dir = Direccion.coordToDir(nuevaCoordenada);
        Laser nuevoLaser = new Laser(ultPos, dir);
        nuevoLaser.agregarPos(ultPos.sumar(nuevaCoordenada));
        laser.agregarPos(ultPos.sumar(dirPos));
        ultPos = laser.getUltimaPos();

        //Si necesito agregar una nueva posicion para salir de la celda, lo hago
        for (Coordenada c: this.celda.getImpares()) {
            if (c.equals(ultPos)) {
                laser.agregarPos(ultPos.sumar(dirPos));
            }
        }
        return nuevoLaser;
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
