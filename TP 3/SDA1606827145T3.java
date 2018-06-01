/* TUGAS PEMROGRAMAN 3
 * Nama	    : Mochamad Aulia Akbar Praditomo
 * NPM		: 1606827145
 * Kelas	: SDA-A
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SDA1606827145T3 {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        RootFolder rootFolder = new RootFolder();
        String input = "";
        while((input = in.readLine()) != null){
            StringTokenizer st = new StringTokenizer(input);
            String command = st.nextToken().toLowerCase();
            switch(command){
                case "add":
                    String namaFolderBaru = st.nextToken();
                    String namaFolderTujuan = st.nextToken();
                    rootFolder.add(namaFolderBaru, namaFolderTujuan);
                    break;
                case "insert":
                    String fileBaru = st.nextToken();
                    int fileSize = Integer.parseInt(st.nextToken());
                    String folderTujuan = st.nextToken();
                    StringTokenizer st2 = new StringTokenizer(fileBaru, ".");
                    String namaFile = st2.nextToken();
                    String tipeFile = st2.nextToken();
                    rootFolder.insertFile(namaFile, tipeFile, fileSize, folderTujuan, false);
                    break;
                case "remove":
                    String yangDiRemove = st.nextToken();
                    rootFolder.remove(yangDiRemove, false);
                    break;
                case "search":
                    String yangDiSearch = st.nextToken();
                    rootFolder.search(yangDiSearch);
                    break;
                case "print":
                    String folderMauDiPrint = st.nextToken();
                    rootFolder.print(folderMauDiPrint);
                    break;
            }
        }
    }

    /*
     * implementasi kelas RootFolder yang defaultnya berisi folder root
     */
    static class RootFolder {
        private Folder root;
        private ArrayList<String> listNamaFolder;

        //konstruktor
        public RootFolder() {
            this.root = new Folder("root", null);
            this.listNamaFolder = new ArrayList<>();
        }

        /*
         * method add dipanggil dari main
         * parameter namaFolderBaru dan folderTujuan
         */
        public void add(String namaFolderBaru, String folderTujuan) {
            if(listNamaFolder.contains(folderTujuan) || folderTujuan.equals("root")) {
                add(root, namaFolderBaru, folderTujuan);
            }
        }

        /*
         * method helper add yang akan merekursif sampai ketemu
         * folder yang namanya seperti di parameter
         */
        public void add(Folder folder, String namaFolderBaru, String folderTujuan) {
            if(folder.getNama().equals(folderTujuan)) {
                folder.addFolder(namaFolderBaru);
            }else {
                Folder yangContainFolder = folder.findFolder(folderTujuan);
                while(!yangContainFolder.getNama().equals(folderTujuan)) {
                    yangContainFolder = yangContainFolder.findFolder(folderTujuan);
                }
                yangContainFolder.addFolder(namaFolderBaru);
            }
            listNamaFolder.add(namaFolderBaru);
        }

        /*
         * method untuk insertFile
         * method akan merekursif sampai ketemu folder kosong atau
         * tipenya file dengan jenis file yang sama
         * menggunakan bantuan arraylist blacklist untuk menandakan
         * folder yang sudah pernah dikunjungi
         */
        public String insertFile(String namaFile, String tipeFile, int fileSize, String folderAwal, boolean isCut) {
            Folder initial = null;
            if(folderAwal.equals("root")) {
                initial = root;
            }else {
                if(!listNamaFolder.contains(folderAwal)) {
                    return "";
                }
                initial = root.findFolder(folderAwal);
                while(!initial.getNama().equals(folderAwal)) {
                    initial = initial.findFolder(folderAwal);
                }
            }
            ArrayList<Folder> blacklist = new ArrayList<>();
            Folder folderTujuan = findFolderTujuan(initial, tipeFile, blacklist);
            if(folderTujuan != null) {
                folderTujuan.addFile(namaFile, tipeFile, fileSize);
                if(!isCut) {
                    System.out.println(namaFile + "." + tipeFile + " added to " + folderTujuan.getNama());
                }
                return folderTujuan.getNama();
            }
            return "";
        }

        /*
         * method helper insertFile yang akan mengembalikan folder tujuan
         * yang sudah final (yang akan dimasukki filenya)
         */
        public Folder findFolderTujuan(Folder folder, String tipeFile, ArrayList<Folder> blacklist) {
            if(folder.isEmpty() || (folder.getTipe().equals("file") && folder.getTipeFile().equals(tipeFile))) {
                return folder;
            }
            if(!blacklist.contains(folder)) {
                blacklist.add(folder);
                if(folder.getTipe().equals("folder")) {
                    Folder anakPertama = folder.getDataFolder().get(0);
                    return findFolderTujuan(anakPertama, tipeFile, blacklist);
                }
            }
            if(folder.getParent() != null) {
                ArrayList<Folder> anakBapak = folder.getParent().getDataFolder();
                int indexFolderNow = anakBapak.indexOf(folder);
                Folder nextFolder = anakBapak.get((indexFolderNow + 1) % anakBapak.size());
                if(blacklist.contains(nextFolder)) {
                    blacklist.add(folder.getParent());
                    return findFolderTujuan(folder.getParent(), tipeFile, blacklist);
                }else {
                    return findFolderTujuan(nextFolder, tipeFile, blacklist);
                }
            }
            return null;
        }

        /*
         * method untuk print isi dari suatu folder
         * dipanggil dari main
         */
        public void print(String folderTujuan) {
            String builder = "";
            if(folderTujuan.equals("root")) {
                builder += printHelp(root, 0);
            }else {
                Folder yangNamaSama = root.findFolder(folderTujuan);
                while(!yangNamaSama.getNama().equals(folderTujuan)) {
                    yangNamaSama = yangNamaSama.findFolder(folderTujuan);
                }
                builder += printHelp(yangNamaSama, 0);
            }
            System.out.print(builder);
        }

        /*
         * method helper print
         */
        public String printHelp(Folder folder, int tabs) {
            String tab = "";
            for(int i = 0; i < tabs; i ++) {
                tab += "  ";
            }
            String builder = tab + "> " + folder.getNama() + " " + folder.getSize() + "\n";
            if(folder.getTipe().equals("folder")) {
                for(Folder folderInside: folder.getDataFolder()) {
                    builder += printHelp(folderInside, tabs + 1);
                }
            }else if(folder.getTipe().equals("file")) {
                tab += "  ";
                for (File file: folder.getDataFile()) {
                    builder += tab + "> " + file.toString() + " " + file.size + "\n";
                }
            }
            return builder;
        }

        /*
         * method untuk remove dipanggil dari main
         * jika yang dihapus merupakan folder, maka
         * akan dicari dan dihapus folder tersebut
         * sementara jika yang dihapus berupa file
         * maka method akan mencari file-file yang
         * namanya sama dengan parameter lalu dihapus
         */
        public void remove(String yangDiRemove, boolean isMove) {
            if(listNamaFolder.contains(yangDiRemove)) {
                //remove folder
                Folder diRemove = root.findFolder(yangDiRemove);
                while(!diRemove.getNama().equals(yangDiRemove)) {
                    diRemove = diRemove.findFolder(yangDiRemove);
                }
                if(!isMove) {
                    System.out.println("Folder " + diRemove.getNama() + " removed");
                }
                int sizeFolderRemoved = diRemove.getSize();
                Folder parentRemoved = diRemove.getParent();
                while(parentRemoved != null) {
                    parentRemoved.setSize(parentRemoved.getSize() - sizeFolderRemoved);
                    parentRemoved = parentRemoved.getParent();
                }
                if(diRemove.getParent().getSize() == 1) {
                    diRemove.getParent().setTipe("kosong");
                }
                diRemove.getParent().remove(diRemove);
                listNamaFolder.remove(yangDiRemove);
            }else {
                int banyakRemove = 0;
                banyakRemove += removeHelper(root, yangDiRemove, 0);
                System.out.println(banyakRemove + " File " + yangDiRemove + " removed");
            }
        }

        /*
         * remove helper untuk meremove
         * semua file dengan nama sesuai parameter
         */
        public int removeHelper(Folder folder, String namaRemove, int banyakRemoved) {
            int jumlahRemoved = 0;
            if(folder.getTipe().equals("file")) {
                ArrayList<File> fileRemoved = new ArrayList<>();
                int jumlahPenguranganSize = 0;
                for(File file: folder.getDataFile()) {
                    if(file.getNama().equals(namaRemove)) {
                        fileRemoved.add(file);
                        jumlahPenguranganSize += file.getSize();
                        jumlahRemoved++;
                    }
                }
                for(File file: fileRemoved) {
                    folder.remove(file);
                }
                Folder parentFolder = folder;
                while(parentFolder != null) {
                    parentFolder.setSize(parentFolder.getSize() - jumlahPenguranganSize);
                    parentFolder = parentFolder.getParent();
                }
                if(folder.size == 1) {
                    folder.setTipe("kosong");
                }
            }else {
                for(Folder fold: folder.getDataFolder()) {
                    jumlahRemoved += removeHelper(fold, namaRemove, banyakRemoved);
                }
            }
            return jumlahRemoved;
        }

        /*
         * method search dipanggil dari main
         * untuk mencari path dari root sampai
         * ke suatu folder atau file
         */
        public void search(String yangDiSearch) {
            String builder = "";
            if(yangDiSearch.equals("root")) {
                builder += "> root";
            }else if(listNamaFolder.contains(yangDiSearch)) {
                builder += searchHelpFolder(root, yangDiSearch, 0);
            }else {
                builder += searchHelpFile(root, yangDiSearch, 0);
            }
            System.out.print(builder);
        }

        /*
         * method helper search jika yang dicari adalah
         * sebuah folder
         */
        public String searchHelpFolder(Folder folder, String yangDiSearch, int tabs) {
            String tab = "";
            for(int i = 0; i < tabs; i ++) {
                tab += "  ";
            }
            String builder = tab + "> " + folder.getNama() + "\n";
            for(Folder isiFolder: folder.getDataFolder()) {
                if(isiFolder.getNama().equals(yangDiSearch)) {
                    builder += tab + "  > " + isiFolder.getNama() + "\n";
                    return builder;
                }
                if(isiFolder.contain(yangDiSearch)) {
                    builder += searchHelpFolder(isiFolder, yangDiSearch, tabs + 1);
                    return builder;
                }
            }
            return builder;
        }

        /*
         * method helper search jika yang dicari
         * adalah sebuah file
         */
        public String searchHelpFile(Folder folder, String yangDiSearch, int tabs) {
            String tab = "";
            for(int i = 0; i < tabs; i ++) {
                tab += "  ";
            }
            String builder = tab + "> " + folder.getNama() + "\n";
            if(folder.getTipe().equals("file")) {
                for(File file: folder.dataFile) {
                    if(file.getNama().equals(yangDiSearch)) {
                        builder += tab + "  > " + file.getNama() + "." + file.getTipe() + "\n";
                    }
                }
            }else {
                for(Folder isiFolder: folder.getDataFolder()) {
                    if(isiFolder.contain(yangDiSearch)) {
                        builder += searchHelpFile(isiFolder, yangDiSearch, tabs + 1);
                    }
                }
            }
            return builder;
        }
    }

    /*
     * implementasi kelas folder
     * mempunyai atribut nama, tipe, tipeFile
     * size, dataFolder, dataFile, dan parent
     * ada 3 jenis tipe, kosong, file, dan folder
     * jika tipenya file, maka data yang diakses
     * adalah dataFile, sementara jika tipenya folder
     * yang diakses adalah dataFolder
     * jika tipenya kosong menandakan folder tidak berisi apa-apa
     */
    static class Folder implements Comparable<Folder> {
        private String nama, tipe, tipeFile;
        private int size;
        private ArrayList<Folder> dataFolder;
        private ArrayList<File> dataFile;
        private ArrayList<String> peranakan;
        private Folder parent;

        //konstruktor
        public Folder(String nama, Folder parent) {
            this.nama = nama;
            this.tipe = "kosong";
            this.tipeFile = "kosong";
            this.size = 1;
            this.parent = parent;
            this.dataFile = new ArrayList<>();
            this.dataFolder = new ArrayList<>();
            this.peranakan = new ArrayList<>();
        }

        public Folder(String nama, String tipe, Folder parent) {
            this.nama = nama;
            this.tipe = tipe;
            this.tipeFile = "kosong";
            this.size = 1;
            this.dataFile = new ArrayList<>();
            this.dataFolder = new ArrayList<>();
            this.parent = parent;
            this.peranakan = new ArrayList<>();
        }

        /*
         * setter getter
         */

        public String getNama() {
            return nama;
        }

        public String getTipe() {
            return tipe;
        }

        public void setTipe(String tipe) {
            this.tipe = tipe;
        }

        public String getTipeFile() {
            return tipeFile;
        }

        public void setTipeFile(String tipeFile) {
            this.tipeFile = tipeFile;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public ArrayList<Folder> getDataFolder() {
            return dataFolder;
        }

        public Folder getParent() {
            return parent;
        }

        public ArrayList<File> getDataFile() {
            return dataFile;
        }

        public void setPeranakan(ArrayList<String> peranakanBaru) {
            this.peranakan = peranakanBaru;
        }

        public ArrayList<String> getPeranakan(){
            return this.peranakan;
        }

        /*
         * method remove file dengan nama
         * sesuai parameter
         */
        public void remove(File file) {
            this.dataFile.remove(file);
            this.peranakan.remove(file.getNama());
            Folder parentFolder = this.parent;
            while(parentFolder != null) {
                parentFolder.remove(file.getNama());
                parentFolder = parentFolder.getParent();
            }
        }

        /*
         * method remove peranakan dengan nama
         * sesuai parameter
         */
        public void remove (String dihapus) {
            this.peranakan.remove(dihapus);
        }

        /*
         * method remove file dengan nama
         * sesuai parameter
         */
        public void remove (Folder folder) {
            this.dataFolder.remove(folder);
            this.peranakan.remove(folder.getNama());
            Folder parentFolder = this.parent;
            while(parentFolder != null) {
                parentFolder.remove(folder.getNama());
                parentFolder = parentFolder.getParent();
            }
        }

        /*
         * method mengembalikan kalau folder ini kosong atau tidak
         */
        public boolean isEmpty() {
            return this.tipe.equals("kosong");
        }

        /*
         * method khusus untuk case menambah folder
         * ke dalam folder bertipe file
         * setelah ditambahkan dataFile, size folder
         * bertambah sesuai isi dataFile
         */
        public void setDataFile(ArrayList<File> newDataFile) {
            for(File file: newDataFile) {
                this.dataFile = addFileSorted(file, this.dataFile);
                this.peranakan.add(file.getNama());
            }
            for(File file: newDataFile) {
                this.size += file.size;
            }
        }

        /*
         * method untuk menambah file kedalam folder
         * lalu mengubah tipeFile yang dikandung
         */
        public void addFile(String namaFile, String tipeFile, int fileSize) {
            this.tipe = "file";
            this.tipeFile = tipeFile;
            File newFile = new File(namaFile, tipeFile, fileSize);
            this.peranakan.add(namaFile);
            this.dataFile = addFileSorted(newFile, this.dataFile);
            this.size += fileSize;
            Folder parentFolder = this.parent;
            while(parentFolder != null) {
                parentFolder.setSize(parentFolder.getSize() + fileSize);
                ArrayList<String> peranakanParent = parentFolder.getPeranakan();
                peranakanParent.add(namaFile);
                parentFolder.setPeranakan(peranakanParent);
                parentFolder = parentFolder.getParent();
            }
        }

        /*
         * method untuk mengetahui jika folder ini
         * mengandung peranakan yang mempunyai
         * nama sesuai parameter atau anak dari anak folder
         */
        public boolean contain(String namaAnak) {
            return this.peranakan.contains(namaAnak);
        }

        /*
         * method addFolder dengan parameter Folder
         * lalu diurutkan isi folder sesuai leksikografis
         * ada case khusus jika folder ini bertipe file
         * tipe folder akan diubah menjadi folder
         * dan isi filenya dimasukkan ke anak baru tersebut
         */
        public void addFolder(String namaFolder) {
            Folder newFolder = new Folder(namaFolder, this);
            if(this.tipe.equals("folder")) {
                this.dataFolder = addFolderSorted(newFolder, this.dataFolder);
            }else if(this.tipe.equals("file")) {
                newFolder = new Folder(namaFolder, "file", this);
                ArrayList<File> arrFileTemp = this.dataFile;
                //isi folder baru diset menjadi file dari folder current
                newFolder.setDataFile(arrFileTemp);
                newFolder.setTipeFile(this.tipeFile);
                this.dataFile.clear();
                this.dataFolder.add(newFolder);
                this.tipe = "folder";
            }else {
                this.tipe = "folder";
                this.dataFolder.add(newFolder);
            }
            this.peranakan.add(namaFolder);
            this.size++;
            Folder parentFolder = this.parent;
            while(parentFolder != null) {
                ArrayList<String> peranakanParent = parentFolder.getPeranakan();
                peranakanParent.add(namaFolder);
                parentFolder.setPeranakan(peranakanParent);
                parentFolder.setSize(parentFolder.getSize() + 1);
                parentFolder = parentFolder.parent;
            }
        }

        /*
         * method untuk mencari folder anak yang contain
         * folder dengan nama sesuai parameter
         */
        public Folder findFolder(String namaFolder) {
            for(Folder folder: this.dataFolder) {
                if(folder.getNama().equals(namaFolder) || folder.contain(namaFolder)) {
                    return folder;
                }
            }
            return null;
        }

        /*
         * method untuk mencari folder anak yang contain
         * file dengan nama sesuai parameter
         */
        public ArrayList<Folder> findFile(String namaFile, ArrayList<Folder> containFile) {
            if(this.tipe.equals("file")) {
                containFile.add(this);
            }else if(this.tipe.equals("folder")) {
                for(Folder folder: this.dataFolder) {
                    if(folder.contain(namaFile)) {
                        containFile = folder.findFile(namaFile, containFile);
                    }
                }
            }
            return containFile;
        }

        /*
         * method compareTo dengan perbandingan nama
         */
        @Override
        public int compareTo(Folder lain) {
            return this.nama.compareTo(lain.nama);
        }
    }

    /*
     * implementasi kelas file
     */
    static class File implements Comparable<File>{
        private String nama, tipe;
        private int size;

        //konstruktor
        public File(String nama, String tipe, int size) {
            this.nama = nama;
            this.tipe = tipe;
            this.size = size;
        }

        /*
         * method untuk mengembalikan
         * nama file berikut tipenya
         */
        @Override
        public String toString() {
            return this.nama + "." + this.tipe;
        }

        /*
         * setter getter
         */

        public String getNama() {
            return this.nama;
        }

        public String getTipe() {
            return this.tipe;
        }

        public int getSize() {
            return this.size;
        }

        /*
         * method compareTo
         * membandingkan nama dulu
         * kalau nama sama membandingkan tipe file
         * kalau tipeFile sama membandingkan size
         */
        @Override
        public int compareTo(File other) {
            if(this.nama.equals(other.getNama())) {
                if(this.tipe.equals(other.getTipe())) {
                    return this.size - other.getSize();
                }else {
                    return this.tipe.compareTo(other.getTipe());
                }
            }else{
                return this.nama.compareTo(other.getNama());
            }
        }
    }

    /*
     * method untuk menambahkan folder
     * secara berurutan
     */
    public static ArrayList<Folder> addFolderSorted(Folder folder, ArrayList<Folder> folders){
        int indexMasuk = 0;
        if(folders.isEmpty()) {
            folders.add(folder);
            return folders;
        }
        while(indexMasuk < folders.size()) {
            if(folder.compareTo(folders.get(indexMasuk)) > 0) {
                indexMasuk++;
            }else {
                break;
            }
        }
        folders.add(indexMasuk, folder);
        return folders;
    }

    /*
     * method untuk menambahkan file ke list
     * secara berurutan
     */
    public static ArrayList<File> addFileSorted(File file, ArrayList<File> files){
        int indexMasuk = 0;
        if(files.isEmpty()) {
            files.add(file);
            return files;
        }
        while(indexMasuk < files.size()) {
            if(file.compareTo(files.get(indexMasuk)) > 0) {
                indexMasuk++;
            }else {
                break;
            }
        }
        files.add(indexMasuk, file);
        return files;
    }
}
