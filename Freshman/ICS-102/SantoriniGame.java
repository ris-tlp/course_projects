package santorini;

public class SantoriniGame {

	private String[][] board = new String[5][5];
	private Worker[] worker = new Worker[4];

	public SantoriniGame() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = ""; // setting each cell to an empty string
			}

			worker = new Worker[] { new Worker("w11"), new Worker("w12"), new Worker("w21"), new Worker("w22") };
			// creating worker array
		}
	}

	public String toString() {
		String boardOutput = "    0\t    1\t    2\t    3\t    4\n";
		boardOutput += "=========================================\n";
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				boardOutput += "|" + board[i][j];
				boardOutput += "\t";
			}
			boardOutput += "|   " + i + "\n";
			boardOutput += "=========================================\n";
		}
		return boardOutput + "\n";

	}

	public boolean build(int positionX, int positionY, int w) {
		// checking if build target is inside the board and not out of bounds
		if (positionX >= 0 && positionY >= 0 && positionX < board.length && positionY < board[positionX].length) {

			int newPositionX = worker[w].getPositionX();
			int newPositionY = worker[w].getPositionY();

			// checking if target is an adjacent cell
			if (((newPositionX == positionX) || (Math.abs(positionX - newPositionX) == 1))
					&& ((newPositionY == positionY) || Math.abs(positionY - newPositionY) == 1)
					&& (!((newPositionX == positionX) && (newPositionY == positionY)))) {

				for (int i = 0; i < worker.length; i++) {
					// checks if there's a worker in the tile
					if ((board[positionX][positionY].indexOf(worker[i].getName()) >= 0)) {
						return false;

					} else if (board[positionX][positionY].indexOf("BBB") >= 0) {

						// checking if a dome already exists on a 3 level building
						if (board[positionX][positionY].indexOf("D") >= 0) {
							return false;
						} else {

							board[positionX][positionY] += "D";
							return true;
						}
					}
				}

				board[positionX][positionY] += "B";
			} else {
				return false;
			}

		} else {
			return false; // invalid if out of bounds
		}

		return true; // all checks passed, move is valid
	}

	public boolean hasWon(String workerName) {

		String oppositeWorkerName = "";
		// checking if current player won by checking if the opposite player is trapped,
		// so the opposite player name is required
		if (workerName.indexOf("w1") >= 0) {
			oppositeWorkerName = "w2";
		} else {
			oppositeWorkerName = "w1";
		}

		if (isTrapped(oppositeWorkerName)) {
			return true;
		} else {
			for (int i = 0; i < worker.length; i++) {
				if (worker[i].getName().indexOf(workerName) >= 0) {
					if (board[worker[i].getPositionX()][worker[i].getPositionY()].indexOf("BBB" + workerName) >= 0) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean isTrapped(String playerName) {

		int index1 = 0, index2 = 0;
		if (playerName.indexOf("w1") >= 0) {
			index1 = 0;
			index2 = 1;
		} else if (playerName.indexOf("w2") >= 0) {
			index1 = 2;
			index2 = 3;
		}

		int posX = worker[index1].getPositionX();
		int posY = worker[index1].getPositionY();

		for (int row = posX - 1; row <= posX + 1; row++) {
			for (int col = posY - 1; col <= posY + 1; col++) {
				if (!((row == posX) && (col == posY))) {
					if (col >= 0 && row >= 0 && row < board.length && col < board.length) {
						if (worker[index1].move(row, col, this, true)) {

							return false; // if worker 1 can move, not trapped
						}
					}
				}
			}
		}

		posX = worker[index2].getPositionX();
		posY = worker[index2].getPositionY();

		for (int row = posX - 1; row <= posX + 1; row++) {
			for (int col = posY - 1; col <= posY + 1; col++) {
				if (!((row == posX) && (col == posY))) {
					if (col >= 0 && row >= 0 && row < board.length && col < board.length) {
						if (worker[index2].move(row, col, this, true)) {

							return false; // if worker 2 can move, not trapped
						}
					}
				}
			}
		}

		return true;
	}

	public void reset() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = "";
			}
		}
	}

	public Worker[] getWorkers() {
		return worker;
	}

	public String[][] getBoard() {
		return board;
	}

	public void setBoard(int positionX, int positionY, String x) {
		board[positionX][positionY] = x;
	}

}
