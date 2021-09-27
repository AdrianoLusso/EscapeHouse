package EscapeHouse;

import Estructuras.Diccionario;
import Estructuras.Grafo;
import Estructuras.Lista;
import Estructuras.MapeoAMuchos;
import Estructuras.TablaDeBusqueda;
import Utiles.TecladoIn;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * @author Adriano Lusso
 */
public class Menu {
    
    private static BufferedWriter escritor;
    private static StringTokenizer tokenizer;
    private static Grafo plano;
    private static TablaDeBusqueda equipos;
    private static Diccionario habitaciones;
    private static Diccionario desafios;
    private static MapeoAMuchos puertasAbiertas;
    private static MapeoAMuchos desafiosResueltos;
    
    public static void main (String[] args) throws IOException
    {
        /*
        Menu principal.
        */
        
        //!!!Crear estas estructuras como vars de clase.
        plano = new Grafo();
        equipos = new TablaDeBusqueda();
        habitaciones = new Diccionario();
        desafios = new Diccionario();
        puertasAbiertas = new MapeoAMuchos();
        desafiosResueltos = new MapeoAMuchos();
        escritor = new BufferedWriter(new FileWriter(crearArchivo()));
        int opcion;

        
        do{
            System.out.println("\nBienvenido a Escape House! Elige una opcion: ");
            System.out.println("1. Carga inicial del sistema.(!Se reiniciaran todos los datos.)");
            System.out.println("2. Sistema ABM.");
            System.out.println("3. Consultas de habitaciones.");
            System.out.println("4. Consultas de desafios.");
            System.out.println("5. Consultas sobre equipos.");
            System.out.println("6. Mostrar el sistema.");
            System.out.println("7. Terminar.\n");
            
            opcion = TecladoIn.readLineInt();
            switch(opcion)
            {
                case 1:
                    cargaInicialSistema();
                    
                    break;
                    
                case 2:
                    sistemaABM();
                    break;
                    
                case 3:
                    consultasHabitaciones();
                    break;
                
                case 4:
                    consultasDesafios();
                    break;
                    
                case 5:
                    consultasEquipos();
                    break;
                case 6:
                    mostrarSistema();
                    break;
                
                case 7:
                    estadoFinal();
                    break;
            }
        }while(opcion != 7);
       
    }
    
    public static void estadoFinal() throws IOException
    {
        /*
        Se imprime en el archivo log output el estado final de los objetos de las
        3 clases principales del dominio...Habitacion,Desafio y equipo.
        */
        
        Lista habts = habitaciones.listarDatos(),desfs = desafios.listarDatos(),
                eqps = equipos.listarDatos();
        int posc;
        
        escribir("------------\nEstado final\n------------\n");
        
        for(posc = 1;posc <= habts.longitud();posc++)
        {
            escribir(mostrar((Habitacion)habts.recuperar(posc)) + "\n\n");
        }
        
        for(posc = 1;posc <= desfs.longitud();posc++)
        {
            escribir(mostrar((Desafio)desfs.recuperar(posc)) + "\n\n");
        }
        
        for(posc = 1;posc <= eqps.longitud();posc++)
        {
            escribir(mostrar((Equipo)eqps.recuperar(posc)) + "\n\n");
        }        
    }
    
    public static void escribir(String texto) throws IOException
    {
        /*
        Este metodo realiza el proceso de escritura output de texto.
        */
        escritor.write(texto);
        escritor.flush();
    }
    
    public static File crearArchivo() throws IOException
    {
        /*
        Genera un archivo output para ciertas operaciones del sistema.
        */
        
        String ruta = "C:\\Users\\54299\\Desktop\\Facultad\\2do anio\\Estructuras de datos\\TPOFinal\\operativa.txt";
        
        File nuevo = new File(ruta);
        
        if(!nuevo.exists())
        {
            nuevo.createNewFile();
        }
        
        return nuevo;
    }
        
    
    public static void mostrarSistema()
    {
        /*
        Muestra las estructuras y los elementos que las conforman.
        */
        
        System.out.println("Plano:\n" + plano.toString() + "\n\n");
        System.out.println("Habitaciones:\n" + habitaciones.toString() + "\n\n");
        System.out.println("Desafios:\n" + desafios.toString() + "\n\n");
        System.out.println("Equipos:\n" + equipos.toString() + "\n\n");
        System.out.println("Desafios resueltos:\n" + desafiosResueltos.toString() + "\n");
    }
        
    
    public static void consultasEquipos()
    {
        /*
        Menu de consultas de equipo.
        */
        
        int opcion;
        Equipo actual = buscarEquipo();
        
        if(actual != null && actual.getClaveHabtActual() != -1)
        {
            do{
                System.out.println("\nQue desea consultar?");
                System.out.println("1. Mostrar equipo.");
                System.out.println("2. Jugar desafio.");
                System.out.println("3. Pasar a habitacion.");
                System.out.println("4. Puede salir de la casa?");
                System.out.println("5. Volver al menu\n");
            
                opcion = TecladoIn.readLineInt();
            
                switch(opcion)
                {
                    case 1:
                        System.out.println("\n" + mostrar(actual) + "\n");
                        break;
                    
                    case 2:
                        System.out.println(jugarDesafio(actual));                      
                       break;
                    
                    case 3:
                        
                        System.out.println(pasarAHabitacion(actual));                 
                        break;
                        
                    case 4:
                        System.out.println(puedeSalir(actual));                   
                        break;
                }
            }while( opcion != 5);
        }
        else
        {
            System.out.println("El equipo ingresado no existe,o aun no se encuentra "
                    + "en una habitacion de la Escape House.");
        } 
    }
    
    
    public static String mostrar(Equipo actual)
    {
        /*
        Metodo sobrecargado que retorna la informacion del equipo.
        */
        
        String res = "Equipo nombre " + actual.getNombre() + "\npuntaje de salida: " +actual.getPuntajeDeSalida()
                + "\npuntaje total: " + actual.getPuntajeTotal() + "\npuntaje actual: " + actual.getPuntajeActual();
        
        if(actual.getClaveHabtActual() > 0)
        {
            res += "\nclave de habitacion actual: " + actual.getClaveHabtActual();
        }
        else
        {
            res += "\nNo se encuentra en ninguna habitacion.\n";
        }
        return res;
    }
    
