package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.view.GameWindow.PlayerMode;

public interface GameViewController<S extends GameState<S,A>, A extends GameAction<S,A>> {

	public void makeRandomMove();
	public void makeSmartMove();
	public void buttonStop();
	public void buttonRestartGame();
	public void setComboBox(PlayerMode playermode);
	public void buttonUndo();
}
