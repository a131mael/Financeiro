package org.aaf.financeiro.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;

import org.aaf.financeiro.model.Boleto;

public class EnviadorEmail {

	public static void main(String[] args) {
		//EnviadorEmail.enviarEmail("Titulo" , "Corpo do email" , null, "fidencioariane@gmail.com" );
	}

	public static void enviarEmail(String titulo, String corpoEmailTexto, ByteArrayInputStream arrayInputStream, String destinatarios, final String remetente, final String senhaRemetente) {
		String returnStatement = null;
		try {
			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(remetente, senhaRemetente);
				}
			};
			Session session = Session.getInstance(getProperties(), auth);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(remetente));
			message.setRecipients(Message.RecipientType.TO, getDestinatarios(destinatarios));
			message.setSentDate(new Date());
			message.setSubject(titulo);
			returnStatement = "The e-mail was sent successfully";
			
	        Multipart multipart = new MimeMultipart();
			if (arrayInputStream != null && arrayInputStream instanceof ByteArrayInputStream) {
			    // create the second message part with the attachment from a OutputStrean
			    MimeBodyPart attachment= new MimeBodyPart();
			    ByteArrayDataSource ds = new ByteArrayDataSource(arrayInputStream, "application/pdf"); 
			    attachment.setDataHandler(new DataHandler(ds));
			    attachment.setFileName("boleto.pdf");
			    multipart.addBodyPart(attachment);
			}
			
			MimeBodyPart corpoEmail= new MimeBodyPart();
			corpoEmail.setText(corpoEmailTexto,"utf-8", "html");
			multipart.addBodyPart(corpoEmail);

		    message.setContent(multipart);
		    
			Transport.send(message);
			System.out.println(returnStatement);
		} catch (Exception e) {
			
			returnStatement = "error in sending mail";
			//e.printStackTrace();
			System.out.println("Nao foi possivel enviar email para : " + destinatarios);
		}
	}
	
	public static void enviarEmail(String titulo, String corpoEmailTexto, ByteArrayInputStream arrayInputStream, ByteArrayInputStream assinatura, String destinatarios, final String remetente, final String senhaRemetente) {
		String returnStatement = null;
		try {
			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(remetente, senhaRemetente);
				}
			};
			Session session = Session.getInstance(getProperties(), auth);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(remetente));
			message.setRecipients(Message.RecipientType.TO, getDestinatarios(destinatarios));
			message.setSentDate(new Date());
			message.setSubject(titulo);
			returnStatement = "The e-mail was sent successfully";
			
	        Multipart multipart = new MimeMultipart();
			if (arrayInputStream != null && arrayInputStream instanceof ByteArrayInputStream) {
			    // create the second message part with the attachment from a OutputStrean
			    MimeBodyPart attachment= new MimeBodyPart();
			    ByteArrayDataSource ds = new ByteArrayDataSource(arrayInputStream, "application/pdf"); 
			    attachment.setDataHandler(new DataHandler(ds));
			    attachment.setFileName("boleto.pdf");
			    multipart.addBodyPart(attachment);
			}
			
			MimeBodyPart corpoEmail= new MimeBodyPart();
			corpoEmail.setText(corpoEmailTexto,"utf-8", "html");
			multipart.addBodyPart(corpoEmail);
			
			if (assinatura != null && assinatura instanceof ByteArrayInputStream) {
			    // create the second message part with the attachment from a OutputStrean
			    MimeBodyPart attachment= new MimeBodyPart();
			    ByteArrayDataSource ds = new ByteArrayDataSource(assinatura, "image/png"); 
			    attachment.setDataHandler(new DataHandler(ds));
			    attachment.setFileName("assinatura.png");
			    multipart.addBodyPart(attachment);
			    
			}
			
		    message.setContent(multipart);
		    
			Transport.send(message);
			System.out.println(returnStatement);
		} catch (Exception e) {
			returnStatement = "error in sending mail";
			e.printStackTrace();
		}
	}
	
	private static InternetAddress[] getDestinatarios(String destinatarios){
		String[] recipientList = destinatarios.split(",");
		InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
		int counter = 0;
		for (String recipient : recipientList) {
		    try {
				recipientAddress[counter] = new InternetAddress(recipient.trim());
			} catch (AddressException e) {
				e.printStackTrace();
			}
		    counter++;
		}
		return recipientAddress;
	}

	public void enviarEmailList(String titulo, String corpoEmail, List<Boleto> boletos) {
		String returnStatement = null;
		try {
			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("a131mael@gmail.com", "129d15c41e95c1");
				}
			};
			Session session = Session.getInstance(getProperties(), auth);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("a131mael@gmail.com"));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress("a131mael@gmail.com"));
			message.setSentDate(new Date());
			message.setSubject(titulo);
			message.setText("corpoEmail");
			returnStatement = "The e-mail was sent successfully";

			Transport.send(message);
			System.out.println(returnStatement);
		} catch (Exception e) {
			returnStatement = "error in sending mail";
			e.printStackTrace();
		}
	}
	
	private static Properties getProperties() {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		return properties;
	}

	
}
