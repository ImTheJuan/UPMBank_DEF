package com.company;

import java.util.Scanner;

public class Movimiento {
    private Cuenta cuenta;
    private String tipo;
    private double dinero;

    public Movimiento(Cuenta cuenta, String tipo, double dinero){
        this.cuenta = cuenta;
        this.tipo = tipo;
        this.dinero = dinero;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public String getTipo() {
        return tipo;
    }

    public double getDinero() {
        return dinero;
    }

    public void setDinero(double dinero) {
        this.dinero = dinero;
    }

    public void imprimir(){
        System.out.println("Cuenta Nº: "+ cuenta.getNumero());
        System.out.println("Tipo: " + tipo);
        System.out.println("Cantidad: " + dinero);
    }

    public static Movimiento crearMovimiento(Scanner teclado, String tipo, ListaClientes listaClientes){
        System.out.println("Introduce tu DNI:");
        String dni = teclado.next();
        Cliente cliente = Cliente.buscarPorDNI(listaClientes.getLista(), dni);
        Movimiento movimiento = null;

        if (cliente != null) {
            if (cliente.getListaCuentas().getLista()[0] != null) {
                //Se muestran las cuentas del usuario y se le pide que seleccione una cuenta
                for (int i = 0; i < cliente.getListaCuentas().getNumeroCuentas(); i++) {
                    int numeroOrden = i + 1;
                    System.out.print(numeroOrden + ". ");
                    cliente.getListaCuentas().getLista()[i].imprimir();
                }
                System.out.println("Seleccione la cuenta a la que desea reallizar el depósito (1, 2, 3 ...):");
                int opcion = teclado.nextInt();
                Cuenta cuenta = cliente.getListaCuentas().getLista()[opcion - 1];

                //Se pide el valor a depositar
                System.out.println("Qué cantidad desea ingresar? Introduzca únicamente el valor numérico del depósito en euros.");
                double dinero = teclado.nextDouble();

                //Se suma o resta el dinero a la cuenta
                if (tipo.equals("DEPOSITO")) cuenta.setSaldo(cuenta.getSaldo() + dinero);
                else if (tipo.equals("RETIRO")) cuenta.setSaldo(cuenta.getSaldo() - dinero);

                //Se crea el movimiento
                movimiento = new Movimiento(cuenta, tipo, dinero);

                //Se introduce en la lista de la cuenta
                cuenta.getListaMovimientos().insertarMovimiento(movimiento);
            }
        }
        return movimiento;
    }
}
