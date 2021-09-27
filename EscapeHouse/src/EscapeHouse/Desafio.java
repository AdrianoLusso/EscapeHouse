/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EscapeHouse;

/**
 *
 * @author 54299
 */
public class Desafio {
    
    /*
    Esta clase representa los desafios que se podran resolver en el juego.
    */
    
    private int puntaje;
    private String nombre;
    /*
    Cada desafio sera definido con un char.En caos de ser necesario,puede modificarse
    el codigo para agregar mas tipos.
    M: matematico
    L:logico
    B:busqueda
    D:destreza
    cualquier otro char:indefinido.
    */
    private char tipo;
    
    //Constructor

    public Desafio(int puntaje, String nombre, char tipo)
    {
        this.puntaje = puntaje;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    
    //Modificadores

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }
    
    //Observadores

    public int getPuntaje() {
        return puntaje;
    }

    public String getNombre() {
        return nombre;
    }

    public char getTipo() {
        return tipo;
    }
    
    //Propios del tipo

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.puntaje;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Desafio other = (Desafio) obj;
        if (this.puntaje != other.puntaje) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "Desafio{" + "puntaje=" + puntaje + '}';
    }
}
