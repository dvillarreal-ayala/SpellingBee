import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Spelling Bee
 *
 * This program accepts an input of letters. It prints to an output file
 * all English words that can be generated from those letters.
 *
 * For example: if the user inputs the letters "doggo" the program will generate:
 * do
 * dog
 * doggo
 * go
 * god
 * gog
 * gogo
 * goo
 * good
 *
 * It utilizes recursion to generate the strings, mergesort to sort them, and
 * binary search to find them in a dictionary.
 *
 * @author Zach Blick, Damian Villarreal-Ayala
 *
 * Written on March 5, 2023 for CS2 @ Menlo School
 *
 * DO NOT MODIFY MAIN OR ANY OF THE METHOD HEADERS.
 */
public class SpellingBee {

    private String letters;
    private ArrayList<String> words;
    public static final int DICTIONARY_SIZE = 143091;
    public static final String[] DICTIONARY = new String[DICTIONARY_SIZE];

    public SpellingBee(String letters) {
        this.letters = letters;
        words = new ArrayList<String>();
    }

    // TODO: generate all possible substrings and permutations of the letters.
    //  Store them all in the ArrayList words. Do this by calling ANOTHER method
    //  that will find the substrings recursively.
    public void generate() {
        // YOUR CODE HERE — Call your recursive method!
        makeWords("", letters);
    }

    public void makeWords(String word, String letters)
    {
        if(letters.length() == 0)
        {
            return;
        }
        for(int i = 0; i < letters.length(); i++)
        {
            //Should be appending letters to word, not other way around.
            String newWord = word + letters.substring(i, i + 1);
            words.add(newWord);
            makeWords(newWord, letters.substring(0, i)+ letters.substring(i + 1));
        }


    }
    // TODO: Apply mergesort to sort all words. Do this by calling ANOTHER method
    //  that will find the substrings recursively.
    public void sort() {
        // YOUR CODE HERE
        words = mergeSort(0, words.size() - 1);

    }

    public ArrayList<String> mergeSort(int low, int high)
    {
        if(high == low)
        {
            ArrayList<String> newArray= new ArrayList<String>();
            newArray.add(words.get(low));
            return newArray;
        }
        int split = (low + high) / 2;
        ArrayList<String> array1 = mergeSort(low, split);
        ArrayList<String> array2 = mergeSort(split + 1, high);
        return merge(array1, array2);
    }

    public ArrayList<String> merge(ArrayList<String> arr1, ArrayList<String> arr2) {
        ArrayList<String> newArray = new ArrayList<String>();
        int i = 0;
        int j = 0;
        while (arr1.size() > i && arr2.size() > j) {
            if (arr1.get(i).compareTo(arr2.get(j)) < 0) {
                newArray.add(arr1.get(i));
                i++;
            } else {
                newArray.add(arr2.get(j));
                j++;
            }
        }
        if(i < arr1.size())
        {
            while(i < arr1.size())
            {
                newArray.add(arr1.get(i));
                i++;
            }
        }
        if(j < arr2.size())
        {
            while(j < arr2.size())
            {
                newArray.add(arr2.get(j));
                j++;
            }
        }
        return newArray;
    }

    // Removes duplicates from the sorted list.
    public void removeDuplicates()
    {
        int i = 0;
        while (i < words.size() - 1) {
            String word = words.get(i);
            if (word.equals(words.get(i + 1)))
                words.remove(i + 1);
            else
                i++;
        }
    }

    // TODO: For each word in words, use binary search to see if it is in the dictionary.
    //  If it is not in the dictionary, remove it from words.
    public void checkWords() {
        // YOUR CODE HERE
//        for(String word: words)
//        {
//            if(found(word) == false)
//            {
//                words.remove(word);
//            }
//        }
    }
    public boolean found(String s)
    {

        return false;
    }

    // Prints all valid words to wordList.txt
    public void printWords() throws IOException {
        File wordFile = new File("Resources/wordList.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(wordFile, false));
        for (String word : words) {
            writer.append(word);
            writer.newLine();
        }
        writer.close();
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    public SpellingBee getBee() {
        return this;
    }

    public static void loadDictionary() {
        Scanner s;
        File dictionaryFile = new File("Resources/dictionary.txt");
        try {
            s = new Scanner(dictionaryFile);
        } catch (FileNotFoundException e) {
            System.out.println("Could not open dictionary file.");
            return;
        }
        int i = 0;
        while(s.hasNextLine()) {
            DICTIONARY[i++] = s.nextLine();
        }
    }

    public static void main(String[] args) {

        // Prompt for letters until given only letters.
        Scanner s = new Scanner(System.in);
        String letters;
        do {
            System.out.print("Enter your letters: ");
            letters = s.nextLine();
        }
        while (!letters.matches("[a-zA-Z]+"));

        // Load the dictionary
        SpellingBee.loadDictionary();

        // Generate and print all valid words from those letters.
        SpellingBee sb = new SpellingBee(letters);
        sb.generate();
        sb.sort();
        sb.removeDuplicates();
        sb.checkWords();
        try {
            sb.printWords();
        } catch (IOException e) {
            System.out.println("Could not write to output file.");
        }
        s.close();
    }
}