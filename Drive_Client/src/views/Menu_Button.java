package views;

import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
	
public class Menu_Button {
	
	public JMenuBar createMenuBar(String nameMenu, String nameMenuItem, ActionListener listener, String comand) {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu = new JMenu(nameMenu);
		
		JMenuItem menuItem = new JMenuItem(nameMenuItem);
		menuItem.setActionCommand(comand);
		menuItem.addActionListener(listener);
		menuItem.setBackground(Color.WHITE);
		
		menu.add(menuItem);
		
		menuBar.add(menu);
		return menuBar;
	}
}
