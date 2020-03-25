header{
 import java.util.*;
 import antlr.*;
}
class Anasem extends TreeParser;
options{
	importVocab=Anasint;
}
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
}
ordenes returns [List<String> errores = new ArrayList<>();] : #(ORDENES (o:orden {errores.add(revisarPaP(o));})+ )
	;
	
orden: (def_funcion)
	| (asignacion)
	| (expresion)
	;
	
def_funcion: #(DEF_FUNCION k:IDENT IDENT f:expresion)
	{
	funs(k,f); 
	funsDeclaradas(k);
	}
	;

asignacion: #(ASIG IDENT expresion)
	;

expresion: #(MAS expresion expresion)
	| #(MENOS expresion expresion)
	| #(POR expresion expresion)
	| #(DIV expresion expresion)
	| NUMERO
	| #(LLAMADA i:IDENT expresion) //{if(!funsdec.contains(i)){System.out.println(i + " no esta declarada");};}
	| IDENT
	;