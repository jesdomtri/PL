class Analex extends Lexer;

options{
	importVocab = Anasint;
}
BT: (' '|'\t') {$setType(Token.SKIP);};
SL: "\n" { newline(); $setType(Token.SKIP);};

protected DIGITO: ('0'..'9');
protected LETRA: ('a'..'z');

IDENT: LETRA(LETRA|DIGITO)* ;
NUMERO: (DIGITO)+ ;
ASIG: '=' ;
PyC: ';' ;
PA: '(' ;
PC: ')' ;
MAS: '+' ;
MENOS: '-';
POR: '*' ;
DIV: '/' ;