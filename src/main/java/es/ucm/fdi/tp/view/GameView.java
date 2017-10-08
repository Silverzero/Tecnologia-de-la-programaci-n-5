package es.ucm.fdi.tp.view;

import javax.swing.JComponent;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameController;


public abstract class GameView< S extends GameState<S,A>, 
A extends GameAction<S,A> > extends JComponent {


private static final long serialVersionUID = 8971247618260421832L;


public abstract void update(S state, int currentTurn, boolean activeGame);
public abstract void setGameViewCtrl(GameController<S, A> gvCtrl);
public abstract void showInfoMessage(String msg);
public abstract void resetInfoMessage();
//public abstract void copyColorChooser(ColorChooser color);
}
