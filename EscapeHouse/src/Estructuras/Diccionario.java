package Estructuras;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Adriano Lusso
 */
public class Diccionario {
 
    /*
    Esta clase representa a los diccionarios implementados con AVL.
    */
    
    private NodoAVLDicc raiz;
    
    //Constructor

    public Diccionario() {
        raiz = null;
    }

    
    //Modificadores
    
    public boolean insertar(Comparable clave,Object dato)
    {
        /*
        Este metodo inserta un elemento en el arbol,si es que no existe previamente.
        Retorna un boolean dependiendo de si fue posible.
        */
        
        boolean exito;
        
        if(raiz != null)
        {
            exito = insertarAux(clave,dato,raiz);
            raiz.recalcularAltura();
            rotacion(null,raiz,true);
        }
        else
        {
            raiz = new NodoAVLDicc(clave,dato);
            exito = true;           
        }
        
        return exito;
    }
    
    private boolean insertarAux(Comparable clave,Object dato,NodoAVLDicc actual)
    {
        /*
        Este metodo recursivo realiza en proceso de insercion del elemento en el arbol.
        Retorna un boolean dependiendo de si fue posible.
        */        
        
        boolean exito,esHijoIzq;
                
        if(clave.compareTo(actual.getClave()) == 0)
        {
            //Si la clave es la actual,falla la incersion.
            exito = false;
        }
        else
        {
            //Sino, sigue el proceso.
            if(clave.compareTo(actual.getClave()) < 0)
            {               
                //Se continuar al subarbol izquierdo,
                if(actual.getIzquierdo() == null)
                {
                    exito = true;
                    actual.setIzquierdo(new NodoAVLDicc(clave,dato));
                }
                else
                {
                    exito = insertarAux(clave,dato,actual.getIzquierdo());
                }   
                
                esHijoIzq = true;
            }
            else
            {
                //Se continuar al subarbol derecho.
                 if(actual.getDerecho() == null)
                {
                    exito = true;
                    actual.setDerecho(new NodoAVLDicc(clave,dato));
                }
                else
                {
                    exito = insertarAux(clave,dato,actual.getDerecho());
                }
                 
               esHijoIzq = false;
            }
            
            if(exito)
                {      
                    //Si se inserto el par, se procede a evaluar posibles rotaciones.
                    
                    actual.recalcularAltura();//!!!se puede eliminar?
                    
                    if(esHijoIzq)
                    {
                        rotacion(actual,actual.getIzquierdo(),true);
                    }
                    else
                    {
                        rotacion(actual,actual.getDerecho(),false);
                    }
                    actual.recalcularAltura();
                }
           
        }
 
        return exito;
    }
    
    private void rotacion(NodoAVLDicc padre,NodoAVLDicc hijo,boolean esHijoIzq )
    {
        /*
        Este metodo decide si es necesario aplicar una rotacion.En caso de serlo,
        cual de las cuatro aplicar.
        
        padre : de tipo NodoAVLDicc.Padre del nodo cuyo balance se analizara.
        hijo : de tipo NodoAVLDicc.Nodo a analizar,y posiblemente, rotar.
        esHijOizq : de tipo boolean.Define si hijo es izquierdo,o no, de padre.
        */
              
        int balance = balance(hijo);
        NodoAVLDicc nuevaRaizSubarbol;
        
        
        if(balance < -1)
        {
            //hijo esta desbalanceado a derecha,o esta balanceado.
            if(balance(hijo.getDerecho()) <= 0)
            {
                //el hijo de nodo hijo esta desbalanceado a derecha
                nuevaRaizSubarbol = rotarIzquierda(hijo);
            }
            else
            {
                //el hijo de nodo hijo esta desbalanceado a izquierda
                nuevaRaizSubarbol = rotarDerechaIzquierda(hijo);
            }
        }
        else if(balance > 1)
        {
            //hijo esta desbalanceado a izquierda
            if(balance(hijo.getIzquierdo()) >= 0)
            {
                //el hijo de nodo hijo esta desbalanceado a izquierda,o esta balanceado.
                nuevaRaizSubarbol = rotarDerecha(hijo);
            }
            else
            {
                //el hijo de nodo hijo esta desbalanceado a derecha.
                nuevaRaizSubarbol = rotarIzquierdaDerecha(hijo);
            }
        }
        else
        {
            //hijo esta balanceado
            nuevaRaizSubarbol = null;
        }
        
        if(nuevaRaizSubarbol != null)
        {
            //si exitio rotacion,a padre de le asigna un nuevo hijo.
            if(padre != null)
            {
                //si nodo hijo no es raiz,entonces tiene padre.
                if(esHijoIzq )
                {
                    padre.setIzquierdo(nuevaRaizSubarbol);
                }
                else
                {
                    padre.setDerecho(nuevaRaizSubarbol);
                }
            }
            else
            {
                //sino,nodo hijo no tiene padre,y se lo asigna a la raiz.
                raiz = nuevaRaizSubarbol;
            }
        
         }
        
    }
    
