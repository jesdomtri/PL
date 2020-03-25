import java.io.FileInputStream;
import java.io.FileNotFoundException;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.collections.AST;
import antlr.debug.misc.ASTFrame;

public class Principal {

	public static void main(String[] args) {
		try {
			FileInputStream f = new FileInputStream(args[0]);
			Analex analex = new Analex(f);
			Anasint anasint = new Anasint(analex);

			// Analisis lexico-sintactico
			anasint.ordenes();
			AST a = anasint.getAST();
			ASTFrame frame = new ASTFrame(args[0], a);
			frame.setVisible(true);

			// Analisis semantico
			Anasem anasem = new Anasem();
			System.out.println("---- SEMANTICO ----");
			for (String string : anasem.ordenes(a)) {
				System.out.println(string);
			}

			// Interpretacion
			Integer cont = 0;
			for (String string : anasem.ordenes(a)) {
				if (string.contains("ERROR")) {
					cont++;
				}
			}
			System.out.println("\n---- INTERPRETE ----");
			if (cont != 0)
				System.out.println("\nErrores semanticos impiden interpretar");
			else {
				Interprete interprete = new Interprete();
				interprete.ordenes(a);
			}

			// Compilacion
			System.out.println("\n---- COMPILADOR ----");
			if (cont != 0)
				System.out.println("\nErrores semanticos impiden compilar");
			else {
				Compilador compilador = new Compilador();
				compilador.ordenes(a);
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (TokenStreamException e) {
			System.out.println("Error in lexical analysis");
		} catch (RecognitionException e) {
			System.out.println("Error in parser analysis");
		}
	}
}
