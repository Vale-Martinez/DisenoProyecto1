package Atenticacion;

public class Encriptacion {

	static final String abc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!#$%*._";
	static final int numero = 50;

	public static String CesarCifradoabc(String frase) {
		String res="";
		for (int i = 0; i < frase.length(); i++) {
			char chr = frase.charAt(i);
			int indexabc = abc.indexOf(chr);
			if (abcNormal(chr)) {
				res+= reemplazarCifrado(indexabc);
			}
		}
		return res;
	}

	private static String reemplazarCifrado(int indexabc) {
		String encriptado = "";
		char code;
		if (indexabc + numero >= abc.length()) {
			code = abc.charAt((indexabc + numero) % abc.length());
			encriptado += code;
		} else {
			code = abc.charAt((indexabc + numero));
			encriptado += code;
		}
		return encriptado;
	}

	public static String CesarDescifradoabc(String frase) {
		String res="";
		for (int i = 0; i < frase.length(); i++) {
			char chr = frase.charAt(i);
			int indexabc = abc.indexOf(chr);
			if (abcNormal(chr)) {
				res+= reemplazarDecifrado(indexabc);
			}
		}
		return res;
	}
	
	private static String reemplazarDecifrado(int indexabc) {
		String encriptado = "";
		char code;
		if (indexabc - numero < 0) {
			code = abc.charAt(abc.length() + (indexabc - numero)) ;
			encriptado += code;
		} else {
			code = abc.charAt((indexabc - numero)) ;
			encriptado += code;
		}
		return encriptado;
	}

	// revisa que el caracter sea una letra.
	private static boolean abcNormal(char c) {
		return (abc.contains(String.valueOf(c)));
	}

}
