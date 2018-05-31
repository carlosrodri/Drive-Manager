package models;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class Connection extends MyThread{

	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;
	
	public Connection(Socket socket) throws IOException {
		this.socket = socket;
		input = new DataInputStream(socket.getInputStream());
		output = new DataOutputStream(socket.getOutputStream());
		start();
	}

	public void send(String data) throws IOException{
		output.writeUTF(data);
	}
	
	public void sendFile(File file){
		try {
			int fileSize = (int) file.length();
			output = new DataOutputStream(socket.getOutputStream());
			output.writeUTF("/file#" + fileSize);
			output.writeUTF(file.getName());
			output.writeInt(fileSize);
			FileInputStream filInp = new FileInputStream(file);
			@SuppressWarnings("resource")
			BufferedInputStream bis = new BufferedInputStream(filInp);
			BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
			byte[] buffer = new byte[fileSize];
			bis.read(buffer);
			for (int i = 0; i < buffer.length; i++) {
				bos.write(buffer[i]);
			}
			bos.flush();
		} catch (IOException e) {
			System.out.println("Error al crear canal de salida en el servidor.");
			e.printStackTrace();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public Socket getSocket() {
		return socket;
	}

	public String readResquest() throws IOException{
		return input.readUTF();
	}
	
	public DataInputStream getInput() {
		return input;
	}

	public DataOutputStream getOutput() {
		return output;
	}
	
	public void close() throws IOException{
		output.close();
		input.close();
		socket.close();
	}
}