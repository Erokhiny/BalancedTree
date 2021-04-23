package com.company;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
class Node {
    int key;
    int height;
    Node left;
    Node right;

    Node(int key){
        this.key = key;
        this.left=null;
        this.right=null;
    }
}
class AVLTree {

    Node root;

    AVLTree(){
        root = null;
    }

    void updateHeight(Node n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    int height(Node n) {
        return n == null ? -1 : n.height;
    }

    int getBalance(Node n) {
        return (n == null) ? 0 : height(n.right) - height(n.left);
    }

    Node rotateRight(Node y) {
        Node x = y.left;
        Node z = x.right;
        x.right = y;
        y.left = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    Node rotateLeft(Node y) {
        Node x = y.right;
        Node z = x.left;
        x.left = y;
        y.right = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    Node rebalance(Node z) {
        updateHeight(z);
        int balance = getBalance(z);
        if (balance > 1) {
            if (height(z.right.right) > height(z.right.left)) {
                z = rotateLeft(z);
            } else {
                z.right = rotateRight(z.right);
                z = rotateLeft(z);
            }
        } else if (balance < -1) {
            if (height(z.left.left) > height(z.left.right))
                z = rotateRight(z);
            else {
                z.left = rotateLeft(z.left);
                z = rotateRight(z);
            }
        }
        return z;
    }

    Node insert(Node node, int key) {
        if(root==null){
            root = new Node(key);
            return root;
        }
        if (node == null) {
            return new Node(key);
        } else if (node.key > key) {
            node.left = insert(node.left, key);
        } else if (node.key < key) {
            node.right = insert(node.right, key);
        } else {
            throw new RuntimeException("duplicate Key!");
        }
        return rebalance(node);
    }

    public void printBinaryTree(Node node, int level) {
        if (node != null) {
            printBinaryTree(node.left, level + 1);
            for (int i = 0; i < level; i++)
                System.out.print("   ");//чем ниже уровень, тем отступ больше
            System.out.println(node.key);
            printBinaryTree(node.right, level + 1);
        }
    }
}
public class Main {

    public static void main(String[] args)
    {
        AVLTree a = new AVLTree();
        Scanner in = new Scanner(System.in);
        int c = in.nextInt();
        while (c!=0){
            a.root = (a.insert(a.root, c));
            a.rebalance(a.root);
            c = in.nextInt();
        }
        a.printBinaryTree(a.root, 0);
    }
}