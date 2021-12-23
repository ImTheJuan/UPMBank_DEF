package com.company;

public class ListaTransferencias {
    public final int MAXIMO_TRANSFERENCIAS;
    private Transferencia[] lista;
    private int ocupacion;

    public ListaTransferencias(int maximo){
        MAXIMO_TRANSFERENCIAS = maximo;
        lista = new Transferencia[maximo];
        ocupacion = 0;
    }

    public Transferencia[] getLista() {
        return lista;
    }

    public int getOcupacion() {
        return ocupacion;
    }

    public void insertarTransferencia(Transferencia transferencia){
        this.getLista()[ocupacion] = transferencia;
        ocupacion++;
    }
}