    public static String puedeSalir(Equipo actual)
    {
        /*
        Retorna un string dependiendo de si el equipo actual puede salir de
        habt actual.
        */
        
        Habitacion habtActual =(Habitacion) habitaciones.obtenerInformacion(actual.getClaveHabtActual());
        String resultado;
        
        if(actual.getPuntajeTotal() >= actual.getPuntajeDeSalida()
                && habtActual.getTieneSalida())
        {
            resultado = "Puede salir de la Escape Room.";
        }
        else
        {
            resultado = "No puede salir de la Escape Room.";
        }
        
        return resultado;
    }    
        
    
    public static String pasarAHabitacion(Equipo actual)
    {
        /*
        Retorna un string dependiendo de si el equipo actual logra pasar de
        habitacion origen a destino.
        */
        String resultado = "Se logro pasar a la habitacion.";
        Habitacion origen = (Habitacion) habitaciones.obtenerInformacion(actual.getClaveHabtActual());
        Habitacion destino = buscarHabitacion();
        int costoPuntaje = plano.obtenerEtiqueta(origen,destino);
        
        if(costoPuntaje != -1)
        {
            if(puertaAbierta(origen.getCodigo(),destino.getCodigo(),puertasAbiertas.obtenerValores(actual)))
            {
                pasoDeHabitacion(actual,destino.getCodigo(),origen,destino);
            }
            else
            {
                if(actual.getPuntajeActual() >= costoPuntaje)
                {
                    pasoDeHabitacion(actual,destino.getCodigo(),origen,destino);
                    actual.setPuntajeActual(actual.getPuntajeActual() - costoPuntaje);
                    puertasAbiertas.asociar(actual,new Puerta(origen.getCodigo(),destino.getCodigo()));
                }
                else
                {
                    resultado = "Necesitas mas puntos para pasar.";
                }
            }
        }
        else
        {
            resultado = "No se puede pasar a la habitacion.";
        }
        
        return resultado;
    }
        
    public static boolean puertaAbierta(int codigoOrigen,int codigoDestino,
            Lista puertasAbiertas)
    {
        /*
        Retorna un boolean dependiendo de si la puerta que conecta las habitaciones
        con codigos ingresados por parametro,pertenece,o no, a la lista de puertas
        abiertas.
        */
        
        int posc = 1;
        Puerta actual;
        boolean estaAbierta = false;
        
        while(!estaAbierta && posc <= puertasAbiertas.longitud())
        {
            actual =(Puerta) puertasAbiertas.recuperar(posc);
            
            if(actual.conecta(codigoOrigen, codigoDestino))
            {
                estaAbierta = true;
            }               
            posc++;
        }
        
        return estaAbierta;
    }
    
    public static String jugarDesafio(Equipo actual)
    {
        /*
        Este metodo retorna un string dependiendo de si equipo actual puede
        jugar el desafio que ingrese el usuario.En caso de que lo juegue,se
        actualizan correctamente los datos.
        */
        
        int puntaje;
        String resultado;
        Desafio desafio;
        
        
        System.out.println("Ingrese el puntaje del desafio.");
        puntaje = TecladoIn.readLineInt();
        desafio = (Desafio) desafios.obtenerInformacion(puntaje);
            
        if(desafio != null && desafiosResueltos.obtenerValores(actual).localizar(desafio) < 0)
        {
            resultado = "Desafio exitoso.";
            actual.setPuntajeActual(actual.getPuntajeActual() + desafio.getPuntaje());
            actual.setPuntajeTotal(actual.getPuntajeTotal() + desafio.getPuntaje());
            desafiosResueltos.asociar(actual, desafio);
        }
        else
        {
            resultado = "El desafio no existe,o ya ha sido jugado.";
        }               
        
        
        return resultado;
    }
    
