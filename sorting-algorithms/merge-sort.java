import java.util.Arrays;

class MergeSort{

    private void merge(int[] A, int p, int q, int r){
        int nOne = q - p + 1;
        int nTwo = r - q;

        int L[] = new int[nOne];
        int R[] = new int[nTwo];

        for(int i = 0; i < nOne; i++){
            L[i] = A[p + i];
        }

        for(int j = 0; j < nTwo; j++){
            R[j] = A[q + j];
        }        

        int i = 0;
        int j = 0;
        printArray(L);
        printArray(R);
        for (int k = p; k < r; k++){
            if (L[i] <= R[j]){
                A[k] = L[i];
                i++;
            }else{
                A[k] = R[j];
                j++;
            }
        }
    }

    private void sort(int A[], int p, int r){
        if (p < r){
            int q = (int)((p + r) / 2);
            sort(A, p, q);
            sort(A, q + 1, r);
            merge(A, p, q, r);
        }
    }

    public MergeSort(int A[]){
        sort(A, 0, A.length - 1);
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
        int n = 4;
        int A[] = new int[n];
        createArray(n, 10, A);
        printArray(A);

        MergeSort ms = new MergeSort(A);

        printArray(A);
    }
}