class Anasint extends Parser;
options{
	buildAST = true;
}
tokens{
	ORDENES;
	DEF_FUNCION;
	LLAMADA;
}

ordenes: (orden PyC!)+ EOF!
	{#ordenes = #(#[ORDENES,"ORDENES"], ##);}
	;
	
orden : (IDENT PA IDENT PC ASIG)=> def_funcion
	| (IDENT ASIG)=> asignacion
	| expresion
	;
	
def_funcion: IDENT PA! IDENT PC! ASIG! expresion
	{#def_funcion = #(#[DEF_FUNCION,"DEF_FUNCION"], ##);}
	;

asignacion: IDENT ASIG^ expresion
	;

expresion: (expresion1 MAS) => expresion1 MAS^ expresion
	| (expresion1 MENOS) => expresion1 MENOS^ expresion
	| expresion1
	;
	
expresion1: (expresion2 POR) => expresion2 POR^ expresion1
	| (expresion2 DIV) => expresion2 DIV^ expresion1
	| expresion2
	;

expresion2: NUMERO
	| (IDENT PA)=> IDENT PA! expresion PC!
		{#expresion2 = #(#[LLAMADA, "LLAMADA"], ##);}
	| IDENT
	| PA! expresion PC!
	;