    public static void consultasDesafios()
    {
        /*
        Menu de consultas de desafios.
        */
        
        int opcion;
        
        do{
            System.out.println("\nQue desea consultar?");
            System.out.println("1. Mostrar desafio.");
            System.out.println("2. Mostrar desafios resueltos por un equipo.");
            System.out.println("3. Mostrar desafios tipo X,puntaje entre [a,b].");
            System.out.println("4. Volver al menu.\n");
            
            opcion = TecladoIn.readLineInt();
            
            switch(opcion)
            {
                case 1:              
                    System.out.println("\n" + mostrar(buscarDesafio()) + "\n");
                    break;
                    
                case 2:
                    System.out.println(mostrarDesafiosResueltosPorEquipo());
                    break;
                    
                case 3: 
                    System.out.println(mostrarDesafiosTipo());
                    break;
            }
                
        }while(opcion != 4);
    }
    
    public static String mostrar(Desafio actual)
    {
        /*
        Metodo sobrecargado que retorna la informacion del desafio.
        */
        
        String res;
        
        if(actual !=null)
        {
            res = "Desafio puntaje " + actual.getPuntaje() + "\nnombre: " +actual.getNombre()
                + "\ntipo: ";
                         
            switch(actual.getTipo())
            {
                case 'M':
                    res += "Matematico";
                    break;
                case 'L':
                    res += "Logico";
                    break;
                case 'B':
                    res += "Busqueda";
                    break;
                case 'D':
                    res += "Destreza";
                    break;
                case 'I':
                    res += "Indefinido";
                    break;
            }
        }
        else
        {
            res = "No existe el desafio ingresado.";
        }
        
        
        return res;
    }
    
    public static String mostrarDesafiosTipo()
    {
        /*
        Este metodo retorna un string con los desafios tipo X,que se encuentre
        en un  rango [a,b].
        */
        
        String resultado = "";
        Desafio actual;
        Lista datos = desafios.listarDatos();
        int min,max,posc;
        char tipo;
        
        System.out.println("Ingrese la informacion con la siguiente estructura:\n"
                + "puntajeMinimo;puntajeMaximo;inicialDelTipoX");
        tokenizer = new StringTokenizer(TecladoIn.readLine(),";");
        
        min = Integer.parseInt(tokenizer.nextToken());
        max = Integer.parseInt(tokenizer.nextToken());
        tipo = Character.toUpperCase(tokenizer.nextToken().charAt(0));
        
        for(posc = 1;posc <= datos.longitud();posc++)
        {
            actual =(Desafio) datos.recuperar(posc);
            if(actual.getPuntaje() >= min && 
                actual.getPuntaje() <= max
                && actual.getTipo() == tipo)
            {
                resultado += actual.toString() + "\n";
            }
        }
        
        if(resultado.equals(""))
        {
            resultado = "No existen desafios con tales caracteristicas.";
        }
        
        return resultado + "\n\n";
    }
    
    public static String mostrarDesafiosResueltosPorEquipo()
    {
        /*
        Este metodo retorna un string con los desafios resueltos por el equipo
        ingresado por parametro.
        */
        
        String resultado;
        Lista resueltos;
        Equipo eqp;
        Desafio actual;
        int posc;
        
 
        eqp =buscarEquipo();
        
        if(eqp != null)
        {
            resueltos = desafiosResueltos.obtenerValores(eqp);
            if(!resueltos.esVacia())
            {
                resultado = "Nombre             Puntaje obtenido\n-------------------------------------\n";
                for(posc = 1;posc <= resueltos.longitud();posc++)
                {
                    actual = (Desafio) resueltos.recuperar(posc);
                    resultado += actual.getNombre() + "         " + 
                    actual.getPuntaje() + "\n\n";
                }
            }
            else
            {
                resultado = "No existen desafios resueltos por este equipo\n\n";
            }   
        }
        else
        {
            resultado = "Equipo inexistente.";
        }
        
        return resultado;
    }
 
    public static void consultasHabitaciones()
    {
        /*
        Menu de consultas de habitaciones.
        */
        
        int opcion;
        Habitacion actual = buscarHabitacion();
        
        if(actual != null)
        {
            do
            {
                System.out.println("\nQue desea consultar?");
                System.out.println("1. Mostrar habitacion.");
                System.out.println("2. Mostrar habitaciones contiguas.");
                System.out.println("3. Es posible llegar de habt1 a habt2?");
                System.out.println("4. Camino sin pasar por una habitacion.");
                System.out.println("5. Volver al menu.\n");
                opcion = TecladoIn.readLineInt();
            
                switch(opcion)
                {
                    case 1:
                        System.out.println("\n" + mostrar(actual) + "\n");
                        break;
                    
                    case 2:
                        System.out.println(mostrarHabitacionesContiguas(actual));                    
                        break;
                  
                    case 3:
                        System.out.println(esPosibleLlegar(actual));
                        break;
                   
                    case 4:
                        sinPasarPor(actual,buscarHabitacion());            
                        break;
                }
        }while(opcion != 5);
        }
        else
        {
            System.out.println("La habitacion no existe.");
        }    
    }
    
