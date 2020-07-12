package envio.email;

import org.junit.Test;

import javaMail.SendEmail;

public class TesteEnvioEmail {
	
	@Test
	public void testeEmail() throws Exception {
		
		StringBuilder stringBuilderTextoEmail = new StringBuilder();
		stringBuilderTextoEmail.append("Olá! <br/><br/>");
		stringBuilderTextoEmail.append("Você está recebendo o link para criar uma conta no Gmail. <br/><br/>");
		stringBuilderTextoEmail.append("Clique no botão abaixo: <br/><br/>");
		stringBuilderTextoEmail.append("<a target=\"_blank\" href=\"https://www.google.com/intl/pt-BR/gmail/about/\" style=\"color: #2525a7; padding: 14px 25px; text-align: center; text-decoration: none; display: inline-block; border-radius: 30px; font-size: 20px; font-family: courier; border: 3px solid green; background-color: #99DA39\">Criar uma conta</a><br/><br/>");
		
		stringBuilderTextoEmail.append("<span style=\"font-size: 13px\">Atenciosamente, <br/></span>");
		stringBuilderTextoEmail.append("<span style=\"font-size: 13px\">Ezandro Bueno <br/></span>");
		
		SendEmail sendEmail = new SendEmail("Ezandro Bueno", 
				                            "******@gmail.com, ******@yahoo.com.br, ******@gmail.com", 
				                            "Testando e-mail com JavaMail", 
				                            stringBuilderTextoEmail.toString());
		
		//sendEmail.enviarEmail(true);
		sendEmail.enviarEmailAnexo(true);
		
		/*
		 * Caso o e-mail não esteja sendo enviado, então coloque um tempo de espera
		 * ATENÇÃO : Só pode ser usado para testes.
		 */
		Thread.sleep(5000); // Tempo de espera de 5s
		
	}
}
