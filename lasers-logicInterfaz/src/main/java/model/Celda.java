package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Celda {

    private Integer dimension;
    private boolean esPiso = true;
    private Coordenada posIni;
    private Bloque bloque = null;
    private List<Coordenada> impares;
    private Coordenada centro;

    public Celda(int filaIni, int colIni, int dim) {
        this.posIni = new Coordenada(colIni, filaIni);
        this.impares = generarImpares();
        this.centro = new Coordenada(colIni +1, filaIni + 1);
        this.dimension = dim;
    }

    public void setTipo(Tipo tipo) {
        if (tipo == Tipo.VACIO) {
            this.esPiso = false;
        }
        if (tipo != Tipo.PISO && tipo != Tipo.VACIO) {
            Bloque b = tipo.crearBloque(this);
            this.bloque = b;
        }
    }

    public boolean agregarBloque(Bloque b) {
        if (esPiso && this.bloque == null) {
            this.bloque = b;
            return true;
        }
        return false;
    }

    public void sacarBloque() {
        if (esPiso && this.bloque != null) {
            this.bloque = null;
        }
    }

    public Bloque getBloque() {
        return bloque;
    }

    public void interactuarConLaser(Laser laser, List<Laser> recorrido) {
            this.bloque.dirigirLaser(laser, recorrido);
    }

    public Coordenada getPosIni() {
        return posIni;
    }

    public boolean esPiso() {
        return esPiso;
    }

    public Coordenada getImparArriba() {
        return impares.get(0);
    }

    public Coordenada getImparAbajo() {
        return impares.get(1);
    }

    public Coordenada getImparIzq() {
        return impares.get(2);
    }

    public Coordenada getImparDer() {
        return impares.get(3);
    }

    private List<Coordenada> generarImpares() {
        var arriba = posIni.sumar(new Coordenada(1, 0));
        var abajo = posIni.sumar(new Coordenada(1, 2));
        var izq = posIni.sumar(new Coordenada(0, 1));
        var der = posIni.sumar(new Coordenada(2, 1));
        var centros = new ArrayList<Coordenada>();
        centros.addAll(Arrays.asList(arriba, abajo, izq, der));
        return centros;
    }

    public List<Coordenada> getImpares() {
        return impares;
    }

    public Coordenada getCentro() {
        return centro;
    }


}


