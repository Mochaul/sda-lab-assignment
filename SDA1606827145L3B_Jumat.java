// Import needed package
import java.io.*;

// Main Class
public class SDA1606827145L3B_Jumat {
    private static int[] memory; //array of integers for saving steps
    public static void main(String[] args) throws IOException { // Main method
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int command = Integer.parseInt(in.readLine());
        memory = new int[10001];
        // Looping for command-times
        for (int i = 0; i < command; i++) {
            int num = Integer.parseInt(in.readLine());
            System.out.println(findRoot(num));
        }
    }
    //
    private static int findRoot(int num) {
        if(memory[num] != 0){
            return memory[num];
        }
        if(num <= 3){
            return num;
        }
        int div = (int) Math.sqrt(num);
        for (int i = div; i > 1; i--) {
            if (num % i == 0) {
                if(memory[num] == 0){
                    memory[num] = 1 + findRoot(num/i);
                }
                else{
                    memory[num] = Math.min(memory[num], 1 + findRoot(num/i));
                }
            }
        }
        if(memory[num] == 0){
            memory[num] = 1 + findRoot(num -1);
        }
        else{
            memory[num] = Math.min(memory[num], 1 + findRoot(num-1));
        }
        return memory[num];
    }
}