    public static String mostrar(Habitacion actual)
    {
        /*
        Este metodo sobrecargado retorna un string con la informacion de la habitacion.
        */
        
        String res = "Habitacion codigo " + actual.getCodigo() + "\nnombre: " +actual.getNombre()
                + "\nplanta: " + actual.getPlanta() + "\nmetros cuadrados: " +actual.getM2();

        if(actual.getTieneSalida())
        {
            res += "\nTiene salida al exterior.";
        }
        else
        {
            res += "\nNo tiene salida al exterior.";
        }
        
        return res;
    }
    
    public static void sinPasarPor(Habitacion origen,Habitacion destino)
    {
        /*
        Este metodo imprime todos lso caminos posibles entre origen y destino,
        sin pasar por bloqueo ni superar puntajeMaximo.
        Si origen y destino coinciden,se considera que existe el camino,sin importar
        cual sea el bloqueo.
        */
        
        Habitacion bloqueo;
        int puntajeMaximo,posc;
        Lista resultado;
        
        System.out.println("Ingrese el codigo de la 3ra habitacion,por la que no se debe pasar: ");       
        bloqueo =(Habitacion) habitaciones.obtenerInformacion(Math.abs(TecladoIn.readLineInt()));
        
        System.out.println("Ingrese el puntaje con el que quiere saber si es"
                + " posible llegar de una habitacion a la otra: ");
        puntajeMaximo = Math.abs(TecladoIn.readLineInt());
        
        resultado = plano.listarCaminosoSinPasarPor(origen, destino, bloqueo, puntajeMaximo);
        
        for(posc = 1;posc <= resultado.longitud();posc++)
        {
            System.out.println("camino encontrado: " + resultado.recuperar(posc).toString() + "\n");
        }                
    }
    
    public static String esPosibleLlegar(Habitacion origen)
    {
        /*
        Este metodo retorna un String dependiendo de si es posible llegar de
        origen a destino,con,maximo,la cantidad de puntos que ingrese el usuario.
        */

        int puntajeMaximo;
        String resultado = "No se encontro el camino buscado.";
        Habitacion destino = buscarHabitacion();
        
        if(destino != null)
        {
            System.out.println("Ingrese el puntaje con el que quiere saber si es"
                + " posible llegar de una habitacion a la otra: ");
            puntajeMaximo = Math.abs(TecladoIn.readLineInt());
        
            if(plano.existeCaminoConPesoMaximo(origen, destino, puntajeMaximo))
            {
                resultado = "Se encontro el camino buscado." ;
            }
        }               
        
        return resultado;
    }
    
    public static String mostrarHabitacionesContiguas(Habitacion actual)
    {
        /*
        Este metodo retorna un string con las habitaciones contiguas de actual.
        */
        
        String resultado;
        Lista pares;
        int posc;
        Habitacion aux;

        pares = plano.verticesAdyascentesYEtiquetas(actual);
        
        if(!pares.esVacia())
        {
            resultado = "Contiguas             Puntaje necesario"
                    + "\n----------------------------------------\n";
            
            for(posc = 1;posc <= pares.longitud();posc++)
            {
                aux = (Habitacion) pares.recuperar(posc);
                resultado += aux.toString() + "       " + 
                pares.recuperar(++posc) + "\n\n";
            }
        }
        else
        {
            resultado = "No existen habitaciones contiguas.\n\n";
        }
        
        return resultado;                     
    }    
    
    public static void sistemaABM() throws IOException
    {
        /*
        Menu del sistema ABM.
        */
        
        int opcion;
        
        do
        {
            System.out.println("\nBienvenido al sistema ABM.Elige la estructura sobre"
                    + " la que quieras operar: ");
            System.out.println("1. Habitaciones");
            System.out.println("2. Desafios.");
            System.out.println("3. Equipos.");
            System.out.println("4.Volver al menu.\n");
            
            opcion = TecladoIn.readLineInt();
            
            switch(opcion)
            {
                case 1:
                    habitacionesABM();
                    break;
                case 2:
                    desafiosABM();
                    break;
                case 3:
                    equiposABM();
                    break;                
            }
        }while(opcion != 4);
    }
    
