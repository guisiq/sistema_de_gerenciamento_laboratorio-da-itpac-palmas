package br.com.itpacpalmas.api_sig_lab_itpac.services;
import java.io.InputStream;

import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;



public class EmailService {
	
	
	private String usuario;
	private String senha;
	private String assunto;
	private String emailDestino;
	private String mensagem;
	
	public static void main(String[] args) {
		EmailService email = new EmailService("davidnpjs@gmail.com", "Mandando mailzin", "Se liga manin vou atras de tu");
		System.out.println(email.enviar());
		
	}
	
	public EmailService(String emailDestino, String assunto, String mensagem) {
		this.emailDestino = emailDestino;
		this.assunto = assunto;
		this.mensagem = mensagem;
		
		Properties prop = new Properties();
		try (InputStream is = this.getClass().getResourceAsStream("/br/com/itpacpalmas/api_sig_lab_itpac/config/email.properties")){
			prop.load(is);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("\n\n\n\n NÃO FOI ENCONTRADO O ARQUIVO email.properties na pasta config. \n\n\n\n");
		}
		
		this.usuario = prop.getProperty("usuario");
		this.senha = prop.getProperty("senha");
	}
	
	
	public boolean enviar() {
		
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true"); // TLS

		Session session = Session.getInstance(prop, 
				new jakarta.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(getUsuario(), getSenha());
					}
				});
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(getUsuario()+"@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(getEmailDestino()));
			message.setSubject(getAssunto());
			message.setText(getMensagem());

			// enviando o email
			Transport.send(message);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			System.out.println("aqui ta o erro");
			return false;
		}		
	}

	private String getUsuario() {
		return usuario;
	}

	private void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	private String getSenha() {
		return senha;
	}

	private void setSenha(String senha) {
		this.senha = senha;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getEmailDestino() {
		return emailDestino;
	}

	public void setEmailDestino(String emailDestino) {
		this.emailDestino = emailDestino;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
}
