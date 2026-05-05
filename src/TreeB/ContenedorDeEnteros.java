package TreeB;

import java.util.LinkedList;
import java.util.Stack;


public class ContenedorDeEnteros {
    LinkedList<InfoPila> cola = new LinkedList<>();
    int minimoClaves;
    FicheroAyuda fichero = new FicheroAyuda();
    int Orden;
    int tamanoDatos = 4;
    int raiz;
    int numElem;
    String nombreFichero;

    protected class Nodo {
        private int numElei; 
        private int direccioni; 
        private final int[] clavei;
        private final int[] enlacei;

        Nodo() {
            direccioni = fichero.dirNula;
            numElei = 0;
          
            clavei = new int[Orden];
            enlacei = new int[Orden+1];
        }

        private int tamano() {
            int tam = 2 * Conversor.INTBYTES; 
            tam += (Orden-1) * tamanoDatos; 
            tam += Orden * Conversor.INTBYTES; 
            return tam;
        }

        private int clave(int i) throws Exception {
            if(i < 1 || i > numElei) throw new Exception("Error interno del multirrama");
            return clavei[i-1];
        }

        private void clave(int i, int d) throws Exception {
            if(i < 1 || i > numElei) throw new Exception("Error interno del multirrama");
            clavei[i-1] = d;
        }

        private int enlace(int i) throws Exception {
            if(i < 0 || i > numElei) throw new Exception("Error interno del multirrama");
            return enlacei[i];
        }

        private void enlace(int i, int d) throws Exception {
            if(i < 0 || i > numElei) throw new Exception("Error interno del multirrama");
            enlacei[i] = d;
        }

        private int direccion() {
            return direccioni;
        }

        private void direccion(int d) {
            direccioni = d;
        }

        public int cardinal() {
            return numElei;
        }

        private void cardinal(int n) {
            numElei = n;
        }


