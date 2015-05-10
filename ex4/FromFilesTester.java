package oop.ex4.data_structures;

import java.io.File;
import java.util.concurrent.TimeUnit;


/**
 * Tests from files using the downlisted conventions:
 *
 * by Ben Asaf.
 */
public class FromFilesTester {

    private static AvlTree myTree;  // The general AvlTree type variable that the tester works with.

    private static final String FULL_PATH = "./src/oop/ex4/data_structures/tests/";
    private static final String RELATIVE_PATH = "./tests/";

    private static final String PATH_CHOSEN = RELATIVE_PATH;  // THIS PATH WILL BE USED TO FIND TEST FOLDER.



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

    // Global trackers:
    private static int currentFile = 0;  // Counter to keep current file updated.
    private static int currentLine = 1;  // Counter to keep current line updated.


    /**
     * Loads all the files, lines and splits to words.
     * @throws Exception - Throws a nuke.
     */
    public static void getAllTestsFiles() throws Exception{
        try{
            File folder = new File(PATH_CHOSEN);
            File[] filesList = folder.listFiles();
            fileNames = new String[filesList.length];
            test = new String[filesList.length][MAX_LINES][MAX_WORDS];
            for (int file = 0; file<test.length; file++){
                fileNames[file] = filesList[file].getName();
                String[] lines = Ex3Utils.fileToLines(PATH_CHOSEN + filesList[file].getName());
                for (int line = 0; line < lines.length; line++){
                    String[] words = lines[line].split(" ");
                    for (int word = 0; word < words.length; word++){
                        test[file][line][word] = words[word];
                    }
                }
            }
        }catch (Exception e){
            printError("");
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
            if ( myTree.findMinNodes(values[0]) != values[1]){
                printError("Failed at: 'findMinNodes'" + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine );
            }
        } catch (Exception e){
            printError("Failed at: 'findMinNodes'" + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine );
        }

    }

    /**
     * Runs the default constructor from the AvlTree.
     *
     */
    private static void add(String[] line){
        int value;
        boolean result;
        try {
            value = Integer.parseInt(line[VALUE]);
            result = Boolean.parseBoolean(line[RESULT]);
            if ( myTree.add(value) != result){
                printError("Failed at 'add' method!\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine );
            }
        } catch (Exception e){
            printError("Failed at 'add' method!" + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine );
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
                if (blah == null) break;  // If null, no more numbers
                else counter++;
            }
            int[] values = new int[counter];  // values to construct with array
            for (int num = 0; num<counter; num++){  //  Parsing Strings to ints.
                values[num] = Integer.parseInt(line[num]);
            }
            myTree = new AvlTree(values);
        }catch (Exception e){
            printError("Failed to call data constructor." + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine);
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
            printError("Failed to call default constructor." + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine);
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
            if ( myTree.contains(value) != result){
                printError("Failed at contains method!" + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine );
            }
        } catch (Exception e){
            printError("Failed at contains method!" + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine );
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
            if ( myTree.delete(value) != result){
                printError("Failed at deleted method!" + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine );
            }
        } catch (Exception e){
            printError("Failed at deleted method!" + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine);
        }
    }

    /**
     * When printing error, it prints thumbs down and exits program.
     * @param message - Message to display upon exit.
     */
    private static void printError(String message){
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            //Handle exception
        }
        System.err.print("\n"+message);
        printThumbsDown();
        exit();
    }

    /**
     * Exits program.
     */
    private static void exit(){
        System.exit(0);
    }

    /**
     * Prints thumbs up in ASCII art.
     */
    private static void printThumbsUp(){
        System.out.println("");
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

    /**
     * Prints thumbs down in ASCII art.
     */
    private static void printThumbsDown(){
        System.err.println("");
        System.err.println("         _,....._");
        System.err.println("        (___     `'-.__");
        System.err.println("       (____");
        System.err.println("       (____");
        System.err.println("       (____         ___");
        System.err.println("            `)   .-'`");
        System.err.println("            /  .'");
        System.err.println("           | =|");
        System.err.println("            \\_\\");
    }


    public static void main(String args[]){
        try{
            getAllTestsFiles();  // Loads all files,lines and words.
            currentFile = 0;  // Counter to keep current file updated.
            currentLine = 1;
            for (String[][] file: test){  // Iterates on files
                System.out.println("\n# Now testing: '" + fileNames[currentFile] + "'");  //Prints current file on
                System.out.print("* Current Line: " + currentLine + ", ");
                lineLoop: for (String[] line: file){  // Iterates on lines
                    for (String word: line){  // Iterates on words
                        if (word == null){  // Reached end of file/last line of file
                            break lineLoop;
                        }
                        else if (word.equals("#")){  // comment line - ignore.
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
                        break;
                    }
                    System.out.print(++currentLine +", ");  // Prints the continuation of the current line
                }
                System.out.println("");  // Prepares for net current file print.
                currentLine=1;  // Resets line.
                currentFile++;  // Just to keep current file name updated..
            }
        } catch(Exception e){
            printError("");
        }
        printThumbsUp();
    }
}