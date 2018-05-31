/*
Name    : Mochamad Aulia Akbar Praditomo
NPM     : 1606827145
Kelas   : SDA-A
Lab     : 4A
*/

// Importing needed packages
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// Main public class
public class SDA1606827145L4A {

    // quickSort method
    private static void quickSort(int[] arr, int low, int high) {
        int i = low, j = high;
        int pivot = arr[(low+high)/2];

        // Main loop for sorting
        while(i<=j){
            while(arr[i]<pivot){
                i++;
            }
            while(arr[j]>pivot){
                j--;
            }
            if (i<=j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }
        // Recursively use method
        if (low < j)
            quickSort(arr,low,j);
        if (i < high)
            quickSort(arr,i,high);
    }

    public static void main (String[] args) throws IOException { // Main method
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int jumlahAngka = Integer.parseInt(br.readLine());
        String[] arrStr = br.readLine().split(" ");
        int[] urutan = new int[jumlahAngka];

        for (int i = 0; i < arrStr.length; i++) {
            urutan[i] = Integer.parseInt(arrStr[i]);
        }

        // Using quickSort method
        quickSort(urutan,0,urutan.length-1);
        for (int i = 0; i < urutan.length; i++) {
            if (i<urutan.length-1) System.out.print(urutan[i] + " ");
            else System.out.println(urutan[i]);
        }
    }
}