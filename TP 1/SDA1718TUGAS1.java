// Importing needed utilities/collections
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeMap;

/*	Author 	: Mochamad Aulia Akbar Praditomo
 * 	NPM		: 1606827145
 *  Class	: SDA-A
 *  Tugas Pemrograman 1
*/

public class SDA1718TUGAS1 { // Main Class
	
	private static Scanner input;

	public static void main(String[] args) { // Main Method
		
		// Creating the necessary object
		TreeMap<String, String> history = new TreeMap<String, String>();
		ArrayList<Stack<String>> tabList = new ArrayList<Stack<String>>();
		Stack<String> tab = new Stack<String>();
		tabList.add(tab);
		int index = 0, jumlahPerintah;
		
		input = new Scanner(System.in);
		jumlahPerintah = input.nextInt(); // First input of how many instruction(s)
		
		while (jumlahPerintah>=0) { // Looping jumlahPerintah-times
			String[] perintah = input.nextLine().split(";");
	
			if (perintah[0].equalsIgnoreCase("VIEW")) { // If the instruction is VIEW
				tabList.get(index).add(perintah[1] + ":" + perintah[2]);
				System.out.println("VIEWING([" + tabList.get(index).peek() + "])");
				history.put(perintah[1], perintah[2]);
			} else if (perintah[0].equalsIgnoreCase("BACK")) {
				if (tabList.get(index).isEmpty()) {
					System.out.println("EMPTY TAB");
				} else if (tabList.get(index).size() == 1){
					tabList.get(index).pop();
					System.out.println("EMPTY TAB");
				} else {
					tabList.get(index).pop();
					System.out.println("VIEWING([" + tabList.get(index).peek() + "])");
				}
			} else if (perintah[0].equalsIgnoreCase("NEWTAB")) { // If the instruction is NEWTAB
				Stack<String> tabNew = new Stack<String>();
				tabList.add(tabNew);
			} else if (perintah[0].equalsIgnoreCase("CHANGETAB")) { // If the instruction is CHANGETAB
				int temp = Integer.parseInt(perintah[1]);
				if (tabList.size() > temp && temp >= 0) {
					index = temp;
					System.out.println("TAB: " + perintah[1]);
				} else {
					System.out.println("NO TAB");
				}
			} else if (perintah[0].equalsIgnoreCase("HISTORY")) { // If the Instuction is HISTORY
				if (history.isEmpty()) {
					System.out.println("NO RECORD");
				} else {
					for (String key : history.keySet()) {
					    System.out.println(key + ":" + history.get(key));
					}
				}
			}
			jumlahPerintah--; // Decrease the number of remaining instruction(s)
		}
	}
}
