package model;

public enum Direccion {
    SE("SE", new Coordenada(1, 1)),
    SW("SW", new Coordenada(-1, 1)),
    NE("NE", new Coordenada(1, -1)),
    NW("NW", new Coordenada(-1, -1)),
    XE("XE", new Coordenada(1, 0)),
    XW("XW", new Coordenada(-1, 0)),
    YN("YN", new Coordenada(0, 1)),
    YS("YS", new Coordenada(0, -1));

    private Coordenada pos;
    private String simbolo;

    Direccion(String simbolo, Coordenada c) {
        this.pos = c;
        this.simbolo = simbolo;
    }

    public static Direccion convertirADir(String c) {

        // A partir de un string representando el simbolo, devuelve su instancia direccion correspondiente

        for (Direccion d: Direccion.values()) {
            if (d.simbolo.equals(c)) {
                return d;
            }
        }
        return null;
    }

    public Direccion coordToDir(Coordenada c) {

        for (Direccion d: Direccion.values()) {
            if (d.pos.equals(c)) {
                return d;
            }
        }
        return null;
    }

    public Coordenada getPos() {
        return pos;
    }

}
