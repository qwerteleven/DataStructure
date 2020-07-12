package TreeB;

import java.util.LinkedList;
import java.util.Stack;

/**
 *
 * Clase que implementa una estructura de árbol-B que almacenará números enteros.
 */
public class ContenedorDeEnteros {
    LinkedList<InfoPila> cola = new LinkedList<>();
    int minimoClaves;
    FicheroAyuda fichero = new FicheroAyuda();
    int Orden;
    int tamanoDatos = 4;
    int raiz;
    int numElem;
    String nombreFichero;
    /**
     *
     * Clase Nodo.
     *
     */
    protected class Nodo {
        private int numElei; //Número de datos de la página
        private int direccioni; //Dirección de la página en el fichero
        private final int[] clavei;
        private final int[] enlacei;

        Nodo() {
            direccioni = fichero.dirNula;
            numElei = 0;
            //El tamaño depende de Orden del objeto donde se crea
            clavei = new int[Orden];
            enlacei = new int[Orden+1];
        }

        private int tamano() {
            int tam = 2 * Conversor.INTBYTES; //Cantidad base
            tam += (Orden-1) * tamanoDatos; //Los datos
            tam += Orden * Conversor.INTBYTES; //Los enlaces
            return tam;
        }

        private int clave(int i) throws Exception {
            //Devuelve la clave
            if(i < 1 || i > numElei) throw new Exception("Error interno del multirrama");
            return clavei[i-1];
        }

        private void clave(int i, int d) throws Exception {
            //Establece la clave
            if(i < 1 || i > numElei) throw new Exception("Error interno del multirrama");
            clavei[i-1] = d;
        }

        private int enlace(int i) throws Exception {
            //Devuelve el enlace
            if(i < 0 || i > numElei) throw new Exception("Error interno del multirrama");
            return enlacei[i];
        }

        private void enlace(int i, int d) throws Exception {
            //Establece el enlace
            if(i < 0 || i > numElei) throw new Exception("Error interno del multirrama");
            enlacei[i] = d;
        }

        private int direccion() {
            //Devuelve la dirección donde se almacena
            return direccioni;
        }

        private void direccion(int d) {
            //Establece la dirección donde se almacena
            direccioni = d;
        }

        public int cardinal() {
            //Devuelve el número de datos almacenados
            return numElei;
        }

        private void cardinal(int n) {
            //Establece el número de datos almacenados
            numElei = n;
        }

        /**
         * @param datos Vector de bytes que hemos leído del fichero
         * Al leer de un fichero, leemos vectores de bytes. Con este método
         * pasamos ese vector a un Nodo.
         */
        private void deByte(byte[] datos) {
            int leb = Conversor.INTBYTES; //Longitud de los enteros en bytes
            direccion(Conversor.aInt(Conversor.toma(datos, 0, leb)));
            numElei = Conversor.aInt(Conversor.toma(datos, leb, leb));
            int baseClaves = leb * 2;
            int baseEnlaces = baseClaves + (numElei) * tamanoDatos;

            for(int i=0; i < numElei; i++) {
                clavei[i] = Conversor.aInt(Conversor.toma(datos, baseClaves + i * tamanoDatos, tamanoDatos));
            }

            for(int i=0; i <= numElei; i++) {
                byte[] dato = Conversor.toma(datos, baseEnlaces + i * leb, leb);
                enlacei[i] = Conversor.aInt(dato);
            }
        }

        /**
         *
         * @return byte[]
         * Este método traduce un Nodo en un vector de bytes, que se usará posteriormente
         * para escribirlo en el fichero.
         * NOTA: no confundirlo con el método aByte de la clase Conversor.
         */
        byte[] abyte() {
            int tam = tamano();
            byte[] res = new byte[tam];
            int pos = 0;
            pos = Conversor.añade(res, Conversor.aByte(direccioni), pos);
            pos = Conversor.añade(res, Conversor.aByte(numElei), pos);

            for(int i=0; i < numElei; i++) {
                pos = Conversor.añade(res, Conversor.aByte(clavei[i]), pos);
            }

            for(int i=0; i <= numElei; i++) {
                pos = Conversor.añade(res, Conversor.aByte(enlacei[i]), pos);
            }
            return res;
        }

        /**
         *
         * @param e
         * @param dir
         * @param pos
         * @throws Exception
         *
         * Inserta un int en el nodo
         */
        void insertar(int e, int dir, int pos) throws Exception {
            numElei++;
            for(int i=numElei - 1; i >= pos; i--) {
                clave(i + 1, clave(i));
                enlace(i + 1, enlace(i));
            }
            clave(pos, e);
            enlace(pos, dir);
        }

