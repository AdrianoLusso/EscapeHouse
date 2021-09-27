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
public class Grafo {
 
    
    /*
    Esta clase implementa un grafo etiquetado de forma dinamica.
    */
    
    NodoVert inicio;
    
    //Constructor

    public Grafo() {
        inicio = null;
    }
    
    //Modificadores
    
    public boolean insertarVertice(Object elem)
    {
        /*
        Este metodo inserta un vertice con elem en el grafo,en caso de que no exista.
        */
        NodoVert aux = this.ubicarVertice(elem);
        boolean exito = false;
        
        if(aux == null)
        {
            exito = true;
            inicio = new NodoVert(elem,inicio);
        }
        
        return exito;
    }
    
    private NodoVert ubicarVertice(Object elem)
    {
        /*
        Este metodo busca un vertice con elem en el grafo,y lo retorna.En caso de
        no existir,retorna null.
        */
        NodoVert aux = inicio;
        
        while(aux != null && !aux.getElem().equals(elem))
        {
            aux = aux.getSigVert();
        }
        
        return aux;
    }
    
    public boolean insertarArco(Object origen, Object destino,int etiqueta)
    {
        /*
        Este metodo inserta un arco entre los vertices con elemento origen y destino.
        El proceso falla si alguno de los vertices no existe, o el arco entre ambos
        ya existe.
        */
          
        NodoVert auxItr = inicio,auxOrigen = null, auxDestino = null;
        boolean exito = false;
        
        //Verifica existen de vertices.
        while(auxItr != null && (auxOrigen == null || auxDestino == null))
        {
            if(auxItr.getElem().equals(origen))
            {
                auxOrigen = auxItr;
            }
            if(auxItr.getElem().equals(destino))
            {
                auxDestino = auxItr;
            }
            
            auxItr = auxItr.getSigVert();
        }
        
        //Si existen los vertices,intenta crear el arco.
        if( auxOrigen != null & auxDestino != null)
        {
            exito = unirVertices(auxOrigen,auxDestino,etiqueta);           
        }
        
        return exito;
    }
    
    private boolean unirVertices(NodoVert org, NodoVert dest,int etiqueta)
            
    {
        /*
        Este metodo intenta unir los vertices ingresados por parametro.Retorna un
        bolean dependiendo de si el arco se pudo crear,o ya existia.
        */
        boolean exito = false;
        
        //Se busca un adyascente con referencia al vertice destino.
        NodoAdy aux = ubicarAdyascente(org.getPrimerAdy(),dest);
        
        if(aux == null)
        {
            //Si no se encontro tal adyascente,se unen los vertices.
            exito = true;
            org.setPrimerAdy(new NodoAdy(dest,org.getPrimerAdy(),etiqueta));
            dest.setPrimerAdy(new NodoAdy(org,dest.getPrimerAdy(),etiqueta));
        }      
        
        return exito;
    }
      
    private NodoAdy ubicarAdyascente(NodoAdy aux,NodoVert buscado)
    {
        /*
        Este metodo busca al vertice ingresado por parametro.Aux es el primer
        adyascente de la lista en la que buscar.
        
        Se usa especificamente para insertar un arco.
        */
        while(aux != null && !aux.getVertice().equals(buscado))
        {
            aux = aux.getSigAdy();
        }
        
        return aux;
    }
    
    public boolean eliminarArco(Object origen,Object destino)
    {
        /*
        Este metodo elimina el arco entre los vertices con elem origen y destino,
        si es que tal arco existe.
        */
        
        NodoVert vertOrg = this.ubicarVertice(origen);
        NodoAdy adyDest,previo;
        boolean exito = false;
        
        if(vertOrg != null)
        {
            //Se busca un adyascente destino en la lista del vertice origen.
            //Si existe,es eliminado. 
            adyDest = vertOrg.getPrimerAdy();
            previo = null; 
            
            while(adyDest != null && !exito)
            {
                if(adyDest.getVertice().getElem().equals(destino))
                {
                     eliminarArcoAux(adyDest.getVertice(),origen);
                     
                     if(previo != null)
                     {
                         previo.setSigAdy(adyDest.getSigAdy());
                     }
                     else
                     {
                         vertOrg.setPrimerAdy(adyDest.getSigAdy());
                     }
                     
                     exito = true;
                }
                else
                {
                    previo = adyDest;
                    adyDest = adyDest.getSigAdy();                    
                }
            }
        }
        
        return exito;
    }
    
