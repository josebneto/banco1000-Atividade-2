package br.ufrn.bti.banco1000.model;

/*
 * Classe que representa uma conta salário, que é uma especialização da classe Conta.
 * Ela possui um limite de saques mensais e um controle de saques realizados.
 */
public class ContaSalario extends Conta {
    private int limiteSaquesMensais; // Limite de saques mensais permitidos
    private int saquesRealizados; // Contador de saques realizados no mês

    /*
     * Construtor da classe ContaSalario, que chama o construtor da classe pai (Conta) para inicializar os atributos principais.
     */
    public ContaSalario(Cliente cliente, int agencia, int numeroConta, double saldo, int limiteSaquesMensais) {
        super(cliente, agencia, numeroConta, "Salário", saldo);
        this.limiteSaquesMensais = limiteSaquesMensais;
        this.saquesRealizados = 0;
    }

    /*
     * Sobrescreve o método sacar da classe pai, garantindo que o número de saques não exceda o limite mensal.
     */
    @Override
    public boolean sacar(double valor) {
        if (saquesRealizados >= limiteSaquesMensais) {
            throw new LimiteDeSaquesExcedidoException("Limite de saques mensais excedido.");
        }
        if (valor > saldo) {
            throw new SaldoInsuficienteException("Saldo insuficiente.");
        }
        saldo -= valor;
        saquesRealizados++;
        return true;
    }

    /*
     * Método para resetar a quantidade de saques mensais, chamado no início de cada mês.
     */
    public void resetarSaquesMensais() {
        saquesRealizados = 0;
    }
}