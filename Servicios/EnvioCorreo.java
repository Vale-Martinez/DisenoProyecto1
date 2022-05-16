/**
 * 
 */
package Servicios;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * @author valem y nathb
 *
 */
public class EnvioCorreo {

	// funcion que manda el correo

	public static boolean EnviarCorreo(String destinatario, String numeroCuenta) {
		// Correo desde donde se va a mandar
		String username = "trabajoa.ATI@gmail.com";
		String password = "nbt123nbt";

		// Autenticación
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		// Crear el email
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress("no-reply@gmail.com"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			// Título para el correo
			msg.setSubject("Invalidación de la cuenta");

			Multipart emailContent = new MimeMultipart();

			// Crea el texto del mensaje
			MimeBodyPart text = new MimeBodyPart();
			text.setText("Se le informa que su cuenta numero " + numeroCuenta
					+ " ha sido inactivada por seguridad; ya que se ha ingresado en dos ocasiones seguidas de manera erronea el pin o la palabra de seguridad. \nComuniquese con nosotros por medio de un correo electornico con el fin de reactivar su cuenta.");

			// Agrega las partes al contenido
			emailContent.addBodyPart(text);

			// Agrega el contenido al mensaje
			msg.setContent(emailContent);

			Transport.send(msg);
			System.out.println("Sent message");
		} catch (MessagingException e) {
			System.out.println(e);
			System.out.println("Error al enviar el correo.");
			return false;
		}
		return true;
	}
}
