package es.ucm.fdi.tp.view;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFrame;

import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.Utils;
import es.ucm.fdi.tp.base.model.GameAction;

import es.ucm.fdi.tp.mvc.GameController;
import es.ucm.fdi.tp.view.JBoard.Shape;




public abstract class RectBoardGameView< S extends GameState<S,A>, A extends GameAction<S,A>> extends GameView<S,A> implements ColorController {

	private static final long serialVersionUID = 1L;
	protected S state;
	private JBoard tablero;
	private InfoPanel<S,A> EastPanel;
	protected int currentTurn;
	protected boolean activeGame;
	protected int PlayerID;
	protected GameController<S, A> controlador;
	
	
	private Map<Integer, Color> playerColors;
	Iterator<Color> colorsIter;
	//private ColorChooser c;

	public RectBoardGameView(int PlayerID)
	{
		super();
		this.PlayerID = PlayerID;
		this.colorsIter = Utils.colorsGenerator();
		this.playerColors = new HashMap<>();
		this.initGUI();
	}

	private void initGUI()
	{
		this.setLayout(new BorderLayout());
		/* TABLERO SIN PANEL PARA QUE SE REDIMENSIONE*/
		
		
		this.tablero = new JBoard(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void keyTyped(int keyCode) {
				RectBoardGameView.this.keyTyped(keyCode);
				
			}

			@Override
			protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
				RectBoardGameView.this.mouseClicked(row, col, clickCount, mouseButton);
				
			}
			
			@Override
			protected Shape getShape(int player) {
				return RectBoardGameView.this.getShape(player);
			}


			@Override
			protected Color getColor(int player) {
				return RectBoardGameView.this.getColor(player);
			}

			
			@Override
			protected Color getBackground(int row, int col) {
				return RectBoardGameView.this.getBackground(row, col);
			}

			@Override
			protected int getNumRows() {
				return RectBoardGameView.this.getNumRows();
			}

			@Override
			protected int getNumCols() {
				return RectBoardGameView.this.getNumCols();
			}

			@Override
			protected Integer getPosition(int row, int col) {
				return RectBoardGameView.this.getPosition(row, col);
			}};
			
			
		this.add(tablero,BorderLayout.CENTER);
		
		/* Añado el panel derecho implementado en otra clase*/
		this.EastPanel = new InfoPanel<S,A>(this);
		this.add(this.EastPanel,BorderLayout.EAST);
		
	}
	
	/*
	private void assignColors(){
		for(int i = 0; i < state.getPlayerCount();i++)
			assignColor(i);
	}
	*/
	
	@Override
	public void setGameViewCtrl(GameController<S, A> controlador) {
		this.controlador = controlador;
	}
	
	protected Shape getShape(int player) {
		return player < state.getPlayerCount() ? Shape.CIRCLE : null;
	}
	
	protected Color getColor(int player) {
		return getPlayerColor(player);
	}
	
	final protected Color getPlayerColor(Integer p) {
		
		Color color = playerColors.get(p);
		
		if (color == null) {
			color = assignColor(p);
		}
		
		return color;
		
	}
	
	private Color assignColor(int p) {
		
		Color c = colorsIter.next();
		playerColors.put(p, c);
		//playerInfoTable.refresh();
		return c;
	}
	
	
	protected Color getBackground(int row, int col) {
		return Color.LIGHT_GRAY;
	}

	
	protected int getSepPixels() {
		return 2;
	}
	
	protected abstract int getNumCols();
	protected abstract int getNumRows();
	protected abstract Integer getPosition(int row, int col);
	protected abstract void mouseClicked(int row, int col, int clickCount, int mouseButton);
	protected abstract void keyTyped(int keyCode);
	
	
	public void update(S state, int currentTurn, boolean activeGame){
		this.state = state;
		this.tablero.repaint();
		this.EastPanel.repaint();
		this.activeGame = activeGame;
		this.currentTurn = currentTurn;
		
	}
	
	public void showInfoMessage(String message)
	{
		this.EastPanel.showInfoMessage(message);
		this.EastPanel.repaint();
	}
	
	public void resetInfoMessage()
	{
		this.EastPanel.resetInfoMessage();
		this.EastPanel.repaint();
	}
	
	public void changePlayerColor(int row, Color  c)
	{
        playerColors.put(row,c);
        repaint();
        this.tablero.repaint();
	}

	public Color PaintColor(int player) {
		return getColor(player);
	}
}