        /**
         *
         * @param pos
         * @throws Exception
         *
         * Extrae un elemento de un Nodo
         */
        void extraer(int pos) throws Exception {
            for(int i = pos; i < numElei; i++) {
                clave(i, clave(i + 1));
                enlace(i, enlace(i + 1));
            }
            numElei--;
        }

        /**
         *
         * @param e
         * @return true en búsqueda exitosa o false en caso contrario
         * @throws Exception
         *
         * Busca un elemento int en el nodo
         */
        public boolean buscar(int e) throws Exception {
            int prim, ulti, med;
            prim = 1;
            ulti = cardinal();

            while(prim <= ulti) {
                med = (prim + ulti) / 2;
                if(e == clave(med)) {
                    return true;
                }
                if(e < clave(med)) {
                    ulti = med - 1;
                } else {
                    prim = med + 1;
                }
            }
            return false;
        }

        /**
         *
         * @param e
         * @return la posición donde está situado el entero "e"
         * @throws Exception
         *
         * Busca un elemento en el nodo y devuelve su posición
         */
        private int buscarPos(int e) throws Exception {
            int pos, prim, ulti, med;
            prim = 1;
            ulti = cardinal();

            while(prim <= ulti) {
                med = (prim + ulti) / 2;
                if(e == clave(med)) {
                    pos = med;
                    return pos;
                }
                if(e < clave(med)) {
                    ulti = med - 1;
                } else {
                    prim = med + 1;
                }
            }
            pos = prim-1;
            return pos;
        }
    }
    /*****************************************************************************************/
    /**
     * Clase auxiliar que permite devolver una pareja formada por
     * una clave y el enlace asociado
     */
    /**
     * @param ruta
    //*
     * @param Orden
     * @throws Exception
     *
     * Crea un fichero en memoria secundaria y le adjunta un objeto
     */
    public void crear(String ruta, int Orden) throws Exception {
        cerrar();
        this.Orden = Orden;
        if(Orden < 5) throw new Exception("Orden inferior a 5 en árbol B no está permitido");
        Nodo nodo = new Nodo();
        nombreFichero = ruta;
        fichero.crear(nombreFichero, nodo.tamano(), 4);
        raiz = fichero.dirNula;
        numElem = 0;
        minimoClaves = (Orden + 1) / 2 - 1;
        fichero.adjunto(0, raiz);
        fichero.adjunto(1, numElem);
        fichero.adjunto(2, tamanoDatos);
        fichero.adjunto(3, Orden);
    }
    /**
     *
     * @param ruta
     *
     * Abre el fichero creado con *.crear(String ruta, int Orden)
     */
    public void abrir(String ruta) {
        fichero.abrir(ruta);
        raiz = fichero.adjunto(0);
        numElem = fichero.adjunto(1);
        tamanoDatos = fichero.adjunto(2);
        Orden = fichero.adjunto(3);
        minimoClaves = (Orden + 1) / 2 - 1;
    }

    /**
     * Cierra el fichero creado con *.crear(String ruta, int Orden).
     */
    public void cerrar() {
        fichero.cerrar();
    }

    /**
     *
     * @return el número de elementos que hay en el contenedor
     */
    public int cardinal() {
        return numElem;
    }

