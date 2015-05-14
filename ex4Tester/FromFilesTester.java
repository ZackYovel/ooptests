package oop.ex4.data_structures;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * Tests from files using the downlisted conventions:
 *
 * by Ben Asaf.
 *
 * LAST UPDATED: 15/5/2015 00:30 PM
 * VERSION: V1.3
 */
public class FromFilesTester {
    // Printer style constants
    //private static final int STYLE_ONE = 1;  // Less accurate. Starts from top to bottom - Disabled cuz its too lame.
    private static final int STYLE_TWO = 2;  // More accurate Goes from left to right
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////// YOU CAN EDIT STUFF IN-HERE: /////////////////////////////////////////
    // Path related:
    private static final String DEFAULT_PATH = "C:\\tests\\";   // DEFAULT PATH
    /////////// PRINT TREE RELATED:
    // ROOT OF TREE RELATED:
    private static final String TREE_ROOT_VARIABLE_NAME = "rootTree";  // What is the name of the variable that holds the root tree?
    private static final String ROOT_OF_TREE_VARIABLE_LOCATION = "Tree";  // In what class is the variable declared? Father of AvlTree? in AvlTree?
    // NODE OF TREE RELATED:
    private static final String NODE_CLASS_NAME = "TreeNode";  // Name of the class you are using for the Nodes in the tree
    private static final String GET_LEFT_VARIABLE_NAME = "left";  // Name of the variable you are using to to hold left node
    private static final String GET_RIGHT_VARIABLE_NAME = "right";  // Name of the variable you are using to to hold right node
    private static final String GET_VALUE_VARIABLE_NAME = "value";  // Name of the variable you are using to to hold the value
    //////////
    private static final int TREE_PRINTER_STYLE = STYLE_TWO;  // CHOOSE STYLE_ONE or STYLE_TWO TO CHANGE PRINT STYLE
    ////////////////////////////// END OF EDITABLE STUFF //////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // Project related:
    private static final String AVL_TREE_CLASS_NAME = "AvlTree"; // Do not touch as for this exercise.
    //////////// PATH RELATED:
    private static final String PACKAGE = "oop.ex4.data_structures.";  // DEFAULT PACKAGE
    ///////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // Commands used:
    private static final String DATA_CONSTRUCT = "data";  // custom format
    private static final String CONSTRUCT2 = "c"; // Custom format
    private static final String ITERATOR = "iter"; // custom format
    private static final String INITIALIZE = "init"; // Custom format
    private static final String PRINT = "print"; // Custom format
    private static final String STRESS_ADD = "stress"; // Custom format
    private static final String STRESS_DELETE = "stressdel"; // Custom format
    private static final String WRECK = "wreck"; // Custom format
    private static final String CONSTRUCT = "";  // OOP format
    private static final String COPY = "copy"; // oop format
    private static final String ADD = "add";  // oop
    private static final String CONTAINS = "contains";  // oop
    private static final String DELETE = "delete";  // oop
    private static final String SIZE = "size";  // oop
    private static final String MIN_NODES = "minNodes"; // oop
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    private static final int MAX_WORDS = 150;  // Maximum words per line. example: add 1 true (3). etc..
    private static final int MAX_LINES = 200;  // Maximum lines of commands in a file.

    private static boolean wasConstructedAlready = false;  // Do not touch.
    private static AvlTree myTree;  // The general AvlTree type variable that the tester works with.
    private static String[][][] test = null;  // Files, Lines, Words - 3d array.
    private static String[] fileNames = null;  // To display file names.
    private static boolean isNewLineNeeded = false;  // Used to display proper current line after print tree.
    private static final Scanner userInput = new Scanner(System.in);


    // Some suggestions to possible exceptions:
    private static final String ITERATOR_POSSIBLE_CAUSES = "\n\nPossible Causes: " +
            "\n1) Make sure each nodes Parent is updated properly after deletion! It can cause weird stuff." +
            "\n  Use debugging, slow and painful process, I know. Follow the iterators process, value after value." +
            "\n2) Make sure your rotations are working correctly! That means checking each node reassigns parents" +
            " and childs properly!\n  Grab a piece of paper and go wild. Then degbug even more!\n";


    // Global trackers:
    private static int currentFile = 0;  // Counter to keep current file updated.
    private static int currentLine = 1;  // Counter to keep current line updated.

