package reservaHotel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, String> quartos = new HashMap<>();
        List<String> historicoReservas = new ArrayList<>();
        
        menuHotel(quartos, historicoReservas);
    }
    
    public static void menuHotel(Map<String, String> quartos, List<String> historicoReservas) {
    	
        inicializarQuartos(quartos);

        String[] options = {"Reservar Quarto", "Realizar Check-out", "Listar Quartos Vagos", "Listar Quartos Ocupados", "Mostrar Histórico de Reservas", "Sair"};

        while (true) {
            int choice = JOptionPane.showOptionDialog(null, "Escolha uma opção", "Sistema de Reservas de Hotel",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    reservarQuarto(quartos, historicoReservas);
                    break;
                case 1:
                    realizarCheckOut(quartos, historicoReservas);
                    break;
                case 2:
                    listarQuartosVagos(quartos);
                    break;
                case 3:
                    listarQuartosOcupados(quartos);
                    break;
                case 4:
                    mostrarHistoricoReservas(historicoReservas);
                    break;
                case 5:
                    System.out.println("Saindo...");
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Por favor, selecione uma opção válida.");
            }
        }
    	
    }
    
    public static void inicializarQuartos(Map<String, String> quartos) {
        String[] codigos = {"S101", "S102", "S103", "S201", "S202", "S203", "S204", "S301", "S302", "S303", "S304"};
        for (String codigo : codigos) {
            quartos.put(codigo, null);
        }
    }

    public static void reservarQuarto(Map<String, String> quartos, List<String> historicoReservas) {
        String quartosLivres = listarQuartosVagos(quartos);
        if (quartosLivres.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há quartos vagos disponíveis.");
            return;
        }
        
        String[] quartosVagosArray = quartosLivres.split("\n");

        int escolhaQuarto = JOptionPane.showOptionDialog(null, "Escolha um quarto", "Quartos Disponíveis",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, quartosVagosArray, quartosVagosArray[0]);

        if (escolhaQuarto == JOptionPane.CLOSED_OPTION) {
            return;
        }

        String codigoQuarto = quartosVagosArray[escolhaQuarto].replace("Quarto ", "").trim();

        if (quartos.containsKey(codigoQuarto) && quartos.get(codigoQuarto) == null) {
            String nome = JOptionPane.showInputDialog("Nome:");
            String cpf = JOptionPane.showInputDialog("CPF:");
            String[] paymentOptions = {"Crédito", "Débito"};
            int choicePagamento = JOptionPane.showOptionDialog(null, "Escolha uma forma de pagamento:", "Forma de pagamento:", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, paymentOptions, paymentOptions[0]);
            String formaPagamento = choicePagamento == 0 ? "Crédito" : "Débito";
            int quantidadeDias = Integer.parseInt(JOptionPane.showInputDialog("Quantidade de Dias:"));
            String telefone = JOptionPane.showInputDialog("Telefone:");
            String email = JOptionPane.showInputDialog("Email:");

            quartos.put(codigoQuarto, nome);
            historicoReservas.add("Check-in - Quarto: " + codigoQuarto + ", Nome: " + nome + ", CPF: " + cpf + ", Forma de Pagamento: " + formaPagamento + ", Dias: " + quantidadeDias + ", Telefone: " + telefone + ", Email: " + email);
            JOptionPane.showMessageDialog(null, "Check-in realizado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Quarto " + codigoQuarto + " não disponível.");
        }
    }

    public static String listarQuartosVagos(Map<String, String> quartos) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : quartos.entrySet()) {
            if (entry.getValue() == null) {
                sb.append("Quarto ").append(entry.getKey()).append("\n");
            }
        }
        return sb.toString().trim();
    }

    public static String listarQuartosOcupados(Map<String, String> quartos) {
    	StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : quartos.entrySet()) {
            if (entry.getValue() != null) {
                sb.append("Quarto ").append(entry.getKey()).append(" ocupado por ").append(entry.getValue()).append("\n");
            }
        }
        String result = sb.toString().trim();
        JOptionPane.showMessageDialog(null, result.isEmpty() ? "Não há quartos ocupados." : result);
        return result;
    }
    
    public static void realizarCheckOut(Map<String, String> quartos, List<String> historicoReservas) {
    	
    	 String quartosOcupados = listarQuartosOcupados(quartos);
         if (quartosOcupados.isEmpty()) {
              JOptionPane.showMessageDialog(null, "Não há quartos ocupados.");
              return;
          }

          String[] quartosOcupadosArray = quartosOcupados.split("\n");

          int escolhaQuarto = JOptionPane.showOptionDialog(null, "Escolha um quarto para check-out", "Quartos Ocupados",
              JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, quartosOcupadosArray, quartosOcupadosArray[0]);

          if (escolhaQuarto == JOptionPane.CLOSED_OPTION) {
              return;
          }

          String codigoQuarto = quartosOcupadosArray[escolhaQuarto].replace("Quarto ", "").split(" ")[0];

          if (quartos.containsKey(codigoQuarto) && quartos.get(codigoQuarto) != null) {
              historicoReservas.add("Check-out - Quarto: " + codigoQuarto + ", Nome: " + quartos.get(codigoQuarto));
              quartos.put(codigoQuarto, null);
              JOptionPane.showMessageDialog(null, "Check-out realizado com sucesso!");
          } else {
              JOptionPane.showMessageDialog(null, "Quarto " + codigoQuarto + " já está vazio ou inexistente.");
          }
    }
    
    public static void mostrarHistoricoReservas(List<String> historicoReservas) {
        StringBuilder sb = new StringBuilder("Histórico de Reservas:\n");
        for (String registro : historicoReservas) {
            sb.append(registro).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }
}
