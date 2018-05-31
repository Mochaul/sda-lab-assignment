import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class SDA1606827145L5B {

	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String perintah = in.readLine();

		// untuk mengetahui semua siswa terdaftar
		BSTreeB<Siswa> treeSiswa = new BSTreeB<Siswa>();

		// untuk kebutuhan semua nilai
		BSTreeB<Integer> treeNilai = new BSTreeB<Integer>();

		// supaya punya data terurut nama siswa per nilai
		HashMap<Integer, BSTreeB<String>> mapNilaiSiswa = new HashMap<Integer, BSTreeB<String>>();


		while (perintah != null){
			String[] perintahSplit = perintah.split(";");

			if (perintahSplit[0].equals("REGISTER")){
				int nilaiSiswa = Integer.parseInt(perintahSplit[2]);
				Siswa baru = new Siswa(perintahSplit[1], nilaiSiswa);
				if (treeSiswa.add(baru)){		//jika proses add berhasil maka harus memasukkan data nilainya juga ke treeNilai dan mapNilaiSiswa
					System.out.println(perintahSplit[1]+":"+perintahSplit[2]+" berhasil ditambahkan");

					if (treeNilai.contains(nilaiSiswa))		// memeriksa apakah nilai sudah ada yang memiliki sebelumnya
						mapNilaiSiswa.get(nilaiSiswa).add(perintahSplit[1]);

					else{
						BSTreeB<String> treeBaru = new BSTreeB<String>();
						treeBaru.add(perintahSplit[1]);
						mapNilaiSiswa.put(nilaiSiswa, treeBaru);
						treeNilai.add(nilaiSiswa);
					}
				}

				else
					System.out.println(perintahSplit[1]+" sudah terdaftar di dalam sistem");
			}

			else if (perintahSplit[0].equals("RESIGN")){
				Siswa namaSama = new Siswa(perintahSplit[1], 0);
				Siswa mauResign = null;
				if (treeSiswa.contains(namaSama))
					mauResign = treeSiswa.find(namaSama).elem;
				if (mauResign != null){		// memeriksa apakah siswa yang mau resign benar2 ada
					int nilainya = mauResign.getNilai();	// melakukan serangkaian proses untuk menghapus semua data terkait siswa tersebut
					treeSiswa.remove(mauResign);
					mapNilaiSiswa.get(nilainya).remove(perintahSplit[1]);

					if (mapNilaiSiswa.get(nilainya).isEmpty()){
						mapNilaiSiswa.remove(nilainya);
						treeNilai.remove(nilainya);
					}
					System.out.println(perintahSplit[1]+" mengundurkan diri");
				}

				else
					System.out.println(perintahSplit[1]+" tidak ditemukan di dalam sistem");

			}

			else if (perintahSplit[0].equals("RETEST")){
				Siswa namaSama = new Siswa(perintahSplit[1], 0);
				Siswa mauRetest = null;
				if (treeSiswa.contains(namaSama))
					mauRetest = treeSiswa.find(namaSama).elem;
				if (mauRetest != null){		// memeriksa apakah siswa yang mau retest benar2 ada
					int nilaiLama = mauRetest.getNilai();		// melakukan serangkaian proses menghapus data nilai siswa yang lama
					int nilaiBaru = Integer.parseInt(perintahSplit[2]);
					treeSiswa.find(mauRetest).elem.setNilai(nilaiBaru);
					mapNilaiSiswa.get(nilaiLama).remove(perintahSplit[1]);

					//proses adding siswa dengan nilai baru
					if (treeNilai.contains(nilaiBaru))
						mapNilaiSiswa.get(nilaiBaru).add(perintahSplit[1]);

					else{
						BSTreeB<String> treeBaru = new BSTreeB<String>();
						treeBaru.add(perintahSplit[1]);
						mapNilaiSiswa.put(nilaiBaru, treeBaru);
						treeNilai.add(nilaiBaru);
					}

					// jika sudah tidak ada siswa yg punya nilai lama
					// maka akan dihapus semua berkas tentang nilai lama
					if(mapNilaiSiswa.get(nilaiLama).isEmpty()){
						mapNilaiSiswa.remove(nilaiLama);
						treeNilai.remove(nilaiLama);
					}

					System.out.println(perintahSplit[1]+":"+perintahSplit[2]+" perubahan berhasil");
				}
				else
					System.out.println(perintahSplit[1]+" tidak ditemukan di dalam sistem");
			}

			else if (perintahSplit[0].equals("SMARTEST")){
				if (treeSiswa.getRoot() != null){
					int nilaiMax = treeNilai.max();
					List<String> siswa2pintar = mapNilaiSiswa.get(nilaiMax).inOrderAscending();

					for (int i=0; i<siswa2pintar.size()-1; i++)
						System.out.print(siswa2pintar.get(i)+", ");

					System.out.println(siswa2pintar.get(siswa2pintar.size()-1)+" : "+nilaiMax);
				}
				else
					System.out.println("Tidak ada siswa yang terdaftar dalam sistem");
			}

			else if (perintahSplit[0].equals("RANKING")){
				if (treeSiswa.getRoot() != null){
					List<Integer> semuaNilai = treeNilai.inOrderDescending();
					int index=1;

					for (int nilai : semuaNilai){
						List<String> siswaNilaiI = mapNilaiSiswa.get(nilai).inOrderAscending();

						System.out.print(index+". ");
						for (int i=0; i<siswaNilaiI.size()-1; i++)
							System.out.print(siswaNilaiI.get(i)+", ");

						System.out.println(siswaNilaiI.get(siswaNilaiI.size()-1)+" : "+nilai);
						index++;
					}
				}
				else
					System.out.println("Tidak ada siswa yang terdaftar dalam sistem");

			}

			perintah = in.readLine();
		}


		in.close();
	}

}

