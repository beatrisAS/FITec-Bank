import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Administrador {

    public void aplicarRegrasGerais(ContaCorrente cc) {
        cc.setLimiteCredito(5000.0);
        cc.setLimiteSaquesPorDia(5);
        cc.setHorarioLimiteTransacoes("18:00");
        cc.setLimiteSaque(20000.0);
        cc.setLimiteEmprestimo(10000.0);
        cc.setJurosEmprestimo(0.02);
    }

    public void aplicarRegrasContaPoupanca(ContaPoupanca cp) {
        cp.setLimiteSaquesPorDia(5);
        cp.setHorarioLimiteTransacoes("18:00");
        cp.setLimiteSaque(20000.0);
        cp.setRendimentoPoupanca(0.005);
    }

    public void aplicarRegrasContaVIP(ContaVIP vip) {
        ContaVIP.setLimiteCredito(50000.0);
        vip.setLimiteSaque(5000000.0);
        vip.setLimiteEmprestimo(200000.0);
        vip.setHorarioLimiteTransacoes("18:00");
        vip.setJurosEmprestimo(0.02);
    }

    public void modificarRendimentoPoupanca(ContaPoupanca cp, double novoRendimento) {
        cp.setRendimentoPoupanca(novoRendimento);
    }

    public void listarContas(Banco banco) {
        banco.listarContas();
    }

    public static boolean verificarHorarioTransacao(String horarioLimiteTransacoes) {
        if (horarioLimiteTransacoes == null || horarioLimiteTransacoes.isEmpty()) {
            return true;
        }

        String[] horarios = horarioLimiteTransacoes.split("-");
        if (horarios.length != 2) {
            return false;
        }

        try {
            LocalTime inicio = LocalTime.parse(horarios[0], DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime fim = LocalTime.parse(horarios[1], DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime agora = LocalTime.now();

            return !agora.isBefore(inicio) && !agora.isAfter(fim);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