    private void eliminarArcoAux(NodoVert vertice, Object buscado)
    {
        /*
        Este metodo elimina el nodo adyascente que vincula al vertice destino con
        el vertice origen.
        */
        
        NodoAdy actual = vertice.getPrimerAdy(),previo = null;
        
        while(actual != null && !actual.getVertice().getElem().equals(buscado))
        {
            previo = actual;
            actual = actual.getSigAdy();
        }
        
        if(previo != null)
        {
            previo.setSigAdy(actual.getSigAdy());
        }
        else
        {
            vertice.setPrimerAdy(actual.getSigAdy());
        }
    }
    
    public boolean eliminarVertice(Object elem)
    {
        /*
        Este metodo elimina un vertice del grafo,en caso de ser encontrado.
        */
        NodoVert encontrado;
        NodoAdy actual;
        boolean exito = false; 
        
        if(inicio != null)
        {
            //Se busca el vertice a eliminar,y se lo desconecta.
            encontrado = desconectarVertice(elem);
            
            if(encontrado != null)
            {
                //Si el vertice a eliminar existia,ahora se eliminan todos los 
                //adyascentes con referencia a este mismo.
                
                exito = true;
                actual = encontrado.getPrimerAdy();
               
                ///!!!Cambiar por while
                while(actual != null)
                {
                    eliminarArcoAux(actual.getVertice(),encontrado.getElem());
                    actual = actual.getSigAdy();
                }
                /*    
                for(actual = encontrado.getPrimerAdy();actual != null;
                    actual = actual.getSigAdy() )
                {
                    
                }
                */
            }
        }
           
        return exito;        
    }
    
    private NodoVert desconectarVertice(Object elem)
    {
        /*
        Este metodo busca el vertice con elem,y lo desconecta del grafo.Retorna
        el vertice desconectado para poder seguir operar con sus adyascentes.
        */
        
        NodoVert actual = inicio ,previo = null;
        
        while(actual != null && !actual.getElem().equals(elem))
        {
            previo = actual;
            actual = actual.getSigVert();
        }
        
        if(actual != null)
        {
            if(previo == null)
            {
                inicio = actual.getSigVert();
            }
            else
            {
                previo.setSigVert(actual.getSigVert());
            }           
        }
        
        return actual;
    }
    
    //Observadores
    
    public boolean existeVertice(Object elem)
    {
        /*
        Este metodo verifica si existe un vertice con elem.
        */
        
        return ubicarVertice(elem) != null;
    }
    
    public boolean existeArco(Object origen,Object destino)
    {
        /*
        Este metodo verifica si existe un arco entre vertices con origen y destino.
        */
        
        //Verifica que exista el vertice origen.
        NodoVert encontrado = ubicarVertice(origen);
        NodoAdy actual;
        boolean existe = false;
        
        if(encontrado != null)
        {
            //Si existe en vertice origen,busca un adyascente al vertice destino.
            //Si lo encuentra,el arco buscado existe.
        
            actual = encontrado.getPrimerAdy();
            
            while(actual != null && !actual.getVertice().getElem().equals(destino))
            {
                actual = actual.getSigAdy();
            }
            
            if(actual != null)
            {
                existe = true;
            }
        }
                    
        return existe;
    }
    