class Siswa implements Comparable<Siswa>{
	private String nama;
	private int nilai;

	public Siswa(String nama, int nilai) {
		this.nama = nama;
		this.nilai = nilai;
	}

	public int getNilai() {
		return nilai;
	}

	public void setNilai(int nilai) {
		this.nilai = nilai;
	}

	public String getNama() {
		return nama;
	}

	public int compareTo(Siswa other) {
		return this.nama.compareTo(other.getNama());
	}

}

/**
 *
 * Kelas Binary Search Tree
 * Mahasiswa tidak diwajibkan menggunakan template ini, namun sangat disarankan menggunakan template ini
 * Pada template ini, diasumsikan kelas yang ingin dipakai mengimplementasikan (implements) interface Comparable
 * NOTE : Tidak semua method yang dibutuhkan sudah disediakan templatenya pada kelas ini sehingga mahasiswa harus menambahkan sendiri method yang dianggap perlu
 * @author Mochamad Aulia Akbar Praditomo
 *
 */
class BSTreeB<E extends Comparable<? super E>> {

	/**
	 *
	 * Kelas yang merepresentasikan node pada tree
	 *
	 */
	static class Node<E> {

		E elem;
		Node<E> left;
		Node<E> right;
		Node<E> parent;

		/**
		 *
		 * Constructor
		 * @param elem pada node
		 * @param left kiri
		 * @param right kanan
		 * @param parent parent
		 *
		 */
		public Node(E elem, Node<E> left, Node<E> right, Node<E> parent){

			this.elem = elem;
			this.left = left;
			this.right = right;
			this.parent = parent;

		}

	}

	private Node<E> root;


	/**
	 *
	 * Constructor Kelas Binary Search Tree
	 *
	 */
	public BSTreeB(){

		root = null;

	}

	/**
	 *
	 * Mengetahui apakah tree kosong atau tidak
	 * @return true jika kosong, false jika sebaliknya
	 *
	 */
	public boolean isEmpty(){

		return root == null;

	}

	/**
	 *
	 * Menambahkan objek ke dalam tree
	 * @param elem yang ingin ditambahkan
	 * @return true jika elemen berhasil ditambahkan, false jika elemen sudah terdapat pada tree
	 *
	 */
	public boolean add(E elem){

		boolean res = false;

		if(root == null){

			root = new Node<E>(elem, null, null, null);
			res = true;

		} else {

			Node<E> current = root;			
			while(current != null){

				E currElem = current.elem;
				if(elem.compareTo(currElem) < 0){

					if (current.left == null){
						current.left = new Node<E>(elem, null, null, current);
						return true;
					}
					else
						current = current.left;

				} else if(elem.compareTo(currElem) > 0){

					if (current.right == null){
						current.right = new Node<E>(elem, null, null, current);
						return true;
					}
					else
						current = current.right;

				} else {
					return false;
				}
			}
		}

		return res;

	}

