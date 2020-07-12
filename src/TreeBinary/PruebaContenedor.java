package TreeBinary;

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;

public class PruebaContenedor {

    public static void main(String[] args) {


        //Iniciamos las pruebas de funcionamiento
        pruebaInsertar();
        pruebaExtraer();
        pruebaBuscar();
        pruebaCardinal();
        pruebaVaciar();
        pruebaElementos();
        //Iniciamos las pruebas de rendimiento
        pruebas();
    }

    //Pruebas de rendimiento
    public static void pruebas() {
        ContenedorDeEnteros a = new ContenedorDeEnteros();
        RandomAccessFile fichero;
        RandomAccessFile ficheroNo;
        FileWriter salida;
        long startTime;
        StringBuilder resultados = new StringBuilder();
        int[] datos = new int[100000];
        int[] datosNo = new int[20000];
        DecimalFormat formato1 = new DecimalFormat("#0.0000");

        try {
            //Abrimos los ficheros
            fichero = new RandomAccessFile("datos.dat", "r");
            ficheroNo = new RandomAccessFile("datos_no.dat", "r");
            salida = new FileWriter("salida3.txt");
            salida.write("Práctica 3");
            //Añadimos a los vectores los datos de los fichero datos.dat y datos_no.dat

            for (int i = 0; i < 100000; i++) {
                datos[i] = fichero.readInt();
            }

            for (int i = 0; i < 20000; i++) {
                datosNo[i] = ficheroNo.readInt();
            }

            //Cerramos los ficheros de lectura
            fichero.close();
            ficheroNo.close();

            //Primera prueba. Inserciones
            System.out.println("Primera Prueba inserciones\r\ntiempo/1000");

            for (int i = 0; i < 10; i++) {
                startTime = System.nanoTime();
                for (int j = 0; j < 10000; j++) {
                    a.insertar(datos[j+(i*10000)]);
                }
                resultados.append("\r\n").append(formato1.format(((System.nanoTime() - startTime) / 10.) / 1.E6));
            }

            System.out.print (resultados);
            salida.write("Primera Prueba Inserciones\r\ntiempo/1000");
            salida.write("\r\n" + resultados);

            //Segunda prueba. Extracciones
            resultados = new StringBuilder();
            System.out.println("\r\n\r\nSegunda Prueba extracciones\r\ntiempo/1000");

            for (int i = 0; i < 10; i++) {
                startTime = System.nanoTime();
                for (int j = 0; j < 10000; j++) {
                    a.extraer(datos[j+(i*10000)]);

                }

                resultados.append("\r\n").append(formato1.format((System.nanoTime() - startTime) / 10. / 1.E6));
            }

            System.out.print (resultados);
            salida.write("\r\n\r\nSegunda Prueba Extracciones\r\ntiempo/1000");
            salida.write(resultados.toString());

            //Tercera prueba. Búsqueda exitosa
            System.out.println("\r\n\r\nTercera Prueba búsquedas\r\ntiempo/1000");
            resultados = new StringBuilder();
            a.vaciar();

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10000; j++) {
                    a.insertar(datos[j+(i*10000)]);
                }
                startTime = System.currentTimeMillis();
                for (int k = 0; k < 100; k++) {
                    for (int j = 0; j < 10000 * (i + 1); j++ ) {
                        a.buscar(datos[j]);
                    }
                }
                resultados.append("\r\n").append(formato1.format(((System.currentTimeMillis() - startTime) / 100.) / (10. * (i + 1))));
            }

            System.out.print (resultados);
            salida.write("\r\n\r\nTercera Prueba Búsqueda Exitosa\r\ntiempo/1000");
            salida.write(resultados.toString());

            System.out.println("\r\n\r\nCuarta Prueba búsquedas\r\ntiempo/1000");
            resultados = new StringBuilder();

