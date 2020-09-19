package br.com.luizalabs.util.exceptionhandler;

public class Erro {
	private String messageForUser;
	private String nameField;
	private String messageForDeveloper;
	
	public Erro(String messageForUser,String  messageForDeveloper,String nameField) {		
		this.messageForUser = messageForUser;
		this.nameField = nameField;
		this.messageForDeveloper = messageForDeveloper;
	}
	
	public String getMessageForUser() {
		return messageForUser;
	}
	public void setMessageForUser(String messageForUser) {
		this.messageForUser = messageForUser;
	}
	public String getMessageForDeveloper() {
		return messageForDeveloper;
	}
	public void setMessageForDeveloper(String messageForDeveloper) {
		this.messageForDeveloper = messageForDeveloper;
	}

	public String getNameField() {
		return nameField;
	}

	public void setNameField(String nameField) {
		this.nameField = nameField;
	}
	
}