    /**
     *
     * @param n
     * @return true en búsqueda exitosa o false en caso contrario
     * @throws Exception
     *
     * Inserta un int en el contenedor, adoptando el método de rotación/partición necesario
     */
    public boolean insertar(int n) throws Exception {
        Stack<InfoPila> pila = new Stack<InfoPila>();
        if(buscar(n, pila)) return false; //No se admiten valores repetidos
        Nodo nodoActual = new Nodo();
        InfoPila info;
        ParejaInsertar pa = new ParejaInsertar();
        pa.clave = n;
        pa.direccion = fichero.dirNula;
        fichero.adjunto(1, ++numElem);
        if(!pila.empty()) {
            //El árbol no está vacío
            info = (InfoPila) pila.pop();
            nodoActual = info.nodo;
            int pos = info.pos;
            nodoActual.insertar(pa.clave, pa.direccion, pos+1);
            if(nodoActual.cardinal() < Orden) {
                //No hay problemas
                escribir(nodoActual);
                return true;
            }

            while(!pila.empty()) {
                //Arreglamos la sobrecarga
                info = (InfoPila) pila.pop();
                Nodo der = new Nodo();
                Nodo izq = new Nodo();
                Nodo padre = info.nodo;
                pos = info.pos;
                if(pos > 0) {
                    //Tiene hermano izquierdo
                    izq = leer(padre.enlace(pos-1));
                    if(izq.cardinal() < Orden - 1) {
                        //Resuelto
                        rotacionderizq(padre, pos-1, izq, nodoActual);
                        return true;
                    }
                }
                if(pos < padre.cardinal()) {
                    //Tiene hermano derecho
                    der = leer(padre.enlace(pos+1));
                    if(der.cardinal() < Orden - 1) {
                        //Resuelto
                        rotacionizqder(padre, pos, nodoActual, der);
                        return true;
                    }
                }
                //No se puede rotar; partimos el nodo
                if(pos == 0) particion_2_3(padre, pos, nodoActual, der);
                else particion_2_3(padre, pos-1, izq, nodoActual);
                if(padre.cardinal() < Orden) {
                    //Resuelto
                    escribir(padre);
                    return true;
                }
                nodoActual = padre;
            }
            //Se parte la raiz
            pa = particion_1_2(nodoActual);
        }
        //Se crea una nueva raiz
        nodoActual.cardinal(1);
        nodoActual.enlace(0, raiz);
        nodoActual.clave(1, pa.clave);
        nodoActual.enlace(1, pa.direccion);
        nodoActual.direccion(fichero.tomarPágina());
        raiz = nodoActual.direccion();
        escribir(nodoActual);
        fichero.adjunto(0, raiz);
        return true;
    }

    /**
     *
     * @param n
     * @return true en búsqueda exitosa o false en caso contrario
     * @throws Exception
     *
     * Extrae un entero del contenedor, siguiendo los métodos de recombinación/rotación necesarios.
     */
    public boolean extraer(int n) throws Exception {
        Stack<InfoPila> pila = new Stack<InfoPila>();
        if(!buscar(n, pila)) return false; //Si no está, retornamos false
        fichero.adjunto(1, --numElem);
        Nodo nodoActual = new Nodo();
        InfoPila info = (InfoPila) pila.pop();
        nodoActual = info.nodo;
        int pos = info.pos;

        if(nodoActual.enlace(0) != fichero.dirNula) {
            //Extracción desde un nodo no hoja
            pila.add(new InfoPila(info.nodo, info.pos));
            //Buscamos el sucesor en simétrico y lo cambiamos
            int dir = nodoActual.enlace(pos);
            do {
                //Descendemos por las ramas izquierdas
                nodoActual = leer(dir);
                dir = nodoActual.enlace(0);
                if(dir == fichero.dirNula) pos = 1;
                else pos = 0;
                //Guardamos el camino en una cola
                cola.addLast(new InfoPila(nodoActual, pos));
            } while(dir != fichero.dirNula);
            info = (InfoPila) pila.pop();
            //Se sustituye por el sucesor
            info.nodo.clave(info.pos, nodoActual.clave(1));
            //Se escribe por si no hay más modificaciones
            escribir(info.nodo);
            pila.add(info);
            while(!cola.isEmpty()) {
                //Se pasa el camino de la cola a la pila
                nodoActual =((InfoPila) cola.getFirst()).nodo;
                pila.add(cola.getFirst());
                cola.removeFirst();
            }
            info = (InfoPila) pila.pop();
            nodoActual = info.nodo;
            pos = info.pos;
        }
        //Extracción en un nodo hoja
        nodoActual.extraer(pos);
        while(nodoActual.cardinal() < minimoClaves && nodoActual.direccion() != raiz) {
			/*Nodo padre = new Nodo();
			Nodo der = new Nodo();
			Nodo izq = new Nodo();*/
            Nodo padre, der = new Nodo(), izq = new Nodo();
            info = (InfoPila) pila.pop();
            padre = info.nodo; //Se toma el padre de la pila
            pos = info.pos;
            if(pos < padre.cardinal()) {
                //Tiene hermano derecho
                der = leer(padre.enlace(pos + 1));
                if(der.cardinal() > minimoClaves) {
                    rotacionderizq(padre, pos, nodoActual, der);
                    return true; //sobra
                }
            }
            if(pos > 0) {
                //Tiene hermano izquierdo
                izq = leer(padre.enlace(pos - 1));
                if(izq.cardinal() > minimoClaves) {
                    rotacionizqder(padre, pos-1, izq, nodoActual);
                    return true; //sobra
                }
            }
            //No se puede rotar; recombinamos nodos
            if(pos > 0 && pos < padre.cardinal()) {
                recombinacion_3_2(padre, pos, izq, nodoActual, der);
            } else if(pos > 0) {
                recombinacion_2_1(padre, pos-1, izq, nodoActual);
            } else {
                recombinacion_2_1(padre, pos, nodoActual, der);
            }
            nodoActual = padre;
        }
        if(nodoActual.cardinal() > 0) {
            //Se escribe el nodo, si tiene información
            escribir(nodoActual);
        } else {
            //La raíz se ha quedado sin datos
            raiz = nodoActual.enlace(0);
            fichero.liberarPágina(nodoActual.direccion());
            fichero.adjunto(0, raiz);
        }
        return true;
    }


