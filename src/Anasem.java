// $ANTLR : "Anasem.g" -> "Anasem.java"$

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


public class Anasem extends antlr.TreeParser       implements AnasemTokenTypes
 {

	Map<String,Set<String>> m = new HashMap<>();
	
	void funs(AST k, AST f){
		String sk = k.getText();
		Set<String> sf = new HashSet<>();
		AST fh = f.getFirstChild();
		while(fh != null){
			if(fh.getType() == LLAMADA){
				AST fhh = fh.getFirstChild();
				sf.add(fhh.getText());
			}
			fh = fh.getNextSibling();
		}
		if(!m.containsKey(sk)){
				m.put(sk,sf);
		}else{
			m.remove(sk);
			m.put(sk,sf);
		}
	}
	
	Set<String> funsdec = new HashSet<>();
	
	void funsDeclaradas(AST k){
		String sk = k.getText();
		funsdec.add(sk);
	}
	
	
	String revisarPaP(AST a){
		String error = "Orden ejecutada correctamente";
		if(a.getType() == LLAMADA){
			String s = a.getFirstChild().getText();
			if(!m.containsKey(s)){
				error = "ERROR: " + s + " no esta declarada y se esta llamando";
			}else{
				Set<String> valores = m.get(s);
				for(String v : valores){
					if(!m.containsKey(v)){
						error = "ERROR: " + v + " es un valor de " + s + " pero no esta declarada antes";
					}
				}
			}
		}
		return error;
	}
public Anasem() {
	tokenNames = _tokenNames;
}

	public final List<String>  ordenes(AST _t) throws RecognitionException {
		List<String> errores = new ArrayList<>();;
		
		AST ordenes_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST o = null;
		
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
					o = _t==ASTNULL ? null : (AST)_t;
					orden(_t);
					_t = _retTree;
					errores.add(revisarPaP(o));
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
		return errores;
	}
	
	public final void orden(AST _t) throws RecognitionException {
		
		AST orden_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case DEF_FUNCION:
			{
				{
				def_funcion(_t);
				_t = _retTree;
				}
				break;
			}
			case ASIG:
			{
				{
				asignacion(_t);
				_t = _retTree;
				}
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
				{
				expresion(_t);
				_t = _retTree;
				}
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
		AST k = null;
		AST f = null;
		
		try {      // for error handling
			AST __t10 = _t;
			AST tmp2_AST_in = (AST)_t;
			match(_t,DEF_FUNCION);
			_t = _t.getFirstChild();
			k = (AST)_t;
			match(_t,IDENT);
			_t = _t.getNextSibling();
			AST tmp3_AST_in = (AST)_t;
			match(_t,IDENT);
			_t = _t.getNextSibling();
			f = _t==ASTNULL ? null : (AST)_t;
			expresion(_t);
			_t = _retTree;
			_t = __t10;
			_t = _t.getNextSibling();
			
				funs(k,f); 
				funsDeclaradas(k);
				
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void asignacion(AST _t) throws RecognitionException {
		
		AST asignacion_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t12 = _t;
			AST tmp4_AST_in = (AST)_t;
			match(_t,ASIG);
			_t = _t.getFirstChild();
			AST tmp5_AST_in = (AST)_t;
			match(_t,IDENT);
			_t = _t.getNextSibling();
			expresion(_t);
			_t = _retTree;
			_t = __t12;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void expresion(AST _t) throws RecognitionException {
		
		AST expresion_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST i = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case MAS:
			{
				AST __t14 = _t;
				AST tmp6_AST_in = (AST)_t;
				match(_t,MAS);
				_t = _t.getFirstChild();
				expresion(_t);
				_t = _retTree;
				expresion(_t);
				_t = _retTree;
				_t = __t14;
				_t = _t.getNextSibling();
				break;
			}
			case MENOS:
			{
				AST __t15 = _t;
				AST tmp7_AST_in = (AST)_t;
				match(_t,MENOS);
				_t = _t.getFirstChild();
				expresion(_t);
				_t = _retTree;
				expresion(_t);
				_t = _retTree;
				_t = __t15;
				_t = _t.getNextSibling();
				break;
			}
			case POR:
			{
				AST __t16 = _t;
				AST tmp8_AST_in = (AST)_t;
				match(_t,POR);
				_t = _t.getFirstChild();
				expresion(_t);
				_t = _retTree;
				expresion(_t);
				_t = _retTree;
				_t = __t16;
				_t = _t.getNextSibling();
				break;
			}
			case DIV:
			{
				AST __t17 = _t;
				AST tmp9_AST_in = (AST)_t;
				match(_t,DIV);
				_t = _t.getFirstChild();
				expresion(_t);
				_t = _retTree;
				expresion(_t);
				_t = _retTree;
				_t = __t17;
				_t = _t.getNextSibling();
				break;
			}
			case NUMERO:
			{
				AST tmp10_AST_in = (AST)_t;
				match(_t,NUMERO);
				_t = _t.getNextSibling();
				break;
			}
			case LLAMADA:
			{
				AST __t18 = _t;
				AST tmp11_AST_in = (AST)_t;
				match(_t,LLAMADA);
				_t = _t.getFirstChild();
				i = (AST)_t;
				match(_t,IDENT);
				_t = _t.getNextSibling();
				expresion(_t);
				_t = _retTree;
				_t = __t18;
				_t = _t.getNextSibling();
				break;
			}
			case IDENT:
			{
				AST tmp12_AST_in = (AST)_t;
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
	
