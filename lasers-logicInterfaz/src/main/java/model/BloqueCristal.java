package model;

import java.util.List;

import static model.Direccion.*;
import static model.Direccion.YS;

public class BloqueCristal extends Bloque {

    public BloqueCristal(Celda celda, Tipo tipo) {
        super(celda, tipo);
    }

    public boolean dirigirLaser(Laser laser, List<Laser> recorrido, Coordenada ini, Coordenada actual) {
        refractarLaser(laser, laser.getDireccion(), laser.getDireccionPos(), actual);
        return true;
    }

    private boolean actualizarLaser(Laser laser, Coordenada posibleCoordenada, Coordenada posDireccion, Coordenada vector, Coordenada actual) {

        // Para refractar el laser, suma dos posiciones en el eje correspondiente y luego suma una nueva posicion en el sentido de la direccion

        Coordenada ultPos = laser.getUltimaPos();

        if  (posibleCoordenada.equals(actual)) {
            laser.agregarPos(ultPos.sumar(vector));
            ultPos = laser.getUltimaPos();
            laser.agregarPos(ultPos.sumar(vector));
            ultPos = laser.getUltimaPos();
            laser.agregarPos(ultPos.sumar(posDireccion));
            return true;
        }
        return false;
    }

    private void refractarLaser(Laser laser, Direccion dir, Coordenada posDireccion, Coordenada actual) {

        // Para cada direccion, obtiene las posibles coordenadas que pueden ser refractadas
        // Si se puede, la refracta

        Coordenada izq = celda.getImparIzq();
        Coordenada der = celda.getImparDer();
        Coordenada arriba = celda.getImparArriba();
        Coordenada abajo = celda.getImparAbajo();

        switch (dir) {
            case SE:
                if (actualizarLaser(laser,izq, posDireccion, XE.getPos(), actual)) { return; }
                actualizarLaser(laser,der, posDireccion, YN.getPos(), actual);
                break;
            case SW:
                if (actualizarLaser(laser,der, posDireccion, XW.getPos(), actual)) { return; }
                actualizarLaser(laser,arriba, posDireccion, YN.getPos(), actual);
                break;
            case NE:
                if (actualizarLaser(laser,izq, posDireccion, XE.getPos(), actual)) { return; }
                actualizarLaser(laser,abajo, posDireccion, YS.getPos(), actual);
                break;
            case NW:
                if (actualizarLaser(laser,der, posDireccion, XW.getPos(), actual)) { return; }
                actualizarLaser(laser,abajo, posDireccion, YS.getPos(), actual);
                break;
        }
    }

}
