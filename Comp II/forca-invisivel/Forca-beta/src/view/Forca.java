package view;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Colecao;
import model.Dicionario;
import model.Som;

public class Forca extends JFrame {

	private JPanel painelForca;

	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		Dicionario dicionario = new Dicionario();
		Colecao objetoSorteado = dicionario.sorteio();
		String respostaSorteada = objetoSorteado.getResposta();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int contador = respostaSorteada.length();
		int limite = 8;
		boolean ok = false;
		char caracter;
		
		//pergunta
		System.out.println(objetoSorteado.getPergunta());
		Som.tocaSom(objetoSorteado.getSomPergunta());

		char[] aux = new char[respostaSorteada.length() * 2];
		
		for (int i = 0; i < respostaSorteada.length()*2; i++)
		{	
			if(i%2 == 0)
				aux[i] = '_';
			else
				aux[i] = ' ';
		}
		
		while(contador != 0 && limite != 0)
		{
			System.out.println(aux);
			System.out.print("Letra: ");
			caracter = br.readLine().toUpperCase().charAt(0);
			
			for (int i = 0; i < respostaSorteada.length(); i++) {
				if((caracter == respostaSorteada.charAt(i)) || 
				   (caracter == 'A' && (respostaSorteada.charAt(i) == 'Á' || respostaSorteada.charAt(i) == 'Â' || respostaSorteada.charAt(i) == 'Ã')) ||
				   (caracter == 'E' && (respostaSorteada.charAt(i) == 'É' || respostaSorteada.charAt(i) == 'Ê')) ||
				   (caracter == 'I' && (respostaSorteada.charAt(i) == 'Í')) ||
				   (caracter == 'O' && (respostaSorteada.charAt(i) == 'Ó' || respostaSorteada.charAt(i) == 'Ô') || respostaSorteada.charAt(i) == 'Õ') ||
				   (caracter == 'U' && (respostaSorteada.charAt(i) == 'Ú')) )
				{
					aux[2*i] = respostaSorteada.charAt(i);
					ok = true;
					contador --;
				}
			}
			
			if(ok == false)
				limite--;
			
			ok = false;
		}
		
		if(limite == 0)
		{
			Som.tocaSom("sons/fimDeJogoErro");
			Som.tocaSom("respostas/mensagem");
		}
		else
		{
			System.out.println(respostaSorteada);
			Som.tocaSom(objetoSorteado.getSomResposta());
			Som.tocaSom("sons/parabens");
			Som.tocaSom("respostas/mensagem");
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Forca frame = new Forca();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Forca(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		painelForca = new JPanel();
		painelForca.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(painelForca);
		painelForca.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 24, 426, 264);
		painelForca.add(panel);
		panel.setLayout(null);
		
		JLabel jLImagemForca = new JLabel("");
		jLImagemForca.setBounds(12, 24, 101, 94);
		jLImagemForca.setIcon(new ImageIcon(Forca.class.getResource("/model/imagens/Forca__01.gif")));
		panel.add(jLImagemForca);
		
		JLabel jLPergunta = new JLabel("Pergunta");
		jLPergunta.setBounds(178, 0, 70, 15);
		panel.add(jLPergunta);
		
		JLabel jLNomeDoParticipante = new JLabel("Nome do Participante");
		jLNomeDoParticipante.setBounds(236, 27, 178, 15);
		panel.add(jLNomeDoParticipante);
		
		JLabel jLResposta = new JLabel("resposta");
		jLResposta.setBounds(156, 107, 70, 15);
		panel.add(jLResposta);
	}
	
	//futuro construtor com parâmetros passados por default
	public Forca(String nomeDoParticipante) {
	}
}
