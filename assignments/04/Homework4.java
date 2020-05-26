// Algorithms HW 4
// Ryan Rau
// Feb. 21 2020

// With help from https://www.geeksforgeeks.org/radix-sort/

import java.util.Scanner;
import java.lang.Math;
import java.util.Arrays;

public class Homework4
{
    private int n;
    private int max;
    private int[] A;

    // Takes in array to be sorted along with n and k, being the length of the array and the "base"
    // input:
    // A[] - Input Array
    // k   - n^some # 
    private void countingSort(int[] A, int k){
        // let C be new array from 0 - n
        int C[] = new int[n];  
        for(int i = 0; i < n; i++){
            C[i] = 0; 
        } 
           
        // C[i] = # of elements = to i
        for(int i = 0; i < n; i++){
            // puts each term of A into of base n and counts occurences based on % n
            int val = (A[i]/k) % n;
            C[val]++; 
        } 

        // C[i] = # of elemnts <= to i
        for (int i = 1; i < n; i++){
            C[i] += C[i - 1]; 
        }

        // Save results to tmp array
        int tmp[] = new int[n];
        for (int i = n - 1; i >= 0; i--) 
        { 
            tmp[C[ (A[i]/k) % n ] - 1] = A[i]; 
            C[ (A[i]/k) % n ]--; 
        } 

        // Copy tmp array to A
        for (int i = 0; i < n; i++) 
            A[i] = tmp[i]; 
    }

    private void radixSort(int[] A, int d){
        for(int i = 0; i < d; i++){            
            countingSort(A, (int)Math.pow(n, i));
            System.out.println("Sorting pass " + i);
            printArray(A);
        }
    }

    // Creates array of lenght n with values bewteen 0 & max
    private void createArray(int n, int max, int[] A){    
        for (int i = 0; i < n; i++) {
           A[i] = (int)(Math.random() * max);
        }
    }

    private void printArray(int[] arr){
        System.out.println(Arrays.toString(arr));
    }

    private Homework4(int x){
        n = x;
        max = (int)(Math.pow(n, 3)) - 1;
        A = new int[n];

        createArray(n, max, A);
        System.out.println("n = " + n);
        System.out.println("Unsorted Array:");
        printArray(A);

        System.out.println("Sorting...");
        radixSort(A, 3);

        System.out.println("Sorted Array:");
        printArray(A);
    }

	public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please Enter a value for n:");
        int n = scan.nextInt();
        scan.close();

        Homework4 hw = new Homework4(n);
    }
}