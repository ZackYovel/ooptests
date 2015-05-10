package oop.ex4.data_structures;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


/**
 * Tests from files using the downlisted conventions:
 *
 * by Ben Asaf.
 */
public class FromFilesTester {

    private static AvlTree myTree;  // The general AvlTree type variable that the tester works with.

    private static final String DEFAULT_PATH = "src\\oop\\ex4\\data_structures\\tests\\";   // DEFAULT PATH

    private static final Scanner userInput = new Scanner(System.in);

    private static boolean wasConstructedAlready = false;  // Do not touch.

    private static final int MAX_WORDS = 50;  // Maximum words per line. example: add 1 true (3). etc..
    private static final int MAX_LINES = 100;  // Maximum lines of commands in a file.
    public static String[][][] test = null;  // Files, Lines, Words - 3d array.
    private static String[] fileNames = null;

    // Commands used:
    private static final String DATA_CONSTRUCT = "data";  // oop format
    private static final String CONSTRUCT = "";  // OOP format
    private static final String CONSTRUCT2 = "c"; // Custom format
    private static final String COPY = "copy"; // oop format
    private static final String ADD = "add";  // oop
    private static final String CONTAINS = "contains";  // oop
    private static final String DELETE = "delete";  // oop
    private static final String SIZE = "size";  // oop
    private static final String MIN_NODES = "minNodes"; // oop

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
    public static void getAllTestsFiles(String path) throws Exception{
        try{
            File folder = new File(path);
            File[] filesList = folder.listFiles();

            fileNames = new String[filesList.length];  // To display file names.

            test = new String[filesList.length][MAX_LINES][MAX_WORDS];  // Initializing the test array.

            for (int file = 0; file<test.length; file++){  // Iterating on files.
                fileNames[file] = filesList[file].getName();
                String[] lines = Ex3Utils.fileToLines(path+fileNames[file]);
                for (int line = 0; line < lines.length; line++){  // Iterating on lines in files.
                    if (line == MAX_LINES){  // To PREVENT OUTOFARRAY BOUNDS
                        break;
                    }
                    String[] words = lines[line].split(" ");
                    for (int word = 0; word < words.length; word++){  // Iterating on words in lines in files
                        if (word == MAX_WORDS){  // To PREVENT OUTOFARRAY BOUNDS
                            break;
                        }
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
                printError("Failed at: 'findMinNodes'" + "\nFile: " + fileNames[currentFile] + "\nLine: " +
                        currentLine + "\nExpected: " + values[1] + ", Actual: " +values[0]);
            }
        } catch (Exception e){
            printError("EXCEPTION at: 'findMinNodes'" + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine );

        }
    }

    /**
     * Runs the default constructor from the AvlTree.
     *
     */
    private static void add(String[] line){
        int value = 0;
        boolean result = false;
        try {
            value = Integer.parseInt(line[VALUE]);
            result = Boolean.parseBoolean(line[RESULT]);
            if ( myTree.add(value) != result){
                printError("EXCEPTION at 'add' method!" + "\nFile: " + fileNames[currentFile] + "\nLine: " +
                        currentLine + "\nExpected: " + result + ", Actual: " + value);
            }
        } catch (Exception e){
            printError("EXCEPTION at 'add' method!" + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine);

        }
    }

    /**
     * Runs the size method from the AvlTree.
     *
     * @param line - String[] from the current line working on from file.
     */
    private static void size(String[] line){
        int actual = 0;
        int result = 0;
        try{
            result = Integer.parseInt(line[VALUE]);
            actual = myTree.size();
            if (actual != result){
                printError("Failed at: 'size'" + "\nFile: " + fileNames[currentFile] + "\nLine: " +
                        currentLine + "\nExpected: " + result + ", Actual: " + actual);
            }
        } catch (Exception e){
            printError("EXCEPTION at: 'size'" + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine);
        }
    }

    /**
     * Copies an AVL Tree to another.
     */
    private static void copy(){
        try{
            AvlTree myTree2 = new AvlTree(myTree);
        }catch (Exception e){
            printError("EXCEPTION when trying to copy AVL Tree using constructor." + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine);
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
                else if (blah == DATA_CONSTRUCT) continue;  // if data cmd.
                else counter++;
            }
            int[] values = new int[counter];  // values to construct with array
            for (int num = 1; num<counter; num++){  //  Parsing Strings to ints.
                values[num-1] = Integer.parseInt(line[num]);
            }
            myTree = new AvlTree(values);
        }catch (Exception e){
            printError("EXCEPTION at call to data constructor." + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine);
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
            printError("EXCEPTION at call to default constructor." + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine);
        }
    }

    /**
     * Runs the contains method from the AvlTree.
     *
     * @param line - String[] from the current line working on from file.
     */
    private static void contains(String[] line){
        int value = 0;
        int result = 0;
        int actual = 0;
        try{
            value = Integer.parseInt(line[VALUE]);
            result = Integer.parseInt(line[RESULT]);
            actual = myTree.contains(value);
            if ( actual != result){
                printError("Failed at contains method!" + "\nFile: " + fileNames[currentFile] + "\nLine: " +
                        currentLine + "\nExpected: " + result + ", Actual: " + actual);

            }
        } catch (Exception e){
            printError("EXCEPTION at contains method!" + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine );
        }
    }

    /**
     * Runs the delete method from the AvlTree.
     *
     * @param line - String[] from the current line working on from file.
     */
    private static void delete(String[] line){
        int value = 0;
        boolean result = false;
        try{
            value = Integer.parseInt(line[VALUE]);
            result = Boolean.parseBoolean(line[RESULT]);
            if ( myTree.delete(value) != result){
                printError("Failed at deleted method!" + "\nFile: " + fileNames[currentFile] + "\nLine: " +
                        currentLine + "\nExpected: " + result + ", Actual: " + value);

            }
        } catch (Exception e){
            printError("EXCEPTION at deleted method!" + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine );
        }
    }

    /**
     * When printing error, it prints thumbs down and exits program.
     * @param message - Message to display upon exit.
     */
    private static void printError(String message){
        try {
            TimeUnit.MILLISECONDS.sleep(800);
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

    private static String takeInputFromUser(){
        System.out.print("Enter path to tests folder: ");
        String path = userInput.nextLine();
        if (path.charAt(path.length()-1) != '/' && path.charAt(path.length()-1) != '\\' ){
            path += "\\";
        }
        return path;
    }


    public static void main(String args[]){
        String path = DEFAULT_PATH;
        try{
            try{
                File folder = new File(path);
                File[] filesList = folder.listFiles();
                if (filesList == null){
                    throw new Exception();
                }
            }catch (Exception e){
                System.err.println("Couldn't find tests folder using: "+ "'" + path + "'");
                path = takeInputFromUser();
            }
            // STARTS HERE.
            getAllTestsFiles(path);  // Loads all files,lines and words.
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
                        else if (word.equals(CONSTRUCT) || word.equals(CONSTRUCT2)) {  // "" - Construct default
                            if (!wasConstructedAlready){
                                constructDefault();
                                wasConstructedAlready = true;
                            }
                            else System.err.println("Warning: Tried to call constructor, or was it a blank line?" +
                                    ". It's ok tho, chill.");
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
                        else if (word.equals(COPY)){  // copy avl tree
                            copy();  // Takes previous built tree (myTree) and puts in myTree2.
                        }
                        else if (word.equals(DATA_CONSTRUCT)){  // CUSTOM COMMAND.
                            if (!wasConstructedAlready){
                                constructWithData(line);
                                wasConstructedAlready = true;
                            }
                            else System.err.println("Warning: Tried to call constructor, or was it a blank line?" +
                                    ". It's ok tho, chill.");
                        }
                        else if (word.equals(SIZE)){
                            size(line);
                        }
                        else{  // If none of them: data constructor. will try to parse the string into int.
                            try{  // THIS PART IS FOR THE OOP CONSTRUCTION WITH DATA. ONLY INTS.
                                int num = Integer.parseInt(word);  // If integer, will continue to next line
                                constructWithData(line);  // It is integer, call data constructor.
                            } catch (NumberFormatException e) {  // Not integers, not data constructor.
                                printError("\nNot a valid command! " + "'" + word + "'" + "\nIn line: " + line[currentLine]);
                            }
                        }
                        break;
                    }
                    System.out.print(++currentLine +", ");  // Prints the continuation of the current line
                }
                System.out.println("");  // Prepares for net current file print.
                currentLine=1;  // Resets line.
                currentFile++;  // Just to keep current file name updated..
                wasConstructedAlready = false;
            }
        } catch(Exception e){
            printError("");
        }
        printThumbsUp();
    }
}