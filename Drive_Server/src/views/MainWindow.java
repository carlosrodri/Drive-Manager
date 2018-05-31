package views;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import constants.ConstantsUI;
import models.ClientConnections;

public class MainWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	private DefaultListModel<String> model;
	private JList<String> list;
	
	public MainWindow() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(ConstantsUI.DIMENSION);
		
		model = new DefaultListModel<>();
		
		list = new JList<>(model);
		list.setBorder(new TitledBorder(ConstantsUI.LIST_TITLE));
		add(new JScrollPane(list), BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	public void refreshList(ArrayList<ClientConnections> list) throws IOException {
		System.out.println(list.get(0).toString());
		model.clear();
		for (ClientConnections string : list) {
			model.addElement("Client:  " + string.getSocket().getInetAddress().toString());
		}
		repaint();
	}
}
