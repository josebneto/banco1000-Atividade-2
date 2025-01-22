// Classe utilitária para salvar e carregar dados de agências em arquivos CSV
package br.ufrn.bti.banco1000.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArquivoCSVUtils {
    // Salva as informações das agências e suas contas em um arquivo CSV
    public static void salvarAgencias(List<Agencia> agencias, String caminhoArquivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Agencia agencia : agencias) {
                for (Conta conta : agencia.getContas()) {
                    // Escreve os dados no formato: codigoAgencia,numeroConta,saldo
                    writer.write(agencia.getCodigoAgencia() + "," + conta.getNumeroConta() + "," + conta.getSaldo());
                    writer.newLine(); // Adiciona uma nova linha após cada registro
                }
            }
        }
    }

    // Carrega as informações das agências e contas a partir de um arquivo CSV
    public static List<Agencia> carregarAgencias(String caminhoArquivo) throws IOException {
        List<Agencia> agencias = new ArrayList<>(); // Lista para armazenar as agências
        Map<String, Agencia> mapaAgencias = new HashMap<>(); // Mapeia código de agência para instâncias de Agencia

        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(","); // Divide a linha em partes usando vírgula como separador
                String codigoAgencia = partes[0];
                String numeroConta = partes[1];
                double saldo = Double.parseDouble(partes[2]); // Converte o saldo para double

                // Recupera ou cria uma nova instância de Agencia
                Agencia agencia = mapaAgencias.computeIfAbsent(codigoAgencia, k -> new Agencia("Agência " + k, k));
                // Adiciona uma conta à agência (valores padrão para cliente e limite)
                agencia.registrarConta(new ContaCorrente(null, 0, Integer.parseInt(numeroConta), saldo, 0.0));
            }
        }

        agencias.addAll(mapaAgencias.values()); // Adiciona todas as agências do mapa à lista final
        return agencias;
    }
}