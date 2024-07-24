
import java.util.HashMap;
import java.util.Map;

class Banco {
    private final Map<Integer, Conta> contas = new HashMap<>();

    public void adicionarConta(Conta conta) {
        contas.put(conta.getNumero(), conta);
    }

    public Conta encontrarConta(int numeroConta) {
        return contas.get(numeroConta);
    }

    public Map<Integer, Conta> getContas() {
        return contas;
    }

    public Map<Integer, Conta> listarContas() {
        return contas;
    }
}