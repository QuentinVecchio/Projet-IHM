package com.insa_lyon.restin.Modeles;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

/**
 * Created by quentinvecchio on 03/01/2017.
 */

public class DataSingleton {
    private static DataSingleton instance = null;

    private ArrayList<Restaurant> restaurants = null;

    private DataSingleton() {
        this.initData();
    }

    public static DataSingleton getInstance() {
        if(DataSingleton.instance == null) {
            DataSingleton.instance = new DataSingleton();
        }
        return DataSingleton.instance;
    }

    public ArrayList<Restaurant> getRestaurants() {
        return this.restaurants;
    }

    public Restaurant getRestaurant(int position) {
        return this.restaurants.get(position);
    }

    public int getRestaurantPosition(Restaurant restaurant) {
        for(int i = 0; i < this.restaurants.size(); i++) {
            if(restaurants.get(i).equals(restaurant)){
                return i;
            }
        }
        return -1;
    }

    private void initData() {
        try {
            this.restaurants = new ArrayList<>();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRENCH);

            //Restaurant RU Jussieu
            ArrayList<Avis> avisRUJussieu = new ArrayList<>();
            avisRUJussieu.add(new Avis("Plein de choix de plat", 4, dateFormat.parse("01/01/2017 12:50")));
            avisRUJussieu.add(new Avis("Très bon rapport qualité prix, je recommande !", 5, dateFormat.parse("01/01/2017 13:13")));
            avisRUJussieu.add(new Avis("Un peu trop de monde vers 12h..", 3, dateFormat.parse("02/01/2017 13:58")));
            avisRUJussieu.add(new Avis("Nickel", 4, dateFormat.parse("03/01/2017 13:00")));
            avisRUJussieu.add(new Avis("Jamais de pizza apres 12h...", 1, dateFormat.parse("04/01/2017 12:50")));



            //Menu RU Jussieu
            ArrayList<String> entreeJussieu = new ArrayList<>();
            ArrayList<String> platJussieu = new ArrayList<>();
            ArrayList<String> dessertJussieu = new ArrayList<>();
            entreeJussieu.add("Salade Lyonnaise");
            entreeJussieu.add("Macédoine");
            platJussieu.add("Hamburger du chef");
            platJussieu.add("Bavette du chef");
            platJussieu.add("Pizza");
            dessertJussieu.add("Crème brulée");
            dessertJussieu.add("Clémentine");

            Menu menuMidiJussieu = new Menu(entreeJussieu, platJussieu, dessertJussieu);

            TreeMap<Double, Integer> affluenceMidiJussieu = new TreeMap<>();

            affluenceMidiJussieu.put(11.5, 4);
            affluenceMidiJussieu.put(12.0, 15);
            affluenceMidiJussieu.put(12.5, 25);
            affluenceMidiJussieu.put(12.8, 22);
            affluenceMidiJussieu.put(12.9, 16);
            affluenceMidiJussieu.put(13.0, 11);
            affluenceMidiJussieu.put(13.5, 8);
            affluenceMidiJussieu.put(14.0, 2);

            Restaurant ruJussieu = new Restaurant("RU Jussieu", 45.780691, 4.876519, avisRUJussieu,
                    null, menuMidiJussieu, null, null, affluenceMidiJussieu, null, 3.75);
            this.restaurants.add(ruJussieu);

            //Restaurant l'olivier
            ArrayList<Avis> avisOlivier = new ArrayList<>();

            ArrayList<String> entreeOlivier = new ArrayList<>();
            ArrayList<String> platOlivier = new ArrayList<>();
            ArrayList<String> dessertOlivier = new ArrayList<>();
            ArrayList<String> produitOlivier = new ArrayList<>();
            ArrayList<String> boissonOlivier = new ArrayList<>();

            entreeOlivier.add("Salade");
            entreeOlivier.add("Charcuterie");
            platOlivier.add("Cordon bleu - Riz");
            platOlivier.add("Saucisse lentille");
            dessertOlivier.add("Yaourt");
            dessertOlivier.add("Banane");

            Menu menuMidiOlivier = new Menu(entreeOlivier, platOlivier, dessertOlivier);

            produitOlivier.add("Croissant");
            produitOlivier.add("Pain au chocolat");
            boissonOlivier.add("Café");
            boissonOlivier.add("Jus d'orange");

            MenuMatin menuMatinOlivier = new MenuMatin(produitOlivier, boissonOlivier);

            TreeMap<Double, Integer> affluenceMidiOlivier = new TreeMap<>();
            TreeMap<Double, Integer> affluenceMatinOlivier = new TreeMap<>();

            affluenceMidiOlivier.put(11.5, 4);
            affluenceMidiOlivier.put(12.0, 10);
            affluenceMidiOlivier.put(12.5, 23);
            affluenceMidiOlivier.put(12.8, 28);
            affluenceMidiOlivier.put(12.9, 16);
            affluenceMidiOlivier.put(13.0, 18);
            affluenceMidiOlivier.put(13.5, 12);
            affluenceMidiOlivier.put(14.0, 6);

            affluenceMatinOlivier.put(7.0, 2);
            affluenceMatinOlivier.put(7.25, 4);
            affluenceMatinOlivier.put(7.5, 6);
            affluenceMatinOlivier.put(7.75, 2);
            affluenceMatinOlivier.put(8.0, 1);


            Restaurant olivier = new Restaurant("L'olivier", 45.784221, 4.874811, avisOlivier,
                    menuMatinOlivier, menuMidiOlivier, null, affluenceMatinOlivier,
                    affluenceMidiOlivier, null, 4.25);
            this.restaurants.add(olivier);

            //Restaurant le Prevert
            ArrayList<Avis> avisPrevert = new ArrayList<>();

            ArrayList<String> entreePrevert = new ArrayList<>();
            ArrayList<String> platPrevert = new ArrayList<>();
            ArrayList<String> dessertPrevert = new ArrayList<>();
            ArrayList<String> produitPrevert = new ArrayList<>();
            ArrayList<String> boissonPrevert = new ArrayList<>();

            entreePrevert.add("Saucisson");
            entreePrevert.add("Carottes râpées");
            platPrevert.add("Steak frites");
            platPrevert.add("Quiche lorraine");
            dessertPrevert.add("Liégeois");
            dessertPrevert.add("Orange");

            Menu menuSoirPrevert = new Menu(entreePrevert, platPrevert, dessertPrevert);

            produitPrevert.add("Croissant");
            produitPrevert.add("Pain au chocolat");
            boissonPrevert.add("Café");
            boissonPrevert.add("Jus d'orange");

            MenuMatin menuMatinPrevert = new MenuMatin(produitPrevert, boissonPrevert);

            Restaurant prevert = new Restaurant("Le Prevert", 45.781173, 4.873638, avisPrevert,
                    menuMatinPrevert, null, menuSoirPrevert, null, null, null, 3.5);
            this.restaurants.add(prevert);

            //Restaurant le grillon
            ArrayList<Avis> avisGrillon = new ArrayList<>();

            ArrayList<String> entreeGrillon = new ArrayList<>();
            ArrayList<String> platGrillon = new ArrayList<>();
            ArrayList<String> dessertGrillon = new ArrayList<>();

            entreeGrillon.add("Lentilles");
            entreeGrillon.add("Céleri");
            platGrillon.add("Choucroute");
            platGrillon.add("Flammekueche");
            dessertGrillon.add("Flan");
            dessertGrillon.add("Brownie");

            Menu menuMidiGrillon = new Menu(entreeGrillon, platGrillon, dessertGrillon);

            ArrayList<String> entreeSoirGrillon = new ArrayList<>();
            ArrayList<String> platSoirGrillon = new ArrayList<>();
            ArrayList<String> dessertSoirGrillon = new ArrayList<>();

            entreeSoirGrillon.add("Friand");
            entreeSoirGrillon.add("Gazpacho à la citrouille");
            platSoirGrillon.add("Blanquette de veau - riz");
            platSoirGrillon.add("Cassoulet");
            dessertSoirGrillon.add("Compote de pomme");
            dessertSoirGrillon.add("Tarte au citron");

            Menu menuSoirGrillon = new Menu(entreeSoirGrillon, platSoirGrillon, dessertSoirGrillon);


            ArrayList<String> produitGrillon = new ArrayList<>();
            ArrayList<String> boissonGrillon = new ArrayList<>();

            produitGrillon.add("Croissant");
            produitGrillon.add("Brioche");
            boissonGrillon.add("Chocolat chaud");
            boissonGrillon.add("Thé");

            MenuMatin menuMatinGrillon = new MenuMatin(produitGrillon, boissonGrillon);

            Restaurant grillon = new Restaurant("Le Grillon", 45.783927, 4.875049, avisGrillon,
                    menuMatinGrillon, menuMidiGrillon, menuSoirGrillon, null, null, null, 3.70);
            this.restaurants.add(grillon);

            //Restaurant la Roulotte dorée
            ArrayList<Avis> avisRoulotteDoree = new ArrayList<>();

            ArrayList<String> entreeRoulotte = new ArrayList<>();
            ArrayList<String> platRoulotte = new ArrayList<>();
            ArrayList<String> dessertRoulotte = new ArrayList<>();

            entreeRoulotte.add("Salade d'endive");
            entreeRoulotte.add("Foie gras");
            platRoulotte.add("Saumon fumé à la marseillaise");
            platRoulotte.add("Bouillabaisse");
            platRoulotte.add("Moules marinées");
            dessertRoulotte.add("Calisson");
            dessertRoulotte.add("Brioche");

            Menu menuSoirRoulotte = new Menu(entreeRoulotte, platRoulotte, dessertRoulotte);

            Restaurant roulotteDoree = new Restaurant("La Roulotte Dorée", 45.782565, 4.876553,
                    avisRoulotteDoree, null, null, menuSoirRoulotte, null, null, null, 5.0);
            this.restaurants.add(roulotteDoree);

            //Restaurant Ali Baba Kebab
            ArrayList<Avis> avisNinkasi = new ArrayList<>();


            ArrayList<String> platKebab = new ArrayList<>();
            ArrayList<String> dessertKebab = new ArrayList<>();

            platKebab.add("Chiche kebab");
            platKebab.add("Chiche taouk");
            platKebab.add("Frites");
            dessertKebab.add("Cornes de gazelle");

            Menu menuMidiKebab = new Menu(null, platKebab, dessertKebab);

            ArrayList<String> platSoirKebab = new ArrayList<>();
            ArrayList<String> dessertSoirKebab = new ArrayList<>();

            platSoirKebab.add("Chiche kebab");
            platSoirKebab.add("Americano");
            platSoirKebab.add("Cordon bleu - frites");
            dessertSoirKebab.add("Loukoum");

            Menu menuSoirKebab = new Menu(null, platSoirKebab, dessertSoirKebab);

            Restaurant ninkasi = new Restaurant("Ninkasi", 45.778878, 4.872942, avisNinkasi, null,
                    menuMidiKebab, menuSoirKebab, null, null, null, 7.0);
            this.restaurants.add(ninkasi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
