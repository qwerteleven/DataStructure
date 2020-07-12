package LinkedList;

public class ContenedorDeEnteros {


    private static class Nodo {
        int entero;
        Nodo siguiente;

        /**
         * Constructor de los elementos de informacion
         */

        public Nodo(int n) {
            entero=n;
        }
    }


    private Nodo primero;

    /**
     * Constructor del Contenedor
     */
    public ContenedorDeEnteros() {
        primero = null;
    }

    /**
     * @return numero de elementos
     */
    public int cardinal() {
        int contador=0;
        Nodo aux= primero;
        while(aux!=null) {
            contador++;
            aux=aux.siguiente;
        }
        return contador;
    }

    /**
     * Introduce un dato en la estructura de informacion
     */

    public void insertar (int entero) {
        if (primero == null) {//inserta por delante
            primero = new Nodo(entero);
        } else {
            Nodo aux = primero;
            while (aux != null) {
                if (aux.entero == entero) {
                    return;
                }
                aux = aux.siguiente;
            }

            aux = primero;
            primero = new Nodo(entero);
            primero.siguiente = aux;

        }
    }

    /**
     * Extrae de la estructura un dato
     */
    public void extraer(int viejo) {
        if(primero != null) {
            if(primero.entero==viejo) {
                primero=primero.siguiente;
                return;
            }
            Nodo aux = primero;
            while(aux.siguiente != null) {
                if(aux.siguiente.entero != viejo) {
                    aux=aux.siguiente;
                } else {
                    aux.siguiente=aux.siguiente.siguiente;
                    return;
                }
            }
        }
    }

    /**
     * Busca dentro de la estructura si el dato esta
     * @return Si ha encontrado el dato
     */
    public boolean buscar(int look) {
        Nodo aux = primero;
        while(aux != null) {
            if(aux.entero != look) {
                aux=aux.siguiente;
            }else {
                return true;
            }
        }
        return false;
    }

    /**
     * Disocia la estructura de datos del contenedor
     */
    public void vaciar() {
        primero=null;
    }

    /**
     * Devuelva el contenido del contenedor
     * @return Array ordenado de los datos
     */
    public int[] elementos() {
        int [] res = new int [cardinal()];
        Nodo aux= primero;
        for(int i = 0; i<res.length;i++) {
            res[i]+=aux.entero;
            aux= aux.siguiente;
        }
        return res;
    }
}