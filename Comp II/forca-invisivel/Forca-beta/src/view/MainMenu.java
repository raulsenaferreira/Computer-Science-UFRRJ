package view;

import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenu extends JFrame {

	private JPanel contentPane;
	private JTextField tFNome;
	private static MainMenu frame = new MainMenu();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
	public MainMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JPanel entrada = new JPanel();
		contentPane.add(entrada);
		entrada.setLayout(null);
		
		JLabel jLNome = new JLabel("Digite seu nome:");
		jLNome.setBounds(12, 80, 120, 15);
		entrada.add(jLNome);
		
		tFNome = new JTextField();
		tFNome.setBounds(155, 78, 219, 19);
		entrada.add(tFNome);
		tFNome.setColumns(10);
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(false);
				Forca forca = new Forca(tFNome.getText());
				forca.setVisible(true);
			}
		});
		btnEntrar.setBounds(155, 109, 117, 25);
		entrada.add(btnEntrar);
	}
}
