package envio.email;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

public class TesteEnvioEmail {
	
	private String userName = "ezandro.developer@gmail.com";
	private String password = "javaMail";
	
	@Test
	public void testeEmail() {
		
		try {
			String host = "smtp.gmail.com";
			
			Properties properties = new Properties();
			properties.put("mail.smtp.auth", "true"); // Autorização
			properties.put("mail.smtp.starttls", "true"); //Autenticação
			properties.put("mail.smtp.host", host); // Servidor Google Gmail
			properties.put("mail.smtp.port", "465"); // Porta do servidor
			properties.put("mail.smtp.socketFactory.port", "465"); // Especifica a porta a ser conectada pelo socket
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // Classe socket de conexão ao SMTP
			
			Session session = Session.getInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, password);
				}
			});
			Address[] toUser = InternetAddress.parse("ezandro.developer@gmail.com, ezandrobueno@yahoo.com.br, ezandro.bueno@gmail.com");
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(userName)); // E-mail de origem
			message.setRecipients(Message.RecipientType.TO, toUser); // E-mail de destino
			message.setSubject("Envio de e-mail com JavaMail utilizando Gmail");
			message.setText("Olá, programador! Você acaba de receber um e-mail enviado com JavaMail utilizando Gmail.");
			
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
