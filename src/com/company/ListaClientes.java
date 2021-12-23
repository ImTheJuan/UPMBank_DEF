package com.company;

public class ListaClientes {
    private Cliente[] lista;
    public final int MAXIMO_CLIENTES;
    private int numeroClientes;

    public ListaClientes(int maximo) {
        MAXIMO_CLIENTES = maximo;
        lista = new Cliente[MAXIMO_CLIENTES];
        numeroClientes = 0;
    }

    public void insertarCliente(Cliente cliente){
        if(numeroClientes<MAXIMO_CLIENTES) {
            lista[numeroClientes] = cliente;
            numeroClientes++;
        }
        else System.out.println("La aplicación ha llegado al límite de usuarios que puede almacenar");
    }

    public Cliente[] getLista() {
        return lista;
    }

    public int getNumeroClientes() {
        return numeroClientes;
    }
}
