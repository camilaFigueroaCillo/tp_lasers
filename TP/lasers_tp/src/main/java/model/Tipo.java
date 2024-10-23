package model;

import java.util.function.Function;

public enum Tipo {
    VACIO(' ', null),
    PISO('.', null),
    OPACOFIJO('F', BloqueOpaco::new),
    OPACOMOVIL('B', BloqueOpaco::new),
    ESPEJO('R', BloqueEspejo::new),
    VIDRIO('G', BloqueVidrio::new),
    CRISTAL('C', BloqueCristal::new);

    private char tipo;
    private Function<Tipo, Bloque> cons;

    Tipo(char tipo, Function<Tipo, Bloque> cons) {
        this.tipo = tipo;
        this.cons = cons;
    }

    public Bloque crearBloque() {
        //A partir de un tipo, crea y retorna el bloque correspondiente a su tipo;
        if (cons == null) {
            return null;
        }
        return cons.apply(this);
    }

    public static Tipo convertirATipo(char c) {

        // A partir de un char simbolizando el tipo, retorna el Tipo correspondiente

        for (Tipo t: Tipo.values()) {
            if (t.tipo == c) {
                return t;
            }
        }
        return null;
    }

}
