import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.LinkedList;

public class SDA1606827145L5A {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String perintah;
        BSTree<String> bsTree = new BSTree<>();

        while ((perintah = in.readLine()) != null) {
            String[] perintahPerKata = perintah.split(";");
            if (perintahPerKata[0].equalsIgnoreCase("add")) {
                if(bsTree.add(perintahPerKata[1])){
                    System.out.println(perintahPerKata[1]+" berhasil ditambahkan ke dalam tree");
                }
                else System.out.println(perintahPerKata[1]+ " sudah dimasukkan sebelumnya");
            } else if (perintahPerKata[0].equalsIgnoreCase("remove")) {
                if(bsTree.remove(perintahPerKata[1])) System.out.println(perintahPerKata[1]+" berhasil dihapus dari tree");
                else System.out.println(perintahPerKata[1]+" tidak ditemukan");
            } else if (perintahPerKata[0].equalsIgnoreCase("contains")) {
                if(bsTree.contains(perintahPerKata[1])) System.out.println(perintahPerKata[1]+" terdapat pada tree");
                else System.out.println(perintahPerKata[1]+" tidak terdapat pada tree");
            } else if (perintahPerKata[0].equalsIgnoreCase("preorder")) {
                if(!bsTree.isEmpty()) {
                    String print = bsTree.preOrder().toString().replace(", ",";");
                    System.out.println(print.substring(1, print.length()-1));
                }
                else System.out.println("Tidak ada elemen pada tree");
            } else if (perintahPerKata[0].equalsIgnoreCase("postorder")) {
                if(!bsTree.isEmpty()) {
                    String print = bsTree.postOrder().toString().replace(", ",";");
                    System.out.println(print.substring(1, print.length()-1));
                }
                else System.out.println("Tidak ada elemen pada tree");
            } else if (perintahPerKata[0].equalsIgnoreCase("ascending")) {
                if(!bsTree.isEmpty()) {
                    String print = bsTree.inOrderAscending().toString().replace(", ",";");
                    System.out.println(print.substring(1, print.length()-1));
                }
                else System.out.println("Tidak ada elemen pada tree");
            } else if (perintahPerKata[0].equalsIgnoreCase("descending")) {
                if(!bsTree.isEmpty()) {
                    String print = bsTree.inOrderDescending().toString().replace(", ",";");
                    System.out.println(print.substring(1, print.length()-1));
                }
                else System.out.println("Tidak ada elemen pada tree");
            } else if (perintahPerKata[0].equalsIgnoreCase("max")) {
                if(!bsTree.isEmpty()) System.out.println(bsTree.max()+" merupakan elemen dengan nilai tertinggi");
                else System.out.println("Tidak ada elemen pada tree");
            } else if (perintahPerKata[0].equalsIgnoreCase("min")) {
                if(!bsTree.isEmpty()) System.out.println(bsTree.min()+" merupakan elemen dengan nilai terendah");
                else System.out.println("Tidak ada elemen pada tree");
            }
        }
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
class BSTree<E extends Comparable<? super E>> {

    private static class Node<E> {

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
    public BSTree(){

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

            // TO DO : Lengkapi bagian ini
            root = new Node<E>(elem, null,null,null);
            res = true;

        } else {

            Node<E> prev = null;
            Node<E> current = root;
            while(current != null){

                E currElem = current.elem;
                if(elem.compareTo(currElem) < 0){
                    // TO DO : Lengkapi bagian ini
                    prev = current;
                    current=current.left;
                    if(current==null) prev.left = new Node<E>(elem,null,null,prev);


                } else if(elem.compareTo(currElem) > 0){
                    // TO DO : Lengkapi bagian ini
                    prev = current;
                    current = current.right;
                    if(current==null) prev.right = new Node<E>(elem,null,null,prev);

                } else {
                    // TO DO : Lengkapi bagian ini
                    return false;

                }

            }
            res = true;
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
    private Node<E> find(E elem){

        Node<E> res = null;

        if(root != null){

            Node<E> current = root;
            boolean found = false;
            while(!found && current != null){

                E currElem = current.elem;
                if(elem.compareTo(currElem) < 0){
                    // TO DO : Lengkapi bagian ini
                    current = current.left;

                } else if(elem.compareTo(currElem) > 0){
                    // TO DO : Lengkapi bagian ini
                    current=current.right;

                } else {
                    // TO DO : Lengkapi bagian ini
                    res = current;
                    found = true;
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
            // TO DO : Lengkapi bagian ini
            while(current.left!=null){
                current = current.left;
            }
            res = current;

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
            // TO DO : Lengkapi bagian ini
            while(current.right!=null){
                current=current.right;
            }
            res = current;
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
        // TO DO : Lengkapi method ini
        Boolean res = false;
        if(find(elem)!= null) res = true;
        return res;
    }

    /**
     * Mengembalikan tree dalam bentuk pre-order
     * @return tree dalam bentuk pre-order sebagai list of E
     */
    public List<E> preOrder(){

        List<E> list = new LinkedList<>(); // default menggunakan LinkedList, silahkan menggunakan List yang sesuai dengan Anda
        return preOrder(root,list);

    }

    /**
     *
     * Method helper dari preOrder()
     * @param node pointer
     * @param list sebagai akumulator
     * @return kumpulan elemen dari subtree yang rootnya adalah node parameter dengan urutan pre-order
     *
     */
    private List<E> preOrder(Node<E> node, List<E> list){

        // TO DO : Lengkapi method ini
        list.add(node.elem);
        if (node.left!=null) {
            preOrder(node.left, list);
        }
        if (node.right!=null) {
            preOrder(node.right, list);
        }
        return list;

    }

    /**
     * Mengembalikan tree dalam bentuk post-order
     * @return tree dalam bentuk post-order sebagai list of E
     */
    public List<E> postOrder(){

        List<E> list = new LinkedList<>(); // default menggunakan LinkedList, silahkan menggunakan List yang sesuai dengan Anda
        return postOrder(root,list);


    }

    /**
     *
     * Method helper dari postOrder()
     * @param node pointer
     * @param list sebagai akumulator
     * @return kumpulan elemen dari subtree yang rootnya adalah node parameter dengan urutan post-order
     *
     */
    private List<E> postOrder(Node<E> node, List<E> list){

        // TO DO : Lengkapi method ini
        if (node.left!=null) {
            postOrder(node.left, list);
        }
        if (node.right!=null){
            postOrder(node.right, list);
        }
        list.add(node.elem);
        return list;
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

        // TO DO : Lengkapi method ini
        if(node != null){
            inOrderAscending(node.left, list);
            list.add(node.elem);
            inOrderAscending(node.right, list);
        }
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
        // TO DO : Lengkapi method ini
        if(node != null){
            inOrderDescending(node.right, list);
            list.add(node.elem);
            inOrderDescending(node.left, list);
        }
        return list;
    }
}