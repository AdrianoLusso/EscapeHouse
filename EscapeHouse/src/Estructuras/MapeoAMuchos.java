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

public class MapeoAMuchos {
    
    /*
    Esta clase implementa un mapeo de uno a muchos.
    */
    
    private static final int TAMANIO = 70;
    private final NodoHashMapeo[] tabla;
    private int cant;

    //Constructor
    public MapeoAMuchos ()
    {
        tabla = new NodoHashMapeo[TAMANIO];
        cant = 0;
    }
    
    //Modificadores
    
    public boolean asociar (Object valorDominio, Object valorRango)
    {
         /*
        Este metodo busca a valorDominio en la tabla,y si no existe, se lo inserta
        como conjunto uno a muchos con valorRango.Retorna un boolean dependiendo
        de si fue posible la insercion.
        */
        
        int posc = Math.abs(valorDominio.hashCode() % TAMANIO);
        NodoHashMapeo previo = null,actual = this.tabla[posc];
        boolean exito = true;
       
        if(actual == null)
        {
            //Si la casilla esta vacia,se agrega el primer conjunto mapeo.
            tabla[posc] = new NodoHashMapeo(valorDominio,valorRango,null);
            cant++;
        }
        else
        {
            //Si no esta vacia,se recorre la lista.
            while(actual != null && !actual.getDominio().equals(valorDominio))
            {
               previo = actual;
               actual = actual.getEnlace();
            }
            
            if(actual == null)
            {
                previo.setEnlace(new NodoHashMapeo(valorDominio,valorRango,null));
                cant++;
            }
            else if(actual.getRango().localizar(valorRango) == -1)
            {
                actual.insertarRango(valorRango);
                cant++;
            }
            else
            {
                exito = false;
            }
        }      
        return exito;
    }
    
    public boolean desasociar(Object valorDominio,Object valorRango)
    {
        /*
        Este metodo busca a valorDominio en la lista.Si existe,y contiene a
        valorRango en su imagen,se los desasocia.Retorna un boolean dependiendo
        de si se elimino con exito.
        */
        boolean exito = false;
        int posc = valorDominio.hashCode() % TAMANIO;
        NodoHashMapeo previo = null,actual = this.tabla[posc];
        
        if(actual != null)
        {
            //Si la casilla no esta vacia,se busca el valorDominio en la lista.  
                
            while(actual != null && !actual.getDominio().equals(valorDominio))
            {
                previo = actual;
                actual = actual.getEnlace();
            }
            
            if(actual != null && actual.getRango().localizar(valorRango) > 0)
            {
                cant--;
                if(actual.getRango().longitud() == 1)
                {
                    previo.setEnlace(actual.getEnlace());
                }
                else
                {
                    actual.eliminarRango(valorRango);
                }
            }
        }
        
        return exito;
    }

    //Observadores
    
    public boolean esVacio()
    {
        return cant == 0;
    }
    
    public Lista obtenerValores(Object valorDominio)
    {
        /*
        Este metodo retorna una lista con los valores mapeados a valorDominio.
        Si no existe valorDominio,se retorna la lista vacia.
        */
         
        int posc = Math.abs(valorDominio.hashCode() % TAMANIO);
        NodoHashMapeo actual = tabla[posc];
        Lista resultado;
        
        while(actual != null && !actual.getDominio().equals(valorDominio))
        {
            actual = actual.getEnlace();
        }
        
        if(actual != null)
        {
            resultado = actual.getRango();
        }
        else
        {
            resultado = new Lista();
        }
        
        return resultado;
    }
    
    public Lista obtenerConjuntoDominio()
    {
        /*
        Este metodo retorna una lista con todos los elementos del dominio.
        */
        
        Lista resultado = new Lista();
        NodoHashMapeo actual;
        int poscTabla, ultimaPosc = 1;
      
        for(poscTabla = 0;poscTabla < TAMANIO;poscTabla++)
        {
           actual = tabla[poscTabla];
          
           while(actual != null)
           {
            resultado.insertar(actual.getDominio(),ultimaPosc++);
            actual = actual.getEnlace();
           }
        } 
         
         return resultado; 
    }
    
    public Lista listarDatos()
    {
        /*
        Este metodo retorna una lista con todos los elementos de la imagen.
        */
        
        Lista rangoActual,resultado = new Lista();
        NodoHashMapeo actual;
        int poscRango,poscTabla, ultimaPosc = 1;
      
        for(poscTabla = 0;poscTabla < TAMANIO;poscTabla++)
        {
           actual = tabla[poscTabla];
          
           while(actual != null)
           {
               rangoActual = actual.getRango();
               for(poscRango = 0;poscRango < rangoActual.longitud();poscRango++)
               {
                   resultado.insertar(rangoActual.recuperar(poscRango),ultimaPosc++);
               }
           
               actual = actual.getEnlace();
           }
        } 
         
         return resultado;
    }
    
    //Propios del tipo
    
    @Override
   public String toString()
   {
       
       String resultado = "";
       int posc;
       NodoHashMapeo actual;     
       
       if(cant > 0)
       {
         for(posc = 0;posc < tabla.length;posc++)
         {
            resultado += posc + ":  ";
            actual = tabla[posc];
            
            while(actual != null)
            {
                resultado += "(" + actual.getDominio() + ", " + 
                        actual.getRango().toString() + "); ";
                actual = actual.getEnlace();
            }
            
            resultado = resultado.substring(0, resultado.length() - 2) + "\n";
            
        }  
       }
       
       
       return resultado;
   }
}
