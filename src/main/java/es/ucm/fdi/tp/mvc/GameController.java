package es.ucm.fdi.tp.mvc;

import java.util.List;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;

public class GameController<S extends GameState<S,A>, A extends GameAction<S,A>> implements Runnable {

private List<GamePlayer> playerslist;
private GameTable<S,A> gametable;

public GameController(List<GamePlayer> players, GameTable<S,A> game)
{
	this.playerslist = players;
	this.gametable = game;
}

public void start()
{
	this.gametable.start();
}

public void undo()
{
	this.gametable.undo();
}

public void stop()
{
	this.gametable.stop();
}

public void makeMove(A action)
{
	this.gametable.execute(action);
}
@Deprecated
public boolean checkMakeMove(int roworg, int colorg, int row, int col)
{
 return Math.sqrt( Math.pow(roworg-row,2) + Math.pow(colorg-col,2)) == Math.sqrt(2);
}

public int getPosicion(int row, int col)
{
	return this.gametable.getToken(row, col);
}

public void run()
{
	int playerCount = 0;
	for (GamePlayer p : this.playerslist) {
		p.join(playerCount++); // welcome each player, and assign
								// playerNumber
	}
	this.start();
	
	while (!this.gametable.getState().isFinished()) {
		// request move
		A action = this.playerslist.get(this.gametable.getState().getTurn()).requestAction(this.gametable.getState());
		this.makeMove(action);
	}
	
	this.stop();
}

}
