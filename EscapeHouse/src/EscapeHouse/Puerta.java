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
public class Puerta {
    
    /*
    Esta clase representa a las puertas que conectaran las habitaciones del juego.
    Los nombres origen y destino no hacen referencia a la direccion de entre habitaciones.
    Sera unicamente utilizada para facilitar la operacion pasarAHabitacion().
    */
    
    int habtOrigen;
    int habtDestino;

    
    //Constructor
    public Puerta(int origen, int destino) {
        this.habtOrigen = origen;
        this.habtDestino = destino;
    }
    
    //Observadores

    public int getHabtOrigen() {
        return habtOrigen;
    }

    public int getHabtDestino() {
        return habtDestino;
    }
    
    //Propios del tipo
    
    public boolean conecta(int uno,int dos)
    {
        /*
        Este metodo retorna un boolean dependiendo de si esta puerta tiene origen
        uno y destino dos,o viceversa.
        */
        
        return (habtOrigen == uno && habtDestino == dos) || (habtOrigen == dos && habtDestino == uno) ;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.habtOrigen;
        hash = 47 * hash + this.habtDestino;
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
        final Puerta other = (Puerta) obj;
        if (this.habtOrigen != other.habtOrigen) {
            return false;
        }
        if (this.habtDestino != other.habtDestino) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Puerta{" + "habtOrigen=" + habtOrigen + ", habtDestino=" + habtDestino + '}';
    }
    
    
}
