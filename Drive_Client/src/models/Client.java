package models;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import constants.ConstantsUI;
import controllers.Controller;

public class Client extends Connection{

	private String name;

	public Client(String ip, int port, String name) throws IOException{
		super(ip, port);
		this.name = name;
	}

	@Override
	void executeTask() {
		try {
			switch (readResponse()) {
			case ConstantsUI.FOLDER_CREATED:
				showFolders();
				break;
			case ConstantsUI.NEW_FOLDER:
//				refresh(readResponse(), readResponse());
//				readFile();
				break;
			case ConstantsUI.FILE:
				readFile();
				break;
			}
			
		} catch (IOException e) {
		}
	}

	private void readFile() {
		System.out.println("leeeee jajaja");
		saveFile();
		Controller.refresh();
	}

	private void showFolders() {
//		if (!string[1].equals(name)) {
//			System.out.println(string + "  acacacaca");
////			Controller.showMessage(string);
//		}else {
//			System.out.println(string + "  yoooo");
////			Controller.showMessageMe(string);
//		}
	}

	public void saveFile() {
		try{
			setInput(new DataInputStream(getSocket().getInputStream()));
			getInput().readUTF().toString();
			String nameFile = readResponse();
			File file = new File("src/datas/" + nameFile);
			if(file.exists()) {
				file.delete();
				System.out.println("disponible el archivo? " + file.exists());
			}
			int tam = getInput().readInt();
			System.out.println(file + "    ruta del archivo");
			FileOutputStream fos = new FileOutputStream(new File("src/datas/" + nameFile));
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
}