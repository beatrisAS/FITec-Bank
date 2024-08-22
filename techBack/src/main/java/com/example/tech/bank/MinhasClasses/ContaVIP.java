import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ContaVIP extends Conta {

    private double limiteSaque;
    private double limiteCredito;
    private double limiteEmprestimo;
    private int limiteSaquesPorDia;
    private String horarioLimiteTransacoes;
    private double jurosEmprestimo;
    private final String cpf;
    private final double salario;
    private Map<String, String> seguros = new HashMap<>();
    private double saldoInvestimento;
    private int quantidadeSaquesDia;
    private final Map<String, Double> investimentos = new HashMap<>();
    private int contadorTransacaoCartao;
    private String dataHora;
    private boolean cartaoContratado;
    private String nome;

    public ContaVIP(int numero, double saldo, double limiteSaque, double limiteCredito,
            double limiteEmprestimo, String nome, String cpf, double salario) {
        super(numero, saldo, nome, cpf);
        this.limiteSaque = limiteSaque;
        this.limiteCredito = limiteCredito;
        this.limiteEmprestimo = limiteEmprestimo;
        this.nome = nome;
        this.cpf = cpf;
        this.salario = salario;
        this.saldoInvestimento = 0.0;
        this.quantidadeSaquesDia = 0;
        this.cartaoContratado = false;
    }

    public double getJurosEmprestimo() {
        return jurosEmprestimo;
    }

    public void setJurosEmprestimo(double juros) {
        this.jurosEmprestimo = juros;
    }

    @Override
    public boolean sacar(double valor) {
        if (valor <= 0) {
            System.out.println("Erro: Valor de saque inválido.");
            return false;
        }

        if (valor > this.getSaldo()) {
            System.out.println("Saldo insuficiente para realizar o saque.");
            return false;
        }

        if (this.getLimiteSaquesPorDia() > 0 && this.getQuantidadeSaquesDia() >= this.getLimiteSaquesPorDia()) {
            System.out.println("Limite diário de saques alcançado.");
            return false;
        }

        if (this.getHorarioLimiteTransacoes() != null
                && !Administrador.verificarHorarioTransacao(this.getHorarioLimiteTransacoes())) {
            System.out.println("Não é permitido realizar saques neste horário.");
            return false;
        }

        this.setSaldo(this.getSaldo() - valor);
        this.incrementarQuantidadeSaques();
        registrarTransacao("Saque", "R$ " + valor);
        return true;
    }

    public double getLimiteSaque() {
        return limiteSaque;
    }

    public void setLimiteSaque(double limiteSaque) {
        this.limiteSaque = limiteSaque;
    }

    public static double setLimiteCredito(double limiteCredito) {
        return limiteCredito;
    }

    public double getLimiteEmprestimo() {
        return limiteEmprestimo;
    }

    public void setLimiteEmprestimo(double limiteEmprestimo) {
        this.limiteEmprestimo = limiteEmprestimo;
    }

    public int getLimiteSaquesPorDia() {
        return limiteSaquesPorDia;
    }

    public void setLimiteSaquesPorDia(int limiteSaquesPorDia) {
        this.limiteSaquesPorDia = limiteSaquesPorDia;
    }

    public String getHorarioLimiteTransacoes() {
        return horarioLimiteTransacoes;
    }

    public void setHorarioLimiteTransacoes(String horarioLimiteTransacoes) {
        this.horarioLimiteTransacoes = horarioLimiteTransacoes;
    }

    public String getCpf() {
        return cpf;
    }

    public double getSalario() {
        return salario;
    }

    public void setSaldoInvestimento(double saldoInvestimento) {
        this.saldoInvestimento = saldoInvestimento;
    }

    public double getSaldoInvestimento() {
        return saldoInvestimento;
    }

    public int getQuantidadeSaquesDia() {
        return quantidadeSaquesDia;
    }

    public void incrementarQuantidadeSaques() {
        this.quantidadeSaquesDia++;
    }

    public double getLimiteCredito() {
        return this.limiteCredito;
    }

    public boolean solicitarEmprestimo(double valor) {
        if (valor <= limiteEmprestimo) {
            saldo += valor;
            registrarTransacao("Empréstimo", "R$ " + valor);
            return true;
        } else {
            System.out.println("Empréstimo negado.");
            return false;
        }
    }

    @Override
    public Map<Integer, String> getExtratoCartaoCredito() {
        return this.extratoCartaoCredito;
    }

    public Map<String, Double> getInvestimentos() {
        return investimentos;
    }

    public void adicionarInvestimento(String tipo, double valor) {
        if (this.getSaldo() >= valor) {
            investimentos.put(tipo, valor);
            this.setSaldo(this.getSaldo() - valor);
            registrarTransacao("Investimento", "R$ " + valor + " em " + tipo);
        } else {
            System.out.println("Saldo insuficiente para realizar o investimento.");
        }
    }

    public void removerInvestimento(String tipo, double valor) {
        if (investimentos.containsKey(tipo) && investimentos.get(tipo) >= valor) {
            investimentos.put(tipo, investimentos.get(tipo) - valor);
            this.setSaldo(this.getSaldo() + valor);
            registrarTransacao("Regate de Investimento", "R$ " + valor + " de " + tipo);
        } else {
            System.out.println("Investimento não encontrado ou valor inválido.");
        }
    }

    public void contratarSeguro(String tipoSeguro, double valorSeguro) {
        if (this.getSaldo() >= valorSeguro) {
            seguros.put(tipoSeguro, "Valor: R$ " + valorSeguro);
            this.setSaldo(this.getSaldo() - valorSeguro);
            registrarTransacao("Seguro", "" + tipoSeguro + ", contratado por R$ " + valorSeguro);
        } else {
            System.out.println("Saldo insuficiente para contratar o seguro.");
        }
    }

    public void removerSeguro(String tipoSeguro) {
        if (seguros.containsKey(tipoSeguro)) {
            String valorStr = seguros.get(tipoSeguro).replace("Valor: R$ ", "");
            double valorSeguro = Double.parseDouble(valorStr);
            seguros.remove(tipoSeguro);
            this.setSaldo(this.getSaldo() + valorSeguro);
            registrarTransacao("Cancelamento de Seguro", "R$ " + valorSeguro + " de " + tipoSeguro);
        } else {
            System.out.println("Tipo de seguro não encontrado.");
        }
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public boolean isCartaoContratado() {
        return cartaoContratado;
    }

    public void setCartaoContratado(boolean cartaoContratado) {
        this.cartaoContratado = cartaoContratado;
    }

    @Override
    public void registrarTransacaoCartaoCredito(String descricao, String descricao1) {
        super.registrarTransacaoCartaoCredito(dataHora, descricao);
    }

    @Override
    public void setContadorTransacaoCartao(int i) {
        this.contadorTransacaoCartao = i;
    }

    public void fazerCompraCartaoCredito(double valor, String loja) {
        if (valor <= limiteCredito) {
            contadorTransacaoCartao++;
            String descricao = "Compra em " + loja + " no valor de R$" + valor;
            registrarTransacao("Cartão de crédito", descricao);
            LocalDateTime agora = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String dataHora = agora.format(formato);
            extratoCartaoCredito.put(contadorTransacaoCartao, dataHora + " - " + descricao);
            limiteCredito -= valor;
        } else {
            System.out.println("Compra não autorizada. Limite insuficiente.");
        }
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public double getLimiteDisponivel() {
        return limiteCredito;
    }

    @Override
    public void setLimiteDisponivel(double limiteDisponivel) {
        this.limiteCredito = limiteDisponivel;
    }

    @Override
    public void registrarTransacao(String tipo, String descricao) {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataHora = agora.format(formato);
        transacoes.put(dataHora + " - " + tipo, descricao);
    }

    @Override
    public void registrarTransacaoCartaoCredito(int numeroTransacao, String descricao) {
        extratoCartaoCredito.put(numeroTransacao, descricao);
    }

    @Override
    public void imprimirExtrato() {
        System.out.println("=== Extrato ===");
        System.out.println("Saldo atual: R$ " + getSaldo());

        for (Map.Entry<String, String> entry : transacoes.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public void registrarTransacao(String tipo, String descricao, Map<String, String> ExtratoCartaoCredito) {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataHora = agora.format(formato);
        ExtratoCartaoCredito.put(dataHora + " - " + tipo, descricao);
    }

    public Map<String, String> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(Map<String, String> transacoes) {
        this.transacoes = transacoes;
    }

    public void imprimirExtratoCartaoCredito() {
        System.out.println("=== Extrato do Cartão de Crédito ===");
        System.out.println("Limite disponível: R$ " + getLimiteDisponivelCartaoCredito());
        for (Map.Entry<Integer, String> entry : extratoCartaoCredito.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public String getLimiteDisponivelCartaoCredito() {
        return String.format("%.2f", getLimiteCredito() - getSaldo());
    }

    public Map<String, String> getSeguros() {
        return seguros;
    }

    public void setSeguros(Map<String, String> seguros) {
        this.seguros = seguros;
    }

    public void setExtratoCartaoCredito(Map<Integer, String> extratoCartaoCredito) {
        this.extratoCartaoCredito = extratoCartaoCredito;
    }

    @Override
    public int getContadorTransacaoCartao() {
        return contadorTransacaoCartao;
    }
}
