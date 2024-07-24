
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public abstract class Conta {

    protected static double saldo;
    private final int numero;
    private String cpf;
    protected Map<String, String> transacoes;
    protected Map<Integer, String> extratoCartaoCredito;
    private int contadorTransacaoCartao;
    private boolean cartaoCreditoContratado;
    private Integer dataHora;
    private double limiteDisponivel;
    private String nome;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public Conta(int numero, double limiteSaque, String nome, String cpf) {
        this.numero = numero;
        this.nome = nome;
        this.cpf = cpf;
        this.transacoes = new HashMap<>();
        this.extratoCartaoCredito = new HashMap<>();
    }

    public static DateTimeFormatter getFormatter() {
        return formatter;

    }

    public int getContadorTransacaoCartao() {
        return contadorTransacaoCartao;

    }

    public void setContadorTransacaoCartao(int i) {
        this.contadorTransacaoCartao = i;

    }

    public String getNome() {
        return nome;
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        Conta.saldo = saldo;
    }

    public boolean sacar(double valor) {
        if (saldo >= valor) {
            saldo -= valor;
            registrarTransacao("Saque", "R$ " + valor);
            return true;
        }
        return false;
    }

    public void depositar(double valor) {
        saldo += valor;
        registrarTransacao("Depósito", "R$ " + valor);
    }

    public abstract void imprimirExtrato();

    public void registrarTransacao(String tipo, String descricao) {
        transacoes.put(tipo, descricao);
    }

    public boolean isCartaoCreditoContratado() {
        return cartaoCreditoContratado;
    }

    public void setCartaoCreditoContratado(boolean cartaoCreditoContratado) {
        this.cartaoCreditoContratado = cartaoCreditoContratado;
    }

    public void registrarTransacaoCartaoCredito(String descricao, String descricao1) {
        extratoCartaoCredito.put(dataHora, descricao);
    }

    public Map<Integer, String> getExtratoCartaoCredito() {
        return extratoCartaoCredito;
    }

    public void registrarTransacaoCartaoCredito(int numeroTransacao, String descricao) {
        extratoCartaoCredito.put(numeroTransacao, descricao);
    }

    public double getLimiteDisponivel() {
        return limiteDisponivel;
    }

    public void setLimiteDisponivel(double limiteDisponivel) {
        this.limiteDisponivel = limiteDisponivel;
    }

    @Override
    public String toString() {
        return "Nome do Cliente: " + nome + ", Tipo de Conta: " + this.getClass().getSimpleName()
                + ", Número da Conta: " + numero;
    }

}
