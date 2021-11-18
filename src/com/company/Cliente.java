package com.company;

import java.util.Objects;
import java.util.Scanner;

public class Cliente {
    private String nombre;
    private String apellidos;
    private String correo;
    private String dni;
    private String fechaNacimiento;

    public Cliente(String nombre, String apellidos, String correo, String dni, String fechaNacimiento) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDni() {
        return dni;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public static String comprobarCorreo(Scanner teclado) {

        boolean correoInvalido = true;
        String correo;

        do {
            correo = teclado.next();
            if (correo.contains("@alumnos.upm.es") || correo.contains("@upm.es")) correoInvalido = false;
            else {
                System.out.println("Tu correo no es válido, vuelve a intentarlo con tu correo UPM:");
            }

        } while(correoInvalido);
        return correo;
    }

    public static String comprobarFecha(Scanner teclado) {

        boolean fechaIncorrecta = true;
        String fechaNacimiento;

        do {
            int diaNacimiento;
            int mesNacimiento;
            int anoNacimiento;

            boolean isDiaCorrect;
            boolean isMesCorrect;
            boolean isAnoCorrect;
            //1, 3, 5, 7, 8, 10, 12
            fechaNacimiento = teclado.next();
            if (fechaNacimiento.length() == 10) {
                diaNacimiento = Integer.parseInt(fechaNacimiento.substring(0, 1)) * 10 + Integer.parseInt(fechaNacimiento.substring(1, 2));
                mesNacimiento = Integer.parseInt(fechaNacimiento.substring(3, 4)) * 10 + Integer.parseInt(fechaNacimiento.substring(4, 5));
                anoNacimiento = Integer.parseInt(fechaNacimiento.substring(6, 7)) * 1000 + Integer.parseInt(fechaNacimiento.substring(7, 8))*100 + Integer.parseInt(fechaNacimiento.substring(8, 9))*10 + Integer.parseInt(fechaNacimiento.substring(9, 10));
                isDiaCorrect = (diaNacimiento > 0 && diaNacimiento <= 31);
                isMesCorrect = (mesNacimiento > 0 && mesNacimiento <= 12);
                isAnoCorrect = (anoNacimiento > 1920 && anoNacimiento <= 2021);
                if (!(isDiaCorrect && isMesCorrect && isAnoCorrect)) System.out.println("La fecha introducida no es válida, vuelve a intentarlo con el formato correspondiente.");
                else fechaIncorrecta = false;
            } else {
                System.out.println("La fecha introducida no es válida, vuelve a intentarlo con el formato correspondiente.");
            }
        } while (fechaIncorrecta);
        return fechaNacimiento;
    }

    public static String comprobarDni(Scanner teclado) {

        int moduloDni;
        int dni;
        String letraDni;
        boolean dniIncorrecto = true;

        do {
            System.out.println("Introduce el número de tu DNI");
            dni = teclado.nextInt();
            System.out.println("Introduce la letra de tu DNI:");
            letraDni = teclado.next();

            String[] comprobacionLetras = new String[]{"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};
            moduloDni = dni % 23;
            boolean esLetraIgualAComprobacion = Objects.equals(letraDni, comprobacionLetras[moduloDni]);
            if (!(esLetraIgualAComprobacion && (dni%10000000 != dni))) {
                System.out.println("Tu DNI es incorrecto. Intenta nuevamente");
            }
            else dniIncorrecto = false;
        } while(dniIncorrecto);
        return dni + letraDni;
    }
}
