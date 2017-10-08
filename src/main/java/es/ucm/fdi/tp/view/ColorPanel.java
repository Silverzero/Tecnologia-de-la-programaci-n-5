package es.ucm.fdi.tp.view;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class ColorPanel extends JPanel {

	private MyTableModel tModel;

	private Map<Integer, Color> colors; // Line -> Color
	private ColorChooser colorChooser;
	private ColorController CController;

	public ColorPanel(ColorController cControler) {
		super();
		this.CController = cControler;
		initGUI();
	}

	private void initGUI() {

		this.setLayout(new BorderLayout());
		this.setMaximumSize(new Dimension(300,200));
		colors = new HashMap<>();
		this.colorChooser = new ColorChooser(new JFrame(), "Choose Line Color", Color.BLACK);

		// names table
		tModel = new MyTableModel();
		tModel.getRowCount();
		final JTable table = new JTable(tModel) {
			private static final long serialVersionUID = 1L;

			// THIS IS HOW WE CHANGE THE COLOR OF EACH ROW
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				Component comp = super.prepareRenderer(renderer, row, col);

				// the color of row 'row' is taken from the colors table, if
				// 'null' setBackground will use the parent component color.
				if (col == 1)
					for(int i = 0; i < tModel.getRowCount(); i++)
					{
						if(row == i)
							comp.setBackground(CController.PaintColor(i));
					}
				else
					comp.setBackground(Color.WHITE);
				comp.setForeground(Color.BLACK);
				return comp;
			}
		};

		table.setToolTipText("Click on a row to change the color of a player");
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = table.rowAtPoint(evt.getPoint());
				int col = table.columnAtPoint(evt.getPoint());
				if (row >= 0 && col >= 0) {
					changeColor(row);
					CController.changePlayerColor(row, colors.put(row, colorChooser.getColor()));
				}
			}

		});

		this.add(new JScrollPane(table), BorderLayout.CENTER);
		JPanel ctrlPabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.add(ctrlPabel, BorderLayout.PAGE_START);

		this.setOpaque(true);
		this.setVisible(true);
	}

	private void changeColor(int row) {
		colorChooser.setSelectedColorDialog(colors.get(row));
		colorChooser.openDialog();
		if (colorChooser.getColor() != null) {
			colors.put(row, colorChooser.getColor());
			repaint();
		}
	}
}
