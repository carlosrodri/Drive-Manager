package views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import constants.ConstantsUI;
import controllers.Controller;
import controllers.MyActions;
import models.entities.User;

public class ClientWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	private String name;
	private Timer timer;
	private static JList<String> list;
	private static DefaultListModel<String> model;
	private static ArrayList<User> listUser;

	public ClientWindow(Controller controller) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(ConstantsUI.DIMENSION);
		setJMenuBar(new Menu_Button().createMenuBar(ConstantsUI.FOLDER, ConstantsUI.FOLDER, controller, MyActions.ADD_NEW.toString()));

		model = new DefaultListModel<>();

		list = new JList<>(model);
		add(new JScrollPane(list), BorderLayout.CENTER);

		timer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(listUser != null) {
					try {
						refreshList();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		timer.start();

		setVisible(true);
	}

	public static void refreshList() throws IOException {
		model.clear();
		for (User user : listUser) {
			model.addElement(user.getName() + " Files:  " + getFiles(user.getDirectory()));
		}
	}

	private static String getFiles(File file) {
		String line =  "";
		String p [] = file.toString().split("#");
		for (int i = 0; i < p.length; i++) {
			line += p[i] + ",  ";
		}
		return line;
	}

	public String getNameClient() {
		name  = JOptionPane.showInputDialog("Name");
		return name;
	}

	public int port() {
		return Integer.parseInt(JOptionPane.showInputDialog("Port"));
	}

	public String getInfo() {
		return name;
	}

	public static void setArray(ArrayList<User> readFile) {
		listUser = readFile;
	}
}
