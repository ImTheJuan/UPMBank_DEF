package com.company;

public class ListaMovimientos {
    public final int MAXIMO_OPERACIONES;
    private Movimiento[] lista;
    private int ocupacion;

    public ListaMovimientos(int maximo){
        MAXIMO_OPERACIONES = maximo;
        lista = new Movimiento[maximo];
        ocupacion = 0;
    }

    public Movimiento[] getLista() {
        return lista;
    }

    public int getOcupacion() {
        return ocupacion;
    }

    public void insertarMovimiento(Movimiento movimiento){
        this.getLista()[ocupacion] = movimiento;
        ocupacion++;
    }
}
