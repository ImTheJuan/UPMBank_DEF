package com.company;

import java.util.Objects;
import java.util.Scanner;

public class Cliente {
    final private String nombre;
    final private String apellidos;
    final private String correo;
    final private String dni;
    final private String fechaNacimiento;
    final private Cuenta[] listaCuentas;
    private int numeroDeCuentas;

    public Cliente(String nombre, String apellidos, String correo, String dni, String fechaNacimiento) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.listaCuentas = new Cuenta[10];
        this.numeroDeCuentas = 0;
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

    public Cuenta[] getListaCuentas() {
        return listaCuentas;
    }

    public int getNumeroDeCuentas() {
        return numeroDeCuentas;
    }

    public void setNumeroDeCuentas(int numeroDeCuentas) {
        this.numeroDeCuentas = numeroDeCuentas;
    }

    //Función que comprueba que el correo pertenezca a la upm y que no lo tenga ya otro usuario existente.
    public static String comprobarCorreo(Scanner teclado, Cliente[] listaClientes) {

        boolean correoInvalido = true;
        String correo;

        do {
            correo = teclado.next();
            if (correo.contains("@alumnos.upm.es") || correo.contains("@upm.es")) {
                if (Cliente.buscarPorCorreo(listaClientes, correo) == null) correoInvalido = false;
                else System.out.println("Ya existe un usuario registrado con esa dirección de correo. Intente de nuevo");
            }
            else System.out.println("Tu correo no es válido, vuelve a intentarlo con tu correo UPM:");


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

    public static Cliente buscarPorDNI(Cliente[] listaClientes, String DNI){
        int i = 0;
        Cliente cliente = null;
        while (i<listaClientes.length && cliente == null){
            if (listaClientes[i].getDni().equals(DNI)) cliente = listaClientes[i];
            i++;
        }
        return cliente;
    }

    //Se puede optimizar y que no recorra_todo el array siempre cuando se encuentra un null
    //Esta función devuelve el cliente en caso de coincidir el correo y null en caso de que no exista.
    public static Cliente buscarPorCorreo(Cliente[] listaClientes, String correo){
        int i = 0;
        Cliente cliente = null;
        while (i<listaClientes.length && cliente == null){
            if (listaClientes[i] != null){
                if (listaClientes[i].getCorreo().equals(correo)) cliente = listaClientes[i];
            }
            i++;
        }
        return cliente;
    }

    public static Cliente crearCliente(Scanner teclado, Cliente[] listaClientes) {
        String nombre, apellidos, correo, fechaNacimiento, dniCompleto;

        //Recoger datos del usuario
        teclado.nextLine();
        System.out.println("Introduce tu nombre: ");
        nombre = teclado.nextLine();

        System.out.println("Introduce tus apellidos: ");
        apellidos = teclado.nextLine();
        System.out.println("Hola " + nombre + " " + apellidos);

        System.out.println("Introduce tu correo:");
        correo = comprobarCorreo(teclado, listaClientes);

        System.out.println("Introduce tu fecha de nacimiento: formato (dd/mm/aaaa)");
        fechaNacimiento = comprobarFecha(teclado);

        dniCompleto = comprobarDni(teclado);


        //Creación del cliente
        Cliente cliente = new Cliente(nombre, apellidos, correo, dniCompleto, fechaNacimiento);

        System.out.println("Perfecto, ya estás registrado en UPMBank.");

        return cliente;
    }

    public void mostrarDatos() {

        System.out.println("Hola " + this.getNombre() + " " + this.getApellidos());
        System.out.println("Estos son los datos de tu cuenta: \n");
        System.out.println("DNI: " + this.getDni());
        System.out.println("Correo electrónico: " + this.getCorreo());
        System.out.println("Fecha de Nacimiento: " + this.getFechaNacimiento());
        System.out.println();

        if (this.getListaCuentas()[0] != null) {
            System.out.println("Estas son las cuentas que tiene creadas en su cuenta: \n");
            for(int i = 0; i < this.getNumeroDeCuentas(); i++) {
                int numeroMenu = i+1;
                System.out.println(numeroMenu + ". Cuenta Nº : " + this.getListaCuentas()[i].getNumero());
                System.out.println("Tipo: " + this.getListaCuentas()[i].getTipoDeCuenta());
                System.out.println("Saldo actual: " + this.getListaCuentas()[i].getSaldo() + "€");
            }
            /*
            System.out.println("Seleccione una cuenta para ver sus transacciones con ella. Si no, pulse 0 para salir.");
            menuCuentas =1;
            if (menuCuentas == 1) {
                if (depositoRealizado) System.out.println("Depósito de " + dineroAIngresar + "€");
                if (retiroRealizado) System.out.println("Retiro de " + dineroARetirar + "€");
                if (transferenciaReaizada) System.out.println("Transferencia de " + dineroAIngresar + "€ a la cuenta " + cuentaDestinoTransferencia);
                if (prestamoRealizado) System.out.println("Préstamo activo de " + capitalPrestamo + "€ a " + interesAnual*100 + "% de interés anual por " + anosPrestamo + " años.");
            }*/
        } else System.out.println("No existe ninguna cuenta bancaria para este usuario. Si desea crear una, seleccione la opción 2 del menú principal.");
    }
}
