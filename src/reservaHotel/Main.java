package reservaHotel;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {

public static void main(String[] args) {
    // Códigos de todos os quartos do hotel.
    String[] codigosQuartos = {"S101", "S102", "S103", "S104", "S201", "S202", "S203", "S204", "S301", "S302", "S303", "S304", "S401", "S402", "S403", "S404"};

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
    String[] options = {"Reservar Quarto", "Realizar Check-out", "Listar Quartos Vagos", "Listar Quartos Ocupados", "Mostrar Histórico de Reservas", "Sair"};

    while (true) {
        // Cria um menu com botões para as opções.
        int choice = JOptionPane.showOptionDialog(null, "Escolha uma opção", "Sistema de Reservas de Granville", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        // Ações baseadas na escolha do usuário.
        switch (choice) {
            case 0:
                reservarQuarto(codigosQuartos, nomesOcupantes, historicoReservas, historicoIndex); // Chama a função para reservar um quarto.
                break;
            case 1:
                realizarCheckOut(codigosQuartos, nomesOcupantes, historicoReservas, historicoIndex); // Chama a função para realizar check-out.
                break;
            case 2:
                listarQuartosVagos(codigosQuartos, nomesOcupantes); // Chama a função para listar quartos vagos.
                break;
            case 3:
                listarQuartosOcupados(codigosQuartos, nomesOcupantes); // Chama a função para listar quartos ocupados.
                break;
            case 4:
                mostrarHistoricoReservas(historicoReservas, historicoIndex); // Chama a função para mostrar o histórico de reservas.
                break;
            case 5:
                System.exit(0); // Encerra o programa.
                break;
            default:
                JOptionPane.showMessageDialog(null, "Por favor, selecione uma opção válida."); // Mensagem para opção inválida.
        }
    }
}

public static void reservarQuarto(String[] codigosQuartos, String[] nomesOcupantes, String[] historicoReservas, int[] historicoIndex) {
    String quartosLivres = listarQuartosVagos(codigosQuartos, nomesOcupantes); // Chama a função listarQuartosVagos e armazena o resultado.

    if (quartosLivres.isEmpty()) { // Verifica se há quartos vagos.
        JOptionPane.showMessageDialog(null, "Não há quartos vagos.");
        return; // Retorna ao menu se não houver quartos disponíveis.
    }

    // Busca a data atual.
    LocalDate dataAtual = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String[] quartosVagosArray = quartosLivres.split("\n"); // Separa os quartos vagos em um array.

    // Cria um menu para o usuário escolher o quarto.
    int escolhaQuarto = JOptionPane.showOptionDialog(null, "Escolha um quarto", "Quartos Disponíveis", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, quartosVagosArray, quartosVagosArray[0]);

    if (escolhaQuarto == JOptionPane.CLOSED_OPTION) { // Se o usuário fechar o menu, retorna ao menu principal.
        return;
    }

    String codigoQuarto = quartosVagosArray[escolhaQuarto].replace("Quarto ", "").trim(); // Extrai o código do quarto escolhido.
    String dataEntrada = JOptionPane.showInputDialog("Data: (Formato: DD-MM-AAAA)"); // Pede a data de check-in ao usuário.
    LocalDate data = LocalDate.parse(dataEntrada, formatter); // Converte a string de data para LocalDate.

    // Verifica se a data escolhida não é anterior à data atual.
    if (dataAtual.isBefore(data)) {
    	
        // Dados do hóspede.
        String nome = JOptionPane.showInputDialog("Nome:");
        String cpf = JOptionPane.showInputDialog("CPF:");
        String[] paymentOptions = {"Crédito", "Débito"};

        int choicePagamento = JOptionPane.showOptionDialog(null, "Escolha uma forma de pagamento:", "Forma de pagamento:", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, paymentOptions, paymentOptions[0]);
        String formaPagamento = paymentOptions[choicePagamento]; // Armazena a forma de pagamento escolhida.
        String telefone = JOptionPane.showInputDialog("Telefone:");
        String email = JOptionPane.showInputDialog("Email:");

        for (int i = 0; i < codigosQuartos.length; i++) { // Associa o nome do ocupante ao quarto escolhido.
            if (codigosQuartos[i].equals(codigoQuarto)) {
                nomesOcupantes[i] = nome; // Armazena o nome do hóspede no índice correspondente ao quarto.
                break;
            }
        }

        // Monta a string do histórico de reservas.
        String reservaInfo = String.format("Check-in - Quarto: %s, Nome: %s, CPF: %s, Data de Entrada: %s, Forma de Pagamento: %s, Telefone: %s, Email: %s",
                codigoQuarto, nome, cpf, data.format(formatter), formaPagamento, telefone, email);
        
        // Armazena a reserva no histórico.
        historicoReservas[historicoIndex[0]++] = reservaInfo; 
        JOptionPane.showMessageDialog(null, "Check-in realizado com sucesso!"); // Mensagem de sucesso.
    } else {
        JOptionPane.showMessageDialog(null, "Não é possível fazer o check-in nessa data."); // Mensagem de erro se a data for inválida.
    }
}

public static void mostrarHistoricoReservas(String[] historicoReservas, int[] historicoIndex) {
    // A partir do uso do StringBuilder.
    // Manipula as strings para que todo histórico esteja em uma única string.
    StringBuilder sb = new StringBuilder("Histórico de Reservas:\n");
    
    // Adiciona todos os hóspedes ao histórico com todas as informações.
    for (int i = 0; i < historicoIndex[0]; i++) {
        sb.append(historicoReservas[i]).append("\n"); // Adiciona cada reserva ao StringBuilder.
    }
    
    // Mostra todas as informações por meio da conversão do método StringBuilder para String.
    JOptionPane.showMessageDialog(null, sb.toString());
}

public static String listarQuartosVagos(String[] codigosQuartos, String[] nomesOcupantes) {
    // Novamente o uso do método StringBuilder.
    StringBuilder sb = new StringBuilder();
    
    // Adiciona todos os quartos e compara se nomesOcupantes de cada quarto possui alguma informação.
    for (int i = 0; i < codigosQuartos.length; i++) {
        if (nomesOcupantes[i] == null) { // Se o quarto está vago.
            sb.append("Quarto ").append(codigosQuartos[i]).append("\n"); // Adiciona o código do quarto ao StringBuilder.
        }
    }
    
    // Converte StringBuilder para String.
    String result = sb.toString().trim();
    
    // Verifica se result está vazio.
    if (result.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Não há quartos vagos"); // Mensagem se não houver quartos vagos.
    } else {
        JOptionPane.showMessageDialog(null, result); // Mostra a string de quartos vagos.
    }
    
    return result; // Retorna quartos vagos.
}

public static String listarQuartosOcupados(String[] codigosQuartos, String[] nomesOcupantes) {
    StringBuilder sb = new StringBuilder(); // Cria um StringBuilder para armazenar os quartos ocupados.
    
    // Percorre todos os quartos para verificar se estão ocupados.
    for (int i = 0; i < codigosQuartos.length; i++) {
        if (nomesOcupantes[i] != null) { // Verifica se o quarto está ocupado.
            // Adiciona o código do quarto e o nome do ocupante ao StringBuilder.
            sb.append("Quarto ").append(codigosQuartos[i]).append(" ocupado por ").append(nomesOcupantes[i]).append("\n");
        }
    }
    
    // Converte StringBuilder para String.
    String result = sb.toString();
    
    // Verifica se a lista de quartos ocupados está vazia.
    if (result.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Não há quartos ocupados."); // Mensagem se não houver quartos ocupados.
    } else {
        JOptionPane.showMessageDialog(null, result); // Mostra os quartos ocupados.
    }
    
    return result; // Retorna a lista de quartos ocupados.
}

public static void realizarCheckOut(String[] codigosQuartos, String[] nomesOcupantes, String[] historicoReservas, int[] historicoIndex) {
    String quartosOcupados = listarQuartosOcupados(codigosQuartos, nomesOcupantes); // Chama a função listarQuartosOcupados.
    LocalDate dataAtual = LocalDate.now();
    if (quartosOcupados.isEmpty()) { // Verifica se há quartos ocupados.
        JOptionPane.showMessageDialog(null, "Não há quartos ocupados."); // Mensagem se não houver quartos ocupados.
        return; // Retorna se não houver quartos ocupados.
    }

    // Cria um array com os quartos ocupados.
    String[] quartosOcupadosArray = quartosOcupados.split("\n");
    
    // Cria um menu para o usuário escolher o quarto que deseja fazer check-out.
    int escolhaQuarto = JOptionPane.showOptionDialog(null, "Escolha um quarto para check-out", "Quartos Ocupados", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, quartosOcupadosArray, quartosOcupadosArray[0]);

    if (escolhaQuarto == JOptionPane.CLOSED_OPTION) { // Verifica se o usuário fechou o menu.
        return; // Retorna ao menu principal.
    }

    // Extrai o código do quarto escolhido.
    String codigoQuarto = quartosOcupadosArray[escolhaQuarto].replace("Quarto ", "").split(" ")[0];
    
    for (int i = 0; i < codigosQuartos.length; i++) { // Verifica se o código do quarto corresponde ao quarto ocupado.
        if (codigosQuartos[i].equals(codigoQuarto)) {
            if (nomesOcupantes[i] != null) { // Verifica se o quarto está ocupado.
                // Adiciona o check-out ao histórico de reservas.
                historicoReservas[historicoIndex[0]++] = "Check-out - Quarto: " + codigoQuarto + ", Nome: " + nomesOcupantes[i] + " Data de Saida: " + dataAtual;
                nomesOcupantes[i] = null; // Define nomesOcupantes do índice do quarto como nulo.
                JOptionPane.showMessageDialog(null, "Check-out realizado com sucesso!"); // Mensagem de sucesso.
                return;
            } else {
                JOptionPane.showMessageDialog(null, "Quarto " + codigoQuarto + " já está vazio ou inexistente."); // Mensagem de erro se o quarto já estiver vazio.
                return;
            }
        }
    }
}
}