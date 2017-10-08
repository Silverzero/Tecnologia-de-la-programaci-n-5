package es.ucm.fdi.tp.mvc;

import java.util.ArrayList;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameError;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent.EventType;
import es.ucm.fdi.tp.was.WasState;

/**
 * An event-driven game engine.
 * Keeps a list of players and a state, and notifies observers
 * of any changes to the game.
 */
public class GameTable<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameObservable<S, A> {

    protected S State;
    protected S preState;
    protected S InitialState;
    protected ArrayList<GameObserver<S,A>> ObserverList;
    protected boolean isStopped;

    public GameTable(S initState)
    {
    	if(initState == null)
    		throw new GameError("Initial state cannot be null");
    	else{
    		this.InitialState = initState;
    		this.State = initState;
    		this.preState = this.State;
    		this.ObserverList = new ArrayList<GameObserver<S,A>>();
    	}
    }
    
    /** */
    public void start(){
        this.State = this.InitialState;
        this.isStopped = false;
        //reiniciamos tambien la lista de observers?;
        this.NotifyObservers(new GameEvent<S,A>(EventType.Start,null,this.State,null,"A game has been started/reset"));
    }
    
    public void undo(){
        this.State = this.preState;
        this.isStopped = false;
        this.NotifyObservers(new GameEvent<S,A>(EventType.Change,null,this.State,null,"The action has been applied"));
    }
    
    /** */
    public void stop(){
        if(!this.isStopped){
        	this.isStopped = true;
        	this.NotifyObservers(new GameEvent<S,A>(EventType.Stop,null,this.State,null,"The game has been Stopped"));
        }
        else{
        	GameError Error = new GameError("The game is already stopped");
        	this.NotifyObservers(new GameEvent<S,A>(EventType.Error,null,this.State,Error,"The game is already stopped"));
        	throw Error;
        }
    }
    
    /** */
    public void execute(A action){
        if(!this.isStopped && !this.State.isFinished())
        {
	        if(this.State.getTurn() == action.getPlayerNumber()){
	        	this.preState = this.State;
	        	this.State = action.applyTo(this.State);
	        	this.NotifyObservers(new GameEvent<S,A>(EventType.Change,action,this.State,null,"The action has been applied"));
	        	if(this.State.isFinished())
	            	this.stop();
	            
	        }
	        else{
	        	GameError Error = new GameError("The action cannot be applied");
	        	this.NotifyObservers(new GameEvent<S,A>(EventType.Error,action,this.State,Error,"The action cannot be applied"));
	        }
        }
    }
    
    /** Getter of State field */
    public S getState(){
		return this.State;
    }
    
    /** Add an Observer to ArrayList of Observers */
    public void addObserver(GameObserver<S, A> o){
       this.ObserverList.add(o);
    }
    
    /** Remove an Observer to ArrayList of Observers */
    public void removeObserver(GameObserver<S, A> o) {
        this.ObserverList.remove(o);
    }
    
    /** */
    protected void NotifyObservers(GameEvent<S,A> event){
    	for(GameObserver<S,A> observer : this.ObserverList)
    		observer.notifyEvent(event);
    }
    
    public int getToken(int row, int col){
    	return ((WasState) this.State).getPosicion(row, col);
    }
}
