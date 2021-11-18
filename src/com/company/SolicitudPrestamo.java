package com.company;

public class SolicitudPrestamo {

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
}
