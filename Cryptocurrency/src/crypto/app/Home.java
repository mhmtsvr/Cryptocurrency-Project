package crypto.app;

import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Home {
	public static JTextPane textPane = new JTextPane();
	static boolean status = true;

	public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home window = new Home();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Home() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("BlockChain");
		frame.setSize(700,600);
		frame.getContentPane().setLayout(null);
	
		
		JLabel label = new JLabel("Welcome to BlockChain App Interface");
	
		label.setBounds(226, 15, 282, 16);
		frame.getContentPane().add(label);
		
		
	
		
		JScrollPane scrollPane = new JScrollPane(textPane);
		textPane.setEditable(false);
		
		//Button
		JButton button = new JButton("Start Transaction");
		button.setBounds(279,54,130,50);
		frame.getContentPane().add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(status == true){
					JOptionPane.showMessageDialog(frame,"The transaction is being started.\nIt will take a moment...","BlockChain - Info",JOptionPane.INFORMATION_MESSAGE); 
					
					App.run();
						
					status = false;
		
					
				}
				else{
					 JOptionPane.showMessageDialog(frame,"The transaction has already been run! Check the result.","BlockChain - Security",JOptionPane.WARNING_MESSAGE); 
				}
			}
		});
		
		
		
		scrollPane.setBounds(50,120,600,400);
		frame.getContentPane().add(scrollPane);
		
		

		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
	}
}