    /**
     *
     * @param n
     * @return true en búsqueda exitosa o false en caso contrario
     * @throws Exception
     *
     * Busca un int en el contenedor.
     */
    public boolean buscar(int n) throws Exception {
        return buscar(n, new Stack<InfoPila>());
    }


    /**
     *
     * @throws Exception
     *
     * Vacía de elementos el contenedor.
     */
    public void vaciar() throws Exception {
        fichero.cerrar();
        crear(nombreFichero, Orden);
    }

    private int recorridoInorden(Nodo p, int[] v, int i) throws Exception{
        if (p.enlace(0) == fichero.dirNula){
            for (int j=1;j<=p.cardinal();j++)
                v[i++] = p.clave(j);
            return i;
        }
        else
            i = recorridoInorden(leer(p.enlace(0)),v,i);
        for (int j=1;j<=p.cardinal();j++){
            v[i++] = p.clave(j);
            if (p.enlace(j) != fichero.dirNula){
                Nodo nodo = leer(p.enlace(j));
                i = recorridoInorden(nodo,v,i);
            }
        }
        return i;
    }

    /**
     * Devuelve un vector "v" con los elementos en el arbol b, ordenados
     * de menor a mayor
     * @return int[] v: Vector que contiene los elementos ordenados
     * @throws java.lang.Exception
     */
    public int[] elementos() throws Exception{
        int[] v = new int[this.cardinal()];
        int j = 0;
        Nodo nodo = leer(raiz);
        recorridoInorden(nodo,v,j);
        return v;
    }

    private class ParejaInsertar {
        public int clave;
        public int direccion;
    }


    /**
     *
     * @param e
     * @param pila
     * @return true en búsqueda exitosa o false en caso contrario
     * @throws Exception
     */
    public boolean buscar(int e, Stack<InfoPila> pila) throws Exception {
        int dirNodo, pos;
        Nodo nodo = new Nodo();
        dirNodo = raiz;
        pila.clear();

        while(dirNodo != fichero.dirNula) {
            nodo = leer(dirNodo);
            pos = nodo.buscarPos(e);
            pila.add(new InfoPila(nodo, pos));
            if(nodo.buscar(e)) return true;
            dirNodo = nodo.enlace(pos);
        }
        return false;
    }



    /**
     *
     * @param nodo
     * @return
     * @throws Exception
     *
     * Partición 1/2. Se usará sólamente cuando el nodo no tenga hermanos
     */
    private ParejaInsertar particion_1_2(Nodo nodo) throws Exception {
        ParejaInsertar pa = new ParejaInsertar();
        Nodo nuevoNodo = new Nodo();
        int ncnuevo = Orden / 2;
        int ncnodo = Orden - ncnuevo - 1;
        int dirNuevo = fichero.tomarPágina();
        nuevoNodo.direccion(dirNuevo);
        nuevoNodo.cardinal(ncnuevo);
        nuevoNodo.enlace(0, nodo.enlace(ncnodo+1));

        for(int i=1; i <= nuevoNodo.cardinal(); i++) {
            nuevoNodo.clave(i, nodo.clave(ncnodo + 1 + i));
            nuevoNodo.enlace(i, nodo.enlace(ncnodo + 1 + i));
        }
        pa.clave = nodo.clave(ncnodo + 1);
        pa.direccion = nuevoNodo.direccion();
        nodo.cardinal(ncnodo);
        escribir(nodo);
        escribir(nuevoNodo);
        return pa;
    }

