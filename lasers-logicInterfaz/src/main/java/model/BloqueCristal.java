package model;

import java.util.List;

import static model.Direccion.*;
import static model.Direccion.YS;

public class BloqueCristal extends Bloque {

    public BloqueCristal(Celda celda, Tipo tipo) {
        super(celda, tipo);
    }

    public boolean dirigirLaser(Laser laser, List<Laser> recorrido) {
        refractarLaser(laser, laser.getDireccion(), laser.getDireccionPos());
        return true;
    }

    private boolean actualizarLaser(Laser laser, Coordenada posibleCoordenada, Coordenada posDireccion, Coordenada vector) {

        // Para refractar el laser, suma dos posiciones en el eje correspondiente y luego suma una nueva posicion en el sentido de la direccion

        Coordenada ultPos = laser.getUltimaPos();

        if  (posibleCoordenada.equals(ultPos)) {
            laser.agregarPos(ultPos.sumar(vector));
            ultPos = laser.getUltimaPos();
            laser.agregarPos(ultPos.sumar(vector));
            ultPos = laser.getUltimaPos();
            laser.agregarPos(ultPos.sumar(posDireccion));
            return true;
        }
        return false;
    }

    private void refractarLaser(Laser laser, Direccion dir, Coordenada posDireccion) {

        // Para cada direccion, obtiene las posibles coordenadas que pueden ser refractadas
        // Si se puede, la refracta

        Coordenada izq = celda.getImparIzq();
        Coordenada der = celda.getImparDer();
        Coordenada arriba = celda.getImparArriba();
        Coordenada abajo = celda.getImparAbajo();

        switch (dir) {
            case SE:
                if (actualizarLaser(laser,izq, posDireccion, XE.getPos())) { return; }
                actualizarLaser(laser,arriba, posDireccion, YN.getPos());
                break;
            case SW:
                if (actualizarLaser(laser,der, posDireccion, XW.getPos())) { return; }
                actualizarLaser(laser,arriba, posDireccion, YN.getPos());
                break;
            case NE:
                if (actualizarLaser(laser,izq, posDireccion, XE.getPos())) { return; }
                actualizarLaser(laser,abajo, posDireccion, YS.getPos());
                break;
            case NW:
                if (actualizarLaser(laser,der, posDireccion, XW.getPos())) { return; }
                actualizarLaser(laser,abajo, posDireccion, YS.getPos());
                break;
        }
    }

}
