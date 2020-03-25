header{
	import java.util.*;
	import antlr.*;
	import java.io.*;
}
class Compilador extends TreeParser; 
options{
   importVocab = Anasint;	
}
{

// Abrir y cerrar fichero
	FileWriter fichero;

	private void crearFichero() {
		try {
			fichero = new FileWriter("src/Programa.java");
		} catch (IOException e) {
			System.out.println("crearFichero (exception): " + e.toString());
		}
	}

	private void cerrarFichero() {
		try {
			fichero.close();
		} catch (IOException e) {
			System.out.println("cerrarFichero (exception): " + e.toString());
		}
	}

	// Generador de fichero
	private void cClase() {
		try {
			fichero.write("import java.io.*;\n");
			fichero.write("public class Programa{\n");
		} catch (IOException e) {
			System.out.println("exception: " + e.toString());
		}
	}

	// Iniciar main()
	private void cMain() {
		try {
			fichero.write("public static void main(String[] args) {");			
		} catch (IOException e) {
			System.out.println("exception: " + e.toString());
		}
	}
	
	// Cerrar main()
	private void finMain() {
		try {
			fichero.write("}\n");
		} catch (IOException e) {
			System.out.println("exception: " + e.toString());
		}
	}

	// Cerrar class()
	private void finClase() {
		try {
			fichero.write("}");
		} catch (IOException e) {
			System.out.println("exception: " + e.toString());
		}
	}
	
	///////////////////////////////// COSAS DENTRO DEL MAIN() /////////////////////////////////
	
	// Inicializar la variable en el main()
	private void inicializarVar(AST v1, AST v2){
		try{
			fichero.write(v1.getText() + " = " + valorVar(v2) + ";");
		}catch (IOException e) {
			System.out.println("exception: " + e.toString());
		}
	}
	
	// Mostrar por pantalla el resultado de las expresiones
	private void mostrarExp(AST o){
		try{
			fichero.write("System.out.println(" + valorVar(o) + ");");
		}catch (IOException e) {
			System.out.println("exception: " + e.toString());
		}
	}
	
	// Obtener el valor de la variable
	private String valorVar(AST v){
		switch(v.getType()){
			case MAS:
				return valorVar(v.getFirstChild()) + " + " +  valorVar(v.getFirstChild().getNextSibling());
			case MENOS:
				return valorVar(v.getFirstChild()) + " - " + valorVar(v.getFirstChild().getNextSibling());
			case POR:
				return valorVar(v.getFirstChild()) + " * " + valorVar(v.getFirstChild().getNextSibling());
			case DIV:
				return valorVar(v.getFirstChild()) + " / " + valorVar(v.getFirstChild().getNextSibling());
			case LLAMADA:
				return valorVar(v.getFirstChild()) + "(" + valorVar(v.getFirstChild().getNextSibling()) + ")";
			case IDENT:
				return v.getText();
			case NUMERO:
				return v.getText();
			default:
					return "";
		}
	}
	
	///////////////////////////////// COSAS FUERA DEL MAIN() /////////////////////////////////
	
	Set<String> variables = new HashSet<>();
	List<String> metodos = new ArrayList<>();
	
	// Declarar las variables fuera del main()
	private void declararVar(){
		try{
			for(String s : variables){
				fichero.write("public static Integer " + s + ";");
			}
		}catch (IOException e) {
			System.out.println("exception: " + e.toString());
		}
	}
	
	//Anyadir metodos a la lista de String llamada metodos
	private void anyadirMet(AST a, AST b, AST c){
		String s1 = "public static Integer " + a.getText() + "(Integer " + b.getText() + "){\n";
		String s2 = "		return " + valorVar(c) + ";\n";
		String s3 = "}\n";
		Collections.addAll(metodos, s1, s2, s3);
	}
	
	// Declarar las funciones fuera del main()
	private void declararFunc(){
		try{
			for(String s : metodos){
				fichero.write(s);
			}
		}catch (IOException e) {
			System.out.println("exception: " + e.toString());
		}
	}
	
}
ordenes: 
	{crearFichero(); cClase(); cMain();}
	#(ORDENES (orden)+)
	{finMain(); declararVar(); declararFunc(); finClase(); cerrarFichero();}
	;
	
orden : 
	def_funcion
	| asignacion
	| e:expresion {mostrarExp(e);} 
	;
	
def_funcion : 
	#(DEF_FUNCION a:IDENT b:IDENT c:expresion){
		anyadirMet(a, b, c);	
	}	 
	;

asignacion : 
	#(ASIG i:IDENT e:expresion) {
		variables.add(i.getText());
		inicializarVar(i, e);
	}
	;

expresion  : 
	#(MAS expresion expresion)
	| #(MENOS expresion expresion) 
	| #(POR expresion expresion) 
	| #(DIV expresion expresion) 
	| j:NUMERO 
	| #(LLAMADA IDENT expresion) 
	| IDENT
	;