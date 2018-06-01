/*
 * @author : Mochamad Aulia Akbar Praditomo (1606827145)
 * SDA-A
 * Lab 6B
 */

// import needed packages
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.*;

public class SDA1606827145L6B { // main class

    // creating input & output object
    static final BufferedReader IN = new BufferedReader(new InputStreamReader(System.in));
    static final PrintWriter OUT = new PrintWriter(new OutputStreamWriter(System.out));

    static class Peminjaman { // Peminjaman class
        List<String> peminjam;
        String buku;

        public Peminjaman(String buku) { // class constructor
            this.buku = buku;
            this.peminjam = new LinkedList<>();
        }

        boolean addPeminjam(String nama) { // addPeminjam buku
            return this.peminjam.add(nama);
        }

        boolean removePeminjam(String nama) { // removePeminjam buku
            return this.peminjam.remove(nama);
        }

        void daftarPeminjam() { // print daftarPeminjam secara leksikografis
            Collections.sort(this.peminjam);

            Iterator<String> iter = this.peminjam.listIterator();
            int size = this.peminjam.size();

            for (int i = 0; i < size - 1 && iter.hasNext(); ++i)
                OUT.print(iter.next() + " ");

            OUT.println(iter.next());
        }

    }

    static class Node<E extends Peminjaman> { // Node class dengan index yang sama
        List<E> collide;

        public Node() { // class constructor
            this.collide = new ArrayList<>();
        }

        public void add(E elem) { // add peminjaman
            this.collide.add(elem);
        }

        public E get(String buku) { // ambil peminjaman berdasarkan nama buku
            for (E e : this.collide)
                if (e.buku.equals(buku))
                    return e;

            return null;
        }

        @Override
        public String toString() {
            return collide.toString();
        }
    }

    public static void main(String args[]) throws Exception { // main method
        // creating needed objects
        Node<Peminjaman> peminjaman[] = new Node[100001];
        String perintah = null;
        int bookHash;
        String nama, buku;

        // main loop
        while ((perintah = IN.readLine()) != null && 0 < perintah.length()) {
            String parse[] = perintah.split(" ");
            switch (parse[0]) {

                case "PINJAM": // case if PINJAM
                    nama = parse[1];
                    buku = parse[2];

                    bookHash = hashFunction(buku);
                    Node<Peminjaman> nodePeminjaman = peminjaman[bookHash];

                    // jika pada index hash tersebut belum ada node
                    if (nodePeminjaman == null)
                        peminjaman[bookHash] = nodePeminjaman = new Node<>();


                    Peminjaman pmj = nodePeminjaman.get(buku);
                    // cek apakah peminjaman pada node tersebut sudah ada
                    if(pmj == null) {
                        pmj = new Peminjaman(buku);
                        nodePeminjaman.add(pmj);
                    }

                    pmj.addPeminjam(nama);
                    break;

                case "DAFTAR_PEMINJAM": // case if DAFTAR_PEMINJAM
                    buku = parse[1];

                    bookHash = hashFunction(buku);
                    peminjaman[bookHash].get(buku).daftarPeminjam();
                    break;

                case "KEMBALI": // case if KEMBALI
                    nama = parse[1];
                    buku = parse[2];

                    bookHash = hashFunction(buku);
                    peminjaman[bookHash].get(buku).removePeminjam(nama);
                    break;
            }
        }

        OUT.flush(); // printing all output
    }

    private static final int TABLE_SIZE = 1009;

    public static int hashFunction(String s) { // method for finding the hashcode

        int hash = 0;

        for (int i = 0; i < s.length(); ++i) {
            hash += 37 * hash + (s.charAt(i) - 'a') * (i+1);
        }

        hash %= TABLE_SIZE;

        if (hash < 0)
            hash += TABLE_SIZE;

        return hash;
    }
}
