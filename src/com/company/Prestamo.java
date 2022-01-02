package com.company;

import java.util.Scanner;

public class Prestamo {
    private Cuenta cuenta;
    private double capital;
    private double interesAnual;
    private int anos;
    private double cuotaMensual;
    private double intereses;
    private double capitalAmortizado;
    private double capitalVivo;

    public Prestamo(Cuenta cuenta, double capital, double interesAnual, int anos) {
        this.cuenta = cuenta;
        this.capital = capital;
        this.interesAnual = interesAnual;
        this.anos = anos;
        cuotaMensual = 0;
        intereses = 0;
        capitalAmortizado = 0;
        capitalVivo = capital;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public double getInteresAnual() {
        return interesAnual;
    }

    public void setInteresAnual(double interesAnual) {
        this.interesAnual = interesAnual;
    }

    public int getAnos() {
        return anos;
    }

    public void setAnos(int anos) {
        this.anos = anos;
    }

    public double getCuotaMensual() {
        return cuotaMensual;
    }

    public void setCuotaMensual(double cuotaMensual) {
        this.cuotaMensual = cuotaMensual;
    }

    public double getIntereses() {
        return intereses;
    }

    public void setIntereses(double intereses) {
        this.intereses = intereses;
    }

    public double getCapitalAmortizado() {
        return capitalAmortizado;
    }

    public void setCapitalAmortizado(double capitalAmortizado) {
        this.capitalAmortizado = capitalAmortizado;
    }

    public double getCapitalVivo() {
        return capitalVivo;
    }

    public void setCapitalVivo(double capitalVivo) {
        this.capitalVivo = capitalVivo;
    }

    public static Prestamo solicitarPrestamo(Scanner teclado, ListaClientes listaClientes) throws NullPointerException {
        //Identifica al cliente y le pide que seleccione con cual de sus cuentas operar
        System.out.println("Introduzca su DNI: ");
        String dni = teclado.next();
        Cliente cliente = Cliente.buscarPorDNI(listaClientes.getLista(), dni);
        cliente.mostrarCuentas();

        System.out.println("Seleccione la cuenta con la que desea reallizar la transferencia (1, 2, 3 ...):");
        int opcion = teclado.nextInt();
        Cuenta cuenta = cliente.getListaCuentas().getLista()[opcion - 1];

        //Pide los datos necesarios al cliente
        System.out.println("Introduzca el capital por el cual desea al préstamo:");
        double capitalPrestamo = teclado.nextDouble();

        System.out.println("Introduzca el número de años en los cuales se deberá devolver el préstamo:");
        int anosPrestamo = teclado.nextInt();
        int mesesPrestamo = anosPrestamo*12;

        System.out.println("Introduzca interés anual al que quiere pagar este préstamo (introduzca el valor como porcentaje) :");
        double interesAnual = teclado.nextDouble()/100;
        double interesMensual = interesAnual/12;

        generarTablaAmortizacion(capitalPrestamo, interesMensual, mesesPrestamo);

        //Se introduce el capital solicitado a la cuenta del usuario
        cuenta.setSaldo(cuenta.getSaldo()+capitalPrestamo);
        System.out.println("El capital de tu préstamo se ha introducido a tu cuenta bancaria.");

        //Se crea el préstamo y se introduce en la lista de préstamos
        Prestamo prestamo = new Prestamo(cuenta, capitalPrestamo, interesAnual, anosPrestamo);
        cuenta.getListaPrestamos().insertarPrestamo(prestamo);

        return prestamo;
    }

    public static void generarTablaAmortizacion(double capitalPrestamo, double interesMensual, int mesesPrestamo) {

        double cuotaMensual = 0;
        double intereses = 0;
        double capitalAmortizado = 0;
        double capitalVivo = capitalPrestamo;

        System.out.println("Perfecto, esta es la tabla de amortización para el préstamo solicitado.");

        System.out.printf("%2s %15s %15s %15s %15s","MES","PAGO MENSUAL","INTERESES","AMORTIZADO","CAPITAL VIVO\n");
        System.out.printf("%2d %15f %15f %15f %15f\n", 0, cuotaMensual, intereses, capitalAmortizado, capitalVivo);

        for (int i=1; i<=mesesPrestamo;i++) {
            cuotaMensual = capitalPrestamo*interesMensual*(Math.pow(1+interesMensual, mesesPrestamo)/(Math.pow(1+interesMensual,mesesPrestamo)-1));
            intereses = capitalVivo*interesMensual;
            capitalAmortizado = cuotaMensual-intereses;
            capitalVivo = capitalVivo-capitalAmortizado;
            if (i<=20 || i>=mesesPrestamo-20) {
                System.out.printf("%2d %15f %15f %15f %15f", i, cuotaMensual, intereses, capitalAmortizado, capitalVivo);
                System.out.println();
            }
        }
    }

    public void imprimir(){
        System.out.println("Tipo: PRÉSTAMO");
        System.out.println("Cuenta: "+ cuenta.getNumero());
        System.out.println("Capital: " + this.capital);
        System.out.println("Interés anual: " + this.interesAnual);
        System.out.println("Tiempo del préstamo: " + this.anos + " años.");
        System.out.println();
    }
}
