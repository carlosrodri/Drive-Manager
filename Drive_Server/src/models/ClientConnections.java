package models;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import constants.ConstantsUI;

public class ClientConnections extends Connection{

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
			}

		} catch (IOException e) {
		}
	}

	private void sendMessage() {
		Server.sendMessageALL();
	}

	public void sendFile(){
		File file = new File("src/datas/file.json");
		sendFile(file);
	}
}
