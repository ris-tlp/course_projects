package com.company;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Represents the structure of the Trie.
 */
public class Trie {
    public TrieNode root;
    private int numberOfNodes;

    public Trie() {
        this.numberOfNodes = 0;
        this.root = new TrieNode();
    }

    /**
     * @param word Word entered by the user.
     * @return {@code true} if the Trie contains the word input by the user
     */
    public boolean contains(String word) {
        TrieNode currentNode = this.root;

        for (char letter : word.toCharArray()) {
            TrieNode subNode = currentNode.getLetters().get(letter);
            if (subNode == null) return false;
            currentNode = subNode;
        }
        return currentNode.isEndOfWord();
    }

    /**
     * Takes a word from the User and determines whether it is a Prefix or not
     *
     * @param prefix Word entered by the user
     * @return {@code true} if the word is a prefix to any other word
     */
    public boolean isPrefix(String prefix) {
        TrieNode currentNode = this.root;

        // Navigate to the TrieNode reference of the end of the word
        // Returns false if a letter in the word does not exist in the Trie
        for (char letter : prefix.toCharArray()) {
            TrieNode subNode = currentNode.getLetters().get(letter);
            if (subNode == null) return false;
            currentNode = subNode;
        }
        return !currentNode.getLetters().isEmpty() || currentNode.isEndOfWord();
    }


    /**
     * Takes a word from the user and inserts it into the Trie
     *
     * @param word Word entered by the user
     */
    public void insert(String word) {
        TrieNode currentNode = this.root;

        // Navigate to the TrieNode reference of the last existing letter in the Trie
        // of the word input by the user to avoid duplicating the letters
        for (char letter : word.toCharArray()) {
            TrieNode subNode = currentNode.getLetters().get(letter);

            // If the TrieNode reference to the letter does not exist, create and insert it
            // Increment the total number of nodes
            if (subNode == null) {
                subNode = new TrieNode(letter);
                currentNode.getLetters().put(letter, subNode);
                this.numberOfNodes++;
            }
            currentNode = subNode;
        }
        // Mark word as completed
        currentNode.setEndOfWord(true);
    }

    /**
     * Takes a word from the user and deletes it from the Trie
     *
     * @param word Word entered by the user.
     *             This word must already exist
     *             in the Trie to be deleted
     */
    public void delete(String word) {
        if (!contains(word)) {
            System.out.println("This word does not exist in the Trie.");
        } else {
            Stack<TrieNode> stack = new Stack<>();
            TrieNode currentNode = this.root;
            stack.add(currentNode); // Push the root reference into the stack

            // Takes all the TrieNode letter references that potentially need to be deleted
            // and pushes them to the stack
            for (char letter : word.toCharArray()) {
                currentNode = currentNode.getLetters().get(letter);
                stack.add(currentNode);
            }

            // Only unmark word if it is a prefix
            if (isPrefix(word) && !currentNode.getLetters().isEmpty()) {
                currentNode.setEndOfWord(false);
            }

            // The word is not a prefix
            else if (currentNode.getLetters().isEmpty()) {
                char currentLetter = stack.pop().getValue();
                currentNode = stack.pop();

                while (!stack.isEmpty()) {
                    word = word.substring(0, word.length() - 1); // current word after a letter has been removed

                    // Remove the letter from the current node
                    currentNode.getLetters().remove(currentLetter);
                    this.numberOfNodes--;

                    // Break out of the loop if the current word is a prefix
                    if (currentNode.isEndOfWord() || isPrefix(word)) break;
                    currentLetter = currentNode.getValue();
                    currentNode = stack.pop();
                }

                // If the letter in the root node is now empty, delete it
                if (this.root.getLetters().get(currentLetter) != null && this.root.getLetters().get(currentLetter).isEmpty()) {
                    this.root.getLetters().remove(currentLetter);
                    this.numberOfNodes--;
                }
            }
        }
    }

    /**
     * Finds all words that start with a specified prefix
     *
     * @param prefix Prefix entered by the user.
     *               Prefix can be a word as well.
     * @return {@code String[]} containing all the words that start with {@code prefix}
     */
    public String[] allWordsPrefix(String prefix) {
        ArrayList<String> allWords = new ArrayList<>();

        if (!this.isPrefix(prefix)) {
            System.out.println("No words found with this prefix");
            return null;
        } else {
            TrieNode prefixNode = this.root;

            // Navigates to the TrieNode reference of the last letter in the prefix
            for (char character : prefix.toCharArray()) {
                TrieNode nextNode = prefixNode.getLetters().get(character);
                prefixNode = nextNode;
            }

            // Adds the prefix if it is a word itself
            if (prefixNode.isEndOfWord()) allWords.add(prefix);

            // Gets all subsequent words that start with the prefix
            for (TrieNode letter : prefixNode.getLetters().values()) {
                fetchWords(allWords, prefix + letter.getValue(), letter);
            }
        }
        return allWords.toArray(new String[allWords.size()]);
    }

    /**
     * Prints all the words in the {@link Trie}
     *
     * @return {@code ArrayList<String>} containing all the words present in the Trie
     */
    public void printAll() {
        ArrayList<String> allWords = new ArrayList();

        for (TrieNode node : this.root.getLetters().values()) {
            if (node != null) {
                fetchWords(allWords, node.getValue() + "", node);
            }
        }

        if (allWords.size() == 0) System.out.println("Your Trie is empty.");
        else {
            for (String word : allWords) {
                System.out.print(String.format("(%s) ", word));
            }
            System.out.println();
        }
    }

    /**
     * Helper method for visiting words in the {@link Trie}
     *
     * @param allWords    {@code ArrayList<String>} containing all
     *                    the words found
     * @param currentWord Current word found in the recursive call
     * @param currentNode Reference to the current {@link TrieNode}
     *                    in the recursive call
     */
    public void fetchWords(ArrayList<String> allWords, String currentWord, TrieNode currentNode) {
        if (currentNode.isEndOfWord()) {
            allWords.add(currentWord);
        }

        for (TrieNode node : currentNode.getLetters().values()) {
            if (node != null) {
                fetchWords(allWords, currentWord + node.getValue(), node);
            }
        }
    }

    /**
     * @return {@code true} if the Trie has no words
     */
    public boolean isEmpty() {
        return this.root.getLetters().size() == 0;
    }

    /**
     * Returns the total number of nodes in the {@link Trie}
     *
     * @return {@code int}
     */
    public int size() {
        return this.numberOfNodes;
    }

    /**
     * Clears the Trie
     */
    public void clear() {
        this.root.getLetters().clear();
    }
}
