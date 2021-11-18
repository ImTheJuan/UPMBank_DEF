package com.company;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class UPMBank {

    /*
    EL PROYECTO TENDRÁ LAS SIGUIENTES CLASES ADEMÁS DE LA MAIN:
    -MENUS (METODOS ESTATICOS CON FUNCIONES BASICAS DE LA APP)
    -CLIENTE (UTILIZARÁ LAS CLASES FECHA Y DNI A SU VEZ)
    -CUENTAS (INTERCONECTAA CON CLIENTES)(UTILIZARÁ TAMBIÉN CLASES INGRESO, RETIRADA, PRESTAMO, TRANSFERENCIA)
    */

    static int menuCuentas, cuentaDestinoTransferencia=0, anosPrestamo = 0;
    static double dineroInicial = 0.0, dineroAIngresar=0.0, dineroARetirar=0.0, dineroATransferir, dineroEnCuenta = 0.0;
    static boolean usuarioCreado = false, cuentaCreada = false, depositoRealizado = false, retiroRealizado = false, transferenciaReaizada = false, prestamoRealizado = false;
    static double capitalPrestamo = 0.0, interesAnual = 0;
    static boolean entradaIncorrecta = true;


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

    public static Cliente darDeAlta(Scanner teclado, Cliente[] listaClientes) {
        String nombre, apellidos, correo, fechaNacimiento, dniCompleto;

        //Recoger datos del usuario
        teclado.nextLine();
        System.out.println("Introduce tu nombre: ");
        nombre = teclado.nextLine();

        System.out.println("Introduce tus apellidos: ");
        apellidos = teclado.nextLine();
        System.out.println("Hola " + nombre + " " + apellidos);

        System.out.println("Introduce tu correo:");
        correo = Cliente.comprobarCorreo(teclado, listaClientes);

        System.out.println("Introduce tu fecha de nacimiento: formato (dd/mm/aaaa)");
        fechaNacimiento = Cliente.comprobarFecha(teclado);

        dniCompleto = Cliente.comprobarDni(teclado);


        //Creación del cliente
        Cliente cliente = new Cliente(nombre, apellidos, correo, dniCompleto, fechaNacimiento);

        System.out.println("Perfecto, ya estás registrado en UPMBank.");

        return cliente;
    }

    public static Cuenta crearCuentaBancaria(Scanner teclado, Cliente[] listaClientes) {

        String tipoDeCuenta="", codigoSucursal, codigoEntidad, digitosCuentaString, numeroDeCuenta="";
        long digitosCuenta;
        int digitoControl1, digitoControl2;

        //Se genera el número de cuenta y se recoge el tipo deseado
        System.out.println("Seleccione el tipo de cuenta que desea crear:");
        System.out.println("1. Corriente");
        System.out.println("2. Ahorro");
        System.out.println("3. Remunerada");
        tipoDeCuenta = Cuenta.comprobarTipoDeCuenta(teclado);

        codigoEntidad = "9010";
        codigoSucursal = "0201";
        digitosCuenta =  (long)(Math.random() * 10000000000L);
        digitosCuentaString = Long.toString(digitosCuenta);

        digitoControl1 = Cuenta.generarDigitoControl1(codigoEntidad, codigoSucursal);
        digitoControl2 = Cuenta.generarDigitoControl2(digitosCuentaString);

        numeroDeCuenta = codigoEntidad + codigoSucursal + digitoControl1 + digitoControl2 + digitosCuentaString;

        //Se muestra por pantalla y se crea la cuenta.
        Cuenta cuenta = new Cuenta(numeroDeCuenta, tipoDeCuenta);

        System.out.println("Tu cuenta se ha creado correctamente:");
        System.out.println("Tipo de cuenta: " + cuenta.getTipoDeCuenta());
        System.out.println("Numero de cuenta: " + cuenta.getNumero());
        System.out.println("Saldo en la cuenta: " + cuenta.getSaldo() + "€");

        return cuenta;
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

    public static void mostrarDatos(Cliente cliente) {

        if (cliente != null) {
            System.out.println("Hola " + cliente.getNombre() + " " + cliente.getApellidos());
            System.out.println("Estos son los datos de tu cuenta: \n");
            System.out.println("DNI: " + cliente.getDni());
            System.out.println("Correo electrónico: " + cliente.getCorreo());
            System.out.println("Fecha de Nacimiento: " + cliente.getFechaNacimiento());
            System.out.println();

            if (cliente.getListaCuentas()[0] != null) {
                System.out.println("Estas son las cuentas que tiene creadas en su cuenta: \n");
                for(int i = 0; i < cliente.getNumeroDeCuentas(); i++) {
                    int numeroMenu = i+1;
                    System.out.println(numeroMenu + ". Cuenta Nº : " + cliente.getListaCuentas()[i].getNumero());
                    System.out.println("Tipo: " + cliente.getListaCuentas()[i].getTipoDeCuenta());
                    System.out.println("Saldo actual: " + cliente.getListaCuentas()[i].getSaldo() + "€");
                }

                System.out.println("Seleccione una cuenta para ver sus transacciones con ella. Si no, pulse 0 para salir.");
                menuCuentas =1;
                if (menuCuentas == 1) {
                    if (depositoRealizado) System.out.println("Depósito de " + dineroAIngresar + "€");
                    if (retiroRealizado) System.out.println("Retiro de " + dineroARetirar + "€");
                    if (transferenciaReaizada) System.out.println("Transferencia de " + dineroAIngresar + "€ a la cuenta " + cuentaDestinoTransferencia);
                    if (prestamoRealizado) System.out.println("Préstamo activo de " + capitalPrestamo + "€ a " + interesAnual*100 + "% de interés anual por " + anosPrestamo + " años.");
                }
            } else System.out.println("No existe ninguna cuenta bancaria para este usuario. Si desea crear una, seleccione la opción 2 del menú principal.");
        } else System.out.println("No existe ningún usuario registrado en el sistema con esas credenciales. Por favor, asegúrese de estar dado de alta en UPMBank.");

    }

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
                        Cliente cliente = darDeAlta(teclado, listaClientes);
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
                        Cuenta cuenta = crearCuentaBancaria(teclado, listaClientes);

                        //Asignas la cuenta al usuario deseado y aumentas el número de cuentas que ese usuario tiene.
                        cuenta.asignarAUsuario(cliente, cuenta);
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
                    mostrarDatos(cliente);
                    finalizarOpcion(teclado);
                    break;

                default:
                    System.out.println("Opción incorrecta. Intente de nuevo");
            }

        } while (entradaIncorrecta);
    }
}