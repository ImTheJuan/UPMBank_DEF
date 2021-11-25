package com.company;

import java.util.Scanner;

public class UPMBank {

    /*
    EL PROYECTO TENDRÁ LAS SIGUIENTES CLASES ADEMÁS DE LA MAIN:
    -MENUS (METODOS ESTATICOS CON FUNCIONES BASICAS DE LA APP)
    -CLIENTE (UTILIZARÁ LAS CLASES FECHA Y DNI A SU VEZ)
    -CUENTAS (INTERCONECTAA CON CLIENTES)(UTILIZARÁ TAMBIÉN CLASES INGRESO, RETIRADA, PRESTAMO, TRANSFERENCIA)
    */

    static int cuentaDestinoTransferencia=0, anosPrestamo = 0;
    static double dineroAIngresar=0.0, dineroARetirar=0.0, dineroATransferir, dineroEnCuenta = 0.0;
    static boolean depositoRealizado = false, retiroRealizado = false, transferenciaReaizada = false, prestamoRealizado = false;
    static double capitalPrestamo = 0.0, interesAnual = 0;
    static boolean entradaIncorrecta = true;

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);
        //Creación array que contiene la lista de clientes
        int maximoClientes = 20;
        Cliente[] listaClientes = new Cliente[maximoClientes];
        int numeroClientes = 0;

        do {
            int numeroMenu = escribirMenu(teclado);
            switch (numeroMenu) {

                case 0:
                    entradaIncorrecta = false;
                    break;

                case 1:
                    if (numeroClientes < maximoClientes) {
                        Cliente cliente = Cliente.crearCliente(teclado, listaClientes);
                        listaClientes[numeroClientes] = cliente;
                        numeroClientes++;
                    }
                    else System.out.println("La aplicación ha llegado al límite de usuarios que puede almacenar");
                    finalizarOpcion(teclado);
                    break;

                case 2:
                    //Se pide el correo del usuario y se trae al cliente con dicho usuario
                    System.out.println("Introduce tu correo:");
                    String correo = teclado.next();
                    Cliente cliente = Cliente.buscarPorCorreo(listaClientes, correo);

                    if (cliente != null) {
                        //Se crea la cuenta
                        Cuenta cuenta = Cuenta.crearCuenta(teclado);
                        cuenta.mostrarCuenta();

                        //Asignas la cuenta al usuario deseado y aumentas el número de cuentas que ese usuario tiene.
                        cuenta.asignarAUsuario(cliente);
                        cliente.setNumeroDeCuentas(cliente.getNumeroDeCuentas() + 1);
                    } else System.out.println("No existe ningún usuario registrado con ese correo.");

                    finalizarOpcion(teclado);
                    break;

                case 3:
                    hacerDeposito(teclado);
                    finalizarOpcion(teclado);
                    break;

                case 4:
                    hacerRetiro(teclado);
                    finalizarOpcion(teclado);
                    break;

                case 5:
                    hacerTransferencia(teclado);
                    finalizarOpcion(teclado);
                    break;

                case 6:
                    solicitarPrestamo(teclado);
                    finalizarOpcion(teclado);
                    break;

                case 7:
                    System.out.println("Introduce tu correo:");
                    correo = teclado.next();
                    cliente = Cliente.buscarPorCorreo(listaClientes, correo);
                    if (cliente != null) cliente.mostrarDatos();
                    else System.out.println("No existe ningún usuario registrado en el sistema con esas credenciales. " +
                            "Por favor, asegúrese de estar dado de alta en UPMBank.");
                    finalizarOpcion(teclado);
                    break;

                default:
                    System.out.println("Opción incorrecta. Intente de nuevo");
            }

        } while (entradaIncorrecta);
    }

    public static int escribirMenu(Scanner teclado) {
        System.out.println("Bienvenido a tu app de UPMBank. Selecciona el número del trámite que deseas realizar.");
        System.out.println("1. Darse de alta");
        System.out.println("2. Crear una cuenta bancaria");
        System.out.println("3. Realizar un depósito");
        System.out.println("4. Realizar una extracción");
        System.out.println("5. Realizar una transferencia");
        System.out.println("6. Solicitar un préstamo");
        System.out.println("7. Mostrar datos del cliente");
        System.out.println("0. Salir");
        return teclado.nextInt();
    }

    //Función que pregunta al usuario si desea repetir la ejecución del programa o cerrr la apliciación.
    public static void finalizarOpcion(Scanner teclado){

        int respuesta;
        boolean respuestaIncorrecta = false;

        System.out.println("\nEscriba 0 para volver al menú de inicio y 1 para salir de la aplicación.");
        do {
            respuesta = teclado.nextInt();
            if (respuesta == 1) entradaIncorrecta = false;
            else if (respuesta != 0) {
                System.out.println("Opción Incorrecta. Intenta de nuevo.");
                respuestaIncorrecta = true;
            }
        } while(respuestaIncorrecta);
    }

    //Función que lee una entrada del teclado y comprueba que está entre 2 valores.
    public static int comprobarEntradaNumerica(Scanner teclado, int numero1, int numero2) {
        //numero1 debe ser < numero2
        int numeroUsuario;
        boolean numeroCorrecto = false;

        do {
            numeroUsuario = teclado.nextInt();
            if ((numeroUsuario >= numero1) && (numeroUsuario <= numero2)){
                numeroCorrecto = true;
            }
            else System.out.println("Número incorrecto, intente de nuevo");
        } while(!numeroCorrecto);
        return numeroUsuario;
    }

    public static void hacerDeposito(Scanner teclado) {
        System.out.println("Qué cantidad desea ingresar? Introduzca únicamente el valor numérico del depósito en euros.");
        dineroAIngresar = teclado.nextDouble();

        dineroEnCuenta += dineroAIngresar;
        System.out.println("Su nuevo saldo es de " + dineroEnCuenta + "€");

        depositoRealizado = true;
    }

    public static void hacerRetiro(Scanner teclado) {
        System.out.println("Qué cantidad desea retirar? Introduzca únicamente el valor numérico del depósito en euros.");
        dineroARetirar = teclado.nextDouble();

        if (dineroARetirar <= dineroEnCuenta) {
            dineroEnCuenta -= dineroARetirar;
            System.out.println("Transacción exitosa. Su nuevo saldo es de " + dineroEnCuenta + "€");
        }
        else System.out.println("Fondos insuficientes");

        retiroRealizado = true;
    }

    public static void hacerTransferencia(Scanner teclado) {
        System.out.println("Introduzca el número de la cuenta destino:");
        cuentaDestinoTransferencia = teclado.nextInt();
        System.out.println("Qué cantidad desea transferir? Introduzca únicamente el valor numérico del depósito en euros.");
        dineroATransferir = teclado.nextDouble();

        if (dineroATransferir <= dineroEnCuenta) {
            dineroEnCuenta -= dineroATransferir;
            System.out.println("Transacción exitosa. Su nuevo saldo es de " + dineroEnCuenta + "€");
        }
        else System.out.println("Fondos insuficientes");

        transferenciaReaizada = true;
    }

    public static void solicitarPrestamo(Scanner teclado) {
        System.out.println("Introduzca su número de cuenta:");
        teclado.next();

        System.out.println("Introduzca el capital por el cual desea al préstamo:");
        capitalPrestamo = teclado.nextInt();

        System.out.println("Introduzca el número de años en los cuales se deberá devolver el préstamo:");
        anosPrestamo = teclado.nextInt();
        int mesesPrestamo = anosPrestamo*12;

        System.out.println("Introduzca interés anual al que quiere pagar este préstamo (introduzca el valor como porcentaje) :");
        interesAnual = teclado.nextDouble()/100;
        double interesMensual = interesAnual/12;

        SolicitudPrestamo.generarTablaAmortizacion(capitalPrestamo, interesMensual, mesesPrestamo);
        System.out.println("El capital de tu préstamo se ha introducido a tu cuenta bancaria.");
        dineroEnCuenta += capitalPrestamo;

        prestamoRealizado = true;
    }

}