package reservaHotel;

import javax.swing.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
        Map<String, String> quartos = new HashMap<>();
        List<String> historicoReservas = new ArrayList<>();
        LocalDate dataAtual = LocalDate.now();
        menuHotel(quartos, historicoReservas);
    }
    
    public static void menuHotel(Map<String, String> quartos, List<String> historicoReservas) {
    	
        inicializarQuartos(quartos);

        String[] options = {"Reservar Quarto", "Realizar Check-out", "Listar Quartos Vagos", "Listar Quartos Ocupados", "Mostrar Histórico de Reservas", "Sair"};

        while (true) {
            int choice = JOptionPane.showOptionDialog(null, "Escolha uma opção", "Sistema de Reservas de Granville",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                	System.out.println("Realizando CheckIn");
                    reservarQuarto(quartos, historicoReservas);
                    break;
                case 1:
                	System.out.println("Realizando CheckOut");
                    realizarCheckOut(quartos, historicoReservas);
                    break;
                case 2:
                	System.out.println("Listando quartos vagos.");
                    listarQuartosVagos(quartos);
                    break;
                case 3:
                	System.out.println("Listando quartos ocupados.");
                    listarQuartosOcupados(quartos);
                    break;
                case 4:
                	System.out.println("Historico de reservas.");
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
        LocalDate dataAtual = LocalDate.now();
        String[] quartosVagosArray = quartosLivres.split("\n");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        int escolhaQuarto = JOptionPane.showOptionDialog(null, "Escolha um quarto", "Quartos Disponíveis",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, quartosVagosArray, quartosVagosArray[0]);

        if (escolhaQuarto == JOptionPane.CLOSED_OPTION) {
            return;
        }

        String codigoQuarto = quartosVagosArray[escolhaQuarto].replace("Quarto ", "").trim();
        System.out.println(codigoQuarto);
        
        String dataEntrada = JOptionPane.showInputDialog("Data: (Formato: DD-MM-AAAA)");
        LocalDate data = LocalDate.parse(dataEntrada, formatter);
        int quantidadeDias = Integer.parseInt(JOptionPane.showInputDialog("Quantidade de Dias:"));
        LocalDate dataSaida = dataAtual.plusDays(quantidadeDias);
        System.out.println(dataSaida);  
        if(!data.isBefore(dataAtual)) {
        	System.out.println(dataEntrada);
        	 String nome = JOptionPane.showInputDialog("Nome:");
             System.out.println(nome);
             String cpf = JOptionPane.showInputDialog("CPF:");
             System.out.println(cpf);
        	String[] paymentOptions = {"Crédito", "Débito"};
            int choicePagamento = JOptionPane.showOptionDialog(null, "Escolha uma forma de pagamento:", "Forma de pagamento:", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, paymentOptions, paymentOptions[0]);
            String formaPagamento = choicePagamento == 0 ? "Crédito" : "Débito";
            System.out.println(formaPagamento);            
            String telefone = JOptionPane.showInputDialog("Telefone:");
            System.out.println(telefone);
            String email = JOptionPane.showInputDialog("Email:");
            System.out.println(email);

            quartos.put(codigoQuarto, nome);
            historicoReservas.add("Check-in - Quarto: " + codigoQuarto + ", Nome: " + nome + ", CPF: " + cpf + ", Data de Entrada: " + dataEntrada + ", Data de Saida: " + dataSaida + ", Forma de Pagamento: " + formaPagamento + ", Dias: " + quantidadeDias + ", Telefone: " + telefone + ", Email: " + email);
            JOptionPane.showMessageDialog(null, "Check-in realizado com sucesso!");
        }else {
        	JOptionPane.showMessageDialog(null,"Não e possivel fazer o checkIn nessa data.");
        }
                  
    }

    public static String listarQuartosVagos(Map<String, String> quartos) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : quartos.entrySet()) {
            if (entry.getValue() == null) {
                sb.append("Quarto ").append(entry.getKey()).append("\n");
            }
        }

        String result = sb.toString().trim();
        JOptionPane.showMessageDialog(null, result.isEmpty() ? "Não há quartos vagos." : result);
        return result;
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