    private NodoAVLDicc rotarIzquierda(NodoAVLDicc padre)
    {
        /*
        Este metodo realiza la operacion de rotar a la izquierda.
        */
        
        NodoAVLDicc hijo = padre.getDerecho(),temp = hijo.getIzquierdo();
        
        hijo.setIzquierdo(padre);
        padre.setDerecho(temp);
        padre.recalcularAltura();              
        hijo.recalcularAltura();
        
        return hijo;
    }
    
    private NodoAVLDicc rotarDerecha(NodoAVLDicc padre)
    {
        /*
        Este metodo realiza la operacion de rotar a la derecha.
        */
        
        NodoAVLDicc hijo = padre.getIzquierdo(),temp = hijo.getDerecho();
        
        hijo.setDerecho(padre);
        padre.setIzquierdo(temp);
        padre.recalcularAltura();
        hijo.recalcularAltura();
        return hijo;
    }
    
    private NodoAVLDicc rotarIzquierdaDerecha(NodoAVLDicc padre)
    {
        /*
        Este metodo realiza la operacion de rotacion doble izquierda-derecha.
        */
        
        padre.setIzquierdo(rotarIzquierda(padre.getIzquierdo()));
        return rotarDerecha(padre);        
    }
    
    private NodoAVLDicc rotarDerechaIzquierda(NodoAVLDicc padre)
    {
        /*
        Este metodo realiza la operacion de rotacion doble derecha-izquierda.
        */
        
        padre.setDerecho(rotarDerecha(padre.getDerecho()));
        return rotarIzquierda(padre);        
    }
    
    private int balance(NodoAVLDicc padre)
    {
        /*
        Este metodo calcula el balance de un nodo padre.
        */
        
        int altIzq,altDer;
        
        if(padre.getIzquierdo()!= null)
        {
            altIzq = padre.getIzquierdo().getAltura();
        }
        else
        {
            altIzq = -1;
        }
        
        if(padre.getDerecho()!= null)
        {
            altDer = padre.getDerecho().getAltura();
        }
        else
        {
            altDer = -1;
        }
        
       return altIzq - altDer;
    }
    
    public boolean eliminar(Comparable clave)
    {
        /*
        Este metodo elimina el par del arbol,si es que existe.
        */
        
        boolean exito;
        
        if(raiz != null)
        {
            exito = eliminarAux(clave,raiz,null,true);
            if(exito)
            {
               raiz.recalcularAltura();
               rotacion(null,raiz,true);
               raiz.recalcularAltura();
            }
        }
        else
        {
            exito = false;
        }
        
        return exito;
    }
    
    private boolean eliminarAux(Comparable clave,NodoAVLDicc actual,NodoAVLDicc padre,boolean esHijoIzq)
    {
        /*
        Este metodo busca un nodo con clave en el arbol,y decide mediante cual caso
        se lo eliminara.En caso de no existir,la operacion no tiene exito.
        
        actual : de tipo NodoAVLDicc.Nodo actual que analizamos.
        padre : de tipo NodoAVLDicc. Nodo padre de actual.
        esHijoIzq : de tipo boolean.Define si el nodo actual es hijo izquierdo.Si
        es la raiz,por defecto es valor true.
        */
        
        int comparacion;
        char hijos;
        boolean exito;
        
        if(actual != null)
        {
            comparacion = clave.compareTo(actual.getClave());
            if(comparacion == 0)
            {
               hijos = cantidadHijos(actual);
            
               switch(hijos)
               {
                    case '0':
                        caso1(padre,esHijoIzq);
                        break;
                        
                    case 'i':
                        caso2(padre,esHijoIzq,true);
                        break;
                        
                    case 'd':
                        caso2(padre,esHijoIzq,false);
                        break;
                        
                    case '2':
                        caso3(actual);
                        break;
                }
            
                exito = true;
            }
            else
            {
                if(comparacion < 0)
                {
                    exito = eliminarAux(clave,actual.getIzquierdo(),actual,true);
                }
                else
                {
                    exito = eliminarAux(clave,actual.getDerecho(),actual,false);
                }
                
                if(exito)
                {      
                    //Si se elimino el par, se procede a evaluar posibles rotaciones.                   
                    
                    if(esHijoIzq)
                    {
                        rotacion(padre,actual,true);
                    }
                    else
                    {
                        rotacion(padre,actual,false);
                    }
                    actual.recalcularAltura();//!!!Evaluar si se elimina.
                }
                
            }                     
        }
        else
        {
            exito = false;
        }
        
        return exito;     
    }
    
