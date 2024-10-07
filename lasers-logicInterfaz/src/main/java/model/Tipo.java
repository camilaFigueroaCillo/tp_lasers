package model;

import java.util.function.BiFunction;

public enum Tipo {
    VACIO(' ', null),
    PISO('.', null),
    OPACOFIJO('F', BloqueOpaco::new),
    OPACOMOVIL('B', BloqueOpaco::new),
    ESPEJO('R', BloqueEspejo::new),
    VIDRIO('G', BloqueVidrio::new),
    CRISTAL('C', BloqueCristal::new);

    private char tipo;
    private BiFunction<Celda, Tipo, Bloque> cons;

    Tipo(char tipo, BiFunction<Celda, Tipo, Bloque> cons) {
        this.tipo = tipo;
        this.cons = cons;
    }

    public Bloque crearBloque(Celda celda) {
        //A partir de un tipo, crea y retorna el bloque correspondiente a su tipo;
        if (cons == null) {
            return null;
        }
        
        return cons.apply(celda, this);
    }

    public static Tipo convertirATipo(char c) {

        // A partir de un char simbolizando el tipo, genera y retorna el Tipo correspondiente

        for (Tipo t: Tipo.values()) {
            if (t.tipo == c) {
                return t;
            }
        }
        return null;
    }

}
