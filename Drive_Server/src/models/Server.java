package models;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import constants.ConstantsUI;
import models.entities.User;
import persistence.JSONFileManager;
import views.MainWindow;

public class Server {

	private ServerSocket serverSocket;
	private MainWindow mainWindow;
	public static ArrayList<ClientConnections> clientConnections;
	public static ArrayList<User> userlist;
	
	public Server() throws IOException {
		clientConnections = new ArrayList<>();
		userlist = new ArrayList<>();
		serverSocket = new ServerSocket(2001);
		mainWindow = new MainWindow();
		new Thread(){
			@Override
			public void run() {
				try {
					while (true) {
						System.out.println("Server online...");
						Socket newConnection = serverSocket.accept();
						System.out.println("aceptado");
						clientConnections.add(new ClientConnections(newConnection));
						refresh();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}.start();
	}

	private void refresh() {
		try {
			mainWindow.refreshList(clientConnections);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<ClientConnections> getClientConnections() {
		return clientConnections;
	}
	
	public static void sendMessageALL(){
		for (ClientConnections clientConnections2 : clientConnections) {
			try {
				if (clientConnections2.getSocket().isConnected()) {
					clientConnections2.send(ConstantsUI.FILE);
					clientConnections2.sendFile();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		File file = new File("src/datas/file.json");
		if (file.exists()) {
			System.out.println(true);
			file.delete();
			System.err.println(file.exists() + "  estado de borrado servidor");
		}
	}
	
	public static void main(String[] args) {
		try {
			new Server();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addTolist(String readResquest, String readResquest2) {
		userlist.add(new User(readResquest, new File(readResquest2)));
		JSONFileManager.writeFile(ConstantsUI.PATH, userlist);
		for (User user : userlist) {
			System.out.println(user.toString() + " cienteeeee");
		}
	}
}