   private char cantidadHijos(NodoAVLDicc actual)
   {
       /*
       Este metodo retorna un char dependiendo de la cantidad de hijos que tenga
       actual,0 de si es Izquierdo o Derecho(en caso de que tenga solo 1)/
       */
       
       char resultado;
       
       if(actual.getIzquierdo() != null)
       {
           if(actual.getDerecho() != null)
           {
               resultado = '2';
           }
           else
           {
               resultado = 'i';
           }                         
       }
       else
       {
           if(actual.getDerecho() != null)
           {
               resultado = 'd';
           }
           else
           {
               resultado = '0';
           }
       }
       
       return resultado;
   }
    
   private void caso1(NodoAVLDicc padre,boolean esHijoIzq)
   {
       /*
       Este metodo elimina del arbol un nodo que es hoja.
       
       padre : de tipo NodoAVLDicc.Representa al padre del nodo a eliminar.
       esHijoIzq : de tipo boolean.Su valor depende de si el nodo a eliminar es hijo
       izquierdo de padre, o no lo es.
       */
       
       if(padre != null)
       {
           //El nodo a eliminar no es raiz.
           if(esHijoIzq)
           {
               //El nodo a eliminar es hijo izquierdo.
               padre.setIzquierdo(null);
           }
           else
           {
               //El nodo a eliminar es hijo derecho.
               padre.setDerecho(null);
           }
       }
       else
       {
           //El nodo a eliminar es la raiz.
           raiz = null;
       }       
   }
   
   private void caso2(NodoAVLDicc padre,boolean esHijoIzq,boolean esNietoIzq)
   {
       /*
       Este metodo elimina del arbol un nodo que tiene 1 hijo.
       
       padre : de tipo NodoAVLDicc.Representa al padre del nodo a eliminar.
       esHijoIzq : de tipo boolean.Su valor depende de si el nodo a eliminar es hijo
       izquierdo de padre, o no lo es.
       esNietoIzq : de tipo boolean.Su valor depende de si el hijo del nodo a eliminar
       es hijo izquierdo de este, o no lo es.
       */
       
       if(padre != null)
       {
           //El nodo a eliminar no es raiz.
           if(esHijoIzq)
           {
               //El nodo a eliminar es hijo izquierdo.
               if(esNietoIzq)
               {
                   //El hijo del nodo a eliminar es hijo izquierdo.
                   padre.setIzquierdo(padre.getIzquierdo().getIzquierdo());
               }
               else
               {
                   //El hijo del nodo a eliminar es hijo derecho.
                    padre.setIzquierdo(padre.getIzquierdo().getDerecho());
               }
           }
           else
           {
               //El nodo a eliminar es hijo izquierdo.
               if(esNietoIzq)
               {
                   //El hijo del nodo a eliminar es hijo izquierdo.
                   padre.setDerecho(padre.getDerecho().getIzquierdo());
               }
               else
               {
                   //El hijo del nodo a eliminar es hijo derecho.
                    padre.setDerecho(padre.getDerecho().getDerecho());
               }
           }
       }
       else
       {
           //El nodo a eliminar es raiz.
           if(esHijoIzq)
           {
               //El nodo a eliminar es hijo izquierdo.
               raiz = raiz.getIzquierdo();
           }
           else
           {
               raiz = raiz.getDerecho();
           }
       }      
   }