	/**
	 *
	 * Mendapatkan node dengan elemen tertentu
	 * @param elem yang ingin dicari nodenya
	 * @return node dari elemen pada parameter, null jika tidak ditemukan
	 *
	 */
	public Node<E> find(E elem){

		Node<E> res = null;

		if(root != null) {
		    Node<E> current = root;
			boolean found = false;
			while(!found && current != null){

				E currElem = current.elem;
				if(elem.compareTo(currElem) < 0){
					current=current.left;

				} else if(elem.compareTo(currElem) > 0){
					current=current.right;

				} else {
					res=current;
					found=true;

				}

			}

		}

		return res;

	}

	/**
	 *
	 * Menghapus objek dari tree, menggunakan successor inorder untuk menghapus elemen yang memiliki left node dan right node
	 * Manfaatkan method minNode(Node<E> node) untuk mencari successor inorder
	 * @param elem yang ingin dihapus
	 * @return true jika elemen ditemukan dan berhasil dihapus, false jika elemen tidak ditemukan
	 *
	 */
	public boolean remove(E elem){

		boolean res = false;

		Node<E> node = find(elem);

		if(node != null){

			if(node.left == null){

				if(node.right == null){

					if (node.parent != null){		// jika bukan root
						E currElem=node.elem;
						node=node.parent;			// akan mengubah panah kanan / kiri parent (tergantung lokasi asli elem dihapus) menjadi null
						if (node.left != null){
							if (node.left.elem.equals(currElem))
								node.left=null;
						}
						if (node.right != null){
							if (node.right.elem.equals(currElem))
								node.right=null;
						}
					}
					else
						root=null;		// jika root, maka tree nya jadi kosong

				} else {

					if (node.parent != null){		// jika elem yg dihapus bukan root
						E currElem=node.elem;		// maka panah kiri / kanan parentnya akan di by pass ke anak kanan elem yg dihapus
						node=node.parent;
						if (node.left != null){
							if (node.left.elem.equals(currElem)){
								node.left.right.parent = node;
								node.left=node.left.right;
							}
						}
						if (node.right != null){
							if (node.right.elem.equals(currElem)){
								node.right.right.parent = node;
								node.right=node.right.right;
							}
						}
					}

					else {
						node.right.parent=null;		// jika elem dihapus adalah root
						root=node.right;			// maka rootnya berubah di by pass ke anak kanan elem yg dihapus
					}

				}

			} else {

				if(node.right == null){

					if (node.parent != null){		// jika elem yg dihapus bukan root
						E currElem=node.elem;		// maka panah kiri / kanan parentnya akan di by pass ke anak kanan elem yg dihapus
						node=node.parent;
						if (node.left != null){
							if (node.left.elem.equals(currElem)){
								node.left.left.parent = node;
								node.left=node.left.left;
							}
						}
						if (node.right != null){
							if (node.right.elem.equals(currElem)){
								node.right.left.parent = node;
								node.right=node.right.left;
							}
						}
					}

					else {
						node.left.parent=null;		// jika elem dihapus adalah root
						root=node.left;				// maka rootnya berubah di by pass ke anak kiri elem yg dihapus
					}

				} else {

					Node<E> succesor = minNode(node.right);
					remove(succesor.elem);

					// melakukan serangkaian usaha untuk mengganti node skrg jadi succesor
					succesor.left=node.left;
					succesor.right=node.right;
					succesor.parent=node.parent;

					if (node.left != null)
						node.left.parent = succesor;
					if (node.right != null)
						node.right.parent = succesor;

					if (node.parent != null){
						E currElem=node.elem;		// maka panah kiri / kanan parentnya akan di by pass ke anak kanan elem yg dihapus
						node=node.parent;
						if (node.left != null)
							if (node.left.elem.equals(currElem))
								node.left = succesor;

						if (node.right != null)
							if (node.right.elem.equals(currElem))
								node.right = succesor;
					}

					else{
						root = succesor;
					}

				}
			}
			res = true;			

		}

		return res;

	}

