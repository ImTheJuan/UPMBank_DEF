package com.company;

public class ListaPrestamos {
    private final Prestamo[] lista;
    public final int MAXIMO_CUENTAS;
    private int ocupacion;

    public ListaPrestamos(int maximoCuentas) {
        MAXIMO_CUENTAS = maximoCuentas;
        lista = new Prestamo[MAXIMO_CUENTAS];
        ocupacion = 0;
    }

    public Prestamo[] getLista() {
        return lista;
    }

    public int getOcupacion() {
        return ocupacion;
    }

    public void insertarPrestamo(Prestamo prestamo){
        if(ocupacion<MAXIMO_CUENTAS) {
            lista[ocupacion] = prestamo;
            ocupacion++;
        }
        else System.out.println("Has llegado al límite de cuentas que puedes crear");
    }
}
