package models;

import java.awt.HeadlessException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Client extends Connection{

	private String name;

	public Client(String ip, int port, String name) throws IOException{
		super(ip, port);
		this.name = name;
	}

	@Override
	void executeTask() {
		try {
			String[] string = readResponse().split("#");
			switch (string[0]) {
			case "/message":
				showMessage(string);
				break;
			default:
				break;
			}
		} catch (IOException e) {
		}
	}

	private void showMessage(String[] string) {
		if (!string[1].equals(name)) {
			System.out.println(string + "  acacacaca");
//			Controller.showMessage(string);
		}else {
			System.out.println(string + "  yoooo");
//			Controller.showMessageMe(string);
		}
	}

	public void saveFile() {
		try{
			setInput(new DataInputStream(getSocket().getInputStream()));
			String nameFile = getInput().readUTF().toString();
			int tam = getInput().readInt();
			FileOutputStream fos = new FileOutputStream(new File("src/datos/" + nameFile));
			@SuppressWarnings("resource")
			BufferedOutputStream out = new BufferedOutputStream(fos);
			BufferedInputStream in = new BufferedInputStream(getSocket().getInputStream());
			byte[] buffer = new byte[tam];
			for (int i = 0; i < buffer.length; i++) {
				buffer[i] = (byte) in.read();
			}
			out.write(buffer);
			out.flush();
		} catch (IOException e1) {
			System.out.println("Recibir "+ e1.toString());
		}
	}

	public String getName() {
		return name;
	}
	
	public static void main(String[] args) {
		try {
			new Client(JOptionPane.showInputDialog("ip"), Integer.parseInt(JOptionPane.showInputDialog("Port")), JOptionPane.showInputDialog("Name"));
		} catch (HeadlessException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}