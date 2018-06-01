package views;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;

import constants.ConstantsUI;
import models.entities.User;


public class DialogDetails extends JDialog{

	private static final long serialVersionUID = 1L;
	private JList<String> files;
	private DefaultListModel<String> model;

	public DialogDetails() {
		setSize(ConstantsUI.DIMENSION_DIALOG_DETAILS);

		model = new DefaultListModel<>();
		files = new JList<>(model);

		add(new JScrollPane(files), BorderLayout.CENTER);
	}

	public void setInfo(User user) {
		setTitle(user.getName());
		model.removeAllElements();
		String[] p = user.getDirectory().toString().split("#");
		for (int i = 0; i < p.length; i++) {
			model.addElement(p[i]);
		}
	}
}
