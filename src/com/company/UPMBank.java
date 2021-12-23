package com.company;

import java.util.Scanner;

public class UPMBank {

    /*
    Lee del fichero
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("ejemplo.txt");
        BufferedReader br = new BufferedReader(fr);
        String texto = br.readLine();
        br.close();
        fr.close();
        (utilizar String.indexOf() y String.substring())
        (mas facil String.split(separador) funcion que devuelve un array con las distintas divisiones)
     */

    /*
    Escribe en fichero
    public static void main(String[] args) throws IOException {
        FileWriter fw = new FileWriter("ejemplo.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        bw.¿writeLine?("texto");
        bw.flush();
        bw.close();
        fw.close();
        (utilizar String.indexOf() y String.substring())

         String ruta = new String("Ficheros\\");
         File fichtexto = new File(ruta, "Personas.csv");
         FileOutputStream flujoSalida = new FileOutputStream(fichtexto);
         Printwriter fSalida = new PrintWriter(flujoSalida);
         fSalida.print("");
         fSalida.flush();
         fSalida.close();

     */
    static int anosPrestamo = 0;
    static double capitalPrestamo = 0.0, interesAnual = 0, dineroEnCuenta = 0.0;

    public static void main(String[] args) {

        boolean entradaIncorrecta = true;

        Scanner teclado = new Scanner(System.in);

        //Creación de la lista de clientes
        ListaClientes listaClientes = new ListaClientes(20);

        //Creación de la lista de todas las cuentas del banco
        ListaCuentas listaCuentasGlobal = new ListaCuentas(400);

        //Creacion de la lista de todas las transferencias del banco
        ListaTransferencias listaTransferencias = new ListaTransferencias(1000);


        do {
            int numeroMenu = escribirMenu(teclado);
            switch (numeroMenu) {

                case 0:
                    entradaIncorrecta = false;
                    break;

                case 1:
                    //Se crea el ciente y se introduce en la lista de clientes del banco
                    Cliente cliente = Cliente.crearCliente(teclado, listaClientes);
                    listaClientes.insertarCliente(cliente);
                    finalizarOpcion(teclado, entradaIncorrecta);
                    break;

                case 2:
                    //Se pide el DNI del usuario y se trae al cliente con dicho DNI
                    System.out.println("Introduce tu DNI:");
                    String dni = teclado.next();
                    cliente = Cliente.buscarPorDNI(listaClientes.getLista(), dni);

                    if (cliente != null) {
                        //Se crea la cuenta
                        Cuenta cuenta = Cuenta.crearCuenta(teclado, listaCuentasGlobal);
                        cuenta.imprimir();

                        //Se asigna la cuenta al usuario deseado y a la lista global
                        cliente.getListaCuentas().insertarCuenta(cuenta);
                        listaCuentasGlobal.insertarCuenta(cuenta);
                    } else System.out.println("No existe ningún usuario registrado con ese DNI.");

                    finalizarOpcion(teclado, entradaIncorrecta);
                    break;

                case 3:
                    Movimiento.crearMovimiento(teclado, "DEPOSITO", listaClientes);
                    finalizarOpcion(teclado, entradaIncorrecta);
                    break;

                case 4:
                    Movimiento.crearMovimiento(teclado, "RETIRO", listaClientes);
                    finalizarOpcion(teclado, entradaIncorrecta);
                    break;

                case 5:
                    //Se pide el DNI del usuario y se trae al cliente con dicho DNI
                    System.out.println("Introduce tu DNI:");
                    dni = teclado.next();
                    cliente = Cliente.buscarPorDNI(listaClientes.getLista(), dni);

                    if (cliente != null) {
                        if (cliente.getListaCuentas().getLista()[0] != null) {
                            //Se muestran las cuentas del usuario y se le pide que seleccione una (cuenta origen)
                            for (int i = 0; i < cliente.getListaCuentas().getNumeroCuentas(); i++) {
                                int numeroOrden = i + 1;
                                System.out.print(numeroOrden + ". ");
                                cliente.getListaCuentas().getLista()[i].imprimir();
                            }
                            System.out.println("Seleccione la cuenta con la que desea reallizar la transferencia (1, 2, 3 ...):");
                            int opcion = teclado.nextInt();
                            Cuenta cuentaOrigen = cliente.getListaCuentas().getLista()[opcion - 1];

                            //Se pide el numero de la cuenta destino
                            System.out.println("Introduzca el número de la cuenta destino:");
                            String numeroCuentaDestino = teclado.next();
                            Cuenta cuentaDestino = Cuenta.buscarPorNumero(listaCuentasGlobal.getLista(), numeroCuentaDestino);

                            //Se pide el valor a transferir
                            System.out.println("Qué cantidad desea transferir? Introduzca únicamente el valor numérico del depósito en euros.");
                            double dineroATransferir = teclado.nextDouble();

                            //Se crea la transferencia
                            Transferencia transferencia = Transferencia.hacerTransferencia(cuentaOrigen, cuentaDestino, dineroATransferir);

                            //Se introduce en la lista de transferencias del banco y a la lista de la cuenta origen
                            listaTransferencias.insertarTransferencia(transferencia);
                            cuentaOrigen.getListaTransferencias().insertarTransferencia(transferencia);

                        } else System.out.println("No tiene cuentas con las cuales operar. Por favor cree alguna en el menú principal.");
                    } else System.out.println("No existe ningún usuario registrado con ese DNI.");

                    finalizarOpcion(teclado, entradaIncorrecta);
                    break;

                case 6:
                    solicitarPrestamo(teclado);
                    finalizarOpcion(teclado, entradaIncorrecta);
                    break;

                case 7:
                    //Solicita el DNI del usuario y busca el cliente identificado con ese DNI.
                    System.out.println("Introduce tu DNI:");
                    dni = teclado.next();
                    cliente = Cliente.buscarPorDNI(listaClientes.getLista(), dni);

                   //Comprueba si el usuario existe y lo muestra en caso afirmativo.
                    if (cliente != null) cliente.imprimir();
                    else System.out.println("No existe ningún usuario registrado en el sistema con esas credenciales. " +
                            "Por favor, asegúrese de estar dado de alta en UPMBank.");

                    finalizarOpcion(teclado, entradaIncorrecta);
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

    //Función que pregunta al usuario si desea repetir la ejecución del programa o cerrar la app.
    public static void finalizarOpcion(Scanner teclado, boolean entradaIncorrecta){

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
    }
}