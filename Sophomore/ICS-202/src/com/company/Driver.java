/**
 * Information and Computer Science (ICS/SWE) Department
 * College of Computer Sciences and Engineering (CCSE)
 * King Fahd University of Petroleum and Minerals - KFUPM
 * Dhahran, Saudi Arabia
 * <p>
 * Course - ICS 202 / Term 192 - Data Structures
 *
 * @author Omar Pervez Khan
 */

package com.company;

public class Driver {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();

        while (!ui.isTerminated()) {
            printMenu();
            int choice = ui.input.nextInt();

            switch (choice) {
                case 1:
                    ui.emptyTrie();
                    break;
                case 2:
                    ui.initialCreation();
                    break;
                case 3:
                    ui.insertWord();
                    break;
                case 4:
                    ui.deleteWord();
                    break;
                case 5:
                    ui.listPrefixWords();
                    break;
                case 6:
                    ui.sizeTrie();
                    break;
                case 7:
                    ui.printTrie();
                    break;
                case 8:
                    ui.checkContains();
                    break;
                case 9:
                    ui.checkPrefix();
                    break;
                case 10:
                    ui.input.close();
                    ui.end();
                    break;
                default:
                    System.out.println("Invalid selection!");
            }
            System.out.println();
            System.out.println();
        }
    }

    public static void printMenu() {
        System.out.print(
                String.join("\n",
                        "\n\n=================================================",
                        "1) Create an empty Trie",
                        "2) Create a trie with initial letters",
                        "3) Insert a word",
                        "4) Delete a word",
                        "5) List all words that begin with a prefix",
                        "6) Size of the Trie",
                        "7) Print all words in the Trie",
                        "8) Check whether a word exists in the Trie",
                        "9) Check whether a word is a prefix in the Trie",
                        "10) End the program",
                        "=================================================\n\n",
                        "Enter your choice: "
                )
        );
    }

}
