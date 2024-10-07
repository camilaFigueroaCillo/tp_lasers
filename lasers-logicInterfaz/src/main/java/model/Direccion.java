package model;

public enum Direccion {
    SE("SE", 1, 1),
    SW("SW", 1, -1),
    NE("NE", -1, 1),
    NW("NW", -1, -1),
    XE("XE", 0, 1),
    XW("XW", 0, -1),
    YN("YN", 1, 0),
    YS("YS", -1, 0);

    private Coordenada pos;
    private String simbolo;

    Direccion(String simbolo, int f, int c) {
        this.pos = new Coordenada(c, f);
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

    public static Direccion coordToDir(Coordenada c) {

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