    public boolean existeCaminoConPesoMaximo(Object origen,Object destino,int pesoMaximo)
    {
        /*
        Este metodo retorna un boolean dependiendo de si existe un camino entre
        el origen y el destino,de forma que la suma de los pesos de los arcos no 
        supere a pesoMaximo.
        */
        
        boolean exito = false;    
        NodoVert auxOrg = null,auxDest= null, aux = inicio;
        Lista visitados;
        
        if(pesoMaximo >= 0)
        {
             //Se buscan los vertices origen y destino en el grafo.
            while((auxOrg == null ||  auxDest == null) && aux != null)
            {
                if(aux.getElem().equals(origen))
                {
                    auxOrg = aux;
                }
                if(aux.getElem().equals(destino))
                {
                    auxDest = aux;
                }
            
                aux = aux.getSigVert();
            }
        
            if(auxOrg != null && auxDest != null)
            {
                //Existen los vertices origen y destino.Se procede a buscar el camino.
                visitados = new Lista();
                exito = existeCaminoConPesoMaximoAux(auxOrg,destino,0,pesoMaximo,visitados);
            }
        }   

        return exito;
    } 
    
    private boolean existeCaminoConPesoMaximoAux(NodoVert actual,Object destino,int pesoActual
        ,int pesoMaximo, Lista visitados)
    {
        /*
        Este metodo analiza si el vertice actual tiene elemento destino.Si no lo tiene,
        se trata de seguir analizando el resto de vertices accesibles desde actual.
        Retorna un boolean correspondiente a si se logro,o no,encontrar un vertice con
        elemento destino.
        */
        
        boolean exito = false;
        NodoAdy adyActual;
        
        if(actual != null && pesoActual <= pesoMaximo)
        {
            //No se supero el peso maximo.
            if(actual.getElem().equals(destino))
            {
                //Se encontro camino.
                exito = true;
            }
            else
            {
                //No se encontro camino.Se prueba seguir buscando.
                visitados.insertar(actual.getElem(), visitados.longitud()+1);
                adyActual = actual.getPrimerAdy();
                
                //Se recorre todos los adyascentes
                while(!exito && adyActual != null)
                {
                    //Se analiza solo los adyascentes que no han sido visitados.
                    if(visitados.localizar(adyActual.getVertice().getElem()) < 0)
                    {
                        exito = existeCaminoConPesoMaximoAux
                        (adyActual.getVertice(),destino,pesoActual+adyActual.getEtiqueta(),pesoMaximo,
                         visitados);
                    }
                    
                    adyActual = adyActual.getSigAdy();
                }
                visitados.eliminar(visitados.longitud());
            }
        }
        
        return exito;
    }
    
    public Lista listarCaminosoSinPasarPor(Object origen,Object destino,Object bloqueo,int pesoMaximo)
    {
        /*
        Este metodo retorna una lista de listas con posibles caminos entre origen
        y destino,sin pasar por bloqueo,y consumiendo no mas de pesoMaximo.
        */
        
        NodoVert auxOrg = null,auxDest= null, aux = inicio;
        Lista visitados,caminosPosibles = new Lista();
        
        //Se buscan los vertices origen y destino en el grafo.
        while((auxOrg == null ||  auxDest == null) && aux != null)
        {
            if(aux.getElem().equals(origen))
            {
                auxOrg = aux;
            }
            if(aux.getElem().equals(destino))
            {
                auxDest = aux;
            }
            
            aux = aux.getSigVert();
        }
        
        if(auxOrg != null && auxDest != null)
        {
            
            //Existen los vertices origen y destino.Se procede a buscar el camino.
            visitados = new Lista();
            caminosPosibles = new Lista();     

            if(this.existeVertice(bloqueo))
            {
                //Existe el bloqueo.Se busca los caminos sin pasar por.
                visitados.insertar(bloqueo, 1);
                listarCaminosSinPasarPorAux(auxOrg,destino,0,pesoMaximo,visitados,
                caminosPosibles);
            }
            else
            {
                //No existe el bloqueo.Se listan caminos posibles.
                listarCaminosAux(auxOrg,destino,0,pesoMaximo,visitados,caminosPosibles);
            }        
        }
        
        return caminosPosibles;
    }
    
