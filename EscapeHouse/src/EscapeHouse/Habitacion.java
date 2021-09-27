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
public class Habitacion {
    
    /*
    Esta clase representa las habitaciones disponibles en el juego.
    */
    
    private int codigo;
    private String nombre;
    private int planta;
    private int m2;
    private boolean tieneSalida;
    private int cantEquipos;
    
    //Constructor

    public Habitacion(int codigo, String nombre, int planta, int m2, boolean tieneSalida) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.planta = planta;
        this.m2 = m2;
        this.tieneSalida = tieneSalida;
        cantEquipos = 0;
    }
    
    //Modificadores
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPlanta(int planta) {
        this.planta = planta;
    }

    public void setM2(int m2) {
        this.m2 = m2;
    }

    public void setTieneSalida(boolean tieneSalida) {
        this.tieneSalida = tieneSalida;
    }

    public void sumarEquipo()
    {
        ++cantEquipos;
    }
    
    
    public void restarEquipo()
    {
        --cantEquipos;
    }
    //Observadores

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPlanta() {
        return planta;
    }

    public int getM2() {
        return m2;
    }

    public boolean getTieneSalida() {
        return tieneSalida;
    }

    public int getCantEquipos() {
        return cantEquipos;
    }
    
    //Propios del tipo

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.codigo;
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
        final Habitacion other = (Habitacion) obj;
        if (this.codigo != other.codigo) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString()
    {
        return "Habitacion{" + "codigo= " + codigo + "}";
    }
    
    
}
