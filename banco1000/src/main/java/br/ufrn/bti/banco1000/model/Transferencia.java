package br.ufrn.bti.banco1000.model;

/**
 * Classe que representa uma transferência bancária entre duas contas.
 * Ela realiza a operação de sacar da conta de origem e depositar na conta de destino.
 * 
 * @author José de Barros
 */
public class Transferencia {

    private Conta contaOrigem;
    private Conta contaDestino;
    private double valor;

    /**
     * Construtor da transferência.
     * 
     * @param contaOrigem Conta de origem da transferência.
     * @param contaDestino Conta de destino da transferência.
     * @param valor Valor da transferência.
     */
    public Transferencia(Conta contaOrigem, Conta contaDestino, double valor) {
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
    }

    /**
     * Realiza a transferência entre as contas, fazendo um saque da conta de origem
     * e um depósito na conta de destino.
     * 
     * @return true se a transferência for bem-sucedida, false caso contrário.
     */
    public boolean realizarTransferencia() {
        if (contaOrigem.sacar(valor)) {  // Tenta sacar da conta de origem
            contaDestino.depositar(valor); // Deposita na conta de destino
            contaDestino.getMovimentacao().add(new Movimentacao("FORMA", contaDestino.getCliente(), "ENTRADA POR TRANSFERENCIA", valor));
            contaOrigem.getMovimentacao().add(new Movimentacao("FORMA", contaOrigem.getCliente(), "SAIDA POR TRANSFERENCIA", valor));
            return true; // Transferência bem-sucedida
        }
        return false; // Caso contrário, a transferência falha
    }
}
