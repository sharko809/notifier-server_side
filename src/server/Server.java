package server;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import main.Main;
import media.Media;

/**
 * ����� ��� �������
 */
public class Server implements Runnable {
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket serverSocket;
	private Socket socket;
	private boolean isRunning = true;

	/**
	 * ���� ����� ��������� ������
	 * 
	 * ��������� ���������� � ������. ��������� �������� �����
	 * � ���������� ��� � ��������� �����.
	 */

	public synchronized void ServerStarter() {
		try {
			serverSocket = new ServerSocket(1234, 100);
			while (isRunning) {
				// ������ ���� � ������, ��� �� ������ � ������� ��� ������
				// �����.
				// ���� ���������� �� IllegalThreadState
				Media m = new Media();
				Thread mediaThread = new Thread(m);

				socket = serverSocket.accept();
				Main.setL1("Connected: " + socket.getInetAddress());
				output = new ObjectOutputStream(socket.getOutputStream());
				output.flush();
				input = new ObjectInputStream(socket.getInputStream());
				Thread.sleep(5000);//��� �� ������ ��������.
				try{
					System.out.println(input.available());
					System.out.println("connected_3");
					Object w = input.readObject();
					output.writeObject(w);
					mediaThread.start();
					showMessage(w);
					mediaThread.interrupt();
					System.out.println(w);
					System.out.println("connected_3");
					output.flush();
				}catch(EOFException e){
					e.printStackTrace();
					input.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * ����� ��� �������� ������� � ����������.
	 */
	
	private void close() {
		try {
			output.close();
			input.close();
			socket.close();
			isRunning = false;
			Main.setL1("Connected :"+null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showMessage(Object w) throws InterruptedException {
		JOptionPane.showMessageDialog(null, w);
										 th.interrupt();

	}

	@Override
	public void run() {
		ServerStarter();
	}

}