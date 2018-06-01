/**
 * @author Mochamad Aulia Akbar Praditomo (1606827145)
 * SDA-A
 * Tugas Pemrograman 2
 */

//import package yang dibutuhkan
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class SDA1606827145T2 { //main class
    //variabel-variabel static
    static char[][] map;
    static HashMap<String, Dungeon> dungeons;
    static HashMap<String, ArrayList<Hero>> summoning;
    static Gudako gudako;
    static int orderHero = 0; //urutan summon hero

    public static void main(String[] args) throws IOException { //main method
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] perintah = in.readLine().split(" ");
        int jumlahHero = Integer.parseInt(perintah[0]);
        int jumlahSummoning = Integer.parseInt(perintah[1]);
        int jumlahDungeon = Integer.parseInt(perintah[2]);
        int manaGudako = Integer.parseInt(perintah[3]);
        int panjangBaris = Integer.parseInt(perintah[4]);
        int panjangColumn = Integer.parseInt(perintah[5]);

        map = new char[panjangBaris][panjangColumn];
        dungeons = new HashMap<String, Dungeon>(); //lokasi dungeon pada map
        summoning = new HashMap<String, ArrayList<Hero>>(); //lokasi hero pada map
        gudako = new Gudako(manaGudako); //inisiasi objek gudako
        ArrayList<Hero> heroTmp = new ArrayList<Hero>(); //tempat penyimpanan sementara hero

        for(int i=0; i<jumlahHero; i++) {
            perintah = in.readLine().split(";");
            Hero hero = new Hero(perintah[0], Integer.parseInt(perintah[1]), Integer.parseInt(perintah[2]), perintah[3]);
            heroTmp.add(hero);
        }
        //memasukan lokasi hero
        for(int i=0; i<jumlahSummoning; i++) {
            perintah = in.readLine().split(";");
            String[] nama = perintah[2].split(",");
            summoning.put(perintah[0]+perintah[1], new ArrayList<Hero>());
            for(int j=0; j<nama.length; j++) {
                for(Hero hero : heroTmp) {
                    if(hero.name.equals(nama[j])) {
                        summoning.get(perintah[0]+perintah[1]).add(hero);
                    }
                }
            }
        }
        //memasukan lokasi dungeon
        for(int i=0; i<jumlahDungeon; i++) {
            perintah = in.readLine().split(";");
            dungeons.put(perintah[0]+perintah[1], new Dungeon(Integer.parseInt(perintah[2]),Integer.parseInt(perintah[3]), perintah[4], Integer.parseInt(perintah[5])));
        }
        //memasukan map
        for(int i=0; i<panjangBaris; i++) {
            String command = in.readLine();
            for(int j=0; j<panjangColumn; j++) {
                map[i][j] = command.charAt(j);
                if(map[i][j]=='M') {
                    gudako.row = i;
                    gudako.column = j;
                }
            }
        }
        mulaiPetualangan(gudako.row, gudako.column); //memulai petualangan Gudako
        System.out.println("Akhir petualangan Gudako");
        System.out.println("Level Gudako: "+gudako.level);
        System.out.println("Level pahlawan:");
        Collections.sort(gudako.heroesSummoned, Hero.levelOrder);
        for(Hero hero : gudako.heroesSummoned) {
            System.out.println(hero.name+": "+hero.level);
        }
    }

    /**
     * Method untuk memulai perjalanan Gudako berpetualang di map
     * @param r = row
     * @param c = column
     */
    public static void mulaiPetualangan(int r, int c) {
        if(map[r][c]=='D') {
            dungeon(r+1, c+1); //dimulai dari 1
        }
        else if(map[r][c]=='S') {
            summon(r+1, c+1); //dimulai dari 1
        }

        //mengubah jalur yang sudah dilewati
        map[r][c] = '#';

        //bergerak ke atas
        if (r-1>=0 && map[r-1][c]!='#')
            mulaiPetualangan(r-1, c);
        //bergerak ke kanan
        if (c+1<map[0].length && map[r][c+1]!='#')
            mulaiPetualangan(r,c+1);
        //bergerak ke bawah
        if (r+1<map.length && map[r+1][c]!='#')
            mulaiPetualangan(r+1, c);
        //bergerak ke kiri
        if (c-1>=0 && map[r][c-1]!='#')
            mulaiPetualangan(r,c-1);
    }

    /**
     * Method untuk proses summon hero yang ada di suatu lokasi di map
     * @param r = row
     * @param c = column
     */
    public static void summon(int r, int c) {
        ArrayList<Hero> heroes = summoning.get(r+""+c); //hero-hero yang ada pada lokasi (r, c)
        int mana = gudako.mana; //mana gudako

        /**
         * Mengurutkan hero mulai dari power terbesar
         * Jika sama maka diurutkan berdasarkan jumlah mana yang paling rendah
         */
        Collections.sort(heroes);

        String summonedHeroes = ""; //menampung nama-nama hero yang disummon Gudako

        //Memeriksa hero-hero yang sesuai untuk disummon oleh Gudako
        for(Hero hero : heroes) {
			/*
			 * Jika jumlah mana gudako >= jumlah mana hero yang diperiksa
			 * Maka hero tersebut akan disummon oleh Gudako, dan jumlah mana
			 * Gudako akan berkurang sesuai mana hero yang telah disummon
			 */
            if(mana>=hero.mana) {
                mana -= hero.mana;
                gudako.heroesSummoned.add(hero);
                hero.order = orderHero++;
                summonedHeroes += hero.name+',';
            }
			/*
			 * Jika mana Gudako < jumlah mana hero yang diperiksa, maka Gudako
			 * Tidak akan summon hero, dan mengabaikan hero-hero yang lain
			 */
            else
                break;
        }

        //Jika ada hero yang disummon, maka cetak lokasi dan hero yang disummon
        if(summonedHeroes.length()>0)
            System.out.println(r+","+c+" Pahlawan yang ikut:"+summonedHeroes.substring(0, summonedHeroes.length()-1));

        //Jika tidak ada satupun hero yang disummon, maka cetak lokasi dan pemberitahuan
        else
            System.out.println(r+","+c+" tidak ada pahlawan yang ikut");
    }

    /**
     * Method untuk memasuki dungeon yang ada di map
     * @param r = row
     * @param c = column
     */
    public static void dungeon(int r, int c) {
        Dungeon dungeon = dungeons.get(r+""+c); //suatu dungeon dengan karakteristik tertentu
        int maxPower = 0; //kekuatan maksimal hero-hero Gudako ketika ingin memasuki dungeon
        String masuk = ""; //menampung nama-nama hero yang masuk ke dalam dungeon

		/*
		 * Jika dungeon berjenis pedang, maka hero yang bertipe pedang
		 * Memiliki battlePower yang sama dengan power awal, sedangkan
		 * Hero yang bertipe panah memiliki battlePower 2x power awal
		 */
        if(dungeon.type.equals("pedang")) {
            for(Hero hero : gudako.heroesSummoned) {
                if(hero.type.equals("pedang")) hero.battlePower = hero.power;
                else hero.battlePower = hero.power*2;
            }
        }
		/*
		 * Jika dungeon berjenis panah, maka hero yang bertipe panah
		 * Memiliki battlePower yang sama dengan power awal, sedangkan
		 * Hero yang bertipe pedang memiliki battlePower 1/2x power awal
		 */
        else if(dungeon.type.equals("panah")) {
            for(Hero hero : gudako.heroesSummoned) {
                if(hero.type.equals("panah")) hero.battlePower = hero.power;
                else hero.battlePower = hero.power/2;
            }
        }

		/*
		 * Mengurutkan hero mulai dari battlePower terbesar (descending), jika
		 * Sama, maka diurutkan siapa yang lebih dulu bergabung bersama Gudako
		 */
        Collections.sort(gudako.heroesSummoned, Hero.heroDungeon);

        //jumlah hero yang masuk ke dalam dungeon
        int jumlahHero = Math.min(dungeon.maxHeroes, gudako.heroesSummoned.size());

        for(int i=0; i<jumlahHero; i++) {
            masuk += gudako.heroesSummoned.get(i).name+",";
            maxPower += gudako.heroesSummoned.get(i).battlePower;
        }

		/*
		 * Jika maxPower Gudako >= kekuatan dungeon. maka Gudako akan masuk
		 * Dan menyelesaikan dungeon tersebut, kemudian hero akan mengalami kenaikan
		 * Level berdasarkan level dungeon, sedangkan Gudako mengalami kenaikan level
		 * Sesuai jumlah hero yang masuk dikalikan dengan level dungeon
		 */
        if(maxPower>=dungeon.power) {
            for(int i=0; i<jumlahHero; i++)
                gudako.heroesSummoned.get(i).level += dungeon.level;
            gudako.level += (jumlahHero)*dungeon.level;
            System.out.println(r+","+c+" BATTLE, kekuatan: "+maxPower+", pahlawan: "+masuk.substring(0, masuk.length()-1));
        }
		/*
		 * Jika maxPower Gudako < kekuatan dungeon
		 * Maka Gudako tidak akan masuk ke dalam dungeon tersebut
		 */
        else
            System.out.println(r+","+c+" RUN, kekuatan maksimal sejumlah: "+maxPower);
    }
}

