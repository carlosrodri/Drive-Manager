package models;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class ClientConnections extends Connection{

	public ClientConnections(Socket newConnection) throws IOException {
		super(newConnection);
	}

	@Override
	void executeTask() {
		try {
			String [] string = readResquest().split("#");
			switch (string[0]) {
			case "/message":
				sendMessage(string);
				break;
			}

		} catch (IOException e) {
		}
	}

	private void sendMessage(String[] string) {
		Server.sendMessageALL(string);
	}

	public void sendFile(){
		sendFile(new File("src/datos/1.jpg"));
	}
}
