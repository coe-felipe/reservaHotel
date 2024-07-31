package reservaHotel;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
        // Dados dos quartos
        String[] codigosQuartos = {"S101", "S102", "S103", "S201", "S202", "S203", "S204", "S301", "S302", "S303", "S304"};
        String[] nomesOcupantes = new String[codigosQuartos.length];
        String[] historicoReservas = new String[100];  // Definido um tamanho máximo para o histórico
        int[] historicoIndex = {0};  // Usado para rastrear a quantidade de registros no histórico

        LocalDate dataAtual = LocalDate.now();
        menuHotel(codigosQuartos, nomesOcupantes, historicoReservas, historicoIndex);
    }
    
    public static void menuHotel(String[] codigosQuartos, String[] nomesOcupantes, String[] historicoReservas, int[] historicoIndex) {
        String[] options = {"Reservar Quarto", "Realizar Check-out", "Listar Quartos Vagos", "Listar Quartos Ocupados", "Mostrar Histórico de Reservas", "Sair"};

        while (true) {
            int choice = JOptionPane.showOptionDialog(null, "Escolha uma opção", "Sistema de Reservas de Granville",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    System.out.println("Realizando CheckIn");
                    reservarQuarto(codigosQuartos, nomesOcupantes, historicoReservas, historicoIndex);
                    break;
                case 1:
                    System.out.println("Realizando CheckOut");
                    realizarCheckOut(codigosQuartos, nomesOcupantes, historicoReservas, historicoIndex);
                    break;
                case 2:
                    System.out.println("Listando quartos vagos.");
                    listarQuartosVagos(codigosQuartos, nomesOcupantes);
                    break;
                case 3:
                    System.out.println("Listando quartos ocupados.");
                    listarQuartosOcupados(codigosQuartos, nomesOcupantes);
                    break;
                case 4:
                    System.out.println("Histórico de reservas.");
                    mostrarHistoricoReservas(historicoReservas, historicoIndex);
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

    public static void reservarQuarto(String[] codigosQuartos, String[] nomesOcupantes, String[] historicoReservas, int[] historicoIndex) {
        String quartosLivres = listarQuartosVagos(codigosQuartos, nomesOcupantes);
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
        LocalDate dataSaida = data.plusDays(quantidadeDias);
        System.out.println(dataSaida);  
        if (!data.isBefore(dataAtual)) {
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

            for (int i = 0; i < codigosQuartos.length; i++) {
                if (codigosQuartos[i].equals(codigoQuarto)) {
                    nomesOcupantes[i] = nome;
                    break;
                }
            }
            historicoReservas[historicoIndex[0]++] = "Check-in - Quarto: " + codigoQuarto + ", Nome: " + nome + ", CPF: " + cpf + ", Data de Entrada: " + dataEntrada + ", Data de Saida: " + dataSaida + ", Forma de Pagamento: " + formaPagamento + ", Dias: " + quantidadeDias + ", Telefone: " + telefone + ", Email: " + email;
            JOptionPane.showMessageDialog(null, "Check-in realizado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(null, "Não é possível fazer o check-in nessa data.");
        }
    }

    public static String listarQuartosVagos(String[] codigosQuartos, String[] nomesOcupantes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < codigosQuartos.length; i++) {
            if (nomesOcupantes[i] == null) {
                sb.append("Quarto ").append(codigosQuartos[i]).append("\n");
            }
        }

        String result = sb.toString().trim();
        JOptionPane.showMessageDialog(null, result.isEmpty() ? "Não há quartos vagos." : result);
        return result;
    }

    public static String listarQuartosOcupados(String[] codigosQuartos, String[] nomesOcupantes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < codigosQuartos.length; i++) {
            if (nomesOcupantes[i] != null) {
                sb.append("Quarto ").append(codigosQuartos[i]).append(" ocupado por ").append(nomesOcupantes[i]).append("\n");
            }
        }
        String result = sb.toString().trim();
        JOptionPane.showMessageDialog(null, result.isEmpty() ? "Não há quartos ocupados." : result);
        return result;
    }

    public static void realizarCheckOut(String[] codigosQuartos, String[] nomesOcupantes, String[] historicoReservas, int[] historicoIndex) {
        String quartosOcupados = listarQuartosOcupados(codigosQuartos, nomesOcupantes);
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

        for (int i = 0; i < codigosQuartos.length; i++) {
            if (codigosQuartos[i].equals(codigoQuarto)) {
                if (nomesOcupantes[i] != null) {
                    historicoReservas[historicoIndex[0]++] = "Check-out - Quarto: " + codigoQuarto + ", Nome: " + nomesOcupantes[i];
                    nomesOcupantes[i] = null;
                    JOptionPane.showMessageDialog(null, "Check-out realizado com sucesso!");
                    return;
                } else {
                    JOptionPane.showMessageDialog(null, "Quarto " + codigoQuarto + " já está vazio ou inexistente.");
                    return;
                }
            }
        }
    }
    
    public static void mostrarHistoricoReservas(String[] historicoReservas, int[] historicoIndex) {
        StringBuilder sb = new StringBuilder("Histórico de Reservas:\n");    
        for (int i = 0; i < historicoIndex[0]; i++) {
            sb.append(historicoReservas[i]).append("\n");
        }
        
        JOptionPane.showMessageDialog(null, sb.toString());
    }
}
