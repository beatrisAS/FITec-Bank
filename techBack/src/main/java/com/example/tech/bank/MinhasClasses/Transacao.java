
import java.util.Date;

public class Transacao {

    private String tipo;
    private double valor;
    private Date dataHora;

    public Transacao(String tipo, double valor) {
        this.tipo = tipo;
        this.valor = valor;
        this.dataHora = new Date();
    }

    public Date getDataHora() {
        return dataHora;
    }

    @Override
    public String toString() {
        return dataHora
                + tipo + ", valor = " + valor;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
