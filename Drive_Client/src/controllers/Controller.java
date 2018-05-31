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
		}
	}

	private void add() throws Exception {
		Client client = new Client(JOptionPane.showInputDialog("IP"), clientWindow.port(), clientWindow.getNameClient());
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