    public static void equiposABM() throws IOException
    {
        /*
        Menu ABM para equipos.
        */
        
        int opcion;
        Equipo aModificar;
        String nombre;
        
        System.out.println("Ingrese el nombre del equipo: ");
        nombre = TecladoIn.readLine().toUpperCase();
        aModificar = (Equipo) equipos.obteneInformacion(nombre);
        
        if(aModificar != null)
        {
            System.out.println("\nQue desea hacer?...");
            System.out.println("1. Eliminar equipo.");
            System.out.println("2. Modificar puntaje de salida.");
            System.out.println("3. Modificar puntaje total.");
            System.out.println("4. Modificar puntaje actual.");
            System.out.println("5.Agregar,o modificar,habitacion actual.\n");
            opcion = TecladoIn.readLineInt();
        
            switch(opcion)
            {
                case 1:
                    System.out.println(eliminar(aModificar));
                    break;
                    
                case 2:
                    primeraMod(aModificar);
                    break;
                    
                case 3:
                    System.out.println(segundaMod(aModificar));
                    break;       
                    
                case 4:
                    System.out.println(terceraMod(aModificar));
                     break;
                    
                case 5:
                    System.out.println(cuartaMod(aModificar));
                    break;
            }
        }
        else
        {
            System.out.println("El equipo no existe.Creela completando la"
                + " siguiente estructura: \npuntajeDeSalida;puntajeTotal;"
                    + "puntajeActual");
            tokenizer = new StringTokenizer(TecladoIn.readLine().toUpperCase(),";");
                    
            if(!insertarEquipo(nombre))
            {
                System.out.println(advertencia());
            }
        }   
    }
    
    public static String eliminar(Equipo actual) throws IOException
    {
        /*
        Metodo sobrecargado que elimina el objeto actual.
        */
        
        escribir("Se elimino el equipo " + actual.getNombre() + ".\n\n");
                    
        if(actual.getClaveHabtActual() != -1)
        {
            ((Habitacion) habitaciones.obtenerInformacion(actual.getClaveHabtActual())).restarEquipo();
         }
                    
        equipos.eliminar(actual.getNombre());
        
        return "Eliminacion exitosa.";
    }      
    
    public static void primeraMod(Equipo actual) throws IOException
    {
        /*
        Metodo sobrecargado que realiza la primera modificacion del respectivo menu
        ABM del objeto actual.
        */
        
        System.out.println("Ingrese el nuevo puntaje de salida: \n");                    
        actual.setPuntajeDeSalida(TecladoIn.readLineInt());
        escribir("El equipo "+ actual.getNombre() + " cambio puntaje de salida a " + actual.getPuntajeDeSalida() + ".\n\n");
    }
    
    public static String segundaMod(Equipo actual) throws IOException
    {
        /*
        Metodo sobrecargado que realiza la segunda modificacion del respectivo menu
        ABM del objeto actual.       
        */
        
        int puntajeTotal;
        String resultado;
        
        System.out.println("Ingrese el nuevo puntaje total: \n");
        puntajeTotal = TecladoIn.readLineInt();
                    
        if(puntajeTotal < actual.getPuntajeActual())
        {
            resultado = advertencia();
        }
        else
        {
            actual.setPuntajeTotal(puntajeTotal);
            
            resultado = "Modificacion exitosa.";
            escribir("El equipo "+ actual.getNombre() + " cambio puntaje total a " + actual.getPuntajeTotal() + ".\n\n");
        }
        
        return resultado;
    }
    
    public static String terceraMod(Equipo actual) throws IOException
    {
        /*
        Metodo sobrecargado que realiza la tercera modificacion del respectivo menu
        ABM del objeto actual.       
        */
        
        int puntajeActual;
        String resultado;
        
        System.out.println("Ingrese el nuevo puntaje actual: \n");
        puntajeActual = TecladoIn.readLineInt();
        if(puntajeActual > actual.getPuntajeTotal())
        {
            resultado = advertencia();
        }
        else
        {
            actual.setPuntajeTotal(puntajeActual);
            
            resultado = "Modificacion exitosa";
            escribir("El equipo "+ actual.getNombre() + " cambio puntaje actual a " + actual.getPuntajeActual() + ".\n\n");
        }
        
        return resultado;
    }
    
    public static String cuartaMod(Equipo actual) throws IOException
    {
        /*
        Metodo sobrecargado que realiza la cuarta modificacion del respectivo menu
        ABM del objeto actual.       
        */
        
        int codigo;
        String resultado;
        
        System.out.println("Ingrese el codigo de la nueva habitacion actual.");
        codigo = Math.abs(TecladoIn.readLineInt());
                    
        if(habitaciones.existeClave(codigo))
        {
            pasoDeHabitacion(actual,codigo,
            (Habitacion)habitaciones.obtenerInformacion(actual.getClaveHabtActual()),
            (Habitacion)habitaciones.obtenerInformacion(codigo));
            
            resultado = "Modificacion exitosa.";
            escribir("El equipo "+ actual.getNombre() + " entro a la habitacion " + codigo + ".\n\n");
            escritor.flush();                      
        }
        else
        {
            resultado = "La habitacion no existe.\n";
        }
        
        return resultado;
    }
    
    public static void pasoDeHabitacion(Equipo aModificar,int nuevoCodigo,Habitacion antigua,
    Habitacion nueva)
    {
        /*
        Este metodo realiza el paso de habitacion de aModificar,de antigua a
        nueva.Es posible que antigua no contenga habitacion alguna.En tal caso,
        se considera que el equipo aun no estaba dentro de la Escape House.
        */
        
        if(antigua != null)
        {
            antigua.restarEquipo();           
        }
        
        nueva.sumarEquipo();
        aModificar.setClaveHabtActual(nuevoCodigo);
    }
    
