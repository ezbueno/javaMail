package javaMail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class SendEmail {
	
	private String userName = "*****@gmail.com";
	private String password = "*****";
	private String nomeRemetente = "Ezandro Bueno";
	private String listaDestinatarios = "";
	private String assuntoEmail = "";
	private String textoEmail = "";
	
	public SendEmail(String nomeRemetente, String listaDestinatarios, String assuntoEmail, String textoEmail) {
		this.nomeRemetente = nomeRemetente;
		this.listaDestinatarios = listaDestinatarios;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
	}
	
	public void enviarEmail(boolean envioHTML) throws Exception {
		String host = "smtp.gmail.com";
		
		Properties properties = new Properties();
		properties.put("mail.smtp.ssl.trust", "*"); // Faz a autenticação utilizando o certificado SSL
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
		Address[] toUser = InternetAddress.parse(listaDestinatarios);
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, nomeRemetente)); // E-mail de origem e nome do Remetente
		message.setRecipients(Message.RecipientType.TO, toUser); // E-mail de destino
		message.setSubject(assuntoEmail); // Assunto do E-mail
		
		if (envioHTML) {
			message.setContent(textoEmail, "text/html; charset=UTF-8"); // Texto do e-mail
		} else {
			message.setText(textoEmail); 
		}
		Transport.send(message);
	}
		
	public void enviarEmailAnexo(boolean envioHTML) throws Exception {
		String host = "smtp.gmail.com";
		
		Properties properties = new Properties();
		properties.put("mail.smtp.ssl.trust", "*"); // Faz a autenticação utilizando o certificado SSL
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
		Address[] toUser = InternetAddress.parse(listaDestinatarios);
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, nomeRemetente)); // E-mail de origem e nome do Remetente
		message.setRecipients(Message.RecipientType.TO, toUser); // E-mail de destino
		message.setSubject(assuntoEmail); // Assunto do E-mail
		
		
		// Parte 1: texto e a descrição do e-mail
		MimeBodyPart corpoEmail = new MimeBodyPart();
		
		if (envioHTML) {
			corpoEmail.setContent(textoEmail, "text/html; charset=UTF-8"); // Texto do e-mail
		} else {
			corpoEmail.setText(textoEmail); 
		}
		
		List<FileInputStream> arquivos = new ArrayList<FileInputStream>();
		arquivos.add(simuladorPDF());
		arquivos.add(simuladorPDF());
		arquivos.add(simuladorPDF());
		arquivos.add(simuladorPDF());
		
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(corpoEmail);
		
		int cont = 0;
		
		for (FileInputStream fileInputStream : arquivos) {
			// Parte 2: anexa o(s) arquivo(s) em formato PDF
			MimeBodyPart anexoEmail = new MimeBodyPart();
			// Caso haja arquivo gravado no BD, substitua o fileInputStream por esse arquivo gravado no BD
			anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(fileInputStream, "application/pdf")));
			anexoEmail.setFileName("arquivoAnexo" + (cont + 1) + ".pdf");
			
			multipart.addBodyPart(anexoEmail);
			
			cont++;
		}
		
		message.setContent(multipart);
		
		Transport.send(message);
	}
	
	
	 /*
	  * Este método simula o PDF ou qualquer arquivo que possa ser enviado por anexo no e-mail
	  * Você pode pegar o arquivo no seu banco de dados, base64, byte[], Stream de arquivos,...
	  * 
	  * Retorna um arquivo PDF em branco com o texto do Parágrafo de exemplo
	  */
	
	private FileInputStream simuladorPDF() throws Exception {
		Document document = new Document();
		File file = new File("arquivo.pdf");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph("Você recebeu um arquivo PDF"));
		document.close();
		
		return new FileInputStream(file);
	}
}
