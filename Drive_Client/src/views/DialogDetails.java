package views;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import constants.ConstantsUI;
import controllers.Controller;
import controllers.MyActions;
import models.entities.User;


public class DialogDetails extends JDialog{

	private static final long serialVersionUID = 1L;
	private JList<String> files;
	private DefaultListModel<String> model;
	private JPopupMenu pop;
	private JMenuItem miDownloadServer, miDownloadClient;

	public DialogDetails(Controller controller) {
		setSize(ConstantsUI.DIMENSION_DIALOG_DETAILS);

		miDownloadClient = new JMenuItem(ConstantsUI.DOWNLOAD_CLIENT);
		miDownloadClient.addActionListener(controller);
		miDownloadClient.setActionCommand(MyActions.CLIENT.toString());

		miDownloadServer = new JMenuItem(ConstantsUI.DOWNLOAD_SERVER);
		miDownloadServer.addActionListener(controller);
		miDownloadServer.setActionCommand(MyActions.SERVER.toString());

		pop = new JPopupMenu();
		pop.add(miDownloadServer);
		pop.add(miDownloadClient);

		model = new DefaultListModel<>();
		files = new JList<>(model);
		files.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getModifiers() == 4) {
					files.setSelectedIndex(files.locationToIndex(e.getPoint()));   
					pop.setLocation(getMousePosition());
					pop.setVisible(true);
				}
			}
		});

		add(new JScrollPane(files), BorderLayout.CENTER);
	}

	public void setInfo(User user) {
		setTitle(user.getName());
		File f = new File(user.getPath());
//		String[] p = user.getDirectory().toString().split("#");
		File [] fi = f.listFiles(); 
		model.removeAllElements();
		for (int i = 0; i < fi.length; i++) {
			model.addElement(fi[i].getName());
		}
	}

	public void ocultPop() {
		pop.setVisible(false);
	}

	public String getNameOfFile() {
		return files.getSelectedValue();
	}
}