    public static String advertencia()
    {
        /*
        Advertencia.Modularizada para evitar volver pesado el codigo donde se
        necesite usarla.Mas informacion al respecto en el word de Manual de usuario.
        */
        return "\nEl puntaje total no coincide a nivel desarrollo."
                        + "Recuerde que el puntaje total \nse actualiza simultaneamente"
                        + " al puntaje actual,tal que\nel puntaje total es igual a"
                        + " todo el puntaje conseguido hasta el momento.\nPor lo tanto,"
                        + " el puntaje total siempre sera mayor o igual al puntaje actual.\n";
    }
    
    public static void desafiosABM() throws IOException
    {
        /*
        Menu ABM de desafios.
        */
        
        int puntaje,opcion;
        Desafio aModificar;
        
        System.out.println("Ingrese el puntaje del desafio: ");
        puntaje = Math.abs(TecladoIn.readLineInt());
        aModificar = (Desafio) desafios.obtenerInformacion(puntaje);
      
        if(aModificar != null)
        {
            System.out.println("\nQue desea hacer?...");
            System.out.println("1. Eliminar desafio.");
            System.out.println("2. Modificar nombre.");
            System.out.println("3. Modificar tipo.\n");
            opcion = TecladoIn.readLineInt();
        
            switch(opcion)
            {
                case 1:
                    System.out.println(eliminar(aModificar));        
                    break;
                    
                case 2:
                    primeraMod(aModificar);
                    break;
                    
                case 3:
                    segundaMod(aModificar);
                    break;       
            }
        }
        else
        {
            System.out.println("El desafio no existe.Creela completando la"
                + " siguiente estructura: \nnombre;tipo");
            tokenizer = new StringTokenizer(TecladoIn.readLine(),";");
                    
            insertarDesafio(puntaje);
        }   
    }
    
    public static String eliminar(Desafio actual) throws IOException
    {
        /*
        Metodo sobrecargado que elimina el objeto actual.
        */
        
        escribir("Se elimino el desafio con puntaje " + actual.getPuntaje() + ".\n\n");
        desafios.eliminar(actual.getPuntaje());
        return "Eliminacion exitosa.";
    }      
    
    public static void primeraMod(Desafio actual) throws IOException
    {
        /*
        Metodo sobrecargado que realiza la primera modificacion del respectivo menu
        ABM del objeto actual.       
        */
        
        System.out.println("Ingrese el nuevo nombre: ");                 
        actual.setNombre(TecladoIn.readLine());
        escribir("El desafio con puntaje"+ actual.getPuntaje() + " cambio de nombre a " + actual.getNombre() + ".\n\n");
    }
    
    public static void segundaMod(Desafio actual) throws IOException
    {
        /*
        Metodo sobrecargado que realiza la segunda modificacion del respectivo menu
        ABM del objeto actual.       
        */
        
        System.out.println("Ingrese el caracter correspondiente:\n"
            + "L:logico\nM:matematico\nD:Destreza\nB:busqueda\nI:indefinido");                 
        actual.setTipo(aTipoPermitido(TecladoIn.readLineNonwhiteChar()));
                    
       escribir("El desafio con puntaje "+ actual.getPuntaje() + " cambio de tipo a " + actual.getTipo() + ".(inicial)\n\n");
    }
    
    public static void habitacionesABM() throws IOException
    {
        /*
        Menu ABM de habitaciones.
        */
        
        int codigo,opcion;
        Habitacion aModificar;
        
        System.out.println("Ingrese el codigo de la habitacion:");
        codigo = Math.abs(TecladoIn.readLineInt());
        aModificar = (Habitacion) habitaciones.obtenerInformacion(codigo);
        
        if(aModificar != null)
        {
            System.out.println("\nQue desea hacer?...");
            System.out.println("1. Eliminar habitacion.");
            System.out.println("2. Modificar nombre.");
            System.out.println("3. Modificar planta.");
            System.out.println("4. Modificar metros cuadrados.");
            System.out.println("5. Modificar si tiene salida al exterior.");
            System.out.println("6. Agregar puerta.\n");
            opcion = TecladoIn.readLineInt();
        
            switch(opcion)
            {
                case 1:                    
                    System.out.println(eliminar(aModificar));
                    break;
                    
                case 2:
                    primeraMod(aModificar);
                    break;
                    
                case 3:
                    segundaMod(aModificar);
                    break;
                    
                case 4:
                    terceraMod(aModificar);
                    break;
                
                case 5:
                    cuartaMod(aModificar);
                    break;
                    
                case 6:
                    quintaMod(aModificar);
                    break;
            }
        }
        else
        {
            System.out.println("La habitacion no existe.Creela completando la"
                + " siguiente estructura: \nnombre;planta;metrosCuadrados;"
                + "tieneSalida?true/false");
            tokenizer = new StringTokenizer(TecladoIn.readLine(),";");
                    
            insertarHabitacion(codigo);
        }   
    }
    
