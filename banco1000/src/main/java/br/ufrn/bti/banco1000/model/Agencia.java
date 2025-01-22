// Classe que representa uma agência bancária
package br.ufrn.bti.banco1000.model;

import java.util.ArrayList;
import java.util.List;

public class Agencia {
    private String codigoAgencia; // Código único da agência
    private List<Conta> contas; // Lista de contas associadas à agência
    private String nome; // Nome da agência

    // Construtor da classe Agencia
    public Agencia(String nome, String codigoAgencia) {
        this.nome = nome;
        this.codigoAgencia = codigoAgencia;
        this.contas = new ArrayList<>(); // Inicializa a lista de contas
    }

    // Retorna o nome da agência
    public String getNome() {
        return nome;
    }

    // Adiciona uma conta à lista de contas da agência
    public void registrarConta(Conta conta) {
        contas.add(conta);
    }

    // Busca uma conta pelo número da conta
    // Lança uma exceção se a conta não for encontrada
    public Conta buscarContaPorNumero(int numeroConta) throws ContaNaoEncontradaException {
        return contas.stream()
                .filter(conta -> conta.getNumeroConta() == numeroConta) // Filtra a lista comparando os números das contas
                .findFirst() // Retorna a primeira ocorrência
                .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada: " + numeroConta));
    }

    // Remove uma conta da agência pelo número da conta
    // Lança uma exceção se a conta não for encontrada
    public void removerConta(int numeroConta) throws ContaNaoEncontradaException {
        Conta conta = buscarContaPorNumero(numeroConta); // Busca a conta antes de removê-la
        contas.remove(conta);
    }

    // Retorna o código da agência
    public String getCodigoAgencia() {
        return codigoAgencia;
    }

    // Retorna a lista de contas associadas à agência
    public List<Conta> getContas() {
        return contas;
    }
}