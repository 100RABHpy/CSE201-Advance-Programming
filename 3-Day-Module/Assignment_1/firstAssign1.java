package IIITD.Sem3.AP.Refresher_module;

import java.util.Scanner;

class Node {
    int data;

    Node next;
}

class LinkedList {
    Node head;

    protected void insert(int data) {
        Node newNode = new Node();
        newNode.data = data;
        if (head == null) {
            head = newNode;

        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;


        }
    }

    protected void deleteAt(int index) {
        int previous = index - 1;
        Node temp = head;
        while (previous != 0) {
            previous--;
            temp = temp.next;

        }
        Node deleteNode = temp.next;
        temp.next = deleteNode.next;

    }

    protected void show() {
        Node temp = head;
        while (temp.next != null) {

            System.out.print(temp.data+" ");
            temp = temp.next;
        }
        System.out.println(temp.data);

    }

}

public class firstAssign1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
       
        
        int t = in.nextInt();

        while (t != 0) {
            LinkedList linkedList = new LinkedList();

            t--;
            int n = in.nextInt();
            int copyN = n;

            while (n != 0) {
                n--;
                linkedList.insert(in.nextInt());

            }

            int x = in.nextInt();
            

                
            
            if (x == 0) {
                System.out.println("False");
                System.out.println(0);
                linkedList.show();
            } else {
                linkedList.deleteAt(x - 1);
                int noofElements = copyN - (x - 1);
                System.out.println("True");
                System.out.println(noofElements);
                linkedList.show();
                

            }
        }
    }

}