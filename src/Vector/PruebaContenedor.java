package Vector;

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;

public class PruebaContenedor {

    public static void main(String[] args) {
        //pruebas de funcionamiento
        pruebasFuncionamiento();
        // pruebas de rendimiento
        pruebas();
    }


    public static void pruebas() {
        ContenedorDeEnteros a = new ContenedorDeEnteros(100001);
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
            salida = new FileWriter("salida2.txt");
            salida.write("Práctica 2");
            //Añadimos a los vectores los datos de los fichero datos.dat y datos_no.dat

            for (int i = 0; i < 100000; i++) {
                datos[i] = fichero.readInt();
            }

            for (int i = 0; i < 20000; i++) {
                datosNo[i] = ficheroNo.readInt();
            }

            fichero.close();
            ficheroNo.close();

            //Primera prueba. Inserciones
            System.out.println("Primera Prueba inserciones\r\ntiempo/1000");

            for (int i = 0; i < 10; i++) {
                startTime = System.currentTimeMillis();
                for (int j = 0; j < 10000; j++) {
                    a.insertar(datos[j+(i*10000)]);
                }
                resultados.append("\r\n").append(formato1.format((System.currentTimeMillis() - startTime) / 10.));
            }

            System.out.print (resultados);
            salida.write("Primera Prueba Inserciones\r\ntiempo/1000");
            salida.write("\r\n" + resultados);

            //Segunda prueba. Extracciones
            resultados = new StringBuilder();
            System.out.println("\r\n\r\nSegunda Prueba extracciones\r\ntiempo/1000");

            for (int i = 0; i < 10; i++) {
                startTime = System.currentTimeMillis();
                for (int j = 0; j < 10000; j++) {
                    a.extraer(datos[j+(i*10000)]);
                }
                resultados.append("\r\n").append(formato1.format((System.currentTimeMillis() - startTime) / 10.));
            }

            System.out.print (resultados);
            salida.write("\r\n\r\nSegunda Prueba Extracciones\r\ntiempo/1000");
            salida.write(resultados.toString());

            //Tercera prueba. Búsqueda exitosa
            System.out.println("\r\n\r\nTercera Prueba búsquedas\r\ntiempo/1000");
            resultados = new StringBuilder();

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10000; j++) {
                    a.insertar(datos[j+(i*10000)]);
                }
                startTime = System.currentTimeMillis();
                for (int t = 0; t < 300; t++) {
                    for (int j = 0; j < 10000 * (i + 1); j++ ) {
                        a.buscar(datos[j]);
                    }
                }
                resultados.append("\r\n").append(formato1.format(((System.currentTimeMillis() - startTime) / 300.) / (10. * (i + 1))));
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
                for (int t = 0; t < 300; t++) {
                    for (int j = 0; j < 20000; j++ ) {
                        a.buscar(datosNo[j]);
                    }
                }
                resultados.append("\r\n").append(formato1.format(((System.currentTimeMillis() - startTime) / 300.) / 20.));
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
    public static void pruebasFuncionamiento() {
        ContenedorDeEnteros a = new ContenedorDeEnteros(10);

        if (a.cardinal() != 0) System.out.println("El contenedor no empieza vacio");
        if (a.buscar(0)) System.out.println("Se encuentran valores no insertados");

        a.insertar(0);a.insertar(1);

        a.vaciar();

        if(a.cardinal() != 0) System.out.println("No se vacia correctamente el contenedor");

        a.insertar(0);a.insertar(1);

        if (a.cardinal() != 2) System.out.println("No se contabiliza bien la insercion de datos");

        if (!a.buscar(0)) System.out.println("No se encuentran valores insertados");

        a.insertar(0);

        if (a.cardinal() != 2) System.out.println("Se insertan valores repetidos");

        if (a.extraer(45)) System.out.println("Se extraen valores inexistentes");

        if(!a.extraer(0)) System.out.println("no se extrae valores insertados");

        if(a.cardinal() != 1) System.out.println("No se contabiliza correctamente la extracion");


        a.insertar(5);a.insertar(3);a.insertar(6);a.insertar(2);a.insertar(0);a.insertar(4);a.insertar(7);a.insertar(8);a.insertar(9);a.insertar(10);

        if (a.insertar(55)) System.out.println("se insertan mas valores del tamaño");

        if (a.cardinal() != 10) System.out.println("Contabilizacion incorrecta al llenar el contenedor");

        int[] aux = a.elementos();

        for (int i = 0; i < 10 ; i++) {
            if (aux[i] != i) System.out.println("El valor" + aux[i] + "No se ordena correctamente");
        }

    }
}