// $ANTLR : "Anasint.g" -> "Anasint.java"$

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import java.util.Hashtable;
import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;

public class Anasint extends antlr.LLkParser       implements AnasintTokenTypes
 {

protected Anasint(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public Anasint(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected Anasint(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public Anasint(TokenStream lexer) {
  this(lexer,1);
}

public Anasint(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

	public final void ordenes() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST ordenes_AST = null;
		
		try {      // for error handling
			{
			int _cnt3=0;
			_loop3:
			do {
				if ((LA(1)==IDENT||LA(1)==PA||LA(1)==NUMERO)) {
					orden();
					astFactory.addASTChild(currentAST, returnAST);
					match(PyC);
				}
				else {
					if ( _cnt3>=1 ) { break _loop3; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt3++;
			} while (true);
			}
			match(Token.EOF_TYPE);
			if ( inputState.guessing==0 ) {
				ordenes_AST = (AST)currentAST.root;
				ordenes_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ORDENES,"ORDENES")).add(ordenes_AST));
				currentAST.root = ordenes_AST;
				currentAST.child = ordenes_AST!=null &&ordenes_AST.getFirstChild()!=null ?
					ordenes_AST.getFirstChild() : ordenes_AST;
				currentAST.advanceChildToEnd();
			}
			ordenes_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_0);
			} else {
			  throw ex;
			}
		}
		returnAST = ordenes_AST;
	}
	
	public final void orden() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST orden_AST = null;
		
		try {      // for error handling
			boolean synPredMatched6 = false;
			if (((LA(1)==IDENT))) {
				int _m6 = mark();
				synPredMatched6 = true;
				inputState.guessing++;
				try {
					{
					match(IDENT);
					match(PA);
					match(IDENT);
					match(PC);
					match(ASIG);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched6 = false;
				}
				rewind(_m6);
inputState.guessing--;
			}
			if ( synPredMatched6 ) {
				def_funcion();
				astFactory.addASTChild(currentAST, returnAST);
				orden_AST = (AST)currentAST.root;
			}
			else {
				boolean synPredMatched8 = false;
				if (((LA(1)==IDENT))) {
					int _m8 = mark();
					synPredMatched8 = true;
					inputState.guessing++;
					try {
						{
						match(IDENT);
						match(ASIG);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched8 = false;
					}
					rewind(_m8);
inputState.guessing--;
				}
				if ( synPredMatched8 ) {
					asignacion();
					astFactory.addASTChild(currentAST, returnAST);
					orden_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==IDENT||LA(1)==PA||LA(1)==NUMERO)) {
					expresion();
					astFactory.addASTChild(currentAST, returnAST);
					orden_AST = (AST)currentAST.root;
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				if (inputState.guessing==0) {
					reportError(ex);
					recover(ex,_tokenSet_1);
				} else {
				  throw ex;
				}
			}
			returnAST = orden_AST;
		}
		
	public final void def_funcion() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST def_funcion_AST = null;
		
		try {      // for error handling
			AST tmp3_AST = null;
			tmp3_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp3_AST);
			match(IDENT);
			match(PA);
			AST tmp5_AST = null;
			tmp5_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp5_AST);
			match(IDENT);
			match(PC);
			match(ASIG);
			expresion();
			astFactory.addASTChild(currentAST, returnAST);
			if ( inputState.guessing==0 ) {
				def_funcion_AST = (AST)currentAST.root;
				def_funcion_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(DEF_FUNCION,"DEF_FUNCION")).add(def_funcion_AST));
				currentAST.root = def_funcion_AST;
				currentAST.child = def_funcion_AST!=null &&def_funcion_AST.getFirstChild()!=null ?
					def_funcion_AST.getFirstChild() : def_funcion_AST;
				currentAST.advanceChildToEnd();
			}
			def_funcion_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = def_funcion_AST;
	}
	
	public final void asignacion() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST asignacion_AST = null;
		
		try {      // for error handling
			AST tmp8_AST = null;
			tmp8_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp8_AST);
			match(IDENT);
			AST tmp9_AST = null;
			tmp9_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp9_AST);
			match(ASIG);
			expresion();
			astFactory.addASTChild(currentAST, returnAST);
			asignacion_AST = (AST)currentAST.root;
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_1);
			} else {
			  throw ex;
			}
		}
		returnAST = asignacion_AST;
	}
	
	public final void expresion() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expresion_AST = null;
		
		try {      // for error handling
			boolean synPredMatched13 = false;
			if (((LA(1)==IDENT||LA(1)==PA||LA(1)==NUMERO))) {
				int _m13 = mark();
				synPredMatched13 = true;
				inputState.guessing++;
				try {
					{
					expresion1();
					match(MAS);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched13 = false;
				}
				rewind(_m13);
inputState.guessing--;
			}
			if ( synPredMatched13 ) {
				expresion1();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp10_AST = null;
				tmp10_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp10_AST);
				match(MAS);
				expresion();
				astFactory.addASTChild(currentAST, returnAST);
				expresion_AST = (AST)currentAST.root;
			}
			else {
				boolean synPredMatched15 = false;
				if (((LA(1)==IDENT||LA(1)==PA||LA(1)==NUMERO))) {
					int _m15 = mark();
					synPredMatched15 = true;
					inputState.guessing++;
					try {
						{
						expresion1();
						match(MENOS);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched15 = false;
					}
					rewind(_m15);
inputState.guessing--;
				}
				if ( synPredMatched15 ) {
					expresion1();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp11_AST = null;
					tmp11_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp11_AST);
					match(MENOS);
					expresion();
					astFactory.addASTChild(currentAST, returnAST);
					expresion_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==IDENT||LA(1)==PA||LA(1)==NUMERO)) {
					expresion1();
					astFactory.addASTChild(currentAST, returnAST);
					expresion_AST = (AST)currentAST.root;
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				if (inputState.guessing==0) {
					reportError(ex);
					recover(ex,_tokenSet_2);
				} else {
				  throw ex;
				}
			}
			returnAST = expresion_AST;
		}
		
	public final void expresion1() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expresion1_AST = null;
		
		try {      // for error handling
			boolean synPredMatched18 = false;
			if (((LA(1)==IDENT||LA(1)==PA||LA(1)==NUMERO))) {
				int _m18 = mark();
				synPredMatched18 = true;
				inputState.guessing++;
				try {
					{
					expresion2();
					match(POR);
					}
				}
				catch (RecognitionException pe) {
					synPredMatched18 = false;
				}
				rewind(_m18);
inputState.guessing--;
			}
			if ( synPredMatched18 ) {
				expresion2();
				astFactory.addASTChild(currentAST, returnAST);
				AST tmp12_AST = null;
				tmp12_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp12_AST);
				match(POR);
				expresion1();
				astFactory.addASTChild(currentAST, returnAST);
				expresion1_AST = (AST)currentAST.root;
			}
			else {
				boolean synPredMatched20 = false;
				if (((LA(1)==IDENT||LA(1)==PA||LA(1)==NUMERO))) {
					int _m20 = mark();
					synPredMatched20 = true;
					inputState.guessing++;
					try {
						{
						expresion2();
						match(DIV);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched20 = false;
					}
					rewind(_m20);
inputState.guessing--;
				}
				if ( synPredMatched20 ) {
					expresion2();
					astFactory.addASTChild(currentAST, returnAST);
					AST tmp13_AST = null;
					tmp13_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp13_AST);
					match(DIV);
					expresion1();
					astFactory.addASTChild(currentAST, returnAST);
					expresion1_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==IDENT||LA(1)==PA||LA(1)==NUMERO)) {
					expresion2();
					astFactory.addASTChild(currentAST, returnAST);
					expresion1_AST = (AST)currentAST.root;
				}
				else {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			catch (RecognitionException ex) {
				if (inputState.guessing==0) {
					reportError(ex);
					recover(ex,_tokenSet_3);
				} else {
				  throw ex;
				}
			}
			returnAST = expresion1_AST;
		}
		
	public final void expresion2() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expresion2_AST = null;
		
		try {      // for error handling
			switch ( LA(1)) {
			case NUMERO:
			{
				AST tmp14_AST = null;
				tmp14_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp14_AST);
				match(NUMERO);
				expresion2_AST = (AST)currentAST.root;
				break;
			}
			case PA:
			{
				match(PA);
				expresion();
				astFactory.addASTChild(currentAST, returnAST);
				match(PC);
				expresion2_AST = (AST)currentAST.root;
				break;
			}
			default:
				boolean synPredMatched23 = false;
				if (((LA(1)==IDENT))) {
					int _m23 = mark();
					synPredMatched23 = true;
					inputState.guessing++;
					try {
						{
						match(IDENT);
						match(PA);
						}
					}
					catch (RecognitionException pe) {
						synPredMatched23 = false;
					}
					rewind(_m23);
inputState.guessing--;
				}
				if ( synPredMatched23 ) {
					AST tmp17_AST = null;
					tmp17_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp17_AST);
					match(IDENT);
					match(PA);
					expresion();
					astFactory.addASTChild(currentAST, returnAST);
					match(PC);
					if ( inputState.guessing==0 ) {
						expresion2_AST = (AST)currentAST.root;
						expresion2_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(LLAMADA,"LLAMADA")).add(expresion2_AST));
						currentAST.root = expresion2_AST;
						currentAST.child = expresion2_AST!=null &&expresion2_AST.getFirstChild()!=null ?
							expresion2_AST.getFirstChild() : expresion2_AST;
						currentAST.advanceChildToEnd();
					}
					expresion2_AST = (AST)currentAST.root;
				}
				else if ((LA(1)==IDENT)) {
					AST tmp20_AST = null;
					tmp20_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp20_AST);
					match(IDENT);
					expresion2_AST = (AST)currentAST.root;
				}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			if (inputState.guessing==0) {
				reportError(ex);
				recover(ex,_tokenSet_4);
			} else {
			  throw ex;
			}
		}
		returnAST = expresion2_AST;
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
	
	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 128L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 1152L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 13440L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 62592L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	
	}
