package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import models.ClientConnections;

public class MainWindow extends JFrame{

	private static final Dimension DIMENSION = new Dimension(350, 600);
	private static final long serialVersionUID = 1L;
	private DefaultListModel<String> model;
	private JList<String> list;
	
	public MainWindow() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(DIMENSION);
		
		model = new DefaultListModel<>();
		
		list = new JList<>(model);
		add(new JScrollPane(list), BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	public void refreshList(ArrayList<ClientConnections> list) throws IOException {
		System.out.println(list.get(0).toString());
		model.clear();
		for (ClientConnections string : list) {
			System.out.println(string.readResquest().split("#")[1]+"  estoo");
			model.addElement(string.getInput().readUTF().split("#")[1]);
		}
		repaint();
	}
}
