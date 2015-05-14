package oop.ex4.data_structures;

import java.io.IOException;
import java.lang.reflect.Field;


/**
 * Edited by Ben Asaf to fit multiple classes using reflection.
 *
 * LAST UPDATED: 15/5/2015 00:30 PM
 * VERSION: V1.3
 */
public class TreePrinterStyleTwo {
    //////////////////////////////////////////////////////////////////////////////////////////////
    // DO NOT EDIT THESE:
    private static String nodeClassName;  // NAME OF THE NODE CLASS YOU ARE USING
    private static String getLeftVariableName;  // METHOD NAME FOR GETTING LEFT CHILD
    private static String getRightVariableName;  // METHOD NAME FOR GETTING RIGHT CHILD
    private static String getValueVariableName;  // METHOD NAME FOR GETTING DATA
    private static Class nodeClass = null;
    private static Field getLeftVariable = null;
    private static Field getRightVariable = null;
    private static Field getValueVariable = null;

    //////////////////////////////////////////////////////////////////////////////////////////////

    public TreePrinterStyleTwo(String className, String getLeftName, String getRightName, String getValueName){
        nodeClassName = className;
        this.getLeftVariableName = getLeftName;
        this.getRightVariableName = getRightName;
        this.getValueVariableName = getValueName;
        loadClassAndMethods();
    }
    public void printTree(Object node) throws IOException {
        try{
            Object rightNode = getRightVariable.get(node);
            if (rightNode != null) {
                printTree(true, "", rightNode);
            }
            printNodeValue(node);
            Object leftNode = getLeftVariable.get(node);
            if (leftNode != null) {
                printTree(false, "", leftNode);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void printNodeValue(Object node) throws IOException {
        try{
            Object nodeValue = getValueVariable.get(node);
            if (nodeValue == null) {
                System.out.print("<null>");
            } else {
                System.out.print((int) nodeValue);
            }
            System.out.print("\n");
        } catch (IllegalAccessException e) {
            System.err.println("ERROR: Could not access left or right variable field of Node!");
        }
    }
    // use string and not stringbuffer on purpose as we need to change the indent at each recursion
    private void printTree(boolean isRight, String indent, Object node) throws IOException {
        try{
            Object rightNode = getRightVariable.get(node);
            if (rightNode != null) {
                printTree(true, indent + (isRight ? "        " : " |      "), rightNode);
            }
            System.out.print(indent);
            if (isRight) {
                System.out.print(" /");
            } else {
                System.out.print(" \\");
            }
            System.out.print("----- ");
            printNodeValue(node);
            Object leftNode = getLeftVariable.get(node);
            if (leftNode != null) {
                printTree(false, indent + (isRight ? " |      " : "        "), leftNode);
            }
        } catch (IllegalAccessException e) {
            System.err.println("ERROR: Could not access left or right variable field of Node!");
        } catch (StackOverflowError e){
            System.err.println("ERROR: Went into crazy infinity loop");
            return;
        }
    }
    private static void loadClassAndMethods() {
        try{
            nodeClass = Class.forName(nodeClassName);
            getLeftVariable = nodeClass.getDeclaredField(getLeftVariableName);
            getRightVariable = nodeClass.getDeclaredField(getRightVariableName);
            getValueVariable = nodeClass.getDeclaredField(getValueVariableName);
        } catch (ClassNotFoundException e){
            System.err.println("Error, could not find such class.");
        } catch (NoSuchFieldException e) {
            System.err.println("ERROR: Could not find the nodes variable in Node Class!");
        }catch (StackOverflowError e){
            System.err.println("ERROR: Went into crazy infinity loop");
            return;
        }
    }
}
