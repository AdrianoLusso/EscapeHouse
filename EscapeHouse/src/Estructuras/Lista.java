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
public class Lista {
 
        /*
        Esta clase representa un lista,con atributos de:
        */
        
        private Nodo cabecera;
        private int longitud;
        
        //Constructores
        
        public Lista()
        {
        cabecera = null; 
        longitud = 0;
        }
        
        //Modificadores
        
        public boolean insertar(Object elemento,int posc)
        {
            
            boolean exito;
            int contadorPosc;
            Nodo nuevo,auxiliar;
            
            //Analizo si la posicion ingresado esta entre 1 y una posicion nueva a agregar.
            if(posc >= 1 && posc <= longitud + 1)
            {
                nuevo = new Nodo(elemento,null);
                
                //Si la posicion no es la 1,se debe avanzar hasta encontrarla.
                //En este if se setean las variables necesarias para eso,y se realiza la busqueda.
                if(posc > 1)
                {
                     contadorPosc = 1;
                     auxiliar = cabecera;
                     posc--;         
                
                      //Busca al nodo en posicion previa a posc.
                     while(contadorPosc < posc)
                     {
                         auxiliar = auxiliar.getEnlace();
                         contadorPosc++;
                     }
                        
                    //Se inserta el elemento.
                    nuevo.setEnlace(auxiliar.getEnlace());
                    auxiliar.setEnlace(nuevo);
                }
                else
                {
                     nuevo.setEnlace(cabecera);
                     cabecera = nuevo;
                }           
                
                longitud++;
                exito = true;
                
            }
            else
            {
                exito = false;
            }
            
            return exito;
        }
        
        public boolean eliminar (int posc)
        {
            boolean exito;
            int contadorPosc;
            Nodo auxiliar;
            
            //Analizo si la posicion ingresado esta entre 1 y la ultima.
            if(posc >= 1 && posc <= longitud)
            {                
               //Si la posicion no es la 1,se debe avanzar para ubicar el elemento.
               //En este if se setean las variables necesarias para eso,y se realiza la busqueda. 
               if(posc > 1)
               {
                   contadorPosc = 1;
                   auxiliar = cabecera;
                   posc--;
                   
                   //Busca al nodo en posicion previa a posc.
                   while(contadorPosc < posc)
                   {
                       auxiliar = auxiliar.getEnlace();
                       contadorPosc++;
                   }
                   
                   //Analiza si posc es la ultima posicion de la lista,
                   //para saber si es necesario enlazar a otro nodo.
                   if(contadorPosc+1 == longitud)
                   {
                       auxiliar.setEnlace(null);
                   }
                   else
                   {
                       auxiliar.setEnlace(auxiliar.getEnlace().getEnlace());
                   }
               }
               else
               {
                   cabecera = cabecera.getEnlace();
               }
                
               longitud--;
               exito = true;
            }
            else
            {
                exito = false;
            }
            
            return exito;
        }
      
        //Observadores
        
        public Object recuperar(int posc)
        {
            Object elemento;
            Nodo auxiliar;
            int contadorPosc;
            
            //Analizo si la posicion ingresado esta entre 1 y la ultima.
            if(posc >= 1 && posc <= longitud) 
            {         
                contadorPosc = 1;
                auxiliar = cabecera;
                
                //Busca al nodo en posicion posc,luego se guarda recupera su elemento.
                while(contadorPosc < posc)
                {
                    contadorPosc++;
                    auxiliar = auxiliar.getEnlace();
                }
                
                elemento = auxiliar.getElemento();               
            }
            else
            {
                elemento = null;
            }
            
            return elemento;
        }
        
        public int localizar(Object elemento)
        {
            int contadorPosc = 1;
            Nodo auxiliar = cabecera;
            boolean encontrado = false;
            
            while(contadorPosc <= longitud && !encontrado)
            {
                if(auxiliar.getElemento().equals(elemento))
                {
                    encontrado = true;
                }
                else
                {
                auxiliar = auxiliar.getEnlace();           
                contadorPosc++;
                }
            }
            
            if(!encontrado)
            {
                contadorPosc = -1;
            }
            
            return contadorPosc;
        }
        
         public int longitud()
        {
            return longitud;
        }
        
        public boolean esVacia()
        {
            return cabecera == null;
        }        
        
        
        //Propios del tipo
        
       public void vaciar()
        {
            cabecera = null;
        }
        
                
       public Lista clone()
       {
           /*
           Este metodo crea una lista vacia, y en caso que la lista original no se vacia,
           se la clona recursivamente.
           */
           
           //crea la lista clon vacia
           Lista clon = new Lista();
           
           //realizo la clonacion solo si la lista no es vacia
           if(this.longitud() > 0)
           {
               clon = clone(this.cabecera,clon);
               clon.longitud = this.longitud;
           }
           
           return clon;
       }
       
