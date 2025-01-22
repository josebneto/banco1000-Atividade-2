// Classe que representa um cliente do banco
package br.ufrn.bti.banco1000.model;

import java.util.ArrayList;

public class Cliente {
    private String nome; // Nome do cliente
    private String cpf; // CPF do cliente (único)
    private String senha; // Senha do cliente para autenticação
    private String email; // Email do cliente
    private String telefone; // Telefone do cliente
    private ArrayList<Conta> contas = new ArrayList<>(); // Lista de contas associadas ao cliente

    // Construtor da classe Cliente
    public Cliente(String nome, String cpf, String senha, String email, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.email = email;
        this.telefone = telefone;
    }

    // Retorna o nome do cliente
    public String getNome() {
        return this.nome;
    }

    // Retorna o CPF do cliente
    public String getCpf() {
        return this.cpf;
    }

    // Retorna a lista de contas associadas ao cliente
    public ArrayList<Conta> getContas() {
        return contas;
    }

    // Adiciona uma conta à lista de contas do cliente
    public void adicionarConta(Conta conta) {
        this.contas.add(conta);
    }

    // Retorna a senha do cliente
    public String getSenha() {
        return this.senha;
    }

    // Verifica se a senha fornecida corresponde à senha do cliente
    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }

    // Retorna o email do cliente
    public String getEmail() {
        return email;
    }

    // Retorna o telefone do cliente
    public String getTelefone() {
        return telefone;
    }

    // Representação textual do cliente para fins de depuração ou exibição
    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}
