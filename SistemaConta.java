
import java.util.*;

public class SistemaConta {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Banco banco = new Banco();
    private static final Random random = new Random();

    public static void main(String[] args) {
        while (true) {
            switch (menuPrincipal()) {
                case 1 -> {
                    while (menuPrincipalCliente()) {
                    }
                }
                case 2 ->
                    menuPrincipalAdministrador();
                case 3 -> {
                    System.out.println("Saindo do sistema...");
                    return;
                }
                default ->
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static int menuPrincipal() {
        System.out.println("Olá, seja bem-vindo(a). Você cliente?");
        System.out.println("1. Sim");
        System.out.println("2. Não");
        System.out.println("3. Sair");
        System.out.print("Opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        return opcao;
    }

    public static boolean menuPrincipalCliente() {
        while (true) {
            System.out.println("=== Menu do Cliente ===");
            System.out.println("1. Criar Conta");
            System.out.println("2. Acessar Conta");
            System.out.println("3. Sair");
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 ->
                    criarConta();
                case 2 ->
                    acessarConta();
                case 3 -> {
                    return false;
                }
                default ->
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void menuPrincipalAdministrador() {
        while (true) {
            System.out.println("=== Menu do Administrador ===");
            System.out.println("1. Listar Contas");
            System.out.println("2. Configurar Contas");
            System.out.println("3. Sair");
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 ->
                    listarContas();
                case 2 ->
                    configurarConta();
                case 3 -> {
                    return;
                }
                default ->
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void listarContas() {
        Map<Integer, Conta> contas = banco.getContas();
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta encontrada.");
        } else {
            for (Conta conta : contas.values()) {
                System.out.println(conta);
            }
        }
    }

    public static void criarConta() {
        System.out.println("=== Criar Conta ===");
        System.out.println("1. Conta Corrente");
        System.out.println("2. Conta VIP");
        System.out.println("3. Conta Poupança");
        System.out.print("Opção: ");
        int tipoConta = scanner.nextInt();
        scanner.nextLine();

        switch (tipoConta) {
            case 1 ->
                criarContaCorrente();
            case 2 ->
                criarContaVIP();
            case 3 ->
                criarContaPoupanca();
            default ->
                System.out.println("Tipo de conta inválido. Tente novamente.");
        }
    }

    public static void criarContaCorrente() {
        System.out.println("=== Criando Conta Corrente ===");
        int numero = gerarNumeroConta();
        String nome = solicitarNome();
        String cpf = solicitarCPF();
        double limiteSaque = 2000.0;

        ContaCorrente cc = new ContaCorrente(numero, 0, limiteSaque, 5000.0, 10000.0, nome, cpf);
        banco.adicionarConta(cc);
        System.out.println("Conta Corrente criada com sucesso! Número da Conta: " + numero + ", Nome: " + nome);
    }

    public static void criarContaVIP() {
        System.out.println("=== Criando Conta VIP ===");
        int numero = gerarNumeroConta();
        String nome = solicitarNome();
        String cpf = solicitarCPF();
        System.out.print("Salário: ");
        double salario = scanner.nextDouble();
        scanner.nextLine();
        if (salario < 50000) {
            System.out.println("Para criar uma conta VIP, o salário deve ser igual ou superior a 50.000.");
            return;
        }

        double limiteSaque = 500000.0;

        ContaVIP vip = new ContaVIP(numero, 0.0, limiteSaque, 500000.0, 200000.0, nome, cpf, salario);
        banco.adicionarConta(vip);
        System.out.println("Conta VIP criada com sucesso! Número da Conta: " + numero + ", Nome: " + nome);
    }

    public static void criarContaPoupanca() {
        System.out.println("=== Criando Conta Poupança ===");
        int numero = gerarNumeroConta();
        String nome = solicitarNome();
        String cpf = solicitarCPF();
        double rendimentoPoupanca = 0.005;

        ContaPoupanca cp = new ContaPoupanca(numero, 0, rendimentoPoupanca, nome, cpf);
        banco.adicionarConta(cp);
        System.out.println("Conta Poupança criada com sucesso! Número da Conta: " + numero + ", Nome: " + nome);
    }

    public static void acessarConta() {
        System.out.println("=== Acessar Conta ===");
        System.out.print("Número da Conta: ");
        int numeroConta = scanner.nextInt();
        scanner.nextLine();

        Conta conta = banco.encontrarConta(numeroConta);
        if (conta != null) {
            switch (conta) {
                case ContaCorrente cc ->
                    menuContaCorrente(cc);
                case ContaVIP vip ->
                    menuContaVIP(vip);
                case ContaPoupanca cp ->
                    menuContaPoupanca(cp);
                default ->
                    System.out.println("Tipo de conta não reconhecido.");
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    public static void menuContaCorrente(ContaCorrente conta) {
        while (true) {
            System.out.println("=== Menu Conta Corrente ===");
            System.out.println("1. Sacar");
            System.out.println("2. Depositar");
            System.out.println("3. Extrato");
            System.out.println("4. Cartão de Crédito");
            System.out.println("5. Empréstimo");
            System.out.println("6. Voltar");
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 ->
                    realizarSaque(conta);
                case 2 ->
                    realizarDeposito(conta);
                case 3 -> {
                    conta.imprimirExtrato();
                    break;
                }
                case 4 ->
                    menuCartaoCredito(conta);
                case 5 ->
                    solicitarEmprestimo(conta);
                case 6 -> {
                    return;
                }
                default ->
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void menuContaVIP(ContaVIP conta) {
        while (true) {
            System.out.println("=== Menu Conta VIP ===");
            System.out.println("1. Sacar");
            System.out.println("2. Depositar");
            System.out.println("3. Extrato");
            System.out.println("4. Cartão de Crédito");
            System.out.println("5. Empréstimo");
            System.out.println("6. Investimento");
            System.out.println("7. Seguros");
            System.out.println("8. Serviços Exclusivos");
            System.out.println("9. Voltar");
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 ->
                    realizarSaque(conta);
                case 2 ->
                    realizarDeposito(conta);
                case 3 -> {
                    conta.imprimirExtrato();
                    break;
                }
                case 4 ->
                    gerenciarCartaoCredito(conta);
                case 5 ->
                    solicitarEmprestimo(conta);
                case 6 ->
                    gerenciarInvestimento(conta);
                case 7 ->
                    gerenciarSeguros(conta);
                case 8 ->
                    gerenciarServicosExclusivos(conta);
                case 9 -> {
                    return;
                }
                default ->
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void menuContaPoupanca(ContaPoupanca conta) {
        while (true) {
            System.out.println("=== Menu Conta Poupança ===");
            System.out.println("1. Sacar");
            System.out.println("2. Depositar");
            System.out.println("3. Extrato");
            System.out.println("4. Voltar");
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 ->
                    realizarSaque(conta);
                case 2 ->
                    realizarDeposito(conta);
                case 3 -> {
                    conta.imprimirExtrato();
                    break;
                }
                case 4 -> {
                    return;
                }
                default ->
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void realizarSaque(Conta conta) {
        System.out.print("Valor do Saque: R$ ");
        double valor = scanner.nextDouble();
        scanner.nextLine();
        if (conta.sacar(valor)) {
            System.out.println("Saque realizado com sucesso.");
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    public static void realizarDeposito(Conta conta) {
        System.out.print("Valor do Depósito: R$ ");
        double valor = scanner.nextDouble();
        scanner.nextLine();
        conta.depositar(valor);
        System.out.println("Depósito realizado com sucesso.");
    }

    public static void gerenciarCartaoCredito(Conta conta) {
        System.out.println("=== Gerenciamento de Cartão de Crédito ===");

        switch (conta) {
            case ContaCorrente contaCorrente -> {
                if (!contaCorrente.isCartaoContratado()) {
                    System.out.println("Deseja contratar um cartão de crédito?");
                    System.out.println("1. Sim");
                    System.out.println("2. Não");
                    System.out.print("Opção: ");
                    int resposta = scanner.nextInt();
                    scanner.nextLine();

                    switch (resposta) {
                        case 1 -> {
                            contaCorrente.setCartaoContratado(true);
                            double limiteDisponivel = contaCorrente.getLimiteCredito();
                            System.out.println("Limite disponível: R$ " + limiteDisponivel);
                            menuCartaoCredito(contaCorrente);
                        }
                        case 2 -> {
                            return;
                        }
                        default -> {
                            System.out.println("Opção inválida. Retornando ao menu anterior.");
                            return;
                        }
                    }
                } else {
                    menuCartaoCredito(contaCorrente);
                }
            }
            case ContaVIP contaVIP -> {
                if (!contaVIP.isCartaoContratado()) {
                    System.out.println("Deseja contratar um cartão de crédito?");
                    System.out.println("1. Sim");
                    System.out.println("2. Não");
                    System.out.print("Opção: ");
                    int resposta = scanner.nextInt();
                    scanner.nextLine();

                    switch (resposta) {
                        case 1 -> {
                            contaVIP.setCartaoContratado(true);
                            double limiteDisponivel = contaVIP.getLimiteCredito();
                            System.out.println("Limite disponível: R$ " + limiteDisponivel);
                            menuCartaoCredito(contaVIP);
                        }
                        case 2 -> {
                            return;
                        }
                        default -> {
                            System.out.println("Opção inválida. Retornando ao menu anterior.");
                            return;
                        }
                    }
                } else {
                    menuCartaoCredito(contaVIP);
                }
            }
            default -> {
            }
        }
    }

    public static void menuCartaoCredito(Conta conta) {
        while (true) {
            System.out.println("=== Menu Cartão de Crédito ===");
            System.out.println("1. Limite");
            System.out.println("2. Extrato");
            System.out.println("3. Voltar");
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> {
                    switch (conta) {
                        case ContaCorrente contaCorrente -> {
                            double limiteDisponivel = contaCorrente.getLimiteCredito();
                            System.out.println("Limite disponível: R$ " + limiteDisponivel);
                        }
                        case ContaVIP contaVIP -> {
                            double limiteDisponivel = contaVIP.getLimiteCredito();
                            System.out.println("Limite disponível: R$ " + limiteDisponivel);
                        }
                        default -> {
                            return;
                        }
                    }
                }

                case 2 -> {
                    switch (conta) {
                        case ContaCorrente contaCorrente -> {
                            Map<Integer, String> extrato = contaCorrente.getExtratoCartaoCredito();
                            if (extrato != null) {
                                System.out.println("=== Extrato do Cartão de Crédito ===");
                                for (String transacao : extrato.values()) {
                                    System.out.println(transacao);
                                }
                            }
                        }
                        case ContaVIP contaVIP -> {
                            Map<Integer, String> extrato = contaVIP.getExtratoCartaoCredito();
                            if (extrato != null) {
                                System.out.println("=== Extrato do Cartão de Crédito ===");
                                for (String transacao : extrato.values()) {
                                    System.out.println(transacao);
                                }
                            }
                        }
                        default -> System.out.println("Operação não suportada para este tipo de conta.");
                    }
                }
                case 3 -> {
                    return;
                }
                default ->
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void solicitarEmprestimo(Conta conta) {
        System.out.print("Valor do Empréstimo: R$ ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        switch (conta) {
            case ContaCorrente contaCorrente -> {
                if (contaCorrente.solicitarEmprestimo(valor)) {
                    System.out.println("Empréstimo aprovado.");
                } else {
                    System.out.println("Empréstimo não aprovado.");
                }
            }
            case ContaVIP contaVIP -> {
                if (contaVIP.solicitarEmprestimo(valor)) {
                    System.out.println("Empréstimo aprovado com sucesso.");
                } else {
                    System.out.println("Empréstimo negado.");
                }
            }
            default ->
                System.out.println("Tipo de conta não suportado para empréstimo.");
        }
    }

    public static void gerenciarInvestimento(ContaVIP conta) {
        while (true) {
            System.out.println("=== Gerenciar Investimentos ===");
            System.out.println("1. Adicionar Investimento");
            System.out.println("2. Resgatar Investimento");
            System.out.println("3. Ver Investimentos");
            System.out.println("4. Sair");
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> adicionarInvestimento(conta);
                case 2 -> removerInvestimento(conta);
                case 3 -> verInvestimentos(conta);
                case 4 -> {
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void adicionarInvestimento(ContaVIP conta) {
        System.out.println("=== Adicionar Investimento ===");
        System.out.println("Escolha o tipo de investimento:");
        System.out.println("1. Imobiliário");
        System.out.println("2. Tesouro Direto");
        System.out.println("3. Bolsa de Valores");
        System.out.println("4. Criptomoedas");
        System.out.print("Opção: ");
        int tipoInvestimento = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o valor do investimento: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        String tipo = "";
        switch (tipoInvestimento) {
            case 1 -> tipo = "Imobiliário";
            case 2 -> tipo = "Tesouro Direto";
            case 3 -> tipo = "Bolsa de Valores";
            case 4 -> tipo = "Criptomoedas";
            default -> {
                System.out.println("Tipo de investimento inválido.");
                return;
            }
        }

        conta.adicionarInvestimento(tipo, valor);
        System.out.println("Investimento adicionado com sucesso!");
    }

    public static void removerInvestimento(ContaVIP conta) {
        System.out.println("=== Remover Investimento ===");
        System.out.println("Escolha o tipo de investimento a remover:");
        System.out.println("1. Imobiliário");
        System.out.println("2. Tesouro Direto");
        System.out.println("3. Bolsa de Valores");
        System.out.println("4. Criptomoedas");
        System.out.print("Opção: ");
        int tipoInvestimento = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o valor a remover: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        String tipo = "";
        switch (tipoInvestimento) {
            case 1 -> tipo = "Imobiliário";
            case 2 -> tipo = "Tesouro Direto";
            case 3 -> tipo = "Bolsa de Valores";
            case 4 -> tipo = "Criptomoedas";
            default -> {
                System.out.println("Tipo de investimento inválido.");
                return;
            }
        }

        conta.removerInvestimento(tipo, valor);
        System.out.println("Investimento removido com sucesso!");
    }

    public static void verInvestimentos(ContaVIP conta) {
        System.out.println("=== Ver Investimentos ===");
        Map<String, Double> investimentos = conta.getInvestimentos();
        if (investimentos.isEmpty()) {
            System.out.println("Nenhum investimento encontrado.");
        } else {
            for (Map.Entry<String, Double> entry : investimentos.entrySet()) {
                System.out.println("Tipo: " + entry.getKey() + ", Valor: R$" + entry.getValue());
            }
        }
    }

    public static void gerenciarSeguros(ContaVIP conta) {
        while (true) {
            System.out.println("=== Gerenciamento de Seguros ===");
            System.out.println("1. Contratar Seguro");
            System.out.println("2. Cancelar Seguro");
            System.out.println("3. Voltar");
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> contratarSeguro(conta);
                case 2 -> {
                    System.out.print("Digite o tipo de seguro a ser cancelado: ");
                    String tipoSeguro = scanner.nextLine();
                    conta.removerSeguro(tipoSeguro);
                }
                case 3 -> {
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void contratarSeguro(ContaVIP conta) {
        System.out.println("=== Contratar Seguro ===");
        System.out.println("1. Viagem");
        System.out.println("2. Concierge");
        System.out.println("3. Carro");
        System.out.println("4. Contra Roubo");
        System.out.print("Escolha o tipo de seguro: ");
        int tipoSeguroOpcao = scanner.nextInt();
        scanner.nextLine();

        String tipoSeguro;
        double valorSeguro;

        switch (tipoSeguroOpcao) {
            case 1 -> {
                tipoSeguro = "Viagem";
                valorSeguro = 1000;
            }
            case 2 -> {
                tipoSeguro = "Concierge";
                valorSeguro = 10000;
            }
            case 3 -> {
                tipoSeguro = "Carro";
                valorSeguro = 5000;
            }
            case 4 -> {
                tipoSeguro = "Contra Roubo";
                valorSeguro = 500;
            }
            default -> {
                System.out.println("Opção inválida. Tente novamente.");
                return;
            }
        }

        System.out.println("Você escolheu: " + tipoSeguro + " no valor de R$ " + valorSeguro);
        System.out.println("Deseja contratar este seguro?");
        System.out.println("1. Sim");
        System.out.println("2. Não");
        System.out.print("Escolha uma opção: ");
        int resposta = scanner.nextInt();
        scanner.nextLine();
        if (resposta == 1) {
            conta.contratarSeguro(tipoSeguro, valorSeguro);
            System.out.println("Seguro contratado com sucesso!");
        } else if (resposta == 2) {
            System.out.println("Operação cancelada.");
            contratarSeguro(conta);
        }
    }

    public static void gerenciarServicosExclusivos(ContaVIP conta) {
        while (true) {
            System.out.println("=== Serviços Exclusivos ===");
            System.out.println("1. Loja Parceira");
            System.out.println("2. Voltar");
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 ->
                    menuLojaParceira(conta);
                case 2 -> {
                    return;
                }
                default ->
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void menuLojaParceira(ContaVIP conta) {
        while (true) {
            System.out.println("=== Loja Parceira ===");
            System.out.println("1. iFood");
            System.out.println("2. Shopee");
            System.out.println("3. Netflix");
            System.out.println("4. Spotify");
            System.out.println("5. Twitch");
            System.out.println("6. Voltar");
            System.out.print("Opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> menuIFood(conta);
                case 2 -> menuShopee(conta);
                case 3 -> menuAssinaturaNetflix(conta);
                case 4 -> menuAssinaturaSpotify(conta);
                case 5 -> {
                    realizarAssinatura(conta, "Twitch", 26.99);
                }
                case 6 -> {
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public static void menuIFood(ContaVIP conta) {
        System.out.println("=== iFood Cardápio ===");
        System.out.println("1. Pizza");
        System.out.println("2. Hambúrguer");
        System.out.println("3. Sushi");
        System.out.println("4. Voltar");
        System.out.print("Opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1 -> realizarCompra(conta, "iFood", 40.00);
            case 2 -> realizarCompra(conta, "iFood", 25.00);
            case 3 -> realizarCompra(conta, "iFood", 50.00);
            case 4 -> {
                return;
            }
            default -> System.out.println("Opção inválida. Tente novamente.");
        }
    }

    public static void menuShopee(ContaVIP conta) {
        System.out.println("=== Shopee Loja ===");
        System.out.println("1. Eletrônicos");
        System.out.println("2. Roupas");
        System.out.println("3. Acessórios");
        System.out.println("4. Voltar");
        System.out.print("Opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1 -> realizarCompra(conta, "Shopee", 200.00);
            case 2 -> realizarCompra(conta, "Shopee", 100.00);
            case 3 -> realizarCompra(conta, "Shopee", 50.00);
            case 4 -> {
                return;
            }
            default -> System.out.println("Opção inválida. Tente novamente.");
        }
    }

    public static void menuAssinaturaNetflix(ContaVIP conta) {
        System.out.println("=== Assinatura Netflix ===");
        System.out.println("1. Premium");
        System.out.println("2. Padrão");
        System.out.println("3. Simples");
        System.out.println("4. Voltar");
        System.out.print("Opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1 -> realizarAssinatura(conta, "Netflix - Premium", 59.90);
            case 2 -> realizarAssinatura(conta, "Netflix - Padrão", 44.90);
            case 3 -> realizarAssinatura(conta, "Netflix - Simples", 20.90);
            case 4 -> {
                return;
            }
            default -> System.out.println("Opção inválida. Tente novamente.");
        }
    }

    public static void menuAssinaturaSpotify(ContaVIP conta) {
        System.out.println("=== Assinatura Spotify ===");
        System.out.println("1. Individual");
        System.out.println("2. Universitário");
        System.out.println("3. Duo");
        System.out.println("4. Família");
        System.out.println("5. Voltar");
        System.out.print("Opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1 -> realizarAssinatura(conta, "Spotify - Individual", 21.90);
            case 2 -> realizarAssinatura(conta, "Spotify - Universitário", 11.90);
            case 3 -> realizarAssinatura(conta, "Spotify - Duo", 27.90);
            case 4 -> realizarAssinatura(conta, "Spotify - Família", 34.90);
            case 5 -> {
                return;
            }
            default -> System.out.println("Opção inválida. Tente novamente.");
        }
    }

    public static void realizarCompra(ContaVIP conta, String loja, double valor) {
        conta.fazerCompraCartaoCredito(valor, loja);
        System.out.println("Compra realizada com sucesso em " + loja + " no valor de R$ " + valor);
    }

    public static void realizarAssinatura(ContaVIP conta, String servico, double valor) {
        conta.fazerCompraCartaoCredito(valor, servico);
        System.out.println("Assinatura de " + servico + " realizada com sucesso no valor de R$ " + valor);
    }

    public static void calcularRendimento(ContaPoupanca conta) {
        double rendimento = conta.calcularRendimento();
        System.out.println("Rendimento calculado: " + rendimento);
    }

    public static void configurarConta() {
        System.out.print("Número da Conta: ");
        int numeroConta = scanner.nextInt();
        scanner.nextLine();

        Conta conta = banco.encontrarConta(numeroConta);
        if (conta != null) {
            System.out.println("Configurações disponíveis para a conta número " + numeroConta);
            switch (conta) {
                case ContaCorrente contaCorrente -> configurarContaCorrente(contaCorrente);
                case ContaVIP contaVIP -> configurarContaVIP(contaVIP);
                case ContaPoupanca contaPoupanca -> configurarContaPoupanca(contaPoupanca);
                default -> System.out.println("Tipo de conta não suportado para configuração.");
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    public static void configurarContaCorrente(ContaCorrente conta) {
        System.out.println("1. Alterar Limite de Crédito");
        System.out.println("2. Alterar Limite de Saques por Dia");
        System.out.println("3. Alterar Limite de Saque");
        System.out.println("4. Alterar Limite de Empréstimo");
        System.out.println("5. Alterar Juros de Empréstimo");
        System.out.println("6. Voltar");
        System.out.println("Opção: ");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1 -> {
                System.out.print("Novo Limite de Crédito: ");
                double novoLimite = scanner.nextDouble();
                scanner.nextLine();
                conta.setLimiteCredito(novoLimite);
                System.out.println("Limite de Crédito atualizado para: " + novoLimite);
            }
            case 2 -> {
                System.out.print("Novo Limite de Saques por Dia: ");
                int novoLimite = scanner.nextInt();
                scanner.nextLine();
                conta.setLimiteSaquesPorDia(novoLimite);
                System.out.println("Limite de Saques por Dia atualizado para: " + novoLimite);
            }
            case 3 -> {
                System.out.print("Novo Limite de Saque: ");
                double novoLimite = scanner.nextDouble();
                scanner.nextLine();
                conta.setLimiteSaque(novoLimite);
                System.out.println("Limite de Saque atualizado para: " + novoLimite);
            }
            case 4 -> {
                System.out.print("Novo Limite de Empréstimo: ");
                double novoLimite = scanner.nextDouble();
                scanner.nextLine();
                conta.setLimiteEmprestimo(novoLimite);
                System.out.println("Limite de Empréstimo atualizado para: " + novoLimite);
            }
            case 5 -> {
                System.out.print("Novo Juros de Empréstimo: ");
                double novoJuros = scanner.nextDouble();
                scanner.nextLine();
                conta.setJurosEmprestimo(novoJuros);
                System.out.println("Juros de Empréstimo atualizado para: " + novoJuros);
            }

            case 6 -> {
                return;
            }
            default -> System.out.println("Opção inválida.");
        }
    }

    public static void configurarContaVIP(ContaVIP conta) {
        System.out.println("1. Alterar Limite de Crédito");
        System.out.println("2. Alterar Limite de Saque");
        System.out.println("3. Alterar Limite de Empréstimo");
        System.out.println("4. Alterar Juros de Empréstimo");
        System.out.println("5. Voltar");
        System.out.println("Opção: ");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1 -> {
                System.out.print("Novo Limite de Crédito: ");
                double novoLimite = scanner.nextDouble();
                scanner.nextLine();
                ContaVIP.setLimiteCredito(novoLimite);
                System.out.println("Limite de Crédito atualizado para: " + novoLimite);
            }
            case 2 -> {
                System.out.print("Novo Limite de Saque: ");
                double novoLimite = scanner.nextDouble();
                scanner.nextLine();
                conta.setLimiteSaque(novoLimite);
                System.out.println("Limite de Saque atualizado para: " + novoLimite);
            }
            case 3 -> {
                System.out.print("Novo Limite de Empréstimo: ");
                double novoLimite = scanner.nextDouble();
                scanner.nextLine();
                conta.setLimiteEmprestimo(novoLimite);
                System.out.println("Limite de Empréstimo atualizado para: " + novoLimite);
            }
            case 4 -> {
                System.out.print("Novo Juros de Empréstimo: ");
                double novoJuros = scanner.nextDouble();
                scanner.nextLine();
                conta.setJurosEmprestimo(novoJuros);
                System.out.println("Juros de Empréstimo atualizado para: " + novoJuros);
            }
            case 5 -> {
                return;
            }
            default -> System.out.println("Opção inválida.");
        }
    }

    public static void configurarContaPoupanca(ContaPoupanca conta) {
        System.out.println("1. Alterar Rendimento da Poupança");
        System.out.println("2. Alterar Limite de Saques por Dia");
        System.out.println("3. Alterar Limite de Saque");
        System.out.println("4. Voltar");
        System.out.println("Opção: ");

        int opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1 -> {
                System.out.print("Novo Rendimento da Poupança: ");
                double novoRendimento = scanner.nextDouble();
                scanner.nextLine();
                conta.setRendimentoPoupanca(novoRendimento);
                System.out.println("Rendimento da Poupança atualizado para: " + novoRendimento);
            }
            case 2 -> {
                System.out.print("Novo Limite de Saques por Dia: ");
                int novoLimite = scanner.nextInt();
                scanner.nextLine();
                conta.setLimiteSaquesPorDia(novoLimite);
                System.out.println("Limite de Saques por Dia atualizado para: " + novoLimite);
            }
            case 3 -> {
                System.out.print("Novo Limite de Saque: ");
                double novoLimite = scanner.nextDouble();
                scanner.nextLine();
                conta.setLimiteSaque(novoLimite);
                System.out.println("Limite de Saque atualizado para: " + novoLimite);
            }
            case 4 -> {
                return;
            }
            default -> System.out.println("Opção inválida.");
        }
    }

    public static String solicitarNome() {
        System.out.print("Nome: ");
        return scanner.nextLine();
    }

    public static String solicitarCPF() {
        while (true) {
            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            if (cpf.matches("\\d{11}")) {
                return cpf;
            } else {
                System.out.println("CPF inválido. Deve conter exatamente 11 dígitos.");
            }
        }
    }

    public static int gerarNumeroConta() {
        return random.nextInt(10000);
    }
}
