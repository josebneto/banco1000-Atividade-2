/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

 package br.ufrn.bti.banco1000;

 import br.ufrn.bti.banco1000.model.SistemaBanco;
 
 import java.util.Scanner;
 
 /**
  * Classe principal do sistema Banco1000.
  * Responsável pela interação com o usuário e controle das funcionalidades do sistema bancário.
  * Autor: José de Barros
  */
 public class Banco1000 {
     public static void main(String[] args) {
         SistemaBanco sistemaBanco = new SistemaBanco(); // Instância do sistema bancário
         Scanner scanner = new Scanner(System.in); // Scanner para entrada do usuário
 
         // Loop principal do sistema
         while (true) {
             System.out.println("\nBem-vindo ao Banco 1000!\nPor: José de Barros Guerra Neto");
             System.out.println("1 - Cadastrar Cliente");
             System.out.println("2 - Login");
             System.out.println("3 - Criar Conta");
             System.out.println("4 - Consultar Saldo");
             System.out.println("5 - Depositar");
             System.out.println("6 - Sacar");
             System.out.println("7 - Transferir");
             System.out.println("8 - Salvar Dados");
             System.out.println("9 - Carregar Dados");
             System.out.println("10 - Sair");
             System.out.print("Escolha uma opção: ");
 
             int opcao = scanner.nextInt();
 
             switch (opcao) {
                 case 1 -> { // Cadastrar cliente
                     System.out.print("Digite o nome: ");
                     String nome = scanner.next();
                     System.out.print("Digite o CPF: ");
                     String cpf = scanner.next();
                     System.out.print("Digite a senha: ");
                     String senha = scanner.next();
                     System.out.print("Digite o email: ");
                     String email = scanner.next();
                     System.out.print("Digite o telefone: ");
                     String telefone = scanner.next();
 
                     if (sistemaBanco.cadastrarCliente(nome, cpf, senha, email, telefone)) {
                         System.out.println("Cliente cadastrado com sucesso!");
                     } else {
                         System.out.println("Erro: Cliente com esse CPF já cadastrado.");
                     }
                 }
                 case 2 -> { // Login
                     System.out.print("Digite o CPF para login: ");
                     String cpfLogin = scanner.next();
                     System.out.print("Digite a senha: ");
                     String senhaLogin = scanner.next();
 
                     if (sistemaBanco.login(cpfLogin, senhaLogin)) {
                         System.out.println("Login realizado com sucesso!");
                     } else {
                         System.out.println("Falha no login! CPF ou senha incorretos.");
                     }
                 }
                 case 3 -> { // Criar conta
                     System.out.print("Digite o CPF do cliente para criar a conta: ");
                     String cpfConta = scanner.next();
                     System.out.print("Digite o número da agência: ");
                     int agencia = scanner.nextInt();
                     System.out.print("Digite o número da conta: ");
                     int numeroConta = scanner.nextInt();
                     System.out.print("Digite o tipo de conta (corrente/poupanca/salario): ");
                     String tipo = scanner.next();
                     System.out.print("Digite o saldo inicial: ");
                     double saldoInicial = scanner.nextDouble();
 
                     if (sistemaBanco.criarContaParaCliente(cpfConta, agencia, numeroConta, tipo, saldoInicial)) {
                         System.out.println("Conta criada com sucesso!");
                     } else {
                         System.out.println("Erro: Cliente não encontrado.");
                     }
                 }
                 case 4 -> { // Consultar saldo
                     System.out.print("Digite o número da conta: ");
                     int numeroConta = scanner.nextInt();
 
                     double saldo = sistemaBanco.consultarSaldo(numeroConta);
                     if (saldo != -1) {
                         System.out.println("Saldo atual: " + saldo);
                     } else {
                         System.out.println("Conta não encontrada ou você não está logado.");
                     }
                 }
                 case 5 -> { // Depositar
                     System.out.print("Digite o número da conta para depósito: ");
                     int numeroConta = scanner.nextInt();
                     System.out.print("Digite o valor a ser depositado: ");
                     double valor = scanner.nextDouble();
 
                     if (sistemaBanco.depositar(numeroConta, valor)) {
                         System.out.println("Depósito realizado com sucesso!");
                     } else {
                         System.out.println("Erro no depósito.");
                     }
                 }
                 case 6 -> { // Sacar
                     System.out.print("Digite o número da conta para saque: ");
                     int numeroConta = scanner.nextInt();
                     System.out.print("Digite o valor a ser sacado: ");
                     double valor = scanner.nextDouble();
 
                     if (sistemaBanco.sacar(numeroConta, valor)) {
                         System.out.println("Saque realizado com sucesso!");
                     } else {
                         System.out.println("Erro no saque.");
                     }
                 }
                 case 7 -> { // Transferir
                     System.out.print("Digite o número da conta de origem: ");
                     int numeroContaOrigem = scanner.nextInt();
                     System.out.print("Digite o número da conta de destino: ");
                     int numeroContaDestino = scanner.nextInt();
                     System.out.print("Digite o valor a ser transferido: ");
                     double valor = scanner.nextDouble();
 
                     if (sistemaBanco.transferir(numeroContaOrigem, numeroContaDestino, valor)) {
                         System.out.println("Transferência realizada com sucesso!");
                     } else {
                         System.out.println("Erro na transferência.");
                     }
                 }
                 case 8 -> { // Salvar dados em CSV
                     System.out.print("Digite o nome do arquivo para salvar os dados: ");
                     scanner.nextLine(); // Consumir quebra de linha pendente
                     String caminho = scanner.nextLine();
                     try {
                         sistemaBanco.salvarDadosCSV(caminho);
                         System.out.println("Dados salvos com sucesso!");
                     } catch (Exception e) {
                         System.out.println("Erro ao salvar os dados: " + e.getMessage());
                     }
                 }
                 case 9 -> { // Carregar dados de CSV
                     System.out.print("Digite o nome do arquivo para carregar os dados: ");
                     scanner.nextLine(); // Consumir quebra de linha pendente
                     String caminhoArquivo = scanner.nextLine();
                     sistemaBanco.carregarDadosCSV(caminhoArquivo);
                     System.out.println("Dados carregados com sucesso do arquivo: " + caminhoArquivo);
                 }
                 case 10 -> { // Sair
                     System.out.println("Saindo do sistema. Até logo!");
                     scanner.close();
                     return;
                 }
                 default -> System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
             }
         }
     }
 } 