    // Some constants...
    private static final int MAX_CURRENT_LINE_PER_LINE = 30;
    private static final int ITERATOR_MAX_ITEMS_PER_LINE = 20;
    private static final int CMD = 0;
    private static final int VALUE = 1;
    private static final int RESULT = 2;
    private static final String CONSTRUCT_ATTEMPT_WARNING = "\n\nWarning: Tried to call constructor again.\n";
    private static final String NEW_CURRENT_LINE_INDENT = "\n                ";
    private static final String ITERATOR_LINE_INDENT = "             ";


    /**
     * Handles printing the tree.
     */
    private static void printTree(){
        try{
            System.out.println("\n");  // To make sure tree print starts at new line
            Class rootVariableClass = Class.forName(PACKAGE + AVL_TREE_CLASS_NAME);
            Field treeRootField;
            if (AVL_TREE_CLASS_NAME.equals(ROOT_OF_TREE_VARIABLE_LOCATION)) {  // If in the same class
                treeRootField = rootVariableClass.getDeclaredField(TREE_ROOT_VARIABLE_NAME);
            }
            else{  // If inherited. Assumes only one level backward.
                treeRootField = rootVariableClass.getSuperclass().getDeclaredField(TREE_ROOT_VARIABLE_NAME);
            }
            treeRootField.setAccessible(true);  // Sets to accessible if it was private.
            Object rootVariable = treeRootField.get(myTree);  // Extracts the value from the current AvlTree instance.
            /*if (TREE_PRINTER_STYLE == STYLE_ONE){
                TreePrinterStyleOne treePrinterStyleOne = new TreePrinterStyleOne(PACKAGE+NODE_CLASS_NAME, GET_LEFT_VARIABLE_NAME, GET_RIGHT_VARIABLE_NAME, GET_VALUE_VARIABLE_NAME);
                treePrinterStyleOne.printTree(rootVariable);
            }*/
            //else{ // STYLE_TWO
        //}
            TreePrinterStyleTwo treePrinterStyleTwo = new TreePrinterStyleTwo(PACKAGE+NODE_CLASS_NAME, GET_LEFT_VARIABLE_NAME, GET_RIGHT_VARIABLE_NAME, GET_VALUE_VARIABLE_NAME);
            treePrinterStyleTwo.printTree(rootVariable);
                System.out.println("\n");  // To make sure other prints start at new line
                isNewLineNeeded = true;
        }catch (NoSuchFieldException e){
            System.err.println("No Field by the name: '" + TREE_ROOT_VARIABLE_NAME + "' was found!");
        }catch (ClassNotFoundException e){
            System.err.println("No Class by the name: '" + ROOT_OF_TREE_VARIABLE_LOCATION + "' was found!");
        }catch (IllegalAccessException e){
            System.err.println("Could not get access to the roots tree variable!");
        }catch (NullPointerException e){
            System.err.println("ERROR: Could not print tree! Tree Variable is null");
        } catch (IllegalArgumentException e){
            System.err.println("ERROR: Could not print tree! Could not find value in the AvlTree instance that is in-use!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all the files, lines and splits to words.
     * @throws Exception - Throws a nuke.
     */
    private static void getAllTestsFiles(String path) throws Exception{
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
     * Runs the findMinNodes method from the AvlTree.
     *
     * @param line - String[] from the current line working on from file.
     */
    private static void findMinNodes(String[] line){
        int value;
        int expected;
        int actual;
        try{
            value = Integer.parseInt(line[VALUE]);
            expected = Integer.parseInt(line[RESULT]);
            actual = myTree.findMinNodes(value);
            if ( actual != expected){
                printError("Failed at: 'findMinNodes'" + "\nFile: " + fileNames[currentFile] + "\nLine: " +
                        currentLine + "\nUsed command: 'findMinNodes(" + value + ")'\nExpected: " + expected + ", Actual: " + actual);
            }
        }catch (NullPointerException e){
            printError("NULL POINTER EXCEPTION when trying to call 'findMinNodes' method!" + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine);
        } catch (Exception e){
            printError("EXCEPTION at: 'findMinNodes'" + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine );

        }
    }

    private static void wreckTest(){
        for (int i=0; i<3; i++){
            stressTestAdd();
            stressTestDelete();
        }
    }

    /**
     * Runs the add method from the AvlTree.
     *
     * @param line - String[] from the current line working on from file.
     */
    private static void add(String[] line){
        int value = 0;
        boolean actual = false;
        boolean expected = false;
        try {
            value = Integer.parseInt(line[VALUE]);
            expected = Boolean.parseBoolean(line[RESULT]);
            actual = myTree.add(value);
            if (actual != expected){
                printError("EXCEPTION at 'add' method!" + "\nFile: " + fileNames[currentFile] + "\nLine: " +
                        currentLine + "\nUsed command: 'add(" + value + ")'\nExpected: " + expected + ", Actual: " + actual);
            }
        }catch (NullPointerException e){
            printError("NULL POINTER EXCEPTION when trying to call 'add' method!" + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine);
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
        int expected = 0;
        try{
            expected = Integer.parseInt(line[VALUE]);
            actual = myTree.size();
            if (actual != expected){
                printError("Failed at: 'size'" + "\nFile: " + fileNames[currentFile] + "\nLine: " +
                        currentLine + "\nExpected: " + expected + ", Actual: " + actual);
            }
        }catch (NullPointerException e){
            printError("NULL POINTER EXCEPTION when trying to call 'size' method!" + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine);
        } catch (Exception e){
            printError("EXCEPTION at: 'size'" + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine +
                        "\n Possible reason:  Make sure you wrote the command 'size <int>'!" );
        }
    }

    /**
     * Copies an AVL Tree to another.
     */
    private static void copy(){
        try{
            myTree = new AvlTree(myTree);  // Now can continue working with myTree and experiment even more
        }catch (NullPointerException e){
            printError("NULL POINTER EXCEPTION when trying to copy AVL Tree using constructor." + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine);
        }catch (Exception e){
            printError("EXCEPTION when trying to copy AVL Tree using constructor." + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine);
        }
    }

    /**
     * Runs the data constructor from the AvlTree.
     */
    private static void constructWithData(String[] line) {
        try{
            int counter=0;
            for (String blah: line){
                if (blah == null) break;  // If null, no more numbers
                else if (blah.equals(DATA_CONSTRUCT)) continue;  // if data cmd.
                else counter++;
            }
            int[] values = new int[counter];  // values to construct with array
            int index = 0, index2 = 0;
            if (line[index].equals(DATA_CONSTRUCT)) index2 = 1;
            while (index < counter){
                values[index] = Integer.parseInt(line[index2]);
                index++;
                index2++;
            }
            myTree = new AvlTree(values);
        }catch (NullPointerException e){
            printError("NULL POINTER EXCEPTION when trying to call data constructor." + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine);
        }catch (Exception e){
            printError("EXCEPTION at call to data constructor." + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine);
        }
    }

    /**
     * Runs the default constructor from the AvlTree.
     */
    private static void constructDefault(){
        try{
            myTree = new AvlTree();
        }catch (NullPointerException e){
            printError("NULL POINTER EXCEPTION when trying to call to default constructor." + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine);
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
                        currentLine + "\nUsed command: 'contains(" + value + ")'\nExpected: " + result + ", Actual: " + actual);
            }
        }catch (NullPointerException e){
            printError("NULL POINTER EXCEPTION when trying to call to 'contains' method." + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine);
        }catch (Exception e){
            printError("EXCEPTION at 'contains' method!" + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine );
        }
    }

    /**
     * Runs the delete method from the AvlTree.
     *
     * @param line - String[] from the current line working on from file.
     */
    private static void delete(String[] line){
        int value = 0;
        boolean actual;
        boolean expected = false;
        try {
            value = Integer.parseInt(line[VALUE]);
            expected = Boolean.parseBoolean(line[RESULT]);
            actual = myTree.delete(value);
            if ( actual != expected){
                printError("Failed at 'deleted' method!" + "\nFile: " + fileNames[currentFile] + "\nLine: " +
                        currentLine + "\nUsed command: 'delete(" + value + ")'\nExpected: " + expected + ", Actual: " + actual);
            }
        }catch (NullPointerException e){
            printError("NULL POINTER EXCEPTION when trying to call 'deleted' method!" + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine);
        } catch (Exception e){
            printError("EXCEPTION at 'deleted' method!" + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine );
        }
    }

    /**
     * Prints the array with system error print.
     * @param array - int[] array
     */
    private static void printArrayGiven(int[] array){
        System.err.print("[");
        for (int i : array){
            System.err.print(i + ",");
        }
        System.err.print("]");
    }

    /**
     * This method checks the iterator implementation.
     */
    private static void iterator(){
        int treeSize = 0;
        int counter = 0;
        int[] treeValues = null;
        int[] treeValuesCopy = null;
        try{
            try{
                treeSize = myTree.size();
                treeValues = new int[treeSize];
            }catch (NullPointerException e){
                printError("NULL POINTER EXCEPTION at 'iterator' method!" + "\nFile: " + fileNames[currentFile]
                        + "\nLine: " + currentLine + "\nReason: Tried to call 'size' method and AvlTree returned null exception.");
            }
            for (int value : myTree) {
                treeValues[counter] = value;
                counter++;
            }
            if (counter != treeSize){
                throw new NoSuchElementException();
            }
            else{
                try{
                    treeValuesCopy = treeValues.clone();
                    MergeSort.sort(treeValues);  // Sorts to see if returned values in-order as expected.
                    System.out.print("\n\n" + ITERATOR_LINE_INDENT + "Returned Iterator Values: [");
                    for (int i=0; i<treeSize; i++){
                        System.out.print(treeValues[i]);
                        if (treeValues[i] != treeValuesCopy[i]){
                            throw new InputMismatchException();
                        }
                        if (i != treeSize - 1){
                            System.out.print(", ");
                        }
                        if (i != 0 && i%ITERATOR_MAX_ITEMS_PER_LINE == 0){
                            System.out.print("\n" + ITERATOR_LINE_INDENT + ITERATOR_LINE_INDENT
                                                +ITERATOR_LINE_INDENT);
                        }
                    }
                    System.out.println("]\n");
                    isNewLineNeeded = true;
                }catch (ArrayIndexOutOfBoundsException e){  // If out of bounds.. not all values returned.
                    throw new NoSuchElementException();
                }catch (InputMismatchException e){  // In-case values are not in-order
                    System.err.print("INPUT MISMATCH EXCEPTION at 'iterator' method!" + "\nFile: " +
                            fileNames[currentFile] + "\nLine: " + currentLine + "\nReason: Did not return values in-order!!" + "\nExpected: ");
                    printArrayGiven(treeValuesCopy);
                    System.err.print(", Actual: ");
                    printArrayGiven(treeValues);
                }catch (NullPointerException e){
                    throw new NullPointerException();
                }
            }
        }catch (NoSuchElementException e){
            printError("NO SUCH ELEMENT EXCEPTION at 'iterator' method!" + "\nFile: " +
                    fileNames[currentFile] + "\nLine: " + currentLine + "\nReason: Did not return all values!"+
                    "\nExpected: " + treeSize + ", Actual: " + counter + ITERATOR_POSSIBLE_CAUSES);
        }catch (NullPointerException e){
            printError("NULL POINTER EXCEPTION at 'iterator' method!" + "\nFile: " + fileNames[currentFile]
                    + "\nLine: " + currentLine + "\nPossible Reason: AvlTree 'iterator' method raises null exception" +
                    " somewhere.");
        }catch (RuntimeException e){
            printError("EXCEPTION at 'iterator' method!\nPossible Reason: Iterator did not return any values" +
                    " as expected." + "\nFile: " + fileNames[currentFile] + "\nLine: " + currentLine);
        }
    }

    /**
     * Handles getting values from user.
     * @return String - path to a folder.
     */
    private static String takeInputFromUser(){
        System.out.print("Enter path to tests folder: ");
        String path = userInput.nextLine();
        if (path.charAt(path.length()-1) != '/' && path.charAt(path.length()-1) != '\\' ){
            path += "\\";
        }
        try{  // Will try to see if path is valid.
            File folder = new File(path);
            File[] filesList = folder.listFiles();
            if (filesList == null){
                throw new Exception();
            }
        }catch (Exception e){  // If input is invalid...
            System.err.println("Invalid input, try again . . .\n");
            takeInputFromUser();
        }
        return path;
    }

    /**
     * Thanks to Dotan-Nir for the suggestions.
     * Stress Test.
     */
    private static void stressTestAdd(){
        try{
            int numberOfElements = 100000;
            int maxSize = 100000;
            Random randomGenerator = new Random();
            for (int i=0; i<numberOfElements; i++){
                int randValue = randomGenerator.nextInt(maxSize);
                myTree.add(randValue);
            }
        }catch (NullPointerException e) {
            printError("NULL POINTER EXCEPTION at 'stress add' method!" + "\nFile: " + fileNames[currentFile]
                    + "\nLine: " + currentLine + "\nPossible Reason: Your tree couldn't handle it, collapsed and returned NULL! DEBUG PLEASE!");
        }
    }

    /**
     * Thanks to Dotan-Nir for the suggestions.
     * Stress Test.
     */
    private static void stressTestDelete(){
        int treeSize = 0;
        List<Integer> valuesDeleted = new ArrayList<>();
        int lastTriedToDeleteValue = 0;
        try{
            Random randomGenerator = new Random();
            treeSize = myTree.size();
            if (treeSize == 0) throw new InputMismatchException();
            int[] valuesToDelete = new int[treeSize];

            Iterator<Integer> iter = myTree.iterator();
            for (int i=0; i<treeSize; i++){
                if (iter.hasNext()){
                    valuesToDelete[i] = iter.next();
                }
                else if(i < treeSize-1){  // Last run of for loop. shouldn't be true if iterator returns all items.
                    throw new Exception();
                }
            }
            if (treeSize/2 > 0) treeSize /= 2;
            /*for (int i = 0; i<treeSize; i++){
                valuesDeleted[i] = -1;
            }*/
            for (int i = 0; i < treeSize; i++){
                int randValue = randomGenerator.nextInt(treeSize);
                if (valuesToDelete[randValue] != 0){  // if its an unused array cell
                    if (myTree.delete(valuesToDelete[randValue]) == false){  // IF IT COULDN't DELETE THEN ITS A LIE!
                        lastTriedToDeleteValue = valuesToDelete[randValue];
                        throw new RuntimeException();
                    }
                    valuesDeleted.add(valuesToDelete[randValue]);
                    valuesToDelete[randValue] = 0;  // assigning 0 to a deleted spot in an array
                }
            }
        }catch (InputMismatchException e){
            System.err.println("ERROR: Could not run 'stressdel' command since tree is empty! size == 0." + "\nFile: " + fileNames[currentFile]
                    + "\nLine: " + currentLine);
        } catch (RuntimeException e){
            System.err.print("\nERROR AT: 'stressdel' method! Tried to delete a value that exists but tree said it didnt." + "\nFile: " + fileNames[currentFile]
                    + "\nLine: " + currentLine + "\n\nTried to remove the value: '" + lastTriedToDeleteValue + "'. \nHere is the iterator from the tree as for now: ");
            delayPrint(500);
            iterator();
            delayPrint(500);
            System.err.print("\nAnd this is the values deleted so far:");
            System.err.print("[");
            for (int value : valuesDeleted){
                System.err.print(value+",");
            }
            System.err.print("]\n");
            System.err.print("My suggestion: Run the test few times until the array 'values deleted so far' is as low as it can be." +
                    "\nThat is for you to debug as less as painful as it can get.");
            printError("\n\nExiting.");
        } catch (Exception e) {
            printError("ERROR AT 'stressdel' method!" + "\nFile: " + fileNames[currentFile]
                    + "\nLine: " + currentLine + "\nPossible Reason: declared size '" + treeSize + "but received from iterator less than that!");
        }
    }

    private static void delayPrint(int milliseconds){
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);  // delaying for a bit, so it wont mix up with other lines.
        } catch (InterruptedException e) {
            //Handle exception
        }
    }

    /**
     * When printing error, it prints thumbs down and exits program.
     * @param message - Message to display upon exit.
     */
    private static void printError(String message){
        delayPrint(800);
        System.err.print("\n" + message);
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
        System.out.println("      Well Done!");
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
        System.err.println("   :-(");
    }

    /**
     * Prints progress. What file is now being tested and prints line 1.
     */
    private static void printProgress(){
        System.out.println("\n# Now testing: '" + fileNames[currentFile] + "'");  //Prints current file on
        System.out.print("* Current Line: " + currentLine + ", ");
    }

    public static void main(String args[]){
        long timeStart = System.nanoTime();
        String path = DEFAULT_PATH;
        try{
            //////////////////////////////////////////////////////////////////////////////////////////////
            // HANDLING INPUT SECTION/VALID PATH ASSURING STARTS HERE:
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
            // INPUT/BLAHBLAH ENDS HERE.
            /////////////////////////////////////////////////////////////////////////////////////////////
            // MAIN LOOP STARTS HERE:
            getAllTestsFiles(path);  // Loads all files,lines and words.
            currentFile = 0;  // Counter to keep current file updated.
            currentLine = 1;
            for (String[][] file: test){  // Iterates on files
                printProgress();
                lineLoop: for (String[] line: file){  // Iterates on lines
                    for (String word: line){  // Iterates on words
                        if (word == null){  // Reached end of file/last line of file
                            break lineLoop;
                        }
                        else if (word.equals("#") || !word.equals("") && word.charAt(0) == '#'){  // comment line - ignore.
                            break;
                        }
                        else if (word.equals(CONSTRUCT) || word.equals(CONSTRUCT2)) {  // "" - Construct default
                            if (!wasConstructedAlready){  // if encountered a 'blank line' or 'c' and already constructed
                                constructDefault();
                                wasConstructedAlready = true;
                            }
                            else{
                                System.err.println(CONSTRUCT_ATTEMPT_WARNING);
                                isNewLineNeeded = true;
                            }
                        }
                        else if (word.equals(ADD)) {  // "add" keyword
                            add(line);
                        }
                        else if (word.equals(INITIALIZE)){  // "init" keyword
                            constructDefault();
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
                        else if (word.equals(ITERATOR)){  // 'iterator' keyword
                            iterator();
                        }
                        else if (word.equals(COPY)){  // copy avl tree
                            copy();  // Takes previous built tree (myTree) and puts in myTree2.
                        }
                        else if (word.equals(DATA_CONSTRUCT)){  // CUSTOM COMMAND.
                            if (!wasConstructedAlready){
                                constructWithData(line);
                                wasConstructedAlready = true;
                            }
                            else System.err.println(CONSTRUCT_ATTEMPT_WARNING);
                        }
                        else if (word.equals(SIZE)){
                            size(line);
                        }
                        else if (word.equals(STRESS_ADD)){
                            stressTestAdd();
                        }
                        else if (word.equals(STRESS_DELETE)){
                            stressTestDelete();
                        }
                        else if (word.equals(WRECK)){
                            wreckTest();
                        }
                        else if (word.equals(PRINT)){
                            printTree();
                        }
                        else{  // If none of them: data constructor. Will now try to parse the string into int.
                            // if successful, its an integer. Else: NO IDEA WHAT IT IS.
                            try{  // THIS PART IS FOR THE OOP CONSTRUCTION WITH DATA. ONLY INTS.
                                int num = Integer.parseInt(word);  // If integer, will continue to next line
                                constructWithData(line);  // It is integer, call data constructor.
                                if (!wasConstructedAlready){
                                    constructWithData(line);
                                    wasConstructedAlready = true;
                                }
                            } catch (NumberFormatException e) {  // Not integers, not data constructor.
                                printError("\nNot a valid command! " + "'" + word + "'" + "\nIn line: " + line[currentLine]);
                            }
                        }
                        break;  // HERE FOR A REASON.
                    }
                    if (isNewLineNeeded){
                        System.out.print("Current line: ");
                        isNewLineNeeded = false;
                    }
                    else if (currentLine%MAX_CURRENT_LINE_PER_LINE == 0) System.out.print(NEW_CURRENT_LINE_INDENT);
                    System.out.print(++currentLine + ", ");  // Prints the continuation of the current line
                }
                System.out.println("");  // Prepares for next current file print.
                currentLine=1;  // Resets line.
                currentFile++;  // Just to keep current file name updated..
                wasConstructedAlready = false;
            }
        }catch(Exception e){  // Should not reach here
            printError("Woops! Something bad happened :-( \nReason unknown.");
        }
        printThumbsUp();  // IF PASSED ALL TESTS FILES :-)
        long difference = System.nanoTime() - timeStart;
        System.out.println("\nTime took: " + difference / 1000000 + " ms");
    }
}