    public static String quintaMod(Habitacion actual) throws IOException
    {
        /*
        Metodo sobrecargado que realiza la quinta modificacion del respectivo menu
        ABM del objeto actual.       
        */
        
        int puntaje;
        Habitacion aux;
        String resultado;
        
        System.out.println("Ingrese el puntaje necesario para abrir la puerta: ");
        puntaje = Math.abs(TecladoIn.readLineInt());
        System.out.println("Ingrese el codigo de la 2da habitacion: ");
        aux = (Habitacion) habitaciones.obtenerInformacion(TecladoIn.readLineInt());
                    
        if(plano.insertarArco(actual,aux, puntaje))
        {
            escribir("Se agrego puerta entre habitacion " + actual.getCodigo() + "y habitacion " + aux.getCodigo() +"\n\n");
            resultado = "La puerta fue agregada exitosamente.\n\n";
        }
        else
        {
            resultado = "La puerta no pudo ser agregada.\n\n";
        }
        
        return resultado;
    }
    
    public static void cuartaMod(Habitacion actual) throws IOException
    {
        /*
        Metodo sobrecargado que realiza la cuarta modificacion del respectivo menu
        ABM del objeto actual.       
        */
        
        System.out.println("Ingrese true si tiene salida al exterior,\ny "
            + "false si no la tiene: ");                 
        actual.setTieneSalida(TecladoIn.readLineBoolean());
        escribir("La habitacion "+ actual.getCodigo() + " ahora tiene salida al exterior: " + actual.getTieneSalida()+ ".\n\n");
    }    
    
    public static void terceraMod(Habitacion actual) throws IOException
    {
        /*
        Metodo sobrecargado que realiza la tercera modificacion del respectivo menu
        ABM del objeto actual.       
        */
        
        System.out.println("Ingrese la nueva medida en metros cuadrados: ");                 
        actual.setM2(TecladoIn.readLineInt());
        escribir("La habitacion "+ actual.getCodigo() + " ahora mide " + actual.getM2()+ " metros cuadrados.\n\n");
    }
    
    public static void segundaMod(Habitacion actual) throws IOException
    {
        /*
        Metodo sobrecargado que realiza la segunda modificacion del respectivo menu
        ABM del objeto actual.       
        */
        
        System.out.println("Ingrese la nueva planta: ");                 
        actual.setPlanta(TecladoIn.readLineInt());
        escribir("La habitacion "+ actual.getCodigo() + " cambio de planta a " + actual.getPlanta() + ".\n\n");
    }
    
    public static void primeraMod(Habitacion actual) throws IOException
    {
        /*
        Metodo sobrecargado que realiza la primera modificacion del respectivo menu
        ABM del objeto actual.       
        */
        
        System.out.println("Ingrese el nuevo nombre: ");                 
        actual.setNombre(TecladoIn.readLine());
        escribir("La habitacion "+ actual.getCodigo() + " cambio de nombre a " + actual.getCodigo() + ".\n\n");
    }
    
    public static String eliminar(Habitacion actual) throws IOException
    {
        /*
        Metodo sobrecargado que elimina el objeto actual.
        */
        String resultado;
        
        if(actual.getCantEquipos() == 0)
        {
            escribir("Se elimino la habitacion " + actual.getCodigo() + ".\n\n");
            resultado = "Eliminacion exitosa.";
            
            plano.eliminarVertice(habitaciones.obtenerInformacion(actual.getCodigo()));
            habitaciones.eliminar(actual.getCodigo());                 
        }
        else
        {
            resultado = "No puede eliminar una habitacion con equipos dentro.";
        }
        
        return resultado;
    }      
    
    public static void cargaInicialSistema() throws FileNotFoundException, IOException
    {
        /*
        Metodo encargada de la carga incial del sistema.
        */
        
        BufferedReader lector = new BufferedReader(new FileReader("C:\\Users\\54299\\Desktop\\Facultad\\2do anio\\Estructuras de datos\\TPOFinal\\cargaDatos.txt"));
        String objeto;
        
        while((objeto = lector.readLine()) != null)
        {
            tokenizer = new StringTokenizer(objeto,";");
            
            switch(tokenizer.nextToken())
            {
                case "H":
                    insertarHabitacion(Integer.parseInt(tokenizer.nextToken()));   
                    break;
                 
                case "E":
                    insertarEquipoJugando(tokenizer.nextToken());
                    
                    break;
                    
                case "D":
                    insertarDesafio(Integer.parseInt(tokenizer.nextToken()));
                    break;
                  
                case "P":
                    insertarPuerta();                     
                    break;
            }
        }
        
        System.out.println("Se ha realizado la carga inicial del sistema.");
    }
    
    public static boolean insertarPuerta() throws IOException
    {
        /*
        Retorna un boolean dependiendo de si la insercion de puerta fue posible.
        */
        
        boolean exito;
        Habitacion 
        origen =(Habitacion) habitaciones.obtenerInformacion(Integer.parseInt(tokenizer.nextToken())),
        destino = (Habitacion) habitaciones.obtenerInformacion(Integer.parseInt(tokenizer.nextToken()));
        
        if(origen != null && destino != null)
        {
            exito = true;
            plano.insertarArco(origen,destino,Integer.parseInt(tokenizer.nextToken()));
            escribir("Se creo puerta entre " + origen.getCodigo() + " y " + destino.getCodigo() + ".\n\n");
        }
        else
        {
            exito = false;
        }
        
        return exito;
    }
    
