//Import package yang dibutuhkan
import java.io.*;

//Class utama
public class SDA1606827145L3A_Jumat {
    public static void main(String[] args) throws IOException, NumberFormatException { // Main method
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int lines = Integer.parseInt(in.readLine());

        for(int i = 0; i<lines; i++){ //looping sebanyak lines kali
            String command = in.readLine();
            if(check(command)){
                System.out.println("BAGUS");
            }
            else{
                System.out.println("TIDAK BAGUS");
            }
        }
    }
    private static boolean check(String str){ //Method pengecekan string

        if(str.equals("")){
            return true;
        }else if(str.contains("LO")){
            return check(str.replace("LO", ""));
        }else if(str.contains("VE")){
            return check(str.replace("VE", ""));
        }else if(str.contains("<3")){
            return check(str.replace("<3", ""));
        }else {
            return false;
        }
    }
}
