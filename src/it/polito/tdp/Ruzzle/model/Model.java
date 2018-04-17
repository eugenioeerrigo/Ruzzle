package it.polito.tdp.Ruzzle.model;

import java.util.*;

import it.polito.tdp.Ruzzle.db.DizionarioDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Model {
	private final int SIZE = 4;
	private Board board ;                // campo di gioco
	private List<String> dizionario ;
	private StringProperty statusText ;

	public Model() {
		this.statusText = new SimpleStringProperty() ;
		
		this.board = new Board(SIZE);                       //creo nuova tabella
		DizionarioDAO dao = new DizionarioDAO() ;             
		this.dizionario = dao.listParola() ;                //leggo dizionario da DB
		statusText.set(String.format("%d parole lette", this.dizionario.size())) ;
	
	}
	
	
	public void reset() {
		this.board.reset() ;
		this.statusText.set("Board Reset");
	}

	public Board getBoard() {
		return this.board;
	}

	public final StringProperty statusTextProperty() {
		return this.statusText;
	}
	

	public final String getStatusText() {
		return this.statusTextProperty().get();
	}
	

	public final void setStatusText(final String statusText) {
		this.statusTextProperty().set(statusText);
	}


	public List<Pos> trovaParola(String parola) {
		Ricerca ricerca = new Ricerca();
		return ricerca.trovaParola(parola, this.board);
	}


	public List<String> trovaTutte() {
		List<String> trovate = new ArrayList<String>();
		
		for(String parola : dizionario) {
			if(parola.length()>1) {
				if(trovaParola(parola.toUpperCase())!=null)
					trovate.add(parola);
			}
		}
		return trovate;
	}
	

}
