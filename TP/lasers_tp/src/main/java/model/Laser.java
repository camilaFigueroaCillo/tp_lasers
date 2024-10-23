package model;

import model.Direccion;

import java.util.ArrayList;
import java.util.List;

public class Laser {
    private List<Direccion> dir = new ArrayList<>();
    private List<Coordenada> recorrido = new ArrayList<>();

    public Laser(Coordenada ini, Direccion d) {
        recorrido.add(ini);
        dir.add(d);
    }

    public Direccion getDireccion() {
        return dir.get(dir.size() - 1);
    }

    public Coordenada getUltimaPos() {
        return recorrido.get(recorrido.size() - 1);
    }

    public Coordenada getDireccionPos() {
        var actual = dir.get(dir.size() - 1);
        return actual.getPos();
    }

    public void cambiarDireccion(Direccion d) {
        dir.add(d);
    }

    public void agregarPos(Coordenada b) {
        recorrido.add(b);
    }

    public List<Coordenada> getRecorrido() {
        return recorrido;
    }

    public void resetRecorrido() {
        var primerPos = recorrido.get(0);
        var primerDir = dir.get(0);
        recorrido.clear();
        recorrido.add(primerPos);
        dir.clear();
        dir.add(primerDir);
    }

}
