package Vector;


public class ContenedorDeEnteros {
    private final int[] vector; //vector que contiene los enteros
    private int tamano; //tamaño del contenedor

  

    public ContenedorDeEnteros(int max) {
        vector = new int[max];
        tamano = 0;
    }



    public int cardinal() {
        return tamano;
    }



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

   

    public void vaciar() {
        tamano = 0;
    }



    public int[] elementos() {
        int[] res = new int[tamano];
        if (tamano >= 0) System.arraycopy(vector, 0, res, 0, tamano);
        return res;
    }
}