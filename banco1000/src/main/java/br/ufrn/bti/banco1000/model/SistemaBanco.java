package br.ufrn.bti.banco1000.model;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SistemaBanco {
    private double taxaManutencao = 10.0;
    private double rendimentoMensal = 0.05;
    private double limiteSaquesMensais = 3;
    private List<Cliente> clientes = new ArrayList<>();
    private Cliente clienteLogado;

    public boolean criarContaParaCliente(String cpf, int agencia, int numeroConta, String tipo, double saldoInicial) {
        Optional<Cliente> cliente = buscarClientePorCPF(cpf);
        if (cliente.isPresent()) {
            System.out.println("Cliente encontrado: " + cliente.get().getNome());  // Depuração para verificar o cliente
            Conta novaConta;
            // Ajuste para criação de conta com base no tipo
            if (tipo.equalsIgnoreCase("corrente")) {  // Usando .equalsIgnoreCase para comparação insensível a maiúsculas/minúsculas
                novaConta = new ContaCorrente(cliente.get(), agencia, numeroConta, saldoInicial, taxaManutencao);
            } else if (tipo.equalsIgnoreCase("poupanca")) {
                novaConta = new ContaPoupanca(cliente.get(), agencia, numeroConta, saldoInicial, rendimentoMensal);
            } else if (tipo.equalsIgnoreCase("salario")) {
                novaConta = new ContaSalario(cliente.get(), agencia, numeroConta, saldoInicial, (int) limiteSaquesMensais); 
            } else {
                return false; // Tipo de conta inválido
            }
            cliente.get().adicionarConta(novaConta);
            return true;
        } else {
            System.out.println("Cliente não encontrado com o CPF: " + cpf);  // Depuração caso não encontre o cliente
        }
        return false;
    }          

    public double consultarSaldo(int numeroConta) {
        if (clienteLogado != null) {
            for (Conta conta : clienteLogado.getContas()) {
                if (conta.getNumeroConta() == numeroConta) {  // Usando getNumeroConta()
                    return conta.getSaldo();
                }
            }
        }
        return -1; // conta não encontrada ou não logado
    }

    public boolean depositar(int numeroConta, double valor) {
        if (clienteLogado != null) {
            for (Conta conta : clienteLogado.getContas()) {
                if (conta.getNumeroConta() == numeroConta) {  // Usando getNumeroConta()
                    conta.depositar(valor);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean sacar(int numeroConta, double valor) {
        if (clienteLogado != null) {
            for (Conta conta : clienteLogado.getContas()) {
                if (conta.getNumeroConta() == numeroConta) {  // Usando getNumeroConta()
                    return conta.sacar(valor);
                }
            }
        }
        return false;
    }

    public boolean transferir(int numeroContaOrigem, int numeroContaDestino, double valor) {
        if (clienteLogado != null) {
            // Encontra a conta de origem
            Conta contaOrigem = clienteLogado.getContas().stream()
                                            .filter(c -> c.getNumeroConta() == numeroContaOrigem)
                                            .findFirst().orElse(null);
            if (contaOrigem != null) {
                // Encontra a conta de destino
                for (Cliente cliente : clientes) {
                    for (Conta contaDestino : cliente.getContas()) {
                        if (contaDestino.getNumeroConta() == numeroContaDestino) {
                            // Cria a transferência e executa
                            Transferencia transferencia = new Transferencia(contaOrigem, contaDestino, valor);
                            return transferencia.realizarTransferencia();
                        }
                    }
                }
            }
        }
        return false; // Caso não encontre contas ou algum erro
    }

    public Optional<Cliente> buscarClientePorCPF(String cpf) {
        return clientes.stream()
                .filter(cliente -> cliente.getCpf().equals(cpf))
                .findFirst();
    }

    public boolean login(String cpf, String senha) {
        Optional<Cliente> cliente = buscarClientePorCPF(cpf);
        if (cliente.isPresent() && cliente.get().getSenha().equals(senha)) {
            clienteLogado = cliente.get();
            return true;  // Login bem-sucedido
        }
        return false;  // Login falhou
    }    
    
    public void logout() {
        clienteLogado = null;
    }

    public boolean cadastrarCliente(String nome, String cpf, String senha, String email, String telefone) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf)) {
                return false;  // Cliente já cadastrado
            }
        }
        Cliente novoCliente = new Cliente(nome, cpf, senha, email, telefone);
        clientes.add(novoCliente);
        return true;  // Cliente cadastrado com sucesso
    }
    
    public void salvarDadosCSV(String caminho) {
        try {
            // Garante que o caminho termine com ".csv"
            if (!caminho.endsWith(".csv")) {
                caminho += ".csv";
            }
    
            // Cria o diretório do caminho, se necessário
            Path path = Paths.get(caminho).getParent();
            if (path != null && !Files.exists(path)) {
                Files.createDirectories(path);
            }
    
            // Depuração simples: validação de dados antes de salvar
            System.out.println("Iniciando a depuração dos dados...");
            for (Cliente cliente : clientes) {
                if (cliente.getNome() == null || cliente.getNome().isEmpty()) {
                    System.out.println("Aviso: Cliente com CPF " + cliente.getCpf() + " possui nome ausente ou vazio.");
                }
                if (cliente.getCpf() == null || cliente.getCpf().isEmpty()) {
                    System.out.println("Erro: Cliente com nome " + cliente.getNome() + " possui CPF ausente ou vazio.");
                }
                if (cliente.getContas().isEmpty()) {
                    System.out.println("Aviso: Cliente com CPF " + cliente.getCpf() + " não possui contas associadas.");
                } else {
                    for (Conta conta : cliente.getContas()) {
                        if (conta.getNumeroConta() <= 0) {
                            System.out.println("Erro: Conta do cliente " + cliente.getNome() + " possui número inválido.");
                        }                                              
                        if (conta.getSaldo() < 0) {
                            System.out.println("Aviso: Conta " + conta.getNumeroConta() + " do cliente " + cliente.getNome() + " possui saldo negativo.");
                        }
                    }
                }
            }
            System.out.println("Depuração concluída.");
    
            // Cria o cabeçalho do arquivo CSV
            StringBuilder conteudo = new StringBuilder();
            conteudo.append("Nome,CPF,Senha,Email,Telefone,Agencia,NumeroConta,TipoConta,Saldo\n");
    
            // Adiciona os dados dos clientes e suas contas
            for (Cliente cliente : clientes) {
                if (cliente.getContas().isEmpty()) {
                    conteudo.append(cliente.getNome()).append(",")
                            .append(cliente.getCpf()).append(",")
                            .append(cliente.getSenha()).append(",")
                            .append(cliente.getEmail()).append(",")
                            .append(cliente.getTelefone()).append(",")
                            .append("N/A,N/A,N/A,N/A\n");
                } else {
                    for (Conta conta : cliente.getContas()) {
                        String tipoConta = conta instanceof ContaCorrente ? "corrente"
                                        : conta instanceof ContaPoupanca ? "poupanca"
                                        : conta instanceof ContaSalario ? "salario" : "desconhecido";
    
                        conteudo.append(cliente.getNome()).append(",")
                                .append(cliente.getCpf()).append(",")
                                .append(cliente.getSenha()).append(",")
                                .append(cliente.getEmail()).append(",")
                                .append(cliente.getTelefone()).append(",")
                                .append(conta.getAgencia()).append(",")
                                .append(conta.getNumeroConta()).append(",")
                                .append(tipoConta).append(",")
                                .append(conta.getSaldo()).append("\n");
                    }
                }
            }
    
            // Escreve o conteúdo no arquivo
            Files.write(Paths.get(caminho), conteudo.toString().getBytes());
            System.out.println("Dados salvos com sucesso em: " + caminho);
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }    

    public void carregarDadosCSV(String caminho) {
        try {
            // Define o diretório base padrão (diretório do projeto)
            String diretorioBase = "C:\\Users\\José e Ju\\Desktop\\LAB_1\\banco1000";
            
            // Adiciona o diretório base se o caminho fornecido não for absoluto
            Path caminhoAbsoluto = Paths.get(caminho);
            if (!caminhoAbsoluto.isAbsolute()) {
                caminho = diretorioBase + File.separator + caminho;
            }
            
            // Adiciona a extensão .csv, se necessário
            if (!caminho.endsWith(".csv")) {
                caminho += ".csv";
            }

            // Verifica se o arquivo existe
            Path caminhoArquivo = Paths.get(caminho);
            if (!Files.exists(caminhoArquivo)) {
                System.out.println("Erro: O arquivo não foi encontrado no caminho especificado: " + caminho);
                return;
            }

            // Lê todas as linhas do arquivo
            List<String> linhas = Files.readAllLines(caminhoArquivo, StandardCharsets.UTF_8);
            if (linhas.isEmpty()) {
                System.out.println("O arquivo está vazio.");
                return;
            }

            // Remove o cabeçalho
            linhas.remove(0);

            // Processa cada linha
            for (String linha : linhas) {
                String[] dados = linha.split(",");
                if (dados.length != 9) {
                    System.out.println("Linha inválida no arquivo: " + linha);
                    continue;
                }

                // Extrai os dados
                String nome = dados[0];
                String cpf = dados[1];
                String senha = dados[2];
                String email = dados[3];
                String telefone = dados[4];
                String agenciaStr = dados[5];
                String numeroContaStr = dados[6];
                String tipoConta = dados[7];
                String saldoStr = dados[8];

                // Verifica valores "N/A" para campos numéricos
                int agencia = agenciaStr.equals("N/A") ? 0 : Integer.parseInt(agenciaStr);
                int numeroConta = numeroContaStr.equals("N/A") ? 0 : Integer.parseInt(numeroContaStr);
                double saldo = saldoStr.equals("N/A") ? 0.0 : Double.parseDouble(saldoStr);

                // Busca ou cria o cliente
                Optional<Cliente> clienteOpt = buscarClientePorCPF(cpf);
                Cliente cliente;
                if (clienteOpt.isPresent()) {
                    cliente = clienteOpt.get();
                } else {
                    cliente = new Cliente(nome, cpf, senha, email, telefone);
                    clientes.add(cliente);
                }

                // Adiciona a conta, se aplicável
                if (numeroConta > 0) {
                    Conta conta;
                    if (tipoConta.equalsIgnoreCase("corrente")) {
                        conta = new ContaCorrente(cliente, agencia, numeroConta, saldo, taxaManutencao);
                    } else if (tipoConta.equalsIgnoreCase("poupanca")) {
                        conta = new ContaPoupanca(cliente, agencia, numeroConta, saldo, rendimentoMensal);
                    } else if (tipoConta.equalsIgnoreCase("salario")) {
                        conta = new ContaSalario(cliente, agencia, numeroConta, saldo, (int) limiteSaquesMensais);
                    } else {
                        System.out.println("Tipo de conta desconhecido: " + tipoConta);
                        continue;
                    }
                    cliente.adicionarConta(conta);
                }
            }

            System.out.println("Dados carregados com sucesso do arquivo: " + caminho);

        } catch (IOException e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro ao processar valores numéricos: " + e.getMessage());
        }
    }       
}    