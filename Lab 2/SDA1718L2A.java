// Import needed utility
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;
import java.util.TreeSet;

public class SDA1718L2A {
	// Created by : Mochamad Aulia Akbar Praditomo
	// 160682715
	// SDA-A
	
	//Main method
	public static void main(String[] args) throws IOException{
		// Membuat object-object yang diperlukan
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		TreeSet<String> archive = new TreeSet<>();
		TreeMap<String, Integer> queue = new TreeMap<>();
		String line;

		// Loop utama
		while ((line = in.readLine()) != null) {
			String[] perintah = line.split(" ");
			try {
				// Case jika PRINT
				if (perintah[0].equalsIgnoreCase("PRINT")) {
					if (queue.isEmpty()) {
						System.out.println("Antrean kosong");
					}
					else {
						// Print 10 halaman
						int print = queue.get(queue.firstKey());
						while (print <= 10) {
							System.out.println("Submisi " + queue.firstKey() + " telah dicetak sebanyak " + queue.get(queue.firstKey()) + " halaman");
							archive.add(queue.firstKey());
							queue.remove(queue.firstKey());
							if (queue.isEmpty()) {
								break;
							}
							print += queue.get(queue.firstKey());
						}
					}
				// Case jika SUBMIT
				} else if (perintah[1].equalsIgnoreCase("SUBMIT")) {
					if (queue.containsKey(perintah[0])) {
						System.out.println("Harap tunggu hingga submisi sebelumnya selesai diproses");
					} else if (Integer.parseInt(perintah[2]) > 10) {
						System.out.println("Jumlah halaman submisi " + perintah[0] + " terlalu banyak");
					} else {
						queue.put(perintah[0], Integer.parseInt(perintah[2]));
						System.out.println("Submisi " + perintah[0] + " telah diterima");
					}	
				// Case jika CANCEL
				} else if (perintah[1].equalsIgnoreCase("CANCEL")) {
					if (!(queue.containsKey(perintah[0]))) {
						System.out.println(perintah[0] + " tidak ada dalam antrean");
					} else {
						System.out.println("Submisi " + perintah[0] + " dibatalkan");
						queue.remove(perintah[0]);
					}
				// Case jika STATUS
				} else if (perintah[0].equalsIgnoreCase("STATUS")) {
					if (archive.contains(perintah[1])) {
						System.out.println("Submisi " + perintah[1] + " sudah diproses");
					} else if (queue.containsKey(perintah[1])) {
						System.out.println("Submisi " + perintah[1] + " masih dalam antrean");
					} else {
						System.out.println(perintah[1] + " tidak ada dalam sistem");
					}
				}
			// Jika keluar index
			} catch (IndexOutOfBoundsException e) {
				continue;
			}
		}
	}
}
