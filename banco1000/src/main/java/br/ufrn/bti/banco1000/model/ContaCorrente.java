package br.ufrn.bti.banco1000.model;

/*
 * Classe que representa uma conta corrente, que é uma especialização da classe Conta.
 * Ela possui uma taxa de manutenção mensal, que pode ser cobrada se houver saldo suficiente.
 */
public class ContaCorrente extends Conta {
    private double taxaManutencao; // Taxa de manutenção mensal da conta corrente

    /*
     * Construtor da classe ContaCorrente, que chama o construtor da classe pai (Conta) para inicializar os atributos principais.
     */
    public ContaCorrente(Cliente cliente, int agencia, int numeroConta, double saldo, double taxaManutencao) {
        super(cliente, agencia, numeroConta, "Corrente", saldo);
        this.taxaManutencao = taxaManutencao;
    }

    /*
     * Método para cobrar a taxa de manutenção da conta.
     * Lança uma exceção se o saldo for insuficiente para cobrir a taxa.
     */
    public void cobrarTaxaManutencao() {
        if (saldo < taxaManutencao) {
            throw new SaldoInsuficienteException("Saldo insuficiente para cobrar taxa de manutenção.");
        }
        saldo -= taxaManutencao;
    }

    /*
     * Sobrescreve o método sacar da classe pai, incluindo uma verificação extra para garantir que o saldo seja suficiente.
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