	/**
	 *
	 * Mencari elemen dengan nilai paling kecil pada tree
	 * @return elemen dengan nilai paling kecil pada tree
	 *
	 */
	public E min(){

		return minNode(root).elem;

	}

	/**
	 *
	 * Method untuk mengembalikan node dengan elemen terkecil pada suatu subtree
	 * Hint : Manfaatkan struktur dari binary search tree
	 * @param node root dari subtree yang ingin dicari elemen terbesarnya
	 * @return node dengan elemen terkecil dari subtree yang diinginkan
	 *
	 */
	private Node<E> minNode(Node<E> node){

		Node<E> res = null;
		if(node != null){

			Node<E> current = node;
			while (current.left != null)
				current=current.left;
			res=current;
		}

		return res;

	}

	/**
	 *
	 * Mencari elemen dengan nilai paling besar pada tree
	 * @return elemen dengan nilai paling besar pada tree
	 *
	 */
	public E max(){

		return maxNode(root).elem;

	}

	/**
	 *
	 * Method untuk mengembalikan node dengan elemen terbesar pada suatu subtree
	 * Hint : Manfaatkan struktur dari binary search tree
	 * @param node root dari subtree yang ingin dicari elemen terbesarnya
	 * @return node dengan elemen terbesar dari subtree yang diinginkan
	 *
	 */
	private Node<E> maxNode(Node<E> node){

		Node<E> res = null;
		if(node != null){

			Node<E> current = node;
			while (current.right != null)
				current=current.right;
			res=current;

		}

		return res;

	}

	/**
	 *
	 * Mengetahui apakah sebuah objek sudah terdapat pada tree
	 * Asumsikan jika elem.compareTo(otherElem) == 0, maka elem dan otherElem merupakan objek yang sama
	 * Hint : Manfaatkan method find
	 * @param elem yang ingin diketahui keberadaannya dalam tree
	 * @return true jika elemen ditemukan, false jika sebaliknya
	 *
	 */
	public boolean contains(E elem){

		Node<E> founded=find(elem);
		return founded!=null;

	}

	/**
	 * Mengembalikan tree dalam bentuk in-order secara ascending
	 * @return tree dalam bentuk in-order secara ascending sebagai list of E
	 */
	public List<E> inOrderAscending(){

		List<E> list = new LinkedList<>(); // default menggunakan LinkedList, silahkan menggunakan List yang sesuai dengan Anda
		return inOrderAscending(root,list);

	}

	/**
	 *
	 * Method helper dari inOrderAscending()
	 * @param node pointer
	 * @param list sebagai akumulator
	 * @return kumpulan elemen dari subtree yang rootnya adalah node parameter dengan urutan in-order secara ascending
	 *
	 */
	private List<E> inOrderAscending(Node<E> node, List<E> list){

		if (node==null)
			return list;

		inOrderAscending(node.left, list);
		list.add(node.elem);
		inOrderAscending(node.right, list);

		return list;

	}


	/**
	 * Mengembalikan tree dalam bentuk in-order secara descending
	 * @return tree dalam bentuk in-order secara descending sebagai list of E
	 */
	public List<E> inOrderDescending(){

		List<E> list = new LinkedList<>(); // default menggunakan LinkedList, silahkan menggunakan List yang sesuai dengan Anda
		return inOrderDescending(root,list);

	}

	/**
	 *
	 * Method helper dari inOrderDescending()
	 * @param node pointer
	 * @param list sebagai akumulator
	 * @return kumpulan elemen dari subtree yang rootnya adalah node parameter dengan urutan in-order descending
	 *
	 */
	private List<E> inOrderDescending(Node<E> node, List<E> list){
		if (node==null)
			return list;

		inOrderDescending(node.right, list);
		list.add(node.elem);
		inOrderDescending(node.left, list);

		return list;

	}

	public Node<E> getRoot(){
		return root;
	}


}
