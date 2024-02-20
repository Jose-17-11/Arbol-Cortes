package arboles;

/**
 *
 * @author jcort
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArbolExpresiones extends JPanel {
    private Nodo raiz;
    private int radio = 20;

    ArbolExpresiones(Nodo raiz) {
        this.raiz = raiz;
    }
    
    ArbolExpresiones() {
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarArbol(g, getWidth() / 2, 30, raiz);
    }

    private void dibujarArbol(Graphics g, int x, int y, Nodo nodo) {
        String param = nodo.valor + "";
        if (nodo != null) {
            g.drawOval(x - radio, y - radio, radio * 2, radio * 2);
            g.drawString(param, x - 5, y + 5);

            if (nodo.izquierda != null) {
                g.drawLine(x, y, x - 50, y + 50);
                dibujarArbol(g, x - 50, y + 50, nodo.izquierda);
            }
            if (nodo.derecha != null) {
                g.drawLine(x, y, x + 50, y + 50);
                dibujarArbol(g, x + 50, y + 50, nodo.derecha);
            }
        }
    }
    
    // Metodo que separa elementos numericos de la cadena expresion
    public static String[] separarElementos(String expresion) {
        List<String> elementos = new ArrayList<>();
        
        // Expresión regular para encontrar números
        Pattern pattern = Pattern.compile("\\d+|\\D");
        Matcher matcher = pattern.matcher(expresion);
        
        // Agregar los elementos encontrados al array
        while (matcher.find()) {
            elementos.add(matcher.group());
        }
        
        // Convertir la lista a un array de strings
        return elementos.toArray(new String[0]);
    }
    
    // Detecta si un elemento String es numerico o no
    public static boolean esNumero(String elemento) {
        try {
            Double.parseDouble(elemento); // Intentar convertir el elemento a un número
            return true; // Si no lanza una excepción, el elemento es un número
        } catch (NumberFormatException e) {
            return false; // Si lanza una excepción, el elemento no es un número
        }
    }
    
    // Recibe los elementos del array y realiza la operacion que se ingreso
    public Double operadores(String operador, String operandoA, String operandoB) {
    	Double num1 = Double.parseDouble(operandoA);
    	Double num2 = Double.parseDouble(operandoB);
    	switch (operador) {
		case "+": 
			return num1 + num2;
		case "-": 
			return num1 - num2;
		case "*": 
			return num1 * num2;
		case "/": 
			return num1 / num2;
		default:
			throw new IllegalArgumentException("Unexpected value: ");
		}
    }
    
    public boolean operadorBoleano(String operador) {
    	switch (operador) {
		case "+":
		case "-":
		case "*":
		case "/": 
			return true;
		default:
			return false;
		}
    }
	public static void main(String[] args) {
	    String expresion = JOptionPane.showInputDialog("Ingrese la expresión:");
	    String[] array = separarElementos(expresion);
    	ArbolExpresiones operadores = new ArbolExpresiones();
	    if (array.length >= 3 && operadores.operadorBoleano(array[1])==true) {
	        SwingUtilities.invokeLater(() -> {
	            JFrame frame = new JFrame("Árbol de Expresiones");
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setSize(400, 400);
	
	            Nodo raiz = new Nodo(array[1]);
	            raiz.izquierda = new Nodo(array[0]);
	            raiz.derecha = new Nodo(array[2]);
	
	            ArbolExpresiones panel = new ArbolExpresiones(raiz);
	            frame.add(panel);
	
	            frame.setVisible(true);
	            
	            if(esNumero(array[0]) && esNumero(array[2])) {
	            	Double resultado = operadores.operadores(array[1], array[0], array[2]);
		            JOptionPane.showMessageDialog(null, expresion + " = " + resultado, "Resultado", JOptionPane.INFORMATION_MESSAGE);
	            }
	        });
	    } else {	    
	    	JOptionPane.showMessageDialog(null, "La expresión debe tener al menos tres caracteres.");
	    }	    
	}

}
