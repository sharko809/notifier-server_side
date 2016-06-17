package main;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import server.Server;

/**
 * Стартовый класс программы
 */
public class Main {
	public static JLabel l1;
	public static JFrame frame = new JFrame("Server");
	
	public static void setL1(String s) {
		l1.setText(s);
	}

	static Server s = new Server();
	static Thread th = new Thread(s);	

	
	public static void main(String[] args) throws InterruptedException {
		l1 = new JLabel("");
		l1.setText("Waiting for action...");
		createGUI();
		th.start();
		th.join();
	}

	/**
	 * Метод для создания интерфейса
	 */
	
	private static void createGUI() {
		frame.setLayout(new FlowLayout());
		frame.add(l1);
		frame.pack();
		frame.setSize(170, 55);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}
}
