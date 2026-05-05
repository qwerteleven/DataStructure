package LinkedList;

public class ContenedorDeEnteros {


    private static class Nodo {
        int entero;
        Nodo siguiente;


        public Nodo(int n) {
            entero=n;
        }
    }


    private Nodo primero;


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


    public void vaciar() {
        primero=null;
    }


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