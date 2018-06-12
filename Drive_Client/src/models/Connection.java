package models;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import constants.ConstantsUI;
import models.entities.User;
import views.ClientWindow;

public abstract class Connection extends MyThread{

	private Socket socket, con;
	private ServerSocket server;
	private DataInputStream input, inputServer;
	private DataOutputStream output, outputServer;


	public Connection(String ip, int port) throws IOException {
		socket = new Socket(ip, port);
		input = new DataInputStream(socket.getInputStream());
		output = new DataOutputStream(socket.getOutputStream());
		start();
	}

	public void asServer(int port, String file, String name) throws IOException {
		while (true) {
			server = new ServerSocket(port);
			con = server.accept();
			System.out.println("aceptadooooo");
			outputServer = new DataOutputStream(con.getOutputStream());
			inputServer = new DataInputStream(con.getInputStream());
			manageRequest(con, file, name);
		}
	}

	private void manageRequest(Socket con, String file, String name) throws IOException {
		switch (inputServer.readUTF()) {
		case ConstantsUI.FILE_TO_USER:
			sendFile(searchFile(file, name));
			//				con.close();
			break;

		}
	}

	private File searchFile(String file, String name) {
		for (User user : ClientWindow.getList()) {
			if(user.getName().equals(name)) {
				return new File(user.getPath()+"/"+file);
			}
		}
		return null;
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

	public void sendFile(File file){
		try {
			int fileSize = (int) file.length();
			output = outputServer;
			output.writeUTF(ConstantsUI.FILES);
			output.writeUTF(file.getName());
			output.writeInt(fileSize);
			FileInputStream filInp = new FileInputStream(file);
			@SuppressWarnings("resource")
			BufferedInputStream bis = new BufferedInputStream(filInp);
			BufferedOutputStream bos = new BufferedOutputStream(output);
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

	public void setPort(int port) throws UnknownHostException, IOException {
		this.socket = new Socket("localhost", port);
		output = new DataOutputStream(socket.getOutputStream());
		input = new DataInputStream(socket.getInputStream());
		output.writeUTF(ConstantsUI.FILE_TO_USER);
	}

	public DataOutputStream getOutputServer() {
		return outputServer;
	}
}