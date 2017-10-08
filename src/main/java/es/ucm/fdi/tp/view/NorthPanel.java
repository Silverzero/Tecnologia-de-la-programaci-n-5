package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import es.ucm.fdi.tp.base.model.*;

public class NorthPanel <S extends GameState<S, A>, A extends GameAction<S,A>> extends JPanel
{
	private static final long serialVersionUID = 1L;
	private GameViewController<S,A> controlador;
	private Settings<S,A> settings;
	
	
	public NorthPanel(GameViewController<S,A> controlador)
	{
		super();
		this.controlador = controlador;
		this.settings = new Settings<S,A>(this.controlador);
		this.initGUI();
	}

	private void initGUI()
	{
		this.setOpaque(true);
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		this.add(this.settings,BorderLayout.WEST);
	}
	
}