    private void listarCaminosSinPasarPorAux(NodoVert actual,Object destino,
            int pesoActual,int pesoMaximo,Lista visitados,Lista caminosPosibles)
    {
        /*
        Este metodo analiza si el vertice actual tiene elemento destino.Si no lo tiene,
        se trata de seguir analizando el resto de vertices accesibles desde actual.
        */
        NodoAdy adyActual;
        Lista aux;
        if(actual != null)
        {
            visitados.insertar(actual.getElem(), visitados.longitud()+1);
            
            if(actual.getElem().equals(destino))
            {
                //Se encontro camino.
                aux = visitados.clone();
                aux.eliminar(1);
                caminosPosibles.insertar(aux,caminosPosibles.longitud()+1);
            }
            else
            {
                //No se encontro camino.Se prueba seguir buscando.                
                adyActual = actual.getPrimerAdy();
                
                //Se recorre todos los adyascentes
                while( adyActual != null)
                {
                    //Se analiza solo los adyascentes que no han sido visitados,
                    //y que al visitarse,no se supere el peso maximo.
                    if(visitados.localizar(adyActual.getVertice().getElem()) < 0
                        && pesoActual + adyActual.getEtiqueta() <= pesoMaximo)
                    {
                        listarCaminosSinPasarPorAux(adyActual.getVertice(),destino,
                                pesoActual + adyActual.getEtiqueta(),pesoMaximo,visitados,caminosPosibles);
                        visitados.eliminar(visitados.longitud());
                    }
                    
                    adyActual = adyActual.getSigAdy();
                }
            }
        }
    }
    
    private void listarCaminosAux(NodoVert actual,Object destino,
            int pesoActual,int pesoMaximo,Lista visitados,Lista caminosPosibles)
    {
        /*
        Este metodo analiza si el vertice actual tiene elemento destino.Si no lo tiene,
        se trata de seguir analizando el resto de vertices accesibles desde actual.
        Retorna un boolean correspondiente a si se logro,o no,encontrar un vertice con
        elemento destino,sin haber superado pesoMaximo en el trayecto.
        */
        NodoAdy adyActual;
        Lista aux;
        if(actual != null)
        {
            visitados.insertar(actual.getElem(), visitados.longitud()+1);
            
            if(actual.getElem().equals(destino))
            {
                //Se encontro camino.
                aux = visitados.clone();
                aux.eliminar(1);
                caminosPosibles.insertar(aux,caminosPosibles.longitud()+1);
            }
            else
            {
                //No se encontro camino.Se prueba seguir buscando.                
                adyActual = actual.getPrimerAdy();
                
                //Se recorre todos los adyascentes
                while( adyActual != null)
                {
                    //Se analiza solo los adyascentes que no han sido visitados,
                    //y que al visitarse,no se supere el peso maximo.
                    if(pesoActual + adyActual.getEtiqueta() <= pesoMaximo)
                    {
                        listarCaminosAux(adyActual.getVertice(),destino,
                                pesoActual + adyActual.getEtiqueta(),pesoMaximo,visitados,caminosPosibles);
                        visitados.eliminar(visitados.longitud());
                    }
                    
                    adyActual = adyActual.getSigAdy();
                }
            }
        }
    }
    
    public boolean existeCamino(Object origen,Object destino)
    {
        /*
        Este metodo retorna un boolean dependiendo de si existe un camino entre
        el origen y el destino
        */
        
        boolean exito = false;    
        NodoVert auxOrg = null,auxDest= null, aux = inicio;
        Lista visitados;
        
        //Se buscan los vertices origen y destino en el grafo.
        while((auxOrg == null ||  auxDest == null) && aux != null)
        {
            if(aux.getElem().equals(origen))
            {
                auxOrg = aux;
            }
            if(aux.getElem().equals(destino))
            {
                auxDest = aux;
            }
            
            aux = aux.getSigVert();
        }
        
        if(auxOrg != null && auxDest != null)
        {
            //Existen los vertices origen y destino.Se procede a buscar el camino.
            visitados = new Lista();
            exito = existeCaminoAux(auxOrg,destino,visitados);
        }
        
        return exito;
    }
    
