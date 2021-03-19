package com.company;

import java.util.HashMap;
import java.util.Objects;

public class TrieNode {
    private HashMap<Character, TrieNode> letters;
    private char value;
    private boolean endOfWord;


    public TrieNode() {
        this.letters = new HashMap<>();
        this.endOfWord = false;
    }

    public TrieNode(char value) {
        this.letters = new HashMap<>();
        this.endOfWord = false;
        this.value = value;
    }

    /**
     * @return {@code HashMap<Character, TrieNode} containing all the letters in the {@link TrieNode}
     */
    public HashMap<Character, TrieNode> getLetters() {
        return this.letters;
    }

    /**
     * @param letters {@code HashMap<Character, TrieNode} containing
     *                the letters referenced by this {@link TrieNode}
     */
    public void setLetters(HashMap<Character, TrieNode> letters) {
        this.letters = letters;
    }

    /**
     * @return {@code true} if the current {@link TrieNode}
     * is the end of a word
     */
    public boolean isEndOfWord() {
        return this.endOfWord;
    }

    /**
     * @param endOfWord {@code boolean} containing the state of the word
     */
    public void setEndOfWord(boolean endOfWord) {
        this.endOfWord = endOfWord;
    }

    /**
     * @return {@code char} containing the letter of the {@link TrieNode}
     */
    public char getValue() { return this.value; }

    /**
     * @param value {@code char} containing the letter of the {@link TrieNode}
     */
    public void setValue(char value) { this.value = value; }

    /**
     * @return {@code true} if the node has no existing letters
     */
    public boolean isEmpty() {
        return this.letters.isEmpty();
    }

    @Override
    public String toString() {
        return "TrieNode{" +
                "letters=" + this.letters +
                ", endOfWord=" + this.endOfWord +
                '}';
    }
}
