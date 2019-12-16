/**
 * Information and Computer Science (ICS) Department
 * College of Computer Sciences and Engineering (CCSE)
 * King Fahd University of Petroleum and Minerals - KFUPM
 * Dhahran, Saudi Arabia  
 * 
 * Course - ICS 102 / Term 182
 * 
 * @author Omar Pervez Khan, Farhan Mohammed Abdul Qadir and Abdul Jawaad
 *
 */


package santorini;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		Scanner file = new Scanner(new FileInputStream("text.txt"));
		Scanner scan = new Scanner(System.in);
		SantoriniGame game = new SantoriniGame();
		do {
			game.reset();

			String Player1 = file.next();
			String Player2 = file.next();

			String Players[] = { Player1, Player2 };

			int positionX, positionY;
			int newPositionX, newPositionY;
			int counter = 0;

			System.out.println(game);
			System.out.println("__________________________________________________________________\n");
			Worker[] workers = game.getWorkers();

			for (int i = 0; i < 4; i++) {
				int j = 0;
				int name = 0;
				if (i > 1)
					name = 1;
				System.out.println(Players[name] + ", please enter valid coordinates for your workers.");
				do {
					String workerName = file.next();
					positionX = file.nextInt();
					positionY = file.nextInt();
					if (workers[i].getName().equals(workerName)) {
						if (workers[i].placeWorker(positionX, positionY, game)) {
							System.out.println(game);
							j = 1;
						} else {
							System.out.println("Invalid. Please try again.");
						}
					}
				} while (j != 1);
				System.out.println("__________________________________________________________________\n");
			}

			String worker;
			do {
				int w = -1;
				System.out.println(Players[counter % 2] + ", do you want to move w" + ((counter % 2) + 1) + "1 or w"
						+ ((counter % 2) + 1) + "2?");
				worker = file.next();

				if (worker.equalsIgnoreCase("w11"))
					w = 0;
				else if (worker.equalsIgnoreCase("w12"))
					w = 1;
				else if (worker.equalsIgnoreCase("w21"))
					w = 2;
				else if (worker.equalsIgnoreCase("w22"))
					w = 3;
				else
					System.out.println("Invalid name.");

				boolean move;
				boolean build = true;
				do {
					System.out.println(Players[counter % 2] + ", please enter valid coordinates.");
					System.out.println("Moving " + workers[w].getName() + " to (" + workers[w].getPositionX() + ","
							+ workers[w].getPositionY() + ").\n");

					newPositionX = file.nextInt();
					newPositionY = file.nextInt();

					move = workers[w].move(newPositionX, newPositionY, game, false);
					if (!move)
						System.out.println("Invalid. Please try again.");
					System.out.println(game);

				} while (!move);

				do {
					System.out
							.println(Players[counter % 2] + ", please enter the coordinates where you want to build.");
					System.out.println(
							workers[w].getName() + " is building at (" + newPositionX + "," + newPositionY + ").\n");
					if (game.hasWon(worker)) {
						file.nextInt();
						file.nextInt();
					} else {
						newPositionX = file.nextInt();
						newPositionY = file.nextInt();
					}
					if (!game.hasWon(worker)) {
						build = game.build(newPositionX, newPositionY, w);
					}

					if (!build)
						System.out.println("Invalid. Please try again.");
					System.out.println(game);
				} while (!build);

				if (game.hasWon(worker)) {

					String oppositeWorker = "";
					if (worker.indexOf("w1") >= 0) {
						oppositeWorker = "w2";
					} else {
						oppositeWorker = "w1";
					}
					if (game.isTrapped(oppositeWorker))
						System.out.println(Players[(counter + 1) % 2] + " is trapped.");
					else
						System.out.println(worker + " has reached the top of a 3 level building.");

					System.out.println(Players[counter % 2] + " has won the game in " + counter + " moves.");
				}
				counter++;

				System.out.println("__________________________________________________________________\n");
			} while (!game.hasWon(worker));

			System.out.println("Would you like to play again? Enter 1 to reset the board or -1 to exit.");
		} while (scan.nextInt() == 1);

		System.out.println("Thank you for playing.");

		scan.close();
		file.close();
	}

}
