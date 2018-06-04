package views;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import constants.ConstantsUI;
import controllers.Controller;
import controllers.MyActions;
import models.entities.User;

public class ClientWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	private String name;
	private static  ArrayList<User> listUser;
	private JList<String> list;
	private static DefaultListModel<String> model;
	private JLabel lbTitle;
	private DialogDetails dialog;

	public ClientWindow(Controller controller) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(ConstantsUI.DIMENSION);
		setJMenuBar(new Menu_Button().createMenuBar(ConstantsUI.FOLDER, ConstantsUI.FOLDER, controller, MyActions.ADD_NEW.toString()));
		this.getContentPane().setBackground(ConstantsUI.WINDOW_COLOR);
		
		dialog = new DialogDetails(controller);
		
		lbTitle = new JLabel(ConstantsUI.TITLE_MAIN_DIALOG);
		lbTitle.setFont(ConstantsUI.FONT);
		lbTitle.setIcon(new ImageIcon(getClass().getResource("/img/folder.png")));
		lbTitle.setHorizontalAlignment(0);
		add(lbTitle, BorderLayout.NORTH);
		
		model = new DefaultListModel<>();
		
		list = new JList<>(model);
		list.setBackground(ConstantsUI.WINDOW_COLOR);
		list.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() >= 2) {
					try {
						dialog.setInfo(searchUser(list.getSelectedValue()));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					dialog.setVisible(true);
				}
			}
		});
		add(new JScrollPane(list), BorderLayout.CENTER);
		
		setVisible(true);
	}

	private User searchUser(String selectedValue) throws Exception {
		for (User user : listUser) {
			if(user.getName().equals(selectedValue)) {
				return user;
			}
		}
			throw new Exception("the element has dont finded");
	}
	
	public static void refreshList() throws IOException {
		model.removeAllElements();
		for (User user : listUser) {
			model.addElement(user.getName());
		}
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

	public static void setArray(ArrayList<User> readFile) throws IOException {
		listUser = readFile;
		refreshList();
	}

	public String getNameOfUser() {
		return dialog.getTitle();
	}

	public String getNameOfFile() {
		return dialog.getNameOfFile();
	}
	
	public void ocultPop() {
		dialog.ocultPop();
	}

	public static ArrayList<User> getList() {
		return listUser;
	}
}