    private boolean existeCaminoAux(NodoVert actual,Object destino,Lista visitados)
    {
        /*
        Este metodo analiza si el vertice actual tiene elemento destino.Si no lo tiene,
        se trata de seguir analizando el resto de vertices accesibles desde actual.
        Retorna un boolean correspondiente a si se logro,o no,encontrar un vertice con
        elemento destino.
        */
        
        boolean exito = false;
        NodoAdy adyActual;
        
        if(actual != null)
        {
            if(actual.getElem().equals(destino))
            {
                //Se encontro camino.
                exito = true;
            }
            else
            {
                //No se encontro camino.Se prueba seguir buscando.
                visitados.insertar(actual.getElem(), visitados.longitud()+1);
                adyActual = actual.getPrimerAdy();
                
                //Se recorre todos los adyascentes
                while(!exito && adyActual != null)
                {
                    //Se analiza solo los adyascentes que no han sido visitados.
                    if(visitados.localizar(adyActual.getVertice().getElem()) < 0)
                    {
                        exito = existeCaminoAux(adyActual.getVertice(),destino,visitados);
                    }
                    
                    adyActual = adyActual.getSigAdy();
                }
            }
        }
        
        return exito;
    }
    
    public boolean esVacio()
    {
        return inicio == null;
    }
    
    //Propios del tipo
    
    @Override
    public Grafo clone()
    {
        /*
        Este metodo retorna un grafo clon del objeto receptor.
        */
        Grafo clon = new Grafo();
        
        if(inicio != null)
        {
            clon.inicio = cloneVertices(this.inicio);       
            cloneArcos(this.inicio,clon.inicio,clon);
        }
        
        return clon;
    }
    
    private void cloneArcos(NodoVert actual,NodoVert actualClon,Grafo grafo)
    {
        /*
        Este metodo clona los arcos.
        */
        NodoAdy ady;
        
        if(actual != null)
        {
            ady = actual.getPrimerAdy();
            
            while(ady != null)
            {
                actualClon.setPrimerAdy(new NodoAdy(grafo.ubicarVertice(ady.getVertice().getElem()),actualClon.getPrimerAdy(),ady.getEtiqueta()));
                ady = ady.getSigAdy();
            }
            /*
            for(ady = actual.getPrimerAdy();ady != null; ady = ady.getSigAdy())
            {
                actualClon.setPrimerAdy(new NodoAdy(grafo.ubicarVertice(ady.getVertice().getElem()),actualClon.getPrimerAdy(),ady.getEtiqueta()));
            } 
            */
        
        cloneArcos(actual.getSigVert(),actualClon.getSigVert(),grafo);
        }
            
    }
    
    private NodoVert cloneVertices(NodoVert actual)
    {
        /*
        Este metodo clona los vertices.
        */
        
        NodoVert clonActual = null;
      
        if(actual != null)
        {
            clonActual = new NodoVert(actual.getElem(),cloneVertices(actual.getSigVert()));                                                            
        }
        
        return clonActual;     
    }
   
    
    public Lista listarEnProfundidad()
    {
        /*
        Este metodo retorna una lista con recorrido en profundidad del grafo.
        */
        
        NodoVert actual = inicio;
        Lista visitados = new Lista();
        
        //Para cada vertice del grafo,si no fue visitado,se inicia 
        //el recorrido en profundidad.
        
        while(actual != null)
        {
            if(visitados.localizar(actual.getElem()) < 0)
            {
                profundidadDesde(actual,visitados);
            }    
            actual = actual.getSigVert();
        }
            
        /*
        for(actual = inicio; actual != null; actual = actual.getSigVert())
        {
            if(visitados.localizar(actual.getElem()) < 0)
            {
                profundidadDesde(actual,visitados);
            }            
        }
        */
        
        return visitados;
    }
    
