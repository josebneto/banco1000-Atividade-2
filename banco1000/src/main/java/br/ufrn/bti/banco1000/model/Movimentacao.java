package br.ufrn.bti.banco1000.model;

import java.util.Date;

/**
 * Classe que representa uma movimentação bancária, como depósitos ou saques.
 * Cada movimentação tem uma data, tipo (depósito, saque), um cliente associado,
 * uma descrição e o valor movimentado.
 * 
 * @author José de Barros
 */
public class Movimentacao {
    private Date data;
    private String tipo;
    private Cliente cliente;
    private String descricao;
    private double valor;

    /**
     * Construtor da movimentação bancária.
     * 
     * @param tipo Tipo de movimentação (depósito, saque, etc.).
     * @param cliente Cliente associado à movimentação.
     * @param descricao Descrição da movimentação.
     * @param valor Valor da movimentação.
     */
    public Movimentacao(String tipo, Cliente cliente, String descricao, double valor) {
        this.data = new Date();  // Data da movimentação é o momento atual.
        this.tipo = tipo;
        this.cliente = cliente;
        this.descricao = descricao;
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public String getTipo() {
        return tipo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }
}
