package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Table extends JPanel{

	private static final long serialVersionUID = 1L;
	private DefaultTableModel model;
	private JTable table;
	private JScrollPane scroll;
	private static final Color HEADER_COLOR = Color.decode("#6a85b1");
	private static final Font FONT_HEADER = new Font("Comic Sans Ms", Font.BOLD, 10);
	private static final int ROW_HEIGTH = 25;
	/**
	 * 
	 * @author Carlos
	 */
	public Table() {
		setLayout(new BorderLayout());

		model = new DefaultTableModel();

		table = new JTable(model);
		table.setRowHeight(ROW_HEIGTH);
		table.setEnabled(true);

		JTableHeader header = table.getTableHeader();
		header.setBackground(HEADER_COLOR);
		header.setForeground(Color.WHITE);
		header.setFont(FONT_HEADER);

		scroll = new JScrollPane(table);

		add(scroll, BorderLayout.CENTER);
	}

	public void setIdentifiers(Object[] header) {
		model.setColumnIdentifiers(header);
		repaint();
	}

	public void refreshTable(String[] list) {
		model.setRowCount(0);
		for (int i = 0; i < list.length; i++) {
			model.addRow(new Object[] {list[i]});
		}
//		model.addRow(list.getLast().getInformation().toObjectVector());
	}
}