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
public class TablaDeBusqueda {
    
    /*
    Esta clase representa a los diccionarios implementados con hash abierto.
    */
    
    private final int TAMANIO = 70;
    private final NodoHashDicc[] tabla;
    private int cant;
    
    //Constructor
    
    public TablaDeBusqueda()
    {
        cant = 0;
        tabla = new NodoHashDicc[TAMANIO];
    }
    
    //Modificadores
    
    public boolean insertar(Object clave,Object dato)
    {
        /*
        Este metodo busca la clave en la tabla,y si no existe, se inserta el par.
        */
        
        int posc = Math.abs(clave.hashCode() % TAMANIO);
        NodoHashDicc previo = null,actual = this.tabla[posc];
        boolean exito = true;
       
        if(actual == null)
        {
            //Si la casilla esta vacia,se agrega el primer par.
            tabla[posc] = new NodoHashDicc(clave,dato,null);
            cant++;
        }
        else
        {
            //Si no esta vacia,se recorre la lista.
            do
            {
                if(actual.getClave().equals(clave))
                {
                    //Si se encuentra la clave,falla la insercion.
                    exito = false;
                }
                else
                {
                    //Si no, se sigue buscando en la lista.
                    previo = actual;
                    actual = actual.getEnlace();
                }
            }while(exito && actual != null);
            
            if(exito)
            {
                //Si no se encontro la clave en la tabla,se agrega el par al final de la lista.
                previo.setEnlace(new NodoHashDicc(clave,dato,null));
                cant++;
            }
        }
        
        return exito;
    }
    
    public boolean eliminar(Object clave)
    {
        /*
        Este metodo busca la clave en la lista,y si existe en esta,elimina el 
        respectivo par.
        */
        boolean exito = false;
        int posc = Math.abs(clave.hashCode() % TAMANIO);
        NodoHashDicc previo = null,actual = this.tabla[posc];
        
        if(actual != null)
        {
            //Si la casilla no esta vacia,se busca la clave en la lista.
            do
            {
                if(actual.getClave().equals(clave))
                {
                    //Si se encuentra la clave,la eliminacion tiene exito.
                    exito = true;
                    cant--;
                }
                else
                {
                    //Si no, se sigue buscando en la lista.
                    previo = actual;
                    actual = actual.getEnlace();
                }
            }while(!exito && actual != null);
        
        
            if(exito)
            {
               //Si se encontrola clave,se elimina su par.
                if (previo == null) {
                    //El par es el primero de la lista.
                    tabla[posc] = actual.getEnlace();
                } 
                else
                {
                    //El par no es el primero ni ultimo de la lista.
                    previo.setEnlace(actual.getEnlace());
                }
            }
        }
        
        return exito;
    }
    
    //Observadores
    
    public boolean existeClave(Object clave)
    {
        /*
        Este metodo retorna un boolean dependiendo de si existe un par con la clave
        ingresada.
        */
        
        boolean exito = false;
        int posc = clave.hashCode() % TAMANIO;
        NodoHashDicc actual = this.tabla[posc];
        
        if(actual != null)
        {
            //Si la casilla no esta vacia,se busca la clave en la lista.
            do
            {
                if(actual.getClave().equals(clave))
                {
                    //Si se encuentra la clave,la eliminacion tiene exito.
                    exito = true;
                }
                else
                {
                    //Si no, se sigue buscando en la lista.
                    actual = actual.getEnlace();
                }
            }while(!exito && actual != null);                          
        }
        
        return exito;
    }
    
     public Object obteneInformacion(Object clave)
    {
        /*
        Este metodo retorna el rango de la asociacion con dominio valorDominio.
        Precondición: valorDominio está en el mapeo (si no existe,
        no se puede asegurar el funcionamiento de la operación
        */
        
        int posc = Math.abs(clave.hashCode() % TAMANIO);
        NodoHashDicc actual = this.tabla[posc];
        Object datoRes = null;
        
        if(actual != null)
        {
            //Si la casilla no esta vacia,se busca el valorDominio.
            do
            {
                if(actual.getClave().equals(clave))
                {
                    //Si se encuentra el valor dominio,se retornara su rango.
                    datoRes = actual.getDato();
                }
                else
                {
                    //Si no,se sigue buscando hasta encontrarlo,o terminar la lista.
                    actual = actual.getEnlace();
                }                        
            }while(actual != null && datoRes == null);
        }
        
        return datoRes;  
    }
     
    public boolean esVacio()
    {
        return cant == 0;
    }
     
    //Propios del tipo 
     
    public Lista listarClaves()
    {
        /*
        Este metodo retorna una lista ordenada con las claves de los pares 
        almacenados en la tabla.
        */
        
        Lista resultado = new Lista();
        NodoHashDicc actual;
        int poscTabla, ultimaPosc = 1;
      
        for(poscTabla = 0;poscTabla < TAMANIO;poscTabla++)
        {
           actual = tabla[poscTabla];
          
           while(actual != null)
           {
            resultado.insertar(actual.getClave(),ultimaPosc++);
            actual = actual.getEnlace();
           }
        } 
         
         return resultado;
    }
    
    public Lista listarDatos()
    {
        /*
        Este metodo retorna una lista ordenada con los datos de los pares 
        almacenados en la tabla.
        */
        
        Lista resultado = new Lista();
        NodoHashDicc actual;
        int poscTabla, ultimaPosc = 1;
      
        for(poscTabla = 0;poscTabla < TAMANIO;poscTabla++)
        {
           actual = tabla[poscTabla];
          
           while(actual != null)
           {
            resultado.insertar(actual.getDato(),ultimaPosc++);
            actual = actual.getEnlace();
           }
        } 
         
         return resultado;
    }
    

    @Override
    public String toString()
    {
        
       String resultado = "";
       int posc;
       NodoHashDicc actual;
       
       if(cant > 0)
       {
           for(posc = 0;posc < tabla.length;posc++)
            {
                resultado += posc + ":  ";
                actual = tabla[posc];
            
                while(actual != null)
                {
                    resultado +=  actual.getClave() + "; ";
                    actual = actual.getEnlace();
                }
                resultado = resultado.substring(0, resultado.length()-2) + "\n";
            }
       }
             
       return resultado;
    }
}