    private void profundidadDesde(NodoVert vertActual,Lista visitados)
    {
        
        NodoAdy adyActual = vertActual.getPrimerAdy();
        
        //Se inserta el vertice actual como visitado.
        visitados.insertar(vertActual.getElem(), visitados.longitud()+1);
        
        //Para cada adyascente no visitado,se analiza recursivamente.
        
        while(adyActual != null)
        {
            adyActual = adyActual.getSigAdy();
            if(visitados.localizar(adyActual.getVertice().getElem()) < 0)
            {
                profundidadDesde(adyActual.getVertice(),visitados);
            }
        }
        /*
        for(adyActual = vertActual.getPrimerAdy();adyActual != null;
            adyActual = adyActual.getSigAdy())
        {
            if(visitados.localizar(adyActual.getVertice().getElem()) < 0)
            {
                profundidadDesde(adyActual.getVertice(),visitados);
            }
        }
        */
    }
    
    public Lista listarEnAnchura()
    {
        /*
        Este metodo retorna una lista con recorrido en anchura del grafo
        */
        
        NodoVert actual = inicio;
        Lista visitados = new Lista();
        
        while(actual != null)
        {
            if(visitados.localizar(actual.getElem()) < 0)
            {
                anchuraDesde(actual,visitados);
            }   
            actual = actual.getSigVert();
        }
        /*
        for(actual = inicio; actual != null; actual = actual.getSigVert())
        {
            if(visitados.localizar(actual.getElem()) < 0)
            {
                anchuraDesde(actual,visitados);
            }            
        }
        */
        return visitados;
    }
    
    private void anchuraDesde(NodoVert vertActual,Lista visitados)
    {
        Cola aux = new Cola();
        NodoAdy adyActual;
        
        //Agrega el vertice ingresado por parametro a la lista y la cola.
        visitados.insertar(vertActual.getElem(), visitados.longitud()+1);
        aux.poner(vertActual);
        
        while(!aux.esVacia())
        {
            vertActual =(NodoVert) aux.obtenerFrente();
            aux.sacar();
            adyActual = vertActual.getPrimerAdy();
            
            while(adyActual != null)
            {
                if(visitados.localizar(adyActual.getVertice().getElem()) < 0)
                {
                    //Si aun no fue visitado este nodo adyascente,se lo visita.
                    visitados.insertar(adyActual.getVertice().getElem(),visitados.longitud()+1);
                    aux.poner(adyActual.getVertice());
                }
                adyActual = adyActual.getSigAdy();
            }
                                
            /*
            //Se analiza los adyascente del vertice frente actual.
            for(adyActual = vertActual.getPrimerAdy();
                adyActual != null;adyActual = adyActual.getSigAdy())
            {
                if(visitados.localizar(adyActual.getVertice().getElem()) < 0)
                {
                    //Si aun no fue visitado este nodo adyascente,se lo visita.
                    visitados.insertar(adyActual.getVertice().getElem(),visitados.longitud()+1);
                    aux.poner(adyActual.getVertice());
                }
            }
            */
        }     
    }
  
    public Lista caminoMasLargo(Object origen,Object destino)
    {
        /*
        Este metodo retorna la lista con el camino mas largo entre el origen y el
        destino,en caso de que el camino exista.
        */
        
        Lista resultado = new Lista();
        NodoVert verticeOrigen = ubicarVertice(origen);
        
        if(verticeOrigen != null)
        {
            
             resultado = encuentraCaminoMasLargo(verticeOrigen,new Lista(),resultado,destino);
        }
        
        return resultado;
    }
    
