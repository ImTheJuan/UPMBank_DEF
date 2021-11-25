package com.company;

public class Transferencia {
    final private Cuenta cuentaOrigen;
    final private Cuenta cuentaDestino;
    final private double dineroATransferir;

    public Transferencia(Cuenta cuentaOrigen, Cuenta cuentaDestino, double dineroATransferir){
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.dineroATransferir = dineroATransferir;
    }

    public Cuenta getCuentaOrigen() {
        return cuentaOrigen;
    }

    public Cuenta getCuentaDestino() {
        return cuentaDestino;
    }

    public double getDineroATransferir() {
        return dineroATransferir;
    }
}
