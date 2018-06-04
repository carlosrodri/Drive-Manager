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
			String option = readResquest();
			switch (option) {
			case ConstantsUI.REGISTRY:
				Server.addTolist(readResquest(), readResquest());
				sendMessage();
				break;
			case ConstantsUI.SERVER:
				getFileOfServer();
				break;
			case ConstantsUI.FILE_USER:
				sendFileToUser();
				break;
			case ConstantsUI.UPDATE_FILE:
				update(readResquest(), readResquest());
				break;
			default:
				this.name = option;
				break;
			}
		} catch (IOException e) {
		}
	}

	private void update(String readResquest, String readResquest2) {
		Server.sendMessageALL();
	}

	private void sendFileToUser() throws IOException {
		saveFile();
		Server.searchUserTosend(readResquest(), getPath());
	}

	private void getFileOfServer() throws IOException {
		Server.search(readResquest(), readResquest(), readResquest());
	}

	private void sendMessage() {
		Server.sendMessageALL();
	}

	public void sendFile(){
		sendFile(new File(ConstantsUI.PATH+".json"));
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
