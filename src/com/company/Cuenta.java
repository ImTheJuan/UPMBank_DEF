package com.company;

import java.util.Scanner;

public class Cuenta {
    final private static String CODIGO_ENTIDAD = "9010";
    private static String CODIGO_SUCURSAL;
    final private String NUMERO;
    final private TipoDeCuenta TIPO_DE_CUENTA;
    private double saldo;
    private ListaMovimientos listaMovimientos;
    private ListaTransferencias listaTransferencias;
    private ListaPrestamos listaPrestamos;

    public Cuenta(String numero, TipoDeCuenta tipoDeCuenta){
        NUMERO = numero;
        TIPO_DE_CUENTA = tipoDeCuenta;
        saldo = 0;
        listaTransferencias = new ListaTransferencias(20);
        listaMovimientos = new ListaMovimientos(20);
        listaPrestamos = new ListaPrestamos(20);
    }

    public String getNumero() {
        return NUMERO;
    }

    public TipoDeCuenta getTipoDeCuenta() {
        return TIPO_DE_CUENTA;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public static void setCodigoSucursal(String codigoSucursal) {
        CODIGO_SUCURSAL = codigoSucursal;
    }

    public ListaMovimientos getListaMovimientos() {
        return listaMovimientos;
    }

    public ListaTransferencias getListaTransferencias() {
        return listaTransferencias;
    }

    public ListaPrestamos getListaPrestamos(){
        return listaPrestamos;
    }

    //La función se encarga de leer un número por teclado entre 1 y 3 y relacionarlo con un tipo de cuenta posible.
    enum TipoDeCuenta {CORRIENTE, AHORRO, REMUNERADA}
    public static TipoDeCuenta comprobarTipoDeCuenta(Scanner teclado) {

        int numero;
        boolean numeroCorrecto = false;
        TipoDeCuenta tipoDeCuenta;
        do {
            numero = teclado.nextInt();
            if ((numero >= 1) && (numero <= 3)) numeroCorrecto = true;
            else System.out.println("Opción incorrecta, intente de nuevo");
        } while(!numeroCorrecto);

        if (numero == 1) tipoDeCuenta = TipoDeCuenta.CORRIENTE;
        else if (numero == 2) tipoDeCuenta = TipoDeCuenta.AHORRO;
        else tipoDeCuenta = TipoDeCuenta.REMUNERADA;

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

    public static Cuenta crearCuenta(Scanner teclado, ListaCuentas listaCuentas) {

        String digitosCuentaString, numeroDeCuenta="";
        long digitosCuenta;
        int digitoControl1, digitoControl2;
        TipoDeCuenta tipoDeCuenta;

        //Se genera el número de cuenta y se recoge el tipo deseado
        System.out.println("Seleccione el tipo de cuenta que desea crear:");
        System.out.println("1. Corriente");
        System.out.println("2. Ahorro");
        System.out.println("3. Remunerada");
        tipoDeCuenta = Cuenta.comprobarTipoDeCuenta(teclado);

        //Se comprueba que el número de la cuenta sea único
        do {
            digitosCuenta = (long) (Math.random() * 10000000000L);
            digitosCuentaString = Long.toString(digitosCuenta);

            digitoControl1 = Cuenta.generarDigitoControl1();
            digitoControl2 = Cuenta.generarDigitoControl2(digitosCuentaString);

            numeroDeCuenta = CODIGO_ENTIDAD + CODIGO_SUCURSAL + digitoControl1 + digitoControl2 + digitosCuentaString;
        } while(Cuenta.buscarPorNumero(listaCuentas.getLista(), numeroDeCuenta) != null);

        //Se crea la cuenta.
        Cuenta cuenta = new Cuenta(numeroDeCuenta, tipoDeCuenta);

        System.out.println("Tu cuenta se ha creado exitosamente.");

        return cuenta;
    }

    public void imprimir(){
        System.out.println("Numero de cuenta: " + this.getNumero());
        System.out.println("Tipo: " + this.getTipoDeCuenta());
        System.out.println("Saldo: " + this.getSaldo() + "€");
    }

    //Busca una cuenta a partir de su número
    public static Cuenta buscarPorNumero(Cuenta[] listaCuentas, String numero){
        int i = 0;
        Cuenta cuenta = null;
        while (i<listaCuentas.length && cuenta == null){
            if (listaCuentas[i] != null){
                if (listaCuentas[i].getNumero().equals(numero)) cuenta = listaCuentas[i];
            }
            i++;
        }
        return cuenta;
    }

    public void mostrarMovimientos(){
        if (this.getListaMovimientos().getLista()[0] != null) {
            for (int j = 0; j < this.getListaMovimientos().getOcupacion(); j++) {
                this.getListaMovimientos().getLista()[j].imprimir();
            }
        }
    }
}
