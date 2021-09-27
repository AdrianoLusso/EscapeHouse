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
public class Cola {
    
    /*
    Esta clase representa una cola implementada de forma dinamica,con atributos de:
    
    frente : de tipo Nodo,representa el nodo actual que es el frente de la cola.
    fin : de tipo Nodo,representa el nodo actual que es el fin de la cola.   
    */
    
    private Nodo frente;
    private Nodo fin;
    
    //Constructor
    
    public Cola()
    {
        frente = null;
        fin = null;
    }
    
    //Modificadores
    
    public boolean poner(Object elemento)
    {
        Nodo nuevo = new Nodo(elemento,null);
        
        if(frente == null)
        {
            frente = nuevo;
            fin = nuevo;
        }
        else
        {
            fin.setEnlace(nuevo);
            fin = nuevo;
        }
        
        return true;
    }
    
    public boolean sacar()
    {
        
        boolean exito;
        
        if(frente == null)
        {
            exito = false;
        }
        else
        {
            frente = frente.getEnlace();
            
            if(frente == null)
            {
                fin = null;
            }
            
            exito = true;
        }
        
        return exito;
    }
    
    //Observadores
    
    public Object obtenerFrente()
    {
        Object resultado;
        
        if(frente == null)
        {
            resultado = null;
        }
        else
        {
            resultado = frente.getElemento();
        }
        return resultado;
    }
    
    public boolean esVacia()
    {
        return frente == null;
    }
    
    //propios del tipo
    
    public void vaciar()
    {
        frente = null;
        fin = null;
    }
    
    public Cola clone()
    {
        
        Cola clon = new Cola();
        
        if(frente !=  null)
        {        
            clone(frente,clon);
        }
        
        return clon;
    }
    
    public Cola clone(Nodo nodoAuxiliar, Cola clon)
    {        
        
        clon.poner(nodoAuxiliar.getElemento());
        
        if(nodoAuxiliar.getEnlace() != null)
        {
            clon = clone(nodoAuxiliar.getEnlace(),clon);
        }        
        
        return clon;
    }
    
    @Override
    public String toString()
    {
        String resultado = "";
        Nodo auxiliar = frente;
        
        while(auxiliar != null)
        {
            resultado += auxiliar.getElemento() + "   ";
            auxiliar = auxiliar.getEnlace();
        }
        
        return resultado;      
    }
    
}
