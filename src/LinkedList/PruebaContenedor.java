package LinkedList;

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;


public class PruebaContenedor {


    public static void main(String[] args) {

        ComprobacionFuncionamiento();

        ContenedorDeEnteros a = new ContenedorDeEnteros();
        RandomAccessFile fichero = null;
        RandomAccessFile ficheroNo;
        FileWriter salida = null;
        long startTime;
        StringBuilder resultados = new StringBuilder();
        int[] datos = new int[100000];
        int[] datosNo = new int[20000];
        DecimalFormat formato1 = new DecimalFormat("#.00");//formato 2 decimales

        try {
            fichero = new RandomAccessFile("datos.dat", "r");
            ficheroNo = new RandomAccessFile("datos_no.dat", "r");
            salida = new FileWriter("salida1.txt");

            //Añadimos a los vectores los datos de los fichero datos.dat y datos_no.dat

            for (int i = 0; i < 100000; i++) {
                datos[i] = fichero.readInt();
            }

            for (int i = 0; i < 20000; i++) {
                datosNo[i] = ficheroNo.readInt();
            }

            //Primera prueba
            System.out.println("Primera Prueba inserciones\r\ntiempo/1000");

            for (int i = 0; i < 10; i++) {
                startTime = System.currentTimeMillis(); //tiempo de inicio de la prueba
                for (int j = 0; j < 10000; j++) {
                    a.insertar(datos[j+(i*10000)]);//lee y se posiciona en el siguente
                }
                resultados.append("\r\n").append(formato1.format((System.currentTimeMillis() - startTime) / 10.));
            }

            System.out.print (resultados);
            salida.write("Primera Prueba Inserciones\r\ntiempo/1000");
            salida.write("\r\n" + resultados);


            resultados = new StringBuilder();
            fichero.seek(0);//puntero del fichero puesta a 0
            System.out.println("\r\n\r\nSegunda Prueba extracciones\r\ntiempo/1000");

            for (int i = 0; i < 10; i++) {
                startTime = System.currentTimeMillis();//tiempo de inicio
                for (int j = 0; j < 10000; j++) {
                    a.extraer(datos[j+(i*10000)]);
                }
                resultados.append("\r\n").append(formato1.format((System.currentTimeMillis() - startTime) / 10.));
            }

            System.out.print (resultados);
            salida.write("\r\n\r\nSegunda Prueba Extracciones\r\ntiempo/1000");
            salida.write(resultados.toString());

            System.out.println("\r\n\r\nTercera Prueba búsquedas\r\ntiempo/1000");
            fichero.seek(0);//puntero del fichero puesto a 0
            resultados = new StringBuilder();

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10000; j++) {
                    a.insertar(datos[j+(i*10000)]);
                }
                startTime = System.currentTimeMillis();
                for (int j = 0; j < 10000 * (i + 1); j++ ) {
                    a.buscar(datos[j]);
                }
                resultados.append("\r\n").append(formato1.format((System.currentTimeMillis() - startTime) / (10. * (i + 1))));
            }

            System.out.print (resultados);
            salida.write("\r\n\r\nTercera Prueba BusquedaExitosa\r\ntiempo/1000");
            salida.write(resultados.toString());

            System.out.println("\r\n\r\nCuarta Prueba búsquedas\r\ntiempo/1000");
            resultados = new StringBuilder();

            a.vaciar();

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10000; j++) {
                    a.insertar(datos[j+(i*10000)]);
                }
                startTime = System.currentTimeMillis();
                for (int j = 0; j < 20000; j++ ) {
                    a.buscar(datosNo[j]);
                }
                resultados.append("\r\n").append(formato1.format((System.currentTimeMillis() - startTime) / 20.));
                ficheroNo.seek(0);
            }

            System.out.print (resultados);
            salida.write("\r\n\r\nCuarta Prueba BusquedaInfructuosa\r\ntiempo/1000");
            salida.write(resultados.toString());

        } catch (IOException ex) { //control de exepciones para la E/S
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (fichero != null) {
                    fichero.close();//intenta cerrar el fichero
                    assert salida != null;
                    salida.close();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void ComprobacionFuncionamiento () {

        ContenedorDeEnteros a = new ContenedorDeEnteros();

        a.insertar(0);

        if(!a.buscar(0)) System.out.println("No se encuentran valores insertados");

        if (a.cardinal() != 1) System.out.println("No se contabiliza la primera insercion") ;

        a.insertar(7);a.insertar(4);a.insertar(3);a.insertar(3);a.insertar(0);

        if (a.cardinal() != 4)System.out.println("Problema al insertar valores ya insertados");

        a.extraer(7);a.extraer(4);

        if (a.cardinal() != 2) System.out.println("Problema al extraer");

        a.extraer(3);a.extraer(3);

        if (a.cardinal() != 1) System.out.println("Problema al extraer valores inexistentes");

        a.vaciar();

        if (a.cardinal() != 0) System.out.println ("No se vacia correctamente");

        for (int i = 0; i < 10; i++) {
            a.insertar(9 - i);
        }

        int[] aux = a.elementos();
        for (int i = 0; i < 10 ; i++) {
            if (aux[i] != i) System.out.println("El elemento" + aux[i] + "no esta");
        }

    }


}
