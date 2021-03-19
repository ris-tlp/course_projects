package com.company;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

public class UserInterface {
    private boolean terminated;
    private Trie trie;
    public Scanner input;
    private ArrayList<String> wordList;

    public UserInterface() {
        this.terminated = false;
        this.input = new Scanner(System.in);
        this.populateWordList();
    }

    /**
     * Handler for User Choice 1.
     * Creates an empty {@link Trie}
     */
    public void emptyTrie() {
        trie = new Trie();
        System.out.println("An Empty Trie has been created.");
    }

    /**
     * Handler for User Choice 2.
     * Creates a {@link Trie} with an initial set of words obtained from generating permutations of the given list
     * of letters
     *
     * @see #generatePermutations(String)
     */
    public void initialCreation() {
        trie = new Trie();

        System.out.print("Enter the list of letters you'd like to initially populate the Trie with: ");
        HashSet<String> subsets = generatePermutations(input.next().toUpperCase());

        for (String subset : subsets) {
            if (Collections.binarySearch(wordList, subset) > 0) {
                trie.insert(subset);
            }
        }
    }

    /**
     * Handler for User Choice 3.
     * Inserts a new word into the {@link Trie}
     */
    public void insertWord() {
        if (isNotInitialized()) {
            System.out.println("Please create your Trie using Choices 1 or 2 first.");
        } else {
            System.out.print("Enter the word you'd like to insert into the Trie: ");
            trie.insert(input.next().toUpperCase());
        }
    }


    /**
     * Handler for User Choice 4.
     * Deletes a pre-existing word from the {@link Trie}
     */
    public void deleteWord() {
        if (isNotInitialized()) {
            System.out.println("Please create your Trie using Choices 1 or 2 first.");
        } else {
            System.out.print("Enter the word you'd like to delete from the Trie: ");
            trie.delete(input.next().toUpperCase());
        }
    }

    /**
     * Handler for User Choice 5.
     * Prints all words starting with a provided Prefix
     */
    public void listPrefixWords() {
        if (isNotInitialized()) {
            System.out.println("Please create your Trie using Choices 1 or 2 first.");
        } else {
            System.out.print("Enter your prefix: ");
            String[] words = trie.allWordsPrefix(input.next().toUpperCase());

            if (words != null)
                for (String word : words)
                    System.out.println(word);

        }
    }

    /**
     * Handler for User Choice 6.
     * Prints the number of nodes in the {@link Trie}
     */
    public void sizeTrie() {
        if (isNotInitialized()) {
            System.out.println("Please create your Trie using Choices 1 or 2 first.");
        } else {
            System.out.println(String.format("The number of nodes in your Trie: %d", trie.size()));
        }
    }

    /**
     * Handler for User Choice 7.
     * Prints all the words present in the {@link Trie}
     */
    public void printTrie() {
        if (isNotInitialized()) {
            System.out.println("Please create your Trie using Choices 1 or 2 first.");
        } else {
            trie.printAll();
        }
    }

    /**
     * Handler for User Choice 8.
     * Prints whether a word exists in the {@link Trie}
     */
    public void checkContains() {
        if (isNotInitialized()) {
            System.out.println("Please create your Trie using Choices 1 or 2 first.");
        } else {
            System.out.print("Enter your word: ");

            if (trie.contains(input.next().toUpperCase())) {
                System.out.println("It exists in the Trie.");
            } else {
                System.out.println("It does not exist in the Trie.");
            }
        }
    }

    /**
     * Handler for User Choice 9.
     * Prints whether a word is a prefix in the {@link Trie}
     */
    public void checkPrefix() {
        if (isNotInitialized()) {
            System.out.println("Please create your Trie using Choices 1 or 2 first.");
        } else {
            System.out.print("Enter your word: ");

            if (trie.isPrefix(input.next().toUpperCase())) {
                System.out.println("It is a prefix.");
            } else {
                System.out.println("It is not a prefix");
            }
        }
    }

    /**
     * Handler for User Choice 10.
     * Ends the program.
     */
    public void end() {
        System.out.println("Exiting program.");
        System.exit(0);
    }


    /**
     * Populates {@link #wordList, wordList} with all words from the given dictionary
     */
    private void populateWordList() {
        wordList = new ArrayList<>();
        Scanner fileReader = null;

        try {
            fileReader = new Scanner(new FileInputStream("dictionary.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Dictionary not found.");
        }

        while (fileReader.hasNext()) {
            this.wordList.add(fileReader.next());
        }

        fileReader.close();
    }


    /**
     * Generates all possible unique permutations of all possible lengths of a word
     * Entry point for {@link #generatePermutationsRecursive(String, String, HashSet), generatePermutationsRecursive}
     *
     * @param word String input by the user
     * @return {@code HashSet<String>} containing all possible subsets of the word
     */
    private HashSet<String> generatePermutations(String word) {
        HashSet<String> permutations = new HashSet<>();
        generatePermutationsRecursive("", word, permutations);

        HashSet<String> subsets = new HashSet<>();

        for (String subset : permutations) {
            for (int i = 0; i < subset.length(); i++) {
                subsets.add(subset.substring(i));
            }
        }
        return subsets;
    }


    private void generatePermutationsRecursive(String permutation, String currentString, HashSet<String> permutations) {
        if (currentString.length() == 0) {
            permutations.add(permutation);
        }

        for (int i = 0; i < currentString.length(); i++) {
            String newSubset = permutation + currentString.charAt(i);
            String newCurrentString = currentString.substring(0, i) + currentString.substring(i + 1);
            generatePermutationsRecursive(newSubset, newCurrentString, permutations);
        }
    }

    /**
     * Get the state of instantiation of the Trie
     *
     * @return {@code null} if the Trie has not been created yet
     */
    private boolean isNotInitialized() {
        return trie == null;
    }

    /**
     * Gets the state of the User Interface
     *
     * @return {@code true} if the user has opted to quit the program
     */
    public boolean isTerminated() {
        return terminated;
    }

    /**
     * Sets the state of the User Interface
     *
     * @param terminated State of the interface
     */
    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }
}
