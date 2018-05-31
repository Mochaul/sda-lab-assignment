/*
Name    : Mochamad Aulia Akbar Praditomo
NPM     : 1606827145
Kelas   : SDA-A
Lab     : 4B
*/

// Importing needed packages
import java.io.*;

// Main public class
public class SDA1606827145L4B {
    static class Card implements Comparable { // Class card
        // Instance variables
        String name;
        int power;

        // Class constructor
        private Card(String name, int power){
            this.name = name;
            this.power = power;
        }

        // Class Card methods
        @Override
        public String toString() {
            return name + " " + power + " dikeluarkan";
        }

        @Override //Compare by integer, if equal compare by string
        public int compareTo(Object o) {
            int result = ((Card) o).power-this.power;
            if (result==0) return this.name.compareTo(((Card) o).name);
            else return result;
        }
    }

    // quickSort sorting method
    private static void quickSort(Card[] arr, int low, int high) {
        int i = low, j = high;
        Card pivot = arr[(low+high)/2];

        while(i<=j){
            while(arr[i].compareTo(pivot)<0){
                i++;
            }
            while(arr[j].compareTo(pivot)>0){
                j--;
            }
            if (i<=j) {
                Card temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }

        if (low < j)
            quickSort(arr,low,j);
        if (i < high)
            quickSort(arr,i,high);
    }

    // Main method
    public static void main (String[] args) throws IOException {
        // Creating necessary variables
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        Card[] arr = new Card[10000];
        String inp;
        int awal=0;
        int akhir=0;

        // Looping until EOF
        while((inp = br.readLine()) != null){
            int len=akhir-awal;
            String[] inps = inp.split(" ");
            if (inps[0].equalsIgnoreCase("PICK")){ // Case if PICK
                String name = inps[1];
                int n = Integer.parseInt(inps[2]);
                bw.write(name + " dengan power " + n + " diambil");
                bw.newLine();
                arr[akhir++] = new Card(name,n);
                quickSort(arr,awal,akhir-1);
            } else if (inp.equalsIgnoreCase("ATTACK")){ // Case if ATTACK
                if (len<1){
                    bw.write("Tidak bisa melakukan Attack");
                    bw.newLine();
                } else {
                    bw.write(String.valueOf(arr[awal++]));
                    bw.newLine();
                }
            } else if (inp.equalsIgnoreCase("DEFENSE")){ // Case if DEFENSE
                if (len<3){
                    bw.write("Tidak bisa melakukan Defense");
                    bw.newLine();
                } else {
                    for (int i = 0;i<3;i++){
                        bw.write(String.valueOf(arr[--akhir]));
                        bw.newLine();
                    }
                }
            } else if (inp.equalsIgnoreCase("SEE CARD")){ // SEE CARD
                //System.out.println(awal + "|" + akhir);
                if (akhir-awal==0) {
                    bw.write("Kartu kosong");
                    bw.newLine();
                } else {
                    for (int i = awal;i<akhir;i++){
                        bw.write(arr[i].name + " " + arr[i].power);
                        bw.newLine();
                    }
                }
            }
            bw.flush(); // Printing the output
        }
    }
}
