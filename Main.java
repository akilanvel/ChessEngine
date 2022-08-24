package ChessEngine;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

//TODO
//check for mate and check
//print in color
//add good decision-making

public class Main {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_CYAN = "\u001B[36m";

	public static Piece[][] board = new Piece[8][8];
	public static HashMap<String, String> hash = new HashMap<>();

	public static void main(String[] args) {
		System.out.println("Chess Engine created by Akilan Gnanavel");
		System.out.println("You are white. The computer is black.");
		System.out.println("Enter movements in this format: a2 to a3");
		System.out.println("Each command should be exactly 8 characters");

		generateBoard();
		printBoard();

		simulateGame();
	}

	public static void simulateGame() {
		Scanner scanner = new Scanner(System.in);
		String move = "";
		boolean done = false;

		while (!done) {
			// System.out.println("here1"); // delete
			// user's move
			System.out.println("Enter your move (ex: 'a2 to a3'):");
			move = scanner.nextLine();
			String start = move.substring(0, 2);
			String finish = move.substring(6, 8);
			String realStart = "";
			String realFinish = "";
			boolean error = false;
			int start1 = 0;
			int start2 = 0;
			int finish1 = 0;
			int finish2 = 0;
			try {
				realStart = hash.get(start);
				realFinish = hash.get(finish);
				start1 = Integer.parseInt(realStart.substring(0, 1));
				start2 = Integer.parseInt(realStart.substring(1, 2));
				finish1 = Integer.parseInt(realFinish.substring(0, 1));
				finish2 = Integer.parseInt(realFinish.substring(1, 2));
			} catch (Exception e) {
				System.out.println("Invalid command or location(s). Please try again");
				error = true;
			}
			if (board[finish1][finish2].color().equals("w")) {
				System.out.println("Invalid command or location(s). Please try again");
				error = true;
			}
			if (!error) {
				if (!isValid(board[start1][start2], realStart, realFinish)) {
					System.out.println("That move is not valid. Please try again");
				} else {
					board[finish1][finish2] = board[start1][start2];
					board[start1][start2] = new Piece(" ", " ");

					// computer's move
					Random rand = new Random();
					boolean notReady = true;
					while (notReady) {
						// System.out.println("here2"); // delete
						start1 = rand.nextInt(8);
						start2 = rand.nextInt(8);
						if (board[start1][start2].color().equals("b")) {
							notReady = false;
						}
					}
					notReady = true;
					while (notReady) {
						// System.out.println("here3"); // delete
						finish1 = rand.nextInt(8);
						finish2 = rand.nextInt(8);
						start = Integer.toString(start1) + Integer.toString(start2);
						finish = Integer.toString(finish1) + Integer.toString(finish2);
						if (isValid(board[start1][start2], start, finish)
								&& !board[finish1][finish2].color().equals("b")) {
							notReady = false;
						}
					}
					board[finish1][finish2] = board[start1][start2];
					board[start1][start2] = new Piece(" ", " ");
				}
			}

			printBoard();
			if (checkVictory()) {
				done = true;
				System.out.println("Congratulations! You have won!");
			}
			if (checkDefeat()) {
				done = true;
				System.out.println("Defeat! You lost to the chess engine!");
			}
		}

		scanner.close();
	}

