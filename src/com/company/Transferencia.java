package com.company;

import java.util.Scanner;

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

    public void imprimir(){
        System.out.println("Cuenta Destino: "+ cuentaDestino.getNumero());
        System.out.println("Cantidad: " + dineroATransferir);
        System.out.println("Saldo final: " + cuentaOrigen.getSaldo());
    }

    public static Transferencia hacerTransferencia(Cuenta cuentaOrigen, Cuenta cuentaDestino, double dineroATransferir) {

        //Modificación del saldo de las cuentas involucradas en la transferencia.
        if (dineroATransferir <= cuentaOrigen.getSaldo()) {
            cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - dineroATransferir);
            cuentaDestino.setSaldo(cuentaDestino.getSaldo() + dineroATransferir);

            System.out.println("Transacción exitosa. Su nuevo saldo es de " + cuentaOrigen.getSaldo() + "€");
        }
        else System.out.println("Fondos insuficientes");

        //Creación del objeto transferencia
        return new Transferencia(cuentaOrigen, cuentaDestino, dineroATransferir);
    }
}