   private void caso3(NodoAVLDicc actual)
   {
       /*
       Este metodo elimina un nodo con dos hijos.
       
       actual : de tipo NodoAVLDicc.Representa el nodo a eliminar.
       */      
       NodoAVLDicc candidato = candidato(actual.getDerecho(),actual,actual);
       
       actual.setClave(candidato.getClave());
       actual.setDato(candidato.getDato());
   }
   
   private NodoAVLDicc candidato(NodoAVLDicc actual,NodoAVLDicc padre,NodoAVLDicc aEliminar)
   {
       /*
       Este metodo recursivo busca un candidato para reemplazar el valor de la raiz.
       Este metodo funciona en contexto al objetivo de caso3()
       
       actual : de tipo NodoAVLDicc.Nodo actual que se esta analizando.
       padre : de tipo NodoAVLDicc.Padre del nodo que se esta analizando.
       aEliminar :de tipo NodoAvlDicc.Nodo que se eliminara a traves del caso 3.
       */

       NodoAVLDicc candidato;
       
       if(actual.getIzquierdo() != null)
       {
           //Se encontro el candidato.
            candidato = candidato(actual.getIzquierdo(),actual,aEliminar);
           
            //Se buscaran rotaciones recursivamente.
            actual.recalcularAltura();           
            if(padre.equals(aEliminar))
            {
                //El cadidato es el hijo del nodo a eliminar.
                rotacion(padre,actual,false);
            }
            else
            {
                //El candidato no es hijo del nodo a eliminar.
                rotacion(padre,actual,true);
            }
            actual.recalcularAltura();
       }
       else
       {
           //Se eliminara al candidato del arbol.
           if(padre.equals(aEliminar))
           {
               //El cadidato es el hijo del nodo a eliminar.
               padre.setDerecho(actual.getDerecho());              
           }
           else
           {
               //El candidato no es hijo del nodo a eliminar.
               padre.setIzquierdo(actual.getDerecho());
           }
           
           candidato = actual;
       }
       
       return candidato;
   }
   
   public void vaciar()
   {
       /*
       Este metodo vacia el arbol.
       */
       
       raiz = null;
   }
   
   //Observadores
   
   public boolean existeClave(Comparable clave)
   {
       /*
       Este metodo retorna un boolean,dependiendo de si el par con clave pertenece, o no,
       al arbol.
       */
       
       boolean pertenece;
       
       if(raiz != null)
       {
           pertenece = existeClaveAux(clave,raiz);
       }
       else
       {
           pertenece = false;
       }
       
       return pertenece;
   }
   
   private boolean existeClaveAux(Comparable clave,NodoAVLDicc actual)
   {
       /*
       Este metodo recursivo evalua los nodos necesarios para ver si existe el par con clave
       en el arbol,y retorna un booleano.
       
       actual : de tipo NodoAVLDicc.Nodo actual que se esta analizando.
       */
       
       boolean exito;
       int comparacion = clave.compareTo(actual.getClave());
       
       if(comparacion == 0)
       {
           //Si se encuentra la clave,hubo exito en la busqueda.
           exito = true;
       }
       else
       {
           //Sino,se intentara seguir buscando.
           if(comparacion < 0 && actual.getIzquierdo() != null)
           {
               //Si existe subarbol izquierdo,y corresponde,se sigue buscando ahi.
               exito = existeClaveAux(clave,actual.getIzquierdo());
           }
           else if(comparacion > 0 && actual.getDerecho() != null)
           {
               //Sino,si existe subarbol derecho,y corresponde,se sigue buscando ahi.
               exito = existeClaveAux(clave,actual.getDerecho());
           }
           else
           {
               //Si no existen subarboles,la busqueda llego a su fin sin exito.
               exito = false;
           }
       }
       
       return exito;
   }
   
   public Object obtenerInformacion(Comparable clave)
   {
       /*
       Este metodo retorna la informacion mapeada a la clave ingresada por parametro.
       */
       
       Object informacion = null;
       
       if(raiz != null)
       {
          informacion = obtenerInformacionAux(clave,raiz); 
       }
       
       return informacion;
   }
   
