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
public class NodoHashMapeo {
    
    
    /*
    Esta clase representa a los nodos usados para un mapeo con implementacion de
    hash abierto.
    */
    
    private Object dominio;
    private Lista rango;
    private NodoHashMapeo enlace;
    
    //Constructor

    public NodoHashMapeo(Object dominio, Object elemRango, NodoHashMapeo enlace) {
        this.dominio = dominio;
        rango = new Lista();
        rango.insertar(elemRango, 1);
        this.enlace = enlace;
    }
    
    //Observadores

    public Object getDominio() 
    {
        return dominio;
    }

    public Lista getRango() 
    {
        return rango;
    }

    public NodoHashMapeo getEnlace()
    {
        return enlace;
    }
    
    //Modificadores

    public void setEnlace(NodoHashMapeo enlace) 
    {
        this.enlace = enlace;
    }
    
    public void insertarRango(Object rango)
    {
        this.rango.insertar(rango,this.rango.longitud());
    }   
    
    public void eliminarRango(Object rango)
    {
        this.rango.eliminar(this.rango.localizar(rango));
    }
}