class Gudako { //class Gudako untuk membuat objek gudako
    int mana, level, row, column; //jumlah mana, level, dan posisi row dan column Gudako
    ArrayList<Hero> heroesSummoned; //hero-hero yang disummon Gudako

    public Gudako(int manaAwal) { //class constructor
        this.mana = manaAwal;
        this.level = 1;
        this.heroesSummoned = new ArrayList<Hero>();
    }
}

class Hero implements Comparable<Hero> { //class Hero untuk membuat objek hero-hero yang akan di summon
    String name, type; //nama dan tipe senjata hero
    int mana, power, order, level, battlePower; //mana, kekuatan, urutan, level, kekuatan bertarung hero

    public Hero(String name, int mana, int power, String type) { //class constructor
        this.name = name;
        this.mana = mana;
        this.power = power;
        this.type = type;
        this.level = 1;
    }

    /**
     * Mengurutkan hero mulai dari power terbesar
     * Jika sama maka diurutkan berdasarkan jumlah mana yang paling rendah
     */
    @Override
    public int compareTo(Hero other) {
        if(this.power==other.power)
            return this.mana-other.mana;
        return other.power-this.power;
    }

    /**
     * Mengurutkan hero mulai dari battlePower terbesar
     * Jika sama maka diurutkan siapa yang lebih dulu bergabung bersama Gudako
     */
    public static Comparator<Hero> heroDungeon = new Comparator<Hero>() {
        public int compare(Hero hero1, Hero hero2) {
            if(hero1.battlePower ==hero2.battlePower)
                return hero1.order-hero2.order;
            return hero2.battlePower -hero1.battlePower;
        }
    };

    /**
     * Mengurutkan hero mulai dari level yang paling tinggi
     * Jika sama maka diurutkan sesuai siapa yang lebih dulu bergabung bersama Gudako
     */
    public static Comparator<Hero> levelOrder = new Comparator<Hero>() {
        public int compare(Hero hero1, Hero hero2) {
            if(hero1.level==hero2.level)
                return hero1.order-hero2.order;
            return hero2.level-hero1.level;
        }
    };
}

class Dungeon { //class Dungeon untuk membuat objek dungeon
    int power, level, maxHeroes; //kekuatan, level, dan maksimal hero di dungeon
    String type; //jenis senjata dungeon

    public Dungeon(int power, int level, String type, int maxHeroes) { //class constructor
        this.power = power;
        this.level = level;
        this.type = type;
        this.maxHeroes = maxHeroes;
    }
}
