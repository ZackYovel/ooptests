package oop.ex4.data_structures;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.io.File;

import static org.junit.Assert.*;
public class AvlTreeTest {

    @Rule
    public Timeout globalTimeout = new Timeout(100000); // 10 seconds max per method tested

    int[] arrayOne = {1,5,10,15,30,59,90,100,150,160};  // 10 elements
    int[] arrayTwo = {90,100,150,1601,5,10,15,30,59,1500,36,11,41,48,57};  // 15 elements
    //
    int[] arrayThree = {81, 62, 57, 77, 96, 56, 99, 23, 79, 32, 53, 15, 18, 26, 75, 12, 22, 8, 89, 63, 0,
            73, 66, 85, 21, 36, 35, 25, 0, 42, 39, 37, 71, 45, 0, 83, 19, 5, 51, 78,  13, 3, 48, 58, 54, 72,
            24, 91, 52, 2, 41, 33, 100, 93, 95, 4, 74, 65, 0, 92, 80};  // 61 Elements

    private static AvlTree myTree;

    private static final String FULL_PATH = "./src/oop/ex4/data_structures/tests/";
    private static final String RELATIVE_PATH = "./tests/";
    private static final int MAX_WORDS = 20;
    private static final int MAX_LINES = 20;
    public static String[][][] test = null;


    private static final String CONSTRUCT = "";
    private static final String ADD = "add";
    private static final String CONTAINS = "contains";
    private static final String DELETE = "delete";
    private static final String SIZE = "size";
    private static final String MIN_NODES = "minNodes";




    public static void getAllTestsFiles() throws Exception{
        File folder = new File(FULL_PATH);
        File[] filesList = folder.listFiles();
        test = new String[filesList.length][MAX_LINES][MAX_WORDS];
        for (int file = 0; file<test.length; file++){
            String[] lines = Ex3Utils.fileToLines(FULL_PATH + filesList[file].getName());
            for (int line = 0; line < lines.length-1; line++){
                String[] words = lines[line].split(" ");
                for (int word = 0; word < words.length; word++){
                    test[file][line][word] = words[word];
                }
            }
        }
    }

    private static void findMinNodes(String[] line) throws Exception {
        int[] values = new int[2];
        try{
            values[0] = Integer.parseInt(line[1]);
            values[1] = Integer.parseInt(line[2]);
            assertEquals("Failed at: 'findMinNodes'", myTree.findMinNodes(values[0]), values[1]);
        } catch (Exception e){
            //null
        }

    }

    private static void add(String[] line){
        int value = 0;
        boolean result = false;
        try{
            value = Integer.parseInt(line[1]);
            result = Boolean.parseBoolean(line[2]);
            assertEquals("Failed at: 'add'", myTree.add(value), result);
        } catch (Exception e){
            // null
        }
    }

    private static void constructWithData(String[] line) {
        try{
            int counter=0;
            for (String blah: line){
                counter++;
            }
            int[] values = new int[counter];
            for (int num = 0; num<counter; num++){
                try{
                    values[num] = Integer.parseInt(line[num]);
                } catch (Exception e){
                    //null
                }
            }
            myTree = new AvlTree(values);
        }catch (Exception e){
            System.err.print("ERROR: Could call data constructor.");
        }
    }

    private static void constructDefault(){
        try{
            myTree = new AvlTree();
        }catch (Exception e){
            System.err.print("ERROR: Could call default constructor.");
        }
    }

    private static void contains(String[] line){
        int value;
        int result;
        try{
            value = Integer.parseInt(line[1]);
            result = Integer.parseInt(line[2]);
            assertEquals("Failed at: 'contains'", myTree.contains(value), result);
        } catch (Exception e){
            //null
        }
    }

    private static void delete(String[] line){
        int value;
        boolean result;
        try{
            value = Integer.parseInt(line[1]);
            result = Boolean.parseBoolean(line[2]);
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
            for (String[][] file: test){
                lineLoop: for (String[] line: file){
                    for (String word: line){
                        if (word == null){
                            break lineLoop;
                        }
                        else if (word.equals("#")){  // comment line
                            break;
                        }
                        else if (word.equals(CONSTRUCT)) {
                            constructDefault();
                        }
                        else if (word.equals(ADD)) {
                            add(line);
                        }
                        else if (word.equals(DELETE)) {
                            delete(line);
                        }
                        else if (word.equals(MIN_NODES)) {
                            findMinNodes(line);
                        }
                        else if (word.equals(CONTAINS)) {
                            contains(line);
                        }
                        else{  // If parsed the String into Integer successfully->Initialize tree with data
                            try{
                                int num = Integer.parseInt(word);  // If integer, will continue to next line
                                constructWithData(line);
                            } catch (NumberFormatException e) {
                                // not an integer!
                            }
                        }
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