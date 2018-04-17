package it.polito.tdp.Ruzzle.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Memorizza le lettere presenti nella scacchiera Ruzzle.
 * @author Fulvio
 *
 */
public class Board {                                 //griglia= elenco di posizioni possibili
	private List<Pos> positions;
	private Map<Pos, StringProperty> cells;          //contenuto delle celle - StringProperty per fare il binding(cambia automaticamente il valore alla pressione del button)

	private int size;

	/**
	 * Crea una nuova scacchiera della dimensione specificata
	 * @param size
	 */
	public Board(int size) {
		this.size = size;

		this.positions = new ArrayList<>();
		for (int row = 0; row < this.size; row++) {
			for (int col = 0; col < this.size; col++) {
				this.positions.add(new Pos(row, col));               //aggiungo 16 oggetti Pos
			}
		}

		this.cells = new HashMap<>();

		for (Pos p : this.positions) {
			this.cells.put(p, new SimpleStringProperty());          //contenuto delle celle (ci metto una StringProperty di stringa nulla)
		}
	}
	
	/**
	 * Fornisce la {@link StringProperty} corrispondente alla {@link Pos} specificata. <p>
	 * 
	 * Può essere usata per sapere che lettera è presente
	 * (es. {@code getCellValueProperty(p).get()}) oppure per fare un binding della proprietà stessa sulla mappa visuale.
	 * @param p
	 * @return
	 */
	public StringProperty getCellValueProperty(Pos p) {
		return this.cells.get(p) ;
	}

	/**
	 * Restituisce la lista di oggetti {@link  Pos} che corrispondono alle posizioni lecite sulla scacchiera. Gli elementi sono ordinati per righe.
	 * @return
	 */
	public List<Pos> getPositions() {
		return positions;
	}

	/**
	 * Crea una nuova scacchiera generando tutte lettere casuali
	 */
	public void reset() { 
		for(Pos p: this.positions) {
			int random = (int)(Math.random()*26) ;
			String letter = Character.toString((char)('A'+random)) ;
			this.cells.get(p).set(letter); 
		}
	}

	public List<Pos> getAdiacenti(Pos ultima) {
		List<Pos> result = new ArrayList<Pos>();
		
		for(int riga=-1; riga<=1; riga++) {                    //tutte le 9 posizioni nell'intorno della cella
			for(int colonna=-1; colonna<=1; colonna++) {
				if(riga!=0 || colonna!=0) {                    //=if(! (riga==0 && colonna==0))  -> escludo la cella stessa (offset (0,0))
					Pos p = new Pos(ultima.getRow()+riga, ultima.getCol()+colonna);
					if(positions.contains(p))                  //aggiunge la posizione solo se non sono uscito dalla board
						result.add(p);
				}
			}
		}
		return result;
	}

	
}
