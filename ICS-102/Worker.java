package santorini;

public class Worker {
	private String name;
	private int positionX, positionY;

	public Worker(String name) {
		this.name = name;
	}

	public boolean placeWorker(int positionX, int positionY, SantoriniGame game) {
		String[][] board = game.getBoard();

		// checking if in bounds and a player isn't already placed there
		if (positionX >= 0 && positionY >= 0 && positionX < board.length && positionY < board[positionX].length
				&& board[positionX][positionY].equals("")) {

			this.positionX = positionX;
			this.positionY = positionY;

			System.out.println(this.name + " placed at (" + this.positionX + "," + this.positionY + ").\n");
			board[positionX][positionY] = this.getName();

			return true;
		}

		return false;

	}

	public String getName() {
		return name;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public boolean move(int newPositionX, int newPositionY, SantoriniGame game, boolean checkForMove) {

		String[] buildings = { "", "B", "BB", "BBB" };
		String board[][] = game.getBoard();
		Worker[] worker = game.getWorkers();

		String tileContent = board[this.positionX][this.positionY];

		// checking whether move is not out of bounds
		if (newPositionX >= 0 && newPositionY >= 0 && newPositionX < board.length && newPositionY < board.length) {
			// checking whether move target is an adjacent tile
			if (((newPositionX == positionX) || (Math.abs(positionX - newPositionX) == 1))
					&& ((newPositionY == positionY) || Math.abs(positionY - newPositionY) == 1)
					&& (!((newPositionX == positionX) && (newPositionY == positionY)))) {

				String newTileContent = board[newPositionX][newPositionY];

				// player can't move to a tile if it has a dome
				if (newTileContent.indexOf("D") >= 0) {
					return false;
				}

				for (int i = 0; i < worker.length; i++) {
					// player can't move to a tile if it has another player
					if (newTileContent.indexOf(worker[i].getName()) >= 0) {
						return false;
					}
				}

				for (int i = 0; i < buildings.length; i++) {
					if (tileContent.substring(0, tileContent.lastIndexOf("B") + 1).equals(buildings[i])) {

						for (int j = 0; j < buildings.length; j++) {

							if (newTileContent.substring(0, newTileContent.lastIndexOf("B") + 1).equals(buildings[j])) {
								if (j - i == 1) {

									// don't change position if only checking if trapped
									if (!checkForMove) {
										// removes player from current position
										game.setBoard(this.positionX, this.positionY,
												tileContent.substring(0, tileContent.indexOf("w")));
										// adds player to new position
										game.setBoard(newPositionX, newPositionY, (newTileContent + this.name));
										this.positionX = newPositionX;
										this.positionY = newPositionY;
									}

									return true;

								} else if (j - i <= 0) {

									// don't change position if only checking if trapped
									if (!checkForMove) { 
										// removes player from current position
										game.setBoard(this.positionX, this.positionY,
												tileContent.substring(0, tileContent.indexOf("w")));
										// adds player to new position
										game.setBoard(newPositionX, newPositionY, (newTileContent + this.name));
										this.positionX = newPositionX;
										this.positionY = newPositionY;
									}

									return true;
								}
							}
						}
					}
				}
			}

		} else {
			return false; // returns false if move is out of bounds
		}

		return false; // returns false if every check fails
	}

}
