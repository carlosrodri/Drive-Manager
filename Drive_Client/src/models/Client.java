package models;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import constants.ConstantsUI;
import controllers.Controller;
import models.entities.User;
import views.ClientWindow;

public class Client extends Connection{

	private String name;

	public Client(String ip, int port, String name) throws IOException{
		super(ip, port);
		this.name = name;
		send(name);
	}

	@Override
	void executeTask() {
		try {
			switch (readResponse()) {
			case ConstantsUI.FILE:
				readFile();
				break;
			case ConstantsUI.FILES:
				readFiles();
				break;
			case ConstantsUI.FILE_DOWN:
				sendFile(new File(readResponse()));
				break;
			case ConstantsUI.OBTAIN_FILE:
				sendFileToServer(readResponse(), readResponse());
				break;
			}
			
		} catch (IOException e) {
		}
	}

	private void readFiles() {
		try {
			saveFiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Controller.refresh();
	}

	private void sendFileToServer(String fileName, String adressee) throws IOException {
		File f;
		try {
			f = new File(findByName(readResponse())+fileName);
			if(f.exists()) {
				System.out.println(adressee+ "             destinatario y existe el archivo en remoto");
				send(ConstantsUI.FILE_USER);
				sendFileUser(f);
				send(adressee);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String findByName(String name) throws Exception {
		for (User user : ClientWindow.getList()) {
			if(user.getName().equals(name)) {
				System.out.println("encontro el directorio  "    + user.getPath());
				return user.getPath()+"/";
			}
		}
		throw new Exception("element not found");
	}

	public void sendFileUser(File file){
		try {
			int fileSize = (int) file.length();
			DataOutputStream output = new DataOutputStream(getOutput());
//			output.writeUTF(ConstantsUI.FILE_USER);
			output.writeUTF(file.getName());
			output.writeInt(fileSize);
			FileInputStream filInp = new FileInputStream(file);
			@SuppressWarnings("resource")
			BufferedInputStream bis = new BufferedInputStream(filInp);
			BufferedOutputStream bos = new BufferedOutputStream(getOutput());
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
	
	public void sendFile(File file){
		try {
			int fileSize = (int) file.length();
			DataOutputStream output = new DataOutputStream(getOutput());
			output.writeUTF(ConstantsUI.FILE);
			output.writeUTF(file.getName());
			output.writeInt(fileSize);
			FileInputStream filInp = new FileInputStream(file);
			@SuppressWarnings("resource")
			BufferedInputStream bis = new BufferedInputStream(filInp);
			BufferedOutputStream bos = new BufferedOutputStream(getOutput());
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
	
	private void readFile() {
		try {
			saveFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Controller.refresh();
	}

	public void saveFile() throws IOException {
		File fi = new File("src/datas/");
		if(!fi.exists()) {
			System.out.println("no existe");
			fi.mkdir();
		}
		try{
			setInput(new DataInputStream(getSocket().getInputStream()));
			String nameFile = getInput().readUTF();
			int tam = getInput().readInt();
			File f = new File("src/datas/" + nameFile);
			FileOutputStream fos = new FileOutputStream(f);
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

	public void saveFiles() throws IOException {
		File fi = null;
		try {
			fi = new File(findByName(getName()));
			if(!fi.exists()) {
				System.out.println("no existe");
				fi.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try{
			setInput(new DataInputStream(getSocket().getInputStream()));
			String nameFile = getInput().readUTF();
			int tam = getInput().readInt();
			File f = new File(fi+"/"+nameFile);
			System.out.println(f+"     carpeta en donde se gusrada al final");
			FileOutputStream fos = new FileOutputStream(f);
			@SuppressWarnings("resource")
			BufferedOutputStream out = new BufferedOutputStream(fos);
			BufferedInputStream in = new BufferedInputStream(getSocket().getInputStream());
			byte[] buffer = new byte[tam];
			for (int i = 0; i < buffer.length; i++) {
				buffer[i] = (byte) in.read();
			}
			out.write(buffer);
			out.flush();
			send(ConstantsUI.UPDATE_FILE);
			send(nameFile);
			send(getName());
		} catch (IOException e1) {
			System.out.println("Recibir "+ e1.toString());
		}
	}
	
	public String getName() {
		return name;
	}
}