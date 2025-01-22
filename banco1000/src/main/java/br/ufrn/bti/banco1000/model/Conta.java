/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufrn.bti.banco1000.model;

import java.util.ArrayList;
import java.util.List;

/*
 * Classe abstrata que representa uma conta bancária.
 * Ela contém as informações básicas de uma conta, como o cliente, número da conta, tipo e saldo.
 * Além disso, ela gerencia as operações de depósito, saque e transferência, bem como as movimentações realizadas.
 */
public abstract class Conta {
    private Cliente cliente; // Cliente associado à conta
    private int agencia; // Número da agência da conta
    protected int numeroConta; // Número da conta
    private String tipo; // Tipo da conta (ex: Corrente, Poupança, etc.)
    protected double saldo; // Saldo da conta
    private ArrayList<Movimentacao> movimentacao = new ArrayList<>(); // Lista de movimentações realizadas na conta

    /*
     * Construtor da classe Conta, que inicializa os valores necessários para criar uma conta.
     */
    public Conta(Cliente cliente, int agencia, int numeroConta, String tipo, double saldo) {
        this.cliente = cliente;
        this.agencia = agencia;
        this.numeroConta = numeroConta;
        this.tipo = tipo;
        this.saldo = saldo;
    }

    // Métodos de acesso (getters) para os atributos da conta
    public Cliente getCliente() {
        return cliente;
    }

    public List<Movimentacao> getMovimentacao() {
        return movimentacao;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public int getAgencia() {
        return this.agencia;
    }

    public String getTipo() {
        return this.tipo;
    }

    /*
     * Método que realiza um depósito na conta.
     * Caso o valor seja negativo ou zero, lança uma exceção.
     */
    public void depositar(double valor) {
        if (valor <= 0) {
            throw new OperacaoInvalidaException("Valor do depósito deve ser positivo.");
        }
        saldo += valor;
    }

    /*
     * Método que realiza um saque na conta.
     * Se o saldo for suficiente, o valor é descontado e a movimentação de saque é registrada.
     * Caso contrário, retorna false.
     */
    public boolean sacar(double valor) {
        if (this.saldo - valor >= 0) {
            this.saldo -= valor;
            this.movimentacao.add(new Movimentacao("SAQUE", this.cliente, "Saque", valor));
            return true;
        }
        return false;
    }

    /*
     * Método para transferir um valor de uma conta para outra.
     * Se o saldo for suficiente, o valor é transferido e as movimentações de transferência são registradas.
     * Caso contrário, retorna false.
     */
    public boolean transferir(Conta conta, double valor) {
        if (this.saldo - valor >= 0) {
            this.sacar(valor);
            conta.depositar(valor);
            conta.movimentacao.add(new Movimentacao("TRANSFERENCIA", this.cliente, "Entrada por Transferência", valor));
            this.movimentacao.add(new Movimentacao("TRANSFERENCIA", this.cliente, "Saída por Transferência", valor));
            return true;
        }
        return false;
    }

    // Método que retorna o saldo atual da conta
    public double getSaldo() {
        return saldo;
    }

    // Representação em String da conta, útil para exibir suas informações
    @Override
    public String toString() {
        return "Conta{" +
                "numeroConta='" + numeroConta + '\'' +
                ", tipo='" + tipo + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}

// Exceções personalizadas para erros nas operações bancárias

class ContaNaoEncontradaException extends RuntimeException {
    public ContaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}

class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(String mensagem) {
        super(mensagem);
    }
}

class OperacaoInvalidaException extends RuntimeException {
    public OperacaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}

class LimiteDeSaquesExcedidoException extends RuntimeException {
    public LimiteDeSaquesExcedidoException(String mensagem) {
        super(mensagem);
    }
}
