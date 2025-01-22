package br.ufrn.bti.banco1000.model;

/*
 * Classe que representa uma conta poupança, que é uma especialização da classe Conta.
 * Ela possui um rendimento mensal que é aplicado sobre o saldo da conta.
 */
public class ContaPoupanca extends Conta {
    private double rendimentoMensal; // Percentual de rendimento mensal da conta poupança

    /*
     * Construtor da classe ContaPoupanca, que chama o construtor da classe pai (Conta) para inicializar os atributos principais.
     */
    public ContaPoupanca(Cliente cliente, int agencia, int numeroConta, double saldo, double rendimentoMensal) {
        super(cliente, agencia, numeroConta, "Poupança", saldo);
        this.rendimentoMensal = rendimentoMensal;
    }

    /*
     * Método para aplicar o rendimento mensal na conta poupança.
     * O saldo é incrementado conforme a porcentagem de rendimento.
     */
    public void aplicarRendimento() {
        saldo += saldo * rendimentoMensal;
    }

    /*
     * Sobrescreve o método sacar da classe pai para garantir que o saldo seja suficiente para o saque.
     */
    @Override
    public boolean sacar(double valor) {
        if (valor > saldo) {
            throw new SaldoInsuficienteException("Saldo insuficiente.");
        }
        saldo -= valor;
        return true;
    }
}