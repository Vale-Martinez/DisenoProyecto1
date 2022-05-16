package Validacion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpresionRegular {


	public static boolean validarEmail(String email) {
		String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		
		return matcher.matches();
	}

	public static boolean validarPin(String pin) {
		String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!#$%*.]).{6,6}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(pin);
		
		return matcher.matches();

	}

	public static boolean validarTelefono(int numTelefono) {
		String tlfStr = String.valueOf(numTelefono);
		String regex = "^[0-9]{8}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(tlfStr);
		
		return matcher.matches();
	}
}
