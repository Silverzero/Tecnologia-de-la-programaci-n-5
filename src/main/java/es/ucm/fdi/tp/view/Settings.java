package es.ucm.fdi.tp.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.view.GameWindow.PlayerMode;


public class Settings<S extends GameState<S, A>, A extends GameAction<S,A>> extends JToolBar{
 
	private static final long serialVersionUID = 1L;
	private GameViewController<S,A> controlador;
	private JComboBox<PlayerMode> ComboPlayer;
	

	public Settings(GameViewController<S,A> controlador)
	{
		super();
		this.controlador = controlador;
		initGUI();
	}

	private void initGUI()
	{
		this.setOpaque(true);
		
		
		/*
		JButton BackButton = new JButton();
		BackButton.setIcon(new ImageIcon("src/main/java/undo.png"));
		BackButton.setToolTipText("Close the game");
		BackButton.addActionListener(
		new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.buttonUndo();
			}
		});
		this.add(BackButton);*/
		
		//Boton Random
		JButton RandomButton = new JButton();
		RandomButton.setIcon(new ImageIcon("src/main/java/random.png"));
		RandomButton.setToolTipText("Random Movement");
		RandomButton.addActionListener(
		new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
					controlador.makeRandomMove();
			}	
		});
		this.add(RandomButton);
		
		//Boton Smart
		JButton SmartButton = new JButton();
		SmartButton.setIcon(new ImageIcon("src/main/java/smart.png"));
		SmartButton.setToolTipText("Smart Movement");
		SmartButton.addActionListener(
		new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
					controlador.makeSmartMove();
			}	
		});
		this.add(SmartButton);
		
		//Boton Restart
		JButton RestartButton = new JButton();
		RestartButton.setIcon(new ImageIcon("src/main/java/restart.png"));
		RestartButton.setToolTipText("Restart the game");
		RestartButton.addActionListener(
		new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.buttonRestartGame();
			}	
		});
		this.add(RestartButton);
		
		//Boton Off
		JButton OffButton = new JButton();
		OffButton.setIcon(new ImageIcon("src/main/java/off.png"));
		OffButton.setToolTipText("Close the game");
		OffButton.addActionListener(
		new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame Stop = new JFrame();
				int eleccion = JOptionPane.showOptionDialog(Stop,"¿Estas seguro que quieres salir del juego?",
				"",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,null,null);
				if(eleccion == JOptionPane.YES_OPTION)
					System.exit(1);
			}
		});
		this.add(OffButton);
		
		//Modo de Juego
		JLabel modo = new JLabel("Player Mode: ");
		this.add(modo);
		
		//Modo de Juego Combo
		this.ComboPlayer = new JComboBox<PlayerMode>(new DefaultComboBoxModel<PlayerMode>()){

			private static final long serialVersionUID = 1L;
			public void setSelectedItem(Object o){
				super.setSelectedItem(o);
				controlador.setComboBox((PlayerMode)o);
			}
		};
		this.ComboPlayer.addItem(PlayerMode.MANUAL);
		this.ComboPlayer.addItem(PlayerMode.RANDOM);
		this.ComboPlayer.addItem(PlayerMode.SMART);
		
		this.ComboPlayer.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				ComboPlayer.getSelectedItem();
			}
			
		});
		this.add(this.ComboPlayer);
		this.setVisible(true);
	}
}

