package com.company;

import java.util.Scanner;

public class Cuenta {
    final private static String CODIGO_SUCURSAL = "9010";
    final private static String CODIGO_ENTIDAD = "0201";
    final private String NUMERO;
    final private String TIPODECUENTA;
    private int saldo;
    private Transferencia[] listaTransferencias;

    public Cuenta(String numero, String tipoDeCuenta){
        this.NUMERO = numero;
        this.TIPODECUENTA = tipoDeCuenta;
        this.saldo = 0;
        this.listaTransferencias = new Transferencia[50];
    }

    public String getNumero() {
        return NUMERO;
    }

    public String getTipoDeCuenta() {
        return TIPODECUENTA;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    //La función se encarga de leer un número por teclado entre 1 y 3 y relacionarlo con un tipo de cuenta posible.
    public static String comprobarTipoDeCuenta(Scanner teclado) {

        int numero;
        boolean numeroCorrecto = false;
        String tipoDeCuenta = "";
        do {
            numero = teclado.nextInt();
            if ((numero >= 1) && (numero <= 3)) numeroCorrecto = true;
            else System.out.println("Opción incorrecta, intente de nuevo");
        } while(!numeroCorrecto);

        if (numero == 1) tipoDeCuenta = "Corriente";
        else if (numero == 2) tipoDeCuenta = "Ahorro";
        else tipoDeCuenta = "Remunerada";

        return tipoDeCuenta;
    }

    public static int generarDigitoControl1() {

        int digitoControl1 = 0;

        int cs0 = Integer.parseInt(String.valueOf(CODIGO_SUCURSAL.charAt(0)));
        int cs1 = Integer.parseInt(String.valueOf(CODIGO_SUCURSAL.charAt(1)));
        int cs2 = Integer.parseInt(String.valueOf(CODIGO_SUCURSAL.charAt(2)));
        int cs3 = Integer.parseInt(String.valueOf(CODIGO_SUCURSAL.charAt(3)));
        int ce0 = Integer.parseInt(String.valueOf(CODIGO_ENTIDAD.charAt(0)));
        int ce1 = Integer.parseInt(String.valueOf(CODIGO_ENTIDAD.charAt(1)));
        int ce2 = Integer.parseInt(String.valueOf(CODIGO_ENTIDAD.charAt(2)));
        int ce3 = Integer.parseInt(String.valueOf(CODIGO_ENTIDAD.charAt(3)));

        int resto1 = (cs3*6 + cs2*3 + cs1*7 + cs0*9 + ce3*10 +ce2*5 + ce1*8 + ce0*4) %11;
        if (11-resto1 < 10) digitoControl1 = resto1;
        else if (11-resto1 == 10) digitoControl1 = 1;

        return digitoControl1;
    }

    public static int generarDigitoControl2(String digitosCuentaString) {

        int digitoControl2 = 0;

        int dc0 = Integer.parseInt(String.valueOf(digitosCuentaString.charAt(0)));
        int dc1 = Integer.parseInt(String.valueOf(digitosCuentaString.charAt(1)));
        int dc2 = Integer.parseInt(String.valueOf(digitosCuentaString.charAt(2)));
        int dc3 = Integer.parseInt(String.valueOf(digitosCuentaString.charAt(3)));
        int dc4 = Integer.parseInt(String.valueOf(digitosCuentaString.charAt(4)));
        int dc5 = Integer.parseInt(String.valueOf(digitosCuentaString.charAt(5)));
        int dc6 = Integer.parseInt(String.valueOf(digitosCuentaString.charAt(6)));
        int dc7 = Integer.parseInt(String.valueOf(digitosCuentaString.charAt(7)));
        int dc8 = Integer.parseInt(String.valueOf(digitosCuentaString.charAt(8)));
        int dc9 = Integer.parseInt(String.valueOf(digitosCuentaString.charAt(9)));

        int resto2 = (dc0 + dc1*2 + dc2*4 + dc3*8 + dc4*5 + dc5*10 + dc6*9 + dc7*7 + dc8*3 +dc9*6) %11;
        if (11-resto2 < 10) digitoControl2 = resto2;
        else if (resto2 == 10) digitoControl2 = 1;

        return digitoControl2;
    }

    public static Cuenta crearCuenta(Scanner teclado) {

        String tipoDeCuenta="", digitosCuentaString, numeroDeCuenta="";
        long digitosCuenta;
        int digitoControl1, digitoControl2;

        //Se genera el número de cuenta y se recoge el tipo deseado
        System.out.println("Seleccione el tipo de cuenta que desea crear:");
        System.out.println("1. Corriente");
        System.out.println("2. Ahorro");
        System.out.println("3. Remunerada");
        tipoDeCuenta = Cuenta.comprobarTipoDeCuenta(teclado);

        digitosCuenta =  (long)(Math.random() * 10000000000L);
        digitosCuentaString = Long.toString(digitosCuenta);

        digitoControl1 = Cuenta.generarDigitoControl1();
        digitoControl2 = Cuenta.generarDigitoControl2(digitosCuentaString);

        numeroDeCuenta = CODIGO_ENTIDAD + CODIGO_SUCURSAL + digitoControl1 + digitoControl2 + digitosCuentaString;

        //Se crea la cuenta.
        Cuenta cuenta = new Cuenta(numeroDeCuenta, tipoDeCuenta);

        return cuenta;
    }

    public void mostrarCuenta(){
        System.out.println("Tu cuenta se ha creado correctamente:");
        System.out.println("Tipo de cuenta: " + this.getTipoDeCuenta());
        System.out.println("Numero de cuenta: " + this.getNumero());
        System.out.println("Saldo en la cuenta: " + this.getSaldo() + "€");
    }
    //La función introduce una cuenta en la lista de cuentas perteneciente alcliente que se quiere
    //¿Vale la pena hacer una funcion de esto?
    public void asignarAUsuario(Cliente cliente){

        cliente.getListaCuentas()[cliente.getNumeroDeCuentas()] = this;
    }
}