        private void deByte(byte[] datos) {
            int leb = Conversor.INTBYTES; 
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


        void insertar(int e, int dir, int pos) throws Exception {
            numElei++;
            for(int i=numElei - 1; i >= pos; i--) {
                clave(i + 1, clave(i));
                enlace(i + 1, enlace(i));
            }
            clave(pos, e);
            enlace(pos, dir);
        }


        void extraer(int pos) throws Exception {
            for(int i = pos; i < numElei; i++) {
                clave(i, clave(i + 1));
                enlace(i, enlace(i + 1));
            }
            numElei--;
        }

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


    public void abrir(String ruta) {
        fichero.abrir(ruta);
        raiz = fichero.adjunto(0);
        numElem = fichero.adjunto(1);
        tamanoDatos = fichero.adjunto(2);
        Orden = fichero.adjunto(3);
        minimoClaves = (Orden + 1) / 2 - 1;
    }


    public void cerrar() {
        fichero.cerrar();
    }


    public int cardinal() {
        return numElem;
    }


    public boolean insertar(int n) throws Exception {
        Stack<InfoPila> pila = new Stack<InfoPila>();
        if(buscar(n, pila)) return false; 
        Nodo nodoActual = new Nodo();
        InfoPila info;
        ParejaInsertar pa = new ParejaInsertar();
        pa.clave = n;
        pa.direccion = fichero.dirNula;
        fichero.adjunto(1, ++numElem);
        if(!pila.empty()) {
            
            info = (InfoPila) pila.pop();
            nodoActual = info.nodo;
            int pos = info.pos;
            nodoActual.insertar(pa.clave, pa.direccion, pos+1);
            if(nodoActual.cardinal() < Orden) {
                
                escribir(nodoActual);
                return true;
            }

            while(!pila.empty()) {
                
                info = (InfoPila) pila.pop();
                Nodo der = new Nodo();
                Nodo izq = new Nodo();
                Nodo padre = info.nodo;
                pos = info.pos;
                if(pos > 0) {
                    
                    izq = leer(padre.enlace(pos-1));
                    if(izq.cardinal() < Orden - 1) {
                        
                        rotacionderizq(padre, pos-1, izq, nodoActual);
                        return true;
                    }
                }
                if(pos < padre.cardinal()) {
                    
                    der = leer(padre.enlace(pos+1));
                    if(der.cardinal() < Orden - 1) {
                        
                        rotacionizqder(padre, pos, nodoActual, der);
                        return true;
                    }
                }
                
                if(pos == 0) particion_2_3(padre, pos, nodoActual, der);
                else particion_2_3(padre, pos-1, izq, nodoActual);
                if(padre.cardinal() < Orden) {
                    escribir(padre);
                    return true;
                }
                nodoActual = padre;
            }
            
            pa = particion_1_2(nodoActual);
        }
        
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


    public boolean extraer(int n) throws Exception {
        Stack<InfoPila> pila = new Stack<InfoPila>();
        if(!buscar(n, pila)) return false; 
        fichero.adjunto(1, --numElem);
        Nodo nodoActual = new Nodo();
        InfoPila info = (InfoPila) pila.pop();
        nodoActual = info.nodo;
        int pos = info.pos;

        if(nodoActual.enlace(0) != fichero.dirNula) {
            pila.add(new InfoPila(info.nodo, info.pos));
            int dir = nodoActual.enlace(pos);
            do {
                nodoActual = leer(dir);
                dir = nodoActual.enlace(0);
                if(dir == fichero.dirNula) pos = 1;
                else pos = 0;
                cola.addLast(new InfoPila(nodoActual, pos));
            } while(dir != fichero.dirNula);
            info = (InfoPila) pila.pop();
            info.nodo.clave(info.pos, nodoActual.clave(1));
            escribir(info.nodo);
            pila.add(info);
            while(!cola.isEmpty()) {
                nodoActual =((InfoPila) cola.getFirst()).nodo;
                pila.add(cola.getFirst());
                cola.removeFirst();
            }
            info = (InfoPila) pila.pop();
            nodoActual = info.nodo;
            pos = info.pos;
        }
        nodoActual.extraer(pos);
        while(nodoActual.cardinal() < minimoClaves && nodoActual.direccion() != raiz) {

            Nodo padre, der = new Nodo(), izq = new Nodo();
            info = (InfoPila) pila.pop();
            padre = info.nodo; 
            pos = info.pos;
            if(pos < padre.cardinal()) {
                der = leer(padre.enlace(pos + 1));
                if(der.cardinal() > minimoClaves) {
                    rotacionderizq(padre, pos, nodoActual, der);
                    return true; //sobra
                }
            }
            if(pos > 0) {
                izq = leer(padre.enlace(pos - 1));
                if(izq.cardinal() > minimoClaves) {
                    rotacionizqder(padre, pos-1, izq, nodoActual);
                    return true; //sobra
                }
            }
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
            escribir(nodoActual);
        } else {
            raiz = nodoActual.enlace(0);
            fichero.liberarPágina(nodoActual.direccion());
            fichero.adjunto(0, raiz);
        }
        return true;
    }


 
    public boolean buscar(int n) throws Exception {
        return buscar(n, new Stack<InfoPila>());
    }



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


    private void particion_2_3(Nodo padre, int posizq, Nodo izq, Nodo der) throws Exception {
        int clavesRepartir = izq.cardinal() + der.cardinal() - 1;
        Nodo reg = new Nodo();
        int ncizq = (clavesRepartir) / 3;
        int ncreg = (clavesRepartir + 1) / 3;
        int ncder = (clavesRepartir + 2) / 3;
        int antncder = der.cardinal();
        int antncizq = izq.cardinal();
        reg.direccion(fichero.tomarPágina());
        padre.insertar(izq.clave(ncizq + 1), reg.direccion(), posizq+1);
        reg.cardinal(ncreg);

        reg.enlace(0, izq.enlace(ncizq+1));
        for(int i=ncizq + 2; i <= antncizq; i++) {
            reg.clave(i - ncizq - 1, izq.clave(i));
            reg.enlace(i - ncizq - 1, izq.enlace(i));
        }
        izq.cardinal(ncizq);
        reg.clave(antncizq - ncizq, padre.clave(posizq+2));
        int posl = antncizq - ncizq;
        reg.enlace(posl, der.enlace(0));

        for(int i=posl+1; i <= ncreg; i++) {
            reg.clave(i, der.clave(i - posl));
            reg.enlace(i, der.enlace(i - posl));
        }
        int ncpas = antncder - ncder;
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


    private void rotacionizqder(Nodo padre, int posizq, Nodo izq, Nodo der) throws Exception {
        int clavesRepartir = izq.cardinal() + der.cardinal();
        int ncizq = (clavesRepartir) / 2;
        int ncder = clavesRepartir - ncizq;
        int ncpas = ncder - der.cardinal();
        int antncder = der.cardinal();
        der.cardinal(ncder);

        for(int i=antncder; i >= 1; i--) {
            der.clave(i + ncpas, der.clave(i));
            der.enlace(i + ncpas, der.enlace(i));
        }
        der.enlace(ncpas, der.enlace(0));
        der.clave(ncpas, padre.clave(posizq+1));

        for(int i=ncizq+2; i <= izq.cardinal(); i++) {
            der.clave(i - (ncizq+1), izq.clave(i));
            der.enlace(i - (ncizq+1), izq.enlace(i));
        }
        der.enlace(0, izq.enlace(ncizq+1));
        padre.clave(posizq+1, izq.clave(ncizq+1));
        izq.cardinal(ncizq);
        escribir(padre);
        escribir(izq);
        escribir(der);
    }


    private void rotacionderizq(Nodo padre, int posizq, Nodo izq, Nodo der) throws Exception {
        int clavesRepartir = izq.cardinal() + der.cardinal();
        int ncder = (clavesRepartir) / 2;
        int ncizq = clavesRepartir - ncder;
        int ncpas = der.cardinal() - ncder;
        int antncizq = izq.cardinal();
        izq.cardinal(ncizq);
        izq.clave(antncizq+1, padre.clave(posizq+1));
        izq.enlace(antncizq+1, der.enlace(0));

        for(int i=1; i < ncpas; i++) {
            izq.clave(antncizq + 1 + i, der.clave(i));
            izq.enlace(antncizq + 1 + i, der.enlace(i));
        }
        padre.clave(posizq + 1, der.clave(ncpas));
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


    private void recombinacion_2_1(Nodo padre, int posizq, Nodo izq, Nodo der) throws Exception {
        int antncizq = izq.cardinal();
        izq.cardinal(izq.cardinal() + 1 + der.cardinal());
        izq.clave(antncizq + 1, padre.clave(posizq+1));
        izq.enlace(antncizq + 1, der.enlace(0));
        for(int i=1; i <= der.cardinal(); i++) {
            izq.clave(antncizq + 1 + i, der.clave(i));
            izq.enlace(antncizq + 1 + i, der.enlace(i));
        }
        padre.extraer(posizq+1);
        escribir(izq);
        fichero.liberarPágina(der.direccion());
    }


    private void recombinacion_3_2(Nodo padre, int posReg, Nodo izq, Nodo reg, Nodo der) throws Exception {
        int aRepartir = izq.cardinal() + reg.cardinal() + der.cardinal() + 1;
        int ncder = aRepartir / 2;
        int ncizq = aRepartir - ncder;
        int antncizq = izq.cardinal();
        int antncder = der.cardinal();
        izq.cardinal(ncizq);
        izq.clave(antncizq + 1, padre.clave(posReg));
        izq.enlace(antncizq + 1, reg.enlace(0));

        for(int i=antncizq + 2; i <= ncizq; i++) {
            izq.clave(i, reg.clave(i - antncizq - 1));
            izq.enlace(i, reg.enlace(i - antncizq - 1));
        }
        der.cardinal(ncder);
        int ncpas = ncder - antncder;

        for(int i=antncder; i >= 1; i--) {
            der.clave(i + ncpas, der.clave(i));
            der.enlace(i + ncpas, der.enlace(i));
        }
        der.enlace(ncpas, der.enlace(0));
        der.clave(ncpas, padre.clave(posReg+1));
        for(int i=ncpas-1; i >= 1; i--) {
            der.clave(i, reg.clave(reg.cardinal() + i - ncpas + 1));
            der.enlace(i, reg.enlace(reg.cardinal() + i - ncpas + 1));
        }
        der.enlace(0, reg.enlace(reg.cardinal() - ncpas + 1));
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