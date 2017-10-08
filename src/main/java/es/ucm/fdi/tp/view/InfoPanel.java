package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
//import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;

public class InfoPanel<S extends GameState<S, A>, A extends GameAction<S,A>>extends JPanel{

	private static final long serialVersionUID = 1L;
	private JPanel PanelSuperior;
	private JPanel PanelInferior;
	private JTextArea area;
	private ColorController CControler;

	public InfoPanel(ColorController c) {
		super();
		this.CControler = c;
		initGUI();
	}

	private void initGUI()
	{
		/*Panel SUPERIOR*/
		//***********************************************************************
		//this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setPreferredSize(new Dimension(230,100));
		
		this.PanelSuperior = new JPanel(new BorderLayout());
		this.PanelSuperior.setMaximumSize(new Dimension(300,600));
		this.area = new JTextArea(15,20);
		area.setEditable(false);
		JScrollPane StatusMessages = new JScrollPane(area,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		StatusMessages.setBorder(BorderFactory.createTitledBorder("Status Messages: "));
		
		this.PanelSuperior.add(StatusMessages,BorderLayout.CENTER);
		this.PanelSuperior.setVisible(true);
		this.add(PanelSuperior,BorderLayout.NORTH);
		
	
		
		
		/*Panel INFERIOR*/
		//***********************************************************************
		this.PanelInferior = new JPanel(new BorderLayout());
		this.PanelInferior.setBorder(BorderFactory.createTitledBorder("Player Information"));
		this.PanelInferior.setMaximumSize(new Dimension(300,100));
		//this.PanelInferior.add(new ColorPanel(), BorderLayout.CENTER);
		this.PanelInferior.add(new ColorPanel(this.CControler), BorderLayout.CENTER);
		this.add(this.PanelInferior,BorderLayout.CENTER);

		
	}
	
	public void showInfoMessage(String message)
	{
		this.area.append(message);
	}
	
	public void resetInfoMessage()
	{
		this.area.setText("");
	}
}
