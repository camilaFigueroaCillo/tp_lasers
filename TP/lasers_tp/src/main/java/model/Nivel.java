package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Nivel {
    private Tablero tablero;
    private List<Laser> lasers = new ArrayList();
    private List<Objetivo> objetivos = new ArrayList();
    private Boolean ganador = false;
    private HashSet objsApuntados = new HashSet<>();
    private List<Laser> nuevosLasers = new ArrayList<>();
    private HashMap<Coordenada, Celda> centros;

    public Nivel(ProcesadorArchivo procesador) {
        this.tablero = new Tablero(procesador.getInfoTablero());
        setLasers(procesador.getInfoEmisores());
        setObjetivos(procesador.getInfoObjetivos());
        this.centros = tablero.getCentros();
    }

    private void setLasers(List<List<String>> emisores) {
        // Dada la info del archivo creo los lasers y los agrego a la lista de lasers.
        for (List em: emisores) {

            Direccion dire = Direccion.convertirADir((String) em.get(2));
            Coordenada coordenada = new Coordenada(Integer.valueOf((String) em.get(0)),Integer.valueOf((String) em.get(1)));
            this.lasers.add(new Laser(coordenada, dire));
        }
    }

    private void setObjetivos(List<List<Integer>> objs) {
        for (List obj: objs) {
            var coordenada = new Coordenada((int) obj.get(0), (int) obj.get(1));
            objetivos.add(new Objetivo(coordenada));
        }
    }

    private void checkGanador() {

        // actualiza el estado del ganador, si la cdad de objetivos apuntados corresponde a la cdad de objetivos en total
        int cdadApuntados = objsApuntados.size();
        int cdadObjetivos = objetivos.size();
        if (cdadApuntados == cdadObjetivos) {
            this.ganador = true;
        }
    }

    public void moverLasers() {

        //Por cada laser en la lista de lasers, va recorriendo el tablero y actualizando el recorrido del laser.

        resetFromOrigin();

        var nuevos = new ArrayList<Laser>();
        var columnas = tablero.getColumnas();
        var filas = tablero.getFilas();

        for (Laser l: lasers) {
            actualizarLaser(l, columnas, filas, nuevos);
        }

        if (nuevos.size() > 0) {
            lasers.addAll(nuevos);
            nuevosLasers.addAll(nuevos);
            resetLasers();
            resetObjetivos();
            for (Laser l: lasers) {
                actualizarLaser(l, columnas, filas, nuevos);
            }
        }

        apuntarObjetivos();
        checkGanador();

    }

    private void resetLasers() {
        for (Laser l: lasers) {
            l.resetRecorrido();
        }
    }

    private void resetObjetivos() {
        objsApuntados = new HashSet();
        for (Objetivo o: objetivos) {
            o.reset();
        }
    }

    private void actualizarLaser(Laser l, int columnas, int filas, List<Laser> lasers) {

        var ultPos = l.getUltimaPos();
        var dirPos = l.getDireccionPos();

        if ((ultPos.getX() < 0) || (ultPos.getX() > columnas * 2) || (ultPos.getY() < 0) || (ultPos.getY() > filas * 2)) {
            return;
        }

        Celda actual = decidirCelda(ultPos, dirPos);

        if (actual == null) {
            var nuevaPosicion = ultPos.sumar(dirPos);
            l.agregarPos(nuevaPosicion);

        } else {
            actual.interactuarConLaser(l, lasers);
        }

        var nuevaPos = l.getUltimaPos();

        if (nuevaPos.equals(ultPos)) {
            return;
        }

        actualizarLaser(l, columnas, filas, lasers);

    }

    private Celda decidirCelda(Coordenada posicionLaser, Coordenada direccionLaser) {
        var opcionUno = posicionLaser.sumar(new Coordenada(direccionLaser.getX(), 0));
        var opcionDos = posicionLaser.sumar(new Coordenada(0, direccionLaser.getY()));
        if (centros.containsKey(opcionUno)) {
            return centros.get(opcionUno);
        }
        if (centros.containsKey(opcionDos)) {
            return centros.get(opcionDos);
        }

        return null;
    }

    public boolean moverBloques(Celda origen, Celda destino) {
        if (origen == null || destino == null ) {
            return false;
        }
        Bloque bloque = origen.getBloque();

        if (bloque == null || !bloque.desplazar(destino)) {
            return false;
        }

        origen.sacarBloque();

        if (!destino.agregarBloque(bloque)) {
            origen.agregarBloque(bloque);
            return false;
        }

        Coordenada centroOrigen = origen.getCentro();
        Coordenada centroDestino = destino.getCentro();

        centros.remove(centroOrigen);
        centros.put(centroDestino, destino);
        return true;
    }

    private void apuntarObjetivos() {

        //Por cada laser la lista de lasers, recorre todos los objetivos y si el laser toca algun objetivo
        //  actualiza el estado de este.

        for (Laser l : lasers) {
            var recorrido = l.getRecorrido();
            for (Coordenada c: recorrido) {
                for (Objetivo o: objetivos) {
                    if (c.equals(o.getCoordenada()) && !o.fueApuntado()) {
                        objsApuntados.add(o);
                        o.apuntar();
                    }
                }
            }
        }

    }

    public Boolean hayGanador() {
        return ganador;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public List<Laser> getLasers() {
        return lasers;
    }

    public List<Objetivo> getObjetivos() {
        return objetivos;
    }

    public int getFilas() {
        return this.tablero.getFilas();
    }

    public int getColumnas() {
        return this.tablero.getColumnas();
    }

    private void resetFromOrigin() {
        lasers.removeIf(l -> nuevosLasers.contains(l));
        resetLasers();
        resetObjetivos();
    }

}
