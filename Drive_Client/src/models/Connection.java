package models;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class Connection extends MyThread{

	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;

	public Connection(String ip, int port) throws IOException {
		this.socket = new Socket(ip, port);
		input = new DataInputStream(socket.getInputStream());
		output = new DataOutputStream(socket.getOutputStream());
		start();
	}

	public void setInput(DataInputStream input) {
		this.input = input;
	}
	
	public Socket getSocket() {
		return socket;
	}

	public DataInputStream getInput() {
		return input;
	}

	public DataOutputStream getOutput() {
		return output;
	}

	public void send(String data) throws IOException{
		output.writeUTF(data);
	}

	public String readResponse() throws IOException{
		return input.readUTF();
	}

	public void close() throws IOException{
		output.close();
		input.close();
		socket.close();
	}
}