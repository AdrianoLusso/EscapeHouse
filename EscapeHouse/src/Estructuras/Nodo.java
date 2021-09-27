/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

/**
 *
 * @Author Adriano Lusso
 */
 class Nodo {
    
    
    /*
    Esta clase representa un nodo,el cual sera utilizado para la implementacion 
    dinamica de pila.Sus atributos son:
    elemento :de tipo Object, almacena el elemento que contiene el nodo.
    enlace :de tipo Nodo, almacena el nodo previo con el cual este esta enlazado.
    */
    
    private Object elemento;
    private Nodo enlace;
    
    //Constructor
    
    public Nodo (Object elemento, Nodo enlace)
    {
        this.elemento = elemento;
        this.enlace = enlace;    
    }
    
    //modificadores
    
    public void setElemento(Object elemento)
    {
        this.elemento = elemento;
    }
    
    public void setEnlace(Nodo enlace)
    {
        this.enlace = enlace;
    }
    
    //observadoras
    
    public Object getElemento()
    {
        return this.elemento;
    }
    
    public Nodo getEnlace()
    {
        return this.enlace;
    }
}
