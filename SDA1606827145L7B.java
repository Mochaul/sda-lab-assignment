/*
 * @author : Mochamad Aulia Akbar Praditomo (1606827145)
 * SDA-A
 * Lab 7B
 */

// import needed packages
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SDA1606827145L7B { // main class
    public static void main(String[] args) throws IOException { // main method
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(in.readLine());
        long num[] = new long[n];
        Integer ans[] = new Integer[n];
        AvlTree<Long> avl = new AvlTree<Long>();
        StringTokenizer st = new StringTokenizer(in.readLine());

        for(int i = 0; i < n; i++) { // memasukan nilai ke array num
            num[i] = Long.parseLong(st.nextToken());
        }
        for(int i = n-1; i >= 0; i--) { // memasukan avl ke array ans
            ans[i] = avl.insert(num[i]);
        }
        // looping utama
        boolean temp = false;
        for(int i = 0; i < n; i++) {
            if(temp) {
                System.out.print(" ");
            } else {
                temp = true;
            }
            System.out.print(ans[i]);
        }
        System.out.println();
    }
}
/**
 *
 * Kelas AVL Tree
 * Mahasiswa tidak diwajibkan menggunakan template ini, namun sangat disarankan menggunakan template ini
 * Pada template ini, diasumsikan kelas yang ingin dipakai mengimplementasikan (implements) interface Comparable
 * NOTE : Tidak semua method yang dibutuhkan sudah disediakan templatenya pada kelas ini sehingga mahasiswa harus menambahkan sendiri method yang dianggap perlu
 * @author Teuku Amrullah Faisal
 *
 */
class AvlTree<T extends Comparable<? super T>> {
    /**
     *
     * Kelas yang merepresentasikan node pada tree
     *
     */
    private static class AvlNode<T> {
        private T           element;      // Data di dalam node
        private AvlNode<T>  left;         // Left child
        private AvlNode<T>  right;        // Right child
        private int         height;       // Height dari node
        private int         size;		 // Size dari node

        /**
         *
         * Constructor
         * @param theElement elemen pada node
         *
         */
        AvlNode( T theElement ) {
            this( theElement, null, null );
        }

        /**
         *
         * Constructor
         * @param theElement elemen pada node
         * @param lt left node
         * @param rt right node
         *
         */
        AvlNode( T theElement, AvlNode<T> lt, AvlNode<T> rt ) {
            element  = theElement;
            left     = lt;
            right    = rt;
            height   = 0;
            size 	= 1;
        }
    }

    private AvlNode<T> root;
    private int counter;

    /**
     *
     * Constructor Kelas AVL Tree
     *
     */
    public AvlTree() {
        root = null;
    }

    /**
     * Memasukkan elemen ke dalam tree
     * @param x elemen yang akan dimasukkan.
     */
    public int insert( T x ) {
        this.counter = 0;
        root = insert( x, root );
        return this.counter;
    }

    /**
     *
     * Method internal untuk menambahkan objek ke dalam tree
     * @param x elemen yang ingin ditambahkan
     * @param t posisi node saat ini
     * @return root baru dari subtree
     *
     */
    private AvlNode<T> insert( T x, AvlNode<T> t ) {
        if( t == null ) {
            return new AvlNode<>(x, null, null);
        }

        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 ) {
            this.counter += size(t.right) + 1;
            t.left = insert(x, t.left);
        }

        else if( compareResult > 0 ) {
            t.right = insert(x, t.right);
        }

