package org.sma.cells;


/**
 * Classe permettant de conserver les états d'une cellule.
 *
 * @author 3
 *
 */
public class State {

	/**
	 * Etat d'origine.
	 */
	private int oriState;
	
	/**
	 * Ancien état. 
	 */
	private int oldState;
	
	/**
	 * Etat courant.
	 */
	private int currState;
	
	
	public State (int state) {
		this.oriState = state;
		this.oldState = state;
		this.currState = state;
	}
	
	/**
	 * Initialise tous les état à l'état d'origine.
	 * @param oriState Etat d'origine.
	 */
	public void setOriState(int oriState) {
		this.oriState = oriState;
		this.oldState = oriState;
		this.currState = oriState;
	}
	
	/**
	 * Met à jour l'état courant et sauvegarde l'état précédent.
	 * @param newState Nouvel état.
	 */
	public void updateState(int newState) {
		this.oldState = currState;
		this.currState = newState;
	}
	
	/**
	 * Met à jour l'ancien état à l'état courant.
	 */
	public void finishUpdate() {
		this.oldState = this.currState;
	}
	
	/**
	 * Réinitialise tous les états à l'état d'origine.
	 * @return L'état d'origine.
	 */
	public int backOrigin() {
		this.oldState = this.oriState;
		this.currState = this.oriState;
		return this.oriState;
	}
	
	public int getOldState () {
		return this.oldState;
	}

}
