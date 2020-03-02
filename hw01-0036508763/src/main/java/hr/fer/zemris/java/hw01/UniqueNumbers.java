package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Tree set. Primitive type integer is used as a value.
 *
 * All recursive methods expect that the left child has value less than its root and right child has value greater than its root.
 * Includes elementary methods which are needed to use a tree set.
 *
 * @author marko
 * @version 1.0.0
 */
public class UniqueNumbers {

    /**
     * Data structure containing integer as a value. Left and right children are TreeNode.
     */
    public static class TreeNode {
        TreeNode left;
        TreeNode right;
        int value;
    }

    /**
     * Recursively adds new tree node to existing root tree node.
     *
     * If the given value is less than the root value recursion is used with left child as root and the same value is passed.
     * Otherwise right child is used as a root with the same value.
     * @param root parent reference of a TreeNode.
     * @param value value to be added in a new TreeNode.
     * @return Returns parent root {@code TreeNode} which is used in a method call.
     */
    public static TreeNode addNode(TreeNode root, int value) {
        if (root == null) {
            root = new TreeNode();
            root.value = value;
        }
        if (value < root.value) {
            root.left = addNode(root.left, value);
        } else if (value > root.value) {
            root.right = addNode(root.right, value);
        }
        return root;
    }

    /**
     * Recursively counts all nodes (including itself) in a given root node.
     * @param root Parent {@code TreeNode} reference.
     * @return Returns {@code integer} number of nodes.
     */
    public static int treeSize(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return treeSize(root.left) + treeSize(root.right) + 1;
    }

    /**
     * Recursively checks if the value is contained in a tree.
     *
     * @param root parent reference of a {@code TreeNode}.
     * @param value {@code integer} value which is being checked.
     * @return {@code true} if value exists, {@code false} otherwise.
     */
    public static boolean containsValue(TreeNode root, int value) {
        if (root == null) {
            return false;
        } else if (root.value == value) {
            return true;
        }
        if (value < root.value) {
            return containsValue(root.left, value);
        }
        return containsValue(root.right, value);
    }

    /**
     * Recursively prints node values starting from the smallest value.
     * @param root {@code TreeNode} root reference.
     */
    public static void printAscending(TreeNode root) {
        if (root == null) {
            return;
        }
        printAscending(root.left);
        System.out.print(" " + root.value);
        printAscending(root.right);
    }

    /**
     * Recursively prints node values starting from the greatest value.
     * @param root {@code TreeNode} root reference.
     */
    public static void printDescending(TreeNode root) {
        if (root == null) {
            return;
        }
        printDescending(root.right);
        System.out.print(" " + root.value);
        printDescending(root.left);
    }

    public static void main(String[] args) {
        TreeNode glava = null;
        Scanner sc = new Scanner(System.in);
        int value;
        while (true) {
            System.out.print("Unesite broj > ");
            String line = sc.next();
            if (line.equals("kraj")) {
                break;
            }
            try {
                value = Integer.parseInt(line);
            } catch (NumberFormatException ex) {
                System.out.println("\'" + line +"\' nije cijeli broj");
                continue;
            }
            if (containsValue(glava, value)) {
                System.out.println("Broj već postoji. Preskačem.");
                continue;
            }
            glava = addNode(glava, value);
            System.out.println("Dodano.");

        }
        sc.close();
        System.out.print("Ispis od najmanjeg:");
        printAscending(glava);

        System.out.print("\nIspis od najvećeg:");
        printDescending(glava);
    }
}
