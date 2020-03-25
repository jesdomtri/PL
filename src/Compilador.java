// $ANTLR : "Compilador.g" -> "Compilador.java"$

	import java.util.*;
	import antlr.*;
	import java.io.*;

import antlr.TreeParser;
import antlr.Token;
import antlr.collections.AST;
import antlr.RecognitionException;
import antlr.ANTLRException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.collections.impl.BitSet;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;


public class Compilador extends antlr.TreeParser       implements CompiladorTokenTypes
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
	
public Compilador() {
	tokenNames = _tokenNames;
}

	public final void ordenes(AST _t) throws RecognitionException {
		
		AST ordenes_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			crearFichero(); cClase(); cMain();
			AST __t2 = _t;
			AST tmp1_AST_in = (AST)_t;
			match(_t,ORDENES);
			_t = _t.getFirstChild();
			{
			int _cnt4=0;
			_loop4:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_tokenSet_0.member(_t.getType()))) {
					orden(_t);
					_t = _retTree;
				}
				else {
					if ( _cnt4>=1 ) { break _loop4; } else {throw new NoViableAltException(_t);}
				}
				
				_cnt4++;
			} while (true);
			}
			_t = __t2;
			_t = _t.getNextSibling();
			finMain(); declararVar(); declararFunc(); finClase(); cerrarFichero();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void orden(AST _t) throws RecognitionException {
		
		AST orden_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST e = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case DEF_FUNCION:
			{
				def_funcion(_t);
				_t = _retTree;
				break;
			}
			case ASIG:
			{
				asignacion(_t);
				_t = _retTree;
				break;
			}
			case LLAMADA:
			case IDENT:
			case MAS:
			case MENOS:
			case POR:
			case DIV:
			case NUMERO:
			{
				e = _t==ASTNULL ? null : (AST)_t;
				expresion(_t);
				_t = _retTree;
				mostrarExp(e);
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void def_funcion(AST _t) throws RecognitionException {
		
		AST def_funcion_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST a = null;
		AST b = null;
		AST c = null;
		
		try {      // for error handling
			AST __t7 = _t;
			AST tmp2_AST_in = (AST)_t;
			match(_t,DEF_FUNCION);
			_t = _t.getFirstChild();
			a = (AST)_t;
			match(_t,IDENT);
			_t = _t.getNextSibling();
			b = (AST)_t;
			match(_t,IDENT);
			_t = _t.getNextSibling();
			c = _t==ASTNULL ? null : (AST)_t;
			expresion(_t);
			_t = _retTree;
			_t = __t7;
			_t = _t.getNextSibling();
			
					anyadirMet(a, b, c);	
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void asignacion(AST _t) throws RecognitionException {
		
		AST asignacion_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST i = null;
		AST e = null;
		
		try {      // for error handling
			AST __t9 = _t;
			AST tmp3_AST_in = (AST)_t;
			match(_t,ASIG);
			_t = _t.getFirstChild();
			i = (AST)_t;
			match(_t,IDENT);
			_t = _t.getNextSibling();
			e = _t==ASTNULL ? null : (AST)_t;
			expresion(_t);
			_t = _retTree;
			_t = __t9;
			_t = _t.getNextSibling();
			
					variables.add(i.getText());
					inicializarVar(i, e);
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void expresion(AST _t) throws RecognitionException {
		
		AST expresion_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST j = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case MAS:
			{
				AST __t11 = _t;
				AST tmp4_AST_in = (AST)_t;
				match(_t,MAS);
				_t = _t.getFirstChild();
				expresion(_t);
				_t = _retTree;
				expresion(_t);
				_t = _retTree;
				_t = __t11;
				_t = _t.getNextSibling();
				break;
			}
			case MENOS:
			{
				AST __t12 = _t;
				AST tmp5_AST_in = (AST)_t;
				match(_t,MENOS);
				_t = _t.getFirstChild();
				expresion(_t);
				_t = _retTree;
				expresion(_t);
				_t = _retTree;
				_t = __t12;
				_t = _t.getNextSibling();
				break;
			}
			case POR:
			{
				AST __t13 = _t;
				AST tmp6_AST_in = (AST)_t;
				match(_t,POR);
				_t = _t.getFirstChild();
				expresion(_t);
				_t = _retTree;
				expresion(_t);
				_t = _retTree;
				_t = __t13;
				_t = _t.getNextSibling();
				break;
			}
			case DIV:
			{
				AST __t14 = _t;
				AST tmp7_AST_in = (AST)_t;
				match(_t,DIV);
				_t = _t.getFirstChild();
				expresion(_t);
				_t = _retTree;
				expresion(_t);
				_t = _retTree;
				_t = __t14;
				_t = _t.getNextSibling();
				break;
			}
			case NUMERO:
			{
				j = (AST)_t;
				match(_t,NUMERO);
				_t = _t.getNextSibling();
				break;
			}
			case LLAMADA:
			{
				AST __t15 = _t;
				AST tmp8_AST_in = (AST)_t;
				match(_t,LLAMADA);
				_t = _t.getFirstChild();
				AST tmp9_AST_in = (AST)_t;
				match(_t,IDENT);
				_t = _t.getNextSibling();
				expresion(_t);
				_t = _retTree;
				_t = __t15;
				_t = _t.getNextSibling();
				break;
			}
			case IDENT:
			{
				AST tmp10_AST_in = (AST)_t;
				match(_t,IDENT);
				_t = _t.getNextSibling();
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"ORDENES",
		"DEF_FUNCION",
		"LLAMADA",
		"PyC",
		"IDENT",
		"PA",
		"PC",
		"ASIG",
		"MAS",
		"MENOS",
		"POR",
		"DIV",
		"NUMERO"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 129376L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	}
	
