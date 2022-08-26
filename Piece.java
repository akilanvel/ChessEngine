package ChessEngine;

public class Piece {
	private String color;
	private String piece;
	private int moves;
	private int points;

	public Piece(String w, String p) {
		color = w;
		piece = p;
		moves = 0;
		if (p.equals("p")) {
			points = 1;
		} else if (p.equals("r")) {
			points = 5;
		} else if (p.equals("b")) {
			points = 3;
		} else if (p.equals("k")) {
			points = 3;
		} else if (p.equals("q")) {
			points = 9;
		} else if (p.equals("K")) { // may have to delete this if statement and prioritize king another way
			points = 10000;
		} else {
			points = 0;
		}

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

	public int getPoints() {
		return points;
	}
}