    private Lista encuentraCaminoMasLargo(NodoVert actual,Lista caminoActual,
            Lista caminoMaximo,Object destino)
    {
        
        NodoAdy adyActual;
        
     
        if(actual != null)
        {
            
            caminoActual.insertar(actual.getElem(), caminoActual.longitud() + 1);

            if(actual.getElem().equals(destino))
            {
                //Si se encuentra el elemento,y el camino actual es mayor que el
                //maximo,pasa a ser el nuevo maximo.
                
                if(caminoActual.longitud() > caminoMaximo.longitud())
                {
                    caminoMaximo = caminoActual.clone();
                }              
            }
            else
            {
                adyActual = actual.getPrimerAdy();
                
                while(adyActual != null)
                {
                    if(caminoActual.localizar(adyActual.getVertice().getElem()) < 0)
                    {
                       caminoMaximo = encuentraCaminoMasLargo(adyActual.getVertice(),caminoActual,
                            caminoMaximo,destino);
                        caminoActual.eliminar(caminoActual.longitud());
                    }   
                    adyActual = adyActual.getSigAdy();
                }
                
                /*
                //No se encontro el elemento.Se sigue la busqueda en profundidad.               
            
                 for(adyActual = actual.getPrimerAdy();adyActual != null;
                 adyActual = adyActual.getSigAdy())
                 {
                    if(caminoActual.localizar(adyActual.getVertice().getElem()) < 0)
                    {
                       caminoMaximo = encuentraCaminoMasLargo(adyActual.getVertice(),caminoActual,
                            caminoMaximo,destino);
                        caminoActual.eliminar(caminoActual.longitud());
                    }   
                 }
                */
            }    
        }
        
        return caminoMaximo;
    }
    
    public Lista caminoMasCorto(Object origen,Object destino)
    {
        /*
        Este metodo retorna la lista con el camino mas corto entre el origen y el
        destino,en caso de que el camino exista.
        */
        
        Lista resultado = new Lista();
        NodoVert verticeOrigen = ubicarVertice(origen);
        
        if(verticeOrigen != null)
        {
            
             resultado = encuentraCaminoMasCorto(verticeOrigen,new Lista(),resultado,destino);
        }
        
        return resultado;
    }
    
     private Lista encuentraCaminoMasCorto(NodoVert actual,Lista caminoActual,
            Lista caminoMinimo,Object destino)
    {

        NodoAdy adyActual;
        
         
     
        if(actual != null)
        {
           
            caminoActual.insertar(actual.getElem(), caminoActual.longitud() + 1);
            
            if(actual.getElem().equals(destino))
            {

                //Si se encuentra el elemento,y el camino actual es mayor que el
                //maximo,pasa a ser el nuevo maximo.
                
                if(caminoMinimo.longitud() == 0 || caminoActual.longitud() < caminoMinimo.longitud())
                {
                    caminoMinimo = caminoActual.clone();
                }              
            }
            else
            {
                adyActual = actual.getPrimerAdy();
                
                while(adyActual != null)
                {
                    if(caminoActual.localizar(adyActual.getVertice().getElem()) < 0)
                    {
                        caminoMinimo = encuentraCaminoMasCorto(adyActual.getVertice(),caminoActual,
                            caminoMinimo,destino);
                        caminoActual.eliminar(caminoActual.longitud());
                    }  
                    
                    adyActual = adyActual.getSigAdy();
                }
                
                /*
                //No se encontro el elemento.Se sigue la busqueda en profundidad.               
                
                 for(adyActual = actual.getPrimerAdy();adyActual != null;
                 adyActual = adyActual.getSigAdy())
                 {
                    if(caminoActual.localizar(adyActual.getVertice().getElem()) < 0)
                    {
                        caminoMinimo = encuentraCaminoMasCorto(adyActual.getVertice(),caminoActual,
                            caminoMinimo,destino);
                        caminoActual.eliminar(caminoActual.longitud());
                    }   
                 }
                */
            }    
        }
        
        return caminoMinimo;
    }
     
