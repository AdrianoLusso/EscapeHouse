package Estructuras;



/**
 *
 * @author Adriano Lusso
 */

public class NodoAVLDicc{
    
    private Comparable clave;
    private Object dato;
    private NodoAVLDicc izquierdo;
    private NodoAVLDicc derecho;
    private int altura;
    
    
   //Constructor

   public NodoAVLDicc(Comparable clave,Object dato)
   {
       /*
       Este metodo crea un nodo AVL.
       */
       
       this.clave = clave;
       this.dato = dato;
       altura = 0;
       this.izquierdo = null;
       this.derecho = null;
   }
   
     
      //Observadores
    
    public Comparable getClave()
    {
        /*
        Este metodo retorna el elemento del NodoAVL.
        */
        
        return clave;
    }

    public Object getDato()
    {
        return dato;
    }
    
    
    
    public NodoAVLDicc getIzquierdo()
    {
        /*
        Este metodo retorna hijo izquierdo del NodoAVL.
        */
        
        return izquierdo;
    }
    
    public NodoAVLDicc getDerecho()
    {
        /*
        Este metodo retorna el hijo derecho del NodoAVL.
        */
        
        return derecho;
    }
    
    public int getAltura()
    {
        /*
        Este metodo retorna la altura del NodoAVL.
        */
        return altura;
    }
    
    //Modificadores

    public void setClave(Comparable clave)
    {
        this.clave = clave;
    }
    
    
    
    public void setDato(Object dato)
    {
        /*
        Este metodo modifica el atributo elemento.
        */
        
        this.dato = dato;       
    }
    
    public void setIzquierdo(NodoAVLDicc izquierdo)
    {
        /*
        Este metodo modifica el atributo izquierdo.
        */
        
        this.izquierdo = izquierdo;     
        
        
    }
    
    public void setDerecho(NodoAVLDicc derecho)
    {
        /*
        Este metodo modifica el atributo derecho.
        */
        
        this.derecho = derecho;       
    }
    
    //Propios del tipo
    
    public void recalcularAltura()
    {
        /*
        Este metodo recalcula la altura de un nodoAVL
        */
        
        int alturaIzq, alturaDer;
        
        if(this.izquierdo != null)
        {
            alturaIzq = this.izquierdo.altura;
        }
        else
        {
            alturaIzq = -1;
        }
        
        if(this.derecho != null)
        {
            alturaDer = this.derecho.altura;
        }
        else
        {
            alturaDer = -1;
        }
        
        altura = Math.max(alturaIzq, alturaDer) + 1;
        
    }
    
    

    
   
   
    
}
