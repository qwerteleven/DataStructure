package TreeB;

import java.io.*;
import java.text.DecimalFormat;

public class PruebaContenedor {
    public static void main(String[] args) throws Exception {
        funcionamiento();
        pruebas();
    }

    public static void pruebas() throws Exception{
        int[] ordenes = {5,7,9,11,20,25,55,75,105,201,301};
        int[] datos = new int[100000];
        int[] datosNo = new int[20000];
        ContenedorDeEnteros a = new ContenedorDeEnteros();
        long startTime;
        StringBuilder resultados;
        DecimalFormat formato1 = new DecimalFormat("#0.0000");//formato 3 decimales

        try {
            //Abrimos los ficheros
            RandomAccessFile fichero = new RandomAccessFile("datos.dat", "r");
            RandomAccessFile ficheroNo = new RandomAccessFile("datos_no.dat", "r");
            PrintStream salida = new PrintStream(new File("salida4.txt"));

            //Añadimos a los vectores los datos de los fichero datos.dat y datos_no.dat
            for (int i = 0; i<100000; i++) {
                datos[i] = fichero.readInt();
            }
            for (int i = 0; i<20000; i++) {
                datosNo[i] = ficheroNo.readInt();
            }
            //Cerramos los ficheros de lectura
            ficheroNo.close();
            fichero.close();

            for (int ordene : ordenes) {
                a.crear("contenedor", ordene);
                salida.println("-> Orden " + ordene);
                System.out.println("-> Orden " + ordene);
                resultados = new StringBuilder();

                //Primera prueba. Inserciones
                System.out.println("Primera Prueba inserciones\r\ntiempo/1000");

                for (int i = 0; i < 10; i++) {
                    startTime = System.nanoTime(); //tiempo de inicio de la prueba
                    for (int j = 0; j < 10000; j++) {
                        a.insertar(datos[j + (i * 10000)]);
                    }
                    resultados.append("\r\n").append(formato1.format(((System.nanoTime() - startTime) / 10.) / 1.E6));
                }

                System.out.print(resultados);
                salida.println("Orden: " + ordene + "Primera Prueba Inserciones\r\ntiempo/1000");
                salida.println("\r\n" + resultados);

                //Segunda prueba. Extracciones
                resultados = new StringBuilder();
                System.out.println("\r\n\r\nSegunda Prueba extracciones\r\ntiempo/1000");

                for (int i = 0; i < 10; i++) {
                    startTime = System.nanoTime();//tiempo de inicio
                    for (int j = 0; j < 10000; j++) {
                        a.extraer(datos[j + (i * 10000)]);

                    }

                    resultados.append("\r\n").append(formato1.format((System.nanoTime() - startTime) / 10. / 1.E6));
                }

                System.out.println(resultados);
                salida.println("\r\n\r\nSegunda Prueba Extracciones\r\ntiempo/1000");
                salida.println(resultados);

                //Tercera prueba. Búsqueda exitosa
                System.out.println("\r\n\r\nTercera Prueba búsquedas\r\ntiempo/1000");
                resultados = new StringBuilder();
                a.vaciar();

                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10000; j++) {
                        a.insertar(datos[j + (i * 10000)]);
                    }
                    startTime = System.currentTimeMillis();
                    //for (int k = 0; k < 100; k++) {
                    for (int j = 0; j < 10000 * (i + 1); j++) {
                        a.buscar(datos[j]);
                    }
                    //}
                    resultados.append("\r\n").append(formato1.format(((System.currentTimeMillis() - startTime)) / (10. * (i + 1))));
                }

                System.out.println(resultados);
                salida.println("\r\n\r\nTercera Prueba Búsqueda Exitosa\r\ntiempo/1000");
                salida.println(resultados);

                System.out.println("\r\n\r\nCuarta Prueba búsquedas\r\ntiempo/1000");
                resultados = new StringBuilder();

                a.vaciar();

                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10000; j++) {
                        a.insertar(datos[j + (i * 10000)]);
                    }
                    startTime = System.currentTimeMillis();
                    //for (int k = 0; k < 100; k++) {
                    for (int j = 0; j < 20000; j++) {
                        a.buscar(datosNo[j]);
                    }
                    //}
                    resultados.append("\r\n").append(formato1.format(((System.currentTimeMillis() - startTime)) / 20.));
                }

                System.out.println(resultados);
                salida.println("\r\n\r\nCuarta Prueba BusquedaInfructuosa\r\ntiempo/1000");
                salida.println(resultados);

            }
            salida.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void funcionamiento() throws Exception {
        boolean control;
        //-----------------------------------------------------------------------------------------------------
        //Probando: Constructor
        ContenedorDeEnteros lista = new ContenedorDeEnteros();
        ContenedorDeEnteros listavacia = new ContenedorDeEnteros();
        ContenedorDeEnteros listaUnElemento = new ContenedorDeEnteros();


        //-----------------------------------------------------------------------------------------------------
        // Probando: insertar()
        lista.crear("temp1", 5);
        listavacia.crear("temp2", 5);
        listaUnElemento.crear("temp3", 5);
        // Insertando elementos en lista vacÃ­a
        for (int i = 0; i < 10; i++) {
            lista.insertar(i);
        }

        if (lista.cardinal() != 10) {
            System.out.println("Fallo en el metodo insertar()");
        }

        // Insertando elementos repetidos
        control = lista.insertar(1);
        if (control) {
            System.out.println("Fallo en el metodo insertar(), elemento repetido ");
        }

        ///-----------------------------------------------------------------------------------------------------
        // Probando: extraer()
        // Eliminar un elemento de una lista

        control = lista.extraer(1);
        if (!control) {
            System.out.println("Fallo en el metodo Extraer() ");

        }


        //Eliminar elemento no contenido
        control = lista.extraer(11);
        if (control) {
            System.out.println("Fallo en el metodo Extraer() ");


        }
        //Eliminar un elemento de una lista vacia
        control = listavacia.extraer(1);
        if (control) {
            System.out.println("Fallo en el metodo Extraer() ");

        }

        //Eliminar primer elemento
        int[] vectorControl = {0, 2, 3, 4, 5, 6, 7, 8};
        lista.extraer(9);
        int [] v = lista.elementos();
        control = java.util.Arrays.equals(vectorControl, v);

        if (!control) {
            System.out.println("Fallo en el metodo Extraer(), al eliminar el primer elemento ");
        }

        //Eliminar Ãºltimo elemento
        int[] vectorControl2 = {2,3,4,5,6,7,8};
        lista.extraer(0);
        v = lista.elementos();
        control = java.util.Arrays.equals(vectorControl2, v);

        if (!control) {
            System.out.println("Fallo en el metodo Extraer(), al eliminar el Ãºltimo elemento ");

        }
    }
}