   private Object obtenerInformacionAux(Comparable clave,NodoAVLDicc actual)
   {
       /*
       Este metodo recursivo busca en el arbol a la clave,para poder retornar su dato.
       actual : de tipo NodoAVLDicc.Representa el nodo actual que se analiza.
       */
       Object informacion;
       
       int comparacion = clave.compareTo(actual.getClave());
       
       if(comparacion == 0)
       {
           //Si se encuentra el elemento,hubo exito en la busqueda.
           informacion = actual.getDato();
       }
       else
       {
           //Sino,se intetara seguir buscando.
           if(comparacion < 0 && actual.getIzquierdo() != null)
           {
               //Si existe subarbol izquierdo,y corresponde,se sigue buscando ahi.
               informacion = obtenerInformacionAux(clave,actual.getIzquierdo());
           }
           else if(comparacion > 0 && actual.getDerecho() != null)
           {
               //Sino,si existe subarbol derecho,y corresponde,se sigue buscando ahi.
               informacion = obtenerInformacionAux(clave,actual.getDerecho());
           }
           else
           {
               //Si no existen subarboles,la busqueda llego a su fin sin exito.
               informacion = null;
           }
       }
       
       return informacion;
   }
   
   public boolean esVacio()
   {
       /*
       Este metodo retorna un boolean,dependiendo de si el arbol es vacio.
       */
       
       return raiz != null;
   }
   
   //Propios del tipo
   
   public Lista listarClaves()
   {
       /*
       Este metodo genera una lista ordenada de las claves del arbol.
       */
       
       Lista lista = new Lista();
       
       if(raiz != null)
       {
          listarClavesAux(raiz,lista,1); 
       }
       
       return lista;
   }
   
    private int listarClavesAux(NodoAVLDicc actual,Lista inorden,int posc)
   {
       //Va al subarbol izquierdo,y repite proceso.
         if(actual.getIzquierdo() != null)
        {
            posc = listarClavesAux(actual.getIzquierdo(),inorden,posc);
        }
         
        //inserta la raiz del subarbol actual.
        inorden.insertar(actual.getClave(), posc);
        posc++;
        
        //Va al subarbol derecho y repite proceso.
        if(actual.getDerecho() != null)
        {
            posc = listarClavesAux(actual.getDerecho(),inorden,posc);
        }
        
        return posc;
   }
       
    public Lista listarDatos()
   {
       /*
       Este metodo genera una lista ordenada de los datos del arbol.
       */
       
       Lista lista = new Lista();
       
       if(raiz != null)
       {
          listarDatosAux(raiz,lista,1); 
       }
       
       return lista;
   }
   
    private int listarDatosAux(NodoAVLDicc actual,Lista inorden,int posc)
    {
       //Va al subarbol izquierdo,y repite proceso.
         if(actual.getIzquierdo() != null)
        {
            posc = listarDatosAux(actual.getIzquierdo(),inorden,posc);
        }
         
        //inserta la raiz del subarbol actual.
        inorden.insertar(actual.getDato(), posc);
        posc++;
        
        //Va al subarbol derecho y repite proceso.
        if(actual.getDerecho() != null)
        {
            posc = listarDatosAux(actual.getDerecho(),inorden,posc);
        }
        
        return posc;
    }
    
    @Override
    public String toString()
    {
        /*
        Este metodo retorna un string con los elementos del arbol.
        */
        
        String resultado = "";
        
        if(raiz != null)
        {
            resultado = generaToString(raiz);
        }
        
        return resultado;
    }
    
    private String generaToString(NodoAVLDicc actual)
    {
        boolean existeIzq,existeDer;
        String resultado,izq,der;
        
        if(actual.getIzquierdo() != null)
        {
            //Si existe hijo izquierdo.
            izq = "" + actual.getIzquierdo().getClave();
            existeIzq = true;
        }
        else
        {
            //Sino existe hijo izquierdo.
            izq = "-";
            existeIzq = false;
            
        }
        
        if(actual.getDerecho() != null)
        {
            //Si existe hijo derecho.
            der = "" + actual.getDerecho().getClave();
            existeDer = true;
        }
        else
        {
            //Si no existe hijo derecho.
            der = "-";
            existeDer = false;
            
        }
        
        //Se genera string en elemento actual.
        resultado = actual.getClave() +  "  HI: " + izq + "  HD: " + der + "\n";
        
        if(existeIzq)
        {
            //Si existe hijo izq,se procede con el.
            resultado += generaToString(actual.getIzquierdo());
        }
        
        if(existeDer)
        {
            //Si existe hijo der,se procede con el.
            resultado += generaToString(actual.getDerecho());
        }
        
        return resultado;
    }
}