    /**
     *
     * @param padre
     * @param posizq
     * @param izq
     * @param der
     * @throws Exception
     *
     * Partición 2/3. Se usará cuando un nodo sobrecargado tenga ambos hermanos
     * y uno de ellos esté lleno
     */
    private void particion_2_3(Nodo padre, int posizq, Nodo izq, Nodo der) throws Exception {
        int clavesRepartir = izq.cardinal() + der.cardinal() - 1;
        Nodo reg = new Nodo();
        int ncizq = (clavesRepartir) / 3;
        int ncreg = (clavesRepartir + 1) / 3;
        int ncder = (clavesRepartir + 2) / 3;
        int antncder = der.cardinal();
        int antncizq = izq.cardinal();
        //Se inserta en el padre una nueva clave y la nueva dirección
        reg.direccion(fichero.tomarPágina());
        padre.insertar(izq.clave(ncizq + 1), reg.direccion(), posizq+1);
        //Pasamos datos de izq a reg
        reg.cardinal(ncreg);

        reg.enlace(0, izq.enlace(ncizq+1));
        for(int i=ncizq + 2; i <= antncizq; i++) {
            reg.clave(i - ncizq - 1, izq.clave(i));
            reg.enlace(i - ncizq - 1, izq.enlace(i));
        }
        izq.cardinal(ncizq);
        //Pasamos el dato del padre a la posición correspondiente de reg
        reg.clave(antncizq - ncizq, padre.clave(posizq+2));
        int posl = antncizq - ncizq;
        reg.enlace(posl, der.enlace(0));

        for(int i=posl+1; i <= ncreg; i++) {
            reg.clave(i, der.clave(i - posl));
            reg.enlace(i, der.enlace(i - posl));
        }
        int ncpas = antncder - ncder;
        //Pasamos al padre el valor correspondiente y compactamos der
        padre.clave(posizq + 2, der.clave(ncpas));
        der.enlace(0, der.enlace(ncpas));

        for(int i=ncpas+1; i <= antncder; i++) {
            der.clave(i - ncpas, der.clave(i));
            der.enlace(i - ncpas, der.enlace(i));
        }
        der.cardinal(ncder);
        escribir(izq);
        escribir(reg);
        escribir(der);
    }

    /**
     *
     * @param padre
     * @param posizq
     * @param izq
     * @param der
     * @throws Exception
     *
     * Rotación de izquierda a derecha. Se usará cuando el nodo sobrecargado tenga hermano izquierdo
     * y ese hermano no esté lleno
     */
    private void rotacionizqder(Nodo padre, int posizq, Nodo izq, Nodo der) throws Exception {
        int clavesRepartir = izq.cardinal() + der.cardinal();
        int ncizq = (clavesRepartir) / 2;
        int ncder = clavesRepartir - ncizq;
        int ncpas = ncder - der.cardinal();
        int antncder = der.cardinal();
        //Hacemos hueco en nodo der
        der.cardinal(ncder);

        for(int i=antncder; i >= 1; i--) {
            der.clave(i + ncpas, der.clave(i));
            der.enlace(i + ncpas, der.enlace(i));
        }
        der.enlace(ncpas, der.enlace(0));
        //Rellenar el nodo der
        der.clave(ncpas, padre.clave(posizq+1));

        for(int i=ncizq+2; i <= izq.cardinal(); i++) {
            der.clave(i - (ncizq+1), izq.clave(i));
            der.enlace(i - (ncizq+1), izq.enlace(i));
        }
        der.enlace(0, izq.enlace(ncizq+1));
        //Modificar el nodo padre
        padre.clave(posizq+1, izq.clave(ncizq+1));
        //Modificar el nodo izq
        izq.cardinal(ncizq);
        //Se escriben en el fichero los tres nodos
        escribir(padre);
        escribir(izq);
        escribir(der);
    }

    /**
     *
     * @param padre
     * @param posizq
     * @param izq
     * @param der
     * @throws Exception
     *
     * Simétrica a la rotación izquierda-derecha
     */
    private void rotacionderizq(Nodo padre, int posizq, Nodo izq, Nodo der) throws Exception {
        int clavesRepartir = izq.cardinal() + der.cardinal();
        int ncder = (clavesRepartir) / 2;
        int ncizq = clavesRepartir - ncder;
        int ncpas = der.cardinal() - ncder;
        int antncizq = izq.cardinal();
        //Pasamos la clave del padre y datos de der a izq
        izq.cardinal(ncizq);
        izq.clave(antncizq+1, padre.clave(posizq+1));
        izq.enlace(antncizq+1, der.enlace(0));

        for(int i=1; i < ncpas; i++) {
            izq.clave(antncizq + 1 + i, der.clave(i));
            izq.enlace(antncizq + 1 + i, der.enlace(i));
        }
        //Pasamos clave al padre
        padre.clave(posizq + 1, der.clave(ncpas));
        //Quitamos hueco en der
        der.enlace(0, der.enlace(ncpas));

        for(int i=1; i <= ncder; i++) {
            der.clave(i, der.clave(i + ncpas));
            der.enlace(i, der.enlace(i + ncpas));
        }
        der.cardinal(ncder);
        escribir(padre);
        escribir(izq);
        escribir(der);
    }

