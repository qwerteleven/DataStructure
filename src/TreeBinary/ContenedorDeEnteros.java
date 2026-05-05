package TreeBinary;

public class ContenedorDeEnteros {

    private int elemento;
    private Nodo arbol;

    private Nodo padre;
    private Nodo hijo;


    private int count;
    private int[] informacion;

    private static class Nodo  {
        int info;
        Nodo[] hijos;

    

        Nodo (int info) {
            this.info = info;
            hijos = new Nodo [2];
        }
    }



    public ContenedorDeEnteros() {
        arbol = null;
    }



    public int cardinal() {
        return elemento;
    }



    public boolean insertar (int valor) {
        if (arbol == null) {
            arbol = new Nodo(valor);
            elemento ++;
            return true;
        }

        Nodo aux = arbol;
        while (true) {
            if (aux.info < valor) {
                if (aux.hijos[1] != null) {
                    aux = aux.hijos[1];
                    continue;
                } else {
                    aux.hijos[1] = new Nodo(valor);
                    break;
                }
            }
            if (aux.info > valor) {
                if (aux.hijos[0] != null) {
                    aux =  aux.hijos[0];
                    continue;
                } else {
                    aux.hijos[0] = new Nodo(valor);
                    break;
                }
            }
            return false;
        }
        elemento ++;
        return true;
    }


    public boolean extraer (int valor) {
        if (arbol == null) {
            return false;
        }
        if (arbol.info == valor) {
            eliminarRaiz();
            elemento --;
            return true;
        }

        if (!buscar(valor)) {
            return false;
        }
        elemento --;
        if (padre.info < valor) {
            if (hijo.hijos[0] == hijo.hijos[1]) {
                padre.hijos[1] = null;
                return true;
            }
            if (hijo.hijos[0] != null && null == hijo.hijos[1]) {
                padre.hijos[1] = hijo.hijos[0];
                return true;
            }
            if (hijo.hijos[0] == null) {
                padre.hijos[1] = hijo.hijos[1];
                return true;
            }
        } else {
            if (hijo.hijos[0] == hijo.hijos[1]) {
                padre.hijos[0] = null;
                return true;
            }
            if (hijo.hijos[0] != null && null == hijo.hijos[1]) {
                padre.hijos[0] = hijo.hijos[0];
                return true;
            }
            if (hijo.hijos[0] == null) {
                padre.hijos[0] = hijo.hijos[1];
                return true;
            }
        }
        simetrico(hijo);
        return true;
    }



    public boolean buscar (int valor) {
        if (arbol == null) {
            return false;
        }
        Nodo aux = arbol;
        padre = arbol;
        hijo = padre;
        while (true) {
            if (aux.info < valor) {
                if (aux.hijos[1] != null) {
                    padre = aux;
                    aux =  aux.hijos[1];
                    continue;
                } else {
                    return false;
                }
            }
            if (aux.info > valor) {
                if (aux.hijos[0] != null) {
                    padre = aux;
                    aux =  aux.hijos[0];
                    continue;
                } else {
                    return false;
                }
            }
            hijo = aux;
            return true;
        }
    }


    public void vaciar () {
        arbol = null;
        elemento = 0;
    }

  

    public int [] elementos () {
        informacion = new int [elemento];
        count = 0;
        inOrden(arbol);
        return informacion;
    }

  
    private void inOrden (Nodo raiz) {
        if (raiz == null) return;

        inOrden(raiz.hijos[0]);
        escribir(raiz.info);
        inOrden(raiz.hijos[1]);
    }

  

    private void escribir (int info) {
        informacion[count] = info;
        count ++;
    }


   
    private void eliminarRaiz() {
        if (arbol.hijos[0] ==  arbol.hijos[1]) {
            arbol = null;
            return;
        }
        if (arbol.hijos[0] != null && null == arbol.hijos[1]) {
            arbol = arbol.hijos[0];
            return;
        }
        if (arbol.hijos[0] == null) {
            arbol = arbol.hijos[1];
            return;
        }
        simetrico(arbol);
    }


    private void simetrico(Nodo raiz) {
        Nodo simetrico = raiz.hijos[1];
        Nodo padre = raiz;
        while (simetrico.hijos[0] != null) {
            padre = simetrico;
            simetrico = simetrico.hijos[0];
        }
        if (raiz == padre) {
            raiz.info = simetrico.info;
            padre.hijos[1] = simetrico.hijos[1];
        } else {
            raiz.info = simetrico.info;
            padre.hijos[0] = simetrico.hijos[1];
        }
    }
}
