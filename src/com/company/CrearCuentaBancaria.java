package com.company;

import java.util.Scanner;

public class CrearCuentaBancaria {

    static Scanner teclado = new Scanner(System.in);

    public static String comprobarTipoDeCuenta(int numeroTipoDeCuenta) {

        String tipoDeCuenta = "";
        boolean tipoDeCuentaIncorrecto = false;

        do {
            if (numeroTipoDeCuenta == 1) tipoDeCuenta = "Corriente";
            else if (numeroTipoDeCuenta == 2) tipoDeCuenta = "Ahorro";
            else if (numeroTipoDeCuenta == 3) tipoDeCuenta = "Remunerada";
            else tipoDeCuentaIncorrecto = true;
        } while (tipoDeCuentaIncorrecto);

        return tipoDeCuenta;
    }

    public static int generarDigitoControl1(String codigoEntidad, String codigoSucursal) {

        int digitoControl1 = 0;

        int cs0 = Integer.parseInt(String.valueOf(codigoSucursal.charAt(0)));
        int cs1 = Integer.parseInt(String.valueOf(codigoSucursal.charAt(1)));
        int cs2 = Integer.parseInt(String.valueOf(codigoSucursal.charAt(2)));
        int cs3 = Integer.parseInt(String.valueOf(codigoSucursal.charAt(3)));
        int ce0 = Integer.parseInt(String.valueOf(codigoEntidad.charAt(0)));
        int ce1 = Integer.parseInt(String.valueOf(codigoEntidad.charAt(1)));
        int ce2 = Integer.parseInt(String.valueOf(codigoEntidad.charAt(2)));
        int ce3 = Integer.parseInt(String.valueOf(codigoEntidad.charAt(3)));

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


}
