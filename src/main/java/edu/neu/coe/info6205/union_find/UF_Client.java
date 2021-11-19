package edu.neu.coe.info6205.union_find;

import java.util.Random;
import java.util.Scanner;

public class UF_Client {

    static Random random = new Random();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        double ration_sum = 0;
        int inputCount = 0;
        while(sc.hasNext()){
            int n = sc.nextInt();
            if(n == 0){
                System.out.println("The average ratio of m and n: " + ration_sum / inputCount);
                System.exit(0);
            }else{
                inputCount++;
                int m = count(n);
                double ratio = ((double)m)/n;
                ration_sum += ratio;
                System.out.println("--------------------------------------------------------");
                System.out.println("Number of objects: " + n + " Number of pairs: " + m);
                System.out.println("The ratio of m and n: " + ratio);
            }
        }


    }

    public static int count(int n){
        int res = 0;
        UF_HWQUPC helper = new UF_HWQUPC(n,true);
        while(helper.components() > 1){
            int p = random.nextInt(n);
            int q = random.nextInt(n);

            if(!helper.connected(p,q)){
                helper.union(p,q);
            }
            res++;
        }
        return res;
    }

}
