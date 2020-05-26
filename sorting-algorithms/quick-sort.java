import java.util.Arrays;

class QuickSort{
    private int partion(int[] A, int p, int r){
        int x = A[r];
        int i = p - 1;
        for (int j = p; j < r; j++){
            if (A[j] <= x){
                i++;
                int tmp = A[i];
                A[i] = A[j];
                A[j] = tmp;
            }
        }
        A[r] = A[i + 1];
        A[i + 1] = x;
        return i + 1;
    }

    private void quickSort(int[] A, int p, int r){
        if (p < r){
            int q = partion(A, p, r);
            quickSort(A, p, q - 1);
            quickSort(A, q + 1, r);
        }
    }

    // Creates array of lenght n with values bewteen 0 & max
    public static void createArray(int n, int max, int[] A){    
        for (int i = 0; i < n; i++) {
           A[i] = (int)(Math.random() * max);
        }
    }

    public static void printArray(int[] arr){
        System.out.println(Arrays.toString(arr));
    }

    public static void main(String[] args) {
        QuickSort sort = new QuickSort();
        int n = 10;
        int[] A = new int[n];
        createArray(n, 20, A);
        printArray(A);
        sort.quickSort(A, 0, n - 1);
        printArray(A);
    }
}