package arboles;

/**
 *
 * @author jcort
 */

/*	Operacion requerida
 * (a+b)+(c+b)+e^z
 * */
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
        dibujarNodo(g, getWidth() / 2, 30, raiz);
    }
    int nivel = 50;
    int line = 36;
    int extra = 10;
	int i = 0;
    // Metodo que pinta en el lienzo el arbol   
    private void dibujarNodo(Graphics g, int x, int y, Nodo nodo) {
    	String param = nodo.valor + "";
    	nivel = (i==1 || i==4) ? 100 : 50;
    	line = (i==1 || i==4) ? 79 : 36;
    	extra = (i==1 || i==4) ? 30 : 0;
    	if (nodo != null) {
            g.drawOval(x - radio, y - radio, radio * 2, radio * 2);
            g.drawString(param, x - 5, y + 5);
            
            if (nodo.izquierda != null) {
                g.drawLine(x-15, y+15, x - line, y + line);
                dibujarNodo(g, x - nivel, y + 50, nodo.izquierda);
//                i++;
//                System.out.println("Se pinto izquierdo: " + param + " " + i);
            }
            if (nodo.derecha != null) {
                g.drawLine(x+14, y+14, x + line, y + line-extra);
                dibujarNodo(g, x + nivel, y + 50, nodo.derecha);
//              i++;
//                System.out.println("Se pinto el derecho: " + param + " " + i);
            }
//            System.out.println("Se pinto el derecho: " + param + " " + i);
        }
//        System.out.println("Se pinto: " + param + " " + i);
//        nivel = (i%2==0) ? nivel-150 : nivel+0;
//        String aviso = (i%2==0) ? "i es par" : "i es impar";
    	i++;
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
    public Double operadores(String operandoA, String operandoB, String operandoC, String operandoE, String operandoZ) {
    	Double a = Double.parseDouble(operandoA);
    	Double b = Double.parseDouble(operandoB);
    	Double c = Double.parseDouble(operandoC);
    	Double e = Double.parseDouble(operandoE);
    	Double z = Double.parseDouble(operandoZ);
    	return ((a+b) + (c+b) + (Math.pow(e, z)));
    }
    // metodo que indica si se tiene operador 
    public boolean operadorBoleano(String operador) {
    	switch (operador) {
		case "+":
		case "-":
		case "*":
		case "/": 
		case "^": 
		case "(": 
		case ")":
			return true;
		default:
			return false;
		}
    }
    
	public static void main(String[] args) {
	    String expresion = JOptionPane.showInputDialog("Ingrese la expresión:");
	    String[] array = separarElementos(expresion);
	    ArbolExpresiones operadores = new ArbolExpresiones();
	    
	    
	    
	    if (array.length >= 15) {
	        SwingUtilities.invokeLater(() -> {
	            JFrame frame = new JFrame("Árbol de Expresiones");
	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frame.setSize(800, 600);
	
	            Nodo raiz = new Nodo(array[5]);
	            raiz.izquierda = new Nodo(array[2]);
	            raiz.izquierda.izquierda = new Nodo(array[1]);
	            raiz.izquierda.derecha = new Nodo(array[3]);
	            raiz.derecha = new Nodo(array[11]);
	            raiz.derecha.izquierda = new Nodo(array[8]);
	            raiz.derecha.izquierda.izquierda = new Nodo(array[7]);
	            raiz.derecha.izquierda.derecha = new Nodo(array[9]);
	            raiz.derecha.derecha = new Nodo(array[13]);
	            raiz.derecha.derecha.izquierda = new Nodo(array[12]);
	            raiz.derecha.derecha.derecha = new Nodo(array[14]);
	            
	
	            ArbolExpresiones panel = new ArbolExpresiones(raiz);
	            frame.add(panel);
	
	            frame.setVisible(true);
	            
	            boolean solucion = true;
	            for(int i = 0; i<array.length; i++) {
	            	if(!esNumero(array[i]) && !operadores.operadorBoleano(array[i])) {
	            		System.out.println(array[i]);
	            		solucion = false;
	            		break;
	            	}else {
//	    		System.out.println("Vas bien bro " + array[i]);
	            	}
	            }//2+4-5*8/10^2    12+10-a*c/5^2
	            
//	            (a+b)+(c+b)+e^z
	            if(solucion==true) {
	            	Double resultado = operadores.operadores(array[1], array[3], array[7], array[12], array[14]);
		            JOptionPane.showMessageDialog(null, expresion + " = " + resultado, "Resultado", JOptionPane.INFORMATION_MESSAGE);
	            }
	        });
	    } else {	    
	    	JOptionPane.showMessageDialog(null, "La expresión debe tener al menos tres caracteres.");
	    }	    
	}

}