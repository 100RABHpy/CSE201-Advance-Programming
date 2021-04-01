package IIITD.Sem3.AP.Refresher_module;

import java.math.BigInteger;
import java.util.Scanner;

// BigInteger b = new BigInteger(String.valueOf(n)); 
//         return Long.parseLong(b.nextProbablePrime().toString()); 
public class thirdAssign1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String str = in.next();
        BigInteger num = new BigInteger(String.valueOf(str.length()));
        BigInteger primeNum = new BigInteger(String.valueOf(2));
        BigInteger one = new BigInteger(String.valueOf(1));
        BigInteger zero = new BigInteger(String.valueOf(0));
        String data = "";

        while (!(num.equals(one))) {
            if (num.mod(primeNum).equals(zero)) {
                num = num.divide(primeNum);
                data += primeNum.toString() + "*";
            } else {
                primeNum = primeNum.nextProbablePrime();
            }
        }

        System.out.println(data.substring(0, data.length() - 1));

    }
}