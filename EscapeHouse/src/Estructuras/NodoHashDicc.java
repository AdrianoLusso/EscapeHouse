/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estructuras;

/**
 *
 * @author Adriano Lusso
 */
public class NodoHashDicc {
    
    
    /*
    Esta clase representa a los nodos usados para un diccionario con implementacion
    de hash abierto.
    */
    
    private Object clave;
    private Object dato;
    private NodoHashDicc enlace;
    
    //Constructor

    public NodoHashDicc(Object clave, Object dato, NodoHashDicc enlace) {
        this.clave = clave;
        this.dato = dato;
        this.enlace = enlace;
    }
    
    //Observadores

    public Object getClave()
    {
        return clave;
    }

    public Object getDato()
    {
        return dato;
    }

    public NodoHashDicc getEnlace()
    {
        return enlace;
    }
    
    //Modificadores
 
    public void setClave(Object clave) 
    {
        this.clave = clave;
    }

    public void setDato(Object dato) 
    {
        this.dato = dato;
    }

    public void setEnlace(NodoHashDicc enlace) 
    {
        this.enlace = enlace;
    }
    
    
    
}
