package com.company;

public class Pruebas {
    public static void main(String[] args) {
        Cliente[] lista = new Cliente[10];

        Cliente cliente1 = new Cliente("Juan", "Hernandez", "@", "3232", "20/11/2003");
        Cliente cliente2 = new Cliente("Diego", "Derch", "@", "3232", "20/11/2003");

        lista[0] = cliente1;
        lista[1] = cliente2;
        lista[2] = null;

        Cliente prueba = lista[3];


        if (pruebita()==null) System.out.println("todo ok");
        System.out.println(lista.length);

    }

    static Cliente pruebita(){
        Cliente cliente = null;
        return cliente;
    }

}
