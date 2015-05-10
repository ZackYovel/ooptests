package oop.ex4.data_structures;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.io.File;

import static org.junit.Assert.*;


/**
 * Tests from files using the downlisted conventions:
 *
 * by Ben Asaf.
 */
public class FromFilesTester {

    @Rule
    public Timeout globalTimeout = new Timeout(100000); // 10 seconds max per method tested

    private static AvlTree myTree;  // The general AvlTree type variable that the tester works with.

    private static final String FULL_PATH = "./src/oop/ex4/data_structures/tests/";
    private static final String RELATIVE_PATH = "./tests/";

    private static final int MAX_WORDS = 20;  // Maximum words per line. example: add 1 true (3). etc..
    private static final int MAX_LINES = 20;  // Maximum lines of commands in a file.
    public static String[][][] test = null;  // Files, Lines, Words - 3d array.
    private static String[] fileNames = null;

    // Commands used:
    private static final String CONSTRUCT = "";
    private static final String ADD = "add";
    private static final String CONTAINS = "contains";
    private static final String DELETE = "delete";
    private static final String SIZE = "size";
    private static final String MIN_NODES = "minNodes";

    // Some constants...
    private static final int CMD = 0;
    private static final int VALUE = 1;
    private static final int RESULT = 2;


    /**
     * Loads all the files, lines and splits to words.
     * @throws Exception - Throws a nuke.
     */
    public static void getAllTestsFiles() throws Exception{
        File folder = new File(FULL_PATH);
        File[] filesList = folder.listFiles();
        fileNames = new String[filesList.length];
        test = new String[filesList.length][MAX_LINES][MAX_WORDS];
        for (int file = 0; file<test.length; file++){
            fileNames[file] = filesList[file].getName();
            String[] lines = Ex3Utils.fileToLines(FULL_PATH + filesList[file].getName());
            for (int line = 0; line < lines.length; line++){
                String[] words = lines[line].split(" ");
                for (int word = 0; word < words.length; word++){
                    test[file][line][word] = words[word];
                }
            }
        }
    }

    /**
     * Runs the add method from the AvlTree.
     *
     * @param line - String[] from the current line working on from file.
     */
    private static void findMinNodes(String[] line){
        int[] values = new int[2];
        try{
            values[0] = Integer.parseInt(line[VALUE]);
            values[1] = Integer.parseInt(line[RESULT]);
            assertEquals("Failed at: 'findMinNodes'", myTree.findMinNodes(values[0]), values[1]);
        } catch (Exception e){
            //null
        }

    }

    /**
     * Runs the default constructor from the AvlTree.
     *
     */
    private static void add(String[] line){
        int value = 0;
        boolean result = false;
        try{
            value = Integer.parseInt(line[VALUE]);
            result = Boolean.parseBoolean(line[RESULT]);
            assertEquals("Failed at: 'add'", myTree.add(value), result);
        } catch (Exception e){
            // null
        }
    }

    /**
     * Runs the data constructor from the AvlTree.
     *
     */
    private static void constructWithData(String[] line) {
        try{
            int counter=0;
            for (String blah: line){
                if (blah == null) break;
                else counter++;
            }
            int[] values = new int[counter];
            for (int num = 0; num<counter; num++){
                try{
                    values[num] = Integer.parseInt(line[num]);
                } catch (Exception e){
                    //null
                }
            }
            try{
                myTree = new AvlTree(values);
            }catch (Exception e){
                //
            }
        }catch (Exception e){
            System.err.print("ERROR: Could not call data constructor.");
        }
    }

    /**
     * Runs the default constructor from the AvlTree.
     *
     */
    private static void constructDefault(){
        try{
            myTree = new AvlTree();
        }catch (Exception e){
            System.err.print("ERROR: Could not call default constructor.");
        }
    }

    /**
     * Runs the contains method from the AvlTree.
     *
     * @param line - String[] from the current line working on from file.
     */
    private static void contains(String[] line){
        int value;
        int result;
        try{
            value = Integer.parseInt(line[VALUE]);
            result = Integer.parseInt(line[RESULT]);
            assertEquals("Failed at: 'contains'", myTree.contains(value), result);
        } catch (Exception e){
            //null
        }
    }

    /**
     * Runs the delete method from the AvlTree.
     *
     * @param line - String[] from the current line working on from file.
     */
    private static void delete(String[] line){
        int value;
        boolean result;
        try{
            value = Integer.parseInt(line[VALUE]);
            result = Boolean.parseBoolean(line[RESULT]);
            assertEquals("Failed at: 'delete'", myTree.add(value), result);
        } catch (Exception e){
            //null
        }
    }

    public static void printThumbsUp(){
        System.out.println("             _");
        System.out.println("             \\`\\");
        System.out.println("             |= |");
        System.out.println("            /-  ;.---.");
        System.out.println("      _ __.'     (____)");
        System.out.println("       `         (_____)");
        System.out.println("       _'  ._ .' (____)");
        System.out.println("        `        (___)");
        System.out.println("      --`'------'`");
    }

    public static void printThumbsDown(){
        System.out.println("         _,....._");
        System.out.println("        (___     `'-.__");
        System.out.println("       (____");
        System.out.println("       (____");
        System.out.println("       (____         ___");
        System.out.println("            `)   .-'`");
        System.out.println("            /  .'");
        System.out.println("           | =|");
        System.out.println("            \\_\\");
    }
    @Test
    public void main(){
        try{
            getAllTestsFiles();
            int currentFile = 0;
            for (String[][] file: test){
                System.out.println("Now testing: " + fileNames[currentFile]);
                lineLoop: for (String[] line: file){
                    for (String word: line){
                        if (word == null){  // Reached end of file/last line of file
                            break lineLoop;
                        }
                        else if (word.equals("#")){  // comment line
                            break;
                        }
                        else if (word.equals(CONSTRUCT)) {  // "" - Construct default
                            constructDefault();
                        }
                        else if (word.equals(ADD)) {  // "add" keyword
                            add(line);
                        }
                        else if (word.equals(DELETE)) {  // "delete" keyword
                            delete(line);
                        }
                        else if (word.equals(MIN_NODES)) {  // 'minNodes' keyword
                            findMinNodes(line);
                        }
                        else if (word.equals(CONTAINS)) {  // 'contains' keyword
                            contains(line);
                        }
                        else{  // If none of them: data constructor. will try to parse the string into int.
                            try{
                                int num = Integer.parseInt(word);  // If integer, will continue to next line
                                constructWithData(line);  // It is integer, call data constructor.
                            } catch (NumberFormatException e) {  // Not integers, not data constructor.
                                // not an integer! (data constructor)
                            }
                        }
                        currentFile++;
                        break;
                    }
                }
            }
        } catch(Exception e){
            printThumbsDown();
        }
        printThumbsUp();
    }

    private void printProgress(String[][] file, String[] line, String word) {
        System.out.println("Now in file:" + file[0][1] + ",\nAt line:" + line + ",\nRunning command: " + line[0] + " " +line[1] + " " + line[2]);
    }


}