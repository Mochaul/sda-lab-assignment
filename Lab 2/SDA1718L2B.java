// Import utility yang dibutuhkan
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class SDA1718L2B {
	// Create by : Mochamad Aulia Akbar Praditomo
	// 1606827145
	// SDA-A
	
	// Main method
	public static void main(String[] args) throws IOException{
		// Membuat object-object yang dibutuhkan
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		LinkedList<String> queue = new LinkedList<>();
		HashSet<String> mogok = new HashSet<>();
		Iterator<String> litr = null;
		String line, temp, mobilKiri, mobilKanan;
		int counterKiri, counterKanan;
		boolean mogokKiri, mogokKanan;

		// Looping utama
		while ((line = in.readLine()) != null) {
			String[] perintah = line.split(" ");
			// Kalau perintah MASUK
			if (perintah[0].equalsIgnoreCase("MASUK")) {
				if (perintah[2].equalsIgnoreCase("BARAT")) {
					queue.addFirst(perintah[1]);
					System.out.println(perintah[1] + " masuk melalui pintu BARAT");
				} else {
					queue.addLast(perintah[1]);
					System.out.println(perintah[1] + " masuk melalui pintu TIMUR");
				}
			// Kalau perintah KELUARKAN
			} else if (perintah[0].equalsIgnoreCase("KELUARKAN")) {
				// Mengisi variabel kosong
				counterKiri = 0;
				counterKanan = 0;
				mogokKiri = false;
				mogokKanan = false;
				mobilKiri = "";
				mobilKanan = "";
				// Kondisi jika tidak ada jenis mobil atau mobil sedang mogok
				if (!(queue.contains(perintah[1]))) {
					System.out.println(perintah[1] + " tidak ada di garasi");
				} else if (mogok.contains(perintah[1])) {
					System.out.println("Mobil " + perintah[1] + " sedang mogok");
				} else {
					// Looping dari kiri
					litr = queue.listIterator();
					while(litr.hasNext()) {
						counterKiri++;
						temp = litr.next();
						if (mogok.contains(temp)) {
							mobilKiri = temp;
							mogokKiri = true;
						} else if (temp.equals(perintah[1])) {
							break;
						}
					}
					// Looping dari kanan
					litr = queue.descendingIterator();
					while(litr.hasNext()) {
						counterKanan++;
						temp = litr.next();
						if (mogok.contains(temp)) {
							mobilKanan = temp;
							mogokKanan = true;
						} else if (temp.equals(perintah[1])) {
							break;
						}
					}
					// Kondisi jika mogok di kiri dan kanan atau lebih sedikit di kiri dan kanan
					if (mogokKanan && mogokKiri) {
						System.out.println(perintah[1] + " tidak bisa keluar, mobil " + mobilKiri + " dan " + mobilKanan + " sedang mogok");
					} else if (mogokKiri || (counterKiri > counterKanan) && !mogokKanan) {
						queue.remove(perintah[1]);
						System.out.println(perintah[1] + " keluar melalui pintu TIMUR");
					} else if (mogokKanan || (counterKanan >= counterKiri) && !mogokKiri) {
						queue.remove(perintah[1]);
						System.out.println(perintah[1] + " keluar melalui pintu BARAT");
					}
				}
			// Kalau perintah MOGOK
			} else if (perintah[0].equalsIgnoreCase("MOGOK")) {
				mogok.add(perintah[1]);
			// Kalau perintah SERVIS
			} else if (perintah[0].equalsIgnoreCase("SERVIS")) {
				mogok.remove(perintah[1]);
			}
		}
	}
}