       private Lista clone(Nodo nodoAuxiliar, Lista clon)
       {
           /*
           Este metodo recursivo realiza la clonacion de cada nodo de la lista.
           nodoAuxiliar : su primer valor es la cabecera de la fila original.Representa
           el nodo que sera clonado y cargado como cabecera de lista clon.
           clon : es la lista que sera,finalmente,la lista clon.
           */
           
                //Analizo si me encuentro en el ultimo nodo de la lista
               if(nodoAuxiliar.getEnlace() == null)
               {
                   //Estoy en el ultimo nodo,lo declaro como cabecera.
                  clon.cabecera = new Nodo(nodoAuxiliar.getElemento(),null); 
               }
               else
               {
                   //No estoy en el ultimo nodo.Invoco a clon,y reasigno el nodo cabecera.
                   clon = clone(nodoAuxiliar.getEnlace(),clon);
                   clon.cabecera = new Nodo(nodoAuxiliar.getElemento(),clon.cabecera);
               }
               
               return clon;
           }
       
       public String toString()
       {
           /*
           Este metodo copia en un String todos los elementos de los nodos de la lista.
           */
           
           String resultado = "";
           Nodo nodoAuxiliar = this.cabecera;
           
           //Voy agregando los elementos de los nodos al String,siempre que exista el nodo auxiliar actual(que no sea null).
           while(nodoAuxiliar != null)
           {
               resultado += nodoAuxiliar.getElemento() + "-";
               nodoAuxiliar = nodoAuxiliar.getEnlace();
           }
           
           
           return resultado.substring(0,resultado.length()-1);
       }
      
       public void invertir()
       {     
           if(longitud > 1)
           {
            cabecera = invertirLista(cabecera,null);
           }
       }
       
       public Nodo invertirLista(Nodo actual,Nodo previo)
       {
           Nodo nuevaCabecera;
           
           if(actual.getEnlace() == null)
           {
               nuevaCabecera = actual;
               actual.setEnlace(previo);
           }
           else
           {
               nuevaCabecera = invertirLista(actual.getEnlace(),actual);
               actual.setEnlace(previo);
           }
           
           return nuevaCabecera;
       }
       
            
       public void eliminarAparicionesDos(Object objeto)
       {
           Nodo actual = cabecera,previo = null;
           
           //Ejecuto el analisis siempre que exista un nodo actual
           while(actual != null)
           {
               //Si el elemento en el nodo actual es igual a objeto,elimino el nodo.
               if(actual.getElemento().equals(objeto))
               {
                   //Si actual es la cabecera,debo cambiar solo a actual y la cabecera.                  
                   if(actual == cabecera)
                   {                     
                       actual = actual.getEnlace();
                       cabecera = actual;                    
                   }
                   //Sino,al nodo previo lo enlazo al siguiente nodo de actual.Actual desaparece.
                   else
                   {      
                       actual = actual.getEnlace();
                       previo.setEnlace(actual);
                   }
                   
                   longitud--;
               }
               else
               //Sino,ncremento previo y actual    
               {
                   previo = actual;                   
                   actual = actual.getEnlace();                  
               }
           }
       }
       
       public Lista obtenerMultiplos(int num)
       {
           int posc = 0;
           Lista resultado = new Lista();
           
           if(num != 0 )
           {
               while(num <= this.longitud)
               {
                   posc++;
                   System.out.println(this.recuperar(num));
                   resultado.insertar(this.recuperar(num), posc);
                   num += num / posc;
               }
           }    
           
           return resultado;
           
       }
       
       public void insertarPosPrevia(Object valor1, Object valor2)
       {
           Nodo previo,nuevo;
           
           if(this.cabecera != null)
           {
               System.out.println("ss");
               System.out.println(this.cabecera.getElemento());
               if(this.cabecera.getElemento().equals(valor1))
               {
                   System.out.println('o');
                   nuevo = new Nodo(valor2,this.cabecera);
                   this.cabecera.setEnlace(new Nodo(valor2,this.cabecera.getEnlace()));
                   previo = this.cabecera.getEnlace();
                   this.cabecera = nuevo;
               }
               else
               {
                   previo = cabecera;
               }
               
               while(previo.getEnlace()!= null)
               {
                   if(previo.getEnlace().getElemento().equals(valor1))
                   {
                       nuevo = new Nodo(valor2,previo.getEnlace());
                       previo.setEnlace(nuevo);
                       previo = previo.getEnlace();
                   }
                   
                   previo = previo.getEnlace();
               }
               System.out.println(this.cabecera.getElemento());
           }
       }
     }
    

