
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ContaPoupanca extends Conta {

    private double limiteSaque;
    private int limiteSaquesPorDia;
    private String horarioLimiteTransacoes;
    private static String cpf;
    private int quantidadeSaquesDia;
    private double rendimentoPoupanca;
    private boolean depositoAutomatico;
    private String cliente;
    private Map<String, String> transacoes = new HashMap<>();
    private String nome;

    public ContaPoupanca(int numero, int limiteSaquesPorDia, double limiteSaque, String nome, String cpf) {
        super(numero, limiteSaque, nome, cpf);
        this.limiteSaque = limiteSaque;
        this.nome = nome;
        this.limiteSaquesPorDia = limiteSaquesPorDia;
        this.transacoes = new HashMap<>();
    }

    @Override
    public String getNome() {
        return nome;
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

    public double getRendimentoPoupanca() {
        return rendimentoPoupanca;
    }

    public void setRendimentoPoupanca(double rendimentoPoupanca) {
        this.rendimentoPoupanca = rendimentoPoupanca;
    }

    public double calcularRendimento() {
        return getSaldo() * rendimentoPoupanca;
    }

    @Override
    public void imprimirExtrato() {
        System.out.println("=== Extrato ===");
        System.out.println("Saldo atual: R$ " + getSaldo());
        for (Map.Entry<String, String> entry : transacoes.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    @Override
    public void registrarTransacao(String tipo, String descricao) {
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        var dataHora = agora.format(formato);
        transacoes.put(dataHora + " - " + tipo, descricao);
    }

    public boolean isDepositoAutomatico() {
        return depositoAutomatico;
    }

    public void setDepositoAutomatico(boolean depositoAutomatico) {
        this.depositoAutomatico = depositoAutomatico;
    }

    public Map<String, String> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(Map<String, String> transacoes) {
        this.transacoes = transacoes;
    }

    public static void setCpf(String cpf) {
        ContaPoupanca.cpf = cpf;
    }

    public void setQuantidadeSaquesDia(int quantidadeSaquesDia) {
        this.quantidadeSaquesDia = quantidadeSaquesDia;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
}
