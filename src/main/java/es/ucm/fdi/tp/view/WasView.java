package es.ucm.fdi.tp.view;

import java.awt.Color;
import java.awt.event.KeyEvent;


import es.ucm.fdi.tp.was.WasAction;
import es.ucm.fdi.tp.was.WasState;

public class WasView extends RectBoardGameView<WasState, WasAction> {

	public WasView(int PlayerID) {
		super(PlayerID);
		// TODO Auto-generated constructor stub
	}

	protected boolean firstClickRecieved;
	
	int origRow;
	int origCol;

	private static final long serialVersionUID = -8721858828945561318L;

	@Override
	protected int getNumCols() {
		return 8;
	}

	@Override
	protected int getNumRows() {
		return 8;
	}

	@Override
	protected Integer getPosition(int row, int col) {
		return state != null && state.at(row, col) != -1 ? state.at(row, col) : null;
	}

	@Override
	protected Color getBackground(int row, int col) {
		return (row+col) % 2 == 0 ? Color.LIGHT_GRAY : Color.BLACK;
	}

	@Override
	protected int getSepPixels() {
		return 0;
	}

	@Override
	protected void keyTyped(int keyCode) {
		
		if (firstClickRecieved && keyCode == KeyEvent.VK_ESCAPE) {
			
			firstClickRecieved = false;
			this.showInfoMessage("Selection canceled");
			
		}
		this.showInfoMessage("* Selection canceled \n");
		
	}

	
	@Override
	protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
		
			if (!firstClickRecieved && currentTurn == PlayerID) {
				
				if( PlayerID == this.controlador.getPosicion(row, col)){
					origRow = row;
					origCol = col;
					this.showInfoMessage("* Selected (" + origRow + "," + origCol + ") \n");
					firstClickRecieved = true;
				}
				else 
					this.showInfoMessage("* Empty cell or Not your token \n");
			}
			else {
				if(!firstClickRecieved)
					this.showInfoMessage("* Its not your turn \n");
				
				else
				{ 
						
					if ( activeGame && currentTurn == PlayerID && PlayerID == this.controlador.getPosicion(origRow,origCol)
					&& this.state.isValid(row,col)){
						
						WasAction action = new WasAction(state.getTurn(), origRow, origCol, row, col);
						if(this.state.ValidMovement(action,PlayerID))
							controlador.makeMove(action);
						else 
							this.showInfoMessage("* Wrong movement \n");
					}
					else 
						this.showInfoMessage("* Action canceled \n");
					
					firstClickRecieved = false;
				}
			}
		}

	@Override
	public void showInfoMessage(String msg)
	{
		super.showInfoMessage(msg);
	}
}

	


