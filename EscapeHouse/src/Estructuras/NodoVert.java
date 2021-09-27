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
public class NodoVert {
    
    /*
    Esta clase representa a los nodos vertices utilizados en la implementacion 
    dinamica de grafo.
    */
    
    private Object elem;
    private NodoVert sigVert;
    private NodoAdy primerAdy;
    
    //Constructor

    public NodoVert(Object elem,NodoVert sigVert) {
        this.elem = elem;
        this.sigVert = sigVert;
        primerAdy = null;
    }
    
    public NodoVert(Object elem) {
        this.elem = elem;
        sigVert = null;
        primerAdy = null;
    }
       
    //Modificadores

    public void setElem(Object elem) {
        this.elem = elem;
    }

    public void setSigVert(NodoVert sigVert) {
        this.sigVert = sigVert;
    }

    public void setPrimerAdy(NodoAdy primerAdy) {
        this.primerAdy = primerAdy;
    }
    
    //Observadores

    public Object getElem() {
        return elem;
    }

    public NodoVert getSigVert() {
        return sigVert;
    }

    public NodoAdy getPrimerAdy() {
        return primerAdy;
    }

}
