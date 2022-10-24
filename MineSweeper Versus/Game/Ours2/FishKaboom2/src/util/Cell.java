package util;

public class Cell {
	/*number adjacents mines (0-8)*/
	private int numberCellsAdjacents;
	
	private Boolean isDiscovered;
	
	/* false = not mine | true = mine */
	private Boolean isMine;
	
	/*default constructor*/
	public Cell() {
		this.setNumberCellsAdjacents(0);
		this.setIsDiscovered(null);
		this.setIsMine(false);
	}
	
	public Cell(int nca, boolean discoverd, boolean mine) {
		this.setNumberCellsAdjacents(nca);
		this.setIsDiscovered(discoverd);
		this.setIsMine(mine);
	}

	public int getNumberCellsAdjacents() {
		return numberCellsAdjacents;
	}

	public void setNumberCellsAdjacents(int numberCellsAdjacents) {
		this.numberCellsAdjacents = numberCellsAdjacents;
	}

	public Boolean getIsDiscovered() {
		return isDiscovered;
	}

	public void setIsDiscovered(Boolean isDiscovered) {
		this.isDiscovered = isDiscovered;
	}

	public Boolean getIsMine() {
		return isMine;
	}

	public void setIsMine(Boolean isMine) {
		this.isMine = isMine;
	}
}
