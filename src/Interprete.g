header{
	import java.util.*;
	import antlr.*;
}
class Interprete extends TreeParser; 
options{
   importVocab = Anasint;	
}
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
	
}
ordenes: #(ORDENES (orden)+)
	;
	
orden {Integer r;} : 
	def_funcion
	| asignacion
	| r=e:expresion {;System.out.println("Expresion: " + e.toStringTree() + " vale " + r);}
	;
	
def_funcion {Integer r;} : 
	#(DEF_FUNCION a:IDENT b:IDENT r=c:expresion) {
		actualizarFunciones(a,b,c);
		System.out.println("Funcion: " + a.getText() + " (" + b.toStringTree() + ") vale " + c.toStringTree());
		}
	;

asignacion {Integer r;} : 
	#(ASIG a:IDENT r=b:expresion){
		actualizarVariables(a,b);
		System.out.println("Asignacion: " + a.getText() + " vale " + r);
	}	
	;

expresion returns [Integer valor=0;] {Integer r,s;} : 
	#(MAS r=expresion s=expresion) {valor = r + s;}
	| #(MENOS r=expresion s=expresion) {valor = r - s;}
	| #(POR r=expresion s=expresion) {valor = r * s;}
	| #(DIV r=expresion s=expresion) {valor = r / s;}
	| j:NUMERO {valor = new Integer(j.getText());}
	| #(LLAMADA f:IDENT r=expresion) {valor = evaluarFuncion((AST)funciones.get(f.getText()), //expresion donde tengo que sustituir
																											(AST)parametros.get(f.getText()), //expresion que tengo que sustituir
																											r //expresion que quiero sustituir
																											);} 
	| i:IDENT {valor = evaluarVariable(i);}
	;