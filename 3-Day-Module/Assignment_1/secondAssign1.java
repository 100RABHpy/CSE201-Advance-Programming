package IIITD.Sem3.AP.Refresher_module;


import java.util.Scanner;

public class secondAssign1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String[] str = in.next().split("");

        for (int i = 0; i < str.length; i++) {
            String temp = str[i];

            int count = 1;
            System.out.print(temp);
            i++;
            while (i < str.length && temp.equals(str[i])) {
                // System.out.println(-22);
                count++;
                i++;
            }
            i--;
            System.out.print(count);

        }
        System.out.println();
    }

}