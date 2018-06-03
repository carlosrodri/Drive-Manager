package models;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class Connection extends MyThread{

	private Socket socket;
	private ServerSocket serverSocket;
	private DataInputStream input, inputServer;
	private DataOutputStream output, outputServer;

	public Connection(String ip, int port) throws IOException {
		this.socket = new Socket(ip, port);
		input = new DataInputStream(socket.getInputStream());
		output = new DataOutputStream(socket.getOutputStream());
		start();
	}

	public void initAsServer() throws IOException {
		//ahora falta la conexion con el cliente directo
		serverSocket = new ServerSocket(2002);
		Socket c = serverSocket.accept();
		inputServer = new DataInputStream(c.getInputStream());
		outputServer = new DataOutputStream(c.getOutputStream());
		
//		outputServer.writeUTF(str);
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

	public int readResponseInt() throws IOException {
		return input.readInt();
	}
	
	public void close() throws IOException{
		output.close();
		input.close();
		socket.close();
	}
}