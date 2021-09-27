/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EscapeHouse;

import java.util.Objects;

/**
 *
 * @author 54299
 */
 public class Equipo {
    
    
    /*
    Esta clase representa los equipos que participaran en el juego.
    */
    
    private String nombre;
    private int puntajeDeSalida;
    private int puntajeTotal;
    private int puntajeActual;
    private int claveHabtActual;
    
    //Constructor

    public Equipo(String nombre, int puntajeDeSalida, int puntajeTotal,int puntajeActual) 
    {
        this.nombre = nombre;
        this.puntajeDeSalida = puntajeDeSalida;
        this.puntajeTotal = puntajeTotal;
        this.puntajeActual = puntajeActual;
        this.claveHabtActual = -1;   
    }
    
    //Modificadores

    public void setPuntajeDeSalida(int puntajeDeSalida) {
        this.puntajeDeSalida = puntajeDeSalida;
    }

    public void setPuntajeTotal(int puntajeTotal) {
        this.puntajeTotal = puntajeTotal;
    }

    public void setPuntajeActual(int puntajeActual) {
        this.puntajeActual = puntajeActual;
    }

    public void setClaveHabtActual(int claveHabtActual) {
        this.claveHabtActual = claveHabtActual;
    }
    
    //Observadores

    public String getNombre() {
        return nombre;
    }

    public int getPuntajeDeSalida() {
        return puntajeDeSalida;
    }

    public int getPuntajeTotal() {
        return puntajeTotal;
    }

    public int getPuntajeActual() {
        return puntajeActual;
    }

    public int getClaveHabtActual() {
        return claveHabtActual;
    }
      
    //Propios del tipo

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.nombre);
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
        final Equipo other = (Equipo) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Equipo{" + "nombre=" + nombre + "}";
    }
}
