package arboles;

class Nodo {
    String valor;
    Nodo izquierda;
    Nodo derecha;
    int numero;

    Nodo(String item) {
        valor = item;
        izquierda = derecha = null;
    }

    // Constructor para números
    public Nodo(int numero) {
        this.numero = numero;
        izquierda = derecha = null;
    }
}
