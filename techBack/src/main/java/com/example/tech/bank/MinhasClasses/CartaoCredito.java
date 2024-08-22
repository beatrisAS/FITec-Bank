import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class CartaoCredito {
    private double limiteCredito;
    private double limiteDisponivel;
    private int contadorTransacaoCartao;
    private final Map<Integer, String> extratoCartaoCredito;

    public CartaoCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
        this.limiteDisponivel = limiteCredito;
        this.contadorTransacaoCartao = 0;
        this.extratoCartaoCredito = new HashMap<>();
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
        this.limiteDisponivel = limiteCredito;
    }

    public double getLimiteDisponivel() {
        return limiteDisponivel;
    }

    public void setLimiteDisponivel(double limiteDisponivel) {
        this.limiteDisponivel = limiteDisponivel;
    }

    public int getContadorTransacaoCartao() {
        return contadorTransacaoCartao;
    }

    public void registrarTransacaoCartaoCredito(String descricao) {
        contadorTransacaoCartao++;
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataHora = agora.format(formato);
        extratoCartaoCredito.put(contadorTransacaoCartao, dataHora + " - " + descricao);
    }

    public void fazerCompra(double valor, String loja) {
        if (valor <= limiteDisponivel) {
            registrarTransacaoCartaoCredito("Compra em " + loja + " no valor de R$" + valor);
            limiteDisponivel -= valor;
        } else {
            System.out.println("Compra não autorizada. Limite insuficiente.");
        }
    }

    public Map<Integer, String> getExtratoCartaoCredito() {
        return extratoCartaoCredito;
    }

    public void imprimirExtratoCartaoCredito() {
        System.out.println("=== Extrato do Cartão de Crédito ===");
        System.out.println("Limite disponível: R$ " + limiteDisponivel);
        for (Map.Entry<Integer, String> entry : extratoCartaoCredito.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}