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
public class NodoAdy {
    
    /*
    Esta clase representa a los nodos adyascentes creados para ser almacenados
    como un atributo de los nodos vertices,en la implementacion dinamica de grafo.
    */
    
    private NodoVert vertice;
    private NodoAdy sigAdy;
    private int etiqueta;
    
    //Constructor

    public NodoAdy(NodoVert vertice, NodoAdy sigAdy,int etiqueta) {
        this.vertice = vertice;
        this.sigAdy = sigAdy;
        this.etiqueta = etiqueta;
    }
    
    //Modificadores

    public void setVertice(NodoVert vertice) {
        this.vertice = vertice;
    }

    public void setSigAdy(NodoAdy sigAdy) {
        this.sigAdy = sigAdy;
    }
    public void setEtiqueta(int etiqueta){
        this.etiqueta = etiqueta;
    }
    
    //Observadores

    public NodoVert getVertice() {
        return vertice;
    }

    public NodoAdy getSigAdy() {
        return sigAdy;
    }

    public int getEtiqueta() {
        return etiqueta;
    }
}