     public Lista verticesAdyascentesYEtiquetas(Object elemento)
     {
         /*
         Este metodo retorna una lista con los vertices adyascentes del nodo con
         elemento y las respectivas etiquetas de sus arcos.
         El formato de la lista es (ady1,et1,ady2,et2,...).
         Si el elemento ingresado no existe,se retorna la lista vacia.
         */
         
         Lista resultado = new Lista();
         NodoVert vertActual = inicio;
         NodoAdy adyActual;
         
         while(vertActual != null && !vertActual.getElem().equals(elemento))
         {
             //Se busca el vertice con elemento.
             vertActual = vertActual.getSigVert();
         }
         
         if(vertActual != null)
         {
             adyActual = vertActual.getPrimerAdy();
             
             while(adyActual != null)
             {
                 resultado.insertar(adyActual.getVertice().getElem(),resultado.longitud()+1);
                 resultado.insertar(adyActual.getEtiqueta(),resultado.longitud()+1);
                 adyActual = adyActual.getSigAdy();
             }
             
             //Si se encontro a elemento,se lista sus adyascentes.
             /*
             for(adyActual = vertActual.getPrimerAdy();adyActual != null;
                adyActual = adyActual.getSigAdy())
             {
                 resultado.insertar(adyActual.getVertice().getElem(),resultado.longitud()+1);
                 resultado.insertar(adyActual.getEtiqueta(),resultado.longitud()+1);
             }
             */
         }
         
         return resultado;
     }
     
     public boolean sonAdyascentes(Object origen,Object destino)
     {
         /*
         Este metodo retorna un boolean dependiendo de si origen y destino son adyascentes.
         Precondicion:deben existir origen y destino.En caso contrario,se retorna false.
         */
         NodoVert vertActual = inicio;
         NodoAdy adyActual;
         boolean sonAdys = false;

         while(vertActual != null && !vertActual.getElem().equals(origen))
         {
             //Se busca el vertice con origen.
             vertActual = vertActual.getSigVert();
         }
         
         if(vertActual != null)
         {
             //Se encontro el vertice origen.
             adyActual = vertActual.getPrimerAdy();
             while(adyActual != null && !adyActual.getVertice().getElem().equals(destino))
             {
                 //Se busca el vertice destino.
                 adyActual = adyActual.getSigAdy();
             }
             
             if(adyActual != null)
             {
                 //Se verifica adyascencia.
                 sonAdys = true;
             }
         }
         
         return sonAdys;
     }
     
     public int obtenerEtiqueta(Object origen,Object destino)
     {
         /*
         Este metodo retorna la etiqueta entre origen y destino.
         Si no son adyascentes,se retorna -1.
         */
         
         int etiqueta = -1;
         NodoVert vertActual = inicio;
         NodoAdy adyActual;
         
         while(vertActual != null && !vertActual.getElem().equals(origen))
         {
             //Se busca el vertice origen.
             vertActual = vertActual.getSigVert();
         }
         
         if(vertActual != null)
         {
             //Si se encontro al origen,se busca a destino entre sus adyascentes.
             adyActual = vertActual.getPrimerAdy();
             while(adyActual != null && !adyActual.getVertice().getElem().equals(destino))
             {
                 //Se busca el vertice destino.
                 adyActual = adyActual.getSigAdy();
             }
             
             if(adyActual != null)
             {
                 //Se verifica adyascencia.
                 etiqueta = adyActual.getEtiqueta();
             }
         }
         
         return etiqueta;
     }
     
    @Override
     public String toString()
     {
         String resultado = "";
         
         if(inicio != null)
         {
             resultado = toStringAux(inicio);
         }
         
         return resultado;
     }
     
     private String toStringAux(NodoVert actual)
     {
         
         String resultado = "";
         NodoAdy adyActual;
         
         for(;actual != null;actual = actual.getSigVert())
         {
             resultado += actual.getElem() + ":  ";
             for(adyActual = actual.getPrimerAdy();adyActual != null;adyActual = adyActual.getSigAdy())
             {
                 resultado +="(" + adyActual.getVertice().getElem() + "," + adyActual.getEtiqueta() + "); ";
             }
             resultado = resultado.substring(0, resultado.length()-2);
             resultado += "\n";
         }
         
         return resultado;
     }
}