    /**
     *
     * @param padre
     * @param posizq
     * @param izq
     * @param der
     * @throws Exception
     *
     * Recombinación 2/1. Se usará cuando el nodo bajo mínimo tenga un hermano
     * que no esté al mínimo
     */
    private void recombinacion_2_1(Nodo padre, int posizq, Nodo izq, Nodo der) throws Exception {
        //Bajamos la clave discriminante en el padre al final del izquierdo
        int antncizq = izq.cardinal();
        izq.cardinal(izq.cardinal() + 1 + der.cardinal());
        izq.clave(antncizq + 1, padre.clave(posizq+1));
        //Pasamos el enlace cero de der a izq ya que no encaja en el bucle
        izq.enlace(antncizq + 1, der.enlace(0));
        //Pasamos el resto de enlaces y claves
        for(int i=1; i <= der.cardinal(); i++) {
            izq.clave(antncizq + 1 + i, der.clave(i));
            izq.enlace(antncizq + 1 + i, der.enlace(i));
        }
        //Quitamos del padre la clave y el enlace a der
        padre.extraer(posizq+1);
        escribir(izq);
        fichero.liberarPágina(der.direccion());
    }

    /**
     *
     * @param padre
     * @param posReg
     * @param izq
     * @param reg
     * @param der
     * @throws Exception
     *
     * Recombinación 3/2. Se usará cuando el nodo bajo mínimo tenga dos hermanos que
     * no estén al mínimo.
     */
    private void recombinacion_3_2(Nodo padre, int posReg, Nodo izq, Nodo reg, Nodo der) throws Exception {
        int aRepartir = izq.cardinal() + reg.cardinal() + der.cardinal() + 1;
        int ncder = aRepartir / 2;
        int ncizq = aRepartir - ncder;
        int antncizq = izq.cardinal();
        int antncder = der.cardinal();
        //Rellenamos el hermano izquierdo
        izq.cardinal(ncizq);
        izq.clave(antncizq + 1, padre.clave(posReg));
        izq.enlace(antncizq + 1, reg.enlace(0));

        for(int i=antncizq + 2; i <= ncizq; i++) {
            izq.clave(i, reg.clave(i - antncizq - 1));
            izq.enlace(i, reg.enlace(i - antncizq - 1));
        }
        //Desplazamiento del hermano derecho para hacer hueco
        der.cardinal(ncder);
        int ncpas = ncder - antncder;

        for(int i=antncder; i >= 1; i--) {
            der.clave(i + ncpas, der.clave(i));
            der.enlace(i + ncpas, der.enlace(i));
        }
        der.enlace(ncpas, der.enlace(0));
        der.clave(ncpas, padre.clave(posReg+1));
        //Rellenamos el hermano derecho
        for(int i=ncpas-1; i >= 1; i--) {
            der.clave(i, reg.clave(reg.cardinal() + i - ncpas + 1));
            der.enlace(i, reg.enlace(reg.cardinal() + i - ncpas + 1));
        }
        der.enlace(0, reg.enlace(reg.cardinal() - ncpas + 1));
        //Modificamos el nodo padre
        fichero.liberarPágina(reg.direccion());
        escribir(izq);
        escribir(der);
        padre.extraer(posReg);
        padre.clave(posReg, reg.clave(reg.cardinal() - ncpas + 1));
    }

    private void escribir(Nodo n) {
        fichero.escribir(n.abyte(), n.direccion());
    }

    Nodo leer(int dir) throws Exception {
        Nodo n = new Nodo();
        n.deByte(fichero.leer(dir));
        if(n.direccion() != dir) throw new Exception("Error al leer un nodo del árbol");
        return n;
    }

    /**
     *
     * Clase auxiliar que implementa la pila de búsqueda
     */
    class InfoPila {
        public Nodo nodo;
        public int pos;

        public InfoPila() {
        };

        public InfoPila(Nodo n, int p) {
            nodo = n;
            pos = p;
        }
    }
}