package reservaHotel;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
    	
    	//Codigos de todos os quartos do hotel.
    	String[] codigosQuartos = {"S101","S102","S103","S104","S201","S202","S203","S204","S301","S302","S303","S304","S401","S402","S403","S404"};
    	//Nomes de todos os ocupantes possiveis do hotel com base nos codigos dos quartos.
    	String[] nomesOcupantes = new String[codigosQuartos.length]; 
        String[] historicoReservas = new String[100];  // Definido um tamanho máximo para o histórico
        int[] historicoIndex = {0};  
        //Abre o menu por meio da chamada da função.
        
    	menu(codigosQuartos,nomesOcupantes,historicoReservas,historicoIndex);// A função menu.
        
    }
    
    public static void menu(String[] codigosQuartos,String[] nomesOcupantes,String[] historicoReservas,int[] historicoIndex) {
    	//Opções do menu.
    	String[] options = {"Reservar Quarto", "Realizar Check-out", "Listar Quartos Vagos", "Listar Quartos Ocupados", "Mostrar Histórico de Reservas", "Sair"};
    	//O uso do while foi necessario para voltar ao menu apos terminar uma ação dentro do programa.
        while (true) {
            //A criação de um menu com botões com endereços personalizados.
        	int choice = JOptionPane.showOptionDialog(null, "Escolha uma opção", "Sistema de Reservas de Granville",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            //As opções do menu.
            switch (choice) {
            	//CheckIn.
                case 0:             
                   reservarQuarto(codigosQuartos, nomesOcupantes, historicoReservas, historicoIndex);
                    break;
                //CheckOut.    
                case 1:
                   realizarCheckOut(codigosQuartos, nomesOcupantes, historicoReservas, historicoIndex);
                    break;
                //Listar os quartos.    
                case 2:
                   listarQuartosVagos(codigosQuartos, nomesOcupantes);
                    break;
                //Listar os quartos ocupados.    
                case 3:
                    listarQuartosOcupados(codigosQuartos, nomesOcupantes);
                    break;
                //Mostrar o historico das reservas do hotel.    
                case 4:
                   mostrarHistoricoReservas(historicoReservas, historicoIndex);
                    break;
                //Para sair do programa.    
                case 5:
                    System.exit(0);
                    break;
                //Caso o usuario clique em um botão invalido.    
                default:
                    JOptionPane.showMessageDialog(null, "Por favor, selecione uma opção válida.");}}}

    public static void reservarQuarto(String[] codigosQuartos, String[] nomesOcupantes, String[] historicoReservas, int[] historicoIndex) {
    
		String quartosLivres = listarQuartosVagos(codigosQuartos, nomesOcupantes); // Chama uma função com retorno e iguala a string quartosLivres.
		//Busca a data que o computador está configurado.
		LocalDate dataAtual = LocalDate.now();
		// Chama a funcao quartosLivres que e separada por meio do \n e iguala a quartosVagosArray.
		String[] quartosVagosArray = quartosLivres.split("\n");
		// formata a data local do computador para a desejada.
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		//Cria um menu para o usuario escolher o quarto que deseja por meio de botoes.
		int escolhaQuarto = JOptionPane.showOptionDialog(null, "Escolha um quarto", "Quartos Disponíveis",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, quartosVagosArray, quartosVagosArray[0]);
		
		//Caso o usuario deseje fechar o programa, vai retornar ao menu.
			if (escolhaQuarto == JOptionPane.CLOSED_OPTION) {
				return;
			}
			//A string codigoQuarto inicializa os codigos dos quartos.
			String codigoQuarto = quartosVagosArray[escolhaQuarto].trim();
			
			//Pede a data de checkIn ao usuario.
			String dataEntrada = JOptionPane.showInputDialog("Data: (Formato: DD-MM-AAAA)");
			//Armazena a data de entrada a variavel data junto com a formatação.
			LocalDate data = LocalDate.parse(dataEntrada, formatter);
			int quantidadeDias = Integer.parseInt(JOptionPane.showInputDialog("Quantidade de Dias:"));
			//Ocorre a adicão dos dias a partir da data escolhida pelo usuario.
			LocalDate dataSaida = data.plusDays(quantidadeDias);		
			//Verifica se a data atual não e anterior a data que o usuario escolheu.
				if (!data.isBefore(dataAtual)) {
					//Os dados do hospede
					String nome = JOptionPane.showInputDialog("Nome:");
					String cpf = JOptionPane.showInputDialog("CPF:");
					// Cria opcoes de credito ou debito.
					String[] paymentOptions = {"Crédito", "Débito"};
					int choicePagamento = JOptionPane.showOptionDialog(null, "Escolha uma forma de pagamento:", "Forma de pagamento:", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, paymentOptions, paymentOptions[0]);
					// Substituindo o switch case para o uso do operador ternario
					// Exemplo: variável = condição ? valorSeVerdadeiro : valorSeFalso;
					String formaPagamento = choicePagamento == 0 ? "Crédito" : "Débito";          
					String telefone = JOptionPane.showInputDialog("Telefone:");
					String email = JOptionPane.showInputDialog("Email:");
					
					//Verifica todos os quartos por meio do indice se o codigoQuarto[i] e igual ao codigoQuarto escolhido pelo usuario
					//Assim iguala o nomesOcupantes[i] a nome.
					//Adicionando o nome do hospede ao quarto.
					for (int i = 0; i < codigosQuartos.length; i++) {
							if (codigosQuartos[i].equals(codigoQuarto)) {
								nomesOcupantes[i] = nome;
								break;
			                }
			            }
							
					// Monta uma string que mostra todos os dados do cliente.
					// O uso do historicoIndex e para não usar o ARRAYLIST. 
					// Assim criando um indice para cada hospede.
					historicoReservas[historicoIndex[0]++] = "Check-in - " + codigoQuarto + ", Nome: " + nome + ", CPF: " + cpf + ", Data de Entrada: " + dataEntrada + ", Data de Saida: " + dataSaida + ", Dias: " + quantidadeDias +", Forma de Pagamento: " + formaPagamento + ", Telefone: " + telefone + ", Email: " + email;
					//Conclui o checkIn
					JOptionPane.showMessageDialog(null, "Check-in realizado com sucesso!");
			
				} else {
					// Caso a data escolhida pelo cliente seja anterior a atual.
					JOptionPane.showMessageDialog(null, "Não é possível fazer o check-in nessa data.");
				}
}
	
    public static void mostrarHistoricoReservas(String[] historicoReservas, int[] historicoIndex) {
        //	A partir do uso do StringBuilder.
    	//	Manipula as string para que todo historico esteja em uma unica string.
    	//  O nome "Historico de Reservas:\n" é o cabeçalho 
    	StringBuilder sb = new StringBuilder("Histórico de Reservas:\n"); 
    	// Adiciona todos os hospedes ao historico com todas as informações.
    	// O uso do metodo .append e para que cada cliente esteja com suas informacoes em uma linha determinada.
        for (int i = 0; i < historicoIndex[0]; i++) {
            sb.append(historicoReservas[i]).append("\n");
        }
        //Aqui mostra todas as informações por meio da conversão do metodo StringBuilder para String para mostrar na tela por meio do JoptionPane.
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public static String listarQuartosVagos(String[] codigosQuartos, String[] nomesOcupantes) {
    	//Novamente o uso do metodo StringBuilder.
    	//Dessa vez sem passar nenhum cabeçalho.
    	StringBuilder sb = new StringBuilder();
    	// Adiciona todos os quartos e compara se nomesOcupantes de cada quarto possue alguma informação
    	// Se não o quarto e vago.
    	// O uso do metodo .append e para que cada quarto esteja em uma linha determinada.
    	for (int i = 0; i < codigosQuartos.length; i++) {
    		if (nomesOcupantes[i] == null) {
    			//Adiciona o código do quarto correspondente ao indice correto do tamanho dos codigosQuartos.
    			sb.append("Quarto ").append(codigosQuartos[i]).append("\n");
    		}
    	}
    	//Aqui ocorre a conversao de StringBuilder para String.
    	String result = sb.toString().trim();
    	//Verifica se result e true ou false.
    	if(result.isEmpty())
    		JOptionPane.showMessageDialog(null,"Não há quartos vagos");
    	//Mostra a string de quartos vagos.
    	JOptionPane.showMessageDialog(null, result);
    	//Retorna quartos vagos.
    	return result;
    }
    
    public static String listarQuartosOcupados(String[] codigosQuartos, String[] nomesOcupantes) {
        StringBuilder sb = new StringBuilder();
        
        // Percorre todos os quartos para verificar se estão ocupados
        for (int i = 0; i < codigosQuartos.length; i++) {
            if (nomesOcupantes[i] != null) { // Verifica se o quarto está ocupado
                // Adiciona o código do quarto e o nome do ocupante
                sb.append("Quarto ").append(codigosQuartos[i]).append(" ocupado por ").append(nomesOcupantes[i]).append("\n");
            }
        }
        
        // Converte StringBuilder para String
        String result = sb.toString();
        
        // Verifica se a lista de quartos ocupados está vazia
        if (result.isEmpty()) {
            //JOptionPane.showMessageDialog(null, "Não há quartos ocupados.");
       } else {
            //Mostra os quartos ocupados
            JOptionPane.showMessageDialog(null, result);
        }
        
        // Retorna a lista de quartos ocupados
        return result;     
    }
    
    public static void realizarCheckOut(String[] codigosQuartos, String[] nomesOcupantes, String[] historicoReservas, int[] historicoIndex) {
        //Iguala a string quartosOcupados a listarQuartosOcupados, assim chamando a funcao.
    	String quartosOcupados = listarQuartosOcupados(codigosQuartos, nomesOcupantes);
        //Verifica se quartosOcupados possue algum true ou false.
    	//Se possue true, existem quartos ocupados.
    	if (quartosOcupados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há quartos ocupados.");
            return;
        }
    	//Cria uma String quartosOcupadosArray e iguala a quartosOcupados separando as informacoes de quartosOcupados por linha.
        String[] quartosOcupadosArray = quartosOcupados.split("\n");
        //Cria um menu para o usuario escolher o quarto que deseja por meio de botoes.
        int escolhaQuarto = JOptionPane.showOptionDialog(null, "Escolha um quarto para check-out", "Quartos Ocupados",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, quartosOcupadosArray, quartosOcupadosArray[0]);
        //Verifica se o usuario fechou o programa assim retornando ao menu.
        if (escolhaQuarto == JOptionPane.CLOSED_OPTION) {
            return;
        }
        //Iguala codigoQuarto com quartosOcupadosArray com o quarto escolhido.
        //Com o metodo .replace retira a palavra "Quarto " da String e separa por espaço, selecionando 
        //pelo o primeiro codigo do quarto pela ordem do index. 
        String codigoQuarto = quartosOcupadosArray[escolhaQuarto].replace("Quarto ", "").split(" ")[0];
        
        for (int i = 0; i < codigosQuartos.length; i++) {
        	//Verifica se codigoQuartos[i] é igual ao codigoQuarto escolhido pelo o usuario.
            if (codigosQuartos[i].equals(codigoQuarto)) {
            	//Se sim
            	//Compara se nesse codigoQuarto possue o valor de nomesOcupantes diferente de nulo.
                if (nomesOcupantes[i] != null) {
                	//Se sim
                	//Imprime como resultado em historicoReservas o checkOut do quarto.
                    historicoReservas[historicoIndex[0]++] = "Check-out - Quarto: " + codigoQuarto + ", Nome: " + nomesOcupantes[i];
                    nomesOcupantes[i] = null;//Define nomesOcupantes do indice do quarto como nulo.
                    JOptionPane.showMessageDialog(null, "Check-out realizado com sucesso!");
                    return;
                } else {
                	//Passa erro de quarto que o quarto ja esta vazio.
                    JOptionPane.showMessageDialog(null, "Quarto " + codigoQuarto + " já está vazio ou inexistente.");
                    return;
                }
            }
        }
    }
}
	
