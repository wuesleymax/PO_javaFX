package model;

import javafx.beans.property.*;

public class Cliente {
	private final IntegerProperty id;
	private final StringProperty nome;
	private StringProperty email;
	private StringProperty telefone;
	private StringProperty endereco;
	
	public Cliente() {
		this.id = new SimpleIntegerProperty();
		this.nome = new SimpleStringProperty();
		this.email = new SimpleStringProperty();
		this.telefone = new SimpleStringProperty();
		this.endereco = new SimpleStringProperty();
	}

	public Cliente(String nome, String email, String telefone, String endereco) {
		this();
		this.nome.set(nome);
		this.email.set(email);
		this.telefone.set(telefone);
		this.endereco.set(endereco);
	}
	
	//Getters e Setters para propriedades 
	public int getId() {
		return id.get();
	}
	
	public void setId(int id) {
		this.id.set(id);
	}
	public IntegerProperty idProperty() {
		return id;
	}
	
	public String getNome() {
		return nome.get();
	}
	
	public void setNome(String nome) {
		this.nome.set(nome);
	}
	public StringProperty nomeProperty() {
		return nome;
	}
	
	public String getEmail() {
		return email.get();
	}
	
	public void setEmail(String email) {
		this.email.set(email);
	}
	public StringProperty emailProperty() {
		return email;
	}
	
	public String getTelefone() {
		return telefone.get();
	}
	
	public void setTelefone(String telefone) {
		this.telefone.set(telefone);
	}
	public StringProperty telefoneProperty() {
		return telefone;
	}
	
	public String getEndereco() {
		return endereco.get();
	}
	
	public void setEndereco(String endereco) {
		this.endereco.set(endereco);
	}
	public StringProperty enderecoProperty() {
		return endereco;
	}
	
	@Override
	public String toString() {
		return nome.get();
	}
	
}
