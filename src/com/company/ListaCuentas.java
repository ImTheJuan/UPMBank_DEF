package com.company;

public class ListaCuentas {
    private final Cuenta[] lista;
    public final int MAXIMO_CUENTAS;
    private int numeroCuentas;

    public ListaCuentas(int maximoCuentas) {
        MAXIMO_CUENTAS = maximoCuentas;
        lista = new Cuenta[MAXIMO_CUENTAS];
        numeroCuentas = 0;
    }

    public Cuenta[] getLista() {
        return lista;
    }

    public int getNumeroCuentas() {
        return numeroCuentas;
    }

    public void insertarCuenta(Cuenta cuenta){
        if(numeroCuentas<MAXIMO_CUENTAS) {
            lista[numeroCuentas] = cuenta;
            numeroCuentas++;
        }
        else System.out.println("Has llegado al límite de cuentas que puedes crear");
    }
}
