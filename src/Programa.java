import java.io.*;

public class Programa {
	public static void main(String[] args) {
		b = 8;
		a = 3 + b;
		System.out.println(3 + 2);
		System.out.println(600 / 5);
		c = 4 * 6 + 1793;
		System.out.println(5 * 9 / 7);
		System.out.println(f(a));
		System.out.println(f(f(2)));
		System.out.println(f(a + 1));
		System.out.println(a + b * f(2));
		System.out.println(n(2 + 56));
		System.out.println(k(4));
	}

	public static Integer a;
	public static Integer b;
	public static Integer c;

	public static Integer f(Integer a) {
		return 10 * a;
	}

	public static Integer n(Integer x) {
		return f(a) + 1;
	}

	public static Integer k(Integer g) {
		return n(g) + 1;
	}
}