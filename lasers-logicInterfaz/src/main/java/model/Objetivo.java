package model;

public class Objetivo {
    private final Coordenada coordenada;
    private boolean apuntado;

    public Objetivo(Coordenada coordenada) {
        this.coordenada = coordenada;
        this.apuntado = false;
    }

    public void apuntar() {
        if (!this.apuntado) {
            this.apuntado = true;
        }
    }

    public boolean fueApuntado() {
        return this.apuntado;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public void reset() {
        this.apuntado = false;
    }
}