            a.vaciar();

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10000; j++) {
                    a.insertar(datos[j+(i*10000)]);
                }
                startTime = System.currentTimeMillis();
                for (int k = 0; k < 100; k++) {
                    for (int j = 0; j < 20000; j++ ) {
                        a.buscar(datosNo[j]);
                    }
                }
                resultados.append("\r\n").append(formato1.format(((System.currentTimeMillis() - startTime) / 100.) / 20.));
            }

            System.out.print (resultados);
            salida.write("\r\n\r\nCuarta Prueba BusquedaInfructuosa\r\ntiempo/1000");
            salida.write(resultados.toString());
            salida.close();
        } catch (IOException ex) { //control de exepciones para la E/S
            System.out.println(ex.getMessage());
        }
    }


    //Pruebas de funcionamiento
    public static void pruebaInsertar() {
        ContenedorDeEnteros a = new ContenedorDeEnteros();
        if (a.cardinal() != 0) {
            System.out.println("No se crea el contenedor vacio");
        }
        a.insertar(0);a.insertar(1);a.insertar(5);a.insertar(3);a.insertar(6);a.insertar(2);
        if (a.cardinal() != 6) {
            System.out.println("No se cuenta las inserciones correctamente");
        }
        a.insertar(0);a.insertar(1);a.insertar(5);a.insertar(3);
        if (a.cardinal() != 6) {
            System.out.println("Se insertan duplicados");
        }
        if (a.insertar(5)) {
            System.out.println("Se insertan duplicados");
        }
        if (!a.insertar(575)) {
            System.out.println("No se insertan valores nuevos");
        }
    }

    public static void pruebaExtraer() {
        ContenedorDeEnteros a = new ContenedorDeEnteros();
        a.insertar(1);a.insertar(0);a.insertar(5);a.insertar(3);a.insertar(6);a.insertar(2);

        if (!a.extraer(1)) {
            System.out.println("No se extrae la raiz");
        }
        if (a.cardinal() != 5) {
            System.out.println("No se contabiliza la extraccion de la raiz");
        }
        if (!a.extraer(6)) {
            System.out.println("No se extrae una hoja");
        }
        if (a.cardinal() != 4) {
            System.out.println("No se contabiliza la extraccion de la hoja");
        }
        if (a.extraer(45675)) {
            System.out.println("se extrae un valor que no existe");
        }
        if (a.cardinal() != 4) {
            System.out.println("se contabiliza la extraccion de un nodo inexistente");
        }
        a.vaciar();
        if (a.extraer(45675)) {
            System.out.println("se extrae un valor que no existe (contenedor vacio)");
        }
        if (a.cardinal() != 0) {
            System.out.println("se contabiliza la extraccion de un nodo inexistente (vacio)");
        }
    }

    public static void pruebaBuscar() {
        ContenedorDeEnteros a = new ContenedorDeEnteros();
        a.insertar(1);a.insertar(0);a.insertar(5);a.insertar(3);a.insertar(6);a.insertar(2);a.insertar(4);

        if (!a.buscar(1)) {
            System.out.println("No encuentra la raiz");
        }
        if (a.buscar(567)) {
            System.out.println("encuentra valores que no existen");
        }
        if (!a.buscar(6)) {
            System.out.println("No encuentra nodos hoja");
        }
        if (!a.buscar(3)) {
            System.out.println("No encuentra nodos intermedios");
        }
        a.extraer(3);
        if (a.buscar(3)) {
            System.out.println("Encuentra un valor extraido (Simetrico)");
        }
        a.extraer(6);
        if (a.buscar(6)) {
            System.out.println("Encuentra un valor extraido (hoja)");
        }
        a.extraer(4);
        if (a.buscar(4)) {
            System.out.println("Encuentra un valor extraido (intermedio)");
        }
    }

    public static void pruebaCardinal() {
        ContenedorDeEnteros a = new ContenedorDeEnteros();
        a.insertar(1);a.insertar(0);a.insertar(5);a.insertar(3);a.insertar(6);a.insertar(2);a.insertar(4);
        if (a.cardinal() != 7) {
            System.out.println("No se inserta correctamente");
            return;
        }
        a.extraer(1);
        if (a.cardinal() != 6) {
            System.out.println("fala al extraer la raiz");
            return;
        }
        a.extraer(6);
        if (a.cardinal() != 5) {
            System.out.println("falla al extraer una hoja");
            return;
        }
        a.vaciar(); a.insertar(0); a.extraer(0);
        if (a.cardinal() != 0) {
            System.out.println("falla al extraer el ultimo nodo");
        }
    }

    public static void pruebaVaciar() {
        ContenedorDeEnteros a = new ContenedorDeEnteros();
        a.insertar(1);a.insertar(0);a.insertar(5);a.insertar(3);a.insertar(6);a.insertar(2);a.insertar(4);
        a.vaciar();
        if (a.cardinal() != 0) {
            System.out.println("No se vacia");
            return;
        }
        a.extraer(1);
        if (a.cardinal() != 0) {
            System.out.println("falla extraer con contenedor vacio");
        }
    }


    public static void pruebaElementos () {
        ContenedorDeEnteros a = new ContenedorDeEnteros();
        a.insertar(1);a.insertar(0);a.insertar(5);a.insertar(3);a.insertar(6);a.insertar(2);a.insertar(4);
        int [] pr = a.elementos();
        if (pr.length != 7) {
            System.out.println("No se recorren todos los elementos");
        }
        for (int i = 0; i < pr.length ; i++ ) {
            if (pr[i] != i) {
                System.out.println("No se ordenan los valores correctamente");
                return;
            }
        }
        a.vaciar();
        pr = a.elementos();
        if (pr.length != 0) {
            System.out.println("No se vacia correctamente el contenedor");
        }
    }


}