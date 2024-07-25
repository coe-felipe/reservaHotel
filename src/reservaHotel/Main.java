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
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Por favor, selecione uma opção válida.");
            }
        }
    }

    private static void inicializarQuartos(Map<String, String> quartos) {
        String[] codigos = {"S101", "S102", "S103", "S204", "S201", "S202", "S203", "S204",
                            "S301", "S302", "S303", "S304", "S301", "S302", "S303", "S304"};
        for (String codigo : codigos) {
            quartos.put(codigo, null);
        }
    }

    private static void reservarQuarto(Map<String, String> quartos, List<String> historicoReservas) {
        String nome = JOptionPane.showInputDialog("Nome:");
        String cpf = JOptionPane.showInputDialog("CPF:");
        String[] paymentOptions = {"Crédito", "Débito"};
        int choicePagamento = JOptionPane.showOptionDialog(null, "Escolha uma forma de pagamento:", "Forma de pagamento:", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, paymentOptions, paymentOptions[0]);                 
        String formaPagamento;
        switch (choicePagamento) {
            case 0:                         
                formaPagamento = "Crédito";                   
                break;
            case 1:                       
                formaPagamento = "Débito";                        
                break;
            default:
                formaPagamento = "Nenhuma forma de pagamento selecionada";
                break;
        }

        int quantidadeDias = Integer.parseInt(JOptionPane.showInputDialog("Quantidade de Dias:"));
        String telefone = JOptionPane.showInputDialog("Telefone:");
        String email = JOptionPane.showInputDialog("Email:");
        String codigoQuarto = JOptionPane.showInputDialog("Código do Quarto:");
        
        if (quartos.containsKey(codigoQuarto) && quartos.get(codigoQuarto) == null) {
            quartos.put(codigoQuarto, nome);
            historicoReservas.add("Check-in - Quarto: " + codigoQuarto + ", Nome: " + nome + ", CPF: " + cpf + ", Forma de Pagamento: " + formaPagamento + ", Dias: " + quantidadeDias + ", Telefone: " + telefone + ", Email: " + email);
            JOptionPane.showMessageDialog(null, "Check-in realizado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Quarto " + codigoQuarto + " não disponível.");
        }
    }

    private static void realizarCheckOut(Map<String, String> quartos, List<String> historicoReservas) {
        String codigoQuarto = JOptionPane.showInputDialog("Código do Quarto para Check-out:");
        if (quartos.containsKey(codigoQuarto) && quartos.get(codigoQuarto) != null) {
            historicoReservas.add("Check-out - Quarto: " + codigoQuarto + ", Nome: " + quartos.get(codigoQuarto));
            quartos.put(codigoQuarto, null);
            JOptionPane.showMessageDialog(null, "Check-out realizado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Quarto " + codigoQuarto + " já está vazio.");
        }
    }

    private static void listarQuartosVagos(Map<String, String> quartos) {
        StringBuilder sb = new StringBuilder("Quartos vagos:\n");
        for (Map.Entry<String, String> entry : quartos.entrySet()) {
            if (entry.getValue() == null) {
                sb.append("Quarto ").append(entry.getKey()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void listarQuartosOcupados(Map<String, String> quartos) {
        StringBuilder sb = new StringBuilder("Quartos ocupados:\n");
        for (Map.Entry<String, String> entry : quartos.entrySet()) {
            if (entry.getValue() != null) {
                sb.append("Quarto ").append(entry.getKey()).append(" ocupado por ").append(entry.getValue()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void mostrarHistoricoReservas(List<String> historicoReservas) {
        StringBuilder sb = new StringBuilder("Histórico de Reservas:\n");
        for (String registro : historicoReservas) {
            sb.append(registro).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }
}
