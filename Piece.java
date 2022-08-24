package ChessEngine;

public class Piece {
	private String color;
	private String piece;
	private int moves;
	
	public Piece(String w, String p) {
		color = w;
		piece = p;
		moves = 0;
	}
	
	public String toString() {
		return piece;
	}
	
	public String color() {
		return color;
	}
	
	public void setPiece(String p) {
		piece = p;
	}
	
	public void addMove() {
		moves++;
	}
	
	public int getMoves() {
		return moves;
	}
}
