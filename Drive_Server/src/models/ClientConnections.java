package models;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import constants.ConstantsUI;

public class ClientConnections extends Connection{
	private String name;


	public ClientConnections(Socket newConnection) throws IOException {
		super(newConnection);
	}

	@Override
	void executeTask() {
		try {
			switch (readResquest()) {
			case ConstantsUI.REGISTRY:
				Server.addTolist(readResquest(), readResquest());
				sendMessage();
				break;
			case ConstantsUI.SERVER:
				getFileOfServer();
				break;
			}

		} catch (IOException e) {
		}
	}

	private void getFileOfServer() throws IOException {
		Server.search(readResquest(), readResquest());
	}

	private void sendMessage() {
		Server.sendMessageALL();
	}

	public void sendFile(){
		File file = new File("src/datas/file.json");
		sendFile(file);
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
