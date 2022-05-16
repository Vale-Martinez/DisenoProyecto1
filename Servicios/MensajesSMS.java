package Servicios;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class MensajesSMS {
	// Find your Account SID and Auth Token at twilio.com/console
	// and set the environment variables. See http://twil.io/secure
	public static final String ACCOUNT_SID = "ACcf775e8848addc5ef4613e9666200b0b";
	public static final String AUTH_TOKEN = "67e03d6a0859652f42c7e44e1636ebcb";

	public static String envioSMS(String numeroTelefono) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		String palabraSecreta = palabraSecreta();
		Message message = Message.creator(
				// Numero al que se le va a enviar el mensaje
				new com.twilio.type.PhoneNumber(numeroTelefono),
				// Numero de Twilio
				new com.twilio.type.PhoneNumber("+19707166579"),
				"La palabra secreta para realizar su transaccion es: " + palabraSecreta).create();

		//System.out.println(message.getSid());
		return palabraSecreta;
	}

	public static String palabraSecreta() {
    	String AlphaNumericString = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    	 StringBuilder sb = new StringBuilder(5);
    	  
         for (int i = 0; i < 5; i++) {
   
             // generate a random number between
             // 0 to AlphaNumericString variable length
             int index = (int)(AlphaNumericString.length()* Math.random());
   
             // add Character one by one in end of sb
             sb.append(AlphaNumericString.charAt(index));
         }
         return sb.toString();
    }
}
