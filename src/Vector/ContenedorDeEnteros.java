package Vector;


public class ContenedorDeEnteros {
    private final int[] vector; //vector que contiene los enteros
    private int tamano; //tamaño del contenedor

    /**
     * Constructor por parametros
     */

    public ContenedorDeEnteros(int max) {
        vector = new int[max];
        tamano = 0;
    }

    /**
     * @return numero de elementos del contenedor
     */

    public int cardinal() {
        return tamano;
    }

    /**
     * Funcion insertar
     * @return true cuando lo añade, return false en caso contrario
     * Si el contenedor esta lleno no se altera
     */

    public boolean insertar(int e) {
        if (tamano == vector.length) {
            return false;
        }
        int first = 0;
        int last = tamano -1;
        int center;

        while (last >= first) {
            center = (last+first)/2;
            if (vector[center] < e) {
                first = center+1;
            } else {
                if (vector[center] > e) {
                    last = center-1;
                } else {
                    return false;
                }
            }
        }
        if (tamano - first >= 0) System.arraycopy(vector, first, vector, first + 1, tamano - first);
        tamano++;
        vector[first] = e;
        return true;
    }

    /**
     * Funcion extraer
     * @return true si lo extrae, return false en caso contrario.
     * Si no se encuentra no se altera el contenedor
     */

    public boolean extraer(int e) {
        int first = 0;
        int last = tamano -1;
        int center;
        while (last >= first) {
            center = (last+first)/2;
            if (vector[center] < e) {
                first = center+1;
            } else {
                if (vector[center] > e) {
                    last = center-1;
                } else {
                    if (tamano - 1 - center >= 0)
                        System.arraycopy(vector, center + 1, vector, center, tamano - 1 - center);
                    tamano--;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Funcion buscar
     * @return true si el valor pasado por parametro pertenece al contenedor,return false en caso contrario
     */

    public boolean buscar(int e) {
        int first = 0;
        int last = tamano -1;
        int center;
        while (last >= first) {
            center = (last+first)/2;
            if (vector[center] < e) {
                first = center+1;
            } else {
                if (vector[center] > e) {
                    last = center-1;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Procedimiento vaciar que deja
     * el contenedor sin elementos.
     */

    public void vaciar() {
        tamano = 0;
    }

    /**
     * Funcion elementos
     * @return vector de elementos ordenados
     */

    public int[] elementos() {
        int[] res = new int[tamano];
        if (tamano >= 0) System.arraycopy(vector, 0, res, 0, tamano);
        return res;
    }
}