    public static void insertarDesafio(int puntaje) throws IOException
    {
        /*
        Este metodo inserta un desafio.
        */
        
        Desafio creacion = new Desafio(puntaje,tokenizer.nextToken(),aTipoPermitido(tokenizer.nextToken().charAt(0)));

        desafios.insertar(creacion.getPuntaje(), creacion);
        escribir("Se creo desafio " + creacion.getNombre() + ", puntaje " + puntaje + ".\n\n");
    }
    
    
    public static boolean insertarEquipoJugando(String nombre) throws IOException
    {
        /*
        Este metodo sobrecargado retorna un boolean dependiendo de si se pudo insertar
        el equipo.Este equipo se crea con codigo de habitacion incluido,por lo que
        se lo considera que ya se encuentra jugando.
        Usado para la carga inicial del sistema.
        */
        
        boolean exito;
        Equipo creacion;
        int puntajeDeSalida =Integer.parseInt(tokenizer.nextToken()),
            puntajeTotal = Integer.parseInt(tokenizer.nextToken()),
            puntajeActual = Integer.parseInt(tokenizer.nextToken()),       
            codigo = Math.abs(Integer.parseInt(tokenizer.nextToken()));
            
        nombre = nombre.toUpperCase();
        
        if(puntajeTotal < puntajeActual)           
        {
            exito = false;
        }
        else
        {
            exito = true;           
            creacion = new Equipo(nombre,puntajeDeSalida,puntajeTotal,
            puntajeActual);
            pasoDeHabitacion(creacion,codigo,null,(Habitacion)habitaciones.obtenerInformacion(codigo));            
            
            equipos.insertar(nombre, creacion);
            escribir("Se creo equipo " + nombre + ".\n\n");      
        }       
        return exito;
    }
    
    public static boolean insertarEquipo(String nombre) throws IOException
    {
        /*
        Este metodo sobrecargado retorna un boolean dependiendo de si se pudo insertar
        el equipo.
        Usado en la insercion manual del usuario.
        */
        
        boolean exito;
        Equipo creacion;
        int puntajeDeSalida =Integer.parseInt(tokenizer.nextToken()),
            puntajeTotal = Integer.parseInt(tokenizer.nextToken()),
            puntajeActual = Integer.parseInt(tokenizer.nextToken());        
        
        nombre = nombre.toUpperCase();
        
        if(puntajeTotal < puntajeActual)           
        {
            exito = false;
        }
        else
        {
            exito = true;           
            creacion = new Equipo(nombre,puntajeDeSalida,puntajeTotal, puntajeActual);
            
            equipos.insertar(nombre, creacion);
            escribir("Se creo equipo " + nombre + ".\n\n");      
        }       
        return exito;
    }
    
    public static void insertarHabitacion(int codigo) throws IOException
    {
        /*
        Este metodo inserta una habitacion.
        */
        Habitacion creacion = new Habitacion(codigo,
                            tokenizer.nextToken(),
                            Integer.parseInt(tokenizer.nextToken()),
                            Integer.parseInt(tokenizer.nextToken()),
                            Boolean.parseBoolean(tokenizer.nextToken()));
        
        habitaciones.insertar(codigo, creacion);
        plano.insertarVertice(creacion);
        
        escribir("Se creo habitacion " + codigo + ", nombre " +creacion.getNombre()+ ".\n\n");      
    }
    
        
    public static Equipo buscarEquipo()
    {
        /*
        Este metodo busca un equipo,y lo retorna.
        */
               
        System.out.println("Ingrese el nombre del equipo: ");
        return (Equipo) equipos.obteneInformacion(TecladoIn.readLine().toUpperCase());
    }
    
    public static Habitacion buscarHabitacion()
    {
        /*
        Este metodo busca una habitacion,y lo retorna.
        */
                
        System.out.println("Ingrese el codigo de la habitacion: ");
        return (Habitacion) habitaciones.obtenerInformacion(Math.abs(TecladoIn.readLineInt()));      
    }
        
    public static Desafio buscarDesafio()
    {
        /*
        Este metodo busca un desafio,y lo retorna.
        */
        
        System.out.println("Ingrese el puntaje del desafio: ");
        return (Desafio) desafios.obtenerInformacion(Math.abs(TecladoIn.readLineInt()));
    }
        
   public static char aTipoPermitido(char tipo)
   {
       /*
       Este metodo transforma la inicial del tipo del desafio ,ingresada por parametro,a una
       de tipo permitido.

       */
       
       tipo = Character.toUpperCase(tipo);
       if(tipo != 'M' && tipo != 'L' && tipo != 'B' && tipo != 'D' && tipo != 'I')
       {
            tipo = 'I';
       }
       
       return tipo;
   }
}
