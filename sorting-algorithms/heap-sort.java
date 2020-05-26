import java.util.Arrays;

class HeapSort{
    private int parent(int i){
        return (int)(i / 2);
    }

    private int left(int i){
        return 2 * i + 1 ;
    }

    private int right(int i){
        return 2 * i + 2;
    }

    private void maxHeapify(int[] A, int heapSize, int i){
        int l = left(i);
        int r = right(i);
        int largest;
        
        if (l < heapSize && A[l] > A[i]){
            largest = l;
        }else{
            largest = i;
        }

        if (r < heapSize && A[r] > A[largest]){
            largest = r;
        }

        if (largest != i){
            int tmp = A[i];
            A[i] = A[largest];
            A[largest] = tmp;
            maxHeapify(A, heapSize, largest);
        }
    }

    private void buildMaxHeap(int[] A, int heapSize){
        for (int i = (int)(heapSize / 2); i >= 0; i--){
            maxHeapify(A, heapSize, i);
        }
    }

    private void heapSort(int[] A){
        int heapSize = A.length;
        buildMaxHeap(A, heapSize);
        for (int i = A.length - 1; i > 0; i--){
            int tmp = A[0];
            A[0] = A[i];
            A[i] = tmp;
            heapSize--;
            maxHeapify(A, heapSize, 0);
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
        HeapSort sort = new HeapSort();
        int n = 10;
        int[] A = new int[10];
        createArray(n, 20, A);
        
        printArray(A);

        sort.heapSort(A);
    
        printArray(A); 
    }
}