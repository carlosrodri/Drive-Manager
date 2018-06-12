package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;

import constants.ConstantsUI;
import models.Client;
import persistence.JSONFileManager;
import views.ClientWindow;
import views.FileChooser;

public class Controller implements ActionListener{
	private FileChooser fileChooser;
	private ClientWindow clientWindow;
	private Client client;

	public Controller() {
		clientWindow = new ClientWindow(this);
		fileChooser = new FileChooser(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (MyActions.valueOf(e.getActionCommand())) {
		case ADD_NEW:
			try {
				add();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case CLIENT:
			try {
				clientDown();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			break;
		case SERVER:
			try {
				serverDown();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;
		}
	}

	private void clientDown() throws IOException {
		clientWindow.ocultPop();
		client.send(ConstantsUI.CLIENT);//envia la peticion al servidor para que busque
		client.send(clientWindow.getNameOfUser());//obtiene el nombre del cliente a buscar
		client.send(clientWindow.getNameOfFile()); // obtiene el  nombre del archov a enviar
		client.send(client.getName());//obtiene el nombre del que pide la info
		client.setTimeInit(client.getTime());
	}

	private void serverDown() throws IOException {
		clientWindow.ocultPop();
		client.send(ConstantsUI.SERVER);//envia la peticion al servidor para que busque
		client.send(clientWindow.getNameOfUser());//obtiene el nombre del cliente a buscar
		client.send(clientWindow.getNameOfFile()); // obtiene el  nombre del archov a enviar
		client.send(client.getName());//obtiene el nombre del que pide la info
		client.setTimeInit(client.getTime());
	}

	private void add() throws Exception {
		client = new Client(JOptionPane.showInputDialog("IP"), clientWindow.port(), clientWindow.getNameClient());
		client.send(ConstantsUI.REGISTRY);
		client.send(clientWindow.getInfo());
		client.send(fileChooser.getPathFile());
	}

	public static void refresh() {
		try {
			ClientWindow.setArray(JSONFileManager.readFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
