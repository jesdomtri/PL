// $ANTLR : "Interprete.g" -> "Interprete.java"$

	import java.util.*;
	import antlr.*;

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


public class Interprete extends antlr.TreeParser       implements InterpreteTokenTypes
 {
	
	//Creo hashtables para las variables, parametros y funciones. Aparte del ASTFactory
	Hashtable variables = new Hashtable();
	Hashtable parametros = new Hashtable();
	Hashtable funciones = new Hashtable();
	ASTFactory af = new ASTFactory();
	
	//Metodo para sustituir las expresiones.
	//exp: expresion que quiero sustituir, formal: es la variable que hay a la hora de declarar la funcion, real: lo que realmente quiero
	void sustituir(AST exp, AST formal, Integer real){
		switch(exp.getType()){
			case MAS:
			case MENOS:
			case POR:
			case DIV:
				sustituir(exp.getFirstChild(), formal, real);
				sustituir(exp.getFirstChild().getNextSibling(), formal, real);
				break;
			case LLAMADA:
				sustituir(exp.getFirstChild().getNextSibling(), formal, real);
				break;
			case IDENT:
				if(exp.getText().equals(formal.getText())){
					exp.setType(NUMERO);
					exp.setText(real.toString());
				}
				break;
			default:
				break;
		}
	}
	
	Integer evaluarVariable(AST v) throws RecognitionException{
			if(variables.containsKey(v.getText())){
				return this.expresion((AST)variables.get(v.getText()));
			}else{
				return 0;
			}
		}
	
	Integer evaluarFuncion(AST f, AST formal, Integer real) throws RecognitionException{
		switch(f.getType()){
			case MAS:
				return evaluarFuncion(f.getFirstChild(),  formal, real) + evaluarFuncion(f.getFirstChild().getNextSibling(), formal, real);
			case MENOS:
				return evaluarFuncion(f.getFirstChild(),  formal, real) - evaluarFuncion(f.getFirstChild().getNextSibling(), formal, real);
			case POR:
				return evaluarFuncion(f.getFirstChild(),  formal, real) * evaluarFuncion(f.getFirstChild().getNextSibling(), formal, real);
			case DIV:
				return evaluarFuncion(f.getFirstChild(),  formal, real) / evaluarFuncion(f.getFirstChild().getNextSibling(), formal, real);
			case LLAMADA:
				if(funciones.containsKey(f.getFirstChild().getText())){
					//Expresion que tengo que sustituir
					AST aux1 = af.dupTree((AST)funciones.get(f.getFirstChild().getText()));
					//Parametro que debo sustituir
					AST aux2 = af.dupTree((AST)parametros.get(f.getFirstChild().getText()));
					//Real es por  lo que quiero sustituir
					sustituir(aux1, aux2, real);
					return this.expresion(aux1);
				}else{
					return 0;
				}
			case IDENT:
				if(f.getText().equals(formal.getText())){
					return real;
				}else{
					return this.expresion(f);	
				}
			case NUMERO:
				return this.expresion(f);
			default:
				return 0;
		}
	}
	
	void actualizarVariables(AST v, AST exp){
		variables.put(v.getText(), af.dupTree(exp));
	}
	
	void actualizarFunciones(AST f, AST formal, AST exp){
		//Funciones contiene la declaracion de la funcion
		funciones.put(f.getText(), af.dupTree(exp));
		//Parametros contiene el nombre de la funcion con la variable que ha de ser sustituida
		parametros.put(f.getText(), af.dupTree(formal));
	}
	
public Interprete() {
	tokenNames = _tokenNames;
}

	public final void ordenes(AST _t) throws RecognitionException {
		
		AST ordenes_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
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
		Integer r;
		
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
				r=expresion(_t);
				_t = _retTree;
				;System.out.println("Expresion: " + e.toStringTree() + " vale " + r);
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
		Integer r;
		
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
			r=expresion(_t);
			_t = _retTree;
			_t = __t7;
			_t = _t.getNextSibling();
			
					actualizarFunciones(a,b,c);
					System.out.println("Funcion: " + a.getText() + " (" + b.toStringTree() + ") vale " + c.toStringTree());
					
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void asignacion(AST _t) throws RecognitionException {
		
		AST asignacion_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST a = null;
		AST b = null;
		Integer r;
		
		try {      // for error handling
			AST __t9 = _t;
			AST tmp3_AST_in = (AST)_t;
			match(_t,ASIG);
			_t = _t.getFirstChild();
			a = (AST)_t;
			match(_t,IDENT);
			_t = _t.getNextSibling();
			b = _t==ASTNULL ? null : (AST)_t;
			r=expresion(_t);
			_t = _retTree;
			_t = __t9;
			_t = _t.getNextSibling();
			
					actualizarVariables(a,b);
					System.out.println("Asignacion: " + a.getText() + " vale " + r);
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final Integer  expresion(AST _t) throws RecognitionException {
		Integer valor=0;;
		
		AST expresion_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST j = null;
		AST f = null;
		AST i = null;
		Integer r,s;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case MAS:
			{
				AST __t11 = _t;
				AST tmp4_AST_in = (AST)_t;
				match(_t,MAS);
				_t = _t.getFirstChild();
				r=expresion(_t);
				_t = _retTree;
				s=expresion(_t);
				_t = _retTree;
				_t = __t11;
				_t = _t.getNextSibling();
				valor = r + s;
				break;
			}
			case MENOS:
			{
				AST __t12 = _t;
				AST tmp5_AST_in = (AST)_t;
				match(_t,MENOS);
				_t = _t.getFirstChild();
				r=expresion(_t);
				_t = _retTree;
				s=expresion(_t);
				_t = _retTree;
				_t = __t12;
				_t = _t.getNextSibling();
				valor = r - s;
				break;
			}
			case POR:
			{
				AST __t13 = _t;
				AST tmp6_AST_in = (AST)_t;
				match(_t,POR);
				_t = _t.getFirstChild();
				r=expresion(_t);
				_t = _retTree;
				s=expresion(_t);
				_t = _retTree;
				_t = __t13;
				_t = _t.getNextSibling();
				valor = r * s;
				break;
			}
			case DIV:
			{
				AST __t14 = _t;
				AST tmp7_AST_in = (AST)_t;
				match(_t,DIV);
				_t = _t.getFirstChild();
				r=expresion(_t);
				_t = _retTree;
				s=expresion(_t);
				_t = _retTree;
				_t = __t14;
				_t = _t.getNextSibling();
				valor = r / s;
				break;
			}
			case NUMERO:
			{
				j = (AST)_t;
				match(_t,NUMERO);
				_t = _t.getNextSibling();
				valor = new Integer(j.getText());
				break;
			}
			case LLAMADA:
			{
				AST __t15 = _t;
				AST tmp8_AST_in = (AST)_t;
				match(_t,LLAMADA);
				_t = _t.getFirstChild();
				f = (AST)_t;
				match(_t,IDENT);
				_t = _t.getNextSibling();
				r=expresion(_t);
				_t = _retTree;
				_t = __t15;
				_t = _t.getNextSibling();
				valor = evaluarFuncion((AST)funciones.get(f.getText()), //expresion donde tengo que sustituir
																															(AST)parametros.get(f.getText()), //expresion que tengo que sustituir
																															r //expresion que quiero sustituir
																															);
				break;
			}
			case IDENT:
			{
				i = (AST)_t;
				match(_t,IDENT);
				_t = _t.getNextSibling();
				valor = evaluarVariable(i);
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
		return valor;
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
	
