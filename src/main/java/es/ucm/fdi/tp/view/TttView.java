package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.ttt.TttAction;
import es.ucm.fdi.tp.ttt.TttState;

public class TttView extends RectBoardGameView<TttState, TttAction> {

	public TttView(int PlayerID) {
		super(PlayerID);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
		if(activeGame && currentTurn == PlayerID && this.state.at(row, col) == -1) {
            controlador.makeMove(new TttAction(state.getTurn(), row, col));
        }
    }

	 @Override
	 protected Integer getPosition(int row, int col) {
		 return state != null && state.at(row, col) != -1 ? state.at(row, col) : null;
	 }

	@Override
	protected int getNumCols() {
        return state != null ? state.getDimension() : -1;
    }


	@Override
	protected int getNumRows() {
		return state != null ? state.getDimension() : -1;
	}

	@Override
	protected void keyTyped(int keyCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showInfoMessage(String msg) {
		super.showInfoMessage(msg);
		
	}


}
