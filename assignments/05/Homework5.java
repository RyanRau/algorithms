import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.io.IOException;

class Homework5 {
    ////////////////////////////////////////////////////////////////////////////////////
    // Quick Sort
    ////////////////////////////////////////////////////////////////////////////////////
    private int partion(long[] A, int p, int r){
        long x = A[r];
        int i = p - 1;
        for (int j = p; j < r; j++){
            if (A[j] <= x){
                i++;
                long tmp = A[i];
                A[i] = A[j];
                A[j] = tmp;
            }
        }
        A[r] = A[i + 1];
        A[i + 1] = x;
        return i + 1;
    }

    private void quickSort(long[] A, int p, int r){
        if (p < r){
            int q = partion(A, p, r);
            quickSort(A, p, q - 1);
            quickSort(A, q + 1, r);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////
    // Radix Sort
    ////////////////////////////////////////////////////////////////////////////////////
    private void countingSort(long[] A, long k){
        int n = A.length;
        // let C be new array from 0 - n
        int C[] = new int[256];  
        for(int i = 0; i < 256; i++){
            C[i] = 0; 
        } 
           
        // C[i] = # of elements = to i
        for(int i = 0; i < n; i++){
            // puts each term of A into of base n and counts occurences based on % n
            int val = (int)((A[i]/k) % 256);
            C[val]++; 
        } 

        // C[i] = # of elemnts <= to i
        for (int i = 1; i < 256; i++){
            C[i] += C[i - 1]; 
        }

        // Save results to tmp array
        long tmp[] = new long[n];
        for (int i = n - 1; i >= 0; i--) 
        { 
            tmp[C[ (int)((A[i]/k) % 256) ] - 1] = A[i]; 
            C[ (int)((A[i]/k) % 256) ] = C[ (int)((A[i]/k) % 256) ] - 1; 
        } 

        // Copy tmp array to A
        for (int i = 0; i < n; i++) 
            A[i] = tmp[i]; 
    }

    private void radixSort(long[] A, int d){
        for(int i = 0; i < d; i++){         
            countingSort(A, (long)Math.pow(256, i));
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////
    // Helper Functions
    ////////////////////////////////////////////////////////////////////////////////////
    private void sortAnalysis(String fileName, long A[]){
        long startTime, stopTime;
        int n = A.length;

        readData(fileName, A);

        // Quick Sort
        System.out.println("Quick Sort " + fileName + " times:");
        for (int i = 1; i < 4; i++){
            readData(fileName, A);

            // actuall sorting
            startTime = System.currentTimeMillis();
            quickSort(A, 0, n - 1);
            stopTime = System.currentTimeMillis();

            if (isSorted(A)){
                System.out.println("Run " + i + ": Elapsed time was " + (stopTime - startTime) + " miliseconds.");
            } else {
                System.out.println("Sorting Failed...");
            }
        }

        // Radix Sort
        System.out.println("Radix Sort " + fileName + " times:");
        for (int i = 1; i < 4; i++){
            readData(fileName, A);

            // actuall sorting
            startTime = System.currentTimeMillis();
            radixSort(A, 4);
            stopTime = System.currentTimeMillis();
            
            if (isSorted(A)){
                System.out.println("Run " + i + ": Elapsed time was " + (stopTime - startTime) + " miliseconds.");
            } else {
                System.out.println("Sorting Failed...");
            }
        }
    }

    public static void createArray(long[] A, int n, long max){    
        for (int i = 0; i < n; i++) {
            A[i] = (long)(Math.random() * max);
        }
    }
    
    public static void printArray(long[] A){
        System.out.println(Arrays.toString(A));
    }

    public static void createData(String fileName, long n, long max) throws IOException {
        try {
            FileWriter writer = new FileWriter(fileName);
     
            for (long i = 0; i < n; i++) {
                writer.write(Long.toString((long)(Math.random() * max)));
            }
    
            writer.close();
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    public static void readData(String fileName, long[] A){
        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file);

            int i = 0;
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                A[i] = Long.parseLong(data);
                i++;
            }
            
            reader.close();
        }catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
    }

    public static void writeData(String fileName, long[] A){
        try {
            FileWriter writer = new FileWriter(fileName);

            for (int i = 0; i < A.length; i++) {
                writer.write(Long.toString(A[i]) + "\n");
            }

            writer.close();
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    public static boolean isSorted(long[] A){
        long tmp = 0;
        
        for (int i = 0; i < A.length; i++){
            if(tmp > A[i]){
                return false;
            }
            tmp = A[i];
        }

        return true;
    }



    public static void main(String[] args) {
        int n = 10000000;
        long max = (long)(Math.pow(2, 32)) - 1;
        long A[] = new long[n];

        Scanner scan = new Scanner(System.in);
        Homework5 hw5 = new Homework5();
        String fileName;


        System.out.println("Note: Program might take a bit of time to read in data each time... Only time actually sorting is recorded.");
        if(args.length == 2)
        {
            fileName = args[0];
            System.out.println("Sorting " + fileName + ". . .");
            hw5.sortAnalysis(fileName, A);

            fileName = args[1];
            writeData(fileName, A);
            System.out.println("Sorted file has been saved as " + fileName);
        } else {
            //Data Creation
            System.out.println("Generating Data. . .");
            for (int i = 1; i < 4; i++){
                fileName = "data" + i + ".txt";
                createArray(A, n, max);
                writeData(fileName, A);
                System.out.println(fileName + " created.");
            }

            // Analysis
            System.out.println("Sorting Data. . .");
            for (int i = 1; i < 4; i++){
                fileName = "data" + i + ".txt";
                hw5.sortAnalysis(fileName, A);
            }
        }

        
        // System.out.println("Note: Program might take a bit of time to read in data each time... Only time actually sorting is recorded.");

        // System.out.println("0: Provide your own data file.");
        // System.out.println("1: Generate new random data.");
        // int c = scan.nextInt();
        
        // switch(c){
        //     case 0:
        //         System.out.println("Enter the name of the file you'd like to sort. (ie. data0.txt)");
        //         System.out.println("Note that it must be in the same directory as this program and be of lenght 10,000,000");
        //         fileName = System.console().readLine();

        //         System.out.println("Sorting " + fileName + ". . .");
        //         hw5.sortAnalysis(fileName, A);

        //         fileName = fileName.split("[.]")[0] + "-sorted.txt";
        //         writeData(fileName, A);
        //         System.out.println("Sorted file has been saved as " + fileName);

        //         break;
        //     case 1:
        //         //Data Creation
        //         System.out.println("Generating Data. . .");
        //         for (int i = 1; i < 4; i++){
        //             fileName = "data" + i + ".txt";
        //             createArray(A, n, max);
        //             writeData(fileName, A);
        //             System.out.println(fileName + " created.");
        //         }

        //         // Analysis
        //         System.out.println("Sorting Data. . .");
        //         for (int i = 1; i < 4; i++){
        //             fileName = "data" + i + ".txt";
        //             hw5.sortAnalysis(fileName, A);
        //         }
        //         break;
        //     default:
        //         System.out.println("Invalid choice entered... Please enter a valid choice next time. Goodbye");
        //         break;
        // }

        scan.close();
    }
}