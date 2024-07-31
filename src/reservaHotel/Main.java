package reservaHotel;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
    
    public static void main(String[] args) {
        // Códigos de todos os quartos do hotel.
        String[] codigosQuartos = {
            "S101", "S102", "S103", "S104", "S201", "S202", "S203", "S204",
            "S301", "S302", "S303", "S304", "S401", "S402", "S403", "S404"
        };
        // Nomes dos ocupantes, inicialmente todos os quartos estão vagos (null).
        String[] nomesOcupantes = new String[codigosQuartos.length]; 
        // Histórico de reservas, limitado a 100 entradas.
        String[] historicoReservas = new String[100];
        int[] historicoIndex = {0}; // Índice para o histórico de reservas.
        
        // Abre o menu principal.
        menu(codigosQuartos, nomesOcupantes, historicoReservas, historicoIndex);
    }
    
    public static void menu(String[] codigosQuartos, String[] nomesOcupantes, String[] historicoReservas, int[] historicoIndex) {
        // Opções do menu.
        String[] options = {
            "Reservar Quarto", "Realizar Check-out", "Listar Quartos Vagos",
            "Listar Quartos Ocupados", "Mostrar Histórico de Reservas", "Sair"
        };
        
        while (true) {
            // Cria um menu com botões para as opções.
            int choice = JOptionPane.showOptionDialog(
                null, "Escolha uma opção", "Sistema de Reservas de Granville",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]
            );
            
            // Ações baseadas na escolha do usuário.
            switch (choice) {
                case 0:
                    reservarQuarto(codigosQuartos, nomesOcupantes, historicoReservas, historicoIndex);
                    break;
                case 1:
                    realizarCheckOut(codigosQuartos, nomesOcupantes, historicoReservas, historicoIndex);
                    break;
                case 2:
                    listarQuartosVagos(codigosQuartos, nomesOcupantes);
                    break;
                case 3:
                    listarQuartosOcupados(codigosQuartos, nomesOcupantes);
                    break;
                case 4:
                    mostrarHistoricoReservas(historicoReservas, historicoIndex);
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Por favor, selecione uma opção válida.");
            }
        }
    }

    public static void reservarQuarto(String[] codigosQuartos, String[] nomesOcupantes, String[] historicoReservas, int[] historicoIndex) {
        String quartosLivres = listarQuartosVagos(codigosQuartos, nomesOcupantes); // Chama a função listarQuartosVagos e armazena o resultado.
        if (quartosLivres.isEmpty()) { // Verifica se há quartos vagos.
            JOptionPane.showMessageDialog(null, "Não há quartos vagos.");
            return;
        }
        
        // Busca a data atual.
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String[] quartosVagosArray = quartosLivres.split("\n"); // Separa os quartos vagos em um array.
        
        // Cria um menu para o usuário escolher o quarto.
        int escolhaQuarto = JOptionPane.showOptionDialog(
            null, "Escolha um quarto", "Quartos Disponíveis",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
            null, quartosVagosArray, quartosVagosArray[0]
        );
        
        if (escolhaQuarto == JOptionPane.CLOSED_OPTION) { // Se o usuário fechar o menu, retorna ao menu principal.
            return;
        }
        
        String codigoQuarto = quartosVagosArray[escolhaQuarto].replace("Quarto ", "").trim(); // Extrai o código do quarto escolhido.
        
        String dataEntrada = JOptionPane.showInputDialog("Data: (Formato: DD-MM-AAAA)");
        LocalDate data = LocalDate.parse(dataEntrada, formatter);
        
        //Verifica se a data e depois se nao retorna.
        if (!dataAtual.isBefore(data)) { 
        	return;
        }
        
        // Dados do hóspede.
        String nome = JOptionPane.showInputDialog("Nome:");
        String cpf = JOptionPane.showInputDialog("CPF:");
        String[] paymentOptions = {"Crédito", "Débito"};
        int choicePagamento = JOptionPane.showOptionDialog(
            null, "Escolha uma forma de pagamento:", "Forma de pagamento:",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
            null, paymentOptions, paymentOptions[0]
        );
        String formaPagamento = paymentOptions[choicePagamento];
        String telefone = JOptionPane.showInputDialog("Telefone:");
        String email = JOptionPane.showInputDialog("Email:");
        
        for (int i = 0; i < codigosQuartos.length; i++) { // Associa o nome do ocupante ao quarto escolhido.
            if (codigosQuartos[i].equals(codigoQuarto)) {
                nomesOcupantes[i] = nome;
                break;
            }
        }
        
        // Monta a string do histórico de reservas.
        String reservaInfo = String.format(
                "Check-in - Quarto: %s, Nome: %s, CPF: %s, Data de Entrada: %s, Forma de Pagamento: %s, Telefone: %s, Email: %s",
                codigoQuarto, nome, cpf, dataAtual.format(formatter), formaPagamento, telefone, email
            );
        
        JOptionPane.showMessageDialog(null, "Check-in realizado com sucesso!");
        historicoReservas[historicoIndex[0]++] = reservaInfo; // Armazena a reserva no histórico.
        
        JOptionPane.showMessageDialog(null, "Check-in realizado com sucesso!");
    }

    public static void mostrarHistoricoReservas(String[] historicoReservas, int[] historicoIndex) {
        // Usa StringBuilder para construir a string do histórico.
        StringBuilder sb = new StringBuilder("Histórico de Reservas:\n");
        for (int i = 0; i < historicoIndex[0]; i++) {
            sb.append(historicoReservas[i]).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public static String listarQuartosVagos(String[] codigosQuartos, String[] nomesOcupantes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < codigosQuartos.length; i++) { // Verifica cada quarto.
            if (nomesOcupantes[i] == null) { // Se o quarto está vago.
                sb.append("Quarto ").append(codigosQuartos[i]).append("\n");
            }
        }
        String result = sb.toString().trim();
        if (result.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há quartos vagos");
        } else {
            JOptionPane.showMessageDialog(null, result);
        }
        return result;
    }
    
    public static String listarQuartosOcupados(String[] codigosQuartos, String[] nomesOcupantes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < codigosQuartos.length; i++) { // Verifica cada quarto.
            if (nomesOcupantes[i] != null) { // Se o quarto está ocupado.
                sb.append("Quarto ").append(codigosQuartos[i]).append(" ocupado por ").append(nomesOcupantes[i]).append("\n");
            }
        }
        String result = sb.toString();
        if (result.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há quartos ocupados.");
        } else {
            JOptionPane.showMessageDialog(null, result);
        }
        return result;
    }
    
    public static void realizarCheckOut(String[] codigosQuartos, String[] nomesOcupantes, String[] historicoReservas, int[] historicoIndex) {
        String quartosOcupados = listarQuartosOcupados(codigosQuartos, nomesOcupantes);
        if (quartosOcupados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há quartos ocupados.");
            return;
        }
        
        String[] quartosOcupadosArray = quartosOcupados.split("\n");
        int escolhaQuarto = JOptionPane.showOptionDialog(
            null, "Escolha um quarto para check-out", "Quartos Ocupados",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
            null, quartosOcupadosArray, quartosOcupadosArray[0]
        );
        
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
}