        return balance( t );
    }

    /**
     * Menghapus elemen dari tree
     * @param x elemen yang akan dihapus
     */
    public void remove( T x ) {
        root = remove( x, root );
    }

    /**
     *
     * Menghapus objek dari tree, menggunakan predecessor inorder untuk menghapus elemen yang memiliki left node dan right node
     * Manfaatkan method findMax(AvlNode<E> node) untuk mencari predecessor inorder
     * @param x elemen yang ingin dihapus
     * @param t posisi node saat ini.
     * @return true root baru dari subtree
     *
     */
    private AvlNode<T> remove( T x, AvlNode<T> t ) {
        if( t == null ) {
            return t;   // Item not found; do nothing
        }

        int compareResult = x.compareTo( t.element );

        if( compareResult < 0 ) {
            t.left = remove(x, t.left);
        }

        else if( compareResult > 0 ) {
            t.right = remove(x, t.right);
        }

        else if( t.left != null && t.right != null ) {
            t.element = findMax(t.left).element;
            t.left = remove(t.element, t.left);
        }

        else { //jika elemen yang ingin dihapus ditemukan
            if(t.left != null){
                t = t.left;
            }
            else{
                t = t.right;
            }
        }
        return balance( t );
    }

    /**
     * Mengosongkan tree
     */
    public void makeEmpty() {
        root = null;
    }

    /**
     *
     * Mengetahui apakah tree kosong atau tidak
     * @return true jika kosong, false jika sebaliknya
     *
     */
    public boolean isEmpty() {
        return root == null;

        //return false;
    }

    private static final int ALLOWED_IMBALANCE = 1;

    /**
     * Mem-balance kan tree
     * Gunakan method rotateWithLeftChild, rotateWithRightChild,
     * doubleWithRightChild, dan doubleWithLeftChild
     * Jangan lupa update height dari node
     * @param t root dari subtree yang ingin dibalance
     * @return node setelah balance
     */
    private AvlNode<T> balance( AvlNode<T> t ) {
        if( t == null ) {
            return t;
        }

        if( height( t.left ) - height( t.right ) > ALLOWED_IMBALANCE ) {
            if( height( t.left.left ) >= height( t.left.right ) ){
                t = rotateWithLeftChild(t);
                //System.out.println("Lakukan rotasi sekali pada " + t.toString());
            }
            else{
                t = doubleWithLeftChild(t);
                //System.out.println("Lakukan rotasi dua kali pada " + t.toString());
            }
        }

        else {
            if( height( t.right ) - height( t.left ) > ALLOWED_IMBALANCE ) {
                if( height( t.right.right ) >= height( t.right.left ) ) {
                    t = rotateWithRightChild(t);
                    //System.out.println("Lakukan rotasi sekali pada " + t.toString());
                }

                else {
                    t = doubleWithRightChild(t);
                    //System.out.println("Lakukan rotasi dua kali pada " + t.toString());
                }
            }
        }
        t.height = Math.max(height(t.left), height(t.right)) + 1;
        t.size = size(t.left) + size(t.right) + 1;
        return t;
    }

    /*
     * mencari item yg paling kecil
     */
    public T findMin() {
        return findMin(root).element;
    }

    /**
     * Mencari min dari sebuah subtree
     * @param t root dari sebuah subtree
     * @return node node terkecil dari subtree
     */
    private AvlNode<T> findMin( AvlNode<T> t ) {
        if( t == null ) {
            return t;
        }

        while( t.left != null ) {
            t = t.left;
        }

        return t;
    }

    /*
     * mencari item yg paling besar
     */
    public T findMax() {
        return findMax(root).element;
    }

    /**
     * Mencari max dari sebuah subtree
     * @param t root dari sebuah subtree
     * @return node node terbesar dari subtree
     */
    private AvlNode<T> findMax( AvlNode<T> t ) {
        if( t == null ) {
            return t;
        }

        while( t.right != null ) {
            t = t.right;
        }

        return t;
    }

    private int size(AvlNode<T> t) {
        if(t == null){
            return 0;
        } else {
            return t.size;
        }
    }

    public void printTree() {
        if(isEmpty()){
            System.out.println("Tidak ada data");
        } else {
            String inOrder = printInOrder();
            String preOrder = printPreOrder();
            String postOrder = printPostOrder();

            System.out.println("In Order: " + inOrder.substring(0, inOrder.length()-2));
            System.out.println("Pre Order: " + preOrder.substring(0, preOrder.length()-2));
            System.out.println("Post Order: " + postOrder.substring(0, postOrder.length()-2));
        }
    }

    /**
     * Method untuk mencetak tree secara inorder
     *
     * @return String representasi dari tree
     */
    public String printInOrder() {
        StringBuilder str = new StringBuilder();
        printInOrder (root, str);
        return str.toString();
    }

    /**
     * Method internal untuk mencetak tree secara inorder
     *
     * @param t   node dari tree
     * @param str hasil dari pencetakan tree
     */
    protected void printInOrder(AvlNode<T> t, StringBuilder str) {
        if (t != null) {
            printInOrder(t.left, str);
            String result = t.element.toString();
            str.append(result);
            str.append("; ");
            printInOrder(t.right, str);
        }
    }

    /**
     * Method untuk mencetak tree secara preorder
     *
     * @return String representasi dari tree
     */
    public String printPreOrder(){
        StringBuilder str = new StringBuilder();
        printPreOrder (root, str);
        return str.toString();
    }

    /**
     * Method internal untuk mencetak tree secara preorder
     *
     * @param t   node dari tree
     * @param str hasil dari pencetakan tree
     */
    private void printPreOrder (AvlNode<T> t, StringBuilder str){
        if (t != null){
            String result = t.element.toString();
            str.append(result);
            str.append("; ");
            printPreOrder(t.left, str);
            printPreOrder(t.right, str);
        }
    }

    /**
     * Method untuk mencetak tree secara postorder
     *
     * @return String representasi dari tree
     */
    public String printPostOrder(){
        StringBuilder str = new StringBuilder();
        printPostOrder (root, str);
        return str.toString();
    }

    /**
     * Method internal untuk mencetak tree secara postorder
     *
     * @param t   node dari tree
     * @param str hasil dari pencetakan tree
     */
    private void printPostOrder (AvlNode<T> t, StringBuilder str){
        if (t != null){
            printPostOrder(t.left, str);
            printPostOrder(t.right, str);
            String result = t.element.toString();
            str.append(result);
            str.append("; ");
        }
    }

    /**
     * Mengembalikan height dari sebuah node
     */
    private int height( AvlNode<T> t ) {
        return t == null ? -1 : t.height;
    }

    /**
     * Merotasi subtree kiri dari sebuah node
     * Ini adalah rotasi untuk kasus 1
     * Method ini telah diimplementasikan sebagian agar anda mendapatkan gambaran rotate kasus 1
     * Jangan lupa untuk mengupdate height dari node
     * @param k2 root dari subtree
     * @return root yang telah diupdate
     */
    private AvlNode<T> rotateWithLeftChild( AvlNode<T> k2 ) {
        AvlNode<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;

        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), k2.height) + 1;
        k2.size = size(k2.left) + size(k2.right) + 1;
        k1.size = size(k1.left) + size(k1.right) + 1;
        return k1;
    }

    /**
     * Merotasi subtree kanan dari sebuah node
     * Ini adalah rotasi untuk kasus 4
     * Jangan lupa untuk mengupdate height dari node
     * @param k1 root dari subtree
     * @return root yang telah diupdate
     */
    private AvlNode<T> rotateWithRightChild( AvlNode<T> k1 ) {
        AvlNode<T> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;

        k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
        k2.height = Math.max(height(k2.right), k1.height) + 1;
        k1.size = size(k1.left) + size(k1.right) + 1;
        k2.size = size(k2.left) + size(k2.right) + 1;
        return k2;
    }

    /**
     * Left child pertama kemudian diikuti oleh right child-nya
     * kemudian k3 dengan left child
     * Ini adalah rotasi untuk kasus 2
     * @param k3 node
     * @return root yang telah diupdate
     */
    private AvlNode<T> doubleWithLeftChild( AvlNode<T> k3 ) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    /**
     * Right child pertama kemudian diikuti oleh left child-nya
     * kemudian k1 dengan right childnya
     * Ini adalah rotasi untuk kasus 3
     * @param k1 node
     * @return root yang telah diupdate
     */
    private AvlNode<T> doubleWithRightChild( AvlNode<T> k1 ) {
        k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }
}