	public static boolean checkVictory() {
		for (int i = 0; i < 8; i++) {
			for (int x = 0; x < 8; x++) {
				if (board[i][x].toString().equals("K") && board[i][x].color().equals("b")) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean checkDefeat() {
		for (int i = 0; i < 8; i++) {
			for (int x = 0; x < 8; x++) {
				if (board[i][x].toString().equals("K") && board[i][x].color().equals("w")) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean isValid(Piece piece, String start, String finish) {
		if (start.equals(finish)) {
			return false;
		}
		int start1 = Integer.parseInt(start.substring(0, 1));
		int start2 = Integer.parseInt(start.substring(1, 2));
		int finish1 = Integer.parseInt(finish.substring(0, 1));
		int finish2 = Integer.parseInt(finish.substring(1, 2));
		if (piece.toString().equals("p")) {
			if (piece.color().equals("w")) {
				if (finish1 == start1 - 2) {
					if (piece.getMoves() > 0) {
						return false;
					} else {
						piece.addMove();
					}
				} else if (start1 - finish1 > 1) {
					return false;
				}
				if (finish2 - start2 > 1 || finish2 - start2 < -1) {
					return false;
				}
				if (finish2 != start2) {
					if (!board[finish1][finish2].color().equals("b")) {
						return false;
					}
					if (board[finish1][finish2].toString().equals(" ")) {
						return false;
					}
				}
				if (finish1 == start1) {
					if (!board[finish1][finish2].toString().equals(" ")) {
						return false;
					}
				}
			} else if (piece.color().equals("b")) {
				if (finish1 != start1 + 1) {
					return false;
				}
				if (start2 - finish2 > 1 || start2 - finish2 < -1) {
					return false;
				}
				if (finish2 != start2) {
					if (!board[finish1][finish2].color().equals("w")) {
						return false;
					}
					if (board[finish1][finish2].toString().equals(" ")) {
						return false;
					}
				}
				if (finish1 == start1) {
					if (!board[finish1][finish2].toString().equals(" ")) {
						return false;
					}
				}
			}
		} else if (piece.toString().equals("r")) {
			// if (piece.color().equals("w")) {
			if (start1 != finish1 && start2 != finish2) {
				return false;
			}
			if (start1 < finish1) { // down
				for (int i = start1 + 1; i < finish1; i++) {
					if (!board[i][finish2].toString().equals(" ")) {
						return false;
					}
				}
			} else if (start1 > finish1) { // up
				for (int i = finish1 + 1; i < start1; i++) {
					if (!board[i][finish2].toString().equals(" ")) {
						return false;
					}
				}
			} else if (start2 < finish2) { // right
				for (int i = start2 + 1; i < finish2; i++) {
					if (!board[finish1][i].toString().equals(" ")) {
						return false;
					}
				}
			} else if (start2 > finish2) { // left
				for (int i = finish2; i < start1; i++) {
					if (!board[finish1][i].toString().equals(" ")) {
						return false;
					}
				}
			}
			if (board[finish1][finish2].color().equals("w")) {
				return false;
			}
			// }
		} else if (piece.toString().equals("k")) {
			// if (piece.color().equals("w")) {
			int vertical = Math.abs(start1 - finish1);
			int horizontal = Math.abs(start2 - finish2);
			if (!((horizontal == 1 && vertical == 2) || (horizontal == 2 && vertical == 1))) {
				return false;
			}
			if (board[finish1][finish2].color().equals("w")) {
				return false;
			}
			// }
		} else if (piece.toString().equals("b")) {
			// if (piece.color().equals("w")) {
			int vertical = Math.abs(start1 - finish1);
			int horizontal = Math.abs(start2 - finish2);
			if (vertical != horizontal) {
				return false;
			}
			if (finish2 > start2) { // right
				if (start1 > finish1) { // up
					for (int i = 1; i < vertical; i++) {
						if (!board[start1 - i][start2 + i].toString().equals(" ")) {
							return false;
						}
					}
				} else if (start1 < finish1) { // down
					for (int i = 1; i < vertical; i++) {
						if (!board[start1 + i][start2 + i].toString().equals(" ")) {
							return false;
						}
					}
				}
			} else if (finish2 < start2) { // left
				if (start1 > finish1) { // up
					for (int i = 1; i < vertical; i++) {
						if (!board[start1 - i][start2 - i].toString().equals(" ")) {
							return false;
						}
					}
				} else if (start1 < finish1) { // down
					for (int i = 1; i < vertical; i++) {
						if (!board[start1 + i][start2 - i].toString().equals(" ")) {
							return false;
						}
					}
				}
			}
			// }
		} else if (piece.toString().equals("q")) {
			// if (piece.color().equals("w")) {
			int vertical = Math.abs(start1 - finish1);
			int horizontal = Math.abs(start2 - finish2);
			if (!((vertical == 0 && horizontal != 0) || (vertical != 0 && horizontal == 0)
					|| (vertical == horizontal))) {
				return false;
			}
			if (finish2 > start2) { // right
				if (start1 > finish1) { // up
					for (int i = 1; i < vertical; i++) {
						if (!board[start1 - i][start2 + i].toString().equals(" ")) {
							return false;
						}
					}
				} else if (start1 < finish1) { // down
					for (int i = 1; i < vertical; i++) {
						if (!board[start1 + i][start2 + i].toString().equals(" ")) {
							return false;
						}
					}
				}
			} else if (finish2 < start2) { // left
				if (start1 > finish1) { // up
					for (int i = 1; i < vertical; i++) {
						if (!board[start1 - i][start2 - i].toString().equals(" ")) {
							return false;
						}
					}
				} else if (start1 < finish1) { // down
					for (int i = 1; i < vertical; i++) {
						if (!board[start1 + i][start2 - i].toString().equals(" ")) {
							return false;
						}
					}
				}
			}
			// }
		} else if (piece.toString().equals("K")) {
			// if (piece.color().equals("w")) {
			int vertical = Math.abs(start1 - finish1);
			int horizontal = Math.abs(start2 - finish2);
			if (vertical > 1 || horizontal > 1) {
				return false;
			}
			// }
		}
		return true;
	}

	public static void generateBoard() {
		for (int i = 2; i < 6; i++) {
			for (int x = 0; x < 8; x++) {
				board[i][x] = new Piece(" ", " ");
			}
		}
		for (int i = 0; i < 8; i++) {
			board[1][i] = new Piece("b", "p");
			board[6][i] = new Piece("w", "p");
		}
		for (int i = 0; i < 8; i++) {
			for (int x = 0; x < 8; x++) {
				if (i == 0) {
					board[i][x] = new Piece("b", " ");
				}
				if (i == 7) {
					board[i][x] = new Piece("w", " ");
				}
			}
		}
		for (int x = 0; x < 8; x++) {
			if (x == 0 || x == 7) {
				board[0][x].setPiece("r");
				board[7][x].setPiece("r");
			}
			if (x == 1 || x == 6) {
				board[0][x].setPiece("k");
				board[7][x].setPiece("k");
			}
			if (x == 2 || x == 5) {
				board[0][x].setPiece("b");
				board[7][x].setPiece("b");
			}
			if (x == 3) {
				board[0][x].setPiece("q");
				board[7][x].setPiece("q");
			}
			if (x == 4) {
				board[0][x].setPiece("K");
				board[7][x].setPiece("K");
			}
		}

		hash.put("a1", "70");
		hash.put("a2", "60");
		hash.put("a3", "50");
		hash.put("a4", "40");
		hash.put("a5", "30");
		hash.put("a6", "20");
		hash.put("a7", "10");
		hash.put("a8", "00");
		hash.put("b1", "71");
		hash.put("b2", "61");
		hash.put("b3", "51");
		hash.put("b4", "41");
		hash.put("b5", "31");
		hash.put("b6", "21");
		hash.put("b7", "11");
		hash.put("b8", "01");
		hash.put("c1", "72");
		hash.put("c2", "62");
		hash.put("c3", "52");
		hash.put("c4", "42");
		hash.put("c5", "32");
		hash.put("c6", "22");
		hash.put("c7", "12");
		hash.put("c8", "02");
		hash.put("d1", "73");
		hash.put("d2", "63");
		hash.put("d3", "53");
		hash.put("d4", "43");
		hash.put("d5", "33");
		hash.put("d6", "23");
		hash.put("d7", "13");
		hash.put("d8", "03");
		hash.put("e1", "74");
		hash.put("e2", "64");
		hash.put("e3", "54");
		hash.put("e4", "44");
		hash.put("e5", "34");
		hash.put("e6", "24");
		hash.put("e7", "14");
		hash.put("e8", "04");
		hash.put("f1", "75");
		hash.put("f2", "65");
		hash.put("f3", "55");
		hash.put("f4", "45");
		hash.put("f5", "35");
		hash.put("f6", "25");
		hash.put("f7", "15");
		hash.put("f8", "05");
		hash.put("g1", "76");
		hash.put("g2", "66");
		hash.put("g3", "56");
		hash.put("g4", "46");
		hash.put("g5", "36");
		hash.put("g6", "26");
		hash.put("g7", "16");
		hash.put("g8", "06");
		hash.put("h1", "77");
		hash.put("h2", "67");
		hash.put("h3", "57");
		hash.put("h4", "47");
		hash.put("h5", "37");
		hash.put("h6", "27");
		hash.put("h7", "17");
		hash.put("h8", "07");
	}

	public static void printBoard() {
		for (int i = 0; i < 9; i++) {
			if (i < 8) {
				if (i == 7) {
					System.out.print("1 ");
				} else if (i == 6) {
					System.out.print("2 ");
				} else if (i == 5) {
					System.out.print("3 ");
				} else if (i == 4) {
					System.out.print("4 ");
				} else if (i == 3) {
					System.out.print("5 ");
				} else if (i == 2) {
					System.out.print("6 ");
				} else if (i == 1) {
					System.out.print("7 ");
				} else if (i == 0) {
					System.out.print("8 ");
				}
				System.out.print("| ");
				for (int x = 0; x < 8; x++) {
					if (x < 7) {
						System.out.print(board[i][x] + " | ");

					} else if (x == 7) {
						System.out.println(board[i][x] + " |");
					}
				}
			} else {
				System.out.println("    -   -   -   -   -   -   -   -");
				System.out.println("    a   b   c   d   e   f   g   h");
			}
		}
	}
}
