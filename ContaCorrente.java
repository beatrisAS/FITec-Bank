import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ContaCorrente extends Conta {
    private double limiteSaque;
    private double limiteCredito;
    private double limiteEmprestimo;
    private int limiteSaquesPorDia;
    private String horarioLimiteTransacoes;
    private double jurosEmprestimo;
    private final String cpf;
    private int quantidadeSaquesDia;
    private Object cartaoCredito;
    private int contadorTransacaoCartao;
    private double limiteDisponivel;
    private boolean cartaoContratado;
    private String nome;

    public ContaCorrente(int numero, double saldo, double limiteSaque, double limiteCredito, double limiteEmprestimo,
            String nome, String cpf) {
        super(numero, limiteSaque, nome, cpf);
        this.limiteSaque = limiteSaque;
        this.limiteCredito = limiteCredito;
        this.limiteEmprestimo = limiteEmprestimo;
        this.nome = nome;
        this.cpf = cpf;
        this.quantidadeSaquesDia = 0;
        this.cartaoContratado = false;
    }

    @Override
    public String getNome() {
        return nome;
    }

    public double getJurosEmprestimo() {
        return jurosEmprestimo;
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
        System.out.println("Saque de R$" + valor + " realizado com sucesso.");
        return true;
    }

    public void setJurosEmprestimo(double juros) {
        this.jurosEmprestimo = juros;
    }

    public int getQuantidadeSaquesDia() {
        return quantidadeSaquesDia;
    }

    public void incrementarQuantidadeSaques() {
        this.quantidadeSaquesDia++;
    }

    public double getLimiteSaque() {
        return limiteSaque;
    }

    public void setLimiteSaque(double limiteSaque) {
        this.limiteSaque = limiteSaque;
    }

    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
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

    public double getLimiteCredito() {
        return this.limiteCredito;
    }

    public boolean solicitarEmprestimo(double valor) {
        if (valor <= limiteEmprestimo) {
            saldo += valor;
            registrarTransacao("Empréstimo:", "R$ " + valor);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void imprimirExtrato() {
        System.out.println("=== Extrato ===");
        System.out.println("Saldo atual: R$ " + getSaldo());
        for (Map.Entry<String, String> entry : transacoes.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        for (Map.Entry<Integer, String> entry : extratoCartaoCredito.entrySet()) {
            System.out.println("Cartão de Crédito - Transação em " + entry.getKey() + ": " + entry.getValue());
        }
    }

    @Override
    public void registrarTransacao(String tipo, String descricao) {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataHora = agora.format(formato);
        transacoes.put(dataHora + " - " + tipo, descricao);
    }

    public boolean isCartaoContratado() {
        return cartaoContratado;
    }

    public void setCartaoContratado(boolean cartaoContratado) {
        this.cartaoContratado = cartaoContratado;
    }

    @Override
    public void registrarTransacaoCartaoCredito(String dataHora, String descricao) {
        super.registrarTransacaoCartaoCredito(dataHora, descricao);
    }

    @Override
    public void setContadorTransacaoCartao(int i) {
        this.contadorTransacaoCartao = i;
    }

    public void fazerCompraCartaoCredito(double valor, String loja) {
        if (hasCartaoCredito() && valor <= getLimiteDisponivel()) {
            String dataHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new DateUtil());
            String descricao = "Compra na loja " + loja + " no valor de R$" + valor;
            registrarTransacaoCartaoCredito(dataHora, descricao);
            setLimiteDisponivel(getLimiteDisponivel() - valor);
        } else {
            throw new UnsupportedOperationException("Compra não autorizada. Verifique o limite disponível.");
        }
    }

    public boolean hasCartaoCredito() {
        return this.cartaoCredito != null;
    }

    @Override
    public double getLimiteDisponivel() {
        return limiteDisponivel;
    }

    @Override
    public void setLimiteDisponivel(double limiteDisponivel) {
        this.limiteDisponivel = limiteDisponivel;
    }

    @Override
    public Map<Integer, String> getExtratoCartaoCredito() {
        return this.extratoCartaoCredito;
    }

    @Override
    public int getContadorTransacaoCartao() {
        return contadorTransacaoCartao;
    }
}
