package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import es.ucm.fdi.tp.mvc.*;
import es.ucm.fdi.tp.mvc.GameEvent.EventType;

import javax.swing.*;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;

public class GameWindow <S extends GameState<S, A>, A extends GameAction<S,A>> extends JFrame implements GameObserver<S,A>, GameViewController<S,A>
{
	private static final long serialVersionUID = 1L;
	
	enum PlayerMode
	{
		MANUAL("Manual"),RANDOM("Random"),SMART("Smart");
		private String name;
		PlayerMode(String name){this.name = name;}	
		public String toString(){ return name;}
	}
	
	
	private GameController<S,A> controlador;
	private int PlayerID;
	private GameView<S,A> juego;
	private NorthPanel<S,A> panelnorte;
	private S estado;
	//private S backup;
	
	GamePlayer Random;
	GamePlayer Smart;
	PlayerMode playermode;
	private int currentTurn;
	private boolean activeGame;
	
	
	
	

	public GameWindow(int i, GamePlayer random, GamePlayer smart, GameView<S, A> vistaJuego,GameController<S, A> controlador, GameObservable<S, A> game) 
	{
		super("Game Emulator");
		this.controlador = controlador;
		this.Random = random;
		this.Smart = smart;
		this.juego = vistaJuego;
		this.juego.setGameViewCtrl(this.controlador);
		this.PlayerID = i;
		this.playermode = PlayerMode.MANUAL;
		this.panelnorte = new NorthPanel<S,A>(this);
	
		this.initGUI();
	
		game.addObserver(this);
	}

	private void initGUI()
	{
		JPanel mainpanel = new JPanel(new BorderLayout());
		mainpanel.add(this.panelnorte,BorderLayout.PAGE_START);
		mainpanel.add(this.juego, BorderLayout.CENTER);
		
		mainpanel.setVisible(true);
		
		this.setContentPane(mainpanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(650,500));
		this.setPreferredSize(new Dimension(650,500));
		this.setMaximumSize(new Dimension(975,750));
		this.pack();
		this.setVisible(true);
	}
	
	
	@Override
	public void notifyEvent(final GameEvent<S, A> e) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				handleEvent(e);
			}
		});

	}
	
	
	public void handleEvent(GameEvent<S, A> e) {
		
		//this.backup = this.estado;
		this.estado = e.getState();
		this.currentTurn = this.estado.getTurn();
		this.activeGame = !this.estado.isFinished() || e.getType() != EventType.Stop;
		switch (e.getType()) {
		case Start:
			juego.update(this.estado,this.currentTurn, this.activeGame);
			this.setTitle("View for Player" + PlayerID);
			this.juego.resetInfoMessage();
			this.juego.showInfoMessage("* " + e.toString() + "\n");
			if(this.estado.getTurn() == PlayerID)
				this.juego.showInfoMessage("* Your turn.\n");
			this.decideAutomaticMakeMove();
			break;
		case Change:
			juego.update(this.estado,this.currentTurn, this.activeGame);
			this.decideAutomaticMakeMove();
			this.juego.showInfoMessage("* "+ e.getAction().toString()+"\n");
			if(this.estado.getTurn() == PlayerID)
				this.juego.showInfoMessage("* Your turn.\n");
			break;
		case Error:
			juego.update(this.estado,this.currentTurn, this.activeGame);
			this.juego.showInfoMessage("* " + e.toString() +"\n");
			break;
		case Stop:
			this.juego.showInfoMessage("* " + e.toString() +"\n");
			if(this.currentTurn == this.PlayerID)
				this.juego.showInfoMessage("* Player " + this.currentTurn + " wins. \n");
			else 
				this.juego.showInfoMessage("* You wins. \n");
			juego.update(this.estado,this.currentTurn, this.activeGame);
			
		
			break;
		default:
			break;
		}

	}
	
	public void decideAutomaticMakeMove()
	{
		switch(this.playermode)
		{
		case RANDOM: SwingUtilities.invokeLater(new Runnable(){
			
			@Override
			public void run() {
				makeRandomMove();
			}
			
		});
		case SMART: SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				makeSmartMove();
			}
			
		});
		default:
			break;
		
		}
		
	}

	@Override
	public void makeRandomMove() {
		if(activeGame && currentTurn == PlayerID)
			controlador.makeMove(Random.requestAction(estado));
	}

	@Override
	public void makeSmartMove() {
		if(activeGame && currentTurn == PlayerID)
			controlador.makeMove(Smart.requestAction(estado));
	}

	@Override
	public void buttonStop() {
		
	}
	
	public void buttonUndo()
	{
		controlador.undo();
	}

	@Override
	public void buttonRestartGame()
	{
		controlador.start();
	}

	@Override
	public void setComboBox(PlayerMode playermode) {
		//PlayerMode antiguo = this.playermode;
		this.playermode = playermode;
		//if(this.playermode != PlayerMode.MANUAL)
			this.decideAutomaticMakeMove();
	
	}
	
}

